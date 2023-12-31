package com.bnpp.dco.business.dto;

// Generated 2 oct. 2013 18:13:46 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Statistics generated by hbm2java
 */
@Entity
@Table(name = "statistics", catalog = "dco")
public class Statistics implements java.io.Serializable {

    private Integer id;
    private DocumentType documentType;
    private User user;
    private Country country;
    private Language language;
    private LegalEntity legalEntity;
    private int typeStat;
    private Integer number;

    public Statistics() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_DOC")
    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(final DocumentType documentType) {
        this.documentType = documentType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER")
    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_DOC")
    public Country getCountry() {
        return this.country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LANGUAGE_DOC")
    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEGAL_ENTITY_DOC")
    public LegalEntity getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(final LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    @Column(name = "TYPE_STAT", nullable = false)
    public int getTypeStat() {
        return this.typeStat;
    }

    public void setTypeStat(final int typeStat) {
        this.typeStat = typeStat;
    }

    @Column(name = "NUMBER", nullable = false)
    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

}
