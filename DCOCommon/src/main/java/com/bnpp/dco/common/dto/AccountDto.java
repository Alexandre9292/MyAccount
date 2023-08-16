package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class AccountDto implements Serializable, Comparator<AccountDto> {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private ParamFuncDto typeAccount;
    private ParamFuncDto currency;
    private ParamFuncDto periodicity;
    private ParamFuncDto channelParam;
    private List<ParamFuncDto> statementTypes;
    private AccountFormDto accountForm;
    private String reference;
    private AddressDto address;
    private String country;
    private String commercialRegister;
    private CountryDto countryAccount;
    private List<AccountThirdPartyDto> accountThirdPartyList;
    private String vatNumber;
    private LanguageDto communicationLanguage;
    private String branchName;
    private List<SignatoryDto> signatoriesList;
    private List<RulesDto> rulesList;
    private EntityDto entity;
    private String name;
    private Date creationDate;
    private String creationDateString;
    private Date editDate;
    private String editDateString;
    private Integer pourcentage;
    private String strategyDocument;
    private String channel;
    private List<CollegeDto> collegeList;
    
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
     * @return the countryAccount
     */
    public CountryDto getCountryAccount() {
        return this.countryAccount;
    }

    /**
     * @param countryAccount the countryAccount to set
     */
    public void setCountryAccount(final CountryDto countryAccount) {
        this.countryAccount = countryAccount;
    }

    public List<ParamFuncDto> getStatementTypes() {
        return this.statementTypes;
    }

    public void setStatementTypes(final List<ParamFuncDto> statementTypes) {
        this.statementTypes = statementTypes;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(final String country) {
        this.country = country;
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
     * @return the reference
     */
    public String getReference() {
        return this.reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(final String reference) {
        this.reference = reference;
    }

    /**
     * @return the address
     */
    public AddressDto getAddress() {
        return this.address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final AddressDto address) {
        this.address = address;
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
     * @return the typeAccount
     */
    public ParamFuncDto getTypeAccount() {
        return this.typeAccount;
    }

    /**
     * @param typeAccount the typeAccount to set
     */
    public void setTypeAccount(final ParamFuncDto typeAccount) {
        this.typeAccount = typeAccount;
    }

    /**
     * @return the currency
     */
    public ParamFuncDto getCurrency() {
        return this.currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(final ParamFuncDto currency) {
        this.currency = currency;
    }

    /**
     * @return the periodicity
     */
    public ParamFuncDto getPeriodicity() {
        return this.periodicity;
    }

    /**
     * @param periodicity the periodicity to set
     */
    public void setPeriodicity(final ParamFuncDto periodicity) {
        this.periodicity = periodicity;
    }

    /**
     * @return the accountForm
     */
    public AccountFormDto getAccountForm() {
        return this.accountForm;
    }

    /**
     * @param accountForm the accountForm to set
     */
    public void setAccountForm(final AccountFormDto accountForm) {
        this.accountForm = accountForm;
    }

    /**
     * @return the vatNumber
     */
    public String getVatNumber() {
        return this.vatNumber;
    }

    /**
     * @param vatNumber the vatNumber to set
     */
    public void setVatNumber(final String vatNumber) {
        this.vatNumber = vatNumber;
    }

    /**
     * @return the communicationLanguage
     */
    public LanguageDto getCommunicationLanguage() {
        return this.communicationLanguage;
    }

    /**
     * @param communicationLanguage the communicationLanguage to set
     */
    public void setCommunicationLanguage(final LanguageDto communicationLanguage) {
        this.communicationLanguage = communicationLanguage;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return this.branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(final String branchName) {
        this.branchName = branchName;
    }
    
    /**
	 * @return the signatoriesList
	 */
	public List<SignatoryDto> getSignatoriesList() {
		return signatoriesList;
	}

	/**
	 * @param signatoriesList the signatoriesList to set
	 */
	public void setSignatoriesList(List<SignatoryDto> signatoriesList) {
		this.signatoriesList = signatoriesList;
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

	public EntityDto getEntity() {
		return entity;
	}

	public void setEntity(EntityDto entity) {
		this.entity = entity;
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the editDate
	 */
	public Date getEditDate() {
		return editDate;
	}

	/**
	 * @param editDate the editDate to set
	 */
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	/**
	 * @return the pourcentage
	 */
	public Integer getPourcentage() {
		return pourcentage;
	}

	/**
	 * @param pourcentage the pourcentage to set
	 */
	public void setPourcentage(Integer pourcentage) {
		this.pourcentage = pourcentage;
	}

	/**
	 * @return the creationDateString
	 */
	public String getCreationDateString() {
		return creationDateString;
	}

	/**
	 * @param creationDateString the creationDateString to set
	 */
	public void setCreationDateString(String creationDateString) {
		this.creationDateString = creationDateString;
	}

	/**
	 * @return the editDateString
	 */
	public String getEditDateString() {
		return editDateString;
	}

	/**
	 * @param editDateString the editDateString to set
	 */
	public void setEditDateString(String editDateString) {
		this.editDateString = editDateString;
	}

	/**
	 * @return the strategyDocument
	 */
	public String getStrategyDocument() {
		return strategyDocument;
	}

	/**
	 * @param strategyDocument the strategyDocument to set
	 */
	public void setStrategyDocument(String strategyDocument) {
		this.strategyDocument = strategyDocument;
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
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public int compare(AccountDto a1, AccountDto a2) {
		return a1.getEditDate().compareTo(a2.getEditDate());
	}

	/**
	 * @return the channelParam
	 */
	public ParamFuncDto getChannelParam() {
		return channelParam;
	}

	/**
	 * @param channelParam the channelParam to set
	 */
	public void setChannelParam(ParamFuncDto channelParam) {
		this.channelParam = channelParam;
	}

}
