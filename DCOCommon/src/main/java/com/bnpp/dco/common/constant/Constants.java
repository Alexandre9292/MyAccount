package com.bnpp.dco.common.constant;

/**
 * Shared constants.
 */
public final class Constants {
    /**
     * Constructor.
     */
    private Constants() {

    }

    /** Field CONTROLLER_USER. */
    public static final String CONTROLLER_USER = "com.bnpp.dco.business.controller.UserController";
    public static final String CONTROLLER_LANGAGE = "com.bnpp.dco.business.controller.LanguageController";
    public static final String CONTROLLER_DATE_FORMAT = "com.bnpp.dco.business.controller.DateFormatController";
    public static final String CONTROLLER_COUNTRY = "com.bnpp.dco.business.controller.CountryController";
    public static final String CONTROLLER_DOCUMENT_TYPE = "com.bnpp.dco.business.controller.DocumentTypeController";
    public static final String CONTROLLER_DOCUMENT = "com.bnpp.dco.business.controller.DocumentController";
    public static final String CONTROLLER_PREFERENCES_USER = "com.bnpp.dco.business.controller.PreferencesUserController";
    public static final String CONTROLLER_ENTITIES = "com.bnpp.dco.business.controller.EntitiesController";
    public static final String CONTROLLER_PARAM = "com.bnpp.dco.business.controller.ParamController";
    public static final String CONTROLLER_ROLE = "com.bnpp.dco.business.controller.RoleController";
    public static final String CONTROLLER_LEGAL_ENTITY = "com.bnpp.dco.business.controller.LegalEntityController";
    public static final String CONTROLLER_STATISTICS = "com.bnpp.dco.business.controller.StatsController";
    public static final String CONTROLLER_ACCOUNT = "com.bnpp.dco.business.controller.AccountController";
    public static final String CONTROLLER_ACCOUNT_FORM = "com.bnpp.dco.business.controller.AccountFormController";
    public static final String CONTROLLER_THIRD_PARTY = "com.bnpp.dco.business.controller.ThirdPartyController";
    public static final String CONTROLLER_CITIZENSHIP = "com.bnpp.dco.business.controller.CitizenshipController";
    public static final String CONTROLLER_ACCOUNT_THIRD_PARTY = "com.bnpp.dco.business.controller.AccountThirdPartyController";
    public static final String CONTROLLER_AUTHORIZATION_THIRD_PARTY = "com.bnpp.dco.business.controller.AuthorizationThirdPartyController";
    // New
    public static final String CONTROLLER_CONTACT = "com.bnpp.dco.business.controller.ContactController";
    public static final String CONTROLLER_REPRESENTATIVE = "com.bnpp.dco.business.controller.RepresentativeController";
    public static final String CONTROLLER_SIGNATORY = "com.bnpp.dco.business.controller.SignatoryController";
    public static final String CONTROLLER_COLLEGE = "com.bnpp.dco.business.controller.CollegeController";
    public static final String CONTROLLER_RULES= "com.bnpp.dco.business.controller.RulesController";
    
    /** Field CONTROLLER_USER_LIST. */
    public static final String CONTROLLER_USER_LIST = "list";
    public static final String CONTROLLER_LIST = "list";
    public static final String CONTROLLER_LIST2 = "list2";
    /** Field CONTROLLER_USER_LOGIN. */
    public static final String CONTROLLER_USER_LOGIN = "login";
    /** Field used to create a new User. */
    public static final String CONTROLLER_USER_CREATE = "create";
    public static final String CONTROLLER_CREATE = "create";

    public static final String CONTROLLER_USER_SAVE_DATAS_USER = "saveDatasUser";
    public static final String CONTROLLER_USER_GET_PREFERENCES = "getPreferencesByUserLogin";
    public static final String CONTROLLER_USER_SAVE_ROLE = "saveRoleUser";
    public static final String CONTROLLER_USER_CREATE_FROM_DTO = "createUserFromDto";
    public static final String CONTROLLER_USER_FORGOTTEN_PASSWORD = "forgottenPassword";
    public static final String CONTROLLER_USER_INIT_PASSWORD = "initUserPassword";
    public static final String CONTROLLER_USER_UPDATE = "updateUser";
    public static final String CONTROLLER_USER_DELETE = "deleteUser";
    public static final String CONTROLLER_USER_BANK_LIST = "bankUserList";
    public static final String CONTROLLER_USER_CLIENT_LIST = "clientUserList";
    public static final String CONTROLLER_USER_SET_ID = "setUserId";
    public static final String CONTROLLER_USER_GET_BY_LOGIN = "getUserByLogin";
    public static final String CONTROLLER_USER_ADD_FAIL = "addFail";
    public static final String CONTROLLER_USER_RESET_LOCK = "resetLock";
    public static final String CONTROLLER_USER_ENTITIES_EXCLUSION = "getUserEntitiesExclusion";
    public static final String CONTROLLER_USER_INCREMENT_STATS_SEARCH_DOC = "incrementUserStatSearchDoc";

    public static final String CONTROLLER_ENTITIES_GET_BY_USER_LOGIN = "getEntityByUserLogin";
    public static final String CONTROLLER_ENTITIES_GET_BY_ID = "getEntityById";
    public static final String CONTROLLER_ENTITIES_ALL_GET_BY_USER_LOGIN = "getEntitiesByUserLogin";
    public static final String CONTROLLER_ENTITIES_GET_BY_LABEL = "doGetEntityDtoByLabel";
    public static final String CONTROLLER_ENTITIES_UPDATE = "updateEntities";
    public static final String CONTROLLER_ENTITIES_UPDATE_FROM_CLIENT_MANAGEMENT = "updateEntitiesFromClientManagement";
    public static final String CONTROLLER_ENTITIES_LABEL_LIST = "listLabel";

    public static final String CONTROLLER_DOCUMENTS_SAVE = "saveDocument";
    public static final String CONTROLLER_DOCUMENTS_DELETE = "deleteDocument";
    public static final String CONTROLLER_DOCUMENTS_MODIFY = "modifyDocument";

    public static final String CONTROLLER_PARAM_RETURN_PAGE = "param";
    public static final String CONTROLLER_PARAM_LOAD_TYPES = "loadTypes";
    public static final String CONTROLLER_PARAM_LOAD_PARAMS = "loadParams";
    public static final String CONTROLLER_PARAM_SAVE_PARAMS = "saveParams";
    public static final String CONTROLLER_PARAM_DELETE_PARAM = "deleteParam";
    public static final String CONTROLLER_PARAM_LOAD_ENTITIES = "loadEntities";
    public static final String CONTROLLER_PARAM_SAVE_ENTITIES = "saveEntities";
    public static final String CONTROLLER_PARAM_SAVE_LANGS = "saveLanguages";
    public static final String CONTROLLER_PARAM_LOAD_COMM_MESS = "loadCommercialMessage";
    public static final String CONTROLLER_PARAM_SAVE_COMM_MESS = "saveCommercialMessage";
    public static final String CONTROLLER_PARAM_SAVE_COUNTRIES = "saveCountries";
    public static final String CONTROLLER_PARAM_LOAD_PARAMS_LANGUAGE = "loadParamByCountryAndLanguage";
    public static final String CONTROLLER_PARAM_LOAD_PARAMS_MAP_LANGUAGE = "loadParamMapByCountryAndLanguage";

    public static final String CONTROLLER_COUNTRY_FILTER_LEGAL_ENTITY = "getCountryByLegalEntity";

    public static final String CONTROLLER_LANGUAGE_GET_BY_ID = "getLanguageById";

    public static final String CONTROLLER_THIRD_PARTY_BY_USER_LOGIN = "getThirdPartyByUserLogin";
    public static final String CONTROLLER_THIRD_PARTY_BY_ID = "getThirdParty";

    public static final String CONTROLLER_ACCOUNT_FORM_SAVE_ACOUNT_FORM = "saveAccountForm";
    public static final String CONTROLLER_ACCOUNT_BY_ACOUNT_FORM = "getAccountByAccountFormId";
    public static final String CONTROLLER_ACCOUNT_FORM_BY_ENTITY = "getAccountFormEntity";
    public static final String CONTROLLER_ACCOUNT_SAVE = "saveAccount";
    public static final String CONTROLLER_ACCOUNT_CREATE = "createAccountFromDto";
    public static final String CONTROLLER_ACCOUNT_REMOVE = "removeAccount";
    public static final String CONTROLLER_ACCOUNT_STATS_PRINTED_DOCS = "incrementPrintedDocByCountry";
    public static final String CONTROLLER_THIRD_PARTY_SAVE = "saveThirdParty";
    public static final String CONTROLLER_ACCOUNT_UPDATE = "updateAccount";
    public static final String CONTROLLER_ACCOUNT_SIMPLE_UPDATE = "simpleUpdate";

    public static final String CONTROLLER_ACCOUNT_THIRD_PARTY_SAVE = "createAccountThirdPartyFromDto";
    public static final String CONTROLLER_ACCOUNT_THIRD_PARTY_DELETE = "deleteAccountThirdParty";
    public static final String CONTROLLER_AUTHORIZATION_THIRD_PARTY_SAVE = "saveAuthorizedThirdPartyListFromDto";
    public static final String CONTROLLER_AUTHORIZATION_THIRD_PARTY_DELETE = "deleteAuthorizationThirdParty";
    public static final String CONTROLLER_THIRD_PARTY_DELETE = "deleteThirdParty";
    public static final String CONTROLLER_CITIZENSHIP_DELETE = "deleteCitizenship";
    public static final String CONTROLLER_ACCOUNT_THIRD_PARTY_GET_ACCOUNT_ID = "getAccountThirdPartyByAccountId";
    public static final String CONTROLLER_ACCOUNT_THIRD_PARTY_GET = "getAccountThirdPartyByAccountThirdPartyId";

    public static final String CONTROLLER_LANG_LIST_INTERFACE = "listInterface";

    public static final String CONTROLLER_STATS_INCREMENT_DOC_STATS = "incrementDocStat";
    public static final String CONTROLLER_STATS_GET_STATS_USERS = "getStatsUsers";
    public static final String CONTROLLER_STATS_GET_STATS_PRINT = "getStatsPrint";
    public static final String CONTROLLER_STATS_GET_STATS_FORM_COUNTRY = "getStatsFormCountry";
    public static final String CONTROLLER_STATS_GET_STATS_FORM_ENTITY = "getStatsFormEntity";
    public static final String CONTROLLER_STATS_GET_STATS_DOCUMENTS = "getStatsDocuments";
    public static final String CONTROLLER_STATS_GET_USER_BY_LEGAL_ENTITY = "usersByEntity";
    
    ////////////////////////////////////
    // New
    public static final String CONTROLLER_CONTACT_SAVE = "saveContactFromDto";
    public static final String CONTROLLER_REPRESENTATIVE_SAVE = "saveRepresentativeFromDto";
    public static final String CONTROLLER_SIGNATORY_SAVE = "saveSignatoryFromDto";
    public static final String CONTROLLER_COLLEGE_SAVE = "saveCollegeFromDto";
    
    public static final String CONTROLLER_COLLEGE_DELETE = "deleteCollege";
    public static final String CONTROLLER_SIGNATORY_DELETE = "deleteSignatory";
    public static final String CONTROLLER_RULES_DELETE = "deleteRule";
    public static final String CONTROLLER_RULES_REPRESENTATIVE = "deleteRepresentative";
    
    public static final String CONTROLLER_ACCOUNT_BY_ID = "getAccountById";
    public static final String CONTROLLER_ACCOUNTS_FROM_COUNTRY = "getAccountsFromCountry";
    
    public static final String CONTROLLER_COUNTRY_GET_BY_LOCALE = "getCountryByLocal";
    /////////////////////////////////////
    
    /** Roles */
    public static final int PROFILE_BANK = 1;
    public static final int PROFILE_CLIENT = 2;
    public static final String ROLE_SUPER_ADMIN = "SA";
    public static final String ROLE_MGMT_ACCOUNT = "MGMT-ACCNT";
    public static final String ROLE_MGMT_DOCUMENTS = "MGMT-DOC";
    public static final String ROLE_VIEW_CLIENT_DATA = "VIEW-CLT-DATA";
    public static final String ROLE_MGMT_PARAMETERS = "MGMT-PARAM";
    public static final String ROLE_VIEW_STATISTICS = "VIEW-STAT";
    public static final int USER_LOCK_NB_ATTEMPTS = 5;

    public static final int ROLE_SUPER_ADMIN_ID = 1;
    public static final int ROLE_MGMT_ACCOUNT_ID = 2;
    public static final int ROLE_MGMT_DOCUMENTS_ID = 3;
    public static final int ROLE_VIEW_CLIENT_DATA_ID = 4;
    public static final int ROLE_MGMT_PARAMETERS_ID = 5;
    public static final int ROLE_VIEW_STATISTICS_ID = 6;

    /** Functional parameter codes used for the Account third party association. */
    public static final int PARAM_CODE_AMOUNT_NO_LIMIT = 1;
    public static final int PARAM_CODE_AMOUNT_LIMIT = 2;
    public static final int PARAM_CODE_ACC_THIRD_PWR_FR1 = 1;
    public static final int PARAM_CODE_ACC_THIRD_PWR_FR2 = 2;
    public static final int PARAM_CODE_ACC_THIRD_PWR_PT = 3;
    public static final int PARAM_CODE_ACC_THIRD_PWR_ES = 4;
    public static final int PARAM_CODE_ACC_THIRD_PWR_GB = 5;
    public static final String PARAM_CODE_ACC_THIRD_CNT_PT = "PT";
    public static final String PARAM_CODE_ACC_THIRD_CNT_ES = "ES";
    public static final String PARAM_CODE_ACC_THIRD_CNT_GB = "GB";
    public static final String PARAM_CODE_ACC_THIRD_CNT_IE = "IE";

    /** Functional parameter codes used for a special country and a document type. */
    public static final String PARAM_TYPE_DOC_COMMON_SUPPORTING = "Common supporting";
    public static final String ALRT_CONTRY_CODE_FRANCE = "FR";
    public static final String ALRT_CONTRY_CODE_BELGIUM = "BE";
    public static final String ALRT_CONTRY_CODE_PORTUGAL = "PT";

    public static final String CITIZENSHIP_ID_VALUE_OK = "OK";
    public static final int ACC_REF_MIN_LENGTH = 4;

    /** Functional parameter codes. */
    public static final int PARAM_TYPE_LEGAL_STATUS = 2;
    public static final int PARAM_TYPE_ACCNT_TYPE = 3;
    public static final int PARAM_TYPE_ACCNT_CURRENCY = 4;
    public static final int PARAM_TYPE_ACCNT_STATE_PERIOD = 5;
    public static final int PARAM_TYPE_ACCNT_STATE_SUPPORT = 6;
    public static final int PARAM_TYPE_ACCNT_INTERAC_LANG = 7;
    public static final int PARAM_TYPE_ACCNT_PAY_MODE = 8;
    public static final int PARAM_TYPE_THIRD_PARTY_POSIT = 9;
    public static final int PARAM_TYPE_THIRD_PARTY_SIGN_AUTH = 10;

    public static final int PARAM_TYPE_CUSTOM_COUNTRY = 1001;
    public static final int PARAM_TYPE_CUSTOM_LANG = 1002;
    public static final int PARAM_TYPE_CUSTOM_COUNTRY_LANG = 1003;
    public static final int PARAM_TYPE_CUSTOM_ENTITY = 1004;
    public static final int PARAM_TYPE_CUSTOM_COUNTRY_ENTITY = 1005;

    public static final int PARAM_TYPE_CUSTOM_COMMERCIAL_MESSAGE_LOGIN = 1010;
    public static final int PARAM_TYPE_CUSTOM_COMMERCIAL_MESSAGE_CLIENT = 1011;

    public static final String PARAM_TYPE_LEGAL_STATUS_OTHER = "Other";

    /** Matching Document */
    public static final String MATCH_TITLE = "matchTitle";
    public static final String MATCH_ALL = "matchAll";
    public static final String NO_MATCH = "noMatch";

    public static final long LONG_LOB_LENGTH = 4294967295L;

    public static final long MAX_SIZE_UPLOAD_DOCUMENT = 5242880L;
    public static final int DOC_TITLE_MAX_LENGTH = 99;

    public static final int VAR_ZERO = 0;
    public static final int VAR_NEG_ONE = -1;
    public static final int VAR_NEG_NINETY = -90;
    public static final int VAR_ONE = 1;
    public static final int VAR_TWO = 2;
    public static final int VAR_THREE = 3;
    public static final int[] EMPTY_ARRAY = {};
    public static final String EMPTY_FIELD = "";
    public static final String SPACE = " ";
    public static final String FILEEXT_SEPARATOR = ".";
    public static final String COMMA = ",";
    public static final String PLUS = "+";

    /** Pattern Model */
    public static final String PATTERN_DATE_TO_FORMAT = "\\d{8}";
    public static final String PATTERN_SPECIAL = "�������������";
    public static final String PATTERN_NAME = "^([a-zA-Z" + PATTERN_SPECIAL + "]+[ ]*[-']?[ ]*[a-zA-Z"
            + PATTERN_SPECIAL + "]+)$";
    public static final String PATTERN_EMAIL = "[^@]+@[^@]+\\.[a-zA-Z]{2,6}";
    public static final String PATTERN_TEL = "(\\+)|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})";
    public static final String PATTERN_INPUTS = "([^=\\{\\}\\x22;<>\\[\\]\\#])*";

    /** Error DCO Exception **/
    public static final int EXCEPTION_CODE_USER_LOGIN_NOT_EXIST = 1;
    public static final int EXCEPTION_CODE_USER_LOGIN_WRONG_PASSWORD = 2;
    public static final int EXCEPTION_CODE_USER_CREATE_PASSWORD = 3;
    public static final int EXCEPTION_CODE_USER_CREATE_ALREADY_EXISTS = 4;
    public static final int EXCEPTION_CODE_USER_DONT_HAVE_PREFERENCES = 5;
    public static final int EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES = 6;
    public static final int EXCEPTION_CALLING_BUSINESS = 7;
    public static final int EXCEPTION_CLOSING_CONTEXT = 8;
    public static final int EXCEPTION_FILE_NOT_FOUND = 9;
    public static final int EXCEPTION_IO = 10;
    public static final int EXCEPTION_DOWNLOAD = 11;
    public static final int EXCEPTION_ROLLBACK = 12;
    public static final int EXCEPTION_RECORDING_DATA = 13;
    public static final int EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_NO_USER = 15;
    public static final int EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_ID_EMAIL_NO_MATCH = 16;
    public static final int EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_LOCKED = 17;
    public static final int EXCEPTION_CODE_EMAIL = 18;
    public static final int EXCEPTION_ENTITY_NO_USER = 19;

    /** Type Stats */
    public static final int STATS_TYPE_DOCUMENT = 1;
    public static final int STATS_USER_BY_ENTITY = 2;
    public static final int STATS_TYPE_CONNECTION = 3;
    public static final int STATS_TYPE_SEARCH = 4;
    public static final int STATS_TYPE_FORM_COUNTRY = 5;
    public static final int STATS_TYPE_FORM_ENTITY = 6;
    public static final int STATS_TYPE_PRINT_COUNTRY = 7;
    public static final int STATS_TYPE_PRINT_ENTITY = 8;

    /** Correspondant Type */
    public static final int THIRD_DIRECTOR = 1;
    public static final int THIRD_CONTACT = 2;
    public static final int THIRD_SIGNATORY = 3;
    public static final int THIRD_LEGAL_REPRESENTATIVE = 4;
    public static final int THIRD_CONTACT_MAX_NUMBER = 2;

    /** Default locale */
    public static final String DEFAULT_LANGUAGE = "en";
}
