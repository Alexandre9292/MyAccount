package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AccountThirdPartyDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private AccountDto account;
    private ThirdPartyDto thirdParty;
    private Long amountLimit;
    private ParamFuncDto signatureAuthorization;
    private Integer id;
    private List<AuthorizationThirdPartyDto> authorizedList;

    private Integer statusAmountLimit;
    private String deviseAmountLimit;
    private Integer powerType;
    private String publicDeedReference;
    private Date boardResolutionDate;
    
    private SignatoryDto signatory;

    /**
     * @return the account
     */
    public AccountDto getAccount() {
        return this.account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(final AccountDto account) {
        this.account = account;
    }

    /**
     * @return the thirdParty
     */
    public ThirdPartyDto getThirdParty() {
        return this.thirdParty;
    }

    /**
     * @param thirdParty the thirdParty to set
     */
    public void setThirdParty(final ThirdPartyDto thirdParty) {
        this.thirdParty = thirdParty;
    }

    /**
     * @return the amountLimit
     */
    public Long getAmountLimit() {
        return this.amountLimit;
    }

    /**
     * @param amountLimit the amountLimit to set
     */
    public void setAmountLimit(final Long amountLimit) {
        this.amountLimit = amountLimit;
    }

    /**
     * @return the signatureAuthorization
     */
    public ParamFuncDto getSignatureAuthorization() {
        return this.signatureAuthorization;
    }

    /**
     * @param signatureAuthorization the signatureAuthorization to set
     */
    public void setSignatureAuthorization(final ParamFuncDto signatureAuthorization) {
        this.signatureAuthorization = signatureAuthorization;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the statusAmountLimit
     */
    public Integer getStatusAmountLimit() {
        return this.statusAmountLimit;
    }

    /**
     * @param statusAmountLimit the statusAmountLimit to set
     */
    public void setStatusAmountLimit(final Integer statusAmountLimit) {
        this.statusAmountLimit = statusAmountLimit;
    }

    /**
     * @return the powerType
     */
    public Integer getPowerType() {
        return this.powerType;
    }

    /**
     * @param powerType the powerType to set
     */
    public void setPowerType(final Integer powerType) {
        this.powerType = powerType;
    }

    /**
     * @return the publicDeedReference
     */
    public String getPublicDeedReference() {
        return this.publicDeedReference;
    }

    /**
     * @param publicDeedReference the publicDeedReference to set
     */
    public void setPublicDeedReference(final String publicDeedReference) {
        this.publicDeedReference = publicDeedReference;
    }

    /**
     * @return the boardResolutionDate
     */
    public Date getBoardResolutionDate() {
        return this.boardResolutionDate;
    }

    /**
     * @param boardResolutionDate the boardResolutionDate to set
     */
    public void setBoardResolutionDate(final Date boardResolutionDate) {
        this.boardResolutionDate = boardResolutionDate;
    }

    /**
     * @return the authorizedList
     */
    public List<AuthorizationThirdPartyDto> getAuthorizedList() {
        return this.authorizedList;
    }

    /**
     * @param authorizedList the authorizedList to set
     */
    public void setAuthorizedList(final List<AuthorizationThirdPartyDto> authorizedList) {
        this.authorizedList = authorizedList;
    }

	public String getDeviseAmountLimit() {
		return deviseAmountLimit;
	}

	public void setDeviseAmountLimit(String deviseAmountLimit) {
		this.deviseAmountLimit = deviseAmountLimit;
	}

	/**
	 * @return the signatory
	 */
	public SignatoryDto getSignatory() {
		return signatory;
	}

	/**
	 * @param signatory the signatory to set
	 */
	public void setSignatory(SignatoryDto signatory) {
		this.signatory = signatory;
	}

}
