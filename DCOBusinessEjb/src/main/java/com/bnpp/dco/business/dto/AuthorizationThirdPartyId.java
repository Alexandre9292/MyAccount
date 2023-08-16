package com.bnpp.dco.business.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AuthorizationThirdPartyId generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */
@Embeddable
public class AuthorizationThirdPartyId implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer thirdId;

    public AuthorizationThirdPartyId() {

    }

    public AuthorizationThirdPartyId(final Integer id, final Integer thirdId) {
        this.id = id;
        this.thirdId = thirdId;
    }

    @Column(name = "ATP_ID", nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Column(name = "THIRD_PARTY_ID", nullable = false)
    public Integer getThirdId() {
        return this.thirdId;
    }

    public void setThirdId(final Integer thirdId) {
        this.thirdId = thirdId;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof AuthorizationThirdPartyId)) {
            return false;
        }
        final AuthorizationThirdPartyId castOther = (AuthorizationThirdPartyId) other;

        return this.getId() == castOther.getId()
                && (this.getThirdId().equals(castOther.getThirdId()) || this.getThirdId() != null
                        && castOther.getThirdId() != null && this.getThirdId().equals(castOther.getThirdId()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getId();
        result = 37 * result + (getThirdId() == null ? 0 : this.getThirdId().hashCode());
        return result;
    }

}
