package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.Country;
import com.bnpp.dco.business.dto.DocumentType;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.Language;
import com.bnpp.dco.business.dto.LegalEntity;
import com.bnpp.dco.business.dto.Statistics;
import com.bnpp.dco.business.dto.User;
import com.bnpp.dco.business.dto.converter.ConverterCountry;
import com.bnpp.dco.business.dto.converter.ConverterDocumentType;
import com.bnpp.dco.business.dto.converter.ConverterLanguage;
import com.bnpp.dco.business.dto.converter.ConverterLegalEntity;
import com.bnpp.dco.business.dto.converter.ConverterStatistics;
import com.bnpp.dco.business.dto.converter.ConverterUser;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DocumentTypeDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.StatisticsDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Controller for the statistics operations.
 */
public class StatsController extends GenericController {

    /**
     * Generic method to increment the stats for an user: nb of connection (type 3) or nb of research (type 4). Or
     * for adding a user per entity (type 2).
     * @param user
     */
    public void incrementUserStat(final User user, final int type, final String entity) {
        if (user != null) {
            boolean error = false;

            final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            final CriteriaQuery<Statistics> q = cb.createQuery(Statistics.class);
            final Root<Statistics> pf = q.from(Statistics.class);

            if (type == Constants.STATS_USER_BY_ENTITY && StringUtils.isNotBlank(entity)) {
                q.where(cb.equal(pf.get("user"), user), cb.equal(pf.get("typeStat"), type),
                        cb.equal(pf.get("entity"), entity));
            } else if (type == Constants.STATS_TYPE_CONNECTION || type == Constants.STATS_TYPE_SEARCH) {
                q.where(cb.equal(pf.get("user"), user), cb.equal(pf.get("typeStat"), type));
            } else {
                error = true;
            }
            if (!error) {

                final TypedQuery<Statistics> tq = getEntityManager().createQuery(q);
                final List<Statistics> result = tq.getResultList();
                final List<Statistics> stats = new ArrayList<Statistics>();
                for (final Object object : result) {
                    if (object instanceof Statistics) {
                        stats.add((Statistics) object);
                    }
                }

                if (stats.size() == Constants.VAR_ONE) {
                    // User or entity already exists in the stats
                    final Statistics stat = stats.get(Constants.VAR_ZERO);
                    stat.setNumber(stat.getNumber() + 1);
                    getEntityManager().merge(stat);
                } else if (stats.size() == Constants.VAR_ZERO) {
                    // User or entity doesn't exist in the stats
                    final Statistics stat = new Statistics();
                    stat.setTypeStat(type);
                    stat.setNumber(Constants.VAR_ONE);
                    stat.setUser(user);
                    getEntityManager().persist(stat);
                }
            }
        }
    }

    /**
     * Increment the number of printed doc by country.
     * @param account
     * @param type
     * @throws DCOException
     */
    public void incrementPrintedDocByCountry(final Account account, final int type) throws DCOException {

        if (account.getCountryAccount() != null) {

            final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            final CriteriaQuery<Statistics> q = cb.createQuery(Statistics.class);
            final Root<Statistics> pf = q.from(Statistics.class);

            q.where(cb.equal(pf.get("typeStat"), type), cb.equal(pf.get("country"), account.getCountryAccount()));

            final TypedQuery<Statistics> tq = getEntityManager().createQuery(q);
            final List<Statistics> result = tq.getResultList();
            final List<Statistics> stats = new ArrayList<Statistics>();
            for (final Object object : result) {
                if (object instanceof Statistics) {
                    stats.add((Statistics) object);
                }
            }

            if (stats.size() == Constants.VAR_ONE) {
                final Statistics stat = stats.get(Constants.VAR_ZERO);
                stat.setNumber(stat.getNumber() + 1);
                getEntityManager().merge(stat);
            } else if (stats.size() == Constants.VAR_ZERO) {
                final Statistics stat = new Statistics();
                stat.setTypeStat(type);
                stat.setCountry(account.getCountryAccount());
                stat.setNumber(Constants.VAR_ONE);
                getEntityManager().persist(stat);
            }
        }
    }

    /**
     * Allow to increment the document's research in function of the filter (type 1).
     * @param country
     * @param legal
     * @param language
     * @param type
     * @throws DCOException
     */
    public void incrementDocStat(final CountryDto country, final LegalEntityDto legal, final LanguageDto language,
            final DocumentTypeDto type) throws DCOException {

        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Statistics> q = cb.createQuery(Statistics.class);
        final Root<Statistics> pf = q.from(Statistics.class);

        final Country countryDB = ConverterCountry.convertDtoToDb(country, false);
        final LegalEntity legalDB = ConverterLegalEntity.convertDtoToDb(legal);
        final Language languageDB = ConverterLanguage.convertDtoToDb(language, false);
        final DocumentType typeDB = ConverterDocumentType.convertDtoToDb(type);

        q.where(cb.equal(pf.get("typeStat"), Constants.STATS_TYPE_DOCUMENT),
                cb.equal(pf.get("country"), countryDB), cb.equal(pf.get("legalEntity"), legalDB),
                cb.equal(pf.get("language"), languageDB), cb.equal(pf.get("documentType"), typeDB));

        final TypedQuery<Statistics> tq = getEntityManager().createQuery(q);
        final List<Statistics> result = tq.getResultList();
        final List<Statistics> stats = new ArrayList<Statistics>();
        for (final Object object : result) {
            if (object instanceof Statistics) {
                stats.add((Statistics) object);
            }
        }

        if (stats.size() == Constants.VAR_ONE) {
            // Doc research with the same filters already exists in the stats
            final Statistics stat = stats.get(Constants.VAR_ZERO);
            stat.setNumber(stat.getNumber() + 1);
            getEntityManager().merge(stat);
        } else if (stats.size() == Constants.VAR_ZERO) {
            // Doc research with the same filters doesn't exist in the stats
            final Statistics stat = new Statistics();
            stat.setTypeStat(Constants.STATS_TYPE_DOCUMENT);
            stat.setNumber(Constants.VAR_ONE);
            stat.setCountry(countryDB);
            stat.setLanguage(languageDB);
            stat.setDocumentType(typeDB);
            stat.setLegalEntity(legalDB);
            getEntityManager().persist(stat);
        }

    }

    /**
     * @return a map of the users and their number of connexions or document searches (type 3 / 4).
     * @throws DCOException
     */
    public Map<UserDto, Integer> getStatsUsers(final Integer type) throws DCOException {

        final Map<UserDto, Integer> map = new HashMap<UserDto, Integer>();

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.STATS_GET_GENERICS_STATS));
        q.setParameter("type", type);
        final List<?> result = q.getResultList();

        for (final Object object : result) {
            if (object instanceof Statistics) {
                final Statistics stat = (Statistics) object;
                map.put(ConverterUser.convertDbToDto(stat.getUser(), false), stat.getNumber());
            }
        }

        return map;
    }

    /**
     * @return a map of the country and their number of printed Doc.
     * @throws DCOException
     */
    public Map<Object, Integer> getStatsPrint(final Integer type) throws DCOException {

        final Map<Object, Integer> map = new HashMap<Object, Integer>();

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.STATS_GET_GENERICS_STATS));
        q.setParameter("type", type);
        final List<?> result = q.getResultList();

        for (final Object object : result) {
            if (object instanceof Statistics) {
                final Statistics stat = (Statistics) object;
                if (type == Constants.STATS_TYPE_FORM_COUNTRY || type == Constants.STATS_TYPE_PRINT_COUNTRY) {
                    map.put(ConverterCountry.convertDbToDto(stat.getCountry(), false), stat.getNumber());
                }
            }
        }

        return map;
    }

    /**
     * Get the number of form per entity
     * @return
     */
    public Map<String, Integer> getStatsFormEntity() {

        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        final Root<Entities> root = cq.from(Entities.class);
        final Expression<String> label = root.get("label");
        final Expression<Integer> idForm = root.get("accountForms");
        final Expression<Long> count = cb.count(idForm);

        cq.multiselect(label.alias("label"), count.alias("CNT"));
        cq.groupBy(label);
        cq.orderBy(cb.desc(count));

        final TypedQuery<Tuple> tq = getEntityManager().createQuery(cq);

        final Map<String, Integer> map = new HashMap<String, Integer>();

        for (final Tuple t : tq.getResultList()) {
            map.put((String) t.get("label"), (int) (long) t.get("CNT"));
        }

        return map;
    }

    /**
     * Get the number of form per country
     * @return
     */
    public Map<String, Integer> getStatsFormCountry() {

        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        final Root<Country> root = cq.from(Country.class);
        final Expression<String> locale = root.get("locale");
        final Expression<Integer> accounts = root.get("accounts");
        final Expression<Long> count = cb.count(accounts);

        cq.multiselect(locale.alias("locale"), count.alias("CNT"));
        cq.distinct(true);
        cq.groupBy(locale);
        cq.orderBy(cb.desc(count));

        final TypedQuery<Tuple> tq = getEntityManager().createQuery(cq);

        final Map<String, Integer> map = new HashMap<String, Integer>();

        for (final Tuple t : tq.getResultList()) {
            map.put((String) t.get("locale"), (int) (long) t.get("CNT"));
        }

        return map;
    }

    /**
     * @return the list of statisticsDto for the document (filter research on doc) (type 1).
     * @throws DCOException
     */
    public List<StatisticsDto> getStatsDocuments() throws DCOException {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Statistics> q = cb.createQuery(Statistics.class);
        final Root<Statistics> pf = q.from(Statistics.class);

        q.where(cb.equal(pf.get("typeStat"), Constants.STATS_TYPE_DOCUMENT));

        final TypedQuery<Statistics> tq = getEntityManager().createQuery(q);
        final List<Statistics> result = tq.getResultList();
        final List<StatisticsDto> stats = new ArrayList<StatisticsDto>();
        for (final Object object : result) {
            if (object instanceof Statistics) {
                stats.add(ConverterStatistics.convertDbToDto((Statistics) object));
            }
        }
        return stats;
    }

    /**
     * List of users in each legal entity.
     * @return a map with the legal entities and their users
     * @throws DCOException
     */
    public Map<String, List<UserDto>> usersByEntity() throws DCOException {
        // Loading all the client users (with a company)
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<User> q = cb.createQuery(User.class);
        final TypedQuery<User> tq = getEntityManager().createQuery(q);
        final List<User> result = tq.getResultList();

        final Map<String, List<UserDto>> map = new HashMap<String, List<UserDto>>();
        boolean alreadyExists = false;

        for (final Object object : result) {
            if (object instanceof User) {
                final User user = (User) object;
                String entityLabel = null;
                if (user.getEntitieses() != null && user.getEntitieses().size() > 0) {
                    final Iterator<Entities> itEntity = user.getEntitieses().iterator();
                    final Entities entity = itEntity.next();
                    entityLabel = entity.getLabel();
                    for (final Entry<String, List<UserDto>> entry : map.entrySet()) {

                        if (entry.getKey().equals(entityLabel)) {
                            entry.getValue().add(ConverterUser.convertDbToDto(user, false));
                            alreadyExists = true;
                            break;
                        } else {
                            alreadyExists = false;
                        }
                    }
                    if (!alreadyExists) {
                        final List<UserDto> list = new ArrayList<UserDto>();
                        list.add(ConverterUser.convertDbToDto(user, false));
                        map.put(entityLabel, list);
                    }
                }
            }
        }

        return map;
    }

    /**
     * List of users used for several calculations.
     * @return a list of users
     * @throws DCOException
     */
    public List<UserDto> users() throws DCOException {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<User> q = cb.createQuery(User.class);
        final TypedQuery<User> tq = getEntityManager().createQuery(q);
        final List<User> users = tq.getResultList();
        List<UserDto> result = null;
        if (users != null) {
            result = new ArrayList<UserDto>();
            for (final User u : users) {
                result.add(ConverterUser.convertDbToDto(u, false));
            }
        }
        return result;
    }

}
