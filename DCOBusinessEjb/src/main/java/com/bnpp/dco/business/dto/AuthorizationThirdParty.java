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
 * AuthorizationThirdParty generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */
@Entity
@Table(name = "authorization_third_party", catalog = "dco")
public class AuthorizationThirdParty implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private AuthorizationThirdPartyId id;
    private AccountThirdParty accountThirdParty;

    public AuthorizationThirdParty() {

    }

    public AuthorizationThirdParty(final AuthorizationThirdPartyId id, final AccountThirdParty accountThirdParty) {
        this.id = id;
        this.accountThirdParty = accountThirdParty;
    }

    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "ATP_ID", nullable = false)),
            @AttributeOverride(name = "thirdId", column = @Column(name = "THIRD_PARTY_ID", nullable = false))})
    public AuthorizationThirdPartyId getId() {
        return this.id;
    }

    public void setId(final AuthorizationThirdPartyId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATP_ID", nullable = false, insertable = false, updatable = false)
    public AccountThirdParty getAccountThirdParty() {
        return this.accountThirdParty;
    }

    public void setAccountThirdParty(final AccountThirdParty accountThirdParty) {
        this.accountThirdParty = accountThirdParty;
    }

}
