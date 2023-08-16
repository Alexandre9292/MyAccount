package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.dto.DocumentTypeDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("documentFilterForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentFilterForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -1565993630841330441L;
	
	/*
     * Set the attributes allow the form to be initialize when we need it from a method, witch have another form in
     * parameter.
     */
    protected int country = -1;
    protected int language = -1;
    protected int documentType = -1;
    protected int legalEntity = -1;
    protected String documentTitle = StringUtils.EMPTY;
    protected String documentTitleWithoutExt = StringUtils.EMPTY;
    protected String documentExtension = StringUtils.EMPTY;
    private boolean filterHidden;
    protected int id = -1;
    protected boolean resident = false;
    protected boolean xbasV2 = false;

    private List<LanguageDto> languageList;
    private List<CountryDto> countryList;
    private List<AjaxDto> countrySort;
    private List<DocumentTypeDto> documentTypeList;
    private List<DocumentDto> documentList;
    private List<LegalEntityDto> legalEntityList;

    public int getCountry() {
        return this.country;
    }

    public void setCountry(final int country) {
        this.country = country;
    }

    public int getLanguage() {
        return this.language;
    }

    public void setLanguage(final int language) {
        this.language = language;
    }

    public int getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(final int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTitle() {
        return this.documentTitle;
    }

    public void setDocumentTitle(final String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentTitleWithoutExt() {
        return this.documentTitleWithoutExt;
    }

    public void setDocumentTitleWithoutExt(final String documentTitleWithoutExt) {
        this.documentTitleWithoutExt = documentTitleWithoutExt;
    }

    public boolean isFilterHidden() {
        return this.filterHidden;
    }

    public void setFilterHidden(final boolean filterHidden) {
        this.filterHidden = filterHidden;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public List<LanguageDto> getLanguageList() {
        return this.languageList;
    }

    public void setLanguageList(final List<LanguageDto> languageList) {
        this.languageList = languageList;
    }

    public List<CountryDto> getCountryList() {
        return this.countryList;
    }

    public void setCountryList(final List<CountryDto> countryList) {

        if (countryList != null) {
            final List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();

            final Locale locale;

            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                    && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
                locale = UserHelper.getUserInSession().getPreferences().getLocale();
            } else {
                locale = new Locale("en");
            }

            for (final CountryDto c : countryList) {
                // add legal entity id as login field into ajaxDto : useful to auto-select legalEntity on Doc page
                final AjaxDto a = new AjaxDto(c.getId(), c.getLocale().getDisplayCountry(locale), String.valueOf(c
                        .getLegalEntity() == null ? "" : c.getLegalEntity().getId()));
                ajaxList.add(a);
            }

            Collections.sort(ajaxList, new AjaxDto.OrderByLabel());

            this.countrySort = ajaxList;
        }
        this.countryList = countryList;
    }

    public List<DocumentTypeDto> getDocumentTypeList() {
        return this.documentTypeList;
    }

    public void setDocumentTypeList(final List<DocumentTypeDto> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }

    public List<DocumentDto> getDocumentList() {
        return this.documentList;
    }

    public void setDocumentList(final List<DocumentDto> documentList) {
        this.documentList = documentList;
    }

    public List<LegalEntityDto> getLegalEntityList() {
        return this.legalEntityList;
    }

    public void setLegalEntityList(final List<LegalEntityDto> legalEntityList) {
        this.legalEntityList = legalEntityList;
    }

    public int getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(final int legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<AjaxDto> getCountrySort() {
        return this.countrySort;
    }

    public void setCountrySort(final List<AjaxDto> countrySort) {
        this.countrySort = countrySort;
    }

    public boolean isResident() {
        return this.resident;
    }

    public void setResident(final boolean resident) {
        this.resident = resident;
    }

    /**
     * @return the documentExtension
     */
    public String getDocumentExtension() {
        return this.documentExtension;
    }

    /**
     * @param documentExtension the documentExtension to set
     */
    public void setDocumentExtension(final String documentExtension) {
        this.documentExtension = documentExtension;
    }

	public boolean isXbasV2() {
		return xbasV2;
	}

	public void setXbasV2(final boolean xbasV2) {
		this.xbasV2 = xbasV2;
	}
}
