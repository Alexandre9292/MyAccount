package com.bnpp.dco.business.dto;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Citizenship generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */

@Entity
@Table(name = "citizenship", catalog = "dco")
public class Citizenship implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private CitizenshipId id;
    private Signatory signatory;

    public Citizenship() {
    }

    public Citizenship(final CitizenshipId id, final Signatory signatory) {
        this.id = id;
        this.signatory = signatory;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "signatoryId", column = @Column(name = "signatory_ID", nullable = false)),
            @AttributeOverride(name = "country", column = @Column(name = "COUNTRY", nullable = false, length = 50)),
            @AttributeOverride(name = "value", column = @Column(name = "VALUE", nullable = false, length = 50))})
    public CitizenshipId getId() {
        return this.id;
    }

    public void setId(final CitizenshipId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signatory_ID", nullable = false, insertable = false, updatable = false)
    public Signatory getSignatory() {
        return this.signatory;
    }

    public void setSignatory(final Signatory signatory) {
        this.signatory = signatory;
    }

}
