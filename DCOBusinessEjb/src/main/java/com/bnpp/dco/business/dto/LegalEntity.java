package com.bnpp.dco.business.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * LegalEntity generated by hbm2java
 */
@Entity
@Table(name = "legal_entity", catalog = "dco")
public class LegalEntity implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String label;
    private Set countries = new HashSet(0);
    private Set documents = new HashSet(0);
    private Set users = new HashSet(0);
    private Address idAddress;

    public LegalEntity() {
    }

    public LegalEntity(final String label) {
        this.label = label;
    }

    public LegalEntity(final String label, final Set countries, final Set documents, final Set users) {
        this.label = label;
        this.countries = countries;
        this.documents = documents;
        this.users = users;
    }

    public LegalEntity(final Integer id) {
        this.id = id;
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

    @Column(name = "LABEL", nullable = false, length = 50)
    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "legalEntity")
    public Set<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(final Set countries) {
        this.countries = countries;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "legalEntity")
    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(final Set documents) {
        this.documents = documents;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_legal_entity", catalog = "dco", joinColumns = {@JoinColumn(name = "LEGAL_ENTITY", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "USER", nullable = false, updatable = false)})
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(final Set users) {
        this.users = users;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ADDRESS")
    public Address getIdAddress() {
        return this.idAddress;
    }

    /**
     * @param idAddress the idAddress to set
     */
    public void setIdAddress(final Address idAddress) {
        this.idAddress = idAddress;
    }

}
