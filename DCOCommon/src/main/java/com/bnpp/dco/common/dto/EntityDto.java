package com.bnpp.dco.common.dto;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EntityDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private AddressDto address;
    private AddressDto addressMailing;
    private Locale country;
    private ThirdPartyDto thirdParty;
    private ThirdPartyDto thirdParty2;
    private ParamFuncDto legalStatus;
    private String label = "";
    private String bankContact = "";
    private String taxResidenceCode;
    private String commercialRegister;
    private Date registrationDate;
    private String registrationCountry;
    private String taxInformation;
    private String countryIncorp;
    private List<UserDto> users;
    private List<AccountFormDto> accountsForm;
    private List<ThirdPartyDto> thirdParties;
    
    private ContactDto contact1;
    private ContactDto contact2;
    private List<RepresentativeDto> representativesList;
    private List<AccountDto> accountList;

    private Date boardResolutionDate;
    private String notaryName;
    private String notaryCity;
    private Date issuanceDate;
    private String publicDeedReference;
    private Date mercantileInscriptionDate;
    private Integer mercantileInscriptionNumber;

    private String legalStatusOther;

    private Boolean sameAddress;
    private Boolean hasContact2;

    /**
     * @return the accountsForm
     */
    public List<AccountFormDto> getAccountsForm() {
        return this.accountsForm;
    }

    /**
     * @param accountsForm the accountsForm to set
     */
    public void setAccountsForm(final List<AccountFormDto> accountsForm) {
        this.accountsForm = accountsForm;
    }

    /**
     * @return the addressMailing
     */
    public AddressDto getAddressMailing() {
        return this.addressMailing;
    }

    /**
     * @param addressMailing the addressMailing to set
     */
    public void setAddressMailing(final AddressDto addressMailing) {
        this.addressMailing = addressMailing;
    }

    /**
     * @return the users
     */
    public List<UserDto> getUsers() {
        return this.users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(final List<UserDto> users) {
        this.users = users;
    }

    /**
     * @return the legalStatus
     */
    public ParamFuncDto getLegalStatus() {
        return this.legalStatus;
    }

    /**
     * @param legalStatus the legalStatus to set
     */
    public void setLegalStatus(final ParamFuncDto legalStatus) {
        this.legalStatus = legalStatus;
    }

    /**
     * @return the registrationCountry
     */
    public String getRegistrationCountry() {
        return this.registrationCountry;
    }

    /**
     * @param registrationCountry the registrationCountry to set
     */
    public void setRegistrationCountry(final String registrationCountry) {
        this.registrationCountry = registrationCountry;
    }

    /**
     * @return the commercialRegister
     */
    public String getCommercialRegister() {
        return this.commercialRegister;
    }

    /**
     * @param commercialRegister the commercialRegister to set
     */
    public void setCommercialRegister(final String commercialRegister) {
        this.commercialRegister = commercialRegister;
    }

    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(final Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the taxInformation
     */
    public String getTaxInformation() {
        return this.taxInformation;
    }

    /**
     * @param taxInformation the taxInformation to set
     */
    public void setTaxInformation(final String taxInformation) {
        this.taxInformation = taxInformation;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final AddressDto getAddress() {
        return this.address;
    }

    public final void setAddress(final AddressDto address) {
        this.address = address;
    }

    public final Locale getCountry() {
        return this.country;
    }

    public final void setCountry(final Locale country) {
        this.country = country;
    }

    public final String getLabel() {
        return this.label;
    }

    public final void setLabel(final String label) {
        this.label = label;
    }

    public final String getBankContact() {
        return this.bankContact;
    }

    public final void setBankContact(final String bankContact) {
        this.bankContact = bankContact;
    }

    public final String getTaxResidenceCode() {
        return this.taxResidenceCode;
    }

    public final void setTaxResidenceCode(final String taxResidenceCode) {
        this.taxResidenceCode = taxResidenceCode;
    }

    public ThirdPartyDto getThirdParty() {
        return this.thirdParty;
    }

    public void setThirdParty(final ThirdPartyDto thirdParty) {
        this.thirdParty = thirdParty;
    }

    /**
     * @return the thirdParty2
     */
    public ThirdPartyDto getThirdParty2() {
        return thirdParty2;
    }

    /**
     * @param thirdParty2 the thirdParty2 to set
     */
    public void setThirdParty2(ThirdPartyDto thirdParty2) {
        this.thirdParty2 = thirdParty2;
    }

    public List<ThirdPartyDto> getThirdParties() {
        return this.thirdParties;
    }

    public void setThirdParties(final List<ThirdPartyDto> thirdParties) {
        this.thirdParties = thirdParties;
    }

    /**
     * @return the boardResolutionDate
     */
    public final Date getBoardResolutionDate() {
        return this.boardResolutionDate;
    }

    /**
     * @param boardResolutionDate the boardResolutionDate to set
     */
    public final void setBoardResolutionDate(final Date boardResolutionDate) {
        this.boardResolutionDate = boardResolutionDate;
    }

    /**
     * @return the notaryName
     */
    public final String getNotaryName() {
        return this.notaryName;
    }

    /**
     * @param notaryName the notaryName to set
     */
    public final void setNotaryName(final String notaryName) {
        this.notaryName = notaryName;
    }

    /**
     * @return the notaryCity
     */
    public final String getNotaryCity() {
        return this.notaryCity;
    }

    /**
     * @param notaryCity the notaryCity to set
     */
    public final void setNotaryCity(final String notaryCity) {
        this.notaryCity = notaryCity;
    }

    /**
     * @return the issuanceDate
     */
    public final Date getIssuanceDate() {
        return this.issuanceDate;
    }

    /**
     * @param issuanceDate the issuanceDate to set
     */
    public final void setIssuanceDate(final Date issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    /**
     * @return the publicDeedReference
     */
    public final String getPublicDeedReference() {
        return this.publicDeedReference;
    }

    /**
     * @param publicDeedReference the publicDeedReference to set
     */
    public final void setPublicDeedReference(final String publicDeedReference) {
        this.publicDeedReference = publicDeedReference;
    }

    /**
     * @return the mercantileInscriptionDate
     */
    public final Date getMercantileInscriptionDate() {
        return this.mercantileInscriptionDate;
    }

    /**
     * @param mercantileInscriptionDate the mercantileInscriptionDate to set
     */
    public final void setMercantileInscriptionDate(final Date mercantileInscriptionDate) {
        this.mercantileInscriptionDate = mercantileInscriptionDate;
    }

    /**
     * @return the mercantileInscriptionNumber
     */
    public final Integer getMercantileInscriptionNumber() {
        return this.mercantileInscriptionNumber;
    }

    /**
     * @param mercantileInscriptionNumber the mercantileInscriptionNumber to set
     */
    public final void setMercantileInscriptionNumber(final Integer mercantileInscriptionNumber) {
        this.mercantileInscriptionNumber = mercantileInscriptionNumber;
    }

    /**
     * @return the legalStatusOther
     */
    public String getLegalStatusOther() {
        return this.legalStatusOther;
    }

    /**
     * @param legalStatusOther the legalStatusOther to set
     */
    public void setLegalStatusOther(final String legalStatusOther) {
        this.legalStatusOther = legalStatusOther;
    }

    /**
     * @return the sameAddress
     */
    public Boolean getSameAddress() {
        return this.sameAddress;
    }

    /**
     * @param sameAddress the sameAddress to set
     */
    public void setSameAddress(final Boolean sameAddress) {
        this.sameAddress = sameAddress;
    }

	/**
	 * @return the countryIncorp
	 */
	public String getCountryIncorp() {
		return countryIncorp;
	}

	/**
	 * @param countryIncorp the countryIncorp to set
	 */
	public void setCountryIncorp(String countryIncorp) {
		this.countryIncorp = countryIncorp;
	}

	/**
	 * @return the contact1
	 */
	public ContactDto getContact1() {
		return contact1;
	}

	/**
	 * @param contact1 the contact1 to set
	 */
	public void setContact1(ContactDto contact1) {
		this.contact1 = contact1;
	}

	/**
	 * @return the contact2
	 */
	public ContactDto getContact2() {
		return contact2;
	}

	/**
	 * @param contact2 the contact2 to set
	 */
	public void setContact2(ContactDto contact2) {
		this.contact2 = contact2;
	}

	/**
	 * @return the representativesList
	 */
	public List<RepresentativeDto> getRepresentativesList() {
		return representativesList;
	}

	/**
	 * @param representativesList the representativesList to set
	 */
	public void setRepresentativesList(List<RepresentativeDto> representativesList) {
		this.representativesList = representativesList;
	}

	/**
	 * @return the accountList
	 */
	public List<AccountDto> getAccountList() {
		return accountList;
	}

	/**
	 * @param accountList the accountList to set
	 */
	public void setAccountList(List<AccountDto> accountList) {
		this.accountList = accountList;
	}

	/**
	 * @return the hasContact2
	 */
	public Boolean getHasContact2() {
		return hasContact2;
	}

	/**
	 * @param hasContact2 the hasContact2 to set
	 */
	public void setHasContact2(Boolean hasContact2) {
		this.hasContact2 = hasContact2;
	}

}
