package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.bnpp.dco.common.dto.AddressDto;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.ThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("formulaireDCOForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FormulaireDCOForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -7152935639371951331L;

	/** Logger (named on purpose after the DCO form). */
    private static final Logger LOG = LoggerFactory.getLogger(FormulaireDCOForm.class);

    // Entity used for each form Page
    private EntityDto entity;

    // Fields used for form1 Page
    private List<AccountDto> accounts;
    private AccountDto newAccount;
    // Field to get posted country locales to get converted to CountryDto beans
    private List<String> selectedCountries = new ArrayList<String>();
    private String selectedCountryForNewAccount;

    // Map to put the lists (value) of account statement, in function of the country (key)
    private Map<String, List<ParamFuncDto>> mapLegalStatus = new HashMap<String, List<ParamFuncDto>>();
    private Map<String, List<ParamFuncDto>> mapCurrency = new HashMap<String, List<ParamFuncDto>>();
    private Map<String, List<ParamFuncDto>> mapTypeAccount = new HashMap<String, List<ParamFuncDto>>();
    private Map<String, List<ParamFuncDto>> mapPeriodicity = new HashMap<String, List<ParamFuncDto>>();
    private Map<String, List<ParamFuncDto>> mapStatementType = new HashMap<String, List<ParamFuncDto>>();

    private List<AjaxDto> listAccountKey;
    private Map<String, List<Integer>> mapAccount;
    private List<String> listBranchName;
    private List<ParamFuncDto> listPeriodicity;
    private List<AddressDto> listAddress;
    private List<String> listCountry;
    private List<String> listCommercialRegister;
    private List<String> listVatNumber;

    private Map<String, List<AjaxDto>> mapCountryComLangs = new HashMap<String, List<AjaxDto>>();

    // Fields binding the entity to the accounts
    private AccountFormDto accountForm;

    // Allow to associate the typeStatements to the accounts.
    private AccountStatementTypesBean accountStatementTypesBean;
    private Integer[] accountStatementTypesForNewAccount;

    // Fields used for form3 Page
    // Field allow to know if a new account is created
    private boolean create = false;
    private List<CountryDto> accountCountries;
    private List<AjaxDto> accountCountriesSort;
    private boolean alert = false;

    // The user thirdParty
    private List<ThirdPartyDto> thirdPartyList;
    private List<ThirdPartyDto> contactsList;
    private List<ThirdPartyDto> legalRepresentativesList;
    private List<ThirdPartyDto> tPForATPList;
    private int entityContact;
    private int[] entityRepresentatives;

    // Utils fields for the form4 Page (Account Third Party).
    private List<ParamFuncDto> signatureAuthorizationList;
    private Integer accountSelect;
    private AccountThirdPartyDto accountThirdPartyDto;
    private String countryATP;
    private Integer thirdPartyDtoSelect;
    private Integer powerType;
    private Integer statusAmountLimit;
    private String deviseAmountLimit;
    private Long amountLimit;
    private String publicDeedReference;
    private Integer signatureAuthorizationSelect;
    private int[] authorizedThirdPartyList;
    private Integer codeSAIndiv;
    private Integer codeSAJoinW;
    private String deleteCountry;
    private Integer deleteThirdParty;
    private Integer deleteSignatureAuthorization;

    // fields used in the signature card form
    
    private String boardResolutionDay;
    private String boardResolutionMonth;
    private String boardResolutionYear;
    private String boardResolutionDate;
    
    private String issuanceDate;
    private String mercantileInscriptionDate;
    private String mercantileInscriptionNumber;

    // Elements for downloading the forms
    private Map<Locale, List<DocumentDto>> mapCountryDocs;
    private FormSummaryForm summary = new FormSummaryForm();
    private Map<String, Boolean> mapCountrySignatureCard;
    // Map of accessible tabs
    private Boolean[] breadcrumbs;
    // List of the languages preferred (selected from Preferences page)
    private List<LanguageDto> prefLangList;
    
    private boolean error = false;

    public void init(final List<ThirdPartyDto> thirdPartyList) {
        try {
            setThirdPartyList(thirdPartyList);

            if (getAccountCountries() == null || getAccountCountries().isEmpty()) {

                final List<CountryDto> countryDtoList = (List<CountryDto>) BusinessHelper.call(
                        Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null);

                setAccountCountries(countryDtoList);

                setSignatureAuthorizationList((List<ParamFuncDto>) BusinessHelper.call(Constants.CONTROLLER_PARAM,
                        Constants.CONTROLLER_PARAM_LOAD_PARAMS_LANGUAGE, new Object[] {
                                Constants.PARAM_TYPE_THIRD_PARTY_SIGN_AUTH, getEntity().getCountry().getCountry(),
                                UserHelper.getUserInSession().getPreferences().getLanguageId()}));

                setPrefLangList((List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                        Constants.CONTROLLER_LANG_LIST_INTERFACE, null));

                if (getEntity().getCountry().getCountry() != null) {
                    final List<String> oneLocaleList = new ArrayList<String>();
                    oneLocaleList.add(getEntity().getCountry().getCountry());
                    setMapLegalStatus(setMapParamFuncDto(Constants.PARAM_TYPE_LEGAL_STATUS, oneLocaleList));
                }

                if (!getSelectedCountries().isEmpty()) {
                    // build map of currency per every country!
                    setMapCurrency(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_CURRENCY, getSelectedCountries()));
                    // build map of account type per every country!
                    setMapTypeAccount(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_TYPE, getSelectedCountries()));
                    // build map of periodicity per every country!
                    setMapPeriodicity(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_STATE_PERIOD,
                            getSelectedCountries()));
                    // useless from the version 1.0.15!
                    setMapStatementType(setMapParamFuncDto(Constants.PARAM_TYPE_ACCNT_STATE_SUPPORT,
                            getSelectedCountries()));
                }
                // build map of Country with Languages enabled
                buildMapAccountCountryComLangs();
            }

        } catch (final DCOException e) {
            LOG.error("Error while initializing legal countries for account openings");
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
                if (lang.getId().compareTo(UserHelper.getUserInSession().getPreferences().getLanguageId()) == 0) {
                    break;
                }
            }
        }
        return mappingPFDT;
    }

    public void mapAccount() {
        final List<AjaxDto> listAccountKey = new ArrayList<AjaxDto>();
        final Map<String, List<Integer>> mapAccount = new LinkedHashMap<String, List<Integer>>();
        final List<String> listBranchName = new ArrayList<String>();
        final List<ParamFuncDto> listPeriodicity = new ArrayList<ParamFuncDto>();
        final List<AddressDto> listAddress = new ArrayList<AddressDto>();
        final List<String> listCommercialRegister = new ArrayList<String>();
        final List<String> listVatNumber = new ArrayList<String>();
        final List<String> listCountry = new ArrayList<String>();
        final Locale locale;
        if (getAccounts() != null && !getAccounts().isEmpty()) {
            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                    && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
                locale = UserHelper.getUserInSession().getPreferences().getLocale();
            } else {
                locale = new Locale("en");
            }
            for (int i = 0; i < getAccounts().size(); i++) {
                final CountryDto c = getAccounts().get(i).getCountryAccount();
                if (mapAccount.containsKey(c.getLocale().getCountry())) {
                    mapAccount.get(c.getLocale().getCountry()).add(i);
                } else {
                    final AjaxDto accountKey = new AjaxDto(c.getLocale().getDisplayCountry(locale), c.getLocale()
                            .getCountry());
                    accountKey.setId(c.getId());
                    listAccountKey.add(accountKey);

                    final List<Integer> tmpList = new ArrayList<Integer>();
                    tmpList.add(i);
                    mapAccount.put(c.getLocale().getCountry(), tmpList);
                    // add objects in the list and if null, initialize them to a new one (empty char or new
                    // object).
                    listPeriodicity.add(getAccounts().get(i).getPeriodicity() == null ? new ParamFuncDto()
                            : getAccounts().get(i).getPeriodicity());
                    listAddress.add(getAccounts().get(i).getAddress() == null ? new AddressDto() : getAccounts()
                            .get(i).getAddress());
                    listCountry.add(getAccounts().get(i).getCountry() == null ? "" : getAccounts().get(i)
                            .getCountry());
                    listCommercialRegister.add(getAccounts().get(i).getCommercialRegister() == null ? ""
                            : getAccounts().get(i).getCommercialRegister());
                    listVatNumber.add(getAccounts().get(i).getVatNumber() == null ? "" : getAccounts().get(i)
                            .getVatNumber());
                    listBranchName.add(getAccounts().get(i).getBranchName() == null ? "" : getAccounts().get(i)
                            .getBranchName());
                }
            }
        }
        setListAccountKey(listAccountKey);
        setMapAccount(mapAccount);
        setListPeriodicity(listPeriodicity);
        setListAddress(listAddress);
        setListCountry(listCountry);
        setListCommercialRegister(listCommercialRegister);
        setListVatNumber(listVatNumber);
        setListBranchName(listBranchName);
    }

    public int checkAllAccountAssociation(final String codeJointly, final String codeJointlyAny) {
        int result = 0;
        if (StringUtils.isNotBlank(codeJointly) && StringUtils.isNotBlank(codeJointlyAny)) {
            for (final AccountDto account : getAccounts()) {
                if (account.getAccountThirdPartyList() == null || account.getAccountThirdPartyList().isEmpty()) {
                    continue;
                }
                result = checkAccountAssociation(codeJointly, codeJointlyAny, account);
                if (0 == result) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param codeJointly
     * @param codeJointlyAny
     * @param account
     * @return
     */
    public int checkAccountAssociation(final String codeJointly, final String codeJointlyAny,
            final AccountDto account) {
        int result = 0;
        for (final AccountThirdPartyDto accountThirdParty : account.getAccountThirdPartyList()) {
            if (accountThirdParty.getSignatureAuthorization() == null) {
                continue;
            }
            if (codeJointly.equalsIgnoreCase(accountThirdParty.getSignatureAuthorization().getEntry())
                    && accountThirdParty.getAuthorizedList() != null) {
                int count = accountThirdParty.getAuthorizedList().size();
                for (final AuthorizationThirdPartyDto authorizationThirdParty : accountThirdParty
                        .getAuthorizedList()) {
                    final int temp = count;
                    for (final AccountThirdPartyDto accountThirdParty1 : account.getAccountThirdPartyList()) {
                        if (accountThirdParty1.getId().compareTo(accountThirdParty.getId()) != Constants.VAR_ZERO
                                && accountThirdParty1.getThirdParty().getId()
                                        .compareTo(authorizationThirdParty.getId().getThirdPartyId()) == Constants.VAR_ZERO) {
                            count = count - 1;
                            break;
                        }
                    }
                    if (temp == count) {
                        break;
                    }
                }
                if (Constants.VAR_ZERO != count) {
                    result = Constants.VAR_ONE;
                }
            }
            if (Constants.VAR_ZERO == result
                    && codeJointlyAny.equalsIgnoreCase(accountThirdParty.getSignatureAuthorization().getEntry())) {
                boolean found = false;
                for (final AccountThirdPartyDto accountThirdParty1 : account.getAccountThirdPartyList()) {
                    if (accountThirdParty1.getId().compareTo(accountThirdParty.getId()) != Constants.VAR_ZERO
                            && accountThirdParty1.getThirdParty().getId()
                                    .compareTo(accountThirdParty.getThirdParty().getId()) != Constants.VAR_ZERO) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    result = Constants.VAR_TWO;
                }
            }
            if (Constants.VAR_ZERO != result) {
                break;
            }
        }
        return result;
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
                    if (getAccounts().get(index) != null
                            && getAccounts().get(index).getAccountThirdPartyList() != null) {
                        for (final AccountThirdPartyDto accountThirdParty : getAccounts().get(index)
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

    /**
     * Utility method to display a country in a given locale from a country ISO code.
     * @param country the country ISO code to use
     * @param locale the locale in which to display the country
     * @return the resulting country name
     * @throws DCOException
     */
    public String getDisplayCountryFromString(final String country, final Locale locale) throws DCOException {
        return LocaleUtil.stringToCountry(country).getDisplayCountry(locale);
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
     * @return the accounts
     */
    public List<AccountDto> getAccounts() {
        return this.accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(final List<AccountDto> accounts) {
        this.accounts = accounts;
        if (this.accounts != null && !this.accounts.isEmpty()) {
            Collections.sort(this.accounts, new Comparator<AccountDto>() {
                @Override
                public int compare(final AccountDto account1, final AccountDto account2) {
                    final int rslt = account1.getCountryAccount().getLocale().getCountry()
                            .compareToIgnoreCase(account2.getCountryAccount().getLocale().getCountry());
                    return rslt == 0 ? account1.getReference().compareToIgnoreCase(account2.getReference()) : rslt;
                }
            });
        }
    }

    /**
     * @return the newAccount
     */
    public AccountDto getNewAccount() {
        return this.newAccount;
    }

    /**
     * @param newAccount the newAccount to set
     */
    public void setNewAccount(final AccountDto newAccount) {
        this.newAccount = newAccount;
    }

    /**
     * @return the selectedCountries
     */
    public List<String> getSelectedCountries() {
        return this.selectedCountries;
    }

    /**
     * @param selectedCountries the selectedCountries to set
     */
    public void setSelectedCountries(final List<String> selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    /**
     * @return the accountCountries
     */
    public List<CountryDto> getAccountCountries() {
        return this.accountCountries;
    }

    public void setAccountCountries(final List<CountryDto> accountCountries) {

        if (accountCountries != null) {
            final List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();

            final Locale locale;

            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                    && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
                locale = UserHelper.getUserInSession().getPreferences().getLocale();
            } else {
                locale = new Locale("en");
            }

            for (final CountryDto c : accountCountries) {
                final AjaxDto a = new AjaxDto(c.getLocale().getDisplayCountry(locale), c.getLocale().getCountry());
                ajaxList.add(a);
            }

            Collections.sort(ajaxList, new AjaxDto.OrderByLabel());

            setAccountCountriesSort(ajaxList);
        }

        this.accountCountries = accountCountries;
    }

    public boolean isCreate() {
        return this.create;
    }

    public void setCreate(final boolean create) {
        this.create = create;
    }
    
    public boolean isError() {
        return this.error;
    }

    public void setError(final boolean error) {
        this.error = error;
    }

    public List<AjaxDto> getAccountCountriesSort() {
        return this.accountCountriesSort;
    }

    public void setAccountCountriesSort(final List<AjaxDto> accountCountriesSort) {
        this.accountCountriesSort = accountCountriesSort;
    }

    public AccountStatementTypesBean getAccountStatementTypesBean() {
        return this.accountStatementTypesBean;
    }

    public void setAccountStatementTypesBean(final AccountStatementTypesBean accountStatementTypesBean) {
        this.accountStatementTypesBean = accountStatementTypesBean;
    }

    public EntityDto getEntity() {
        return this.entity;
    }

    public void setEntity(final EntityDto entity) {
        this.entity = entity;
    }

    public List<ThirdPartyDto> getThirdPartyList() {
        return this.thirdPartyList;
    }

    public void setThirdPartyList(final List<ThirdPartyDto> thirdPartyList) {
        this.thirdPartyList = thirdPartyList;

        if (thirdPartyList != null && thirdPartyList.size() > Constants.VAR_ZERO) {

            final List<ThirdPartyDto> contact = new ArrayList<ThirdPartyDto>();
            final List<ThirdPartyDto> legalRepresentatives = new ArrayList<ThirdPartyDto>();
            final List<ThirdPartyDto> tPForATP = new ArrayList<ThirdPartyDto>();

            for (final ThirdPartyDto third : thirdPartyList) {

                if (third.getCorrespondantType() != null
                        && third.getCorrespondantType().compareTo(Constants.THIRD_CONTACT) == Constants.VAR_ZERO) {
                    contact.add(third);
                }

                if (third.getCorrespondantTypeTwo() != null
                        && third.getCorrespondantTypeTwo().compareTo(Constants.THIRD_SIGNATORY) == Constants.VAR_ZERO) {
                    tPForATP.add(third);
                }

                if (third.getCorrespondantTypeThree() != null
                        && third.getCorrespondantTypeThree().compareTo(Constants.THIRD_LEGAL_REPRESENTATIVE) == Constants.VAR_ZERO) {
                    legalRepresentatives.add(third);
                }
            }

            setLegalRepresentativesList(legalRepresentatives);
            setTPForATPList(tPForATP);
        }

    }

    public List<ThirdPartyDto> getContactsList() {
        return this.contactsList;
    }

    public void setContactsList(final List<ThirdPartyDto> contactsList) {
        this.contactsList = contactsList;
    }

    public List<ThirdPartyDto> getLegalRepresentativesList() {
        return this.legalRepresentativesList;
    }

    public void setLegalRepresentativesList(final List<ThirdPartyDto> legalRepresentativesList) {
        this.legalRepresentativesList = legalRepresentativesList;
    }

    public int getEntityContact() {
        return this.entityContact;
    }

    public void setEntityContact(final int entityContact) {
        this.entityContact = entityContact;
    }

    public int[] getEntityRepresentatives() {
        return this.entityRepresentatives;
    }

    public void setEntityRepresentatives(final int[] entityRepresentatives) {
        this.entityRepresentatives = Arrays.copyOf(entityRepresentatives, entityRepresentatives.length);
    }

    public List<ParamFuncDto> getSignatureAuthorizationList() {
        return this.signatureAuthorizationList;
    }

    public void setSignatureAuthorizationList(final List<ParamFuncDto> signatureAuthorizationList) {
        this.signatureAuthorizationList = signatureAuthorizationList;
    }

    public List<ThirdPartyDto> getTPForATPList() {
        return this.tPForATPList;
    }

    public void setTPForATPList(final List<ThirdPartyDto> tPForATPList) {
        this.tPForATPList = tPForATPList;
    }

    public String getSelectedCountryForNewAccount() {
        return this.selectedCountryForNewAccount;
    }

    public void setSelectedCountryForNewAccount(final String selectedCountryForNewAccount) {
        this.selectedCountryForNewAccount = selectedCountryForNewAccount;
    }

    public Integer[] getAccountStatementTypesForNewAccount() {
        return this.accountStatementTypesForNewAccount;
    }

    public void setAccountStatementTypesForNewAccount(final Integer[] accountStatementTypesForNewAccount) {
        this.accountStatementTypesForNewAccount = Arrays.copyOf(accountStatementTypesForNewAccount,
                accountStatementTypesForNewAccount.length);
    }

    public Integer getThirdPartyDtoSelect() {
        return this.thirdPartyDtoSelect;
    }

    public void setThirdPartyDtoSelect(final Integer thirdPartyDtoSelect) {
        this.thirdPartyDtoSelect = thirdPartyDtoSelect;
    }

    public Integer getSignatureAuthorizationSelect() {
        return this.signatureAuthorizationSelect;
    }

    public void setSignatureAuthorizationSelect(final Integer signatureAuthorizationSelect) {
        this.signatureAuthorizationSelect = signatureAuthorizationSelect;
    }

    /**
     * @return the accountThirdPartyDto
     */
    public AccountThirdPartyDto getAccountThirdPartyDto() {
        return this.accountThirdPartyDto;
    }

    /**
     * @param accountThirdPartyDto the accountThirdPartyDto to set
     */
    public void setAccountThirdPartyDto(final AccountThirdPartyDto accountThirdPartyDto) {
        this.accountThirdPartyDto = accountThirdPartyDto;
    }

    /**
     * @return the countryATP
     */
    public String getCountryATP() {
        return this.countryATP;
    }

    /**
     * @param countryATP the countryATP to set
     */
    public void setCountryATP(final String countryATP) {
        this.countryATP = countryATP;
    }

    public Long getAmountLimit() {
        return this.amountLimit;
    }

    public void setAmountLimit(final Long amountLimit) {
        this.amountLimit = amountLimit;
    }

    public Integer getAccountSelect() {
        return this.accountSelect;
    }

    public void setAccountSelect(final Integer accountSelect) {
        this.accountSelect = accountSelect;
    }

    /**
     * Getter for mapCountryDocs.
     * @return the mapCountryDocs
     */
    public Map<Locale, List<DocumentDto>> getMapCountryDocs() {
        return this.mapCountryDocs;
    }

    /**
     * Setter for mapCountryDocs.
     * @param mapCountryDocs the mapCountryDocs to set
     */
    public void setMapCountryDocs(final Map<Locale, List<DocumentDto>> mapCountryDocs) {
        this.mapCountryDocs = mapCountryDocs;
    }

    public Map<String, List<ParamFuncDto>> getMapLegalStatus() {
        return this.mapLegalStatus;
    }

    public void setMapLegalStatus(final Map<String, List<ParamFuncDto>> mapLegalStatus) {
        this.mapLegalStatus = mapLegalStatus;
    }

    public Map<String, List<ParamFuncDto>> getMapCurrency() {
        return this.mapCurrency;
    }

    public void setMapCurrency(final Map<String, List<ParamFuncDto>> mapCurrency) {
        this.mapCurrency = mapCurrency;
    }

    public Map<String, List<ParamFuncDto>> getMapTypeAccount() {
        return this.mapTypeAccount;
    }

    public void setMapTypeAccount(final Map<String, List<ParamFuncDto>> mapTypeAccount) {
        this.mapTypeAccount = mapTypeAccount;
    }

    public Map<String, List<ParamFuncDto>> getMapPeriodicity() {
        return this.mapPeriodicity;
    }

    public void setMapPeriodicity(final Map<String, List<ParamFuncDto>> mapPeriodicity) {
        this.mapPeriodicity = mapPeriodicity;
    }

    public Map<String, List<ParamFuncDto>> getMapStatementType() {
        return this.mapStatementType;
    }

    public void setMapStatementType(final Map<String, List<ParamFuncDto>> mapStatementType) {
        this.mapStatementType = mapStatementType;
    }

    /**
     * Getter for summary.
     * @return the summary
     */
    public FormSummaryForm getSummary() {
        return this.summary;
    }

    /**
     * Setter for summary.
     * @param summary the summary to set
     */
    public void setSummary(final FormSummaryForm summary) {
        this.summary = summary;
    }

    /**
     * @return the mapCountrySignatureCard
     */
    public Map<String, Boolean> getMapCountrySignatureCard() {
        return this.mapCountrySignatureCard;
    }

    /**
     * @param mapCountrySignatureCard the mapCountrySignatureCard to set
     */
    public void setMapCountrySignatureCard(final Map<String, Boolean> mapCountrySignatureCard) {
        this.mapCountrySignatureCard = mapCountrySignatureCard;
    }

    /**
     * @return the boardResolutionDay
     */
    public String getBoardResolutionDay() {
        return this.boardResolutionDay;
    }

    /**
     * @param boardResolutionDay the boardResolutionDay to set
     */
    public void setBoardResolutionDay(final String boardResolutionDay) {
        this.boardResolutionDay = boardResolutionDay;
    }
    
    /**
     * @return the boardResolutionMonth
     */
    public String getBoardResolutionMonth() {
        return this.boardResolutionMonth;
    }

    /**
     * @param boardResolutionMonth the boardResolutionMonth to set
     */
    public void setBoardResolutionMonth(final String boardResolutionMonth) {
        this.boardResolutionMonth = boardResolutionMonth;
    }
    
    /**
     * @return the boardResolutionYear
     */
    public String getBoardResolutionYear() {
    	return this.boardResolutionYear;
    }

    /**
     * @param boardResolutionMonth the boardResolutionMonth to set
     */
    public void setBoardResolutionYear(final String boardResolutionYear) {
        this.boardResolutionYear = boardResolutionYear;
    }
    
    /**
     * @return the boardResolutionDate
     */
    public String getBoardResolutionDate() {
        return this.boardResolutionDate;
    }

    /**
     * @param boardResolutionDate the boardResolutionDate to set
     */
    public void setBoardResolutionDate(final String boardResolutionDate) {
        this.boardResolutionDate = boardResolutionDate;
    }

    /**
     * @return the issuanceDate
     */
    public String getIssuanceDate() {
        return this.issuanceDate;
    }

    /**
     * @param issuanceDate the issuanceDate to set
     */
    public void setIssuanceDate(final String issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    /**
     * @return the mercantileInscriptionDate
     */
    public String getMercantileInscriptionDate() {
        return this.mercantileInscriptionDate;
    }

    /**
     * @param mercantileInscriptionDate the mercantileInscriptionDate to set
     */
    public void setMercantileInscriptionDate(final String mercantileInscriptionDate) {
        this.mercantileInscriptionDate = mercantileInscriptionDate;
    }

    /**
     * @return the mercantileInscriptionNumber
     */
    public String getMercantileInscriptionNumber() {
        return this.mercantileInscriptionNumber;
    }

    /**
     * @param mercantileInscriptionNumber the mercantileInscriptionNumber to set
     */
    public void setMercantileInscriptionNumber(final String mercantileInscriptionNumber) {
        this.mercantileInscriptionNumber = mercantileInscriptionNumber;
    }

    /**
     * Utility method to map Country and Languages flagged (and set them to the Form object property).
     */
    private void buildMapAccountCountryComLangs() {
        final Map<String, List<AjaxDto>> mapAccountCountryComLangs = new HashMap<String, List<AjaxDto>>();

        final Locale locale;

        if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
            locale = UserHelper.getUserInSession().getPreferences().getLocale();
        } else {
            locale = new Locale("en");
        }

        List<CountryDto> countriesComLangs = new ArrayList<CountryDto>();
        try {
            countriesComLangs = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                    Constants.CONTROLLER_LIST, null);
        } catch (final DCOException e) {
            this.LOG.error("An error occurred while loading parameter communication countries", e);
        }
        if (countriesComLangs != null) {
            for (final CountryDto c : countriesComLangs) {
                final List<AjaxDto> langs = new ArrayList<AjaxDto>();

                if (c.getLanguages() != null) {
                    mapAccountCountryComLangs.put(c.getLocale().getCountry(), langs);
                    for (final LanguageDto lExisting : c.getLanguages()) {
                        final AjaxDto ajaxLang = new AjaxDto(lExisting.getId(), lExisting.getLocale()
                                .getLanguage(), lExisting.getLocale().getDisplayLanguage(locale));
                        langs.add(ajaxLang);
                    }
                }
            }
        }
        this.setMapCountryComLangs(mapAccountCountryComLangs);
    }

    /**
     * @return the mapCountryComLangs
     */
    public Map<String, List<AjaxDto>> getMapCountryComLangs() {
        return this.mapCountryComLangs;
    }

    /**
     * @param mapCountryComLangs the mapCountryComLangs to set
     */
    public void setMapCountryComLangs(final Map<String, List<AjaxDto>> mapCountryComLangs) {
        this.mapCountryComLangs = mapCountryComLangs;
    }

    /**
     * @return the breadcrumbs
     */
    public Boolean[] getBreadcrumbs() {
        return this.breadcrumbs;
    }

    /**
     * @param breadcrumbs the breadcrumbs to set
     */
    public void setBreadcrumbs(final Boolean[] breadcrumbs) {
        this.breadcrumbs = new Boolean[breadcrumbs.length];
        for (int index = 0; index < breadcrumbs.length; index++) {
            this.breadcrumbs[index] = breadcrumbs[index];
        }
    }

    public void setBreadcrumbs(final int index, final Boolean accessible) {
        this.breadcrumbs[index] = accessible;
    }

    /**
     * @return the prefLangList
     */
    public List<LanguageDto> getPrefLangList() {
        return this.prefLangList;
    }

    /**
     * @param prefLangList the prefLangList to set
     */
    public void setPrefLangList(final List<LanguageDto> prefLangList) {
        this.prefLangList = prefLangList;
    }

    /**
     * @param country the country of in witch we search
     * @param typeId the research key
     */
    public ParamFuncDto getAccountType(final CountryDto country, final String entry) {
        ParamFuncDto result = null;
        final List<ParamFuncDto> listParamFuncDto = getMapTypeAccount().get(country.getLocale().getCountry());
        if (listParamFuncDto != null && !listParamFuncDto.isEmpty()) {
            for (final ParamFuncDto paramFuncDto : listParamFuncDto) {
                if (paramFuncDto != null && paramFuncDto.getEntry().equalsIgnoreCase(entry)) {
                    result = paramFuncDto;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param country the country of in witch we search
     * @param typeId the research key
     */
    public ParamFuncDto getAccountCurrency(final CountryDto country, final int id) {
        ParamFuncDto result = null;
        final List<ParamFuncDto> listParamFuncDto = getMapCurrency().get(country.getLocale().getCountry());
        if (listParamFuncDto != null && !listParamFuncDto.isEmpty()) {
            for (final ParamFuncDto paramFuncDto : listParamFuncDto) {
                if (paramFuncDto != null && paramFuncDto.getId().equals(id)) {
                    result = paramFuncDto;
                    break;
                }
            }
        }
        return result;
    }

    public boolean generateNewAccountReference() {
        boolean result = false;
        if (getNewAccount().getCountryAccount() != null && getNewAccount().getCurrency().getId() != null) {
            getNewAccount().setReference(
                    getNewAccount().getCountryAccount().getLocale().getCountry()
                            + getAccountCurrency(getNewAccount().getCountryAccount(),
                                    getNewAccount().getCurrency().getId()).getEntry()
                            + String.format("%02d",
                                    numberOfAccountByCountry(getNewAccount().getCountryAccount()) + 1));
            result = true;
        }
        return result;
    }

    /**
     * @param country the id of reference
     */
    private int numberOfAccountByCountry(final CountryDto country) {
        int total = 0;
        int max = 0;
        if (getAccounts() != null && !getAccounts().isEmpty()) {
            for (final AccountDto account : getAccounts()) {
                if (account.getCountryAccount().getLocale().getCountry().equals(country.getLocale().getCountry())) {
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
     * @return the alert
     */
    public boolean getAlert() {
        return this.alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(final boolean alert) {
        this.alert = alert;
    }

    /**
     * @return the listBranchName
     */
    public List<String> getListBranchName() {
        return this.listBranchName;
    }

    /**
     * @param listBranchName the listBranchName to set
     */
    public void setListBranchName(final List<String> listBranchName) {
        this.listBranchName = listBranchName;
    }

    /**
     * @return the listPeriodicity
     */
    public List<ParamFuncDto> getListPeriodicity() {
        return this.listPeriodicity;
    }

    /**
     * @param listPeriodicity the listPeriodicity to set
     */
    public void setListPeriodicity(final List<ParamFuncDto> listPeriodicity) {
        this.listPeriodicity = listPeriodicity;
    }

    /**
     * @return the listAddress
     */
    public List<AddressDto> getListAddress() {
        return this.listAddress;
    }

    /**
     * @param listAddress the listAddress to set
     */
    public void setListAddress(final List<AddressDto> listAddress) {
        this.listAddress = listAddress;
    }

    /**
     * @return the mapAccount
     */
    public Map<String, List<Integer>> getMapAccount() {
        return this.mapAccount;
    }

    /**
     * @param mapAccount the mapAccount to set
     */
    public void setMapAccount(final Map<String, List<Integer>> mapAccount) {
        this.mapAccount = mapAccount;
    }

    /**
     * @return the listCommercialRegister
     */
    public List<String> getListCommercialRegister() {
        return this.listCommercialRegister;
    }

    /**
     * @param listCommercialRegister the listCommercialRegister to set
     */
    public void setListCommercialRegister(final List<String> listCommercialRegister) {
        this.listCommercialRegister = listCommercialRegister;
    }

    /**
     * @return the listVatNumber
     */
    public List<String> getListVatNumber() {
        return this.listVatNumber;
    }

    /**
     * @param listVatNumber the listVatNumber to set
     */
    public void setListVatNumber(final List<String> listVatNumber) {
        this.listVatNumber = listVatNumber;
    }

    /**
     * @return the listCountry
     */
    public List<String> getListCountry() {
        return this.listCountry;
    }

    /**
     * @param listCountry the listCountry to set
     */
    public void setListCountry(final List<String> listCountry) {
        this.listCountry = listCountry;
    }

    /**
     * @return the listAccountKey
     */
    public List<AjaxDto> getListAccountKey() {
        return this.listAccountKey;
    }

    /**
     * @param listAccountKey the listAccountKey to set
     */
    public void setListAccountKey(final List<AjaxDto> listAccountKey) {
        this.listAccountKey = listAccountKey;
    }

    /**
     * @return the authorizedThirdPartyList
     */
    public int[] getAuthorizedThirdPartyList() {
        return this.authorizedThirdPartyList;
    }

    /**
     * @param authorizedThirdPartyList the authorizedThirdPartyList to set
     */
    public void setAuthorizedThirdPartyList(final int[] authorizedThirdPartyList) {
        this.authorizedThirdPartyList = authorizedThirdPartyList.clone();
    }

    /**
     * @return the powerType
     */
    public Integer getPowerType() {
        return this.powerType;
    }

    /**
     * @param powerType the powerType to set
     */
    public void setPowerType(final Integer powerType) {
        this.powerType = powerType;
    }

    /**
     * @return the statusAmountLimit
     */
    public Integer getStatusAmountLimit() {
        return this.statusAmountLimit;
    }

    /**
     * @param statusAmountLimit the statusAmountLimit to set
     */
    public void setStatusAmountLimit(final Integer statusAmountLimit) {
        this.statusAmountLimit = statusAmountLimit;
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
     * @return the codeSAIndiv
     */
    public Integer getCodeSAIndiv() {
        return this.codeSAIndiv;
    }

    /**
     * @param codeSAIndiv the codeSAIndiv to set
     */
    public void setCodeSAIndiv(final Integer codeSAIndiv) {
        this.codeSAIndiv = codeSAIndiv;
    }

    /**
     * @return the codeSAJoinW
     */
    public Integer getCodeSAJoinW() {
        return this.codeSAJoinW;
    }

    /**
     * @param codeSAJoinW the codeSAJoinW to set
     */
    public void setCodeSAJoinW(final Integer codeSAJoinW) {
        this.codeSAJoinW = codeSAJoinW;
    }

    /**
     * @return the deleteCountry
     */
    public String getDeleteCountry() {
        return this.deleteCountry;
    }

    /**
     * @param deleteCountry the deleteCountry to set
     */
    public void setDeleteCountry(final String deleteCountry) {
        this.deleteCountry = deleteCountry;
    }

    /**
     * @return the deleteThirdParty
     */
    public Integer getDeleteThirdParty() {
        return this.deleteThirdParty;
    }

    /**
     * @param deleteThirdParty the deleteThirdParty to set
     */
    public void setDeleteThirdParty(final Integer deleteThirdParty) {
        this.deleteThirdParty = deleteThirdParty;
    }

    /**
     * @return the deleteSignatureAuthorization
     */
    public Integer getDeleteSignatureAuthorization() {
        return this.deleteSignatureAuthorization;
    }

    /**
     * @param deleteSignatureAuthorization the deleteSignatureAuthorization to set
     */
    public void setDeleteSignatureAuthorization(final Integer deleteSignatureAuthorization) {
        this.deleteSignatureAuthorization = deleteSignatureAuthorization;
    }

	public String getDeviseAmountLimit() {
		return deviseAmountLimit;
	}

	public void setDeviseAmountLimit(String deviseAmountLimit) {
		this.deviseAmountLimit = deviseAmountLimit;
	}

}
