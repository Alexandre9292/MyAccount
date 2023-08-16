package com.bnpp.dco.presentation.controller.newsite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.AddressDto;
import com.bnpp.dco.common.dto.ContactDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.controller.GenericController;
import com.bnpp.dco.presentation.form.newsite.CompanyDetailForm;
import com.bnpp.dco.presentation.form.newsite.NewCompanyForm;
import com.bnpp.dco.presentation.form.newsite.UserCompaniesForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class CompanyDetailController extends GenericController {

	private static final Logger LOG = LoggerFactory.getLogger(CompanyDetailController.class);
	
	@Autowired
	private PropertiesHelper propertiesHelper;
	
	@Autowired
    private CompanyDetailForm companyDetailForm;
	
	@Autowired
	private UserCompaniesForm userCompaniesForm;
	
	@Autowired
    private NewCompanyForm newCompanyForm;
	
	/**
     * TEMPORARY : access to new WebSite
     * @return the page to forward to
     */
    @RequestMapping(value = "detailCompanyLoad")
    public final String detailCompanyLoad(final HttpServletRequest request, final HttpServletResponse response) {
    	userCompaniesForm.init(0);
    	
    	String returnPath = "";
    	
    	//fillUserSessionPrefs(UserHelper.getUserInSession().getLogin());
    	
    	if (UserHelper.getUserInSession().getProfile() == Constants.PROFILE_BANK) {
    	
	    	 final List<String> roles = new ArrayList<String>();
	
	         final Iterator<GrantedAuthority> it = UserHelper.getUserInSession().getAuthorities().iterator();
	         while (it.hasNext()) {
	             final GrantedAuthority ga = it.next();
	             roles.add(ga.getAuthority());
	         }
	         
	         if (roles.contains(Constants.ROLE_SUPER_ADMIN)) {
	        	 returnPath = WebConstants.REDIRECT_ACTION + WebConstants.BANK_ACCOUNT_MANAGEMENT_LOAD;
	            } else if (roles.contains(Constants.ROLE_MGMT_PARAMETERS)) {
	            	returnPath = WebConstants.REDIRECT_ACTION + WebConstants.PARAM;
	            } else if (roles.contains(Constants.ROLE_MGMT_ACCOUNT)) {
	            	returnPath = WebConstants.REDIRECT_ACTION + WebConstants.CLIENT_MANAGEMENT_LOAD;
	            } else if (roles.contains(Constants.ROLE_MGMT_DOCUMENTS)) {
	            	returnPath = WebConstants.REDIRECT_ACTION + WebConstants.DOCUMENTS_LOAD;
	            } else if (roles.contains(Constants.ROLE_VIEW_CLIENT_DATA)) {
	            	returnPath = WebConstants.REDIRECT_ACTION + WebConstants.CLIENT_DATA_LOAD;
	            } else if (roles.contains(Constants.ROLE_VIEW_STATISTICS)) {
	            	returnPath = WebConstants.REDIRECT_ACTION + WebConstants.STATS_LOAD;
	            } else {
	                addError(this.propertiesHelper.getMessage("page.login.web.message.no.roles"));
	                return WebConstants.REDIRECT_ACTION + WebConstants.LOGIN_LOAD;
	            }
    	
    	} else if (userCompaniesForm.getSelectedEntity() == null) {
    		// Aucune entit�
    		returnPath = WebConstants.REDIRECT_ACTION + WebConstants.NEW_COMPANY_LOAD;
    	} else {
    		
    		updateCompanyDetailForm();
    		
            returnPath = WebConstants.NEW + WebConstants.HOME ;
    	}
    	
    	return returnPath;
    }
    
    /**
     * Met à jour l'entity
     */
    private void updateCompanyDetailForm() {
    	// pointer sur la 1ere entit�
		companyDetailForm.setEntity(userCompaniesForm.getSelectedEntity());
    	companyDetailForm.setAccountCountries(userCompaniesForm.getCountries());
		List<AccountDto> entityAccounts = userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId());
    	
		if (entityAccounts != null) {
			Collections.sort(entityAccounts, new AccountDto());
			Collections.reverse(entityAccounts);
		}
		
		companyDetailForm.setAccounts(entityAccounts);
    	companyDetailForm.setAccountForm(userCompaniesForm.getEntitiesAccountsForms().get(userCompaniesForm.getSelectedEntity().getId()));
    	
    	companyDetailForm.updateAccountCountriesList();
	}

	/**
     * Back to company home page
     * @return the page to forward to
     */
    @RequestMapping(value = "homeCompanyBack")
    public final String homeCompanyBack() {
    	// Retour en mode consultation
    	userCompaniesForm.setModeEdition(false);
    	
    	if (userCompaniesForm.getSelectedEntity() != null && userCompaniesForm.getSelectedTag() == -1) {
    		// quitter l'onglet add Entity pour retourner � la 1ere entit� de la liste
    		userCompaniesForm.setSelectedTag(0);
    	}
    	
        return WebConstants.NEW + WebConstants.HOME ;
    }
    
    /**
     * Back to company home page from account detail
     * @return the page to forward to
     */
    @RequestMapping(value = "homeCompanyBackFromAccount")
    public final String homeCompanyBackFromAccount(HttpServletRequest request) {
    	resetMessages();
    	
    	AccountDto account = (AccountDto) request.getSession().getAttribute("account"); 	
    	if(account != null){
	    	boolean isNewAccount = true;
	    	
	    	int index = 0;
	    	if(isNewAccount){
	    		if(userCompaniesForm.getEntitiesAccounts() != null && !userCompaniesForm.getEntitiesAccounts().isEmpty() &&
	    				userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId()) != null){
	    			for (AccountDto accountIt : userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId())) {
		        		if (accountIt.getId().intValue() == account.getId().intValue()) {
		        			isNewAccount = false;
		        			break;
		        		}
		        		index++;
		        	}
	    		}
	    	}
	    	
	    	if (isNewAccount) {
	    		if (userCompaniesForm.getEntitiesAccounts() != null && userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId()) != null) {
	    			Collections.reverse(userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId()));
	    		}
	    		userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId()).add(account);
	    		companyDetailForm.getAccountCountries().add(account.getCountry());
	    		Collections.reverse(userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId()));
	    	} else {
	    		userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId()).set(index,account);
	    	}
	    	
	    	
	    	// MAJ de l'entité (pour les representants)
	    	userCompaniesForm.getSelectedEntity().setRepresentativesList(account.getEntity().getRepresentativesList());
	    	
	    	/* userCompaniesForm.init(userCompaniesForm.getSelectedTag());
	    	updateCompanyDetailForm(); */
	    	
        	return WebConstants.NEW + WebConstants.HOME ;
	    	
	    }
    	
        return WebConstants.NEW + WebConstants.HOME ;
    }
    
    /**
     * Prepare l'�dition de compte
     * @return the page to forward to
     */
    @RequestMapping(value = "editAccount")
    public final String editAccount(@RequestParam("selectedAccountId") Integer accountId,
    		HttpServletRequest request) {
    	request.getSession().setAttribute("selectedAccountId", accountId);
    	request.getSession().setAttribute("entity", userCompaniesForm.getSelectedEntity());
        return WebConstants.REDIRECT_ACTION + WebConstants.DETAIL_ACCOUNT_LOAD ;
    }
    
    /**
     * Charger un autre onglet
     * @return the page to forward to
     */
    @RequestMapping(value = "changeOrCreateCompany")
    public final String changeOrCreateCompany(@ModelAttribute("userCompaniesForm") final UserCompaniesForm form) {
    	String returnPath;
    	
    	if (form.getSelectedTag() == -1) {
    		returnPath = WebConstants.REDIRECT_ACTION + WebConstants.NEW_COMPANY_LOAD;
    	} else {
    		userCompaniesForm.changeEntity(form.getSelectedTag());
    		userCompaniesForm.setModeEdition(false);
        	companyDetailForm.setEntity(userCompaniesForm.getSelectedEntity());
        	
        	List<AccountDto> entityAccounts = userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId());
        	companyDetailForm.setAccounts(entityAccounts);
        	companyDetailForm.setAccountForm(userCompaniesForm.getEntitiesAccountsForms().get(userCompaniesForm.getSelectedEntity().getId()));
        	companyDetailForm.updateAccountCountriesList();
        	returnPath = WebConstants.NEW + WebConstants.HOME;
    	}
        return returnPath;
    }
    
	/**
     * Charger le formulaire company en mode creation
     * @return the page to forward to
     */
    @RequestMapping(value = "newCompanyLoad")
    public final String newCompanyLoad() {
    	EntityDto entity = new EntityDto();
    	try {
			final UserDto user = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_GET_BY_LOGIN, 
					new Object[] {UserHelper.getUserInSession().getLogin()});
			
			List<UserDto> users = new ArrayList<UserDto>();
			users.add(user);
			
			entity.setUsers(users);
		} catch (DCOException e) {
			LOG.error("Error while getting user");
		}
    	newCompanyForm.init(entity);
        return WebConstants.NEW + WebConstants.HOME;
    }
    
    /**
     * Charger le formulaire company en mode �dition
     * @return the page to forward to
     */
    @RequestMapping(value = "editCompanyLoad")
    public final String editCompanyLoad(@ModelAttribute("companyDetailForm") final CompanyDetailForm companyDetailForm) {
    	try {
			EntityDto company = (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,Constants.CONTROLLER_ENTITIES_GET_BY_ID, 
					new Object[] {companyDetailForm.getSelectedEntityId()});
			newCompanyForm.init(company);
			userCompaniesForm.setModeEdition(true);
		} catch (DCOException e) {
			LOG.error("Error while getting account to edit");
		}
    	return WebConstants.NEW + WebConstants.HOME;
    }

    /**
     * Save the account and contacts from part 1
     * @return the page to forward to
     */
    @RequestMapping(value = "saveNewCompany", method = RequestMethod.POST)
	public final String saveNewCompany(@ModelAttribute("newCompanyForm") NewCompanyForm newCompany) {
    	EntityDto entity = null;
    	
		resetMessages();
		cleanRepresentativesList(newCompany.getNewEntity());
		boolean error = checkEntity(newCompany.getNewEntity());
		
		if (newCompany.getNewEntity().getSameAddress()) {
			String field1 = newCompany.getNewEntity().getAddress().getFieldOne();
			String field2 = newCompany.getNewEntity().getAddress().getFieldTwo();
			String field3 = newCompany.getNewEntity().getAddress().getFieldThree();
			String field4 = newCompany.getNewEntity().getAddress().getFieldFour();
			String field5 = newCompany.getNewEntity().getAddress().getFieldFive();
			
			newCompany.getNewEntity().getAddressMailing().setFieldOne(field1);
			newCompany.getNewEntity().getAddressMailing().setFieldTwo(field2);
			newCompany.getNewEntity().getAddressMailing().setFieldThree(field3);
			newCompany.getNewEntity().getAddressMailing().setFieldFour(field4);
			newCompany.getNewEntity().getAddressMailing().setFieldFive(field5);
		}
		
		if(!error){
			if(newCompany.getNewEntity().getContact1() == null){
				error=true;
			}else{
				error = checkContact(newCompany.getNewEntity().getContact1());
			}
		}
		if(!error && newCompany.getNewEntity().getHasContact2()){
			error = checkContact(newCompany.getNewEntity().getContact2());
		}
		if(!error && (newCompany.getNewEntity().getRepresentativesList() == null || newCompany.getNewEntity().getRepresentativesList().isEmpty())){
			error = true;
		}
		if(!error){
			List<RepresentativeDto> tempListRepresentatives = new ArrayList<RepresentativeDto>();
			tempListRepresentatives.addAll(newCompany.getNewEntity().getRepresentativesList());
			for(RepresentativeDto r : newCompany.getNewEntity().getRepresentativesList()){
				if(r.getName()==null || r.getName().equals("")){
					tempListRepresentatives.remove(r);
				}
			}
			newCompany.getNewEntity().setRepresentativesList(tempListRepresentatives);
		}
	
		if (!error) {
			try {
				newCompany.getNewEntity().setCountry(LocaleUtil.stringToCountry(newCompany.getNewEntity().getCountryIncorp()));
				entity = (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES, Constants.CONTROLLER_ENTITIES_UPDATE, new Object[] { newCompany.getNewEntity() });			
				
				// Affectation de l'account Form
				if (entity.getAccountsForm() == null || entity.getAccountsForm().isEmpty()) {
					AccountFormDto newAccountForm = new AccountFormDto();
					newAccountForm.setEntity(entity);
					newAccountForm = (AccountFormDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_FORM,
		                    Constants.CONTROLLER_ACCOUNT_FORM_SAVE_ACOUNT_FORM, new Object[] {newAccountForm});
					final List<AccountFormDto> afList = new ArrayList<AccountFormDto>();
		            afList.add(newAccountForm);
		            entity.setAccountsForm(afList);
		            
					entity = (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES, Constants.CONTROLLER_ENTITIES_UPDATE, new Object[] { entity });
				}
			} catch (final DCOException e) {
				error = true;
				LOG.error("An error occurred while saving the entity", e);
				addError(this.propertiesHelper.getMessage("page.param.error.saving"));
			}
		}
		
		String returnPath;
		if (!error) {
			if (userCompaniesForm.getSelectedTag() == -1) {
				// cas 1ere cr�ation
				if (companyDetailForm.getAccountCountries() == null || companyDetailForm.getAccountCountries().isEmpty()) {
					companyDetailForm.setAccountCountries(userCompaniesForm.getCountries());
				}	
				
				// cas cr�ation
				if (userCompaniesForm.getUserEntities() == null) {
					userCompaniesForm.setUserEntities(new ArrayList<EntityDto>());
				}
				userCompaniesForm.getUserEntities().add(entity);
				userCompaniesForm.setSelectedTag(userCompaniesForm.getUserEntities().size()-1);
				userCompaniesForm.setSelectedEntity(entity);
				userCompaniesForm.getEntitiesAccounts().put(entity.getId(), new ArrayList<AccountDto>());
				companyDetailForm.setAccountForm(entity.getAccountsForm().get(0));
				companyDetailForm.setAccounts(userCompaniesForm.getEntitiesAccounts().get(entity.getId()));
				companyDetailForm.setAccountCountries(new HashSet<String>());
		    	
		    	List<AccountDto> entityAccounts = userCompaniesForm.getEntitiesAccounts().get(userCompaniesForm.getSelectedEntity().getId());
		    	companyDetailForm.setAccounts(entityAccounts);
		    	companyDetailForm.setAccountForm(userCompaniesForm.getEntitiesAccountsForms().get(userCompaniesForm.getSelectedEntity().getId()));
			} else {
				// cas edition
				userCompaniesForm.getUserEntities().set(userCompaniesForm.getSelectedTag(), entity);
				userCompaniesForm.setSelectedEntity(entity);
			}
			userCompaniesForm.setModeEdition(false);
			
			returnPath = WebConstants.NEW + WebConstants.HOME;
		} else {
			newCompanyForm.setRepresentatives(newCompany.getNewEntity().getRepresentativesList());
			newCompanyForm.setNbRepresentatives(newCompany.getNbRepresentatives());
			
			returnPath = WebConstants.NEW + WebConstants.HOME;
		}
		
		if(error){
			addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
		}
		
		return returnPath; 
	}
    
    
    
    /**
     * Check entity data
     * @param company
     * @return
     */
    private boolean checkEntity(final EntityDto company) {
	    boolean error = false;
	    boolean missingFields = false;
	    
	    error = checkCompany(company) && checkAddress(company.getAddress());
		if (!company.getSameAddress()) {
			error = error && checkAddress(company.getAddressMailing());
		}
		
		if (company.getLabel() == null ||
				company.getRegistrationCountry() == null ||
				company.getCommercialRegister() == null ||
				company.getTaxInformation() == null ||
				company.getCountryIncorp() == null ||
				company.getLegalStatus() == null) {
			missingFields = true;
		}
		
		if(error || missingFields) {
			addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
		}
		
		return error;
    }
    
    private boolean checkContact(final ContactDto contact) {
	    boolean error = false;
	    
	    if (StringUtils.isBlank(contact.getName()) ||
        		StringUtils.isBlank(contact.getFirstname()) ||
        		StringUtils.isBlank(contact.getMail()) ||
        		StringUtils.isBlank(contact.getTel())) {  
            error = true;
        }
		
		if(error) {
			addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
		}
		
		return error;
    }
    	
    /**
     * Check entity data
     * @param company
     * @return
     */
    private void cleanRepresentativesList(EntityDto company) {
    	if (company.getRepresentativesList() != null && !company.getRepresentativesList().isEmpty()) {
    		List<RepresentativeDto> representativesToDelete = new ArrayList<RepresentativeDto>();
    		for(RepresentativeDto representative : company.getRepresentativesList()) {
        		if (representative.getFirstname() == null && representative.getName() == null) {
        			representativesToDelete.add(representative);
        		}
        	}
        	company.getRepresentativesList().removeAll(representativesToDelete);
    	}
    }
    
    /** 
     * Check company data
     */
    private boolean checkCompany(final EntityDto company) {
        boolean error = false;
        if (StringUtils.isBlank(company.getLabel()) ||
        		company.getLegalStatus() == null ||
        		StringUtils.isBlank(company.getCountryIncorp()) ||
        		StringUtils.isBlank(company.getCommercialRegister()) ||
        		StringUtils.isBlank(company.getRegistrationCountry()) ||
        		StringUtils.isBlank(company.getTaxInformation())) {  
            error = true;
        }
        return error;
    }
    
    /** 
     * Check address data
     */
    private boolean checkAddress(final AddressDto address) {
        boolean error = false;
        if (StringUtils.isBlank(address.getFieldOne())||
        		StringUtils.isBlank(address.getFieldTwo())||
        		StringUtils.isBlank(address.getFieldThree())||
        		StringUtils.isBlank(address.getFieldFour())) {
            error = true;
        }
        return error;
    }
    
	/**
	 * @return the companyDetailForm
	 */
    @ModelAttribute("companyDetailForm")
	public CompanyDetailForm getCompanyDetailForm() {
		return companyDetailForm;
	}

	/**
	 * @return the userHomeForm
	 */
    @ModelAttribute("userCompaniesForm")
	public UserCompaniesForm getUserCompaniesForm() {
		return userCompaniesForm;
	}

	/**
	 * @return the newCompanyForm
	 */
    @ModelAttribute("newCompanyForm")
	public NewCompanyForm getNewCompanyForm() {
		return newCompanyForm;
	}
}
