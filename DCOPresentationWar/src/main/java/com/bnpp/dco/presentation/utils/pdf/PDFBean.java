package com.bnpp.dco.presentation.utils.pdf;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Bean holding all the information for the PDF creation.
 */
public class PDFBean {
    /** Field country. */
    private Locale country;
    /** Field language. */
    private Locale language;
    /** Field dateFormat. */
    private SimpleDateFormat dateFormat;

    /** Field entity. */
    private PDFBeanEntity entity;
    /** Field accounts. */
    private List<PDFBeanAccount> accounts;
    /** Field accounts. */
    private List<PDFBeanThirdParty> thirdParties;
    /** Field accounts. */
    private Boolean authorizedSignatories = false;
    /** Field accounts. */
    private Boolean signatureCard = false;
    
    /** Field Xbas V2, to know which properties to load. */
    private Boolean xbasV2;
    
    private List<PDFBeanSignatory> signatoryList;

    /**
     * Getter for country.
     * @return the country
     */
    public Locale getCountry() {
        return this.country;
    }

    /**
     * Setter for country.
     * @param country the country to set
     */
    public void setCountry(final Locale country) {
        this.country = country;
    }

    /**
     * Getter for language.
     * @return the language
     */
    public Locale getLanguage() {
        return this.language;
    }

    /**
     * Setter for language.
     * @param language the language to set
     */
    public void setLanguage(final Locale language) {
        this.language = language;
    }

    /**
     * Getter for entity.
     * @return the entity
     */
    public PDFBeanEntity getEntity() {
        return this.entity;
    }

    /**
     * Setter for entity.
     * @param entity the entity to set
     */
    public void setEntity(final PDFBeanEntity entity) {
        this.entity = entity;
    }

    /**
     * Getter for accounts.
     * @return the accounts
     */
    public List<PDFBeanAccount> getAccounts() {
        return this.accounts;
    }

    /**
     * Setter for accounts.
     * @param accounts the accounts to set
     */
    public void setAccounts(final List<PDFBeanAccount> accounts) {
        this.accounts = accounts;
    }

    /**
     * Getter for thirdParties.
     * @return the thirdParties
     */
    public List<PDFBeanThirdParty> getThirdParties() {
        return this.thirdParties;
    }

    /**
     * Setter for thirdParties.
     * @param thirdParties the thirdParties to set
     */
    public void setThirdParties(final List<PDFBeanThirdParty> thirdParties) {
        this.thirdParties = thirdParties;
    }

    /**
     * Getter for dateFormat.
     * @return the dateFormat
     */
    public SimpleDateFormat getDateFormat() {
        return this.dateFormat;
    }

    /**
     * Setter for dateFormat.
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(final SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the authorizedSignatories
     */
    public Boolean getAuthorizedSignatories() {
        return this.authorizedSignatories;
    }

    /**
     * @param authorizedSignatories the authorizedSignatories to set
     */
    public void setAuthorizedSignatories(final Boolean authorizedSignatories) {
        this.authorizedSignatories = authorizedSignatories;
    }

    /**
     * @return the signatureCard
     */
    public Boolean getSignatureCard() {
        return this.signatureCard;
    }

    /**
     * @param signatureCard the signatureCard to set
     */
    public void setSignatureCard(final Boolean signatureCard) {
        this.signatureCard = signatureCard;
    }

	/**
	 * @return the xbasV2
	 */
	public Boolean getXbasV2() {
		return xbasV2;
	}

	/**
	 * @param xbasV2 the xbasV2 to set
	 */
	public void setXbasV2(Boolean xbasV2) {
		this.xbasV2 = xbasV2;
	}

	/**
	 * @return the signatoryList
	 */
	public List<PDFBeanSignatory> getSignatoryList() {
		return signatoryList;
	}

	/**
	 * @param signatoryList the signatoryList to set
	 */
	public void setSignatoryList(List<PDFBeanSignatory> signatoryList) {
		this.signatoryList = signatoryList;
	}

}
