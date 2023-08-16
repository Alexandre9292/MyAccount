package com.bnpp.dco.presentation.utils.pdf;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PDFBeanSignatory {
		
    /** Field firstName. */
    private String firstName;
    /** Field lastName. */
    private String lastName;
    /** Field position. */
    private String position;
    /** Field birthDate. */
    private Date birthDate;
    private String birthDay;
	private String birthMonth;
	private String birthYear;
    /** Field birthPlace. */
    private String birthPlace;
    /** Field nationality. */
    private String nationality;
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
    
    private String citizenship;


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}
	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	/**
	 * @return the birthMonth
	 */
	public String getBirthMonth() {
		return birthMonth;
	}
	/**
	 * @param birthMonth the birthMonth to set
	 */
	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}
	/**
	 * @return the birthYear
	 */
	public String getBirthYear() {
		return birthYear;
	}
	/**
	 * @param birthYear the birthYear to set
	 */
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	/**
	 * @return the birthPlace
	 */
	public String getBirthPlace() {
		return birthPlace;
	}
	/**
	 * @param birthPlace the birthPlace to set
	 */
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}
	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * @return the citizenships
	 */
	public List<Locale> getCitizenships() {
		return citizenships;
	}
	/**
	 * @param citizenships the citizenships to set
	 */
	public void setCitizenships(List<Locale> citizenships) {
		this.citizenships = citizenships;
	}
	/**
	 * @return the addressHome
	 */
	public PDFBeanAddress getAddressHome() {
		return addressHome;
	}
	/**
	 * @param addressHome the addressHome to set
	 */
	public void setAddressHome(PDFBeanAddress addressHome) {
		this.addressHome = addressHome;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the iDNumber
	 */
	public String getIDNumber() {
		return iDNumber;
	}
	/**
	 * @param iDNumber the iDNumber to set
	 */
	public void setIDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}
	/**
	 * @return the actingIndivOrJoin
	 */
	public String getActingIndivOrJoin() {
		return actingIndivOrJoin;
	}
	/**
	 * @param actingIndivOrJoin the actingIndivOrJoin to set
	 */
	public void setActingIndivOrJoin(String actingIndivOrJoin) {
		this.actingIndivOrJoin = actingIndivOrJoin;
	}
	/**
	 * @return the actingIndividually
	 */
	public String getActingIndividually() {
		return actingIndividually;
	}
	/**
	 * @param actingIndividually the actingIndividually to set
	 */
	public void setActingIndividually(String actingIndividually) {
		this.actingIndividually = actingIndividually;
	}
	/**
	 * @return the actingJointly
	 */
	public String getActingJointly() {
		return actingJointly;
	}
	/**
	 * @param actingJointly the actingJointly to set
	 */
	public void setActingJointly(String actingJointly) {
		this.actingJointly = actingJointly;
	}
	/**
	 * @return the accounts
	 */
	public String getAccounts() {
		return accounts;
	}
	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	/**
	 * @return the legalEntityName
	 */
	public String getLegalEntityName() {
		return legalEntityName;
	}
	/**
	 * @param legalEntityName the legalEntityName to set
	 */
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	/**
	 * @return the pdfBeanActingDetails
	 */
	public List<PDFBeanActingDetails> getPdfBeanActingDetails() {
		return pdfBeanActingDetails;
	}
	/**
	 * @param pdfBeanActingDetails the pdfBeanActingDetails to set
	 */
	public void setPdfBeanActingDetails(List<PDFBeanActingDetails> pdfBeanActingDetails) {
		this.pdfBeanActingDetails = pdfBeanActingDetails;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the formAOR
	 */
	public Boolean getFormAOR() {
		return formAOR;
	}
	/**
	 * @param formAOR the formAOR to set
	 */
	public void setFormAOR(Boolean formAOR) {
		this.formAOR = formAOR;
	}
	/**
	 * @return the signatureCard
	 */
	public Boolean getSignatureCard() {
		return signatureCard;
	}
	/**
	 * @param signatureCard the signatureCard to set
	 */
	public void setSignatureCard(Boolean signatureCard) {
		this.signatureCard = signatureCard;
	}
	/**
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}
	/**
	 * @param citizenship the citizenship to set
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
}
