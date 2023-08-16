package com.bnpp.dco.presentation.form.newsite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("companyDetailForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompanyDetailForm implements Serializable {

	/** Serial UID */
	private static final long serialVersionUID = 624647531587946997L;
	
	/** Logger (named on purpose after the form). */
    private static final Logger LOG = LoggerFactory.getLogger(CompanyDetailForm.class);
    
    /** Datas */
    private EntityDto entity;
    private String selectedCountry;
    private Integer selectedAccountId;
    private Integer selectedEntityId;
    private AccountFormDto accountForm;
        
    /** Lists */
    private List<AccountDto> accounts;
    private List<CountryDto> countries;
    private List<AjaxDto> countriesSort;
    
    /* other */
    private Set<String> accountCountries;
    
    
    /**
     * Initialitation des donn�es de la page
     */
	public void init(EntityDto entity) {
		try {
			// Liste des pays
			final List<CountryDto> countryDtoList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null);
			setAccountCountries(countryDtoList);	
			
			this.entity = entity;	
		} catch (final DCOException e) {
			LOG.error("Error while initializing legal countries for account openings");
		}
	}
	
	/**
	 * Liste des pays pour le visuel de carte
	 */
	public void updateAccountCountriesList() {
		if (accounts != null && !accounts.isEmpty()) {
			accountCountries = new HashSet<String>();
			for (AccountDto account : accounts) {
				accountCountries.add(account.getCountry());
			}
		} else {
			// clean la liste des pays
			accountCountries = new HashSet<String>();
		}
	}
	
	
	/** 
	 * set list of contries to create an account 
	 */
	public void setAccountCountries(final List<CountryDto> countries) {
        if (countries != null) {
            final List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();
            final Locale locale;
            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                    && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
                locale = UserHelper.getUserInSession().getPreferences().getLocale();
            } else {
                locale = new Locale("en");
            }
            for (final CountryDto c : countries) {
                final AjaxDto a = new AjaxDto(c.getLocale().getDisplayCountry(locale), c.getLocale().getCountry());
                ajaxList.add(a);
            }
            Collections.sort(ajaxList, new AjaxDto.OrderByLabel());
            setCountriesSort(ajaxList);
        }
        this.countries = countries;
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	
    
	/**
	 * @return the entity
	 */
	public EntityDto getEntity() {
		return entity;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(EntityDto entity) {
		this.entity = entity;
	}
	/**
	 * @return the selectedCountry
	 */
	public String getSelectedCountry() {
		return selectedCountry;
	}
	/**
	 * @param selectedCountry the selectedCountry to set
	 */
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	/**
	 * @return the accounts
	 */
	public List<AccountDto> getAccounts() {
		return accounts;
	}
	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<AccountDto> accounts) {
		this.accounts = accounts;
	}
	/**
	 * @return the countries
	 */
	public List<CountryDto> getCountries() {
		return countries;
	}
	/**
	 * @param contries the countries to set
	 */
	public void setCountries(List<CountryDto> countries) {
		this.countries = countries;
	}

	/**
	 * @return the countriesSort
	 */
	public List<AjaxDto> getCountriesSort() {
		return countriesSort;
	}

	/**
	 * @param countriesSort the countriesSort to set
	 */
	public void setCountriesSort(List<AjaxDto> countriesSort) {
		this.countriesSort = countriesSort;
	}

	/**
	 * @return the selectedAccount
	 */
	public Integer getSelectedAccountId() {
		return selectedAccountId;
	}

	/**
	 * @param selectedAccount the selectedAccount to set
	 */
	public void setSelectedAccount(Integer selectedAccountId) {
		this.selectedAccountId = selectedAccountId;
	}

	/**
	 * @return the selectedentityId
	 */
	public Integer getSelectedEntityId() {
		return selectedEntityId;
	}

	/**
	 * @param selectedentityId the selectedentityId to set
	 */
	public void setSelectedEntityId(Integer selectedEntityId) {
		this.selectedEntityId = selectedEntityId;
	}


	/**
	 * @return the accountForm
	 */
	public AccountFormDto getAccountForm() {
		return accountForm;
	}

	/**
	 * @param accountForm the accountForm to set
	 */
	public void setAccountForm(AccountFormDto accountForm) {
		this.accountForm = accountForm;
	}

	/**
	 * @return the accountCoutries
	 */
	public Set<String> getAccountCountries() {
		return accountCountries;
	}

	/**
	 * @param accountCoutries the accountCoutries to set
	 */
	public void setAccountCountries(Set<String> accountCountries) {
		this.accountCountries = accountCountries;
	}
}
