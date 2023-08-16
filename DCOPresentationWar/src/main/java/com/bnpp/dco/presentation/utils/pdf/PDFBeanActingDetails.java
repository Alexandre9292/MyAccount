package com.bnpp.dco.presentation.utils.pdf;

import java.util.Date;
import java.util.List;

import com.bnpp.dco.common.dto.AjaxDto;

/**
 * * Bean for PDF holding the authorizes signatories acting details.
 */
public class PDFBeanActingDetails {
    /** Field acting Jointly flag */
    private boolean actingIndivflag = false;
    /** Field acting Jointly flag */
    private boolean actingJointlyflag = false;
    /** Field powerCode. */
    private Integer powerCode;
    /** Field power. */
    private String power;
    /** Field dated. */
    private Date boardResolutionDate;
    /** Field publicDeedReference. */
    private String publicDeedReference;
    /** Field amountLimit. */
    private String deviseAmountLimit;
    private String amountLimit;
    /** Field list of third party use label and login. */
    private List<AjaxDto> jointThidParty;

    /**
     * @return the actingIndivflag
     */
    public boolean getActingIndivflag() {
        return this.actingIndivflag;
    }

    /**
     * @param actingIndivflag the actingIndivflag to set
     */
    public void setActingIndivflag(final boolean actingIndivflag) {
        this.actingIndivflag = actingIndivflag;
    }

    /**
     * @return the actingJointlyflag
     */
    public boolean getActingJointlyflag() {
        return this.actingJointlyflag;
    }

    /**
     * @param actingJointlyflag the actingJointlyflag to set
     */
    public void setActingJointlyflag(final boolean actingJointlyflag) {
        this.actingJointlyflag = actingJointlyflag;
    }

    /**
     * @return the powerCode
     */
    public Integer getPowerCode() {
        return this.powerCode;
    }

    /**
     * @param powerCode the powerCode to set
     */
    public void setPowerCode(final Integer powerCode) {
        this.powerCode = powerCode;
    }

    /**
     * @return the power
     */
    public String getPower() {
        return this.power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(final String power) {
        this.power = power;
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
     * @return the amountLimit
     */
    public String getAmountLimit() {
        return this.amountLimit;
    }

    /**
     * @param amountLimit the amountLimit to set
     */
    public void setAmountLimit(final String amountLimit) {
        this.amountLimit = amountLimit;
    }

    /**
     * @return the jointThidParty
     */
    public List<AjaxDto> getJointThidParty() {
        return this.jointThidParty;
    }

    /**
     * @param jointThidParty the jointThidParty to set
     */
    public void setJointThidParty(final List<AjaxDto> jointThidParty) {
        this.jointThidParty = jointThidParty;
    }

	public String getDeviseAmountLimit() {
		return deviseAmountLimit;
	}

	public void setDeviseAmountLimit(String deviseAmountLimit) {
		this.deviseAmountLimit = deviseAmountLimit;
	}

}
