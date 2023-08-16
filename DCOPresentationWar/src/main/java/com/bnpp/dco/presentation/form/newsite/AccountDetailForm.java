package com.bnpp.dco.presentation.form.newsite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.dto.DocumentTypeDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.controller.ParamController;
import com.bnpp.dco.presentation.form.FormSummaryForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("accountDetailForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountDetailForm implements Serializable {
	/** Serial UID */
	private static final long serialVersionUID = -7604719834109605281L;
	
	/** Logger (named on purpose after the form). */
    private static final Logger LOG = LoggerFactory.getLogger(AccountDetailForm.class);
    
    /** Generic */
    private EntityDto entity;
    private CountryDto country;
    private List<LanguageDto> prefLangList;
    private String countryFullName;
    
 // Elements for downloading the forms
    private Map<Locale, List<DocumentDto>> mapCountryDocs;
	
    /** ACCOUNT */
	private AccountDto account;
    private AccountFormDto accountForm;
    private Map<String, List<ParamFuncDto>> mapCurrency = new HashMap<String, List<ParamFuncDto>>();
	private List<ParamFuncDto> currencyList = new ArrayList<ParamFuncDto>();
    private List<ParamFuncDto> typeAccountList = new ArrayList<ParamFuncDto>();
    private List<ParamFuncDto> periodicityList = new ArrayList<ParamFuncDto>();
    private List<ParamFuncDto> channelList = new ArrayList<ParamFuncDto>();
    private List<AccountDto> listAccountCountry;
    
    private List<String> selectedCountries = new ArrayList<String>();
    private FormSummaryForm summary = new FormSummaryForm();
    
    /** SIGNATORY */
    private List<SignatoryDto> signatories;
    private List<SignatoryDto> signatoriesToResume;
    private SignatoryDto newSignatory;
    private Integer nbSignatories;
    private Integer nbSignatories2;
    private List<String> citizenshipList;
    private List<Locale> countryList;
    
    /** COLLEGE */
    private List<CollegeDto> colleges;
    private CollegeDto newCollege;
    private Integer nbColleges;
    private Integer nbColleges2;
    private Integer nbSignatoryByColleges;
    private Integer nbCollegesBySignatory;
    
    /** RULES */
    private List<RulesDto> listRules;
    private RulesDto newRules;
    private Integer nbRules;
    private Integer nbRules2;
    
    /** REPRESENTATIVE */
    private List<RepresentativeDto> listRepresentative;
    private RepresentativeDto newRepresentative;
    
    /** HAORF */
    private List<RepresentativeDto> listRepresentativeSelected;
    private List<AccountDto> listAccountSelected;
    private List<Integer> listAccountSelectedID;
    private List<String> listAccountSelectedIDS;
    
    /**OTHER*/
    private Map<String, List<Integer>> mapAccount;
    private Map<String, Boolean> mapCountrySignatureCard;
    
    /**
     * Initialitation des donn�es du formulaire pour un nouveau compte
     */
    public void init(AccountDto accountInit) {
        try {     
        	account = accountInit;
        				
			selectedCountries = (List<String>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST2, null);
        	
        	// Initialisation listes en fonction du pays selectionn� 
			setPrefLangList( (List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                    Constants.CONTROLLER_LANG_LIST_INTERFACE, null));
        	List<String> countries = new ArrayList<String>();
        	countries.add(account.getCountry());
        	setCurrencyList(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_CURRENCY, countries).get(account.getCountry()));
            setTypeAccountList(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_TYPE, countries).get(account.getCountry()));
            setPeriodicityList(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_STATE_PERIOD, countries).get(account.getCountry()));            
            setChannelList(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_STATE_SUPPORT, countries).get(account.getCountry()));  
            
            // Liste des pays
            countryList = new ArrayList<Locale>();
            final Locale[] allLocales = Locale.getAvailableLocales();
            final List<String> existingLocales = new ArrayList<String>();
            for (final Locale l : allLocales) {
                if (!existingLocales.contains(l.getCountry()) && !l.getCountry().equals(Constants.EMPTY_FIELD)) {
                    existingLocales.add(l.getCountry());
                    countryList.add(new Locale("", l.getCountry()));
                }
            }
            
            // Liste des signatories
            signatories = account.getSignatoriesList();
			if (signatories != null) {
				Collections.sort(signatories);
				nbSignatories = signatories.size();
				nbSignatories2 = signatories.size();
			} else {
				nbSignatories = 0;
				nbSignatories2 = 0;
			}
			
			// Liste des colleges
			colleges = account.getCollegeList();
			if (colleges != null) {
	        	Collections.sort(colleges);
				nbColleges = colleges.size();
				nbColleges2 = colleges.size();
			} else {
				nbColleges = 0;
				nbColleges2 = 0;
			}
			for(int i=0; i<nbColleges; i++){
				colleges.get(i).setNbSignatory(colleges.get(i).getSignatoriesList().size()); 
			}
			nbCollegesBySignatory = nbColleges;
			newCollege = new CollegeDto();
			
			signatoriesToResume = new ArrayList<SignatoryDto>();
			
			if(signatories != null && !signatories.isEmpty()){
				List<SignatoryDto> signatoriesTmp = new ArrayList<SignatoryDto>();
				for(SignatoryDto s : signatories){
					if(s.getCollegeList() == null || s.getCollegeList().isEmpty()){
						s.setNbCollege(0);
						signatoriesToResume.add(s);
						signatoriesTmp.add(s);
					}
					else {
						s.setNbCollege(s.getCollegeList().size());
						signatoriesTmp.add(s);
					}
				}
				signatories = signatoriesTmp;
			}
			
			// Liste des r�gles
			listRules = account.getRulesList();
			if (listRules != null) {
				nbRules = listRules.size();
				nbRules2 = listRules.size();
			} else {
				nbRules = 0;
				nbRules2 = 0;
			}
			
			//List des compte du pays
//			listAccountCountry=new ArrayList<AccountDto>();
//			if(entity.getAccountList() != null){
//				for(AccountDto accounts : entity.getAccountList()){
//					if(accounts.getCountryAccount().getLocale().getCountry().equals(country.getLocale().getCountry()) && !accounts.getId().equals(account.getId())){
//						listAccountCountry.add(accounts);
//					}
//				}
//			}	
			
			listAccountCountry = (List<AccountDto>) BusinessHelper.call(Constants.CONTROLLER_ACCOUNT, Constants.CONTROLLER_ACCOUNTS_FROM_COUNTRY,
					new Object[] { country.getLocale().getCountry(), accountForm.getId(), account.getId() });
			
			initListOfDocuments();
			
			//HAORF
			//setCountry();
			listRepresentativeSelected = new ArrayList<RepresentativeDto>();
			if(getEntity().getRepresentativesList() != null && !getEntity().getRepresentativesList().isEmpty()){
				for(RepresentativeDto r : getEntity().getRepresentativesList()){
					if(r.getCountryList() != null && !r.getCountryList().isEmpty()){
						for(CountryDto c : r.getCountryList()){
							if(c.getId().equals(getCountry().getId())){
								listRepresentativeSelected.add(r);
							}
							
						}
					}
				}
			}			
			
		    listAccountSelected = new ArrayList<AccountDto>();
		    listAccountSelectedID = new ArrayList<Integer>();
		    listAccountSelectedIDS = new ArrayList<String>();
            
                        
        } catch (final DCOException e) {
            LOG.error("Error while initializing legal countries for account openings");
        }
    }
    
    /**
     * initialize a map use to display the signature card file name in the document list on summary page
     */
    public void initMapCountrySignatureCard() {
        final Map<String, Boolean> mapCountrySignatureCard = new HashMap<String, Boolean>();
        if (getMapAccount() != null && !getMapAccount().isEmpty()) {
            for (final Entry<String, List<Integer>> entry : getMapAccount().entrySet()) {
                Boolean flag = false;
                for (final Integer index : entry.getValue()) {
                    if (getListAccountSelected().get(index) != null
                            && getListAccountSelected().get(index).getAccountThirdPartyList() != null) {
                        for (final AccountThirdPartyDto accountThirdParty : getListAccountSelected().get(index)
                                .getAccountThirdPartyList()) {
                            if (accountThirdParty != null && accountThirdParty.getPowerType() != null
                                    && accountThirdParty.getPowerType() != Constants.PARAM_CODE_ACC_THIRD_PWR_FR1) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            break;
                        }

                    }
                }
                mapCountrySignatureCard.put(entry.getKey(), flag);
            }
        }
        setMapCountrySignatureCard(mapCountrySignatureCard);
    }
    
    public void initListOfDocuments(){
    	List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
    	
          try {
			countryDtoList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
			          Constants.CONTROLLER_LIST, null);
		
		
			 // Init country languages if necessary
			//for (final AccountDto account : getListAccountCountry()) {
            if (getCountry() != null) {
                final Locale defaultLocale = ParamController.getDefaultLanguage().getLocale();
                List<Locale> langs = getSummary().getMapCountryLangs()
                        .get(getCountry().getLocale().getCountry());
                // If the map of options has not already been initialized for this country
                if (langs == null) {
                    // Looking for the list of languages in a country object that has it
                    for (final CountryDto country : countryDtoList) {
                        if (country.getId().equals(getCountry().getId())) {
                            langs = new ArrayList<Locale>();
                            // Adding the default language
                            langs.add(defaultLocale);
                            if (country.getLanguages() != null) {
                                for (final LanguageDto language : country.getLanguages()) {
                                    // Adding the language if not the default (already added)
                                    if (!defaultLocale.getLanguage()
                                            .equals(language.getLocale().getLanguage())) {
                                        langs.add(language.getLocale());
                                    }
                                }
                            }
                            getSummary().getMapCountryLangs()
                                    .put(getCountry().getLocale().getCountry(), langs);
                            break;
                        }
                    }
                }
                // If the selected language for the country is not in the list, we add an entry
                if (!getSummary().getMapCountrySelectedLang()
                        .containsKey(getCountry().getLocale().getCountry())) {
                	getSummary()
                            .getMapCountrySelectedLang()
                            .put(getCountry().getLocale().getCountry(),
                                    defaultLocale.getLanguage());
                }
            }
	        //}
			// Init documents lists
			final String entityCountry = getEntity().getCountry().getCountry();
	        final Map<Locale, List<DocumentDto>> mapCountryDocs = new TreeMap<Locale, List<DocumentDto>>(
	                new Comparator<Locale>() {
	                    @Override
	                    public int compare(final Locale locale1, final Locale locale2) {
	                        return locale1.getCountry().compareToIgnoreCase(locale2.getCountry());
	                    }
	                });
	        int docCountryId = Constants.VAR_NEG_ONE;
	      int docCommonSupportingId = Constants.VAR_NEG_ONE;
	      if (entityCountry != null) {
	          for (final CountryDto country : countryDtoList) {
	              if (country.getLocale().getCountry().equalsIgnoreCase(entityCountry)) {
	                  docCountryId = country.getId();
	              }
	          }
	          final List<DocumentTypeDto> documentTypeList = (List<DocumentTypeDto>) BusinessHelper.call(
	                  Constants.CONTROLLER_DOCUMENT_TYPE, Constants.CONTROLLER_LIST, new Object[] {});
	          for (final DocumentTypeDto documentType : documentTypeList) {
	              if (Constants.PARAM_TYPE_DOC_COMMON_SUPPORTING.equalsIgnoreCase(documentType.getLabel())) {
	                  docCommonSupportingId = documentType.getId();
	                  break;
	              }
	          }
	      }
	      if(getListAccountCountry() != null && !getListAccountCountry().isEmpty()){
		      for (final AccountDto account : getListAccountCountry()) {
		      	if (account.getId() != null && account.getCountryAccount() != null) {
	                  boolean resident = false;
	                  if (entityCountry.equals(account.getCountryAccount().getLocale().getCountry())) {
	                      resident = true;
	                  }
	                  final String selectedLang = getSummary().getMapCountrySelectedLang()
	                          .get(account.getCountryAccount().getLocale().getCountry());
	                  Integer selectedLangId = null;
	                  if (StringUtils.isNotBlank(selectedLang) && countryDtoList != null) {
	                      for (final CountryDto country : countryDtoList) {
	                          if (country.getId().equals(account.getCountryAccount().getId())) {
	                              if (country.getLanguages() != null) {
	                                  for (final LanguageDto language : country.getLanguages()) {
	                                      if (selectedLang.equals(language.getLocale().getLanguage())) {
	                                          selectedLangId = language.getId();
	                                          break;
	                                      }
	                                  }
	                              }
	                              break;
	                          }
	                      }
	                  }
	                  mapCountryDocs.put(account.getCountryAccount().getLocale(),
	                          (List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
	                                  Constants.CONTROLLER_LIST, new Object[] {
	                                          account.getCountryAccount().getId(), selectedLangId,
	                                          Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE, null,
	                                          new Integer[0], resident, true, UserHelper.getUserInSession().isXbasV2()}));
	                  if (Constants.VAR_NEG_ONE != docCountryId
	                          && Constants.VAR_NEG_ONE != docCommonSupportingId && !resident) {
	                      mapCountryDocs.get(account.getCountryAccount().getLocale()).addAll(
	                              (List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
	                                      Constants.CONTROLLER_LIST,
	                                      new Object[] {
	                                              docCountryId,
	                                              account.getCommunicationLanguage() != null ? account
	                                                      .getCommunicationLanguage().getId()
	                                                      : selectedLangId, docCommonSupportingId,
	                                              Constants.VAR_NEG_ONE, null, null, null, true, UserHelper.getUserInSession().isXbasV2()}));
	                  }
	                  break;
		      		}
		      	}
	      	}else {
		      	if (getCountry() != null) {
                      boolean resident = false;
                      if (entityCountry.equals(getCountry().getLocale().getCountry())) {
                          resident = true;
                      }
                      final String selectedLang = getSummary().getMapCountrySelectedLang()
                              .get(getCountry().getLocale().getCountry());
                      Integer selectedLangId = null;
                      if (StringUtils.isNotBlank(selectedLang) && countryDtoList != null) {
                          for (final CountryDto country : countryDtoList) {
                              if (country.getId().equals(getCountry().getId())) {
                                  if (country.getLanguages() != null) {
                                      for (final LanguageDto language : country.getLanguages()) {
                                          if (selectedLang.equals(language.getLocale().getLanguage())) {
                                              selectedLangId = language.getId();
                                              break;
                                          }
                                      }
                                  }
                                  break;
                              }
                          }
                      }
                      mapCountryDocs.put(getCountry().getLocale(),
                              (List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                                      Constants.CONTROLLER_LIST, new Object[] {
                                    		  getCountry().getId(), selectedLangId,
                                              Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE, null,
                                              new Integer[0], resident, true, UserHelper.getUserInSession().isXbasV2()}));
                      if (Constants.VAR_NEG_ONE != docCountryId
                              && Constants.VAR_NEG_ONE != docCommonSupportingId && !resident) {
                          mapCountryDocs.get(getCountry().getLocale()).addAll(
                                  (List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                                          Constants.CONTROLLER_LIST,
                                          new Object[] {
                                                  docCountryId,
                                                  getAccount().getCommunicationLanguage() != null ? getAccount()
                                                          .getCommunicationLanguage().getId()
                                                          : selectedLangId, docCommonSupportingId,
                                                  Constants.VAR_NEG_ONE, null, null, null, true, UserHelper.getUserInSession().isXbasV2()}));
                      }
                  }
	      	}
	      setMapCountryDocs(mapCountryDocs);
	      initMapCountrySignatureCard();
         } catch (DCOException e) {
        	 LOG.error("Error while initializing documents");
  		}
    }

	/**
     * build a map for different type of paramFuncDto
     * @param paramDtoType type of paramFuncDto to map
     * @param list Countries for the map
     * @return map of paramfuncDto
     * @throws DCOException
     */
    private Map<String, List<ParamFuncDto>> setMapParamFuncDto(final int paramDtoType, final List<String> list)
            throws DCOException {
        final Map<String, List<ParamFuncDto>> mappingPFDT = new HashMap<String, List<ParamFuncDto>>();
        if (getPrefLangList() != null && !getPrefLangList().isEmpty()) {
            int mapCount = 0;
            for (final LanguageDto lang : getPrefLangList()) {
                Map<String, List<ParamFuncDto>> mappingPFDTTemp = (Map<String, List<ParamFuncDto>>) BusinessHelper
                        .call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_LOAD_PARAMS_MAP_LANGUAGE,
                                new Object[] {paramDtoType, list, lang.getId()});

                if (mappingPFDTTemp.entrySet() != null) {
                    mapCount++;
                    for (final Entry<String, List<ParamFuncDto>> entry : mappingPFDTTemp.entrySet()) {
                        if (mapCount == 1) {
                            mappingPFDT.put(entry.getKey(), entry.getValue());
                        } else {
                            boolean found = false;
                            for (final Entry<String, List<ParamFuncDto>> entrySaved : mappingPFDT.entrySet()) {
                                final List<ParamFuncDto> paramsList = new ArrayList<ParamFuncDto>();
                                int index = 0;
                                for (final ParamFuncDto param : entrySaved.getValue()) {
                                    if (index < entry.getValue().size()
                                            && entry.getKey().equals(entrySaved.getKey())) {
                                        param.setValue(entry.getValue().get(index).getValue());
                                        paramsList.add(param);
                                        index++;
                                        found = true;
                                    }
                                }
                                if (found) {
                                    entrySaved.setValue(paramsList);
                                    break;
                                }
                            }
                        }
                    }
                }
                mappingPFDTTemp = null;
                if (UserHelper.getUserInSession().getPreferences() != null && lang.getId().compareTo(UserHelper.getUserInSession().getPreferences().getLanguageId()) == 0) {
                    break;
                } else if (UserHelper.getUserInSession().getPreferences() == null && lang.getId().compareTo(6) == 0) {
                	// Default english for first connexions
                	break;
                }
            }
        }
        return mappingPFDT;
    }
    
    public ParamFuncDto getAccountCurrency(final CountryDto country, final int id) {
        ParamFuncDto result = null;
        if (currencyList != null && !currencyList.isEmpty()) {
            for (final ParamFuncDto currency : currencyList) {
                if (currency != null && currency.getId().equals(id)) {
                    result = currency;
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * @param country the id of reference
     */
    
    public boolean generateNewAccountReference() {
        boolean result = false;
        
//        try {
//			setMapCurrency(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_CURRENCY, selectedCountries));
//		} catch (DCOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        if (getAccount().getCountryAccount() != null && getAccount().getCurrency().getId() != null) {
        	String ref = getAccount().getCountryAccount().getLocale().getCountry()
                  + getAccountCurrency(getAccount().getCountryAccount(),
            		getAccount().getCurrency().getId()).getEntry()
			        + String.format("%02d",
			        		numberOfAccountByCountry(getAccount().getCountryAccount()) + 1);
        	
        	getAccount().setReference(ref);
        			
            result = true;
        }
        return result;
    }
    
    private int numberOfAccountByCountry(final CountryDto country) {
        int total = 0;
        int max = 0;
        if (entity.getAccountList() != null && !entity.getAccountList().isEmpty()) {
            for (final AccountDto account : entity.getAccountList()) {
                if (account.getCountry().equals(country.getLocale().getCountry())) {
                    final String ref = account.getReference();
                    if (Constants.ACC_REF_MIN_LENGTH < ref.length()) {
                        try {
                            int temp = 0;
                            temp = Integer.parseInt(ref.substring(ref.length() - 2));
                            if (temp > max) {
                                max = temp;
                            }
                        } catch (final NumberFormatException ex) {
                            LOG.info("Account counter format is not a number: ", ex);
                        }
                    }
                    total++;
                }
            }
            if (total < max) {
                total = max;
            }
        }
        return total;
    }

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
	 * @return the country
	 */
	public CountryDto getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryDto country) {
		this.country = country;
	}

	/**
	 * @return the prefLangList
	 */
	public List<LanguageDto> getPrefLangList() {
		return prefLangList;
	}

	/**
	 * @param prefLangList the prefLangList to set
	 */
	public void setPrefLangList(List<LanguageDto> prefLangList) {
		this.prefLangList = prefLangList;
	}

	/**
	 * @return the account
	 */
	public AccountDto getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(AccountDto account) {
		this.account = account;
	}

	/**
	 * @return the currencyList
	 */
	public List<ParamFuncDto> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @param currencyList the currencyList to set
	 */
	public void setCurrencyList(List<ParamFuncDto> currencyList) {
		this.currencyList = currencyList;
	}

	/**
	 * @return the typeAccountList
	 */
	public List<ParamFuncDto> getTypeAccountList() {
		return typeAccountList;
	}

	/**
	 * @param typeAccountList the typeAccountList to set
	 */
	public void setTypeAccountList(List<ParamFuncDto> typeAccountList) {
		this.typeAccountList = typeAccountList;
	}

	/**
	 * @return the periodicityList
	 */
	public List<ParamFuncDto> getPeriodicityList() {
		return periodicityList;
	}

	/**
	 * @param periodicityList the periodicityList to set
	 */
	public void setPeriodicityList(List<ParamFuncDto> periodicityList) {
		this.periodicityList = periodicityList;
	}

	/**
	 * @return the signatories
	 */
	public List<SignatoryDto> getSignatories() {
		return signatories;
	}

	/**
	 * @param signatories the signatories to set
	 */
	public void setSignatories(List<SignatoryDto> signatories) {
		this.signatories = signatories;
	}
	
	/**
	 * @return the newSignatory
	 */
	public SignatoryDto getNewSignatory() {
		return newSignatory;
	}

	/**
	 * @param newSignatory the newSignatory to set
	 */
	public void setNewSignatory(SignatoryDto newSignatory) {
		this.newSignatory = newSignatory;
	}

	/**
	 * @return the citizenshipList
	 */
	public List<String> getCitizenshipList() {
		return citizenshipList;
	}

	/**
	 * @param citizenshipList the citizenshipList to set
	 */
	public void setCitizenshipList(List<String> citizenshipList) {
		this.citizenshipList = citizenshipList;
	}

	/**
	 * @return the countryList
	 */
	public List<Locale> getCountryList() {
		return countryList;
	}

	/**
	 * @param countryList the countryList to set
	 */
	public void setCountryList(List<Locale> countryList) {
		this.countryList = countryList;
	}

	/**
	 * @return the colleges
	 */
	public List<CollegeDto> getColleges() {
		return colleges;
	}

	/**
	 * @param colleges the colleges to set
	 */
	public void setColleges(List<CollegeDto> colleges) {
		this.colleges = colleges;
	}

	/**
	 * @return the newCollege
	 */
	public CollegeDto getNewCollege() {
		return newCollege;
	}

	/**
	 * @param newCollege the newCollege to set
	 */
	public void setNewCollege(CollegeDto newCollege) {
		this.newCollege = newCollege;
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
	 * @return the mapCurrency
	 */
	public Map<String, List<ParamFuncDto>> getMapCurrency() {
		return mapCurrency;
	}

	/**
	 * @param mapCurrency the mapCurrency to set
	 */
	public void setMapCurrency(Map<String, List<ParamFuncDto>> mapCurrency) {
		this.mapCurrency = mapCurrency;
	}

	/**
	 * @return the selectedCountries
	 */
	public List<String> getSelectedCountries() {
		return selectedCountries;
	}

	/**
	 * @param selectedCountries the selectedCountries to set
	 */
	public void setSelectedCountries(List<String> selectedCountries) {
		this.selectedCountries = selectedCountries;
	}

	/**
	 * @return the listRules
	 */
	public List<RulesDto> getListRules() {
		return listRules;
	}

	/**
	 * @param listRules the listRules to set
	 */
	public void setListRules(List<RulesDto> listRules) {
		this.listRules = listRules;
	}

	/**
	 * @return the newRules
	 */
	public RulesDto getNewRules() {
		return newRules;
	}

	/**
	 * @param newRules the newRules to set
	 */
	public void setNewRules(RulesDto newRules) {
		this.newRules = newRules;
	}

	/**
	 * @return the nbSignatories
	 */
	public Integer getNbSignatories() {
		return nbSignatories;
	}

	/**
	 * @param nbSignatories the nbSignatories to set
	 */
	public void setNbSignatories(Integer nbSignatories) {
		this.nbSignatories = nbSignatories;
	}

	/**
	 * @return the nbColleges
	 */
	public Integer getNbColleges() {
		return nbColleges;
	}

	/**
	 * @param nbColleges the nbColleges to set
	 */
	public void setNbColleges(Integer nbColleges) {
		this.nbColleges = nbColleges;
	}

	/**
	 * @return the nbSignatoryByColleges
	 */
	public Integer getNbSignatoryByColleges() {
		return nbSignatoryByColleges;
	}

	/**
	 * @param nbSignatoryByColleges the nbSignatoryByColleges to set
	 */
	public void setNbSignatoryByColleges(Integer nbSignatoryByColleges) {
		this.nbSignatoryByColleges = nbSignatoryByColleges;
	}

	/**
	 * @return the nbRules
	 */
	public Integer getNbRules() {
		return nbRules;
	}

	/**
	 * @param nbRules the nbRules to set
	 */
	public void setNbRules(Integer nbRules) {
		this.nbRules = nbRules;
	}

	/**
	 * @return the nbCollegesBySignatory
	 */
	public Integer getNbCollegesBySignatory() {
		return nbCollegesBySignatory;
	}

	/**
	 * @param nbCollegesBySignatory the nbCollegesBySignatory to set
	 */
	public void setNbCollegesBySignatory(Integer nbCollegesBySignatory) {
		this.nbCollegesBySignatory = nbCollegesBySignatory;
	}

	/**
	 * @return the countryFullName
	 */
	public String getCountryFullName() {
		return countryFullName;
	}

	/**
	 * @param countryFullName the countryFullName to set
	 */
	public void setCountryFullName(String countryFullName) {
		this.countryFullName = countryFullName;
	}

	/**
	 * @return the mapCountryDocs
	 */
	public Map<Locale, List<DocumentDto>> getMapCountryDocs() {
		return mapCountryDocs;
	}

	/**
	 * @param mapCountryDocs the mapCountryDocs to set
	 */
	public void setMapCountryDocs(Map<Locale, List<DocumentDto>> mapCountryDocs) {
		this.mapCountryDocs = mapCountryDocs;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the summary
	 */
	public FormSummaryForm getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(FormSummaryForm summary) {
		this.summary = summary;
	}

	/**
	 * @return the listRepresentative
	 */
	public List<RepresentativeDto> getListRepresentative() {
		return listRepresentative;
	}

	/**
	 * @param listRepresentative the listRepresentative to set
	 */
	public void setListRepresentative(List<RepresentativeDto> listRepresentative) {
		this.listRepresentative = listRepresentative;
	}

	/**
	 * @return the newRepresentative
	 */
	public RepresentativeDto getNewRepresentative() {
		return newRepresentative;
	}

	/**
	 * @param newRepresentative the newRepresentative to set
	 */
	public void setNewRepresentative(RepresentativeDto newRepresentative) {
		this.newRepresentative = newRepresentative;
	}

	/**
	 * @return the listAccountCountry
	 */
	public List<AccountDto> getListAccountCountry() {
		return listAccountCountry;
	}

	/**
	 * @param listAccountCountry the listAccountCountry to set
	 */
	public void setListAccountCountry(List<AccountDto> listAccountCountry) {
		this.listAccountCountry = listAccountCountry;
	}

	/**
	 * @return the listRepresentativeSelected
	 */
	public List<RepresentativeDto> getListRepresentativeSelected() {
		return listRepresentativeSelected;
	}

	/**
	 * @param listRepresentativeSelected the listRepresentativeSelected to set
	 */
	public void setListRepresentativeSelected(List<RepresentativeDto> listRepresentativeSelected) {
		this.listRepresentativeSelected = listRepresentativeSelected;
	}

	/**
	 * @return the listAccountSelected
	 */
	public List<AccountDto> getListAccountSelected() {
		return listAccountSelected;
	}

	/**
	 * @param listAccountSelected the listAccountSelected to set
	 */
	public void setListAccountSelected(List<AccountDto> listAccountSelected) {
		this.listAccountSelected = listAccountSelected;
	}

	/**
	 * @return the listAccountSelectedID
	 */
	public List<Integer> getListAccountSelectedID() {
		return listAccountSelectedID;
	}

	/**
	 * @param listAccountSelectedID the listAccountSelectedID to set
	 */
	public void setListAccountSelectedID(List<Integer> listAccountSelectedID) {
		this.listAccountSelectedID = listAccountSelectedID;
	}

	/**
	 * @return the listAccountSelectedIDS
	 */
	public List<String> getListAccountSelectedIDS() {
		return listAccountSelectedIDS;
	}

	/**
	 * @param listAccountSelectedIDS the listAccountSelectedIDS to set
	 */
	public void setListAccountSelectedIDS(List<String> listAccountSelectedIDS) {
		this.listAccountSelectedIDS = listAccountSelectedIDS;
	}

	/**
	 * @return the channelList
	 */
	public List<ParamFuncDto> getChannelList() {
		return channelList;
	}

	/**
	 * @param channelList the channelList to set
	 */
	public void setChannelList(List<ParamFuncDto> channelList) {
		this.channelList = channelList;
	}

	/**
	 * @return the signatoriesToResume
	 */
	public List<SignatoryDto> getSignatoriesToResume() {
		return signatoriesToResume;
	}

	/**
	 * @param signatoriesToResume the signatoriesToResume to set
	 */
	public void setSignatoriesToResume(List<SignatoryDto> signatoriesToResume) {
		this.signatoriesToResume = signatoriesToResume;
	}

	/**
	 * @return the mapAccount
	 */
	public Map<String, List<Integer>> getMapAccount() {
		return mapAccount;
	}

	/**
	 * @param mapAccount the mapAccount to set
	 */
	public void setMapAccount(Map<String, List<Integer>> mapAccount) {
		this.mapAccount = mapAccount;
	}

	/**
	 * @return the mapCountrySignatureCard
	 */
	public Map<String, Boolean> getMapCountrySignatureCard() {
		return mapCountrySignatureCard;
	}

	/**
	 * @param mapCountrySignatureCard the mapCountrySignatureCard to set
	 */
	public void setMapCountrySignatureCard(Map<String, Boolean> mapCountrySignatureCard) {
		this.mapCountrySignatureCard = mapCountrySignatureCard;
	}

	/**
	 * @return the nbSignatories2
	 */
	public Integer getNbSignatories2() {
		return nbSignatories2;
	}

	/**
	 * @param nbSignatories2 the nbSignatories2 to set
	 */
	public void setNbSignatories2(Integer nbSignatories2) {
		this.nbSignatories2 = nbSignatories2;
	}

	/**
	 * @return the nbColleges2
	 */
	public Integer getNbColleges2() {
		return nbColleges2;
	}

	/**
	 * @param nbColleges2 the nbColleges2 to set
	 */
	public void setNbColleges2(Integer nbColleges2) {
		this.nbColleges2 = nbColleges2;
	}

	/**
	 * @return the nbRules2
	 */
	public Integer getNbRules2() {
		return nbRules2;
	}

	/**
	 * @param nbRules2 the nbRules2 to set
	 */
	public void setNbRules2(Integer nbRules2) {
		this.nbRules2 = nbRules2;
	}
}
