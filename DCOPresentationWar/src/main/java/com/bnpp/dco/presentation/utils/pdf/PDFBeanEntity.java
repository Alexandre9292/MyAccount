package com.bnpp.dco.presentation.utils.pdf;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.LegalEntityDto;

/**
 * Bean for PDF holding the entity information.
 */
public class PDFBeanEntity {
    /** Field name. */
    private String name;
    /** Field country. */
    private Locale country;
    /** Field legalForm. */
    private String legalForm;
    /** Field addressHeadQuarters. */
    private PDFBeanAddress addressHeadQuarters;
    /** Field addressPostal. */
    private PDFBeanAddress addressPostal;
    /** Field telephone. */
    private String telephone;
    /** Field fax. */
    private String fax;
    /** Field email. */
    private String email;
    /** Field taxInfo. */
    private String taxInfo;
    /** Field naceCode. */
    private String naceCode;
    /** Field registrationNb. */
    private String registrationNb;
    /** Field registrationCountry. */
    private Locale registrationCountry;
    /** Field registrationDate. */
    private String registrationDate;
    /** Field Legal Entity */
    private LegalEntityDto legalEntity;
    // Customer contact
    private PDFBeanThirdParty thirdParty;
    // Customer contact
    private PDFBeanThirdParty thirdParty2;
    // Customer legal representatives (list)
    private List<PDFBeanThirdParty> thirdParties;
    // Customer signatories (list)
    private List<PDFBeanThirdParty> tPForATPList;
    // Countries in which the account opened request is enabled.
    private List<CountryDto> countriesList;
    
    //   NEW
    
 // Customer contact
    private PDFBeanContact contact1;
    // Customer contact
    private PDFBeanContact contact2;
    
 // Customer legal representatives (list)
    private List<PDFBeanRepresentative> representativeList;
    // Customer signatories (list)
    private List<PDFBeanSignatory> signatoryList;

    /** Fields Signature Card details */
    private Date boardResolutionDate;
    private String notaryName;
    private String notaryCity;
    private Date issuanceDate;
    private String publicDeedReference;
    private Date mercantileInscriptionDate;
    private Integer mercantileInscriptionNumber;

    /**
     * Getter for name.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for name.
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

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
     * @return the legalForm
     */
    public String getLegalForm() {
        return this.legalForm;
    }

    /**
     * @param legalForm the legalForm to set
     */
    public void setLegalForm(final String legalForm) {
        this.legalForm = legalForm;
    }

    /**
     * @return the addressHeadQuarters
     */
    public PDFBeanAddress getAddressHeadQuarters() {
        return this.addressHeadQuarters;
    }

    /**
     * @param addressHeadQuarters the addressHeadQuarters to set
     */
    public void setAddressHeadQuarters(final PDFBeanAddress addressHeadQuarters) {
        this.addressHeadQuarters = addressHeadQuarters;
    }

    /**
     * @return the addressPostal
     */
    public PDFBeanAddress getAddressPostal() {
        return this.addressPostal;
    }

    /**
     * @param addressPostal the addressPostal to set
     */
    public void setAddressPostal(final PDFBeanAddress addressPostal) {
        this.addressPostal = addressPostal;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(final String fax) {
        this.fax = fax;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the taxInfo
     */
    public String getTaxInfo() {
        return this.taxInfo;
    }

    /**
     * @param taxInfo the taxInfo to set
     */
    public void setTaxInfo(final String taxInfo) {
        this.taxInfo = taxInfo;
    }

    /**
     * @return the naceCode
     */
    public String getNaceCode() {
        return this.naceCode;
    }

    /**
     * @param naceCode the naceCode to set
     */
    public void setNaceCode(final String naceCode) {
        this.naceCode = naceCode;
    }

    /**
     * @return the registrationNb
     */
    public String getRegistrationNb() {
        return this.registrationNb;
    }

    /**
     * @param registrationNb the registrationNb to set
     */
    public void setRegistrationNb(final String registrationNb) {
        this.registrationNb = registrationNb;
    }

    /**
     * @return the registrationCountry
     */
    public Locale getRegistrationCountry() {
        return this.registrationCountry;
    }

    /**
     * @param registrationCountry the registrationCountry to set
     */
    public void setRegistrationCountry(final Locale registrationCountry) {
        this.registrationCountry = registrationCountry;
    }

    /**
     * @return the registrationDate
     */
    public String getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(final String registrationDate) {
        this.registrationDate = registrationDate;
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
     * @return the notaryName
     */
    public String getNotaryName() {
        return this.notaryName;
    }

    /**
     * @param notaryName the notaryName to set
     */
    public void setNotaryName(final String notaryName) {
        this.notaryName = notaryName;
    }

    /**
     * @return the notaryCity
     */
    public String getNotaryCity() {
        return this.notaryCity;
    }

    /**
     * @param notaryCity the notaryCity to set
     */
    public void setNotaryCity(final String notaryCity) {
        this.notaryCity = notaryCity;
    }

    /**
     * @return the issuanceDate
     */
    public Date getIssuanceDate() {
        return this.issuanceDate;
    }

    /**
     * @param issuanceDate the issuanceDate to set
     */
    public void setIssuanceDate(final Date issuanceDate) {
        this.issuanceDate = issuanceDate;
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
     * @return the mercantileInscriptionDate
     */
    public Date getMercantileInscriptionDate() {
        return this.mercantileInscriptionDate;
    }

    /**
     * @param mercantileInscriptionDate the mercantileInscriptionDate to set
     */
    public void setMercantileInscriptionDate(final Date mercantileInscriptionDate) {
        this.mercantileInscriptionDate = mercantileInscriptionDate;
    }

    /**
     * @return the mercantileInscriptionNumber
     */
    public Integer getMercantileInscriptionNumber() {
        return this.mercantileInscriptionNumber;
    }

    /**
     * @param mercantileInscriptionNumber the mercantileInscriptionNumber to set
     */
    public void setMercantileInscriptionNumber(final Integer mercantileInscriptionNumber) {
        this.mercantileInscriptionNumber = mercantileInscriptionNumber;
    }

    /**
     * @return the thirdParty
     */
    public PDFBeanThirdParty getThirdParty() {
        return this.thirdParty;
    }

    /**
     * @param thirdParty the thirdParty to set
     */
    public void setThirdParty(final PDFBeanThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    /**
     * @return the thirdParty2
     */
    public PDFBeanThirdParty getThirdParty2() {
        return this.thirdParty2;
    }

    /**
     * @param thirdParty2 the thirdParty2 to set
     */
    public void setThirdParty2(final PDFBeanThirdParty thirdParty2) {
        this.thirdParty2 = thirdParty2;
    }

    /**
     * @return the thirdParties
     */
    public List<PDFBeanThirdParty> getThirdParties() {
        return this.thirdParties;
    }

    /**
     * @param thirdParties the thirdParties to set
     */
    public void setThirdParties(final List<PDFBeanThirdParty> thirdParties) {
        this.thirdParties = thirdParties;
    }

    /**
     * @return the legalEntity
     */
    public LegalEntityDto getLegalEntity() {
        return this.legalEntity;
    }

    /**
     * @param legalEntity the legalEntity to set
     */
    public void setLegalEntity(final LegalEntityDto legalEntity) {
        this.legalEntity = legalEntity;
    }

    /**
     * @return the countriesList
     */
    public List<CountryDto> getCountriesList() {
        return this.countriesList;
    }

    /**
     * @param countriesList the countriesList to set
     */
    public void setCountriesList(final List<CountryDto> countriesList) {
        this.countriesList = countriesList;
    }

    /**
     * @return the tPForATPList
     */
    public List<PDFBeanThirdParty> getTPForATPList() {
        return this.tPForATPList;
    }

    /**
     * @param tPForATPList the tPForATPList to set
     */
    public void setTPForATPList(final List<PDFBeanThirdParty> tPForATPList) {
        this.tPForATPList = tPForATPList;
    }

	/**
	 * @return the contact1
	 */
	public PDFBeanContact getContact1() {
		return contact1;
	}

	/**
	 * @param contact1 the contact1 to set
	 */
	public void setContact1(PDFBeanContact contact1) {
		this.contact1 = contact1;
	}

	/**
	 * @return the contact2
	 */
	public PDFBeanContact getContact2() {
		return contact2;
	}

	/**
	 * @param contact2 the contact2 to set
	 */
	public void setContact2(PDFBeanContact contact2) {
		this.contact2 = contact2;
	}

	/**
	 * @return the representativeList
	 */
	public List<PDFBeanRepresentative> getRepresentativeList() {
		return representativeList;
	}

	/**
	 * @param representativeList the representativeList to set
	 */
	public void setRepresentativeList(List<PDFBeanRepresentative> representativeList) {
		this.representativeList = representativeList;
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
