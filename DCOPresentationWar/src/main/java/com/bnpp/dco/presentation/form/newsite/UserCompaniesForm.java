package com.bnpp.dco.presentation.form.newsite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("userCompaniesForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserCompaniesForm implements Serializable {

	/** Serial UID */
	private static final long serialVersionUID = 1666254108833555574L;

	/** Logger (named on purpose after the form). */
    private static final Logger LOG = LoggerFactory.getLogger(UserCompaniesForm.class);
    
    /* general */
    private Integer selectedTag;
    private boolean modeEdition;
    
    /* Data */
    private List<EntityDto> userEntities;
    private EntityDto selectedEntity;
    private Map<Integer, List<AccountDto>> entitiesAccounts;
    private Map<Integer, AccountFormDto> entitiesAccountsForms;
    
    /* List */
    private List<CountryDto> countries;
    
    
    /**
     * Initialisation du form en r�cup�rant la liste des entit�s de l'utilisateur
     */
    public void init(Integer tagNumero) {
		try {
			// Liste des pays
			final List<CountryDto> countryDtoList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null);
			setCountries(countryDtoList);	
			
			// Liste des entit�s
			final List<EntityDto> entities = (List<EntityDto>) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,Constants.CONTROLLER_ENTITIES_ALL_GET_BY_USER_LOGIN, 
					new Object[] {UserHelper.getUserInSession().getLogin()}); 
			setUserEntities(entities);
			
			if (entities != null && !entities.isEmpty()) {
				// map des comptes pour les entit�s
				List<Integer> entitiesId = new ArrayList<Integer>();
				List<Integer> accountFormsId = new ArrayList<Integer>();
				for (EntityDto entity : entities) {
					entitiesId.add(entity.getId());
					if (entity.getAccountsForm().get(0) != null) {
						accountFormsId.add(entity.getAccountsForm().get(0).getId());
					} else {
						accountFormsId.add(null);
					}
				}
				entitiesAccounts = setMapEntitiesAccount(entitiesId, accountFormsId);
				
				changeEntity(tagNumero);
			} else {
				// page cr�ation d'entit�
				entitiesAccounts = setMapEntitiesAccount(null, null);
						
				changeEntity(-1);
			}
			
			
		} catch (DCOException e) {
			LOG.error("Error while initializing list of entities for user");
		}		// UserHelper.getUserInSession().getLogin()
    }
    
    /**
     * Initialisation du form en r�cup�rant la liste des entit�s de l'utilisateur
     */
    public void changeEntity(Integer tagNumero) {			
		selectedTag = tagNumero;
		if (selectedTag >= 0) {
			selectedEntity = userEntities.get(selectedTag);
		}
    }
    
    
    /**
     * r�cup�rer la liste des comptes de chaque entit�
     * @param entitiesId
     */
    private Map<Integer, List<AccountDto>> setMapEntitiesAccount (final List<Integer> entitiesId, final List<Integer> accountFormsId) {
    	final Map<Integer, List<AccountDto>> mapEntitiesAccount = new HashMap<Integer, List<AccountDto>>();
    	entitiesAccountsForms = new HashMap<Integer, AccountFormDto>();
    	
    	if (entitiesId != null) {
    		try {
        		for (int i = 0; i<entitiesId.size(); i++) {
            		
        			
        			// Formulaire des comptes
//            		AccountFormDto accountForm = (AccountFormDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_FORM, Constants.CONTROLLER_ACCOUNT_FORM_BY_ENTITY, 
//        					new Object[] {entityId});
//            		entitiesAccountsForms.put(entityId, accountForm);
        			
        			Integer accountFormId = accountFormsId.get(i);
        			Integer entityId = entitiesId.get(i);
        			// Liste des comptes 
        			if (accountFormId != null) {
        				final List<AccountDto> entityAccounts = (List<AccountDto>) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_BY_ACOUNT_FORM, 
        						new Object[] {accountFormId});
        				
        				if (entityAccounts != null) {
            				mapEntitiesAccount.put(entityId, entityAccounts);
        				} else {
        					mapEntitiesAccount.put(entityId, new ArrayList<AccountDto>());
        				}
        			}
            	}
        	} catch (DCOException e) {
        		LOG.error("Error while initializing list of accounts for entities");
        	}
    	}
    	
        return mapEntitiesAccount;
    }
    

	/**
	 * @return the userEntities
	 */
	public List<EntityDto> getUserEntities() {
		return userEntities;
	}

	/**
	 * @param userEntities the userEntities to set
	 */
	public void setUserEntities(List<EntityDto> userEntities) {
		this.userEntities = userEntities;
	}


	/**
	 * @return the selectedEntity
	 */
	public EntityDto getSelectedEntity() {
		return selectedEntity;
	}


	/**
	 * @param selectedEntity the selectedEntity to set
	 */
	public void setSelectedEntity(EntityDto selectedEntity) {
		this.selectedEntity = selectedEntity;
	}


	/**
	 * @return the selectedTag
	 */
	public Integer getSelectedTag() {
		return selectedTag;
	}


	/**
	 * @param selectedTag the selectedTag to set
	 */
	public void setSelectedTag(Integer selectedTag) {
		this.selectedTag = selectedTag;
	}


	/**
	 * @return the modeEdition
	 */
	public boolean isModeEdition() {
		return modeEdition;
	}


	/**
	 * @param modeEdition the modeEdition to set
	 */
	public void setModeEdition(boolean modeEdition) {
		this.modeEdition = modeEdition;
	}

	/**
	 * @return the entitiesAccounts
	 */
	public Map<Integer, List<AccountDto>> getEntitiesAccounts() {
		return entitiesAccounts;
	}

	/**
	 * @param entitiesAccounts the entitiesAccounts to set
	 */
	public void setEntitiesAccounts(Map<Integer, List<AccountDto>> entitiesAccounts) {
		this.entitiesAccounts = entitiesAccounts;
	}

	/**
	 * @return the entitiesAccountsForms
	 */
	public Map<Integer, AccountFormDto> getEntitiesAccountsForms() {
		return entitiesAccountsForms;
	}

	/**
	 * @param entitiesAccountsForms the entitiesAccountsForms to set
	 */
	public void setEntitiesAccountsForms(Map<Integer, AccountFormDto> entitiesAccountsForms) {
		this.entitiesAccountsForms = entitiesAccountsForms;
	}

	/**
	 * @return the countries
	 */
	public List<CountryDto> getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<CountryDto> countries) {
		this.countries = countries;
	}
}
