package com.bnpp.dco.presentation.controller.newsite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.dto.AddressDto;
import com.bnpp.dco.common.dto.CitizenshipDto;
import com.bnpp.dco.common.dto.CitizenshipIdDto;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.ContactDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DateFormatDto;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.dto.DocumentTypeDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.bean.UserSession;
import com.bnpp.dco.presentation.controller.GenericController;
import com.bnpp.dco.presentation.controller.ParamController;
import com.bnpp.dco.presentation.form.newsite.AccountDetailForm;
import com.bnpp.dco.presentation.form.newsite.CompanyDetailForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.DateUtils;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;
import com.bnpp.dco.presentation.utils.pdf.PDFBean;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanAccount;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanActingDetails;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanAddress;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanCollege;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanContact;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanEntity;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanRepresentative;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanRules;
import com.bnpp.dco.presentation.utils.pdf.PDFBeanSignatory;
import com.bnpp.dco.presentation.utils.pdf.PDFGenerator;
import com.lowagie.text.DocumentException;

@Controller
public class AccountDetailController extends GenericController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountDetailController.class);

	@Autowired
	private PropertiesHelper propertiesHelper;

	@Autowired
	private AccountDetailForm accountDetailForm;

	@Autowired
	private PDFGenerator pdfGenerator;

	/**
	 * @return the newAccountForm
	 */
	@ModelAttribute("accountDetailForm")
	public AccountDetailForm getAccountDetailForm() {
		return accountDetailForm;
	}

	/**
	 * Chargement de la page de d�tail pour une �dition
	 * 
	 * @return the page to forward to
	 */
	@RequestMapping(value = "detailAccountLoad")
	public final String detailAccountLoad(HttpServletRequest request) {
		try {
			accountDetailForm.setEntity((EntityDto) request.getSession().getAttribute("entity"));

			// Liste des comptes
			final AccountFormDto accountform = (AccountFormDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_FORM,
					Constants.CONTROLLER_ACCOUNT_FORM_BY_ENTITY, new Object[] { accountDetailForm.getEntity().getId() });

			accountDetailForm.setAccountForm(accountform);

			// Formulaire des comptes
//			if (accountform != null) {
//				final List<AccountDto> entityAccounts = (List<AccountDto>) BusinessHelper.call(
//						Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_BY_ACOUNT_FORM,
//						new Object[] { accountform.getId() });
//				accountDetailForm.getEntity().setAccountList(entityAccounts);
//			}
			
			AccountDto account = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT,
					Constants.CONTROLLER_ACCOUNT_BY_ID,
					new Object[] { request.getSession().getAttribute("selectedAccountId") });
			account.setCountry(account.getCountryAccount().getLocale().getCountry());
			account.setEntity(accountDetailForm.getEntity());
			accountDetailForm.setCountry(account.getCountryAccount());
			accountDetailForm.init(account);
			
			request.getSession().setAttribute("account", account);

		} catch (DCOException e) {
			LOG.error("Error while gettin account to edit");
		}
		return WebConstants.NEW + WebConstants.DETAIL_ACCOUNT;
	}

	/**
	 * Chargement de la page de d�tail pour une cr�ation
	 * 
	 * @return the page to forward to
	 */
	@RequestMapping(value = "newAccountLoad")
	public final String newAccountLoad(
			@ModelAttribute("companyDetailForm") final CompanyDetailForm companyAccountsForm) {
		try {
			AccountDto newAccount = new AccountDto();
			if(companyAccountsForm.getSelectedCountry() != null){
				newAccount.setCountry(companyAccountsForm.getSelectedCountry());
				
				CountryDto c = (CountryDto) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
						Constants.CONTROLLER_COUNTRY_GET_BY_LOCALE, new Object[] { newAccount.getCountry() });
				newAccount.setCountryAccount(c);
				
				/* newAccount.setCountryAccount(new CountryDto());
				newAccount.getCountryAccount()
						.setLocale(LocaleUtil.stringToCountry(companyAccountsForm.getSelectedCountry())); */
				accountDetailForm.setCountry(newAccount.getCountryAccount());
				newAccount.setPourcentage(0);
				
				EntityDto ent = (EntityDto) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
						Constants.CONTROLLER_ENTITIES_GET_BY_ID,
						new Object[] { companyAccountsForm.getSelectedEntityId() });

				// Liste des comptes
				final AccountFormDto accountform = (AccountFormDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT_FORM,
						Constants.CONTROLLER_ACCOUNT_FORM_BY_ENTITY, new Object[] { ent.getId() });

				accountDetailForm.setAccountForm(accountform);
				
				// Formulaire des comptes
//				if (accountform != null) {
//					final List<AccountDto> entityAccounts = (List<AccountDto>) BusinessHelper.call(
//							Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_BY_ACOUNT_FORM,
//							new Object[] { accountform.getId() });
//					ent.setAccountList(entityAccounts);
//				}

				accountDetailForm.setEntity(ent);
				accountDetailForm.init(newAccount);
			}
			else{
				LOG.error("You have to select a country");
				addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
			}
			

		} catch (DCOException e) {
			LOG.error("Error while gettin account to edit");
		}
		return WebConstants.NEW + WebConstants.DETAIL_ACCOUNT;

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// PART 1
	///////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Save the account and contacts from part 1
	 * 
	 * @return the page to forward to
	 */
	@RequestMapping(value = "saveAccountAndContacts", method = RequestMethod.POST)
	public @ResponseBody List<String> saveAccountAndContacts(@ModelAttribute("accountDetailForm") final AccountDetailForm accountForm,
			HttpServletRequest request) {

		resetMessages();
		AccountDto newAccount = accountForm.getAccount();
		newAccount.setEntity(accountDetailForm.getEntity());// getCountryByLocal
		newAccount.setCountry(accountDetailForm.getAccount().getCountry());
		boolean error = checkAccount(newAccount);

		if (!error) {
			try {
				/**
				 * 
				 */
				if (newAccount.getCreationDate() == null) {
					newAccount.setCreationDate(new Date());
				}
				newAccount.setEditDate(new Date());
				if (newAccount.getPourcentage() == 0) {
					newAccount.setPourcentage(40);
				}

				/** ACCOUNT */
				CountryDto c = (CountryDto) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
						Constants.CONTROLLER_COUNTRY_GET_BY_LOCALE, new Object[] { newAccount.getCountry() });
				newAccount.setCountryAccount(c);

				if(newAccount.getReference() == null){
					accountForm.generateNewAccountReference();
				}
				newAccount.setAccountForm(newAccount.getEntity().getAccountsForm().get(0));

				AccountDto returnAccount = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_CREATE,
						new Object[] { newAccount });
				accountDetailForm.setAccount(returnAccount);
				
				
				// sauvegarder l'account courant en session
				request.getSession().setAttribute("account", returnAccount);

			} catch (final DCOException e) {
				LOG.error("An error occurred while saving the account", e);
				addWarning(this.propertiesHelper.getMessage("page.param.error.saving"));
			}
		}
		return getMessages().getErrors();
	}

	/**
	 * Check account data
	 */
	private boolean checkAccount(final AccountDto account) {
		boolean error = false;
		if (account.getCurrency().getId() == null || 
				account.getTypeAccount().getId() == null || 
				account.getPeriodicity().getId() == null || 
				account.getChannel() == null) {
			addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
			error = true;
		}
		return error;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// PART 2
	///////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * TEMPORARY : test de r�cup�ration des donn�es du Form
	 * 
	 * @return the page to forward to
	 * @throws DCOException 
	 */
	@RequestMapping(value = "saveSigningStrategy", method = RequestMethod.POST)
	public @ResponseBody List<String> saveSigningStrategy(
			@ModelAttribute("accountDetailForm") final AccountDetailForm accountForm, 
			HttpServletRequest request) throws DCOException {
		resetMessages();
		boolean error=checkAccount(accountForm.getAccount());
		
		List<RulesDto> listRulesDelete = new ArrayList<RulesDto>();
		List<CollegeDto> listCollegeDelete = new ArrayList<CollegeDto>();
		List<SignatoryDto> listSignatoryDelete = new ArrayList<SignatoryDto>();
		if (error) {
			resetMessages();
			addError(this.propertiesHelper.getMessage("page.formulaire.web.message.error.noAccount"));
		} else {
			
			if (!error) {
				accountForm.getAccount().setEditDate(new Date());
				if (accountForm.getAccount().getPourcentage() == 40) {
					accountForm.getAccount().setPourcentage(90);
				}
				
				
				// Delete signatory
				if(accountForm.getAccount().getSignatoriesList() != null && !accountForm.getAccount().getSignatoriesList().isEmpty()){
					List<SignatoryDto> tempListSignatory = new ArrayList<SignatoryDto>();
					tempListSignatory.addAll(accountForm.getAccount().getSignatoriesList());
					for(SignatoryDto s : accountForm.getAccount().getSignatoriesList()){
						if(s.getName() == null || s.getName().equals("")){
							listSignatoryDelete.add(s);
							tempListSignatory.remove(s);
						}
					}
					accountForm.getAccount().setSignatoriesList(tempListSignatory);
					accountDetailForm.setSignatories(tempListSignatory);
				}
				// Delete signatoryGroup and group
				if(accountForm.getAccount().getCollegeList() != null && !accountForm.getAccount().getCollegeList().isEmpty()){
					List<CollegeDto> tempListCollege = new ArrayList<CollegeDto>();
					for(CollegeDto c : accountForm.getAccount().getCollegeList()){
						if(c.getSignatoriesList()!=null && !c.getSignatoriesList().isEmpty()){
							List<SignatoryDto> tempListSignatory = new ArrayList<SignatoryDto>();
							tempListSignatory.addAll(c.getSignatoriesList());
							for(SignatoryDto s : c.getSignatoriesList()){
								if(s.getName() == null || s.getName().equals("") || s.getName().contains("suppr")){
									s.setCollegeList(null);
									listSignatoryDelete.add(s);
									tempListSignatory.remove(s);
								}
							}
							c.setSignatoriesList(tempListSignatory);
						}
						if(c.getName()==null || c.getName().equals("")){
							listCollegeDelete.add(c);
						}
						else{
							tempListCollege.add(c);
						}
					}
					accountForm.getAccount().setCollegeList(tempListCollege);
					accountDetailForm.setColleges(tempListCollege);
				}
				
				// Delete rules
				if(accountForm.getAccount().getRulesList() != null && !accountForm.getAccount().getRulesList().isEmpty()){
					List<RulesDto> tempListRules = new ArrayList<RulesDto>();
					tempListRules.addAll(accountForm.getAccount().getRulesList());
					for(RulesDto r : accountForm.getAccount().getRulesList()){
						if(r.getTypeOperation() == null || r.getTypeOperation().contains("suppr")){	// || r.getTypeOperation().equals("")
							listRulesDelete.add(r);
							tempListRules.remove(r);
						}
					}
					accountForm.getAccount().setRulesList(tempListRules);
					accountDetailForm.setListRules(tempListRules);
				}
				
				if(accountForm.getAccount().getSignatoriesList()!= null && !accountForm.getAccount().getSignatoriesList().isEmpty()){					
					// Save the date of birth
					List<SignatoryDto> signatoryList = new ArrayList<SignatoryDto>();
					for(SignatoryDto s : accountForm.getAccount().getSignatoriesList()){
						final String dateDayString = s.getBirthDay();
		                final String dateMonthString = s.getBirthMonth();
		                final String dateYearString = s.getBirthYear();
		                String dateBirthDayString=null;
		                
		                dateBirthDayString = dateDayString + "/" + dateMonthString + "/" + dateYearString;
		                
		                if (StringUtils.isNotBlank(dateDayString) || StringUtils.isNotBlank(dateMonthString) || StringUtils.isNotBlank(dateYearString)) {
		                    final Date dateParsed = DateUtils.validate(dateBirthDayString);
		                    if (dateParsed == null) {
		                        error = true;
		                        addError(this.propertiesHelper
		                                .getMessage("page.formulaire.web.message.badDateFormat.birthDate"));
		                    } else {
		                        s.setBirthDate(dateParsed);
		                    }
		                }
		            
		              // Save the list of citizenship
		                if(s.getCitizenship() != null){
			                accountDetailForm.setCitizenshipList(new ArrayList<String>());
		                	accountDetailForm.getCitizenshipList().add(s.getCitizenship());
		                	s.setNationality(s.getCitizenship());
		                }
						if (accountDetailForm.getCitizenshipList() != null
		                        && accountDetailForm.getCitizenshipList().size() > Constants.VAR_ZERO) {
		                    // delete all unselected citizenships from the old list only: useful on db storage!
		                    if (s.getCitizenshipsList() != null
		                            && !s.getCitizenshipsList().isEmpty()
		                            && s.getCitizenshipsList().size() > Constants.VAR_ZERO) {
		                        for (final CitizenshipDto citizen : s.getCitizenshipsList()) {
		                            Boolean citizenshipAlreadySaved = false;
		                            for (final String country : accountDetailForm.getCitizenshipList()) {
		                                if (citizen.getCitizenship().getCountry().getCountry().equals(country)) {
		                                    citizenshipAlreadySaved = true;
		                                    break;
		                                }
		                            }
		                            if (!citizenshipAlreadySaved) {
		                                // delete unselected citizenship only
		                                doDeleteCitizenship(citizen.getCitizenship());
		                            }
		                        }
		                    }
		                    // render the citizenships selected into thirdPartyForm.
	                        final List<CitizenshipDto> citizenship = new ArrayList<CitizenshipDto>();
	                        for (final String country : accountDetailForm.getCitizenshipList()) {
	                            final CitizenshipIdDto citizenId = new CitizenshipIdDto();
	                            citizenId.setSignatoryId(s.getId());
	                            citizenId.setCountry(LocaleUtil.stringToCountry(country));
	                            // useful to complete db: pk group.
	                            citizenId.setValue(Constants.CITIZENSHIP_ID_VALUE_OK);
	                            final CitizenshipDto citizen = new CitizenshipDto();
	                            citizen.setCitizenship(citizenId);
	                            citizen.setSignatory(s);
	                            citizenship.add(citizen);
	                        }
	                        s.setCitizenshipsList(citizenship);
						}
	                    signatoryList.add(s);
					}
					accountForm.getAccount().setSignatoriesList(signatoryList);
					
					
				}	
				
				try {
					
					AccountDto returnAccount = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_UPDATE,
							new Object[] { accountForm.getAccount() });	
					
					/* Suppression des signataires, collèges, et rules */
					if(!listRulesDelete.isEmpty()){
						BusinessHelper.call(Constants.CONTROLLER_RULES, Constants.CONTROLLER_RULES_DELETE,
								new Object[] { listRulesDelete });
					}
		        	
					if(!listSignatoryDelete.isEmpty()){
						BusinessHelper.call(Constants.CONTROLLER_SIGNATORY, Constants.CONTROLLER_SIGNATORY_DELETE,
								new Object[] { listSignatoryDelete });
					}
		        	
					if(!listCollegeDelete.isEmpty()){
			        	BusinessHelper.call(Constants.CONTROLLER_COLLEGE, Constants.CONTROLLER_COLLEGE_DELETE,
							new Object[] { listCollegeDelete });	
					}
					
					if (!listRulesDelete.isEmpty() || !listSignatoryDelete.isEmpty() || !listCollegeDelete.isEmpty()) {
						returnAccount = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT,
								Constants.CONTROLLER_ACCOUNT_BY_ID,
								new Object[] { returnAccount.getId() });
					}
					
					returnAccount.setEntity(accountDetailForm.getEntity());
					accountDetailForm.setAccount(returnAccount);
					
					// sauvegarder l'account courant en session
					request.getSession().setAttribute("account", returnAccount);
						
				} catch (final DCOException e) {
					LOG.error("An error occurred while saving the signatory", e);
					addError(this.propertiesHelper.getMessage("page.param.error.saving"));
				}
			}
			else{
				addError(this.propertiesHelper.getMessage("page.param.error.saving"));
			}
		}

		return getMessages().getErrors();
	}

	/**
	 * Save the contacts from part 4
	 * 
	 * @return the page to forward to
	 */
	@RequestMapping(value = "savePart4", method = RequestMethod.POST)
	public @ResponseBody List<String> savePart4(
			@ModelAttribute("accountDetailForm") final AccountDetailForm accountForm) {
		boolean error = checkSignatory(accountDetailForm.getNewSignatory());
		if (!error) {
			try {
				// TEMPORARY ///
				final SimpleDateFormat formatFR = new SimpleDateFormat("dd/mm/yyyy");
				Date birthdate = formatFR.parse(accountDetailForm.getNewSignatory().getBirthDay() + "/"
						+ accountDetailForm.getNewSignatory().getBirthMonth() + "/"
						+ accountDetailForm.getNewSignatory().getBirthYear());
				accountForm.getNewSignatory().setBirthDate(birthdate);
				////////////////////

				BusinessHelper.call(Constants.CONTROLLER_SIGNATORY, Constants.CONTROLLER_SIGNATORY_SAVE,
						new Object[] { accountDetailForm.getNewSignatory() });
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final DCOException e) {
				LOG.error("An error occurred while saving the contact", e);
				addWarning(this.propertiesHelper.getMessage("page.param.error.saving"));
			}
		}
		return getMessages().getErrors();
	}

	/**
	 * Check signatory data
	 */
	private boolean checkSignatory(final SignatoryDto signatory) {
		boolean error = false;
		if (StringUtils.isBlank(signatory.getName()) || StringUtils.isBlank(signatory.getFirstname())
				|| StringUtils.isBlank(signatory.getCitizenship()) || StringUtils.isBlank(signatory.getBirthDay())
				|| StringUtils.isBlank(signatory.getBirthMonth()) || StringUtils.isBlank(signatory.getBirthYear())
				|| StringUtils.isBlank(
						signatory.getBirthPlace())/*
													 * || StringUtils.isBlank(
													 * signatory.getEmail())
													 */
				|| StringUtils.isBlank(signatory.getTel())
				|| StringUtils.isBlank(signatory.getHomeAddress().getFieldOne())
				|| StringUtils.isBlank(signatory.getHomeAddress().getFieldTwo())
				|| StringUtils.isBlank(signatory.getHomeAddress().getFieldThree())
				|| StringUtils.isBlank(signatory.getHomeAddress().getFieldFour())) {
			addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
			error = true;
		}
		return error;
	}

	/**
	 * Check group data
	 */
	private boolean checkCollege(final CollegeDto College) {
		boolean error = false;
		if (StringUtils.isBlank(College.getName())) {
			addError(this.propertiesHelper.getMessage("page.formulaire.empty.required.field.error"));
			error = true;
		}
		return error;
	}
	
	private void doDeleteCitizenship(final CitizenshipIdDto id) throws DCOException {
        BusinessHelper.call(Constants.CONTROLLER_CITIZENSHIP, Constants.CONTROLLER_CITIZENSHIP_DELETE,
                new Object[] {id});
    }
	

	/////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// PART 3
	///////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "getListRepresentativeId")
	public @ResponseBody List<String> getListRepresentativeId(HttpServletRequest request,
			@ModelAttribute("accountDetailForm") final AccountDetailForm accountForm) {
		resetMessages();
		boolean error=false;
		
		String[] setListRepresentativeIDSelectedString = request.getParameterValues("listRepresentativeSelectedInt");
		
		if(setListRepresentativeIDSelectedString != null){
			List<String> tmp = new ArrayList<String>();
			for(int i=0; i<setListRepresentativeIDSelectedString.length; i++){
				tmp.add(setListRepresentativeIDSelectedString[i]);
			}
			
			int cpt=0;
			for(String s : tmp){
				String[] parts = s.split(";");
				setListRepresentativeIDSelectedString[cpt] = parts[0];
				cpt++;			
			}		
			
			if(setListRepresentativeIDSelectedString != null){
				for(int i=0; i<setListRepresentativeIDSelectedString.length; i++){
					for(RepresentativeDto rep : accountDetailForm.getEntity().getRepresentativesList()){
						if(Integer.parseInt(setListRepresentativeIDSelectedString[i]) == rep.getId()){
							if(rep.getCountryList() == null || rep.getCountryList().isEmpty()){
								rep.setCountryList(new ArrayList<CountryDto>());
							} 
							if(accountDetailForm.getAccount().getCountryAccount().getRepresentatives() == null || accountDetailForm.getAccount().getCountryAccount().getRepresentatives().isEmpty()){
								accountDetailForm.getAccount().getCountryAccount().setRepresentatives(new ArrayList<RepresentativeDto>());
							}
							if(!rep.getCountryList().contains(accountDetailForm.getCountry()) && !accountDetailForm.getListRepresentativeSelected().contains(rep)){
								rep.getCountryList().add(accountDetailForm.getCountry());
								accountDetailForm.getAccount().getCountryAccount().getRepresentatives().add(rep);								
								accountDetailForm.getListRepresentativeSelected().add(rep);
							}
						}
					}
				}
			}
		}
		else{
			error = true;
			addError(this.propertiesHelper.getMessage("page.form.download.error.no.representatives"));
		}
		
		if (!error && accountForm.getAccount().getPourcentage() == 90) {
			accountForm.getAccount().setPourcentage(100);
		}
		
		if(!error){
			try {
				//Update representatives
				for(RepresentativeDto r : accountDetailForm.getListRepresentativeSelected()){
					List<EntityDto> representativeEntities = new ArrayList<EntityDto>();
					representativeEntities.add(accountDetailForm.getEntity());
					r.setEntityList(representativeEntities);
					BusinessHelper.call(Constants.CONTROLLER_REPRESENTATIVE, Constants.CONTROLLER_REPRESENTATIVE_SAVE,
							new Object[] { r });
				}
				
				//Update account
				AccountDto returnAccount = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_SIMPLE_UPDATE,
						new Object[] { accountForm.getAccount() });	
				accountDetailForm.setAccount(returnAccount);
				
				// sauvegarder l'account courant en session
				request.getSession().setAttribute("account", returnAccount);
			} catch (final DCOException e) {
				LOG.error("An error occurred while saving the signatory", e);
				addError(this.propertiesHelper.getMessage("page.param.error.saving"));
			}
		}
		
		return getMessages().getErrors();
	}
	

	/***************************************************************************************************************
	 *************************************************** ____PDF____ ***********************************************
	 **************************************************************************************************************/

	/**
	 * Controller for displaying the help page to the right anchor
	 * 
	 * @param id
	 *            the id of the anchor
	 * @return the built link to redirect to
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 * @throws DCOException 
	 */
	@RequestMapping(value = "newFormDownload")
	// public @ResponseBody List<String> newFormDownload(final HttpServletResponse response, HttpServletRequest request,
	public final String newFormDownload(final HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("accountDetailForm") final AccountDetailForm accountForm) throws FileNotFoundException, DocumentException, DCOException {
		resetMessages();
		
		if (accountForm.getAccount().getPourcentage() < 90) {
			addError(this.propertiesHelper.getMessage("page.formulaire.web.message.error.missingDataToDownload"));
			return WebConstants.NEW + WebConstants.DETAIL_ACCOUNT;
		}
		//Mise a jour du pourcentage du compte
		if (accountForm.getAccount().getPourcentage() == 90) {
			accountForm.getAccount().setPourcentage(100);
			
			try {
				//Update account
				AccountDto returnAccount = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_UPDATE,
						new Object[] { accountForm.getAccount() });	
				accountDetailForm.setAccount(returnAccount);
					
			} catch (final DCOException e) {
				LOG.error("An error occurred while saving the signatory", e);
				addError(this.propertiesHelper.getMessage("page.param.error.saving"));
			}
		}
		
		AccountDto returnAccount = (AccountDto) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT,
				Constants.CONTROLLER_ACCOUNT_BY_ID,
				new Object[] { accountDetailForm.getAccount().getId() });
		
		accountDetailForm.setAccount(returnAccount);
		
		//R�cup�rer les comptes choisit
		accountDetailForm.setListAccountSelected(new ArrayList<AccountDto>());
		accountDetailForm.getListAccountSelected().add(accountDetailForm.getAccount());
		
		//String[] setListAccountIDSelectedString = request.getParameterValues("listAccountSelectedInt");
		
		if(accountDetailForm.getListAccountSelectedIDS() != null && !accountDetailForm.getListAccountSelectedIDS().isEmpty()){
			for(int i=0; i<accountDetailForm.getListAccountSelectedIDS().size(); i++){
			//for(Integer id : accountDetailForm.getListAccountSelectedID()){
				for(AccountDto accountCountryChecked : accountDetailForm.getListAccountCountry()){
					String idString = accountDetailForm.getListAccountSelectedIDS().get(i);
					if (idString != null) {
						Integer id = Integer.parseInt(idString);
						if(id == accountCountryChecked.getId().intValue()){
							accountDetailForm.getListAccountSelected().add(accountCountryChecked);
						}
					}
				}
			}
		}
		
		//if (accountForm.getAccount().getStrategyDocument().equals("Yes")) {
			if(accountDetailForm.getListAccountSelected() != null && !accountDetailForm.getListAccountSelected().isEmpty()){
				for(AccountDto a : accountDetailForm.getListAccountSelected()){
					if(!a.getStrategyDocument().equals(accountForm.getAccount().getStrategyDocument())){
						addError(this.propertiesHelper.getMessage("page.formulaire.web.message.error.not.same.account.specification"));
						return WebConstants.NEW + WebConstants.DETAIL_ACCOUNT;
					}
				}
			}
		//}
//		else if (accountForm.getAccount().getStrategyDocument().equals("No")) {
//			if(accountDetailForm.getListAccountCountry() != null && !accountDetailForm.getListAccountCountry().isEmpty()){
//				for(AccountDto a : accountDetailForm.getListAccountCountry()){
//					if(!a.getStrategyDocument().equals("No")){
//						addError(this.propertiesHelper.getMessage("Please select accounts with booklets"));
//						return WebConstants.NEW + WebConstants.DETAIL_ACCOUNT;
//					}
//				}
//			}
//		}
		
		ServletOutputStream sos = null;
        try{
        	
        	final UserSession userSession = UserHelper.getUserInSession();

            // Map holding the PDF beans for each country
            final Map<String, PDFBean> mapCountryBeans = new HashMap<String, PDFBean>();
            final Map<String, Boolean> mapCountryAuthorizedSignatories = new HashMap<String, Boolean>();
            final Map<String, Boolean> mapCountrySignatureCard = new HashMap<String, Boolean>();
            final Map<String, List<PDFBeanAccount>> mapCountryAccountBeans = new HashMap<String, List<PDFBeanAccount>>();
            List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
        	
          //R�cup list pays
        	if (accountDetailForm.getListAccountSelected() != null) {
                BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNT_STATS_PRINTED_DOCS,
                        new Object[] {accountDetailForm.getListAccountSelected()});
                // USEFUL TO GET LEGAL ENTITY FOR COUNTRY!
                countryDtoList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                        Constants.CONTROLLER_LIST, null);
            }
        	
	//		if (!controlBeforeDownload()) {
	//			addWarning(this.propertiesHelper.getMessage("page.param.error.saving"));
	//			return getMessages().getErrors();
	//        }
		
            
        	//R�cup tout les comptes
        	for (final AccountDto account : accountDetailForm.getListAccountSelected()) {
        		boolean authorizedSignatoryFlag = false;
                boolean signatureCardFlag = false;
        		if (account.getId() != null) {
        			
        			account.setEntity(accountDetailForm.getEntity());
        			final String accountCountry = account.getCountryAccount().getLocale().getCountry();
        			
        			List<PDFBeanAccount> accountsForCountry = mapCountryAccountBeans.get(accountCountry);
                    if (accountsForCountry == null) {
                        accountsForCountry = new ArrayList<PDFBeanAccount>();
                        mapCountryAccountBeans.put(accountCountry, accountsForCountry);
                    }
                    
        			final PDFBeanAccount accountBean = new PDFBeanAccount();
        			accountBean.setId(account.getId());
        			accountBean.setCountry(account.getCountryAccount().getLocale());
                    accountBean.setName(account.getName());
                    accountBean.setReference(account.getReference());
                    accountBean.setType(account.getTypeAccount() != null ? account.getTypeAccount().getValue()
                            : "");
                    accountBean.setCurrency(account.getCurrency() != null ? account.getCurrency().getValue() : "");
                    accountBean.setStatementPeriodicity(account.getPeriodicity() != null ? account
                            .getPeriodicity().getEntry() : "");
                    if (account.getBranchName() != null || account.getAddress() != null
                            || account.getCommercialRegister() != null || account.getVatNumber() != null) {
                        accountBean.setAddress(buildPDFBeanAdress(account.getAddress()));

                        accountBean.setCommercialRegister(account.getCommercialRegister());
                        accountBean.setVatNumber(account.getVatNumber());
                        accountBean.setBranchName(account.getBranchName());
                    }
                    accountBean.setComLangEnabled(account.getCountryAccount().isCom_lang_enabled());
                    accountBean.setCommunicationLanguage(account.getCommunicationLanguage());
                    accountBean.setChannel(account.getChannel());
                    accountBean.setStrategyDocument(account.getStrategyDocument());
                    
                    boolean booklet = account.getStrategyDocument().equals("Yes");
                    
                    final List<PDFBeanSignatory> signatoryList = new ArrayList<PDFBeanSignatory>();
                    if (!booklet && account.getSignatoriesList() != null && !account.getSignatoriesList().isEmpty()) {
                    	 for (final SignatoryDto signatory : account.getSignatoriesList()) {
                    		 PDFBeanSignatory signatoryBean = getPdfBeanSignatoryById(signatoryList,
                    				 signatory.getId());
                    		 if (signatoryBean == null) {
                    			 signatoryBean = new PDFBeanSignatory();
                    			 signatoryBean.setId(signatory.getId());
                    			 signatoryBean.setFirstName(signatory.getFirstname());
                    			 signatoryBean.setLastName(signatory.getName());
                                 signatoryBean.setPosition(signatory.getRole());
                                 signatoryBean.setBirthDate(signatory.getBirthDate());
                                 signatoryBean.setBirthPlace(signatory.getBirthPlace());
                                 signatoryBean.setNationality(signatory.getNationality());
                                 signatoryBean.setCitizenships(buildPDFListCitizenship(signatory.getCitizenshipsList()));
                                 signatoryBean.setAddressHome(buildPDFBeanAdress(signatory
                                         .getHomeAddress()));
                                 signatoryBean.setTelephone(signatory.getTel());
                                 signatoryBean.setFax(signatory.getFax());
                                 signatoryBean.setEmail(signatory.getEmail());
                                 signatoryBean.setIDNumber(signatory.getReferenceId());
                                 signatoryBean.setLegalEntityName(signatory
                                         .getLegalEntityName());
                                 signatoryBean.setPdfBeanActingDetails(new ArrayList<PDFBeanActingDetails>());
                                 signatoryList.add(signatoryBean);
                             }
                    	 }
                    	 authorizedSignatoryFlag=true;
                    	 signatureCardFlag = true;
                    }
                    
                    final List<PDFBeanCollege> collegeList = new ArrayList<PDFBeanCollege>();
                    if (!booklet && account.getCollegeList() != null && !account.getCollegeList().isEmpty()) {
                    	 for (final CollegeDto college : account.getCollegeList()) {
                    		 PDFBeanCollege collegeBean = getPdfBeanCollegeById(collegeList,
                    				 college.getId());
                    		 if (collegeBean == null) {
                    			 collegeBean = new PDFBeanCollege();
                    			 collegeBean.setId(college.getId());
                    			 collegeBean.setName(college.getName());
                    			 final List<PDFBeanSignatory> signatoryCollegeList = new ArrayList<PDFBeanSignatory>();
                    			 if (college.getSignatoriesList() != null && !college.getSignatoriesList().isEmpty()) {
                                	 for (final SignatoryDto signatory : college.getSignatoriesList()) {
                                		 PDFBeanSignatory signatoryBeanCollege = getPdfBeanSignatoryById(signatoryCollegeList,
                                				 signatory.getId());
                                		 if (signatoryBeanCollege == null) {
                                			 signatoryBeanCollege = new PDFBeanSignatory();
                                			 signatoryBeanCollege.setId(signatory.getId());
                                			 signatoryBeanCollege.setFirstName(signatory.getFirstname());
                                			 signatoryBeanCollege.setLastName(signatory.getName());
                                			 signatoryBeanCollege.setPosition(signatory.getPositionName());
                                			 signatoryBeanCollege.setBirthDate(signatory.getBirthDate());
                                			 signatoryBeanCollege.setBirthPlace(signatory.getBirthPlace());
                                			 signatoryBeanCollege.setCitizenships(buildPDFListCitizenship(signatory.getCitizenshipsList()));
                                			 signatoryBeanCollege.setNationality(signatory.getNationality());
                                			 signatoryBeanCollege.setAddressHome(buildPDFBeanAdress(signatory
                                                     .getHomeAddress()));
                                			 signatoryBeanCollege.setTelephone(signatory.getTel());
                                			 signatoryBeanCollege.setFax(signatory.getFax());
                                			 signatoryBeanCollege.setEmail(signatory.getEmail());
                                			 signatoryBeanCollege.setIDNumber(signatory.getReferenceId());
                                			 signatoryBeanCollege.setLegalEntityName(signatory
                                                     .getLegalEntityName());
                                			 signatoryBeanCollege.setPdfBeanActingDetails(new ArrayList<PDFBeanActingDetails>());
                                			 signatoryCollegeList.add(signatoryBeanCollege);
                                         }
                                	 }
                    			 }
                    			 collegeBean.setSignatoriesList(signatoryCollegeList);
                    			 collegeList.add(collegeBean);
                             }
                    	 }
                    }
                    
                    final List<PDFBeanRepresentative> representativeList = new ArrayList<PDFBeanRepresentative>();
                    if (accountDetailForm.getListRepresentativeSelected() != null && !accountDetailForm.getListRepresentativeSelected().isEmpty()) {
                    	 for (final RepresentativeDto representative : accountDetailForm.getListRepresentativeSelected()) {
                    		 PDFBeanRepresentative representativeBean = getPdfBeanRepresentativeById(representativeList,
                    				 representative.getId());
                    		 if (representativeBean == null) {
                    			 representativeBean = new PDFBeanRepresentative();
                    			 representativeBean.setId(representative.getId());
                    			 representativeBean.setFirstname(representative.getFirstname());
                    			 representativeBean.setName(representative.getName());
                    			representativeList.add(representativeBean);
                             }
                    	 }
                    }
                    
                    final PDFBeanContact contact1 = new PDFBeanContact();
                    final PDFBeanContact contact2 = new PDFBeanContact();
                    if (account.getEntity().getContact1() != null) {
                    	
                    	contact1.setFirstname(account.getEntity().getContact1().getFirstname());
                    	contact1.setName(account.getEntity().getContact1().getName());
                    	contact1.setPositionName(account.getEntity().getContact1().getPositionName());
                    	contact1.setTel(account.getEntity().getContact1().getTel());
                    	contact1.setFax(account.getEntity().getContact1().getFax());
                        contact1.setMail(account.getEntity().getContact1().getMail());
                    }
                    if (account.getEntity().getContact2() != null) {
                    	
                    	contact2.setFirstname(account.getEntity().getContact2().getFirstname());
                    	contact2.setName(account.getEntity().getContact2().getName());
                    	contact2.setPositionName(account.getEntity().getContact2().getPositionName());
                    	contact2.setTel(account.getEntity().getContact2().getTel());
                    	contact2.setFax(account.getEntity().getContact2().getFax());
                        contact2.setMail(account.getEntity().getContact2().getMail());
                    }
                    
                    final List<PDFBeanRules> rulesList = new ArrayList<PDFBeanRules>();
                    if (!booklet && account.getRulesList() != null && !account.getRulesList().isEmpty()) {
                    	 for (final RulesDto rules : account.getRulesList()) {
                    		 PDFBeanRules rulesBean = getPdfBeanRulesById(rulesList,
                    				 rules.getId());
                    		 if (rulesBean == null) {
                    			 rulesBean = new PDFBeanRules();
                    			 rulesBean.setId(rules.getId());
                    			 rulesBean.setAmountMax(rules.getAmountMax());
                    			 rulesBean.setAmountMin(rules.getAmountMin());
                    			 if( rules.getCollege() != null){
                    				 rulesBean.setCollege(getPdfBeanCollegeById(collegeList,
                    						 rules.getCollege().getId()));
                    			 }else {
                    				 rulesBean.setCollege(null);
                    			 }
                    			 if( rules.getCollege2() != null){
                    				 rulesBean.setCollege2(getPdfBeanCollegeById(collegeList,
                    						 rules.getCollege2().getId()));
                    			 }else {
                    				 rulesBean.setCollege2(null);
                    			 }
                    			 if( rules.getSignatory() != null){
                    				 rulesBean.setSignatory(getPdfBeanSignatoryById(signatoryList,
                    						 rules.getSignatory().getId()));
                    			 }else {
                    				 rulesBean.setSignatory(null);
                    			 }
                    			 if( rules.getSignatory2() != null){
                    				 rulesBean.setSignatory2(getPdfBeanSignatoryById(signatoryList,
                    						 rules.getSignatory2().getId()));
                    			 }else {
                    				 rulesBean.setSignatory2(null);
                    			 }
                    			 rulesBean.setTypeOperation(rules.getTypeOperation());
                    			 rulesBean.setField(rules.getField());
                                 rulesList.add(rulesBean);
                             }
                    	 }
                    }
                    
                    accountBean.setAccountSignatoryList(signatoryList);
                    accountBean.setCollegeList(collegeList);
                    accountBean.setAccountRepresentativeList(representativeList);
                    accountBean.setAccountContact1(contact1);
                    accountBean.setAccountContact2(contact2);
                    accountBean.setRulesList(rulesList);
                    accountsForCountry.add(accountBean);
                    
                    final Boolean as = mapCountryAuthorizedSignatories.get(accountCountry);
                    mapCountryAuthorizedSignatories.put(accountCountry, as == null ? authorizedSignatoryFlag
                            : authorizedSignatoryFlag || as);
                    
                    final Boolean sc = mapCountrySignatureCard.get(accountCountry);
                    mapCountrySignatureCard.put(accountCountry, sc == null ? signatureCardFlag : signatureCardFlag
                            || sc);
                    
                    PDFBean beanForCountry = mapCountryBeans.get(accountCountry);
                    if (beanForCountry == null) {
                        beanForCountry = generateBeanForCountry(accountForm, accountCountry, countryDtoList);
                        mapCountryBeans.put(accountCountry, beanForCountry);
                    }
        		}
        	}
        	
            //Download PDF
        	ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        	String fileName = "";
        	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream(baos);
            
        	for (final Map.Entry<String, PDFBean> entry : mapCountryBeans.entrySet()) {
        		final String country = entry.getKey();
                final PDFBean bean = entry.getValue();
                final List<PDFBeanAccount> accounts = mapCountryAccountBeans.get(country);
                bean.setAccounts(accounts);
                bean.setAuthorizedSignatories(mapCountryAuthorizedSignatories.get(country));
                bean.setSignatureCard(mapCountrySignatureCard.get(country));
                bean.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
                bean.setXbasV2(userSession.isXbasV2());
                
//                List<byte[]> docs = new ArrayList<byte[]>();
                
                pdf = this.pdfGenerator.createPdf(bean);
    			
    			final String countryName = new Locale("", country).getDisplayCountry(LocaleUtil
                        .stringToLanguage("GB"));
            	//final String countryName = new Locale("", "FR").getDisplayCountry(LocaleUtil.stringToLanguage("FR"));
    			fileName = this.pdfGenerator.getFilename(bean);
    			fileName = countryName + Constants.SPACE + fileName; 
                
    			ZipEntry ze = new ZipEntry(new StringBuilder(countryName).append(File.separator).append(fileName)
                        .append(".pdf").toString());
                zos.putNextEntry(ze);
                zos.write(pdf.toByteArray());
                // Adding the documents for the country
                for (final Map.Entry<Locale, List<DocumentDto>> entryCountryDocs : accountDetailForm.getMapCountryDocs().entrySet()) {
                    if (entryCountryDocs.getKey().getCountry().equals(country)) {
                        for (final DocumentDto doc : entryCountryDocs.getValue()) {
                            ze = new ZipEntry(new StringBuilder(countryName).append(File.separator)
                                    .append(doc.getTitle()).toString());
                            zos.putNextEntry(ze);
                            zos.write(doc.getData());
                        }
                        break;
                    }
                }  		
        	}
        	
        	zos.closeEntry();
            zos.close();
            // Sending response
            sos = response.getOutputStream();
            // Writing the content to the output stream
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Pragma", "private");
            response.setHeader("Expires", "0");
            response.setHeader("Content-Disposition", "attachment;filename=\"Forms.zip\"");
            IOUtils.copy(new ByteArrayInputStream(baos.toByteArray()), sos);
        	        
//            response.reset();
//            response.setContentType("application/pdf");
//            response.setHeader("FtpReplay Template", "content-description");
//            response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
//            
//            response.getOutputStream().write(pdf.toByteArray(), 0, pdf.toByteArray().length);
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
	      
       }catch(IOException | DCOException ex){
          ex.printStackTrace(); 
          addWarning(this.propertiesHelper.getMessage("page.param.error.saving"));
       }finally {
           if (sos != null) {
               try {
                   sos.close();
               } catch (final IOException e) {
                   LOG.warn("Error while closing the PDF generation output stream => Proceeding");
               }
           }
       }
			
		//return getMessages().getErrors();
		return WebConstants.NEW + WebConstants.DETAIL_ACCOUNT;
	}

	private boolean controlBeforeDownload() {
		boolean result = true;
		if (accountDetailForm.getListAccountSelected() == null
				|| accountDetailForm.getListAccountSelected().isEmpty()) {
			addError(this.propertiesHelper.getMessage("page.form.download.error.no.account"));
			result = false;
		} else {
			boolean hasAssoc = false;
			for (final AccountDto account : accountDetailForm.getListAccountSelected()) {
				if (account.getId() != null && account.getSignatoriesList() != null) {
					for (final SignatoryDto atp : account.getSignatoriesList()) {
						if (atp != null) {
							hasAssoc = true;
							break;
						}
					}
				}
				if (hasAssoc) {
					break;
				}
			}
			if (!hasAssoc) {
				//addError(this.propertiesHelper.getMessage("page.form.download.error.no.association"));
				result = false;
			}
		}
		return result;
	}

	/**
	 * @param addressDto
	 */
	private PDFBeanAddress buildPDFBeanAdress(final AddressDto addressDto) {
		final PDFBeanAddress pdfBeanAddress = new PDFBeanAddress();
		if (addressDto != null) {
			pdfBeanAddress.setLine1(addressDto.getFieldOne());
			pdfBeanAddress.setLine2(addressDto.getFieldTwo());
			pdfBeanAddress.setLine3(addressDto.getFieldThree());
			pdfBeanAddress.setLine4(addressDto.getFieldFour());
			pdfBeanAddress.setLine5(addressDto.getFieldFive());
			pdfBeanAddress.setLine6(addressDto.getFieldSix());
			pdfBeanAddress.setLine7(addressDto.getFieldSeven());
		}
		return pdfBeanAddress;
	}

	private PDFBeanSignatory getPdfBeanSignatoryById(final List<PDFBeanSignatory> beansignatories, final Integer id) {
		PDFBeanSignatory beanSignatory = null;
		if (id != null) {
			for (final PDFBeanSignatory pdfBeanSignatory : beansignatories) {
				if (pdfBeanSignatory.getId().equals(id)) {
					beanSignatory = pdfBeanSignatory;
					break;
				}
			}
		}
		return beanSignatory;
	}
	
	private PDFBeanCollege getPdfBeanCollegeById(final List<PDFBeanCollege> beancolleges, final Integer id) {
		PDFBeanCollege beanCollege = null;
		if (id != null) {
			for (final PDFBeanCollege pdfBeanCollege : beancolleges) {
				if (pdfBeanCollege.getId().equals(id)) {
					beanCollege = pdfBeanCollege;
					break;
				}
			}
		}
		return beanCollege;
	}

	private PDFBeanRepresentative getPdfBeanRepresentativeById(final List<PDFBeanRepresentative> beanrepresentative,
			final Integer id) {
		PDFBeanRepresentative beanRepresentative = null;
		if (id != null) {
			for (final PDFBeanRepresentative pdfBeanRepresentative : beanrepresentative) {
				if (pdfBeanRepresentative.getId().equals(id)) {
					beanRepresentative = pdfBeanRepresentative;
					break;
				}
			}
		}
		return beanRepresentative;
	}

	private PDFBeanRules getPdfBeanRulesById(final List<PDFBeanRules> beanrules, final Integer id) {
		PDFBeanRules beanRules = null;
		if (id != null) {
			for (final PDFBeanRules pdfBeanRules : beanrules) {
				if (pdfBeanRules.getId().equals(id)) {
					beanRules = pdfBeanRules;
					break;
				}
			}
		}
		return beanRules;
	}
	
	private List<Locale> buildPDFListCitizenship(final List<CitizenshipDto> citizenshipDtos) {
		final List<Locale> citizenships = new ArrayList<Locale>();
		if (citizenshipDtos != null) {
			for (final CitizenshipDto citizenship : citizenshipDtos) {
				citizenships.add(citizenship.getCitizenship().getCountry());
			}
		}
		return citizenships;
	}
	
	private PDFBean generateBeanForCountry(final AccountDetailForm userSession, final String country,
            final List<CountryDto> countriesList) throws DCOException {
        final PDFBean bean = new PDFBean();
        bean.setCountry(LocaleUtil.stringToCountry(country));
        bean.setLanguage(new Locale("en", "GB"));
        bean.setDateFormat(new SimpleDateFormat(new DateFormatDto(1).getLabelLong(), new Locale("en", "GB")));
        final PDFBeanEntity entity = new PDFBeanEntity();
        bean.setEntity(entity);
        entity.setName(userSession.getEntity().getLabel());
        // got it from account country
        entity.setCountry(bean.getCountry());
        // retrieve and set legal entity data from countries list
        if (countriesList != null) {
            for (final CountryDto localCountry : countriesList) {
                if (localCountry.getLocale() != null
                        && bean.getEntity().getCountry().getDisplayCountry(bean.getLanguage())
                                .equals(localCountry.getLocale().getDisplayCountry(bean.getLanguage()))) {
                    entity.setLegalEntity(localCountry.getLegalEntity());
                    break;
                }
            }
        }
        // set countries in which the Acc. Opened Request is enabled.
        entity.setCountriesList(countriesList);

        entity.setTaxInfo(getAccountDetailForm().getEntity().getTaxInformation());
        entity.setAddressHeadQuarters(buildPDFBeanAdress(getAccountDetailForm().getEntity().getAddress()));
        entity.setAddressPostal(buildPDFBeanAdress(getAccountDetailForm().getEntity().getAddressMailing()));

        entity.setContact1(buildPDFBeanContactLight(getAccountDetailForm().getEntity().getContact1()));
        entity.setContact2(buildPDFBeanContactLight(getAccountDetailForm().getEntity().getContact2()));

        final List<PDFBeanRepresentative> legalRepresentativesList = new ArrayList<PDFBeanRepresentative>();
        if (accountDetailForm.getListRepresentativeSelected() != null) {
            for (final RepresentativeDto representative : accountDetailForm.getListRepresentativeSelected()) {
                legalRepresentativesList.add(buildPDFBeanRepresentativeLight(representative));
            }
        }
        entity.setRepresentativeList(legalRepresentativesList);

        // set the signatory list on entity bean.
        if (getAccountDetailForm().getAccount().getSignatoriesList() != null && !getAccountDetailForm().getAccount().getSignatoriesList().isEmpty()) {
            final List<PDFBeanSignatory> signatoryList = new ArrayList<PDFBeanSignatory>();
            for (final SignatoryDto signatory : getAccountDetailForm().getAccount().getSignatoriesList()) {
            	signatoryList.add(buildPDFBeanSignatoryLight(signatory));
            }
            entity.setSignatoryList(signatoryList);
        }

        final String legalForm = getAccountDetailForm().getEntity().getLegalStatus().getEntry();
        entity.setLegalForm(legalForm.equalsIgnoreCase(Constants.PARAM_TYPE_LEGAL_STATUS_OTHER) ? getAccountDetailForm()
                .getEntity().getLegalStatusOther() : getAccountDetailForm().getEntity().getLegalStatus().getValue());
        entity.setRegistrationNb(getAccountDetailForm().getEntity().getCommercialRegister());
        entity.setRegistrationCountry(LocaleUtil
                .stringToCountry(getAccountDetailForm().getEntity().getRegistrationCountry()));
        entity.setBoardResolutionDate(getAccountDetailForm().getEntity().getBoardResolutionDate());
        entity.setNotaryName(getAccountDetailForm().getEntity().getNotaryName());
        entity.setNotaryCity(getAccountDetailForm().getEntity().getNotaryCity());
        entity.setIssuanceDate(getAccountDetailForm().getEntity().getIssuanceDate());
        entity.setPublicDeedReference(getAccountDetailForm().getEntity().getPublicDeedReference());
        entity.setMercantileInscriptionDate(getAccountDetailForm().getEntity().getMercantileInscriptionDate());
        entity.setMercantileInscriptionNumber(getAccountDetailForm().getEntity().getMercantileInscriptionNumber());
        return bean;
    }
	
	private PDFBeanSignatory buildPDFBeanSignatoryLight(final SignatoryDto signatory) {
        if (signatory != null) {
            final PDFBeanSignatory beanSignatory = new PDFBeanSignatory();
            beanSignatory.setFirstName(signatory.getFirstname());
            beanSignatory.setLastName(signatory.getName());
            beanSignatory.setPosition(signatory.getPositionName());
            beanSignatory.setBirthDate(signatory.getBirthDate());
            beanSignatory.setBirthPlace(signatory.getBirthPlace());
            beanSignatory.setCitizenships(buildPDFListCitizenship(signatory.getCitizenshipsList()));
            beanSignatory.setAddressHome(buildPDFBeanAdress(signatory.getHomeAddress()));
            beanSignatory.setTelephone(signatory.getTel());
            beanSignatory.setFax(signatory.getFax());
            beanSignatory.setEmail(signatory.getEmail());
            beanSignatory.setIDNumber(signatory.getReferenceId());
            beanSignatory.setLegalEntityName(signatory.getLegalEntityName());
            beanSignatory.setId(signatory.getId());
            return beanSignatory;
        }
        return null;
    }
	
	 private PDFBeanContact buildPDFBeanContactLight(final ContactDto contact) {
        if (contact != null) {
            final PDFBeanContact beanContact = new PDFBeanContact();
            beanContact.setFirstname(contact.getFirstname());
            beanContact.setName(contact.getName());
            beanContact.setPositionName(contact.getPositionName());
            beanContact.setTel(contact.getTel());
            beanContact.setFax(contact.getFax());
            beanContact.setMail(contact.getMail());
            beanContact.setId(contact.getId());
            return beanContact;
        }
        return null;
	 }
	 
	 private PDFBeanRepresentative buildPDFBeanRepresentativeLight(final RepresentativeDto representative) {
        if (representative != null) {
            final PDFBeanRepresentative beanRepresentative = new PDFBeanRepresentative();
            beanRepresentative.setFirstname(representative.getFirstname());
            beanRepresentative.setName(representative.getName());
            beanRepresentative.setId(representative.getId());
            return beanRepresentative;
        }
        return null;
	 }
}
