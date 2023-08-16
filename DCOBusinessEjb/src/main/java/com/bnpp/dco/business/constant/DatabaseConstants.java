package com.bnpp.dco.business.constant;

/**
 * Utility class holding all the database constants.
 */
public final class DatabaseConstants {

    /** Constructor. */
    private DatabaseConstants() {
    }

    /** USER */
    public static final String USER_LOGIN = "user.login";
    public static final String USER_ID = "user.id";
    public static final String USER_LIST = "user.list";
    public static final String USER_ENTITIES = "user.entities";
    public static final String USER_PREFERENCES = "user.preferences";
    public static final String USER_FILTER_PROFILE = "user.filter.profile";
    public static final String USER_FILTER_LOGIN = "user.filter.login";
    public static final String USER_FILTER_LASTNAME = "user.filter.lastname";
    public static final String USER_FILTER_FIRSTNAME = "user.filter.firstname";

    public static final String USER_FILTER_ROLE = "user.filter.role";
    public static final String USER_FILTER_COUNTRY_LEGAL_ENTITY = "user.filter.country.legal.entity";

    public static final String USER_JOIN_ROLE = "user.join.role";
    public static final String USER_JOIN_LEGAL_ENTITY = "user.join.legal.entity";
    public static final String USER_JOIN_ENTITY = "user.join.entity";
    public static final String USER_JOIN_COUNTRY_LEGAL_ENTITY = "user.join.country.legal.entity";
    public static final String USER_ENTITIES_EXCLUSION = "user.entities.exclusion";

    /** DOCUMENT */
    public static final String DOCUMENT_FILTER_PROFILE = "user.filter.profile";
    public static final String DOCUMENT_FILTER_LEGAL_ENTITY = "user.filter.legal.entity";
    public static final String DOCUMENT_FILTER_ENTITY = "user.filter.entity";

    /** ENTITY */
    public static final String ENTITIES_LIST = "entities.list";
    public static final String ENTITIES_LABEL_LIST = "entities.label.list";
    public static final String ENTITIES_LABEL = "entities.label";
    /** LEGAL ENTITY */
    public static final String LEGAL_ENTITY_FILTER_COUNTRY = "legal.entity.filter.country";

    /** COUNTRY */
    public static final String COUNTRY_FILTER_LEGAL_ENTITY = "country.filter.legal.entity";

    /** LIST */
    public static final String LANGUAGE_LIST = "language.list";
    public static final String LANGUAGE_LOCALE = "language.locale";
    public static final String DATE_FORMAT_LIST = "dateFormat.list";
    public static final String COUNTRY_LIST = "country.list";
    public static final String COUNTRY_LIST2 = "country.list2";
    public static final String PREFERENCES_USER_LIST = "preferencesUser.list";
    public static final String DOCUMENT_TYPE_LIST = "documentType.list";
    public static final String ROLE_LIST = "role.list";
    public static final String LEGAL_ENTITY_LIST = "legal.entity.list";
    public static final String ACCOUNT_LIST = "account.list";
    public static final String THIRD_PARTY_LIST = "third.party.list";
    public static final String THIRD_PARTY_LIST_ID = "third.party.list.id";
    public static final String ACCOUNT_FORM_LIST = "account.form.list";
    public static final String ACCOUNT_BY_ACCOUNT_FORM_LIST = "account.list.account.form";
    public static final String ACCOUNT_THIRD_PARTY = "account.third.party";
    public static final String ACCOUNT_THIRD_PARTY_ACCOUNT_ID = "account.third.party.account.id";
    public static final String REPRESENTATIVE_LIST_ID = "representative.list.id";

    /** DOCUMENT **/
    public static final String DOCUMENT_LIST = "document.list";
    public static final String DOCUMENT_FILTER_COUNTRY = "document.filter.country";
    public static final String DOCUMENT_FILTER_LANGUAGE = "document.filter.language";
    public static final String DOCUMENT_LEGAL_ENTITY_LANGUAGE = "document.filter.legal.entity";
    public static final String DOCUMENT_FILTER_DOCUMENT_TYPE = "document.filter.documentType";
    public static final String DOCUMENT_FILTER_TITLE = "document.filter.title";
    public static final String DOCUMENT_FILTER_RESIDENT = "document.filter.resident";
    public static final String DOCUMENT_FILTER_NON_RESIDENT = "document.filter.non.resident";
    public static final String DOCUMENT_FILTER_XBAS = "document.filter.xbasV2";
    public static final String DOCUMENT_FILTER_NON_XBAS = "document.filter.non.xbasV2";
    public static final String DOCUMENT_FILTER_ID = "document.filter.id";
    public static final String DOCUMENT_COUNTRY_NULL = "document.country.null";
    public static final String DOCUMENT_LANGUAGE_NULL = "document.language.null";
    public static final String DOCUMENT_LEGAL_ENTITY_NULL = "document.legal.entity.null";
    /** PARAM */
    public static final String PARAM_FILTER_TYPES = "param.filter.type";
    public static final String PARAM_FILTER_COUNTRIES = "param.filter.country";
    public static final String PARAM_LOAD_PARAMS_LANGUAGE = "param.loadparamslanguage";

    /** STATS */
    public static final String STATS_GET_STATS_USER = "stat.get.stats.user";
    public static final String STATS_GET_GENERICS_STATS = "stat.get.stats";

    /** CREATE **/
    public static final String USER_CREATE = "user.create";
    public static final String ENTITIES_CREATE = "entities.create";
    public static final String EMAIL_CREATE = "email.create";

    /** CLAUSE **/
    public static final String AND_CLAUSE = " AND ";
    public static final String WHERE_CLAUSE = " WHERE ";
    public static final String OR_CLAUSE = " OR ";
    public static final String LEFT_BRAQUET = " ( ";
    public static final String RIGHT_BRAQUET = " ) ";
    public static final String JOIN_CLAUSE = " JOIN ";
    
    /** NEW WEBSITE */
    public static final String ENTITY_ACCOUNT_BY_ID = "entity.account";
    public static final String ENTITY_BY_ID = "entity.id";
    public static final String ACCOUNTS_SAME_COUNTRY = "accounts.country";
    public static final String COUNTRY_BY_LOCAL = "country.locale";
    public static final String DELETE_RULES = "rules.delete";
    public static final String DELETE_SIGNATORY = "signatory.delete";
    public static final String DELETE_COLLEGE = "college.delete";
    public static final String DELETE_REPRESENTATIVE = "representative.delete";
}
