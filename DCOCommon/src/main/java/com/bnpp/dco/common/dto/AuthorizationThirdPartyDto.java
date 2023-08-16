package com.bnpp.dco.common.dto;

/**
 * AuthorizationThirdPartyDto generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */

public class AuthorizationThirdPartyDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private AuthorizationThirdPartyIdDto id;
    private AccountThirdPartyDto accountThirdParty;

    /**
     * @return the id
     */
    public AuthorizationThirdPartyIdDto getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final AuthorizationThirdPartyIdDto id) {
        this.id = id;
    }

    /**
     * @return the accountThirdParty
     */
    public AccountThirdPartyDto getAccountThirdParty() {
        return this.accountThirdParty;
    }

    /**
     * @param accountThirdParty the accountThirdParty to set
     */
    public void setAccountThirdParty(final AccountThirdPartyDto accountThirdParty) {
        this.accountThirdParty = accountThirdParty;
    }
}
