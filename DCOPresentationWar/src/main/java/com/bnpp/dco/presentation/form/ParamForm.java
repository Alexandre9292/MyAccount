package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.ParamFuncTypeDto;

@Component("paramForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ParamForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 7645434040044097299L;
	
	private List<ParamFuncTypeDto> types;
    private int selectedType;
    private ParamFuncTypeDto currentType;
    private List<CountryDto> countries;
    private List<Locale> allLocales;
    private String selectedCountry;
    private Map<String, List<ParamFuncDto>> parameters;
    private String newEntry;
    private List<LegalEntityDto> entities;
    private List<LanguageDto> languages;
    private List<LanguageDto> languagesCommMess;
    // We cannot put the map directly in the form (because of Spring proxying) => object
    private ParamFormEnableLang enableComLang;
    private ParamFormCountryLang countryLang;
    private ParamFormCountryEntity countryEntity;

    public boolean isStandardAllCountries() {
        switch (this.currentType.getId()) {
        case Constants.PARAM_TYPE_LEGAL_STATUS:
        case Constants.PARAM_TYPE_THIRD_PARTY_POSIT:
        case Constants.PARAM_TYPE_THIRD_PARTY_SIGN_AUTH:
            return true;
        default:
            return false;
        }
    }

    public boolean isStandardBankCountries() {
        switch (this.currentType.getId()) {
        case Constants.PARAM_TYPE_ACCNT_TYPE:
        case Constants.PARAM_TYPE_ACCNT_CURRENCY:
        case Constants.PARAM_TYPE_ACCNT_STATE_PERIOD:
        case Constants.PARAM_TYPE_ACCNT_STATE_SUPPORT:
        case Constants.PARAM_TYPE_ACCNT_INTERAC_LANG:
        case Constants.PARAM_TYPE_ACCNT_PAY_MODE:
            return true;
        default:
            return false;
        }
    }

    public List<ParamFuncTypeDto> getTypes() {
        return this.types;
    }

    public void setTypes(final List<ParamFuncTypeDto> types) {
        this.types = types;
    }

    public int getSelectedType() {
        return this.selectedType;
    }

    public void setSelectedType(final int selectedType) {
        this.selectedType = selectedType;
    }

    public ParamFuncTypeDto getCurrentType() {
        return this.currentType;
    }

    public void setCurrentType(final ParamFuncTypeDto currentType) {
        this.currentType = currentType;
    }

    public List<CountryDto> getCountries() {
        return this.countries;
    }

    public void setCountries(final List<CountryDto> countries) {
        this.countries = countries;
    }

    public List<Locale> getAllLocales() {
        return this.allLocales;
    }

    public void setAllLocales(final List<Locale> allCountries) {
        this.allLocales = allCountries;
    }

    public String getSelectedCountry() {
        return this.selectedCountry;
    }

    public void setSelectedCountry(final String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public Map<String, List<ParamFuncDto>> getParameters() {
        return this.parameters;
    }

    public void setParameters(final Map<String, List<ParamFuncDto>> parameters) {
        this.parameters = parameters;
    }

    public String getNewEntry() {
        return this.newEntry;
    }

    public void setNewEntry(final String newEntry) {
        this.newEntry = newEntry;
    }

    public List<LegalEntityDto> getEntities() {
        return this.entities;
    }

    public void setEntities(final List<LegalEntityDto> entities) {
        this.entities = entities;
    }

    public List<LanguageDto> getLanguages() {
        return this.languages;
    }

    public void setLanguages(final List<LanguageDto> languages) {
        this.languages = languages;
    }

    public List<LanguageDto> getLanguagesCommMess() {
        return this.languagesCommMess;
    }

    public void setLanguagesCommMess(final List<LanguageDto> languagesCommMess) {
        this.languagesCommMess = languagesCommMess;
    }

    public ParamFormCountryLang getCountryLang() {
        return this.countryLang;
    }

    public void setCountryLang(final ParamFormCountryLang countryLang) {
        this.countryLang = countryLang;
    }

    public ParamFormCountryEntity getCountryEntity() {
        return this.countryEntity;
    }

    public void setCountryEntity(final ParamFormCountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    /**
     * @return the enableComLang
     */
    public ParamFormEnableLang getEnableComLang() {
        return this.enableComLang;
    }

    /**
     * @param enableComLang the enableComLang to set
     */
    public void setEnableComLang(final ParamFormEnableLang enableComLang) {
        this.enableComLang = enableComLang;
    }

}
