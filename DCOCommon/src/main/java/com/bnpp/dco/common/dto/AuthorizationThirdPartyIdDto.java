package com.bnpp.dco.common.dto;

/**
 * AuthorizationThirdPartyIdDto generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */

public class AuthorizationThirdPartyIdDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer accountThirdPartyId;
    private Integer thirdPartyId;

    /**
     * @return the accountThirdPartyId
     */
    public Integer getAccountThirdPartyId() {
        return this.accountThirdPartyId;
    }

    /**
     * @param accountThirdPartyId the accountThirdPartyId to set
     */
    public void setAccountThirdPartyId(final Integer accountThirdPartyId) {
        this.accountThirdPartyId = accountThirdPartyId;
    }

    /**
     * @return the thirdPartyId
     */
    public Integer getThirdPartyId() {
        return this.thirdPartyId;
    }

    /**
     * @param thirdPartyId the thirdPartyListId to set
     */
    public void setThirdPartyId(final Integer thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

}
