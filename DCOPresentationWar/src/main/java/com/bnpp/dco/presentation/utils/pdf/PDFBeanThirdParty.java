package com.bnpp.dco.presentation.utils.pdf;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Bean for PDF holding an account information.
 */
public class PDFBeanThirdParty {
    /** Field type. */
    private Integer type;
    /** Field firstName. */
    private String firstName;
    /** Field lastName. */
    private String lastName;
    /** Field position. */
    private String position;
    /** Field birthDate. */
    private Date birthDate;
    /** Field birthPlace. */
    private String birthPlace;
    /** Field nationality. */
    private Locale nationality;
    /** Field nationality. */
    private List<Locale> citizenships;
    /** Field addressHome. */
    private PDFBeanAddress addressHome;
    /** Field telephone. */
    private String telephone;
    /** Field fax. */
    private String fax;
    /** Field email. */
    private String email;
    /** Field iDNumber. */
    private String iDNumber;
    /** Field actingIndivOrJoin. */
    private String actingIndivOrJoin;
    /** Field acting Individually */
    private String actingIndividually;
    /** Field acting Jointly */
    private String actingJointly;
    /** Field amountLimit. */
    private String amountLimit;
    private String deviseAmountLimit;
    /** Field accounts. */
    private String accounts;
    /** Field Legal Entity */
    private String legalEntityName;
    /** Field list of third parti use label and login. */
    private List<PDFBeanActingDetails> pdfBeanActingDetails;
    private Integer id;
    /** Field flag for the authorisation signatories. */
    private Boolean formAOR = false;
    /** Field flag for the signature card. */
    private Boolean signatureCard = false;

    /**
     * @return the aORForm
     */
    public Boolean getFormAOR() {
        return this.formAOR;
    }

    /**
     * @param aORForm the aORForm to set
     */
    public void setFormAOR(final Boolean formAOR) {
        this.formAOR = formAOR;
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
     * Getter for type.
     * @return the type
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * Setter for type.
     * @param type the type to set
     */
    public void setType(final Integer type) {
        this.type = type;
    }

    /**
     * Getter for firstName.
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for firstName.
     * @param name the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName.
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter for lastName.
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for position.
     * @return the position
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * Setter for position.
     * @param position the position to set
     */
    public void setPosition(final String position) {
        this.position = position;
    }

    /**
     * Getter for birthDate.
     * @return the birthDate
     */
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     * Setter for birthDate.
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Getter for birthPlace.
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return this.birthPlace;
    }

    /**
     * Setter for birthPlace.
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * Getter for nationality.
     * @return the nationality
     */
    public Locale getNationality() {
        return this.nationality;
    }

    /**
     * Setter for nationality.
     * @param nationality the nationality to set
     */
    public void setNationality(final Locale nationality) {
        this.nationality = nationality;
    }

    /**
     * Getter for addressHome.
     * @return the addressHome
     */
    public PDFBeanAddress getAddressHome() {
        return this.addressHome;
    }

    /**
     * Setter for addressHome.
     * @param addressHome the addressHome to set
     */
    public void setAddressHome(final PDFBeanAddress addressHome) {
        this.addressHome = addressHome;
    }

    /**
     * Getter for telephone.
     * @return the telephone
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * Setter for telephone.
     * @param telephone the telephone to set
     */
    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter for fax.
     * @return the fax
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * Setter for fax.
     * @param fax the fax to set
     */
    public void setFax(final String fax) {
        this.fax = fax;
    }

    /**
     * Getter for email.
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for email.
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Getter for iDNumber.
     * @return the iDNumber
     */
    public String getIDNumber() {
        return this.iDNumber;
    }

    /**
     * Setter for iDNumber.
     * @param iDNumber the iDNumber to set
     */
    public void setIDNumber(final String iDNumber) {
        this.iDNumber = iDNumber;
    }

    /**
     * Getter for actingIndivOrJoin.
     * @return the actingIndivOrJoin
     */
    public String getActingIndivOrJoin() {
        return this.actingIndivOrJoin;
    }

    /**
     * Setter for actingIndivOrJoin.
     * @param actingIndivOrJoin the actingIndivOrJoin to set
     */
    public void setActingIndivOrJoin(final String actingIndivOrJoin) {
        this.actingIndivOrJoin = actingIndivOrJoin;
    }

    /**
     * Getter for amountLimit.
     * @return the amountLimit
     */
    public String getAmountLimit() {
        return this.amountLimit;
    }

    /**
     * Setter for amountLimit.
     * @param amountLimit the amountLimit to set
     */
    public void setAmountLimit(final String amountLimit) {
        this.amountLimit = amountLimit;
    }

    /**
     * Getter for accounts.
     * @return the accounts
     */
    public String getAccounts() {
        return this.accounts;
    }

    /**
     * Setter for accounts.
     * @param accounts the accounts to set
     */
    public void setAccounts(final String accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the correspondantType
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param correspondantType the correspondantType to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the actingIndividually
     */
    public String getActingIndividually() {
        return this.actingIndividually;
    }

    /**
     * @param actingIndividually the actingIndividually to set
     */
    public void setActingIndividually(final String actingIndividually) {
        this.actingIndividually = actingIndividually;
    }

    /**
     * @return the actingJointly
     */
    public String getActingJointly() {
        return this.actingJointly;
    }

    /**
     * @param actingJointly the actingJointly to set
     */
    public void setActingJointly(final String actingJointly) {
        this.actingJointly = actingJointly;
    }

    /**
     * @return the legalEntityName
     */
    public String getLegalEntityName() {
        return this.legalEntityName;
    }

    /**
     * @param legalEntityName the legalEntityName to set
     */
    public void setLegalEntityName(final String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }

    /**
     * @return the citizenships
     */
    public List<Locale> getCitizenships() {
        return this.citizenships;
    }

    /**
     * @param citizenships the citizenships to set
     */
    public void setCitizenships(final List<Locale> citizenships) {
        this.citizenships = citizenships;
    }

    /**
     * @return the pdfBeanActingDetails
     */
    public List<PDFBeanActingDetails> getPdfBeanActingDetails() {
        return this.pdfBeanActingDetails;
    }

    /**
     * @param pdfBeanActingDetails the pdfBeanActingDetails to set
     */
    public void setPdfBeanActingDetails(final List<PDFBeanActingDetails> pdfBeanActingDetails) {
        this.pdfBeanActingDetails = pdfBeanActingDetails;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return this.id.equals(((PDFBeanThirdParty) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId();
    }

	public String getDeviseAmountLimit() {
		return deviseAmountLimit;
	}

	public void setDeviseAmountLimit(String deviseAmountLimit) {
		this.deviseAmountLimit = deviseAmountLimit;
	}

}
