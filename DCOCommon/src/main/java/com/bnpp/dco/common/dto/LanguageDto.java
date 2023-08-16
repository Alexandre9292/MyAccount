package com.bnpp.dco.common.dto;

import java.util.List;
import java.util.Locale;

public class LanguageDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Locale locale;
    private boolean userInterface;
    private String commercialMessageLogin;
    private String commercialMessageClient;
    private List<CountryDto> countries;

    public LanguageDto() {
        super();
    }

    public LanguageDto(final Integer id) {
        super();
        this.id = id;
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public boolean isUserInterface() {
        return this.userInterface;
    }

    public void setUserInterface(final boolean userInterface) {
        this.userInterface = userInterface;
    }

    public String getCommercialMessageLogin() {
        return this.commercialMessageLogin;
    }

    public void setCommercialMessageLogin(final String commercialMessageLogin) {
        this.commercialMessageLogin = commercialMessageLogin;
    }

    public String getCommercialMessageClient() {
        return this.commercialMessageClient;
    }

    public void setCommercialMessageClient(final String commercialMessageClient) {
        this.commercialMessageClient = commercialMessageClient;
    }

    public List<CountryDto> getCountries() {
        return this.countries;
    }

    public void setCountries(final List<CountryDto> countries) {
        this.countries = countries;
    }
}
