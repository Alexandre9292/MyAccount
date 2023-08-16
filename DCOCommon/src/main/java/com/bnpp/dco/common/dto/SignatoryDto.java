package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SignatoryDto implements Serializable, Comparable<SignatoryDto>{

	private static final long serialVersionUID = 691359537665283552L;
	private Integer id;
	private String legalEntityName;
	private String positionName;
	private AddressDto homeAddress;
	private UserDto user;
	private String name;
	private String firstname;
	private Date birthDate;
	private String birthDay;
	private String birthMonth;
	private String birthYear;
	private String birthPlace;
	private String email;
	private String tel;
	private String fax;
	private String referenceId;
	private String nationality;
	private List<CitizenshipDto> citizenshipsList;
	private String citizenship;
	private List<CollegeDto> collegeList;
	private List<Integer> collegeIndexes;
	private List<RulesDto> rulesList;
	private List<AccountDto> account;
	private String role;
	private Integer nbCollege;
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
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}
	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/**
	 * @return the addressByIdHomeAdress
	 */
	public AddressDto getHomeAddress() {
		return homeAddress;
	}
	/**
	 * @param addressByIdHomeAdress the addressByIdHomeAdress to set
	 */
	public void setHomeAddress(AddressDto homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
	 * @return the user
	 */
	public UserDto getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserDto user) {
		this.user = user;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
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
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}
	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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
	 * @return the citizenshipsList
	 */
	public List<CitizenshipDto> getCitizenshipsList() {
		return citizenshipsList;
	}
	/**
	 * @param citizenshipsList the citizenshipsList to set
	 */
	public void setCitizenshipsList(List<CitizenshipDto> citizenshipsList) {
		this.citizenshipsList = citizenshipsList;
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
	/**
	 * @return the collegeList
	 */
	public List<CollegeDto> getCollegeList() {
		return collegeList;
	}
	/**
	 * @param collegeList the collegeList to set
	 */
	public void setCollegeList(List<CollegeDto> collegeList) {
		this.collegeList = collegeList;
	}
	/**
	 * @return the rulesList
	 */
	public List<RulesDto> getRulesList() {
		return rulesList;
	}
	/**
	 * @param rulesList the rulesList to set
	 */
	public void setRulesList(List<RulesDto> rulesList) {
		this.rulesList = rulesList;
	}
	/**
	 * @return the account
	 */
	public List<AccountDto> getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(List<AccountDto> account) {
		this.account = account;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the nbCollege
	 */
	public Integer getNbCollege() {
		return nbCollege;
	}
	/**
	 * @param nbCollege the nbCollege to set
	 */
	public void setNbCollege(Integer nbCollege) {
		this.nbCollege = nbCollege;
	}
	/**
	 * @return the collegeIndexes
	 */
	public List<Integer> getCollegeIndexes() {
		return collegeIndexes;
	}
	/**
	 * @param collegeIndexes the collegeIndexes to set
	 */
	public void setCollegeIndexes(List<Integer> collegeIndexes) {
		this.collegeIndexes = collegeIndexes;
	}
	@Override
	public int compareTo(SignatoryDto s) {
		if (this.getId() == s.getId() || this.getId() == null || s.getId() == null) {
			return 0;
		}
		if (this.getId() < s.getId()) {
			return -1;
        } else {
            return 1;
        }
	}
	
	
	
}
