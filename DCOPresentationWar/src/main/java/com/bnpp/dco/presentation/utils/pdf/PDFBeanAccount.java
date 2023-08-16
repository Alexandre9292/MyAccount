package com.bnpp.dco.presentation.utils.pdf;

import java.util.List;
import java.util.Locale;

import com.bnpp.dco.common.dto.LanguageDto;

/**
 * Bean for PDF holding an account information.
 */
public class PDFBeanAccount {
    /** Field name. */
    private String name;
    /** Field type. */
    private String type;
    /** Field currency. */
    private String currency;
    /** Field statementPeriodicity. */
    private String statementPeriodicity;
    /** Field statementForm. */
    private String statementForm;
    /** Field address subsidiary. */
    private PDFBeanAddress address;
    /** Field VAT registration number. */
    private String vatNumber;
    /** Field registration number */
    private String commercialRegister;
    /** Field to check if communication language has been enabled for this country */
    private Boolean comLangEnabled;
    /** Field to set communication language selected */
    private LanguageDto communicationLanguage;
    /** Field account ThirdParty List. 20/08/2014 by FTA */
    private List<PDFBeanThirdParty> accountThirdPartyList;
    /** Field account country */
    private Locale country;
    /** Field account branch name */
    private String branchName;
    
    /** Field account branch name */
    private String reference;
    
    private String channel;
    private String strategyDocument;
    
    private List<PDFBeanSignatory> accountSignatoryList;
    private List<PDFBeanRepresentative> accountRepresentativeList;
    private PDFBeanContact accountContact1;
    private PDFBeanContact accountContact2;
    private List<PDFBeanCollege> collegeList;
    private List<PDFBeanRules> rulesList;
    
    private Integer id;

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
     * Getter for type.
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for type.
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Getter for currency.
     * @return the currency
     */
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Setter for currency.
     * @param name the currency to set
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * Getter for statementPeriodicity.
     * @return the statementPeriodicity
     */
    public String getStatementPeriodicity() {
        return this.statementPeriodicity;
    }

    /**
     * Setter for statementPeriodicity.
     * @param statementPeriodicity the statementPeriodicity to set
     */
    public void setStatementPeriodicity(final String statementPeriodicity) {
        this.statementPeriodicity = statementPeriodicity;
    }

    /**
     * Getter for statementForm.
     * @return the statementForm
     */
    public String getStatementForm() {
        return this.statementForm;
    }

    /**
     * Setter for statementForm.
     * @param statementForm the statementForm to set
     */
    public void setStatementForm(final String statementForm) {
        this.statementForm = statementForm;
    }

    /**
     * @return the address
     */
    public PDFBeanAddress getAddress() {
        return this.address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final PDFBeanAddress address) {
        this.address = address;
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
     * @return the com_lang_enabled
     */
    public Boolean getComLangEnabled() {
        return this.comLangEnabled;
    }

    /**
     * @param com_lang_enabled the com_lang_enabled to set
     */
    public void setComLangEnabled(final Boolean comLangEnabled) {
        this.comLangEnabled = comLangEnabled;
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
     * @return the accountThirdPartyList
     */
    public List<PDFBeanThirdParty> getAccountThirdPartyList() {
        return this.accountThirdPartyList;
    }

    /**
     * @param accountThirdPartyList the accountThirdPartyList to set
     */
    public void setAccountThirdPartyList(final List<PDFBeanThirdParty> accountThirdPartyList) {
        this.accountThirdPartyList = accountThirdPartyList;
    }

    /**
     * @return the country
     */
    public Locale getCountry() {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(final Locale country) {
        this.country = country;
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
	 * @return the accountSignatoryList
	 */
	public List<PDFBeanSignatory> getAccountSignatoryList() {
		return accountSignatoryList;
	}

	/**
	 * @param accountSignatoryList the accountSignatoryList to set
	 */
	public void setAccountSignatoryList(List<PDFBeanSignatory> accountSignatoryList) {
		this.accountSignatoryList = accountSignatoryList;
	}

	/**
	 * @return the accountRepresentativeList
	 */
	public List<PDFBeanRepresentative> getAccountRepresentativeList() {
		return accountRepresentativeList;
	}

	/**
	 * @param accountRepresentativeList the accountRepresentativeList to set
	 */
	public void setAccountRepresentativeList(List<PDFBeanRepresentative> accountRepresentativeList) {
		this.accountRepresentativeList = accountRepresentativeList;
	}

	/**
	 * @return the accountContact2
	 */
	public PDFBeanContact getAccountContact2() {
		return accountContact2;
	}

	/**
	 * @param accountContact2 the accountContact2 to set
	 */
	public void setAccountContact2(PDFBeanContact accountContact2) {
		this.accountContact2 = accountContact2;
	}

	/**
	 * @return the accountContact1
	 */
	public PDFBeanContact getAccountContact1() {
		return accountContact1;
	}

	/**
	 * @param accountContact1 the accountContact1 to set
	 */
	public void setAccountContact1(PDFBeanContact accountContact1) {
		this.accountContact1 = accountContact1;
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

	/**
	 * @return the collegeList
	 */
	public List<PDFBeanCollege> getCollegeList() {
		return collegeList;
	}

	/**
	 * @param collegeList the collegeList to set
	 */
	public void setCollegeList(List<PDFBeanCollege> collegeList) {
		this.collegeList = collegeList;
	}

	/**
	 * @return the rulesList
	 */
	public List<PDFBeanRules> getRulesList() {
		return rulesList;
	}

	/**
	 * @param rulesList the rulesList to set
	 */
	public void setRulesList(List<PDFBeanRules> rulesList) {
		this.rulesList = rulesList;
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
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
}
