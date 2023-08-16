package com.bnpp.dco.presentation.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.dto.AddressDto;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyDto;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyIdDto;
import com.bnpp.dco.common.dto.CitizenshipDto;
import com.bnpp.dco.common.dto.CitizenshipIdDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.dto.DocumentTypeDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.ThirdPartyDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.bean.UserSession;
import com.bnpp.dco.presentation.form.AccountStatementTypesBean;
import com.bnpp.dco.presentation.form.FormulaireDCOForm;
import com.bnpp.dco.presentation.form.ThirdPartyForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.DateUtils;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;
import com.bnpp.dco.presentation.utils.pdf.PDFBean;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanAccount;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanActingDetails;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanAddress;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanEntity;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanThirdParty;
import com.bnpp.dco.presentation.utils.pdf.PDFGenerator;
import com.lowagie.text.DocumentException;

@Controller
public class FormulaireController extends GenericController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(FormulaireController.class);

    private static final String PROP_NO_ENTITY = "page.preferences.user.no.entity";

    private static List<Locale> countryList;

    private static List<Locale> countryClientList;

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private ThirdPartyForm thirdPartyForm;

    @Autowired
    private FormulaireDCOForm dcoForm;
    
    private boolean err = false;

    /**
     * Country used for the form3 Page, when the user has to choose a country register for his entity.
     */
    static {
        countryList = new ArrayList<Locale>();

        final Locale[] allLocales = Locale.getAvailableLocales();
        final List<String> existingLocales = new ArrayList<String>();
        for (final Locale l : allLocales) {
            if (!existingLocales.contains(l.getCountry()) && !l.getCountry().equals(Constants.EMPTY_FIELD)) {
                existingLocales.add(l.getCountry());
                countryList.add(new Locale("", l.getCountry()));
            }
        }

        countryClientList = new ArrayList<Locale>();
        List<CountryDto> countryDtoClientList = null;
        try {
            countryDtoClientList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                    Constants.CONTROLLER_LIST, null);
        } catch (final DCOException e) {
            LOG.error("Error while initializing account countries for client account request (login page)!");
        }
        if (countryDtoClientList != null && !countryDtoClientList.isEmpty()) {
            for (final CountryDto c : countryDtoClientList) {
                countryClientList.add(new Locale("", c.getLocale().getCountry()));
            }
        }
    }

    /**
     * @return the log
     */
    public static Logger getLog() {
        return LOG;
    }

    public List<Locale> getCountryList() {
        return countryList;
    }

    /**
     * @return the account country list to be used as registration country for a new client account!
     */
    public List<Locale> getCountryClientList() {
        return countryClientList;
    }

    /**
     * @return the FormulaireDCOForm
     */
    @ModelAttribute("formulaireDCOForm")
    public FormulaireDCOForm getDcoForm() {
        return this.dcoForm;
    }

    /**
     * @return the ThirdPartyForm
     */
    @ModelAttribute("thirdPartyForm")
    public ThirdPartyForm getThirdPartyForm() {
        return this.thirdPartyForm;
    }

    /***************************************************************************************************************
     **************************************** _____GENERAL____ *****************************************************
     **************************************************************************************************************/
    /**
     * Controller for displaying the help page to the right anchor.
     * @param id the id of the anchor
     * @return the built link to redirect to
     */
    @RequestMapping(value = "/formHelper", method = RequestMethod.GET)
    public final String formHelper() {
        return WebConstants.FORM_HELP;
    }

    /***************************************************************************************************************
     **************************************** _____FORMULAIRE1____ *************************************************
     **************************************************************************************************************/
    /**
     * Method to initialize the form and return the first pag of the AOR workflow.
     * @return the form1 Page
     */
    @RequestMapping(value = "/initForm", method = RequestMethod.GET)
    public final String initForm() {
        String ret = WebConstants.FORMULAIRE1;
        dcoForm.setError(false);     
        try {
            resetForm();
            if (getDcoForm().getEntity() != null) {
                getDcoForm().init(getThirdPartyForm().getThirdPartyList());
                initEntityRepresentatives();
                initBreadcrumbs();
            } else {
                addError(this.propertiesHelper.getMessage(PROP_NO_ENTITY));
                dcoForm.setError(true);
                ret = WebConstants.HOME;
            }
        } catch (final DCOException e) {
            if (e.getCode() == Constants.EXCEPTION_CALLING_BUSINESS) {
                addError(this.propertiesHelper.getMessage("business.error"));
                dcoForm.setError(true);
            }
            addError(this.propertiesHelper.getMessage("page.unload.model"));
            dcoForm.setError(true);
            ret = WebConstants.HOME;
        }
        return ret;
    }

    /**
     * Access the form1 page
     * @return the form1 Page
     */
    @RequestMapping(value = "/" + WebConstants.FORMULAIRE1, method = RequestMethod.GET)
    public final String getFormulaire1() {
        return WebConstants.FORMULAIRE1;
    }

    /**
     * Go to the next page after saving the form1
     * @param dcoForm
     * @return the third party page.
     */
    @RequestMapping(value = "/saveForm1", method = RequestMethod.POST, params = "form1ToNext")
    public final String form1ToNext(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        String ret = genericSaveForm1(dcoForm, WebConstants.THIRD_PARTY);
        if (!getDcoForm().getBreadcrumbs()[1]) {
            ret = WebConstants.REDIRECT + WebConstants.FORMULAIRE1;
        }
        return ret;
    }

    /**
     * Saving the form1 and redirect (using breadcrumbs)
     * @param dcoForm
     * @param redirect page to redirect
     * @return the redirect page.
     */
    @RequestMapping(value = "/saveForm1AndRedirect/{redirect}", method = RequestMethod.POST)
    public final String saveForm1AndRedirect(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            @PathVariable final String redirect) {
        final String ret = genericSaveForm1(dcoForm, redirect);
        return ret;
    }

    /**
     * Go to the form1 page using back or navigation button
     * @return the form1 page
     */
    @RequestMapping(value = "/backToForm1", method = RequestMethod.POST)
    public final String backToForm1(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return WebConstants.FORMULAIRE1;
    }

    /**
     * Save the modification on the form1 page and return to the form1 page.
     * @param dcoForm
     * @return the form1 page.
     */
    @RequestMapping(value = "/saveForm1", method = RequestMethod.POST, params = "submitForm1")
    public final String saveForm1(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return genericSaveForm1(dcoForm, WebConstants.FORMULAIRE1);
    }

    /**
     * Generic method to validate the form1 and record the entity.
     * @param dcoForm
     * @param retForm page to access after saving.
     * @return the return page in function of the action (record: form1 Page, next: thirdParty Page).
     */
    private String genericSaveForm1(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            final String retForm) {
        String ret = retForm;
        boolean error = false;
        if (!(dcoForm.getMapLegalStatus() == null || dcoForm.getMapLegalStatus().isEmpty())) {
            error = checkForm1(dcoForm);
        }
        if (!error) {
            try {
                final EntityDto entity = updateEntity(dcoForm.getEntity());
                dcoForm.setEntity(entity);
                getThirdPartyForm().setEntity(entity);
                initEntityRepresentatives();
                initBreadcrumbs();
                addConfirm(this.propertiesHelper.getMessage("page.formulaire.success.saving"));
            } catch (final DCOException e) {
                if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                } else if (e.getCode() == Constants.EXCEPTION_CALLING_BUSINESS) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message."));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_PREFERENCES) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                }
                addWarning(this.propertiesHelper.getMessage("page.formulaire.success.saving.error"));
                error = true;
            }
        }
        if (!error) {
            ret = WebConstants.REDIRECT_ACTION + ret;
        } else {
            ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE1;
            dcoForm.setError(true);
            err = false;
        }
        return ret;
    }

    /**
     * Allow to check the required fields in form1.
     * @param dcoForm
     * @return true if the form1 is invalid, false otherwise.
     */
    private boolean checkForm1(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        final List<Boolean> errors = new ArrayList<Boolean>();
        if (StringUtils.isNotBlank(dcoForm.getEntity().getLegalStatusOther())) {
            errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity()
                    .getLegalStatusOther()));
        }
        errors.add(this
                .validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getCommercialRegister()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getTaxInformation()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldOne()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldTwo()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldThree()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldFour()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldFive()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldSix()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddress()
                .getFieldSeven()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldOne()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldTwo()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldThree()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldFour()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldFive()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldSix()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getEntity().getAddressMailing()
                .getFieldSeven()));
        if (errors.contains(Boolean.TRUE)) {
            addError(this.propertiesHelper.getMessage("page.form.invalidSpecialCharacters"));
            error = true;
        }

        if (!error && StringUtils.isBlank(dcoForm.getEntity().getTaxInformation())) {
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.taxInfo"));
            error = true;
        }
        if (!error && StringUtils.isBlank(dcoForm.getEntity().getCommercialRegister())) {
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.commercialRegister"));
            error = true;
        }
        if (!error && (dcoForm.getEntity().getLegalStatus() == null
                || dcoForm.getEntity().getLegalStatus().getId() == null)) {
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.legalStatus"));
            error = true;
        } else {
            // Set the legal Status to the entity
            if (dcoForm.getEntity().getCountry().getCountry() != null
                    && !dcoForm.getEntity().getCountry().getCountry().isEmpty()) {
                final List<ParamFuncDto> listParam = dcoForm.getMapLegalStatus().get(
                        dcoForm.getEntity().getCountry().getCountry());
                if (listParam != null) {
                    final Iterator<ParamFuncDto> itLS = listParam.iterator();
                    boolean find = false;
                    while (itLS.hasNext() && !find) {
                        final ParamFuncDto paramLS = itLS.next();
                        if (paramLS.getId().compareTo(dcoForm.getEntity().getLegalStatus().getId()) == Constants.VAR_ZERO) {
                            dcoForm.getEntity().setLegalStatus(paramLS);
                            find = true;
                        }
                    }
                }
            }
        }
        if (!error
                && dcoForm.getEntity().getLegalStatus().getEntry()
                        .equalsIgnoreCase(Constants.PARAM_TYPE_LEGAL_STATUS_OTHER)
                && StringUtils.isBlank(dcoForm.getEntity().getLegalStatusOther())) {
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.legalStatus"));
            error = true;
        }
        if (!error && StringUtils.isBlank(dcoForm.getEntity().getRegistrationCountry())) {
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.registrationCountry"));
            error = true;
        }
        if (!error && dcoForm.getEntity().getAddress() == null || !dcoForm.getEntity().getAddress().isValid()) {
            addError(this.propertiesHelper.getMessage("page.account.modify.bank.user.empty.field.error"));
            error = true;
        }

        if (!error
                && !dcoForm.getEntity().getSameAddress()
                && (dcoForm.getEntity().getAddressMailing() == null || !dcoForm.getEntity().getAddressMailing()
                        .isValid())) {
            addError(this.propertiesHelper.getMessage("page.account.modify.bank.user.empty.field.error"));
            error = true;
        }
        return error;
    }

    /***************************************************************************************************************
     ********************************************** ____THIRD PARTY____ ********************************************
     **************************************************************************************************************/

    /**
     * Validate if field respects given pattern
     * @param patternInputs the pattern to apply
     * @param field field to validate
     * @return true
     */
    private boolean validateFieldPattern(final String patternInputs, final String field) {
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(patternInputs)
                && !Pattern.compile(patternInputs).matcher(field).matches()) {
            return true;
        }
        return false;
    }

    /**
     * Access the third Party page
     * @param thirdPartyForm
     * @return the third party page
     */
    @RequestMapping(value = "/" + WebConstants.THIRD_PARTY, method = RequestMethod.GET)
    public final String getThirdParty(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm) {
        if(!err) {
        	dcoForm.setError(false);
	    	ThirdPartyDto thirdParty;
	        try {
	            final List<String> citizenshipList = new ArrayList<String>();
	            if (thirdPartyForm.getThirdPartySelect() != null
	                    && !thirdPartyForm.getThirdPartySelect().equals(Constants.VAR_NEG_ONE)) {
	                thirdParty = (ThirdPartyDto) BusinessHelper.call(Constants.CONTROLLER_THIRD_PARTY,
	                        Constants.CONTROLLER_THIRD_PARTY_BY_ID,
	                        new Object[] {thirdPartyForm.getThirdPartySelect()});
	                if (thirdParty != null && thirdParty.getCitizenships() != null
	                        && !thirdParty.getCitizenships().isEmpty()) {
	                    for (final CitizenshipDto citizenship : thirdParty.getCitizenships()) {
	                        citizenshipList.add(citizenship.getCitizenship().getCountry().getCountry());
	                    }
	                }
	                if (thirdParty == null) {
	                    thirdParty = new ThirdPartyDto();
	                }
	            } else {
	                thirdParty = new ThirdPartyDto();
	            }
	            thirdPartyForm.setCitizenshipList(citizenshipList);
	            // set legal entity name
	            if (thirdParty.getLegalEntityName() != null) {
	                thirdPartyForm.setLegalEntityName(thirdParty.getLegalEntityName());
	            }
	            if (thirdParty.getBirthDate() != null) {
	                thirdPartyForm.setBirthDate(new SimpleDateFormat(UserHelper.getUserInSession().getPreferences()
	                        .getDateFormat().getLabel()).format(thirdParty.getBirthDate()));
	                if(UserHelper.getUserInSession().getPreferences()
	                        .getDateFormat().getLabelDisplay().equals("dd/mm/yyyy")) {
		                thirdPartyForm.setBirthDay(thirdPartyForm.getBirthDate().substring(0, thirdPartyForm.getBirthDate().indexOf("/")));
		                thirdPartyForm.setBirthMonth(thirdPartyForm.getBirthDate().substring(thirdPartyForm.getBirthDate().indexOf("/")+1, thirdPartyForm.getBirthDate().lastIndexOf("/")));
		                thirdPartyForm.setBirthYear(thirdPartyForm.getBirthDate().substring(thirdPartyForm.getBirthDate().lastIndexOf("/")+1));
	                }
	                else if(UserHelper.getUserInSession().getPreferences()
	                        .getDateFormat().getLabelDisplay().equals("mm/dd/yyyy")) {
		                thirdPartyForm.setBirthDay(thirdPartyForm.getBirthDate().substring(thirdPartyForm.getBirthDate().indexOf("/")+1, thirdPartyForm.getBirthDate().lastIndexOf("/")));
		                thirdPartyForm.setBirthMonth(thirdPartyForm.getBirthDate().substring(0, thirdPartyForm.getBirthDate().indexOf("/")));
		                thirdPartyForm.setBirthYear(thirdPartyForm.getBirthDate().substring(thirdPartyForm.getBirthDate().lastIndexOf("/")+1));
	                }
	                else if(UserHelper.getUserInSession().getPreferences()
	                        .getDateFormat().getLabelDisplay().equals("yyyy/mm/dd")) {
		                thirdPartyForm.setBirthDay(thirdPartyForm.getBirthDate().substring(thirdPartyForm.getBirthDate().lastIndexOf("/")+1));
		                thirdPartyForm.setBirthMonth(thirdPartyForm.getBirthDate().substring(thirdPartyForm.getBirthDate().indexOf("/")+1, thirdPartyForm.getBirthDate().lastIndexOf("/")));
		                thirdPartyForm.setBirthYear(thirdPartyForm.getBirthDate().substring(0, thirdPartyForm.getBirthDate().indexOf("/")));
	                }
	            } else {
	                thirdPartyForm.setBirthDate(null);
	                thirdPartyForm.setBirthDay(null);
	                thirdPartyForm.setBirthMonth(null);
	                thirdPartyForm.setBirthYear(null);
	            }
	            if (StringUtils.isBlank(thirdParty.getFax())) {
	                thirdParty.setFax(Constants.PLUS);
	            }
	            thirdPartyForm.setThirdPartyDto(thirdParty);
	
	        } catch (final DCOException ex) {
	            thirdParty = new ThirdPartyDto();
	            thirdParty.setFax(Constants.PLUS);
	            this.thirdPartyForm.setThirdPartyDto(thirdParty);
	        }
        }
        else{
        	dcoForm.setError(true);
        	err=false;
        }
        return WebConstants.THIRD_PARTY;
    }

    /**
     * Go to the next page after saving the thirdparty page
     * @param thirdPartyForm
     * @return the form3 page.
     */
    @RequestMapping(value = "/thirdPartyToNext", method = RequestMethod.POST)
    public final String thirdPartyToNext(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm) {
        String ret = WebConstants.FORMULAIRE3;
        if (preCheckThirdPartyForm(thirdPartyForm)) {
            ret = genericSaveThirdParty(thirdPartyForm, ret);
        }
        if (ret.contains(WebConstants.FORMULAIRE3)) {
            if (!getDcoForm().getBreadcrumbs()[1]) {
                ret = WebConstants.REDIRECT + WebConstants.FORMULAIRE1;
            }
            if (!getDcoForm().getBreadcrumbs()[2]) {
                ret = WebConstants.REDIRECT + WebConstants.THIRD_PARTY;
                addError(this.propertiesHelper.getMessage("page.thirdParty.warning.1"));
            }
        }
        dcoForm.setError(false);
        return ret;
    }

    /**
     * Go to the third party page using back or navigation button
     * @param thirdPartyForm
     * @return go to getThirdPatty()
     */
    @RequestMapping(value = "/backToThirdParty", method = RequestMethod.POST)
    public final String backToThirdParty(
            @ModelAttribute("formulaireDCOForm") final FormulaireDCOForm formulaireDCOForm) {
        return WebConstants.REDIRECT + WebConstants.THIRD_PARTY;
    }

    /**
     * Delete a third party given his id
     * @param thirdPartyForm
     * @return Return to the third Party page
     */
    @RequestMapping(value = "/deleteThirdParty", method = RequestMethod.POST)
    public final String deleteThirdParty(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm) {
        String ret = WebConstants.THIRD_PARTY;
        final Integer id = getThirdPartyForm().getThirdPartySelect();
        boolean error = false;
        if (id > Constants.VAR_ZERO) {
            try {
                final UserDto user = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
                        Constants.CONTROLLER_USER_GET_BY_LOGIN, new Object[] {UserHelper.getUserInSession()
                                .getLogin()});
                if (!getThirdPartyForm().getThirdPartyDto().getUser().getId().equals(user.getId())) {
                    // because different from the user having created this thirdParty.
                    error = true;
                }
                if (!error && thirdPartyForm.getEntity().getAccountsForm() != null) {
                    for (final AccountFormDto accountForm : thirdPartyForm.getEntity().getAccountsForm()) {
                        if (accountForm.getAccountDtoList() == null) {
                            continue;
                        }
                        for (final AccountDto account : accountForm.getAccountDtoList()) {
                            if (account.getAccountThirdPartyList() == null) {
                                continue;
                            }
                            for (final AccountThirdPartyDto accountThirdParty : account.getAccountThirdPartyList()) {
                                if (accountThirdParty.getAuthorizedList() == null) {
                                    continue;
                                }
                                if (accountThirdParty.getThirdParty().getId().equals(id)) {
                                    addError(this.propertiesHelper
                                            .getMessage("page.thirdParty.deleted.user.not.allowed"));
                                    return ret;
                                }
                                for (final AuthorizationThirdPartyDto authorizationThirdParty : accountThirdParty
                                        .getAuthorizedList()) {
                                    if (authorizationThirdParty.getId().getThirdPartyId().equals(id)) {
                                        addError(this.propertiesHelper
                                                .getMessage("page.thirdParty.deleted.user.not.allowed"));
                                        return ret;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!error) {
                    doDeleteThirdParty(id);
                    removeAsContact(thirdPartyForm, id);
                    ret = WebConstants.REDIRECT + ret;
                    addConfirm(this.propertiesHelper.getMessage("page.thirdParty.deleted.user.success"));
                    resetForm();
                    getDcoForm().setThirdPartyList(thirdPartyForm.getThirdPartyList());
                    initBreadcrumbs();
                }
            } catch (final DCOException e) {
                LOG.error("Error while deleting the third party", e);
                if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                } else if (e.getCode() == Constants.EXCEPTION_CALLING_BUSINESS) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message."));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_PREFERENCES) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                } else {
                    addError(this.propertiesHelper.getMessage("page.thirdParty.deleted.user.technical.error"));
                }
            }
            if (error) {
                LOG.error("Error while deleting the thirdParty with db ID: (" + id
                        + "); IT MUST BE ACHIEVED BY HIS OWN CREATOR!");
                addError(this.propertiesHelper.getMessage("page.thirdParty.deleted.user.technical.error.grant"));
            }
        } else {
            addError(this.propertiesHelper.getMessage("page.thirdParty.deleted.user.technical.error.hacking"));
        }
        return ret;
    }

    /**
     * Save the modification or create a new third party and return to the third party page.
     * @param thirdPartyForm
     * @return Return to the third Party page
     */
    @RequestMapping(value = "/saveThirdParty", method = RequestMethod.POST)
    public final String saveThirdParty(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm) {
        return genericSaveThirdParty(thirdPartyForm, WebConstants.THIRD_PARTY);
    }

    /**
     * Save the modification or create a new third party and redirect (using breadcrumbs).
     * @param thirdPartyForm
     * @param redirect page to redirect to
     * @return Return to the redirect page
     */
    @RequestMapping(value = "/saveThirdPartyAndRedirect/{redirect}", method = RequestMethod.POST)
    public final String saveThirdPartyAndRedirect(
            @ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm,
            @PathVariable final String redirect) {
        String ret = redirect;
        if (preCheckThirdPartyForm(thirdPartyForm)) {
            ret = genericSaveThirdParty(thirdPartyForm, ret);
        } else {
            ret = WebConstants.REDIRECT_ACTION + ret;
        }
        if (ret.contains(WebConstants.FORMULAIRE3)) {
            if (!getDcoForm().getBreadcrumbs()[1]) {
                ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE1;
            }
            if (!getDcoForm().getBreadcrumbs()[2]) {
                ret = WebConstants.REDIRECT_ACTION + WebConstants.THIRD_PARTY;
                addError(this.propertiesHelper.getMessage("page.thirdParty.warning.1"));
            }
        }
        return ret;
    }

    /**
     * Initialize the third party form
     * @param entity
     * @return none
     */
    private void initThirdPartyForm(final EntityDto entity) {
        try {
            getThirdPartyForm().setEntity(entity);
            getThirdPartyForm().init();
            // init citizenships of third party
            if (getThirdPartyForm().getThirdPartyDto() != null) {
                final ThirdPartyDto thirdParty = getThirdPartyForm().getThirdPartyDto();
                final List<String> citizenshipsList = new ArrayList<String>();
                if (thirdParty.getCitizenships() != null && !thirdParty.getCitizenships().isEmpty()) {
                    String code = null;
                    for (final CitizenshipDto citiz : thirdParty.getCitizenships()) {
                        code = citiz.getCitizenship().getCountry().getCountry();
                        citizenshipsList.add(code);
                    }
                }
                getThirdPartyForm().setCitizenshipList(citizenshipsList);
            }

        } catch (final DCOException e) {
            if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES) {
                addError(this.propertiesHelper.getMessage("page.preferences.user.no.entity"));
            } else if (e.getCode() == Constants.EXCEPTION_CALLING_BUSINESS) {
                addError(this.propertiesHelper.getMessage("business.error"));
            }
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }
    }

    /**
     * Generic method to validate the third party form, record or update the third party.
     * @param thirdPartyForm
     * @param retForm
     * @return the return page in function of the action (save: third Party Page, next: form3 Page).
     */
    private String genericSaveThirdParty(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm,
            final String retForm) {
        String ret = retForm;
        boolean error;
        error = checkThirdPartyForm(thirdPartyForm);
        err=false;
        if (!error) {
            try {
                if (thirdPartyForm.getThirdPartyDto() != null) {
                    thirdPartyForm.getThirdPartyDto().setUser(
                            (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
                                    Constants.CONTROLLER_USER_GET_BY_LOGIN, new Object[] {UserHelper
                                            .getUserInSession().getLogin()}));

                    // Check date Birthday
                    final String dateDayString = thirdPartyForm.getBirthDay();
                    final String dateMonthString = thirdPartyForm.getBirthMonth();
                    final String dateYearString = thirdPartyForm.getBirthYear();
                    String dateBirthDayString=null;
                    
                    if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("dd/mm/yyyy")){
                    	dateBirthDayString = dateDayString + "/" + dateMonthString + "/" + dateYearString;
                    }
                    else if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("mm/dd/yyyy")){
                    	dateBirthDayString = dateMonthString + "/" + dateDayString + "/" + dateYearString;
                    }
                    else if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("yyyy/mm/dd")){
                    	dateBirthDayString = dateYearString + "/" + dateMonthString + "/" + dateDayString;
                    }
                    
                    if (StringUtils.isNotBlank(dateDayString) || StringUtils.isNotBlank(dateMonthString) || StringUtils.isNotBlank(dateYearString)) {
                        final Date dateParsed = DateUtils.validate(dateBirthDayString);
                        if (dateParsed == null) {
                            error = true;
                            addError(this.propertiesHelper
                                    .getMessage("page.formulaire.web.message.badDateFormat.birthDate"));
                        } else {
                            thirdPartyForm.getThirdPartyDto().setBirthDate(dateParsed);
                        }
                    }

                    if (!error) {
                        // Check legal entity name
                        if (StringUtils.isBlank(thirdPartyForm.getThirdPartyDto().getLegalEntityName())) {
                            thirdPartyForm.getThirdPartyDto().setLegalEntityName(null);
                        }
                        // check the all correspondant types (role) and set them to default value if null: VAR_ZERO
                        if (thirdPartyForm.getThirdPartyDto().getCorrespondantType() == null) {
                            thirdPartyForm.getThirdPartyDto().setCorrespondantType(Constants.VAR_ZERO);
                        }
                        if (thirdPartyForm.getThirdPartyDto().getCorrespondantTypeTwo() == null) {
                            thirdPartyForm.getThirdPartyDto().setCorrespondantTypeTwo(Constants.VAR_ZERO);
                        }
                        if (thirdPartyForm.getThirdPartyDto().getCorrespondantTypeThree() == null) {
                            thirdPartyForm.getThirdPartyDto().setCorrespondantTypeThree(Constants.VAR_ZERO);
                        }

                        // save citizenships
                        ThirdPartyDto third = null;
                        if (thirdPartyForm.getThirdPartyDto().getId() == null) {
                            // save the new thirdParty
                            doSaveThirdParty(thirdPartyForm.getThirdPartyDto());
                            final List<ThirdPartyDto> thirdPartyList = (List<ThirdPartyDto>) BusinessHelper.call(
                                    Constants.CONTROLLER_THIRD_PARTY,
                                    Constants.CONTROLLER_THIRD_PARTY_BY_USER_LOGIN, new Object[] {UserHelper
                                            .getUserInSession().getLogin()});
                            // get the last entry in the list (new one)
                            if (thirdPartyList != null && !thirdPartyList.isEmpty()) {
                                third = thirdPartyList.get(thirdPartyList.size() - 1);
                                thirdPartyForm.getThirdPartyDto().setId(third.getId());
                            } else {
                                error = true;
                                addError(this.propertiesHelper.getMessage("page.formulaire.error.thirdpartylist"));
                            }
                        }
                    }
                    // save, update and delete the citizenships from third party list
                    if (!error && thirdPartyForm.getThirdPartyDto().getId() != null
                            && thirdPartyForm.getCitizenshipList() != null
                            && thirdPartyForm.getCitizenshipList().size() > Constants.VAR_ZERO) {
                        // delete all unselected citizenships from the old list only: useful on db storage!
                        if (thirdPartyForm.getThirdPartyDto().getCitizenships() != null
                                && !thirdPartyForm.getThirdPartyDto().getCitizenships().isEmpty()
                                && thirdPartyForm.getThirdPartyDto().getCitizenships().size() > Constants.VAR_ZERO) {
                            for (final CitizenshipDto citizen : thirdPartyForm.getThirdPartyDto()
                                    .getCitizenships()) {
                                Boolean citizenshipAlreadySaved = false;
                                for (final String country : thirdPartyForm.getCitizenshipList()) {
                                    if (citizen.getCitizenship().getCountry().getCountry().equals(country)) {
                                        citizenshipAlreadySaved = true;
                                        break;
                                    }
                                }
                                if (!citizenshipAlreadySaved) {
                                    // delete unselected citizenship only
                                    doDeleteCitizenship(citizen.getCitizenship());
                                }
                            }
                        }
//                        // render the citizenships selected into thirdPartyForm.
//                        final List<CitizenshipDto> citizenship = new ArrayList<CitizenshipDto>();
//                        for (final String country : thirdPartyForm.getCitizenshipList()) {
//                            final CitizenshipIdDto citizenId = new CitizenshipIdDto();
//                            citizenId.setThirdPartyId(thirdPartyForm.getThirdPartyDto().getId());
//                            citizenId.setCountry(LocaleUtil.stringToCountry(country));
//                            // useful to complete db: pk group.
//                            citizenId.setValue(Constants.CITIZENSHIP_ID_VALUE_OK);
//                            final CitizenshipDto citizen = new CitizenshipDto();
//                            citizen.setCitizenship(citizenId);
//                            citizen.setThirdParty(thirdPartyForm.getThirdPartyDto());
//                            citizenship.add(citizen);
//                        }
//                        thirdPartyForm.getThirdPartyDto().setCitizenships(citizenship);

                        final ThirdPartyDto td = doSaveThirdParty(thirdPartyForm.getThirdPartyDto());
                        setAsContact(thirdPartyForm, td);
                        if (td.getCitizenships() != null && !td.getCitizenships().isEmpty()) {
                            final List<String> citizenshipsList = new ArrayList<String>();
                            String code = null;
                            for (final CitizenshipDto citiz : td.getCitizenships()) {
                                code = citiz.getCitizenship().getCountry().getCountry();
                                citizenshipsList.add(code);
                            }
                            thirdPartyForm.setCitizenshipList(citizenshipsList);
                        }
                        // model views
                        thirdPartyForm.setThirdPartyDto(td);
                        ret = WebConstants.REDIRECT_ACTION + ret;
                        addConfirm(this.propertiesHelper.getMessage("page.thirdParty.created.user.success"));
                    } else if (!error) {
                        if (thirdPartyForm.getThirdPartyDto().getId() != null) {
                            final ThirdPartyDto td = doSaveThirdParty(thirdPartyForm.getThirdPartyDto());
                            // model view
                            thirdPartyForm.setThirdPartyDto(td);
                            // update third, no citiz.
                            setAsContact(thirdPartyForm, td);
                            ret = WebConstants.REDIRECT_ACTION + ret;
                            addConfirm(this.propertiesHelper.getMessage("page.thirdParty.created.user.success"));
                        }
                    }
                    if (!error) {
                        final Integer tmp = thirdPartyForm.getThirdPartySelect();
                        resetForm();
                        thirdPartyForm.setThirdPartySelect(tmp);
                        thirdPartyForm.setThirdPartyList((List<ThirdPartyDto>) BusinessHelper.call(
                                Constants.CONTROLLER_THIRD_PARTY, Constants.CONTROLLER_THIRD_PARTY_BY_USER_LOGIN,
                                new Object[] {UserHelper.getUserInSession().getLogin()}));
                        getDcoForm().setThirdPartyList(thirdPartyForm.getThirdPartyList());
                        initBreadcrumbs();
                    }
                } else {
                    ret = WebConstants.REDIRECT_ACTION + WebConstants.THIRD_PARTY;
                }
            } catch (final DCOException e) {
                ret = WebConstants.REDIRECT_ACTION + WebConstants.THIRD_PARTY;
                LOG.error("Error while saving the third party", e);
                if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                } else if (e.getCode() == Constants.EXCEPTION_CALLING_BUSINESS) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message."));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_PREFERENCES) {
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                }
            }
        }
        if (error) {
            ret = WebConstants.REDIRECT_ACTION + WebConstants.THIRD_PARTY;
            err=true;
        }
        return ret;
    }

    /**
     * Make the business call for the save/update of a third party
     * @param thirdPartyDto is the third party to be saved/updated
     * @return the saved third Party
     * @throws DCOException
     */
    private ThirdPartyDto doSaveThirdParty(final ThirdPartyDto thirdPartyDto) throws DCOException {
        return (ThirdPartyDto) BusinessHelper.call(Constants.CONTROLLER_THIRD_PARTY,
                Constants.CONTROLLER_THIRD_PARTY_SAVE, new Object[] {thirdPartyDto});
    }

    /**
     * Check that the thirdPartyForm is for a save or a next
     * @param thirdPartyForm
     * @return Return true if we need to do a save, false if we go directly for a next
     */
    private boolean preCheckThirdPartyForm(final ThirdPartyForm thirdPartyForm) {
        boolean rslt = true;
        final ThirdPartyDto thirdparty = thirdPartyForm.getThirdPartyDto();
        if (thirdparty == null) {
            rslt = false;
        } else if (StringUtils.isBlank(thirdparty.getName()) && StringUtils.isBlank(thirdparty.getFirstName())
                && thirdparty.getCorrespondantType() == null && thirdparty.getCorrespondantTypeTwo() == null
                && thirdparty.getCorrespondantTypeThree() == null) {
            rslt = false;
        }
        return rslt;
    }

    /**
     * Allow to check the required fields in thirdPartyForm.
     * @param thirdPartyForm
     * @return true if the thirdPartyForm is invalid, false otherwise.
     */
    private boolean checkThirdPartyForm(final ThirdPartyForm thirdPartyForm) {
        boolean error = false;
        final List<Boolean> errors = new ArrayList<Boolean>();
        errors.add(this
                .validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto().getName()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getFirstName()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getPositionName()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getBirthDate()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getBirthPlace()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldOne()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldTwo()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldThree()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldFour()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldFive()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldSix()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto()
                .getHomeAddress().getFieldSeven()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto().getTel()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto().getFax()));
        errors.add(this
                .validateFieldPattern(Constants.PATTERN_INPUTS, thirdPartyForm.getThirdPartyDto().getMail()));
        if (errors.contains(Boolean.TRUE)) {
            addError(this.propertiesHelper.getMessage("page.form.invalidSpecialCharacters"));
            error = true;
        }

        final ThirdPartyDto thirdparty = thirdPartyForm.getThirdPartyDto();

        if (thirdparty != null) {
            if (StringUtils.isNotBlank(thirdparty.getTel()) && thirdparty.getTel().equals(Constants.PLUS)) {
                thirdPartyForm.getThirdPartyDto().setTel("");
            }
            if (StringUtils.isNotBlank(thirdparty.getFax()) && thirdparty.getFax().equals(Constants.PLUS)) {
                thirdPartyForm.getThirdPartyDto().setFax("");
            }

            if (StringUtils.isBlank(thirdparty.getName()) || StringUtils.isBlank(thirdparty.getFirstName())) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
            }

            if (!error
                    && (thirdparty.getCorrespondantType() == null || thirdparty.getCorrespondantType().compareTo(
                            Constants.THIRD_CONTACT) != Constants.VAR_ZERO
                            && thirdparty.getCorrespondantType().compareTo(Constants.VAR_ZERO) != Constants.VAR_ZERO)
                    && (thirdparty.getCorrespondantTypeTwo() == null || thirdparty.getCorrespondantTypeTwo()
                            .compareTo(Constants.THIRD_SIGNATORY) != Constants.VAR_ZERO
                            && thirdparty.getCorrespondantTypeTwo().compareTo(Constants.VAR_ZERO) != Constants.VAR_ZERO)
                    && (thirdparty.getCorrespondantTypeThree() == null || thirdparty.getCorrespondantTypeThree()
                            .compareTo(Constants.THIRD_LEGAL_REPRESENTATIVE) != Constants.VAR_ZERO
                            && thirdparty.getCorrespondantTypeThree().compareTo(Constants.VAR_ZERO) != Constants.VAR_ZERO)) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
            }

            if (!error && thirdparty.getCorrespondantType() != null
                    && thirdparty.getCorrespondantType().equals(Constants.THIRD_CONTACT)) {
                if (thirdPartyForm.getEntity().getThirdParty() != null
                        && thirdPartyForm.getEntity().getThirdParty2() != null
                        && !thirdPartyForm.getEntity().getThirdParty().getId().equals(thirdparty.getId())
                        && !thirdPartyForm.getEntity().getThirdParty2().getId().equals(thirdparty.getId())) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.thirdParty.warning.2"));
                } else if (StringUtils.isBlank(thirdparty.getTel()) || StringUtils.isBlank(thirdparty.getMail())) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
                }
            }

            // Check validation of form fields according to the correspondant type.
            if (!error && thirdparty.getCorrespondantTypeTwo() != null
                    && thirdparty.getCorrespondantTypeTwo().equals(Constants.THIRD_SIGNATORY)) {
            	String DateTmp = null;
            	if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("dd/mm/yyyy")){
            		DateTmp = thirdPartyForm.getBirthDay()+"/"+thirdPartyForm.getBirthMonth()+"/"+thirdPartyForm.getBirthYear();
            	}
            	else if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("mm/dd/yyyy")){
            		DateTmp = thirdPartyForm.getBirthMonth()+"/"+thirdPartyForm.getBirthDay()+"/"+thirdPartyForm.getBirthYear();
            	}
            	else if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("yyyy/mm/dd")){
            		DateTmp = thirdPartyForm.getBirthYear()+"/"+thirdPartyForm.getBirthMonth()+"/"+thirdPartyForm.getBirthDay();
            	}
                if (StringUtils.isBlank(thirdparty.getPositionName())
                		|| StringUtils.isBlank(thirdPartyForm.getBirthDay())
                		|| StringUtils.isBlank(thirdPartyForm.getBirthMonth())
                		|| StringUtils.isBlank(thirdPartyForm.getBirthYear())
                        || StringUtils.isBlank(thirdparty.getBirthPlace())
                        || StringUtils.isBlank(thirdparty.getHomeAddress().getFieldOne())
                        || StringUtils.isBlank(thirdparty.getHomeAddress().getFieldTwo())
                        || StringUtils.isBlank(thirdparty.getHomeAddress().getFieldThree())
                        || StringUtils.isBlank(thirdparty.getHomeAddress().getFieldFour())) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
                } else if (DateUtils.validate(DateTmp) == null) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.thirdParty.created.user.date.error"));
                } else if (thirdPartyForm.getCitizenshipList() == null
                        || thirdPartyForm.getCitizenshipList().size() == Constants.VAR_ZERO) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.formulaire.empty.citizenships"));
                }
            }

            // Check valid pattern fields
            if (!error) {
                if (StringUtils.isNotBlank(thirdparty.getMail())
                        && !Pattern.compile(Constants.PATTERN_EMAIL).matcher(thirdparty.getMail()).matches()) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.pattern.email.error"));
                }
                if (StringUtils.isNotBlank(thirdparty.getTel())){
                	if (thirdparty.getTel().charAt(0)<'0' || thirdparty.getTel().charAt(0)>'9') {
                    	if(thirdparty.getTel().charAt(0) != '+') {
                    		error = true;
                    		addError(this.propertiesHelper.getMessage("page.pattern.tel.error2"));
                    	}
                    }
                	if(!error && !Pattern.compile(Constants.PATTERN_TEL).matcher(thirdparty.getTel()).matches()) {
                		for(int i=1; i<thirdparty.getTel().length(); i++){
                    		if (thirdparty.getTel().charAt(i)<'0' || thirdparty.getTel().charAt(i)>'9') {
                            	error = true;
                            	addError(this.propertiesHelper.getMessage("page.pattern.tel.error2"));
                            	break;
                            }
                    	}
                	}
                	if(!error && !Pattern.compile(Constants.PATTERN_TEL).matcher(thirdparty.getTel()).matches()) {
                        error = true;
                        addError(this.propertiesHelper.getMessage("page.pattern.tel.error"));
                	}
                }
                if (StringUtils.isNotBlank(thirdparty.getFax())
                        && !Pattern.compile(Constants.PATTERN_TEL).matcher(thirdparty.getFax()).matches()) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.pattern.fax.error"));
                }
            }
        } else {
            error = true;
            addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
        }
        return error;
    }

    /**
     * Make the business call for the delete of a third party
     * @param id is the id of the third party
     * @return none
     * @throws DCOException
     */
    private void doDeleteThirdParty(final Integer id) throws DCOException {
        BusinessHelper.call(Constants.CONTROLLER_THIRD_PARTY, Constants.CONTROLLER_THIRD_PARTY_DELETE,
                new Object[] {id});
    }

    /**
     * Make the business call for the delete of a citizenship for a ThirdParty
     * @param id is the CitizenshipIdDto
     * @return none
     * @throws DCOException
     */
    private void doDeleteCitizenship(final CitizenshipIdDto id) throws DCOException {
        BusinessHelper.call(Constants.CONTROLLER_CITIZENSHIP, Constants.CONTROLLER_CITIZENSHIP_DELETE,
                new Object[] {id});
    }

    /**
     * Set on the entity object the thirdParty as one of the two contact
     * @param thirdPartyForm
     * @param thirdParty to be set as a contact
     * @throws DCOException
     */
    private void setAsContact(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm,
            final ThirdPartyDto thirdParty) throws DCOException {
        boolean flag = false;
        if (thirdParty.getCorrespondantType().equals(Constants.THIRD_CONTACT)) {
            if (thirdPartyForm.getEntity().getThirdParty() == null
                    || thirdPartyForm.getEntity().getThirdParty().getId().equals(thirdParty.getId())) {
                thirdPartyForm.getEntity().setThirdParty(thirdParty);
                flag = true;
            } else if (thirdPartyForm.getEntity().getThirdParty2() == null
                    || thirdPartyForm.getEntity().getThirdParty2().getId().equals(thirdParty.getId())) {
                thirdPartyForm.getEntity().setThirdParty2(thirdParty);
                flag = true;
            }
        } else {
            if (thirdPartyForm.getEntity().getThirdParty() != null
                    && thirdPartyForm.getEntity().getThirdParty().getId().equals(thirdParty.getId())) {
                thirdPartyForm.getEntity().setThirdParty(null);
                flag = true;
            } else if (thirdPartyForm.getEntity().getThirdParty2() != null
                    && thirdPartyForm.getEntity().getThirdParty2().getId().equals(thirdParty.getId())) {
                thirdPartyForm.getEntity().setThirdParty2(null);
                flag = true;
            }
        }
        if (flag) {
            thirdPartyForm.setEntity(updateEntity(thirdPartyForm.getEntity()));
            getDcoForm().setEntity(thirdPartyForm.getEntity());
        }
    }

    /**
     * Check that the thirdParty is a contact, if true then remove it.
     * @param thirdPartyForm
     * @param thirdPartyId the id of the thirdParty
     * @throws DCOException
     */
    private void removeAsContact(@ModelAttribute("thirdPartyForm") final ThirdPartyForm thirdPartyForm,
            final Integer thirdPartyId) throws DCOException {
        boolean flag = false;
        if (thirdPartyForm.getEntity().getThirdParty() != null
                && thirdPartyId.equals(thirdPartyForm.getEntity().getThirdParty().getId())) {
            thirdPartyForm.getEntity().setThirdParty(null);
            flag = true;
        } else if (thirdPartyForm.getEntity().getThirdParty2() != null
                && thirdPartyId.equals(thirdPartyForm.getEntity().getThirdParty2().getId())) {
            thirdPartyForm.getEntity().setThirdParty2(null);
            flag = true;
        }
        if (flag) {
            thirdPartyForm.setEntity(updateEntity(thirdPartyForm.getEntity()));
            getDcoForm().setEntity(thirdPartyForm.getEntity());
        }
    }

    /***************************************************************************************************************
     ********************************************** ____FORMULAIRE3____ ********************************************
     **************************************************************************************************************/

    /**
     * Access the form3 page
     * @return the form3 Page.
     */
    @RequestMapping(value = "/" + WebConstants.FORMULAIRE3, method = RequestMethod.GET)
    public final String getFormulaire3() {
    	if(!err){
    		dcoForm.setError(false);
    	}else {
    		dcoForm.setError(true);
    		err = false;
    	}
        return WebConstants.FORMULAIRE3;
    }

    /**
     * Allow to save the new account (if it has been created), and all the modifications on the others accounts
     * @param dcoForm
     * @return the form4 Page.
     */
    @RequestMapping(value = "/saveForm3", method = RequestMethod.POST, params = "form3ToNext")
    public final String form3ToNext(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
    	dcoForm.setError(false);
    	return genericSaveForm3(dcoForm, WebConstants.FORMULAIRE4);
    }

    /**
     * Allow to save the new account (if it has been created), and all the modifications on the others accounts and
     * redirect.
     * @param dcoForm
     * @return the redirect Page.
     */
    @RequestMapping(value = "/saveForm3AndRedirect/{redirect}", method = RequestMethod.POST)
    public final String saveForm3AndRedirect(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            @PathVariable final String redirect) {
        String returnString;
        if (dcoForm.getMapAccount() == null || dcoForm.getMapAccount().isEmpty() || dcoForm.getAccounts() == null
                || dcoForm.getAccounts().isEmpty()) {
            returnString = WebConstants.REDIRECT_ACTION + redirect;
        } else {
            returnString = genericSaveForm3(dcoForm, redirect);
        }
        return returnString;
    }

    /**
     * Go to the form3 page using back or navigation button
     * @param dcoForm
     * @return form3 page
     */
    @RequestMapping(value = "/backToForm3", method = RequestMethod.POST)
    public final String backToForm3(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return WebConstants.FORMULAIRE3;
    }

    /**
     * Reload the form3 page, after a change of country.
     * @param dcoForm
     * @return form3 page
     */
    @RequestMapping(value = "/reloadForm3ChangeCountry", method = RequestMethod.POST)
    public final String reloadForm3ChangeCountry(
            @ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        setMapsParamsFunc();

        if (dcoForm.getAccounts() != null) {
            for (int i = 0; i < dcoForm.getAccounts().size(); i++) {
                if (i < dcoForm.getSelectedCountries().size()) {
                    continue;
                }
                final String selectedCountry = dcoForm.getSelectedCountries().get(i);
                if (dcoForm.getAccounts().get(i).getCountryAccount().getLocale().getCountry()
                        .equals(selectedCountry)) {
                    continue;
                }
                dcoForm.getAccounts().get(i).setCountryAccount(null);
                for (final CountryDto c : dcoForm.getAccountCountries()) {
                    if (c.getLocale().getCountry().equals(selectedCountry)) {
                        dcoForm.getAccounts().get(i).setCountryAccount(c);
                        applyChangeOnAccount(dcoForm, dcoForm.getAccounts().get(i), c);
                        break;
                    }
                }
            }
        }
        dcoForm.mapAccount();
        if (StringUtils.isNotBlank(dcoForm.getSelectedCountryForNewAccount())) {
            for (final CountryDto c : dcoForm.getAccountCountries()) {
                if (c.getLocale().getCountry().equals(dcoForm.getSelectedCountryForNewAccount())) {
                    dcoForm.getNewAccount().setCountryAccount(c);
                    applyChangeOnAccount(dcoForm, dcoForm.getNewAccount(), c);
                    break;
                }
            }
        } else {
            dcoForm.getNewAccount().setCountryAccount(null);
        }

        return WebConstants.FORMULAIRE3;
    }

    /**
     * Allow to delete an account in the form3 Page.
     * @param dcoForm
     * @param id of the account to delete
     * @return the form3 Page (reload if success)
     */
    @RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
    public String deleteAccount(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            final Integer id) {
        String ret = WebConstants.FORMULAIRE3;
        try {
            if (id != Constants.VAR_NEG_ONE) {
                BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_REMOVE,
                        new Object[] {id});
            }
            resetForm();
            ret = WebConstants.REDIRECT + ret;
        } catch (final DCOException e) {
        	err = true;
            LOG.error("An error occurred while deleteing the account: " + id, e);
            addError(this.propertiesHelper.getMessage("page.formulaire.web.message.delete.account.error"));
        }
        initBreadcrumbs();
        return ret;
    }

    /**
     * Allow to save the new account (if it has been created), and all the modifications on the others accounts
     * already existing.
     * @param dcoForm
     * @return the form3 Page.
     */
    @RequestMapping(value = "/saveForm3", method = RequestMethod.POST, params = "submit_etape3")
    public final String saveForm3(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return genericSaveForm3(dcoForm, WebConstants.FORMULAIRE3);
    }

    /**
     * Generic method to validate the form3 and record the accounts.
     * @param dcoForm
     * @param retForm
     * @return the return page in function of the action (previous: form2 Page, record: form3Page, next:
     *         formSignatureCard Page).
     */
    private String genericSaveForm3(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            final String retForm) {
        String ret = retForm;
        if (dcoForm.getMapAccount() != null && !dcoForm.getMapAccount().isEmpty() && dcoForm.getAccounts() != null
                && !dcoForm.getAccounts().isEmpty()) {
            int i = 0;
            Boolean test = true;
            for (final Entry<String, List<Integer>> entry : dcoForm.getMapAccount().entrySet()) {
                for (final Integer accountKey : entry.getValue()) {
                    dcoForm.getAccounts().get(accountKey).setPeriodicity(dcoForm.getListPeriodicity().get(i));
                    dcoForm.getAccounts().get(accountKey).setAddress(dcoForm.getListAddress().get(i));
                    dcoForm.getAccounts().get(accountKey).setCountry(dcoForm.getListCountry().get(i));
                    dcoForm.getAccounts().get(accountKey)
                            .setCommercialRegister(dcoForm.getListCommercialRegister().get(i));
                    dcoForm.getAccounts().get(accountKey).setVatNumber(dcoForm.getListVatNumber().get(i));
                    dcoForm.getAccounts().get(accountKey).setBranchName(dcoForm.getListBranchName().get(i));
                }
                if (dcoForm.getNewAccount() != null
                        && dcoForm.getNewAccount().getCountryAccount() != null
                        && dcoForm.getNewAccount().getCountryAccount().getLocale().getCountry()
                                .equalsIgnoreCase(entry.getKey())) {
                    dcoForm.getNewAccount().setPeriodicity(dcoForm.getListPeriodicity().get(i));
                    dcoForm.getNewAccount().setAddress(dcoForm.getListAddress().get(i));
                    dcoForm.getNewAccount().setCountry(dcoForm.getListCountry().get(i));
                    dcoForm.getNewAccount().setCommercialRegister(dcoForm.getListCommercialRegister().get(i));
                    dcoForm.getNewAccount().setVatNumber(dcoForm.getListVatNumber().get(i));
                    dcoForm.getNewAccount().setBranchName(dcoForm.getListBranchName().get(i));
                }
                i++;
            }

        }
        if (!checkInitFieldsForm3(dcoForm)) {
            boolean error = false;

            if (getDcoForm().getAccounts() != null && getDcoForm().getAccounts().size() > Constants.VAR_ZERO) {
                // Converting beans to statement types
                final AccountStatementTypesBean bean = dcoForm.getAccountStatementTypesBean();
                if (bean != null && bean.getMap() != null && bean.getMap().entrySet() != null) {
                    // for each int[] typeStatements associated with the account's id, in the bean: convert the
                    // statementTypes.
                    for (final Entry<Integer, Integer[]> entry : bean.getMap().entrySet()) {
                        // Fetch the corresponding account with the entry key.
                        AccountDto account = null;
                        final Iterator<AccountDto> itAccount = dcoForm.getAccounts().iterator();
                        boolean findAccount = false;
                        // We get the account in the form3, corresponding to the id key in the bean.
                        while (itAccount.hasNext() && !findAccount) {
                            final AccountDto acc = itAccount.next();
                            if (acc.getId() != null && acc.getId().compareTo(entry.getKey()) == Constants.VAR_ZERO) {
                                account = acc;
                                findAccount = true;
                            }
                        }
                        // Get the integer[] typeStatements, and convert it to List<ParamFunc>.
                        if (entry.getValue() != null) {
                            final Integer[] statementTypes = entry.getValue();
                            if (account != null && account.getCountryAccount() != null) {
                                final List<ParamFuncDto> statementTypesList = convertStatementType(statementTypes,
                                        account.getCountryAccount().getLocale().getCountry());
                                account.setStatementTypes(statementTypesList);
                            } else if (account != null) {
                                account.setStatementTypes(null);
                            }
                        }
                    }
                }
                error = checkForm3(dcoForm);
            }

            if (!error && checkForm3SpecialCharacters(dcoForm)) {
            	err = true;
                ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE3;
            } else if (!error) {
                error = doSaveForm3(dcoForm, retForm);
                if (!error) {
                    ret = WebConstants.REDIRECT_ACTION + ret;
                    try {
                        resetForm();
                    } catch (final DCOException e) {
                        addError(this.propertiesHelper.getMessage("page.unload.model"));
                        ret = WebConstants.REDIRECT_ACTION + WebConstants.HOME;
                        err = true;
                    }
                } else {
                	err = true;
                    ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE3;
                }
            } else {
            	err = true;
                ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE3;
                addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
            }
            if(error){
            	err = true;
            }
        } else {
            ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE3;
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
            err = true;
        }

        initBreadcrumbs();
        return ret;
    }

    /**
     * Check if the form3 contains one or more of special characters not allowed
     * @param dcoForm2
     * @return
     */
    private boolean checkForm3SpecialCharacters(final FormulaireDCOForm dcoForm2) {
        boolean error = false;
        if (dcoForm2.getAccounts() != null) {
            final Iterator<AccountDto> itAccount = dcoForm2.getAccounts().iterator();
            while (!error && itAccount.hasNext()) {
                final AccountDto account = itAccount.next();
                error = checkSpecialCharactersFieldsAccount(account);
            }
        }
        return error;
    }

    /**
     * Checks special characters in an account
     * @param account
     * @return
     */
    private boolean checkSpecialCharactersFieldsAccount(final AccountDto account) {
        boolean error = false;
        final List<Boolean> errors = new ArrayList<Boolean>();
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldOne()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldTwo()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldThree()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldFour()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldFive()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldSix()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getAddress().getFieldSeven()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getBranchName()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getCommercialRegister()));
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, account.getVatNumber()));
        if (errors.contains(Boolean.TRUE)) {
            addError(this.propertiesHelper.getMessage("page.form.invalidSpecialCharacters"));
            error = true;
            err = true;
        }
        return error;
    }

    /**
     * Allow to check the required fields in form3.
     * @param dcoForm
     * @return the verification
     */
    private boolean checkForm3(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        // Checking the required fields.
        if (dcoForm.getSelectedCountries() != null) {
            for (int i = 0; i < dcoForm.getSelectedCountries().size(); i++) {
                if (StringUtils.isBlank(dcoForm.getSelectedCountries().get(i))) {
                    error = true;
                    break;
                }
            }
        } else {
            error = true;
        }
        if (dcoForm.getAccounts() != null && !error) {
            final Iterator<AccountDto> itAccount = dcoForm.getAccounts().iterator();
            while (!error && itAccount.hasNext()) {
                final AccountDto account = itAccount.next();
                if (!error) {
                    error = checkRequiredFieldsAccount(account);
                }
            }
        } else {
            error = true;
        }
        initBreadcrumbs();
        return error;
    }

    /**
     * Check the required fields in the account.
     * @param account
     * @return
     */
    private boolean checkRequiredFieldsAccount(final AccountDto account) {
        boolean error = false;
        
        if (StringUtils.isBlank(account.getReference()) || account.getTypeAccount().getId() == null
                || account.getCurrency().getId() == null) {
            error = true;
        }
        if (account.getPeriodicity().getId() == null || checkSubsidiary(account)) {
            error = true;
        }
        return error;
    }

    /**
     * Allow to save the changes about the entity's accounts at the form3 Page.
     * @param dcoForm
     */
    private boolean doSaveForm3(final FormulaireDCOForm dcoForm, final String retForm) {
        boolean error = false;
        boolean newAccountError = false;
        boolean newAccountSpecialCharactersError = false;
        ;
        boolean duplicateReference = false;
        boolean newAccount = false;

        try {
            // Match selected countries
            if (dcoForm.getAccounts() != null) {
                for (int i = 0; i < dcoForm.getAccounts().size(); i++) {
                    if (i < dcoForm.getSelectedCountries().size()) {
                        final String selectedCountry = dcoForm.getSelectedCountries().get(i);
                        for (final CountryDto c : dcoForm.getAccountCountries()) {
                            if (c.getLocale().getCountry().equals(selectedCountry)) {
                                dcoForm.getAccounts().get(i).setCountryAccount(c);
                                break;
                            }
                        }
                    }
                }
            }
            List<AccountDto> accounts = null;
            if (dcoForm.getAccounts() != null && !dcoForm.getAccounts().isEmpty()) {
                accounts = dcoForm.getAccounts();
            }
            if (dcoForm.getNewAccount() != null) {
                dcoForm.generateNewAccountReference();
                if (StringUtils.isNotBlank(dcoForm.getNewAccount().getReference())) {
                    // The user has created a new account
                    newAccountError = checkNewAccount(dcoForm);
                    if (!newAccountError) {
                        newAccountSpecialCharactersError = checkSpecialCharactersFieldsAccount(dcoForm
                                .getNewAccount());
                    }
                    if (!newAccountError && !newAccountSpecialCharactersError) {
                        duplicateReference = checkReference(dcoForm);
                    }

                    if (!newAccountError && !duplicateReference && !newAccountSpecialCharactersError) {
                        if (accounts == null) {
                            accounts = new ArrayList<AccountDto>();
                        }
                        accounts.add(dcoForm.getNewAccount());
                        newAccount = true;
                    }
                    if (newAccountError) {
                    	err = true;
                        addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
                    }
                    // avoiding to reset form if true!
                    error = duplicateReference || newAccountSpecialCharactersError;

                } else if (dcoForm.getNewAccount().getCountryAccount() != null) {
                    // add required field error in case the new account fields have not been filled
                    // Reference field is required to save the new account
                    newAccountError = true;
                    err = true;
                    addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
                } else {
                    // don't add the new account into the accounts list and continue processing ...
                    // check reference duplication!
                    duplicateReference = checkReferenceModified(dcoForm);
                }
                if (duplicateReference) {
                    // defined or not: reset it to null!
                    dcoForm.getNewAccount().setReference(null);
                    err = true;
                    addError(this.propertiesHelper.getMessage("page.formulaire.duplicate.reference.error"));
                }
            }

            if (accounts != null && !newAccountError && !duplicateReference && !newAccountSpecialCharactersError) {
                saveAccounts(accounts, dcoForm.getAccountForm().getId());
                if (newAccount) {
                    copyAccountAssociation();
                }
                dcoForm.setAccounts(null);
                dcoForm.setMapAccount(null);
                addConfirm(this.propertiesHelper.getMessage("page.formulaire.success.saving"));
            }
        } catch (final DCOException e) {
            error = true;
            LOG.error("An error occurred while saving the account", e);
            addError(this.propertiesHelper.getMessage("page.param.error.saving"));
            err = true;
        }
        return error || newAccountError;
    }

    /**
     * Allow to check the new account (if it has been created)
     * @param dcoForm
     * @return false if there is none error.
     */
    private boolean checkNewAccount(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        if (dcoForm.getNewAccount().getCountryAccount() != null) {
            final List<ParamFuncDto> statementType = convertStatementType(
                    dcoForm.getAccountStatementTypesForNewAccount(), dcoForm.getNewAccount().getCountryAccount()
                            .getLocale().getCountry());
            getDcoForm().getNewAccount().setStatementTypes(statementType);
        } else {
            getDcoForm().getNewAccount().setStatementTypes(null);
        }

        if (StringUtils.isBlank(dcoForm.getSelectedCountryForNewAccount())
                || checkRequiredFieldsAccount(getDcoForm().getNewAccount())) {
            error = true;
        }

        if (!error) {
            final String selectedCountry = dcoForm.getSelectedCountryForNewAccount();
            for (final CountryDto c : dcoForm.getAccountCountries()) {
                if (c.getLocale().getCountry().equals(selectedCountry)) {
                    dcoForm.getNewAccount().setCountryAccount(c);
                    break;
                }
            }
        }
        return error;
    }

    /**
     * This method allows to check if all the required fields are filled (mainly the select blocks), in the form3
     * Page.
     * @param dcoForm
     * @return true if we can't save the accounts, in the form3 Page.
     */
    private boolean checkInitFieldsForm3(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        if (dcoForm.getMapCurrency() == null || dcoForm.getMapCurrency().isEmpty()
                || dcoForm.getMapLegalStatus() == null || dcoForm.getMapLegalStatus().isEmpty()) {
            error = true;
        }
        if (dcoForm.getMapPeriodicity() == null || dcoForm.getMapPeriodicity().isEmpty()
                || dcoForm.getMapStatementType() == null || dcoForm.getMapStatementType().isEmpty()) {
            error = true;
        }
        if (dcoForm.getAccountCountries() == null || dcoForm.getAccountCountries().size() == Constants.VAR_ZERO
                || getCountryList() == null || getCountryList().size() == Constants.VAR_ZERO) {
            error = true;
        }
        if (dcoForm.getMapAccount().size() < dcoForm.getListPeriodicity().size()) {
            error = true;
        }
        for (int i = Constants.VAR_ZERO; i < dcoForm.getListPeriodicity().size(); i++) {
            if (dcoForm.getListPeriodicity().get(i) == null) {
                error = true;
                break;
            }

        }
        return error;
    }

    /**
     * Check if the new account reference is unique
     * @param dcoForm
     * @return true if account reference already exists
     */
    private boolean checkReference(final FormulaireDCOForm dcoForm) {

        final String accString = dcoForm.getNewAccount().getReference();
        boolean error = false;
        if (dcoForm.getAccounts() != null) {
            if (dcoForm.getAccounts().isEmpty()) {
                return false;
            }
            for (final AccountDto acc : this.getDcoForm().getAccounts()) {
                if (acc.getReference().equals(accString)) {
                    error = true;
                    break;
                }
            }
        }
        return error;
    }

    /**
     * Check if the account reference modified is unique
     * @param dcoForm
     * @return true if account reference already exists
     */
    private boolean checkReferenceModified(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        boolean modRefDuplicated = false;
        if (dcoForm.getAccounts().isEmpty()) {
            return false;
        }
        for (final AccountDto acc : dcoForm.getAccounts()) {
            final String refToken = acc.getReference();
            // check reference duplication in the accounts list modified!
            if (!modRefDuplicated) {
                for (final AccountDto accSaved : dcoForm.getAccounts()) {
                    if (accSaved.getId().compareTo(acc.getId()) != Constants.VAR_ZERO
                            && refToken.equals(accSaved.getReference())) {
                        modRefDuplicated = true;
                        error = true;
                        // stop checking ...
                        break;
                    }
                }
            } else {
                // a reference duplicated has been found in the old accounts list modified!
                break;
            }
        }
        return error;
    }

    /**
     * Allow to check the subsidiary's fields.
     * @param account
     * @return true if all the required fields are empty (case OK), or false if at less one required field is not
     *         empty whereas the others are filled.
     */
    private boolean checkSubsidiary(final AccountDto account) {
        boolean error = true;
        	if (StringUtils.isBlank(account.getAddress().getFieldOne())
                    && StringUtils.isBlank(account.getAddress().getFieldTwo())
                    && StringUtils.isBlank(account.getAddress().getFieldThree())
                    && StringUtils.isBlank(account.getAddress().getFieldFour())
                    && StringUtils.isBlank(account.getCommercialRegister())
                    && StringUtils.isBlank(account.getVatNumber()) && StringUtils.isBlank(account.getCountry())
                    && StringUtils.isBlank(account.getBranchName())) {
                error = false;
            } else if (StringUtils.isNotBlank(account.getAddress().getFieldOne())
                    && StringUtils.isNotBlank(account.getAddress().getFieldTwo())
                    && StringUtils.isNotBlank(account.getAddress().getFieldThree())
                    && StringUtils.isNotBlank(account.getAddress().getFieldFour())
                    && StringUtils.isNotBlank(account.getCommercialRegister())
                    && StringUtils.isNotBlank(account.getVatNumber()) && StringUtils.isNotBlank(account.getCountry())
                    && StringUtils.isNotBlank(account.getBranchName())) {
                error = false;
            }
        
        return error;
    }

    /**
     * Copy the association from an account to another of the same country, at creation.
     * @throws DCOException
     */
    private void copyAccountAssociation() throws DCOException {
        if (getDcoForm().getNewAccount() != null) {
            final EntityDto entity = (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                    Constants.CONTROLLER_ENTITIES_GET_BY_USER_LOGIN, new Object[] {UserHelper.getUserInSession()
                            .getLogin()});
            if (entity != null) {
                final List<AccountDto> accountDtos = entity.getAccountsForm().get(Constants.VAR_ZERO)
                        .getAccountDtoList();
                final CountryDto c = getDcoForm().getNewAccount().getCountryAccount();
                final String reference = getDcoForm().getNewAccount().getReference();
                AccountDto accountRef = null;
                AccountDto account = null;
                if (c != null && StringUtils.isNotBlank(reference) && accountDtos != null) {
                    for (final AccountDto accountDto : accountDtos) {
                        if (accountDto.getReference().equalsIgnoreCase(reference)) {
                            account = accountDto;
                        } else if (accountDto.getCountryAccount().getLocale().getCountry()
                                .equals(c.getLocale().getCountry())
                                && accountDto.getAccountThirdPartyList().size() != Constants.VAR_ZERO) {
                            accountRef = accountDto;
                        }
                        if (accountRef != null && account != null) {
                            break;
                        }
                    }
                }
                if (accountRef != null && account != null && accountRef.getAccountThirdPartyList() != null
                        && accountRef.getAccountThirdPartyList().size() != Constants.VAR_ZERO) {
                    for (final AccountThirdPartyDto accountThirdPartyRef : accountRef.getAccountThirdPartyList()) {
                        final AccountThirdPartyDto accountThirdParty = new AccountThirdPartyDto();
                        accountThirdParty.setAccount(account);
                        accountThirdParty.setThirdParty(accountThirdPartyRef.getThirdParty());
                        accountThirdParty.setStatusAmountLimit(accountThirdPartyRef.getStatusAmountLimit());
                        accountThirdParty.setDeviseAmountLimit(accountThirdPartyRef.getDeviseAmountLimit());
                        accountThirdParty.setAmountLimit(accountThirdPartyRef.getAmountLimit());
                        accountThirdParty.setSignatureAuthorization(accountThirdPartyRef
                                .getSignatureAuthorization());
                        accountThirdParty.setPowerType(accountThirdPartyRef.getPowerType());
                        accountThirdParty.setPublicDeedReference(accountThirdPartyRef.getPublicDeedReference());
                        accountThirdParty.setBoardResolutionDate(accountThirdPartyRef.getBoardResolutionDate());
                        BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                Constants.CONTROLLER_ACCOUNT_THIRD_PARTY_SAVE, new Object[] {accountThirdParty});
                        if (accountThirdParty.getAccount() != null
                                && accountThirdParty.getAccount().getId() > Constants.VAR_ZERO
                                && accountThirdPartyRef.getAuthorizedList() != null
                                && accountThirdPartyRef.getAuthorizedList().size() > Constants.VAR_ZERO) {
                            final Integer accountId = accountThirdParty.getAccount().getId();
                            final List<AccountThirdPartyDto> accountThirdPartyList = (List<AccountThirdPartyDto>) BusinessHelper
                                    .call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                            Constants.CONTROLLER_ACCOUNT_THIRD_PARTY_GET_ACCOUNT_ID,
                                            new Object[] {accountId});
                            AccountThirdPartyDto newAccountThirdParty = accountThirdPartyList
                                    .get(accountThirdPartyList.size() - 1);
                            newAccountThirdParty = addAuthorizationThirdParty(newAccountThirdParty,
                                    accountThirdPartyRef.getAuthorizedList());
                            if (newAccountThirdParty.getId() != Constants.VAR_NEG_ONE) {
                                BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                        Constants.CONTROLLER_AUTHORIZATION_THIRD_PARTY_SAVE,
                                        new Object[] {newAccountThirdParty});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * set the authorized thirdParty list on the current account thirdParty association and return it.
     * @param accountThirdParty
     * @param authorizationThirdPartyListRef
     * @return
     */
    private AccountThirdPartyDto addAuthorizationThirdParty(final AccountThirdPartyDto accountThirdParty,
            final List<AuthorizationThirdPartyDto> authorizationThirdPartyListRef) {
        final AccountThirdPartyDto newAccountThirdParty = new AccountThirdPartyDto();
        final List<AuthorizationThirdPartyDto> authorizationThirdPartyList = new ArrayList<AuthorizationThirdPartyDto>();
        if (accountThirdParty != null && accountThirdParty.getId() > Constants.VAR_ZERO
                && authorizationThirdPartyListRef != null
                && authorizationThirdPartyListRef.size() > Constants.VAR_ZERO) {
            final Integer accounThirdPartyId = accountThirdParty.getId();
            for (final AuthorizationThirdPartyDto authorizationThirdPartyRef : authorizationThirdPartyListRef) {
                final AuthorizationThirdPartyIdDto authorizationThirdPartyId = new AuthorizationThirdPartyIdDto();
                authorizationThirdPartyId.setAccountThirdPartyId(accounThirdPartyId);
                authorizationThirdPartyId.setThirdPartyId(authorizationThirdPartyRef.getId().getThirdPartyId());
                final AuthorizationThirdPartyDto authorizationThirdParty = new AuthorizationThirdPartyDto();
                authorizationThirdParty.setId(authorizationThirdPartyId);
                authorizationThirdParty.setAccountThirdParty(accountThirdParty);
                authorizationThirdPartyList.add(authorizationThirdParty);
            }
        }
        if (accountThirdParty != null) {
            newAccountThirdParty.setId(accountThirdParty.getId());
            newAccountThirdParty.setThirdParty(accountThirdParty.getThirdParty());
            newAccountThirdParty.setPowerType(accountThirdParty.getPowerType());
            newAccountThirdParty.setAccount(accountThirdParty.getAccount());
            newAccountThirdParty.setBoardResolutionDate(accountThirdParty.getBoardResolutionDate());
            newAccountThirdParty.setPublicDeedReference(accountThirdParty.getPublicDeedReference());
            newAccountThirdParty.setStatusAmountLimit(accountThirdParty.getStatusAmountLimit());
            newAccountThirdParty.setDeviseAmountLimit(accountThirdParty.getDeviseAmountLimit());
            newAccountThirdParty.setAmountLimit(accountThirdParty.getAmountLimit());
            newAccountThirdParty.setSignatureAuthorization(accountThirdParty.getSignatureAuthorization());
            newAccountThirdParty.setAuthorizedList(authorizationThirdPartyList);
        } else {
            newAccountThirdParty.setId(Constants.VAR_NEG_ONE);
        }
        return newAccountThirdParty;
    }

    /**
     * Converter the integer[] statementType to List<ParamFunc>.
     * @param statementTypes
     */
    private List<ParamFuncDto> convertStatementType(final Integer[] statementTypes, final String country) {
        final List<ParamFuncDto> statementTypesList = new ArrayList<ParamFuncDto>();
        for (final Integer idStatement : statementTypes) {
            final Iterator<ParamFuncDto> itStatementType = getDcoForm().getMapStatementType().get(country)
                    .iterator();
            boolean find = false;
            while (itStatementType.hasNext() && !find) {
                final ParamFuncDto statementType = itStatementType.next();
                if (statementType.getId() != null
                        && statementType.getId().compareTo(idStatement) == Constants.VAR_ZERO) {
                    statementTypesList.add(statementType);
                    find = true;
                }
            }
        }
        return statementTypesList;
    }

    /**
     * Set an alert message and a default account Type for an account
     * @param dcoForm the form for the account page
     * @param accountDto account that was modified
     * @param c country selected for the account
     */
    private void applyChangeOnAccount(final FormulaireDCOForm dcoForm, final AccountDto accountDto,
            final CountryDto c) {
        if (c.getLocale().getCountry().equalsIgnoreCase(Constants.ALRT_CONTRY_CODE_FRANCE)
                || c.getLocale().getCountry().equalsIgnoreCase(Constants.ALRT_CONTRY_CODE_BELGIUM)
                || c.getLocale().getCountry().equalsIgnoreCase(Constants.ALRT_CONTRY_CODE_PORTUGAL)) {
            dcoForm.setAlert(true);
        } else {
            dcoForm.setAlert(false);
        }
        final ParamFuncDto typeAccount = dcoForm.getAccountType(c,
                this.propertiesHelper.getMessage("page.fomulaire.account.default.type"));
        if (typeAccount != null) {
            accountDto.setTypeAccount(typeAccount);
        }
    }

    /**
     * Generic method to fill all the maps of paramFunc list.
     */
    private void setMapsParamsFunc() {
        try {
            final List<String> selectedCountries = new ArrayList<String>(getDcoForm().getSelectedCountries());
            if (!selectedCountries.contains(getDcoForm().getSelectedCountryForNewAccount())) {
                selectedCountries.add(getDcoForm().getSelectedCountryForNewAccount());
            }
            for (final String country : selectedCountries) {
                fillParamFuncMap(country, Constants.PARAM_TYPE_ACCNT_TYPE, getDcoForm().getMapTypeAccount());
                fillParamFuncMap(country, Constants.PARAM_TYPE_ACCNT_CURRENCY, getDcoForm().getMapCurrency());
                fillParamFuncMap(country, Constants.PARAM_TYPE_ACCNT_STATE_PERIOD, getDcoForm()
                        .getMapPeriodicity());
                fillParamFuncMap(country, Constants.PARAM_TYPE_ACCNT_STATE_SUPPORT, getDcoForm()
                        .getMapStatementType());
            }
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.unload.model"));
            err = true;
        }
    }

    /***************************************************************************************************************
     ********************************************** ____FORMULAIRE4____ ********************************************
     **************************************************************************************************************/

    /**
     * Access the form4 page
     * @return form4 page
     */
    @RequestMapping(value = "/" + WebConstants.FORMULAIRE4, method = RequestMethod.GET)
    public final String getFormulaire4() {
        String ret = WebConstants.FORMULAIRE4;
        
        if(!err){
        	initAccountThitdPartyFields();
        	dcoForm.setError(false);
        }
        else{
        	dcoForm.setError(true);
        }
        if (getDcoForm().getAccounts() == null || getDcoForm().getAccounts().isEmpty()) {
            ret = WebConstants.FORMULAIRE3;
            addError(this.propertiesHelper.getMessage("page.formulaire.no.existing.account"));
            err = true;
        } else {
            for (final AccountDto account : getDcoForm().getAccounts()) {
                if (account.getCountryAccount() == null) {
                	err = true;
                    addError(this.propertiesHelper.getMessage("page.user.error"));
                    // Returning to the Account Page (data integrity error occurred on accounts)
                    ret = WebConstants.FORMULAIRE3;
                    break;
                }
            }
        }
        return ret;
    }

    @RequestMapping(value = "/form4ToNext", method = RequestMethod.POST)
    public final String form4ToNext(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        String ret = WebConstants.FORMULAIRE5;
        if (precheckFormulaire4(dcoForm)) {
            ret = genericSaveFormulaire4(dcoForm, ret);
        } else {
            ret = WebConstants.REDIRECT + ret;
        }
        if (ret.contains(WebConstants.FORMULAIRE5) && checkAssociations(true)) {
            initFormLegalRepresentatives();
        }
        return ret;
    }

    @RequestMapping(value = "/saveForm4AndRedirect/{redirect}", method = RequestMethod.POST)
    public final String saveForm4AndRedirect(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            @PathVariable final String redirect) {
        String ret = redirect;
        if (precheckFormulaire4(dcoForm)) {
            ret = genericSaveFormulaire4(dcoForm, ret);
        } else {
            ret = WebConstants.REDIRECT_ACTION + ret;
        }
        if (ret.contains(WebConstants.FORMULAIRE5) && checkAssociations(true)) {
            initFormLegalRepresentatives();
        }
        return ret;
    }

    @RequestMapping(value = "/backToForm4", method = RequestMethod.POST)
    public final String backToForm4(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return WebConstants.REDIRECT + WebConstants.FORMULAIRE4;
    }

    @RequestMapping(value = "/deleteFormulaire4", method = RequestMethod.POST)
    public String deleteCountryAccountThirdParty(
            @ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        boolean remove = false;
        boolean error = false;
        if (dcoForm.getAccounts() == null || dcoForm.getMapAccount() == null) {
        	err = true;
            addError(this.propertiesHelper
                    .getMessage("page.formulaire.web.message.delete.account.third.party.error"));
            error = true;
        }
        if (!error && StringUtils.isBlank(dcoForm.getDeleteCountry())
                || dcoForm.getDeleteThirdParty() <= Constants.VAR_ZERO
                || dcoForm.getDeleteSignatureAuthorization() <= Constants.VAR_ZERO) {
            addError(this.propertiesHelper
                    .getMessage("page.formulaire.web.message.delete.account.third.party.error"));
            error = true;
            err = true;
        }
        if (!error) {
            final List<AccountDto> accounts = new ArrayList<AccountDto>();
            final List<Integer> array = new ArrayList<Integer>();
            for (final Integer accPos : dcoForm.getMapAccount().get(dcoForm.getDeleteCountry())) {
                remove = false;
                final AccountDto account = dcoForm.getAccounts().get(accPos);
                if (account != null) {
                    for (int i = 0; i < account.getAccountThirdPartyList().size(); i++) {
                        final AccountThirdPartyDto accountThirdParty = account.getAccountThirdPartyList().get(i);
                        if (accountThirdParty.getThirdParty().getId().compareTo(dcoForm.getDeleteThirdParty()) == Constants.VAR_ZERO
                                && accountThirdParty.getSignatureAuthorization().getId()
                                        .compareTo(dcoForm.getDeleteSignatureAuthorization()) == Constants.VAR_ZERO) {
                            array.add(accountThirdParty.getId());
                            account.getAccountThirdPartyList().remove(i);
                            remove = true;
                            break;
                        }
                    }
                    if (remove) {
                        accounts.add(account);
                    }
                }
            }
            if (accounts.size() == dcoForm.getMapAccount().get(dcoForm.getDeleteCountry()).size()) {
                try {
                    for (int i = 0; i < accounts.size(); i++) {
                        final List<AccountDto> saveAccounts = new ArrayList<AccountDto>(1);
                        saveAccounts.add(accounts.get(i));
                        saveAccounts(accounts, getDcoForm().getAccountForm().getId());
                        BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                Constants.CONTROLLER_ACCOUNT_THIRD_PARTY_DELETE, new Object[] {array.get(i)});
                    }
                    addConfirm(this.propertiesHelper
                            .getMessage("page.formulaire.web.message.delete.account.third.party"));
                } catch (final DCOException e) {
                	err = true;
                    addError(this.propertiesHelper
                            .getMessage("page.formulaire.web.message.delete.account.third.party.error"));
                }
            }
        }
        getDcoForm().mapAccount();
        initBreadcrumbs();
        return WebConstants.REDIRECT + WebConstants.FORMULAIRE4;
    }

    @RequestMapping(value = "/saveFormulaire4", method = RequestMethod.POST)
    public final String saveFormulaire4(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return genericSaveFormulaire4(dcoForm, WebConstants.FORMULAIRE4);
    }

    private void initAccountThitdPartyFields() {
        getDcoForm().setCountryATP("");
        getDcoForm().setThirdPartyDtoSelect(null);
        getDcoForm().setAccountThirdPartyDto(new AccountThirdPartyDto());
        getDcoForm().setPowerType(null);
        
        getDcoForm().setBoardResolutionDay("");
        getDcoForm().setBoardResolutionMonth("");
        getDcoForm().setBoardResolutionYear("");
        getDcoForm().setBoardResolutionDate("");
        
        getDcoForm().setPublicDeedReference("");
        getDcoForm().setStatusAmountLimit(null);
        getDcoForm().setDeviseAmountLimit(null);
        getDcoForm().setAmountLimit(null);
        getDcoForm().setSignatureAuthorizationSelect(null);
        final int[] tab = ArrayUtils.EMPTY_INT_ARRAY;
        getDcoForm().setAuthorizedThirdPartyList(tab);
        retriveSignatureAuthorisationCode();
    }

    private String genericSaveFormulaire4(final FormulaireDCOForm dcoForm, final String retForm) {
        err=false;
    	String ret = retForm;
        boolean error = checkFormulaire4(dcoForm);
        if (!error) {
            error = buildAccountThirdParty(dcoForm);
        }
        if (!error) {
            final List<Integer> accountIndex = dcoForm.getMapAccount().get(dcoForm.getCountryATP());
            if (accountIndex != null && !accountIndex.isEmpty()) {
                for (final Integer index : accountIndex) {
                    error = checkAccountThirdPartyDuplicate(dcoForm.getAccounts().get(index),
                            dcoForm.getAccountThirdPartyDto());
                    if (error) {
                        break;
                    }
                }
            }
        }
        if (!error) {
            try {
                final List<Integer> accountIndex = dcoForm.getMapAccount().get(dcoForm.getCountryATP());
                if (accountIndex != null && !accountIndex.isEmpty()) {
                    for (final Integer index : accountIndex) {
                        final AccountThirdPartyDto accountThirdPartyDto = new AccountThirdPartyDto();
                        accountThirdPartyDto.setAccount(dcoForm.getAccounts().get(index));
                        accountThirdPartyDto.setThirdParty(dcoForm.getAccountThirdPartyDto().getThirdParty());
                        accountThirdPartyDto.setStatusAmountLimit(dcoForm.getAccountThirdPartyDto()
                                .getStatusAmountLimit());
                        accountThirdPartyDto.setDeviseAmountLimit(dcoForm.getAccountThirdPartyDto()
                                .getDeviseAmountLimit());
                        accountThirdPartyDto.setAmountLimit(dcoForm.getAccountThirdPartyDto().getAmountLimit());
                        accountThirdPartyDto.setSignatureAuthorization(dcoForm.getAccountThirdPartyDto()
                                .getSignatureAuthorization());
                        accountThirdPartyDto.setPowerType(dcoForm.getAccountThirdPartyDto().getPowerType());
                        accountThirdPartyDto.setPublicDeedReference(dcoForm.getAccountThirdPartyDto()
                                .getPublicDeedReference());
                        accountThirdPartyDto.setBoardResolutionDate(dcoForm.getAccountThirdPartyDto()
                                .getBoardResolutionDate());

                        BusinessHelper
                                .call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                        Constants.CONTROLLER_ACCOUNT_THIRD_PARTY_SAVE,
                                        new Object[] {accountThirdPartyDto});

                        if (getDcoForm().getAuthorizedThirdPartyList() != null
                                && getDcoForm().getAuthorizedThirdPartyList().length > Constants.VAR_ZERO
                                && accountThirdPartyDto.getAccount() != null
                                && accountThirdPartyDto.getAccount().getId() > Constants.VAR_ZERO) {
                            final Integer accountDtoId = accountThirdPartyDto.getAccount().getId();
                            final List<AccountThirdPartyDto> accountThirdPartyList = (List<AccountThirdPartyDto>) BusinessHelper
                                    .call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                            Constants.CONTROLLER_ACCOUNT_THIRD_PARTY_GET_ACCOUNT_ID,
                                            new Object[] {accountDtoId});

                            AccountThirdPartyDto newAccountThirdParty = accountThirdPartyList
                                    .get(accountThirdPartyList.size() - 1);
                            if (newAccountThirdParty.getId() != Constants.VAR_NEG_ONE) {
                                newAccountThirdParty = doSaveAuthorizationThirdParty(newAccountThirdParty);
                                if (newAccountThirdParty.getId() != Constants.VAR_NEG_ONE) {
                                    BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY,
                                            Constants.CONTROLLER_AUTHORIZATION_THIRD_PARTY_SAVE,
                                            new Object[] {newAccountThirdParty});
                                }
                            }
                        }
                    }
                    resetForm();
                    addConfirm(this.propertiesHelper.getMessage("page.param.saving.account.third.party.success"));
                }
            } catch (final DCOException e) {
                LOG.error("An error occurred while saving the account third party", e);
                addError(this.propertiesHelper.getMessage("page.param.error.saving.account.third.party"));
                ret = WebConstants.FORMULAIRE4;
                err = true;
            }
        }
        initBreadcrumbs();
        if (error) {
            ret = WebConstants.FORMULAIRE4;
            err = true;
        }
        ret = WebConstants.REDIRECT_ACTION + ret;
        return ret;
    }

    /**
     * Method set the authorized thirdParty list on the current account thirdParty association.
     **/
    private AccountThirdPartyDto doSaveAuthorizationThirdParty(final AccountThirdPartyDto accTp) {

        final AccountThirdPartyDto newAccountThird = new AccountThirdPartyDto();
        final List<AuthorizationThirdPartyDto> thirdList = new ArrayList<AuthorizationThirdPartyDto>();

        if (accTp != null && accTp.getId() > Constants.VAR_ZERO
                && getDcoForm().getAuthorizedThirdPartyList() != null
                && getDcoForm().getAuthorizedThirdPartyList().length > Constants.VAR_ZERO) {
            final Integer accounTthirdPartyId = accTp.getId();
            for (final Integer thirdId : getDcoForm().getAuthorizedThirdPartyList()) {
                final AuthorizationThirdPartyIdDto tdId = new AuthorizationThirdPartyIdDto();
                tdId.setAccountThirdPartyId(accounTthirdPartyId);
                tdId.setThirdPartyId(thirdId);
                final AuthorizationThirdPartyDto authTdId = new AuthorizationThirdPartyDto();
                authTdId.setId(tdId);
                authTdId.setAccountThirdParty(accTp);
                thirdList.add(authTdId);
            }
        }
        if (accTp != null) {
            newAccountThird.setId(accTp.getId());
            newAccountThird.setThirdParty(accTp.getThirdParty());
            newAccountThird.setPowerType(accTp.getPowerType());
            newAccountThird.setAccount(accTp.getAccount());
            newAccountThird.setBoardResolutionDate(accTp.getBoardResolutionDate());
            newAccountThird.setPublicDeedReference(accTp.getPublicDeedReference());
            newAccountThird.setStatusAmountLimit(accTp.getStatusAmountLimit());
            newAccountThird.setDeviseAmountLimit(accTp.getDeviseAmountLimit());
            newAccountThird.setAmountLimit(accTp.getAmountLimit());
            newAccountThird.setSignatureAuthorization(accTp.getSignatureAuthorization());
            newAccountThird.setAuthorizedList(thirdList);
        } else {
            newAccountThird.setId(Constants.VAR_NEG_ONE);
        }
        return newAccountThird;
    }

    private void retriveSignatureAuthorisationCode() {
        if (getDcoForm().getSignatureAuthorizationList() != null
                && !getDcoForm().getSignatureAuthorizationList().isEmpty()) {
            for (final ParamFuncDto paramFuncDto : getDcoForm().getSignatureAuthorizationList()) {
                if (paramFuncDto.getEntry().equalsIgnoreCase(
                        this.propertiesHelper.getMessage("page.formulaire.param.individually"))) {
                    getDcoForm().setCodeSAIndiv(paramFuncDto.getId());
                }
                if (paramFuncDto.getEntry().equalsIgnoreCase(
                        this.propertiesHelper.getMessage("page.formulaire.param.jointly"))) {
                    getDcoForm().setCodeSAJoinW(paramFuncDto.getId());
                }
            }
        }
    }

    private boolean precheckFormulaire4(final FormulaireDCOForm dcoForm) {
        boolean result = true;
        if (StringUtils.isBlank(dcoForm.getCountryATP()) && dcoForm.getThirdPartyDtoSelect() == null) {
            result = false;
        }
        return result;
    }

    private boolean checkFormulaire4(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        final List<Boolean> errors = new ArrayList<Boolean>();
        
        final String dateDayString = dcoForm.getBoardResolutionDay();
        final String dateMonthString = dcoForm.getBoardResolutionMonth();
        final String dateYearString = dcoForm.getBoardResolutionYear();
        
        if(dcoForm.getPowerType()!= null && dcoForm.getPowerType()!= Constants.PARAM_CODE_ACC_THIRD_PWR_FR1 && dcoForm.getPowerType()!= Constants.PARAM_CODE_ACC_THIRD_PWR_FR2){
        	if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("dd/mm/yyyy")){
            	dcoForm.setBoardResolutionDate(dateDayString + "/" + dateMonthString + "/" + dateYearString);
            }
            else if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("mm/dd/yyyy")){
            	dcoForm.setBoardResolutionDate(dateMonthString + "/" + dateDayString + "/" + dateYearString);
            }
            else if(UserHelper.getUserInSession().getPreferences().getDateFormat().getLabelDisplay().equals("yyyy/mm/dd")){
            	dcoForm.setBoardResolutionDate(dateYearString + "/" + dateMonthString + "/" + dateDayString);
            }
        }
        
        errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, dcoForm.getBoardResolutionDate()));
        if (errors.contains(Boolean.TRUE)) {
            addError(this.propertiesHelper.getMessage("page.form.invalidSpecialCharacters"));
            error = true;
        }
        if (dcoForm.getSignatureAuthorizationList() == null || dcoForm.getSignatureAuthorizationList().isEmpty()) {
            addError(this.propertiesHelper.getMessage("page.param.error.saving.account.third.party"));
            error = true;
        }
        if (!error && StringUtils.isBlank(dcoForm.getCountryATP())) {
            addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.country"));
            error = true;
        }
        if (!error && dcoForm.getThirdPartyDtoSelect() == null) {
            addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.third.party"));
            error = true;
        }
        if (!error) {
        	if(dcoForm.getPowerType() == null){
        		addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.power"));
        		error = true;
        	}
        	else {
        		switch (dcoForm.getPowerType()) {
                case Constants.PARAM_CODE_ACC_THIRD_PWR_PT:
                    if (StringUtils.isBlank(dcoForm.getBoardResolutionDate())
                            || DateUtils.validate(dcoForm.getBoardResolutionDate()) == null
                            || StringUtils.isNotBlank(dcoForm.getPublicDeedReference())) {
                        addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.act.dated",
                                new String[] {UserHelper.getUserInSession().getPreferences().getDateFormat()
                                        .getLabelDisplay()}));
                        error = true;
                    }
                    break;
                case Constants.PARAM_CODE_ACC_THIRD_PWR_GB:
                    if (StringUtils.isBlank(dcoForm.getBoardResolutionDate())
                            || DateUtils.validate(dcoForm.getBoardResolutionDate()) == null
                            || StringUtils.isNotBlank(dcoForm.getPublicDeedReference())) {
                        addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.board.dated",
                                new String[] {UserHelper.getUserInSession().getPreferences().getDateFormat()
                                        .getLabelDisplay()}));
                        error = true;
                    }
                    break;
                case Constants.PARAM_CODE_ACC_THIRD_PWR_ES:
                	if (StringUtils.isBlank(dcoForm.getPublicDeedReference())
                            || StringUtils.isBlank(dcoForm.getBoardResolutionDate())
                            || DateUtils.validate(dcoForm.getBoardResolutionDate()) == null) {
                        addError(this.propertiesHelper.getMessage(
                                "page.fomulaire.account.third.party.no.public.dated", new String[] {UserHelper
                                        .getUserInSession().getPreferences().getDateFormat().getLabelDisplay()}));
                        error = true;
                    }
                    break;
                case Constants.PARAM_CODE_ACC_THIRD_PWR_FR1:
                case Constants.PARAM_CODE_ACC_THIRD_PWR_FR2:
                    if (StringUtils.isNotBlank(dcoForm.getPublicDeedReference())
                            || StringUtils.isNotBlank(dcoForm.getBoardResolutionDate())) {
                        addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.dated",
                                new String[] {UserHelper.getUserInSession().getPreferences().getDateFormat()
                                        .getLabelDisplay()}));
                        error = true;
                    }
                    break;
                default:
                    addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.power"));
                    error = true;
                }
        	}
        }
        if (!error && dcoForm.getSignatureAuthorizationSelect() == null) {
            addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.signature"));
            error = true;
        }
        ParamFuncDto signature = null;
        if (!error) {
            for (final ParamFuncDto paramFuncDto : dcoForm.getSignatureAuthorizationList()) {
                if (paramFuncDto.getId().equals(dcoForm.getSignatureAuthorizationSelect())) {
                    signature = paramFuncDto;
                    break;
                }
            }
            if (signature == null) {
                addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.signature"));
                error = true;
            }
        }
        if (!error) {
            if (signature.getId().equals(dcoForm.getCodeSAJoinW())
                    ^ (dcoForm.getAuthorizedThirdPartyList() != null && dcoForm.getAuthorizedThirdPartyList().length != Constants.VAR_ZERO)) {
                addError(this.propertiesHelper.getMessage("page.param.error.saving.account.third.party"));
                error = true;
            }
        }
        if (!error && signature.getId().equals(dcoForm.getCodeSAJoinW())) {
            for (int i = 0; !error && i < dcoForm.getAuthorizedThirdPartyList().length; i++) {
                if (dcoForm.getThirdPartyDtoSelect().equals(dcoForm.getAuthorizedThirdPartyList()[i])) {
                    addError(this.propertiesHelper.getMessage("page.param.error.saving.account.third.party"));
                    error = true;
                }
            }
        }
        if (!error && dcoForm.getStatusAmountLimit() == null) {
            addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.amount.status"));
            error = true;
        }
        if (!error) {
            switch (dcoForm.getStatusAmountLimit()) {
            case Constants.PARAM_CODE_AMOUNT_NO_LIMIT:
                if (dcoForm.getAmountLimit() != null) {
                    addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.amount"));
                    error = true;
                }
                break;
            case Constants.PARAM_CODE_AMOUNT_LIMIT:
            	if (StringUtils.isBlank(dcoForm.getDeviseAmountLimit())) {
                    addError(this.propertiesHelper.getMessage("page.formulaire.web.message.dropdownListeDeviseCompte"));
                    error = true;
                }
                if (dcoForm.getAmountLimit() == null) {
                    addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.no.amount"));
                    error = true;
                }
                break;
            default:
                addError(this.propertiesHelper.getMessage("page.fomulaire.account.third.party.amount.status"));
                error = true;
            }
        }
        return error;
    }

    private boolean buildAccountThirdParty(final FormulaireDCOForm dcoForm) {
        boolean error = false;
        if (dcoForm.getTPForATPList() != null && !dcoForm.getTPForATPList().isEmpty()) {
            for (final ThirdPartyDto tp : getDcoForm().getTPForATPList()) {
                if (tp.getId().equals(dcoForm.getThirdPartyDtoSelect())) {
                    dcoForm.getAccountThirdPartyDto().setThirdParty(tp);
                }
            }
        }
        if (dcoForm.getAccountThirdPartyDto().getThirdParty() == null) {
            addError(this.propertiesHelper.getMessage("page.param.error.saving.account.no.third.party"));
            error = true;
        }
        if (!error) {
            dcoForm.getAccountThirdPartyDto().setPowerType(dcoForm.getPowerType());
            dcoForm.getAccountThirdPartyDto().setPublicDeedReference(dcoForm.getPublicDeedReference());
            dcoForm.getAccountThirdPartyDto().setBoardResolutionDate(
                    DateUtils.validate(dcoForm.getBoardResolutionDate()));
            dcoForm.getAccountThirdPartyDto().setStatusAmountLimit(dcoForm.getStatusAmountLimit());
            dcoForm.getAccountThirdPartyDto().setDeviseAmountLimit(dcoForm.getDeviseAmountLimit());
            dcoForm.getAccountThirdPartyDto().setAmountLimit(dcoForm.getAmountLimit());
            for (final ParamFuncDto paramFuncDto : dcoForm.getSignatureAuthorizationList()) {
                if (paramFuncDto.getId().compareTo(dcoForm.getSignatureAuthorizationSelect()) == Constants.VAR_ZERO) {
                    dcoForm.getAccountThirdPartyDto().setSignatureAuthorization(paramFuncDto);
                    break;
                }
            }
        }
        return error;
    }

    private boolean checkAccountThirdPartyDuplicate(final AccountDto account, final AccountThirdPartyDto aTP) {
        boolean error = false;

        if (account.getAccountThirdPartyList() != null && !account.getAccountThirdPartyList().isEmpty()) {
            for (final AccountThirdPartyDto aTPdto : account.getAccountThirdPartyList()) {
                if (aTP.getSignatureAuthorization() != null
                        && aTPdto.getSignatureAuthorization() != null
                        && aTP.getSignatureAuthorization().getId()
                                .compareTo(aTPdto.getSignatureAuthorization().getId()) == Constants.VAR_ZERO
                        && aTP.getThirdParty().getId().compareTo(aTPdto.getThirdParty().getId()) == Constants.VAR_ZERO) {
                    addError(this.propertiesHelper
                            .getMessage("page.param.error.saving.account.third.party.signatory"));
                    error = true;
                    break;
                }
            }

        }
        return error;
    }

    /***************************************************************************************************************
     ********************************************** ____FORMULAIRE5____ ********************************************
     **************************************************************************************************************/

    /**
     * @return the legal Representatives Page.
     */
    @RequestMapping(value = "/" + WebConstants.FORMULAIRE5, method = RequestMethod.GET)
    public final String getFormulaire5() {
        
    	if(!err){
        	dcoForm.setError(false);
        }
        else{
        	dcoForm.setError(true);
        }
    	
    	String ret = WebConstants.FORMULAIRE5;
        if (checkAssociations(false)) {
            initFormLegalRepresentatives();
        } else {
            ret = WebConstants.REDIRECT + WebConstants.FORMULAIRE4;
        }
        return ret;
    }

    /**
     * Allow to save legal Representatives and go forward next step
     * @param dcoForm
     * @return the Summary Page.
     */
    @RequestMapping(value = "/form5ToNext", method = RequestMethod.POST)
    public final String form5ToNext(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
    	dcoForm.setError(false);
    	return genericSaveForm5(dcoForm, WebConstants.FORM_SUMMARY);
    }

    /**
     * Allow to save legal Representatives and redirect
     * @param dcoForm
     * @param redirect page to redirect to
     * @return the redirect Page.
     */
    @RequestMapping(value = "/saveForm5AndRedirect/{redirect}", method = RequestMethod.POST)
    public final String saveForm5AndRedirect(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            @PathVariable final String redirect) {
        return genericSaveForm5(dcoForm, redirect);
    }

    @RequestMapping(value = "/backToForm5", method = RequestMethod.POST)
    public final String backToForm5(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        initFormLegalRepresentatives();
        return WebConstants.FORMULAIRE5;
    }

    /**
     * Allow to save the legal Representatives and stay on the form page
     * @param dcoForm
     * @return the legal Representatives Page.
     */
    @RequestMapping(value = "/saveForm5", method = RequestMethod.POST)
    public final String saveForm5(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return genericSaveForm5(dcoForm, WebConstants.FORMULAIRE5);
    }

    /**
     * Method used to upload list of legal representatives into main form.
     **/
    private void initFormLegalRepresentatives() {
        try {
            final List<ThirdPartyDto> thirdPartyList = (List<ThirdPartyDto>) BusinessHelper.call(
                    Constants.CONTROLLER_THIRD_PARTY, Constants.CONTROLLER_THIRD_PARTY_BY_USER_LOGIN,
                    new Object[] {UserHelper.getUserInSession().getLogin()});

            if (thirdPartyList != null && thirdPartyList.size() > Constants.VAR_ZERO) {

                final List<ThirdPartyDto> legalRepresentatives = new ArrayList<ThirdPartyDto>();

                for (final ThirdPartyDto third : thirdPartyList) {
                    if (third.getCorrespondantTypeThree() != null
                            && third.getCorrespondantTypeThree().compareTo(Constants.THIRD_LEGAL_REPRESENTATIVE) == Constants.VAR_ZERO) {
                        legalRepresentatives.add(third);
                    }
                }
                getDcoForm().setLegalRepresentativesList(legalRepresentatives);

            }

        } catch (final DCOException e) {
            LOG.error("Error while initializing legal Representatives of the entity");
        }
    }

    /**
     * Generic method to validate the page5form and record the datas.
     * @param dcoForm
     * @param retForm
     * @return the return page in function of the action (previous: form4 Page, record: page5, next: summary Page).
     */
    private String genericSaveForm5(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm,
            final String retForm) {
        String ret = retForm;

        if (checkForm5(dcoForm)) {
            ret = WebConstants.REDIRECT_ACTION + WebConstants.FORMULAIRE5;
        } else {
            if (!doSaveForm5(dcoForm, ret)) {
                ret = WebConstants.REDIRECT_ACTION + ret;
            }
        }
        return ret;
    }

    /**
     * Allow to save the changes about the entity at the legal Representatives Page.
     * @param dcoForm
     */
    private boolean doSaveForm5(final FormulaireDCOForm dcoForm, final String retForm) {
        boolean error = false;
        try {

            if (dcoForm.getEntity() != null) {
                // save the list of thirdParties (legal representatives).
                saveThirdParties();

                final EntityDto entity = updateEntity(dcoForm.getEntity());
                dcoForm.setEntity(entity);
                getThirdPartyForm().setEntity(entity);

                addConfirm(this.propertiesHelper.getMessage("page.formulaire.success.saving"));
            } else {
                error = true;
                addWarning(this.propertiesHelper.getMessage(PROP_NO_ENTITY));
            }
        } catch (final DCOException e) {
            error = true;
            if (e.getCode() == Constants.EXCEPTION_CALLING_BUSINESS) {
                addWarning(this.propertiesHelper.getMessage("business.error"));
            }
            addWarning(this.propertiesHelper.getMessage("page.formulaire.success.saving.error"));
        }
        return error;
    }

    /**
     * save third parties.
     */
    private void saveThirdParties() {
        if (getDcoForm().getLegalRepresentativesList() != null) {
            final Iterator<ThirdPartyDto> itRepresentatives = getDcoForm().getLegalRepresentativesList()
                    .iterator();
            final List<ThirdPartyDto> thirdList = new ArrayList<ThirdPartyDto>();
            if (getDcoForm().getEntityRepresentatives().length > Constants.VAR_ZERO) {
                while (itRepresentatives.hasNext()) {
                    final ThirdPartyDto representative = itRepresentatives.next();
                    for (final int id : getDcoForm().getEntityRepresentatives()) {
                        if (representative.getId().compareTo(Integer.valueOf(id)) == Constants.VAR_ZERO) {
                            thirdList.add(representative);
                            break;
                        }
                    }
                }
            }
            getDcoForm().getEntity().setThirdParties(thirdList);
        }
    }

    /**
     * Check the required fields in page5form (legal Representatives): not require (empty list is also valid).
     * @param dcoForm
     * @return the verification
     */
    private boolean checkForm5(final FormulaireDCOForm dcoForm) {
        // allow access only if accounts page is validated (at least one account created).
        
    	boolean error = false;
    	
    	initBreadcrumbs();
  
        if (dcoForm.getEntityRepresentatives().length == 0) {
            addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
            error = true;
            err = true;
        }
        
        return error;
    }

    /***************************************************************************************************************
     ************************************************ ____SUMMARY____ **********************************************
     ***************************************************************************************************************/

    @RequestMapping(value = "/" + WebConstants.FORM_SUMMARY)
    public final String getFormSummary(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        String result = WebConstants.FORM_SUMMARY;
        if (controlBeforeDownload()) {
            // Init country languages if necessary
            for (final AccountDto account : dcoForm.getAccounts()) {
                if (account.getCountryAccount() != null) {
                    final Locale defaultLocale = ParamController.getDefaultLanguage().getLocale();
                    List<Locale> langs = dcoForm.getSummary().getMapCountryLangs()
                            .get(account.getCountryAccount().getLocale().getCountry());
                    // If the map of options has not already been initialized for this country
                    if (langs == null) {
                        // Looking for the list of languages in a country object that has it
                        for (final CountryDto country : dcoForm.getAccountCountries()) {
                            if (country.getId().equals(account.getCountryAccount().getId())) {
                                langs = new ArrayList<Locale>();
                                // Adding the default language
                                langs.add(defaultLocale);
                                if (country.getLanguages() != null) {
                                    for (final LanguageDto language : country.getLanguages()) {
                                        // Adding the language if not the default (already added)
                                        if (!defaultLocale.getLanguage()
                                                .equals(language.getLocale().getLanguage())) {
                                            langs.add(language.getLocale());
                                        }
                                    }
                                }
                                dcoForm.getSummary().getMapCountryLangs()
                                        .put(account.getCountryAccount().getLocale().getCountry(), langs);
                                break;
                            }
                        }
                    }
                    // If the selected language for the country is not in the list, we add an entry
                    if (!dcoForm.getSummary().getMapCountrySelectedLang()
                            .containsKey(account.getCountryAccount().getLocale().getCountry())) {
                        dcoForm.getSummary()
                                .getMapCountrySelectedLang()
                                .put(account.getCountryAccount().getLocale().getCountry(),
                                        defaultLocale.getLanguage());
                    }
                }
            }
            // Init documents lists
            final String entityCountry = UserHelper.getUserInSession().getLocaleEntity().getCountry();
            final Map<Locale, List<DocumentDto>> mapCountryDocs = new TreeMap<Locale, List<DocumentDto>>(
                    new Comparator<Locale>() {
                        @Override
                        public int compare(final Locale locale1, final Locale locale2) {
                            return locale1.getCountry().compareToIgnoreCase(locale2.getCountry());
                        }
                    });
            try {
                int docCountryId = Constants.VAR_NEG_ONE;
                int docCommonSupportingId = Constants.VAR_NEG_ONE;
                if (entityCountry != null) {
                    final List<CountryDto> countryDtoList = (List<CountryDto>) BusinessHelper.call(
                            Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null);
                    for (final CountryDto country : countryDtoList) {
                        if (country.getLocale().getCountry().equalsIgnoreCase(entityCountry)) {
                            docCountryId = country.getId();
                        }
                    }
                    final List<DocumentTypeDto> documentTypeList = (List<DocumentTypeDto>) BusinessHelper.call(
                            Constants.CONTROLLER_DOCUMENT_TYPE, Constants.CONTROLLER_LIST, new Object[] {});
                    for (final DocumentTypeDto documentType : documentTypeList) {
                        if (Constants.PARAM_TYPE_DOC_COMMON_SUPPORTING.equalsIgnoreCase(documentType.getLabel())) {
                            docCommonSupportingId = documentType.getId();
                            break;
                        }
                    }
                }
                for (final AccountDto account : dcoForm.getAccounts()) {
                    if (account.getId() != null && account.getAccountThirdPartyList() != null
                            && account.getCountryAccount() != null) {
                        for (final AccountThirdPartyDto atp : account.getAccountThirdPartyList()) {
                            if (atp.getThirdParty() != null) {
                                boolean resident = false;
                                if (entityCountry.equals(account.getCountryAccount().getLocale().getCountry())) {
                                    resident = true;
                                }
                                final String selectedLang = dcoForm.getSummary().getMapCountrySelectedLang()
                                        .get(account.getCountryAccount().getLocale().getCountry());
                                Integer selectedLangId = null;
                                if (StringUtils.isNotBlank(selectedLang) && dcoForm.getAccountCountries() != null) {
                                    for (final CountryDto country : dcoForm.getAccountCountries()) {
                                        if (country.getId().equals(account.getCountryAccount().getId())) {
                                            if (country.getLanguages() != null) {
                                                for (final LanguageDto language : country.getLanguages()) {
                                                    if (selectedLang.equals(language.getLocale().getLanguage())) {
                                                        selectedLangId = language.getId();
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                mapCountryDocs.put(account.getCountryAccount().getLocale(),
                                        (List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                                                Constants.CONTROLLER_LIST, new Object[] {
                                                        account.getCountryAccount().getId(), selectedLangId,
                                                        Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE, null,
                                                        new Integer[0], resident, true, UserHelper.getUserInSession().isXbasV2()}));
                                if (Constants.VAR_NEG_ONE != docCountryId
                                        && Constants.VAR_NEG_ONE != docCommonSupportingId && !resident) {
                                    mapCountryDocs.get(account.getCountryAccount().getLocale()).addAll(
                                            (List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                                                    Constants.CONTROLLER_LIST,
                                                    new Object[] {
                                                            docCountryId,
                                                            account.getCommunicationLanguage() != null ? account
                                                                    .getCommunicationLanguage().getId()
                                                                    : selectedLangId, docCommonSupportingId,
                                                            Constants.VAR_NEG_ONE, null, null, null, true, UserHelper.getUserInSession().isXbasV2()}));
                                }
                                break;
                            }
                        }
                    } else {
                        addError(this.propertiesHelper.getMessage("page.user.error"));
                        // Returning to the Accounts Page (data integrity error occurred on accounts)
                        result = WebConstants.FORMULAIRE3;
                        break;
                    }
                }
                dcoForm.setMapCountryDocs(mapCountryDocs);
                dcoForm.initMapCountrySignatureCard();
            } catch (final DCOException e) {
                LOG.error("An error occurred while loading the documents before downloading", e);
                addError(this.propertiesHelper.getMessage("page.technical.error"));
                // Returning to the calling page
                result = WebConstants.FORMULAIRE4;
            }
        } else {
            // The error messages have been added by the control method
            // Returning to the calling page
            result = WebConstants.FORMULAIRE4;
        }
        return result;
    }

    @RequestMapping(value = "/backToSummary", method = RequestMethod.POST)
    public final String backToSummary(@ModelAttribute("formulaireDCOForm") final FormulaireDCOForm dcoForm) {
        return WebConstants.FORM_SUMMARY;
    }

    /***************************************************************************************************************
     ******************************************** ____OTHER___ *****************************************************
     **************************************************************************************************************/

    /**
     * Call all the init function
     * @throws DCOException
     */
    private void resetForm() throws DCOException {
        // Reload the entity with the accounts
        final EntityDto entity = (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                Constants.CONTROLLER_ENTITIES_GET_BY_USER_LOGIN, new Object[] {UserHelper.getUserInSession()
                        .getLogin()});
        if (entity != null) {
            if (StringUtils.isBlank(entity.getRegistrationCountry()) && entity.getCountry() != null) {
                // default of fiscal country!
                entity.setRegistrationCountry(entity.getCountry().getCountry());
            }
            getDcoForm().setEntity(entity);
            initThirdPartyForm(entity);
            initAccounts();
        }
        getDcoForm().setAccountStatementTypesForNewAccount(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY);
        getDcoForm().setSelectedCountryForNewAccount(StringUtils.EMPTY);
    }

    /**
     * initialize the vector Breadcrumbs {0>>form1, 1>>thirdParty, 2>>form3, 3>>form4, 4>>form5, 5>>formSummmary}
     */
    private void initBreadcrumbs() {
        final Boolean[] tabPageDisplay = new Boolean[6];
        tabPageDisplay[0] = Boolean.TRUE;
        tabPageDisplay[1] = Boolean.FALSE;
        tabPageDisplay[2] = Boolean.FALSE;
        tabPageDisplay[3] = Boolean.FALSE;
        tabPageDisplay[4] = Boolean.FALSE;
        tabPageDisplay[5] = Boolean.FALSE;

        if (tabPageDisplay[0] && checkEntity(getDcoForm().getEntity())) {
            tabPageDisplay[1] = Boolean.TRUE;
        }
        if (tabPageDisplay[1] && getThirdPartyForm().checkNumberThirdParty()) {
            tabPageDisplay[2] = Boolean.TRUE;
        }
        if (tabPageDisplay[2] && getDcoForm().getAccounts() != null && !getDcoForm().getAccounts().isEmpty()) {
            tabPageDisplay[3] = Boolean.TRUE;
        }
        if (tabPageDisplay[3] && getDcoForm().getAccounts() != null) {
            tabPageDisplay[4] = checkAssociations(Boolean.FALSE);
            tabPageDisplay[5] = tabPageDisplay[4];
        }
        getDcoForm().setBreadcrumbs(tabPageDisplay);
    }

    private boolean checkEntity(final EntityDto entity) {
        boolean rslt = false;
        if (entity.getLegalStatus() != null && StringUtils.isNotBlank(entity.getTaxInformation())
                && StringUtils.isNotBlank(entity.getCommercialRegister())
                && StringUtils.isNotBlank(entity.getRegistrationCountry())) {
            rslt = true;
        }
        return rslt;
    }

    /**
     * Method to init the accounts in the form3.
     * @param entity
     * @throws DCOException
     */
    private void initAccounts() throws DCOException {
        // Init the accounts in the form3 Page
        AccountFormDto accountForm;
        if (getDcoForm().getEntity().getAccountsForm() == null
                || getDcoForm().getEntity().getAccountsForm().size() == Constants.VAR_ZERO) {
            accountForm = new AccountFormDto();
            accountForm.setEntity(getDcoForm().getEntity());
            accountForm = (AccountFormDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_FORM,
                    Constants.CONTROLLER_ACCOUNT_FORM_SAVE_ACOUNT_FORM, new Object[] {accountForm});
            final List<AccountFormDto> afList = new ArrayList<AccountFormDto>();
            afList.add(accountForm);
            getDcoForm().getEntity().setAccountsForm(afList);
            getDcoForm().setEntity(updateEntity(getDcoForm().getEntity()));
        } else {
            accountForm = getDcoForm().getEntity().getAccountsForm().get(Constants.VAR_ZERO);
        }

        final AccountDto newAccount = new AccountDto();
        newAccount.setAccountForm(accountForm);
        getDcoForm().setNewAccount(newAccount);
        getDcoForm().setAlert(false);
        getDcoForm().setAccounts(accountForm.getAccountDtoList());
        getDcoForm().mapAccount();

        // Init selected countries
        if (getDcoForm().getAccounts() != null) {
            getDcoForm().setSelectedCountries(new ArrayList<String>());
            for (int i = 0; i < getDcoForm().getAccounts().size(); i++) {
                if (getDcoForm().getAccounts().get(i).getCountryAccount() == null) {
                    getDcoForm().getSelectedCountries().add("");
                } else {
                    getDcoForm().getSelectedCountries().add(
                            getDcoForm().getAccounts().get(i).getCountryAccount().getLocale().getCountry());
                }
            }
        }
        getDcoForm().setAccountForm(accountForm);
        // Convert account statement types to beans
        if (getDcoForm().getAccounts() != null) {

            final AccountStatementTypesBean bean = new AccountStatementTypesBean();
            final Map<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
            bean.setMap(map);

            for (final AccountDto account : getDcoForm().getAccounts()) {

                if (account.getStatementTypes() != null) {
                    Integer[] statementTypeList = ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
                    for (final ParamFuncDto statementType : account.getStatementTypes()) {
                        statementTypeList = ArrayUtils.add(statementTypeList, statementType.getId().intValue());
                    }
                    bean.getMap().put(account.getId(), statementTypeList);
                } else {
                    // Account0: id == null
                    bean.getMap().put(null, ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY);
                }
            }
            getDcoForm().setAccountStatementTypesBean(bean);
        }
        // set map (maybe redundancy but no matter): Country with Languages
        setMapCountryComLanguages();
        // enabled!!!
    }

    /**
     * Method to init the contact and the legal representatives for the entity, in the Form2 Page.
     */
    private void initEntityRepresentatives() {
        final List<ThirdPartyDto> representatives = getDcoForm().getEntity().getThirdParties();
        int[] tab = ArrayUtils.EMPTY_INT_ARRAY;
        if (representatives != null) {
            for (final ThirdPartyDto thirdParty : representatives) {
                tab = ArrayUtils.add(tab, thirdParty.getId().intValue());
            }
        }
        getDcoForm().setEntityRepresentatives(tab);
    }

    private EntityDto updateEntity(final EntityDto entity) throws DCOException {
        return (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                Constants.CONTROLLER_ENTITIES_UPDATE, new Object[] {entity});
    }

    /**
     * Method to fetch the map between Country ad Languages enabled!!!
     */
    private void setMapCountryComLanguages() {
        final Map<String, List<AjaxDto>> mapAccountCountryComLangs = getDcoForm().getMapCountryComLangs();

        final Locale locale;

        if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
            locale = UserHelper.getUserInSession().getPreferences().getLocale();
        } else {
            locale = new Locale("en");
        }

        List<CountryDto> countriesComLangs = new ArrayList<CountryDto>();
        try {
            countriesComLangs = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                    Constants.CONTROLLER_LIST, null);
        } catch (final DCOException e) {
            LOG.error("An error occurred while loading parameter communication countries", e);
            addError(this.propertiesHelper.getMessage("page.param.error.loading"));
        }
        if (countriesComLangs != null) {
            for (final CountryDto c : countriesComLangs) {
                final List<AjaxDto> langs = new ArrayList<AjaxDto>();

                if (c.getLanguages() != null) {
                    mapAccountCountryComLangs.put(c.getLocale().getCountry(), langs);
                    for (final LanguageDto lExisting : c.getLanguages()) {
                        final AjaxDto ajaxLang = new AjaxDto(lExisting.getId(), lExisting.getLocale()
                                .getLanguage(), lExisting.getLocale().getDisplayLanguage(locale));
                        langs.add(ajaxLang);
                    }
                }

            }
        }
        getDcoForm().setMapCountryComLangs(mapAccountCountryComLangs);
    }

    /**
     * Fetch the paramFunc list if it doesn't exist in the corresponding map.
     * @param country
     * @param type
     * @param map
     * @throws DCOException
     */
    private void fillParamFuncMap(final String country, final int type, final Map<String, List<ParamFuncDto>> map)
            throws DCOException {
        if (!map.containsKey(country)) {
            // Nothing was found in the map
            final List<String> oneLocaleList = new ArrayList<String>();
            oneLocaleList.add(country);
            final Map<String, List<ParamFuncDto>> paramFuncMap = (Map<String, List<ParamFuncDto>>) BusinessHelper
                    .call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_LOAD_PARAMS_MAP_LANGUAGE,
                            new Object[] {type, oneLocaleList,
                                    UserHelper.getUserInSession().getPreferences().getLanguageId()});

            if (paramFuncMap != null) {
                for (final Entry<String, List<ParamFuncDto>> entry : paramFuncMap.entrySet()) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }

        }
    }

    private boolean checkAssociations(final boolean messageDisplay) {
        boolean result = true;
        boolean found = false;
        int value = 0;
        final String codeJointly = this.propertiesHelper.getMessage("page.formulaire.param.jointly");
        final String codeJointlyAny = this.propertiesHelper.getMessage("page.formulaire.param.jointly.any");
        for (final AccountDto account : getDcoForm().getAccounts()) {
            if (account.getAccountThirdPartyList() == null || account.getAccountThirdPartyList().isEmpty()) {
                continue;
            }
            found = true;
            value = getDcoForm().checkAccountAssociation(codeJointly, codeJointlyAny, account);
            if (value != Constants.VAR_ZERO) {
                break;
            }
        }
        if (!found) {
            result = false;
            if (messageDisplay) {
            	err = true;
                addError(this.propertiesHelper.getMessage("page.formulaire.web.error.missing.association"));
            }
        } else {
            if (Constants.VAR_ONE == value) {
                result = false;
                if (messageDisplay) {
                	err = true;
                    addError(this.propertiesHelper.getMessage("page.formulaire.web.error.missing.association1"));
                }
            } else if (Constants.VAR_TWO == value) {
                result = false;
                if (messageDisplay) {
                	err = true;
                    addError(this.propertiesHelper.getMessage("page.formulaire.web.error.missing.association2"));
                }
            }
        }
        return result;
    }

    private void saveAccounts(final List<AccountDto> accounts, final Integer accountFormId) throws DCOException {
        BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_SAVE, new Object[] {
                accounts, accountFormId});
    }

    /***************************************************************************************************************
     *************************************************** ____PDF____ ***********************************************
     **************************************************************************************************************/

    /**
     * Controller for displaying the help page to the right anchor
     * @param id the id of the anchor
     * @return the built link to redirect to
     */
    @RequestMapping(value = "formDownload")
    public final String formDownload(final HttpServletResponse response,
            @ModelAttribute("formulaireDCOForm") final FormulaireDCOForm form) {
        if (!controlBeforeDownload()) {
            return WebConstants.REDIRECT + WebConstants.FORM_SUMMARY;
        }
        retriveSignatureAuthorisationCode();
        String result = null;
        ServletOutputStream sos = null;
        try {
            final UserSession userSession = UserHelper.getUserInSession();

            // Map holding the PDF beans for each country
            final Map<String, PDFBean> mapCountryBeans = new HashMap<String, PDFBean>();
            final Map<String, Boolean> mapCountryAuthorizedSignatories = new HashMap<String, Boolean>();
            final Map<String, Boolean> mapCountrySignatureCard = new HashMap<String, Boolean>();
            final Map<String, List<PDFBeanAccount>> mapCountryAccountBeans = new HashMap<String, List<PDFBeanAccount>>();
            final Map<Integer, PDFBeanThirdParty> mapBeanThirdParties = new HashMap<Integer, PDFBeanThirdParty>();
            List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
            if (getDcoForm().getAccounts() != null) {
                BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_STATS_PRINTED_DOCS,
                        new Object[] {getDcoForm().getAccounts()});
                // USEFUL TO GET LEGAL ENTITY FOR COUNTRY!
                countryDtoList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                        Constants.CONTROLLER_LIST, null);
            }
            // Generating account third party list -> accounts beans and country beans
            for (final AccountDto account : form.getAccounts()) {
                boolean authorizedSignatoryFlag = false;
                boolean signatureCardFlag = false;
                if (account.getId() != null) {
                    final String accountCountry = account.getCountryAccount().getLocale().getCountry();

                    List<PDFBeanAccount> accountsForCountry = mapCountryAccountBeans.get(accountCountry);
                    if (accountsForCountry == null) {
                        accountsForCountry = new ArrayList<PDFBeanAccount>();
                        mapCountryAccountBeans.put(accountCountry, accountsForCountry);
                    }
                    final PDFBeanAccount accountBean = new PDFBeanAccount();
                    accountBean.setCountry(account.getCountryAccount().getLocale());
                    accountBean.setName(account.getReference());
                    accountBean.setType(account.getTypeAccount() != null ? account.getTypeAccount().getValue()
                            : "");
                    accountBean.setCurrency(account.getCurrency() != null ? account.getCurrency().getValue() : "");
                    accountBean.setStatementPeriodicity(account.getPeriodicity() != null ? account
                            .getPeriodicity().getValue() : "");

                    if (account.getBranchName() != null || account.getAddress() != null
                            || account.getCommercialRegister() != null || account.getVatNumber() != null) {
                        accountBean.setAddress(buildPDFBeanAdress(account.getAddress()));

                        accountBean.setCommercialRegister(account.getCommercialRegister());
                        accountBean.setVatNumber(account.getVatNumber());
                        accountBean.setBranchName(account.getBranchName());
                    }
                    accountBean.setComLangEnabled(account.getCountryAccount().isCom_lang_enabled());
                    accountBean.setCommunicationLanguage(account.getCommunicationLanguage());

                    final List<PDFBeanThirdParty> thirdPartyList = new ArrayList<PDFBeanThirdParty>();
                    if (account.getAccountThirdPartyList() != null) {

                        for (final AccountThirdPartyDto accountThirdParty : account.getAccountThirdPartyList()) {
                            PDFBeanThirdParty thirdPartyBean = getPdfBeanThirdPartyById(thirdPartyList,
                                    accountThirdParty.getThirdParty().getId());
                            if (thirdPartyBean == null) {
                                thirdPartyBean = new PDFBeanThirdParty();
                                thirdPartyBean.setId(accountThirdParty.getThirdParty().getId());
                                thirdPartyBean
                                        .setType(accountThirdParty.getThirdParty().getCorrespondantTypeTwo());
                                thirdPartyBean.setFirstName(accountThirdParty.getThirdParty().getFirstName());
                                thirdPartyBean.setLastName(accountThirdParty.getThirdParty().getName());
                                thirdPartyBean.setPosition(accountThirdParty.getThirdParty().getPositionName());
                                thirdPartyBean.setBirthDate(accountThirdParty.getThirdParty().getBirthDate());
                                thirdPartyBean.setBirthPlace(accountThirdParty.getThirdParty().getBirthPlace());
                                thirdPartyBean.setNationality(accountThirdParty.getThirdParty().getNationality());
                                thirdPartyBean.setCitizenships(buildPDFListCitizenship(accountThirdParty
                                        .getThirdParty().getCitizenships()));
                                thirdPartyBean.setAddressHome(buildPDFBeanAdress(accountThirdParty.getThirdParty()
                                        .getHomeAddress()));
                                thirdPartyBean.setTelephone(accountThirdParty.getThirdParty().getTel());
                                thirdPartyBean.setFax(accountThirdParty.getThirdParty().getFax());
                                thirdPartyBean.setEmail(accountThirdParty.getThirdParty().getMail());
                                thirdPartyBean.setIDNumber(accountThirdParty.getThirdParty().getIdReference());
                                thirdPartyBean.setLegalEntityName(accountThirdParty.getThirdParty()
                                        .getLegalEntityName());
                                thirdPartyBean.setPdfBeanActingDetails(new ArrayList<PDFBeanActingDetails>());
                                thirdPartyList.add(thirdPartyBean);
                            }
                            if (accountThirdParty.getSignatureAuthorization() != null
                                    && accountThirdParty.getPowerType() != null) {
                                final PDFBeanActingDetails actingDetails = new PDFBeanActingDetails();
                                actingDetails.setPowerCode(accountThirdParty.getPowerType());
                                switch (accountThirdParty.getPowerType()) {
                                case Constants.PARAM_CODE_ACC_THIRD_PWR_FR1:
                                    actingDetails.setPower("page.formulaire.account.third.party.power.FR1");
                                    authorizedSignatoryFlag = true;
                                    thirdPartyBean.setFormAOR(true);
                                    break;
                                case Constants.PARAM_CODE_ACC_THIRD_PWR_FR2:
                                    actingDetails.setPower("page.formulaire.account.third.party.power.FR2");
                                    signatureCardFlag = true;
                                    thirdPartyBean.setSignatureCard(true);
                                    break;
                                case Constants.PARAM_CODE_ACC_THIRD_PWR_PT:
                                    actingDetails.setPower("page.formulaire.account.third.party.power.PT");
                                    signatureCardFlag = true;
                                    thirdPartyBean.setSignatureCard(true);
                                    break;
                                case Constants.PARAM_CODE_ACC_THIRD_PWR_ES:
                                    actingDetails.setPower("page.formulaire.account.third.party.power.ES");
                                    signatureCardFlag = true;
                                    thirdPartyBean.setSignatureCard(true);
                                    break;
                                case Constants.PARAM_CODE_ACC_THIRD_PWR_GB:
                                    actingDetails.setPower("page.formulaire.account.third.party.power.GB");
                                    signatureCardFlag = true;
                                    thirdPartyBean.setSignatureCard(true);
                                    break;
                                }

                                actingDetails.setBoardResolutionDate(accountThirdParty.getBoardResolutionDate());
                                actingDetails.setPublicDeedReference(accountThirdParty.getPublicDeedReference());
                                if (accountThirdParty.getStatusAmountLimit() == null) {
                                    actingDetails.setAmountLimit(accountThirdParty.getAmountLimit() == null ? ""
                                            : accountThirdParty.getAmountLimit().toString());
                                } else {
                                    switch (accountThirdParty.getStatusAmountLimit()) {
                                    case Constants.PARAM_CODE_AMOUNT_NO_LIMIT:
                                        actingDetails.setAmountLimit(this.propertiesHelper
                                                .getMessage("page.formulaire.web.message.status.ammount.1"));
                                        break;
                                    case Constants.PARAM_CODE_AMOUNT_LIMIT:
                                    	actingDetails
                                        	.setDeviseAmountLimit(accountThirdParty.getDeviseAmountLimit());
                                        actingDetails
                                                .setAmountLimit(accountThirdParty.getAmountLimit() == null ? ""
                                                        : accountThirdParty.getAmountLimit().toString());
                                        break;
                                    default:
                                        actingDetails
                                                .setAmountLimit(accountThirdParty.getAmountLimit() == null ? ""
                                                        : accountThirdParty.getAmountLimit().toString());
                                        break;
                                    }
                                }
                                final String key = accountThirdParty.getSignatureAuthorization().getEntry();
                                if (key.equals(this.propertiesHelper
                                        .getMessage("page.formulaire.param.individually"))) {
                                    actingDetails.setActingIndivflag(true);
                                } else if (key.equals(this.propertiesHelper
                                        .getMessage("page.formulaire.param.jointly"))) {
                                    actingDetails.setActingJointlyflag(true);
                                    final List<AjaxDto> dtos = new ArrayList<AjaxDto>();
                                    for (final AuthorizationThirdPartyDto atp : accountThirdParty
                                            .getAuthorizedList()) {
                                        ThirdPartyDto tp = null;
                                        for (final ThirdPartyDto temp : getDcoForm().getThirdPartyList()) {
                                            if (temp.getId().equals(atp.getId().getThirdPartyId())) {
                                                tp = temp;
                                                break;
                                            }
                                        }
                                        if (tp != null) {
                                            final AjaxDto ajaxDto = new AjaxDto(tp.getId(), tp.getFirstName(),
                                                    tp.getName());
                                            dtos.add(ajaxDto);
                                        }
                                    }
                                    if (dtos != null) {
                                        actingDetails.setJointThidParty(dtos);
                                    }
                                }
                                thirdPartyBean.getPdfBeanActingDetails().add(actingDetails);
                            }
                        }
                    }
                    accountBean.setAccountThirdPartyList(thirdPartyList);
                    accountsForCountry.add(accountBean);

                    final Boolean as = mapCountryAuthorizedSignatories.get(accountCountry);
                    mapCountryAuthorizedSignatories.put(accountCountry, as == null ? authorizedSignatoryFlag
                            : authorizedSignatoryFlag || as);
                    final Boolean sc = mapCountrySignatureCard.get(accountCountry);
                    mapCountrySignatureCard.put(accountCountry, sc == null ? signatureCardFlag : signatureCardFlag
                            || sc);
                    PDFBean beanForCountry = mapCountryBeans.get(accountCountry);
                    if (beanForCountry == null) {
                        beanForCountry = generateBeanForCountry(userSession, accountCountry, countryDtoList);
                        mapCountryBeans.put(accountCountry, beanForCountry);
                    }
                }
            }
            // Generating third parties -- business logic move to method "generateBeanForCountry" (legal Repres.)
            if (getDcoForm().getThirdPartyList() != null) {
                for (final ThirdPartyDto thirdParty : getDcoForm().getThirdPartyList()) {
                    final PDFBeanThirdParty thirdPartyBean = new PDFBeanThirdParty();
                    thirdPartyBean.setType(thirdParty.getCorrespondantType());
                    thirdPartyBean.setFirstName(thirdParty.getFirstName());
                    thirdPartyBean.setLastName(thirdParty.getName());
                    thirdPartyBean.setPosition(thirdParty.getPositionName());

                    thirdPartyBean.setBirthDate(thirdParty.getBirthDate());
                    thirdPartyBean.setBirthPlace(thirdParty.getBirthPlace());
                    thirdPartyBean.setNationality(thirdParty.getNationality());
                    thirdPartyBean.setCitizenships(buildPDFListCitizenship(thirdParty.getCitizenships()));
                    thirdPartyBean.setAddressHome(buildPDFBeanAdress(thirdParty.getHomeAddress()));
                    thirdPartyBean.setTelephone(thirdParty.getTel());
                    thirdPartyBean.setFax(thirdParty.getFax());
                    thirdPartyBean.setEmail(thirdParty.getMail());
                    thirdPartyBean.setIDNumber(thirdParty.getIdReference());
                    // TDODO: Signature authorization?
                    thirdPartyBean.setActingIndivOrJoin("Oui");

                    thirdPartyBean.setAmountLimit(thirdParty.getAmountLimit());
                    // Setting "accounts" dynamically for each bean
                    mapBeanThirdParties.put(thirdParty.getId(), thirdPartyBean);
                }
            }

            // Generating PDF for all beans
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream(baos);
            for (final Map.Entry<String, PDFBean> entry : mapCountryBeans.entrySet()) {
                final String country = entry.getKey();
                final PDFBean bean = entry.getValue();
                final List<PDFBeanAccount> accounts = mapCountryAccountBeans.get(country);
                bean.setAccounts(accounts);
                bean.setAuthorizedSignatories(mapCountryAuthorizedSignatories.get(country));
                bean.setSignatureCard(mapCountrySignatureCard.get(country));
                bean.setXbasV2(userSession.isXbasV2());

                // Generating PDF
               // final ByteArrayOutputStream pdf = this.pdfGenerator.createPdf(bean);
                final String countryName = new Locale("", country).getDisplayCountry(LocaleUtil
                        .stringToLanguage(getDcoForm().getSummary().getMapCountrySelectedLang().get(country)));
                String fileName = this.pdfGenerator.getFilename(bean);
                fileName = countryName + Constants.SPACE + fileName;
                ZipEntry ze = new ZipEntry(new StringBuilder(countryName).append(File.separator).append(fileName)
                        .append(".pdf").toString());
                zos.putNextEntry(ze);
                //zos.write(pdf.toByteArray());
                // Adding the documents for the country
                for (final Map.Entry<Locale, List<DocumentDto>> entryCountryDocs : getDcoForm()
                        .getMapCountryDocs().entrySet()) {
                    if (entryCountryDocs.getKey().getCountry().equals(country)) {
                        for (final DocumentDto doc : entryCountryDocs.getValue()) {
                            ze = new ZipEntry(new StringBuilder(countryName).append(File.separator)
                                    .append(doc.getTitle()).toString());
                            zos.putNextEntry(ze);
                            zos.write(doc.getData());
                        }
                        break;
                    }
                }
            }
            zos.closeEntry();
            zos.close();
            // Sending response
            sos = response.getOutputStream();
            // Writing the content to the output stream
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Pragma", "private");
            response.setHeader("Expires", "0");
            response.setHeader("Content-Disposition", "attachment;filename=\"Forms.zip\"");
            IOUtils.copy(new ByteArrayInputStream(baos.toByteArray()), sos);
        } catch (final IOException | DCOException e) {
            LOG.error("Error while generating the PDF", e);
            result = WebConstants.FORM_SUMMARY;
        } finally {
            if (sos != null) {
                try {
                    sos.close();
                } catch (final IOException e) {
                    LOG.warn("Error while closing the PDF generation output stream => Proceeding");
                }
            }
        }
        return result;
    }

    private boolean controlBeforeDownload() {
        boolean result = true;
        if (getDcoForm().getAccounts() == null || getDcoForm().getAccounts().isEmpty()) {
            addError(this.propertiesHelper.getMessage("page.form.download.error.no.account"));
            result = false;
        } else {
            boolean hasAssoc = false;
            for (final AccountDto account : getDcoForm().getAccounts()) {
                if (account.getId() != null && account.getAccountThirdPartyList() != null) {
                    for (final AccountThirdPartyDto atp : account.getAccountThirdPartyList()) {
                        if (atp.getThirdParty() != null) {
                            hasAssoc = true;
                            break;
                        }
                    }
                }
                if (hasAssoc) {
                    break;
                }
            }
            if (!hasAssoc) {
                addError(this.propertiesHelper.getMessage("page.form.download.error.no.association"));
                result = false;
            }
        }
        return result;
    }

    private PDFBeanThirdParty getPdfBeanThirdPartyById(final List<PDFBeanThirdParty> beanThirdParties,
            final Integer id) {
        PDFBeanThirdParty beanThirdParty = null;
        if (id != null) {
            for (final PDFBeanThirdParty pdfBeanThirdParty : beanThirdParties) {
                if (pdfBeanThirdParty.getId().equals(id)) {
                    beanThirdParty = pdfBeanThirdParty;
                    break;
                }
            }
        }
        return beanThirdParty;
    }

    private PDFBean generateBeanForCountry(final UserSession userSession, final String country,
            final List<CountryDto> countriesList) throws DCOException {
        final PDFBean bean = new PDFBean();
        bean.setCountry(LocaleUtil.stringToCountry(country));
        bean.setLanguage(LocaleUtil.stringToLanguage(getDcoForm().getSummary().getMapCountrySelectedLang()
                .get(country)));
        bean.setDateFormat(new SimpleDateFormat(UserHelper.getUserInSession().getPreferences().getDateFormat()
                .getLabelLong(), UserHelper.getUserInSession().getPreferences().getLocale()));
        final PDFBeanEntity entity = new PDFBeanEntity();
        bean.setEntity(entity);
        entity.setName(userSession.getEntity());
        // got it from account country
        entity.setCountry(bean.getCountry());
        // retrieve and set legal entity data from countries list
        if (countriesList != null) {
            for (final CountryDto localCountry : countriesList) {
                if (localCountry.getLocale() != null
                        && bean.getEntity().getCountry().getDisplayCountry(bean.getLanguage())
                                .equals(localCountry.getLocale().getDisplayCountry(bean.getLanguage()))) {
                    entity.setLegalEntity(localCountry.getLegalEntity());
                    break;
                }
            }
        }
        // set countries in which the Acc. Opened Request is enabled.
        entity.setCountriesList(countriesList);

        entity.setTaxInfo(getDcoForm().getEntity().getTaxInformation());
        entity.setAddressHeadQuarters(buildPDFBeanAdress(getDcoForm().getEntity().getAddress()));
        entity.setAddressPostal(buildPDFBeanAdress(getDcoForm().getEntity().getAddressMailing()));

        entity.setThirdParty(buildPDFBeanThirdPartyLight(getDcoForm().getEntity().getThirdParty()));
        entity.setThirdParty2(buildPDFBeanThirdPartyLight(getDcoForm().getEntity().getThirdParty2()));

        final List<PDFBeanThirdParty> legalRepresentativesList = new ArrayList<PDFBeanThirdParty>();
        if (getDcoForm().getEntity().getThirdParties() != null) {
            for (final ThirdPartyDto thirdParty : getDcoForm().getEntity().getThirdParties()) {
                legalRepresentativesList.add(buildPDFBeanThirdPartyLight(thirdParty));
            }
        }
        entity.setThirdParties(legalRepresentativesList);

        // set the signatory list on entity bean.
        if (getDcoForm().getTPForATPList() != null && !getDcoForm().getTPForATPList().isEmpty()) {
            final List<PDFBeanThirdParty> thirdSignatoryList = new ArrayList<PDFBeanThirdParty>();
            for (final ThirdPartyDto thirdParty : getDcoForm().getTPForATPList()) {
                thirdSignatoryList.add(buildPDFBeanThirdPartyLight(thirdParty));
            }
            entity.setTPForATPList(thirdSignatoryList);
        }

        final String legalForm = getDcoForm().getEntity().getLegalStatus().getEntry();
        entity.setLegalForm(legalForm.equalsIgnoreCase(Constants.PARAM_TYPE_LEGAL_STATUS_OTHER) ? getDcoForm()
                .getEntity().getLegalStatusOther() : getDcoForm().getEntity().getLegalStatus().getValue());
        entity.setRegistrationNb(getDcoForm().getEntity().getCommercialRegister());
        entity.setRegistrationCountry(LocaleUtil
                .stringToCountry(getDcoForm().getEntity().getRegistrationCountry()));
        entity.setBoardResolutionDate(getDcoForm().getEntity().getBoardResolutionDate());
        entity.setNotaryName(getDcoForm().getEntity().getNotaryName());
        entity.setNotaryCity(getDcoForm().getEntity().getNotaryCity());
        entity.setIssuanceDate(getDcoForm().getEntity().getIssuanceDate());
        entity.setPublicDeedReference(getDcoForm().getEntity().getPublicDeedReference());
        entity.setMercantileInscriptionDate(getDcoForm().getEntity().getMercantileInscriptionDate());
        entity.setMercantileInscriptionNumber(getDcoForm().getEntity().getMercantileInscriptionNumber());
        return bean;
    }

    /**
     * @param beanThirdParty
     * @param thirdParty
     * @return PDFBeanThirdParty
     */
    private PDFBeanThirdParty buildPDFBeanThirdPartyLight(final ThirdPartyDto thirdParty) {
        if (thirdParty != null) {
            final PDFBeanThirdParty beanThirdParty = new PDFBeanThirdParty();
            beanThirdParty.setType(thirdParty.getCorrespondantType());
            beanThirdParty.setFirstName(thirdParty.getFirstName());
            beanThirdParty.setLastName(thirdParty.getName());
            beanThirdParty.setPosition(thirdParty.getPositionName());
            beanThirdParty.setBirthDate(thirdParty.getBirthDate());
            beanThirdParty.setBirthPlace(thirdParty.getBirthPlace());
            beanThirdParty.setNationality(thirdParty.getNationality());
            beanThirdParty.setCitizenships(buildPDFListCitizenship(thirdParty.getCitizenships()));
            beanThirdParty.setAddressHome(buildPDFBeanAdress(thirdParty.getHomeAddress()));
            beanThirdParty.setTelephone(thirdParty.getTel());
            beanThirdParty.setFax(thirdParty.getFax());
            beanThirdParty.setEmail(thirdParty.getMail());
            beanThirdParty.setIDNumber(thirdParty.getIdReference());
            beanThirdParty.setLegalEntityName(thirdParty.getLegalEntityName());
            beanThirdParty.setId(thirdParty.getId());
            return beanThirdParty;
        }
        return null;
    }

    /**
     * @param addressDto
     */
    private PDFBeanAddress buildPDFBeanAdress(final AddressDto addressDto) {
        final PDFBeanAddress pdfBeanAddress = new PDFBeanAddress();
        if (addressDto != null) {
            pdfBeanAddress.setLine1(addressDto.getFieldOne());
            pdfBeanAddress.setLine2(addressDto.getFieldTwo());
            pdfBeanAddress.setLine3(addressDto.getFieldThree());
            pdfBeanAddress.setLine4(addressDto.getFieldFour());
            pdfBeanAddress.setLine5(addressDto.getFieldFive());
            pdfBeanAddress.setLine6(addressDto.getFieldSix());
            pdfBeanAddress.setLine7(addressDto.getFieldSeven());
        }
        return pdfBeanAddress;
    }

    /**
     * @param contact
     * @param thirdPartyTemp
     * @return
     */
    private List<Locale> buildPDFListCitizenship(final List<CitizenshipDto> citizenshipDtos) {
        final List<Locale> citizenships = new ArrayList<Locale>();
        if (citizenshipDtos != null) {
            for (final CitizenshipDto citizenship : citizenshipDtos) {
                citizenships.add(citizenship.getCitizenship().getCountry());
            }
        }
        return citizenships;
    }
}