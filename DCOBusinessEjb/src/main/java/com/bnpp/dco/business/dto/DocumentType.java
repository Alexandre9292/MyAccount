package com.bnpp.dco.business.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DocumentType generated by hbm2java
 */
@Entity
@Table(name = "document_type", catalog = "dco")
public class DocumentType implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String label;
    private Set documents = new HashSet(0);

    public DocumentType() {
    }

    public DocumentType(final Integer id) {
        this.id = id;
    }

    public DocumentType(final String label) {
        this.label = label;
    }

    public DocumentType(final String label, final Set documents) {
        this.label = label;
        this.documents = documents;
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

    @Column(name = "LABEL", nullable = false, length = 45)
    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(final Set documents) {
        this.documents = documents;
    }

}
