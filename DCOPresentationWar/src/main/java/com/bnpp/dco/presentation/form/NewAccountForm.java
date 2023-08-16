package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.CountryDto;

@Component("newAccountForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NewAccountForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 8364755885721140644L;

	@NotEmpty
    private String userName;

    @NotEmpty
    private String emailClient;

    @NotEmpty
    private String entity;

    private String country;

    private List<CountryDto> countryEntitiesList;
    private List<AjaxDto> countrySort;

    public void reset() {
        setUserName("");
        setEmailClient("");
        setEntity("");
        setCountry(null);
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getEmailClient() {
        return this.emailClient;
    }

    public void setEmailClient(final String emailClient) {
        this.emailClient = emailClient;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(final String entity) {
        this.entity = entity;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public List<CountryDto> getCountryEntitiesList() {
        return this.countryEntitiesList;
    }

    public void setCountryEntitiesList(final List<CountryDto> countryEntitiesList) {
        this.countryEntitiesList = countryEntitiesList;
    }

    public void setCountryEntitiesList(final List<CountryDto> countryEntitiesList, final Locale defaultLocale) {
        if (countryEntitiesList != null) {
            final List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();
            Locale locale;
            if (defaultLocale == null || defaultLocale.getCountry().isEmpty()) {
                locale = new Locale("en");
            } else {
                locale = defaultLocale;
            }

            for (final CountryDto c : countryEntitiesList) {
                final AjaxDto a = new AjaxDto(c.getLocale().getDisplayCountry(locale), c.getLocale().getCountry());
                ajaxList.add(a);
            }
            Collections.sort(ajaxList, new AjaxDto.OrderByLabel());
            this.countrySort = ajaxList;
        }
        this.countryEntitiesList = countryEntitiesList;
    }

    public List<AjaxDto> getCountrySort() {
        return this.countrySort;
    }

    public void setCountrySort(final List<AjaxDto> countrySort) {
        this.countrySort = countrySort;
    }

}
