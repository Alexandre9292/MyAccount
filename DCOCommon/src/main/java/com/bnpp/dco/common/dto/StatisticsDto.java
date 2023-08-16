package com.bnpp.dco.common.dto;

public class StatisticsDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private DocumentTypeDto documentType;
    private UserDto user;
    private CountryDto country;
    private LanguageDto language;
    private LegalEntityDto legalEntity;
    private int typeStat;
    private Integer number;

    public StatisticsDto() {
        super();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public DocumentTypeDto getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(final DocumentTypeDto documentType) {
        this.documentType = documentType;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(final UserDto user) {
        this.user = user;
    }

    public CountryDto getCountry() {
        return this.country;
    }

    public void setCountry(final CountryDto country) {
        this.country = country;
    }

    public LanguageDto getLanguage() {
        return this.language;
    }

    public void setLanguage(final LanguageDto language) {
        this.language = language;
    }

    public LegalEntityDto getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(final LegalEntityDto legalEntity) {
        this.legalEntity = legalEntity;
    }

    public int getTypeStat() {
        return this.typeStat;
    }

    public void setTypeStat(final int typeStat) {
        this.typeStat = typeStat;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

}
