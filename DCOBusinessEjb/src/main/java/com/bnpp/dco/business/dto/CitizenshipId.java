package com.bnpp.dco.business.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CitizenshipId generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */
@Embeddable
public class CitizenshipId implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer signatoryId;
    private String country;
    private String value;

    public CitizenshipId() {
    }

    public CitizenshipId(final Integer signatoryId, final String country, final String value) {
        this.signatoryId = signatoryId;
        this.country = country;
        this.value = value;
    }

    @Column(name = "signatory_ID", nullable = false)
    public Integer getSignatoryId() {
        return this.signatoryId;
    }

    public void setSignatoryId(final Integer signatoryId) {
        this.signatoryId = signatoryId;
    }

    @Column(name = "COUNTRY", nullable = false, length = 50)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Column(name = "VALUE", nullable = false, length = 50)
    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof CitizenshipId)) {
            return false;
        }
        final CitizenshipId castOther = (CitizenshipId) other;

        return this.getSignatoryId().equals(castOther.getSignatoryId())
                && (this.getCountry().equals(castOther.getCountry()) || this.getCountry() != null
                        && castOther.getCountry() != null && this.getCountry().equals(castOther.getCountry()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSignatoryId();
        result = 37 * result + (getCountry() == null ? 0 : this.getCountry().hashCode());
        return result;
    }
}
