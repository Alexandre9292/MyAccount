package com.bnpp.dco.presentation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.ParamFuncTypeDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.form.ParamForm;
import com.bnpp.dco.presentation.form.ParamFormCountryEntity;
import com.bnpp.dco.presentation.form.ParamFormCountryLang;
import com.bnpp.dco.presentation.form.ParamFormEnableLang;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

/**
 * Controller managing functional parameters.
 */
@Controller
public class ParamController extends GenericController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ParamController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private ParamForm form;

    @ModelAttribute("form")
    public ParamForm getParamForm() {
        return this.form;
    }

    /** The default language object. */
    private static LanguageDto defaultLanguage;

    /**
     * Getter for the default language.<br/>
     * Initializes the object if not already done.<br/>
     * This getter <b>MUST</b> be the only way to access the object.<br/>
     * @return the default language object
     */
    public static synchronized LanguageDto getDefaultLanguage() {
        if (defaultLanguage == null) {
            try {
                defaultLanguage = (LanguageDto) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                        Constants.CONTROLLER_LANGUAGE_GET_BY_ID, new Object[] {Constants.DEFAULT_LANGUAGE});
            } catch (final DCOException e) {
                LOG.error("Error while initializing the default language", e);
                // Nothing special to do here, this case should never happen as it is in the init SQL script
            }
        }
        return defaultLanguage;
    }

    @RequestMapping(value = "paramLoad", method = RequestMethod.GET)
    public final String paramLoad(final Model model) {
        this.form.setSelectedType(0);
        this.form.setSelectedCountry(null);
        try {
            this.form.setTypes((List<ParamFuncTypeDto>) BusinessHelper.call(Constants.CONTROLLER_PARAM,
                    Constants.CONTROLLER_PARAM_LOAD_TYPES, null));
            this.form.setLanguages((List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                    Constants.CONTROLLER_LIST, null));
            this.form.setCountries((List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                    Constants.CONTROLLER_LIST, null));
        } catch (final DCOException e) {
            LOG.error("An error occurred while loading parameter types or countries", e);
            addError(this.propertiesHelper.getMessage("page.param.error.loading"));
        }
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramFilterType", method = RequestMethod.POST)
    public final String paramFilterType(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        if (this.form.getSelectedType() == Constants.VAR_ZERO) {
            this.form.setParameters(null);
        } else {
            for (final ParamFuncTypeDto t : this.form.getTypes()) {
                if (t.getId().equals(this.form.getSelectedType())) {
                    this.form.setCurrentType(t);
                    break;
                }
            }
            // Resetting the selected country if the new type is not for all countries
            // and the selected country is not a bank country
            if (this.form.isStandardBankCountries() && this.form.getCountries() != null) {
                boolean found = false;
                for (final CountryDto c : this.form.getCountries()) {
                    if (c.getLocale().getCountry().equals(this.form.getSelectedCountry())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    this.form.setSelectedCountry(null);
                }
            }
        }
        fetchParams();
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramStdFilterCountry", method = RequestMethod.POST)
    public final String paramFilterStdCountry(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        if (this.form.getSelectedCountry() == null) {
            this.form.setParameters(null);
        }
        fetchParams();
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramStdAdd", method = RequestMethod.POST)
    public final String paramStdAdd(@ModelAttribute("form") final ParamForm postForm, final BindingResult result,
            final Model model) {
        if (StringUtils.isBlank(this.form.getNewEntry())) {
            addError(this.propertiesHelper.getMessage("page.param.error.new.entry.empty"));
        } else {
            // Adding the new parameter entries for each language
            if (this.form.getParameters() == null) {
                this.form.setParameters(new HashMap<String, List<ParamFuncDto>>());
            }
            final List<ParamFuncDto> newList = new ArrayList<ParamFuncDto>();
            this.form.getParameters().put(this.form.getNewEntry(), newList);
            for (final LanguageDto l : this.form.getLanguages()) {
                final ParamFuncDto newParam = new ParamFuncDto();
                newParam.setParamFuncType(this.form.getCurrentType());
                newParam.setCountry(this.form.getSelectedCountry());
                newParam.setLanguage(l);
                newParam.setEntry(this.form.getNewEntry());
                newList.add(newParam);
            }
        }
        // Returning
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramStdSave", method = RequestMethod.POST)
    public final String paramStdSave(@ModelAttribute("form") final ParamForm postForm, final BindingResult result,
            final Model model) {
        try {
            // Controlling that a value has been defined for the default language for each entry
            boolean error = false;
            for (final Map.Entry<String, List<ParamFuncDto>> entry : this.form.getParameters().entrySet()) {
                boolean foundDefaultLanguageValue = false;
                for (final ParamFuncDto param : entry.getValue()) {
                    if (param.getLanguage().getId().equals(getDefaultLanguage().getId())) {
                        if (StringUtils.isNotBlank(param.getValue())) {
                            foundDefaultLanguageValue = true;
                        }
                        break;
                    }
                }
                if (!foundDefaultLanguageValue) {
                    error = true;
                    break;
                }
            }
            if (error) {
                addError(this.propertiesHelper.getMessage(
                        "page.param.error.default.language.value",
                        new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                                UserHelper.getUserInSession().getPreferences().getLocale())}));
            } else {
                final Map<String, List<ParamFuncDto>> map = (Map<String, List<ParamFuncDto>>) BusinessHelper.call(
                        Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_PARAMS,
                        new Object[] {this.form.getParameters()});
                this.form.setParameters(map);
                addConfirm(this.propertiesHelper.getMessage(
                        "page.param.save.success",
                        new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                                UserHelper.getUserInSession().getPreferences().getLocale())}));
            }
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the standard parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramEntityAdd", method = RequestMethod.POST)
    public final String paramEntityAdd(@ModelAttribute("form") final ParamForm paramForm,
            final BindingResult result, final Model model) {
        if (StringUtils.isBlank(paramForm.getNewEntry())) {
            addError(this.propertiesHelper.getMessage("page.param.error.new.entry.empty"));
        } else {
            // Adding the new parameter entries for each language
            final LegalEntityDto newEntity = new LegalEntityDto();
            newEntity.setLabel(paramForm.getNewEntry());
            if (paramForm.getEntities() == null) {
                paramForm.setEntities(new ArrayList<LegalEntityDto>());
            }
            paramForm.getEntities().add(newEntity);
        }
        // Returning
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramEntitySave", method = RequestMethod.POST)
    public final String paramEntitySave(@ModelAttribute("form") final ParamForm paramForm,
            final BindingResult result, final Model model) {
        boolean error = false;
        try {
            // BUG IE WITH JS, Check that value are not empty.
            for (final LegalEntityDto legalEntity : paramForm.getEntities()) {
                if (!checkLegalEntityAddress(legalEntity)) {
                    addError(this.propertiesHelper.getMessage("page.param.save.empty.field"));
                    error = true;
                    break;
                }
            }
            if (!error) {
                BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_ENTITIES,
                        new Object[] {paramForm.getEntities()});
                addConfirm(this.propertiesHelper.getMessage(
                        "page.param.save.success",
                        new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                                UserHelper.getUserInSession().getPreferences().getLocale())}));
            }
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the entity parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        if (!error) {
            fetchParams();
        }
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramCountryAdd", method = RequestMethod.POST)
    public final String paramCountryAdd(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        if (StringUtils.isBlank(this.form.getNewEntry())) {
            addError(this.propertiesHelper.getMessage("page.param.error.new.entry.empty"));
        } else {
            // Adding the new parameter entries for each country
            final CountryDto newCountry = new CountryDto();
            try {
                newCountry.setLocale(LocaleUtil.stringToCountry(this.form.getNewEntry()));
            } catch (final DCOException e) {
                // This should never happen as the list of strings has been built from locales
                LOG.error("An error occurred while saving adding a country", e);
                addError(this.propertiesHelper.getMessage("page.param.error.loading"));
                return Constants.CONTROLLER_PARAM_RETURN_PAGE;
            }
            // The userInterface flag defaults to false
            if (this.form.getCountries() == null) {
                this.form.setCountries(new ArrayList<CountryDto>());
            }
            this.form.getCountries().add(newCountry);
            buildListLocalesCountry();
        }
        // Returning
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramCountrySave", method = RequestMethod.POST)
    public final String paramCountrySave(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_COUNTRIES,
                    new Object[] {this.form.getCountries()});
            this.form.setCountries((List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                    Constants.CONTROLLER_LIST, null));
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the country parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramLangAdd", method = RequestMethod.POST)
    public final String paramLangAdd(@ModelAttribute("form") final ParamForm postForm, final BindingResult result,
            final Model model) {
        if (StringUtils.isBlank(this.form.getNewEntry())) {
            addError(this.propertiesHelper.getMessage("page.param.error.new.entry.empty"));
        } else {
            // Adding the new parameter entries for each language
            final LanguageDto newLang = new LanguageDto();
            try {
                newLang.setLocale(LocaleUtil.stringToLanguage(this.form.getNewEntry()));
            } catch (final DCOException e) {
                // This should never happen as the list of strings has been built from locales
                LOG.error("An error occurred while saving adding a language", e);
                addError(this.propertiesHelper.getMessage("page.param.error.loading"));
                return Constants.CONTROLLER_PARAM_RETURN_PAGE;
            }
            // The userInterface flag defaults to false
            if (this.form.getLanguages() == null) {
                this.form.setLanguages(new ArrayList<LanguageDto>());
            }
            this.form.getLanguages().add(newLang);
            buildListLocalesLanguage();
        }
        // Returning
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramLangSave", method = RequestMethod.POST)
    public final String paramLangSave(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_LANGS,
                    new Object[] {this.form.getLanguages()});
            this.form.setLanguages((List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                    Constants.CONTROLLER_LIST, null));
            addConfirm(this.propertiesHelper.getMessage(
                    "page.param.save.success",
                    new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                            UserHelper.getUserInSession().getPreferences().getLocale())}));
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the language parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramCommMessSave", method = RequestMethod.POST)
    public final String paramCommMessSave(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_COMM_MESS,
                    new Object[] {this.form.getLanguagesCommMess()});
            addConfirm(this.propertiesHelper.getMessage(
                    "page.param.save.success",
                    new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                            UserHelper.getUserInSession().getPreferences().getLocale())}));
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the commercial message parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        fetchParams();
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramCountryLangSave", method = RequestMethod.POST)
    public final String paramCountryLangSave(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        buildCountryLangForSave();
        buildEnableComLangForSave();
        try {
            BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_COUNTRIES,
                    new Object[] {this.form.getCountries()});
            addConfirm(this.propertiesHelper.getMessage(
                    "page.param.save.success",
                    new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                            UserHelper.getUserInSession().getPreferences().getLocale())}));
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the country/language association parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        fetchParams();
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    @RequestMapping(value = "paramCountryEntitySave", method = RequestMethod.POST)
    public final String paramCountryEntitySave(@ModelAttribute("form") final ParamForm postForm,
            final BindingResult result, final Model model) {
        buildCountryEntityForSave();
        try {
            BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_SAVE_COUNTRIES,
                    new Object[] {this.form.getCountries()});
            addConfirm(this.propertiesHelper.getMessage(
                    "page.param.save.success",
                    new Object[] {getDefaultLanguage().getLocale().getDisplayLanguage(
                            UserHelper.getUserInSession().getPreferences().getLocale())}));
        } catch (final DCOException e) {
            LOG.error("An error occurred while saving the country/entity association parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
        }
        fetchParams();
        return Constants.CONTROLLER_PARAM_RETURN_PAGE;
    }

    private void fetchParams() {
        if (this.form.getSelectedType() != Constants.VAR_ZERO) {
            // Checking if the parameter is standard
            if (this.form.isStandardAllCountries() || this.form.isStandardBankCountries()) {
                if (this.form.getSelectedCountry() != null) {
                    try {
                        this.form.setParameters((Map<String, List<ParamFuncDto>>) BusinessHelper.call(
                                Constants.CONTROLLER_PARAM,
                                Constants.CONTROLLER_PARAM_LOAD_PARAMS,
                                new Object[] {Integer.valueOf(this.form.getSelectedType()),
                                        this.form.getSelectedCountry()}));
                    } catch (final DCOException e) {
                        LOG.error("An error occurred while loading standard parameter", e);
                        addError(this.propertiesHelper.getMessage("page.param.error.loading"));
                        return;
                    }
                }
            } else {
                try {
                    switch (this.form.getCurrentType().getId()) {
                    case Constants.PARAM_TYPE_CUSTOM_COUNTRY:
                        this.form.setCountries((List<CountryDto>) BusinessHelper.call(
                                Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null));
                        buildListLocalesCountry();
                        break;
                    case Constants.PARAM_TYPE_CUSTOM_ENTITY:
                        this.form.setEntities((List<LegalEntityDto>) BusinessHelper.call(
                                Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_LOAD_ENTITIES, null));
                        break;
                    case Constants.PARAM_TYPE_CUSTOM_LANG:
                        this.form.setLanguages((List<LanguageDto>) BusinessHelper.call(
                                Constants.CONTROLLER_LANGAGE, Constants.CONTROLLER_LIST, null));
                        buildListLocalesLanguage();
                        break;
                    case Constants.PARAM_TYPE_CUSTOM_COMMERCIAL_MESSAGE_LOGIN:
                    case Constants.PARAM_TYPE_CUSTOM_COMMERCIAL_MESSAGE_CLIENT:
                        this.form.setLanguagesCommMess((List<LanguageDto>) BusinessHelper.call(
                                Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_LOAD_COMM_MESS, null));
                        break;
                    case Constants.PARAM_TYPE_CUSTOM_COUNTRY_LANG:
                        this.form.setCountries((List<CountryDto>) BusinessHelper.call(
                                Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null));
                        this.form.setLanguages((List<LanguageDto>) BusinessHelper.call(
                                Constants.CONTROLLER_LANGAGE, Constants.CONTROLLER_LIST, null));
                        buildMapCountryLang();
                        buildMapEnableComLang();
                        break;
                    case Constants.PARAM_TYPE_CUSTOM_COUNTRY_ENTITY:
                        this.form.setCountries((List<CountryDto>) BusinessHelper.call(
                                Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null));
                        this.form.setEntities((List<LegalEntityDto>) BusinessHelper.call(
                                Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_LOAD_ENTITIES, null));
                        buildMapCountryEntity();
                        break;
                    default:
                        // This should never happen (as we use constants)
                        addError("Invalid parameter type");
                        return;
                    }
                } catch (final DCOException e) {
                    LOG.error(
                            "An error occurred while fetching custom parameter of type "
                                    + this.form.getSelectedType(), e);
                    addError(this.propertiesHelper.getMessage("page.param.error.loading"));
                    return;
                }
            }
        }
    }

    /**
     * Utility method to prepare the list of languages for display.
     */
    private void buildListLocalesLanguage() {
        final Locale[] allLocales = Locale.getAvailableLocales();
        final List<Locale> localesToUse = new ArrayList<Locale>();
        final List<String> alreadyAdded = new ArrayList<String>();
        for (final LanguageDto dto : this.form.getLanguages()) {
            alreadyAdded.add(dto.getLocale().getLanguage());
        }
        for (final Locale l : allLocales) {
            if (StringUtils.isBlank(l.getCountry()) && !alreadyAdded.contains(l.getLanguage())) {
                localesToUse.add(l);
                alreadyAdded.add(l.getLanguage());
            }
        }
        this.form.setAllLocales(localesToUse);
    }

    /**
     * Utility method to prepare the list of countries for display.
     */
    private void buildListLocalesCountry() {
        final Locale[] allLocales = Locale.getAvailableLocales();
        final List<Locale> localesToUse = new ArrayList<Locale>();
        final List<String> alreadyAdded = new ArrayList<String>();
        for (final CountryDto dto : this.form.getCountries()) {
            alreadyAdded.add(dto.getLocale().getCountry());
        }
        for (final Locale l : allLocales) {
            if (!alreadyAdded.contains(l.getCountry())) {
                localesToUse.add(new Locale("", l.getCountry()));
                alreadyAdded.add(l.getCountry());
            }
        }
        this.form.setAllLocales(localesToUse);
    }

    /**
     * Utility method to prepare the form object from database objects.
     */
    private void buildMapCountryLang() {
        final Map<Integer, Map<Integer, Boolean>> mapCountryLang = new HashMap<Integer, Map<Integer, Boolean>>();
        final ParamFormCountryLang countryLang = new ParamFormCountryLang();
        countryLang.setMap(mapCountryLang);
        this.form.setCountryLang(countryLang);
        if (this.form.getCountries() != null && this.form.getLanguages() != null) {
            for (final CountryDto c : this.form.getCountries()) {
                final Map<Integer, Boolean> langs = new HashMap<Integer, Boolean>();
                mapCountryLang.put(c.getId(), langs);
                for (final LanguageDto l : this.form.getLanguages()) {
                    boolean found = false;
                    if (c.getLanguages() != null) {
                        for (final LanguageDto lExisting : c.getLanguages()) {
                            if (lExisting.getId().equals(l.getId())) {
                                langs.put(l.getId(), Boolean.TRUE);
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        langs.put(l.getId(), Boolean.FALSE);
                    }
                }
            }
        }
    }

    /**
     * Utility method to prepare to objects to send to the database from the form object.
     */
    private void buildCountryLangForSave() {
        if (this.form.getCountryLang().getMap() != null) {
            for (final Map.Entry<Integer, Map<Integer, Boolean>> entryCountry : this.form.getCountryLang()
                    .getMap().entrySet()) {
                // Finding the country
                final Integer countryId = entryCountry.getKey();
                CountryDto country = null;
                for (final CountryDto c : this.form.getCountries()) {
                    if (c.getId().equals(countryId)) {
                        country = c;
                        break;
                    }
                }
                // We should always have a country here
                // this business case can't appear, so we don't test null on country.
                final List<LanguageDto> newLanguages = new ArrayList<LanguageDto>();
                if (country != null) {
                    country.setLanguages(newLanguages);
                }
                final Map<Integer, Boolean> langs = entryCountry.getValue();
                for (final Map.Entry<Integer, Boolean> lang : langs.entrySet()) {
                    // Only adding the languages selected
                    if (lang.getValue()) {
                        for (final LanguageDto l : this.form.getLanguages()) {
                            if (l.getId().equals(lang.getKey())) {
                                newLanguages.add(l);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Utility method to prepare the form object from database objects.
     */
    private void buildMapEnableComLang() {
        final Map<Integer, Boolean> mapCountryComLang = new HashMap<Integer, Boolean>();
        final ParamFormEnableLang enableComLang = new ParamFormEnableLang();
        enableComLang.setMap(mapCountryComLang);
        this.form.setEnableComLang(enableComLang);
        if (this.form.getCountries() != null) {
            for (final CountryDto c : this.form.getCountries()) {
                if (c.isCom_lang_enabled()) {
                    mapCountryComLang.put(c.getId(), Boolean.TRUE);
                } else {
                    mapCountryComLang.put(c.getId(), Boolean.FALSE);
                }
            }
        }
    }

    /**
     * Utility method to prepare to objects to send to the database from the form object.
     */
    private void buildEnableComLangForSave() {
        final Map<Integer, Boolean> map = this.form.getEnableComLang().getMap();
        if (map != null && this.form.getCountries() != null) {
            for (final CountryDto country : this.form.getCountries()) {
                if (map.get(country.getId()) != null) {
                    country.setCom_lang_enabled(map.get(country.getId()));
                } else {
                    country.setCom_lang_enabled(false);
                }
            }
        }
    }

    /**
     * Utility method to prepare the form object from database objects.
     */
    private void buildMapCountryEntity() {
        final Map<Integer, Integer> mapCountryEntity = new HashMap<Integer, Integer>();
        final ParamFormCountryEntity countryEntity = new ParamFormCountryEntity();
        countryEntity.setMap(mapCountryEntity);
        this.form.setCountryEntity(countryEntity);
        if (this.form.getCountries() != null) {
            for (final CountryDto c : this.form.getCountries()) {
                if (c.getLegalEntity() == null) {
                    mapCountryEntity.put(c.getId(), 0);
                } else {
                    mapCountryEntity.put(c.getId(), c.getLegalEntity().getId());
                }
            }
        }
    }

    /**
     * Utility method to prepare to objects to send to the database from the form object.
     */
    private void buildCountryEntityForSave() {
        if (this.form.getCountryEntity().getMap() != null && this.form.getCountries() != null) {
            for (final CountryDto country : this.form.getCountries()) {
                for (final LegalEntityDto legalEntity : this.form.getEntities()) {
                    if (legalEntity.getId() == this.form.getCountryEntity().getMap().get(country.getId())
                            .intValue()) {
                        country.setLegalEntity(legalEntity);
                        break;
                    }
                }
            }
        }
    }

    @RequestMapping(value = "deleteParam", method = RequestMethod.GET)
    public final String deleteParam(final Integer typeParam, final String country, final String entry,
            final Model model) {

        try {
            BusinessHelper.call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_DELETE_PARAM, new Object[] {
                    typeParam, country, entry});
        } catch (final DCOException e) {
            LOG.error("An error occurred while deleting parameter", e);
            addError(this.propertiesHelper.getMessage("page.param.error.deleting"));
        }

        return paramLoad(model);
    }

    private boolean checkLegalEntityAddress(final LegalEntityDto legalEntityDto) {
        Boolean result = false;
        if (legalEntityDto.getAddress() != null && legalEntityDto.getAddress().isValid()) {
            result = true;
        }
        return result;
    }
}
