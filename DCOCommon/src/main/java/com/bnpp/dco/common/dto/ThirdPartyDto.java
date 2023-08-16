package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bnpp.dco.common.constant.Constants;

public class ThirdPartyDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String firstName;
    private String splittedNames;
    private Date birthDate;
    private String birthPlace;
    private String idReference;
    private Locale nationality;
    private ParamFuncDto signatureAuthorization;
    private String amountLimit;
    private String typeCompteAutorisation;
    private AddressDto homeAddress;
    private String tel;
    private String mail;
    private String fax;
    private UserDto user;
    private ParamFuncDto typeThirdParty;
    private Integer correspondantType;
    private Integer correspondantTypeTwo;
    private Integer correspondantTypeThree;
    private List<AccountThirdPartyDto> accountThirdPartyList;
    private String legalEntityName;
    private String positionName;
    private List<CitizenshipDto> citizenships;

    /**
     * @return the accountThirdPartyList
     */
    public List<AccountThirdPartyDto> getAccountThirdPartyList() {
        return this.accountThirdPartyList;
    }

    /**
     * @param accountThirdPartyList the accountThirdPartyList to set
     */
    public void setAccountThirdPartyList(final List<AccountThirdPartyDto> accountThirdPartyList) {
        this.accountThirdPartyList = accountThirdPartyList;
    }

    /**
     * @return the typeThirdParty
     */
    public ParamFuncDto getTypeThirdParty() {
        return this.typeThirdParty;
    }

    /**
     * @param typeThirdParty the typeThirdParty to set
     */
    public void setTypeThirdParty(final ParamFuncDto typeThirdParty) {
        this.typeThirdParty = typeThirdParty;

    }

    /**
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return this.birthPlace;
    }

    /**
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * @return the idReference
     */
    public String getIdReference() {
        return this.idReference;
    }

    /**
     * @param idReference the idReference to set
     */
    public void setIdReference(final String idReference) {
        this.idReference = idReference;
    }

    /**
     * @return the nationality
     */
    public Locale getNationality() {
        return this.nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(final Locale nationality) {
        this.nationality = nationality;
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
     * @return the typeCompteAutorisation
     */
    public String getTypeCompteAutorisation() {
        return this.typeCompteAutorisation;
    }

    /**
     * @param typeCompteAutorisation the typeCompteAutorisation to set
     */
    public void setTypeCompteAutorisation(final String typeCompteAutorisation) {
        this.typeCompteAutorisation = typeCompteAutorisation;
    }

    /**
     * @return the homeAddress
     */
    public AddressDto getHomeAddress() {
        return this.homeAddress;
    }

    /**
     * @param homeAddress the homeAddress to set
     */
    public void setHomeAddress(final AddressDto homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(final String tel) {
        this.tel = tel;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(final String mail) {
        this.mail = mail;
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
     * @return the user
     */
    public UserDto getUser() {
        return this.user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(final UserDto user) {
        this.user = user;
    }

    public Integer getCorrespondantType() {
        return this.correspondantType;
    }

    public void setCorrespondantType(final Integer correspondantType) {
        this.correspondantType = correspondantType;
    }

    public String getSplittedNames() {
        return this.splittedNames;
    }

    public void setSplittedNames() {

        final StringBuilder splittedNames = new StringBuilder("");

        if (this.firstName != null && this.firstName.length() > 19) {
            splittedNames.append(this.firstName.substring(0, 20));
        } else {
            splittedNames.append(this.firstName);
        }

        splittedNames.append(Constants.SPACE);

        if (this.name != null && this.name.length() > 19) {
            splittedNames.append(this.name.substring(0, 20));
        } else {
            splittedNames.append(this.name);
        }

        this.splittedNames = splittedNames.toString();
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
     * @return the positionName
     */
    public String getPositionName() {
        return this.positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(final String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the correspondantType2
     */
    public Integer getCorrespondantTypeTwo() {
        return this.correspondantTypeTwo;
    }

    /**
     * @param correspondantType2 the correspondantType2 to set
     */
    public void setCorrespondantTypeTwo(final Integer correspondantTypeTwo) {
        this.correspondantTypeTwo = correspondantTypeTwo;
    }

    /**
     * @return the correspondantType3
     */
    public Integer getCorrespondantTypeThree() {
        return this.correspondantTypeThree;
    }

    /**
     * @param correspondantType3 the correspondantType3 to set
     */
    public void setCorrespondantTypeThree(final Integer correspondantTypeThree) {
        this.correspondantTypeThree = correspondantTypeThree;
    }

    /**
     * @return the citizenships
     */
    public List<CitizenshipDto> getCitizenships() {
        return this.citizenships;
    }

    /**
     * @param citizenships the citizenships to set
     */
    public void setCitizenships(final List<CitizenshipDto> citizenships) {
        this.citizenships = citizenships;
    }

}
