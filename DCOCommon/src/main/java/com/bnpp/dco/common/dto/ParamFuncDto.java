package com.bnpp.dco.common.dto;

public class ParamFuncDto implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String country;
    private LanguageDto language;
    private ParamFuncTypeDto paramFuncType;
    private String entry;
    private String value;

    public ParamFuncDto() {
    }

    public ParamFuncDto(final String country, final LanguageDto language, final ParamFuncTypeDto paramFuncType,
            final String value) {
        this.country = country;
        this.language = language;
        this.paramFuncType = paramFuncType;
        this.value = value;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public LanguageDto getLanguage() {
        return this.language;
    }

    public void setLanguage(final LanguageDto language) {
        this.language = language;
    }

    public ParamFuncTypeDto getParamFuncType() {
        return this.paramFuncType;
    }

    public void setParamFuncType(final ParamFuncTypeDto paramFuncType) {
        this.paramFuncType = paramFuncType;
    }

    public String getEntry() {
        return this.entry;
    }

    public void setEntry(final String entry) {
        this.entry = entry;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
