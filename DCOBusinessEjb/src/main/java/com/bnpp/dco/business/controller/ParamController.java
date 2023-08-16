package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Language;
import com.bnpp.dco.business.dto.LegalEntity;
import com.bnpp.dco.business.dto.ParamFunc;
import com.bnpp.dco.business.dto.ParamFuncType;
import com.bnpp.dco.business.dto.converter.ConverterAddress;
import com.bnpp.dco.business.dto.converter.ConverterCountry;
import com.bnpp.dco.business.dto.converter.ConverterLanguage;
import com.bnpp.dco.business.dto.converter.ConverterLegalEntity;
import com.bnpp.dco.business.dto.converter.ConverterParamFunc;
import com.bnpp.dco.business.dto.converter.ConverterParamFuncType;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.ParamFuncTypeDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Controller for functional parameters.
 */
public final class ParamController extends GenericController {

    private Integer defaultLanguageId = null;

    /** Language controller. */
    private LanguageController languageController;

    /**
     * Method to return the default language id.
     * @return an Integer if exists, null otherwise.
     */
    private Integer getDefaultLanguageId() {
        if (this.defaultLanguageId == null) {
            final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            final CriteriaQuery<Language> q = cb.createQuery(Language.class);
            final Root<Language> pf = q.from(Language.class);
            q.where(cb.equal(pf.get("locale"), Constants.DEFAULT_LANGUAGE));

            final TypedQuery<Language> tq = getEntityManager().createQuery(q);
            final Language result = tq.getSingleResult();

            this.defaultLanguageId = result.getId();
        }
        return this.defaultLanguageId;
    }

    /**
     * Setter for languageController.
     * @param languageController the languageController to set
     */
    public void setLanguageController(final LanguageController languageController) {
        this.languageController = languageController;
    }

    /**
     * Get the list of parameter types.
     * @return the list of parameter types
     * @throws DCOException a DCOException
     */
    public List<ParamFuncTypeDto> loadTypes() throws DCOException {
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.PARAM_FILTER_TYPES));
        final List<ParamFuncType> listResult = q.getResultList();
        if (listResult.isEmpty()) {
            throw new DCOException("A problem occured while fetching functional parameters", 1);
        }
        final List<ParamFuncTypeDto> result = new ArrayList<ParamFuncTypeDto>();
        for (final ParamFuncType p : listResult) {
            result.add(ConverterParamFuncType.convertDbToDto(p));
        }
        return result;
    }

    /**
     * Get the list of parameters for the given type and country.
     * @return the map of parameters
     * @throws DCOException a DCOException
     */
    public Map<String, List<ParamFuncDto>> loadParams(final Integer type, final String country)
            throws DCOException {
        final Map<String, List<ParamFunc>> resultPersist = new HashMap<String, List<ParamFunc>>();
        final Map<String, List<Integer>> mapLangExistent = new HashMap<String, List<Integer>>();
        final ParamFuncType objType = getEntityManager().find(ParamFuncType.class, type);
        // Loading all the existing parameters
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ParamFunc> q = cb.createQuery(ParamFunc.class);
        final Root<ParamFunc> pf = q.from(ParamFunc.class);
        q.where(cb.and(cb.equal(pf.get("paramFuncType"), objType), cb.equal(pf.get("country"), country)));
        final TypedQuery<ParamFunc> tq = getEntityManager().createQuery(q);
        final List<ParamFunc> listResultParams = tq.getResultList();
        if (!listResultParams.isEmpty()) {
            for (final ParamFunc p : listResultParams) {
                List<ParamFunc> list = resultPersist.get(p.getEntry());
                List<Integer> lang = mapLangExistent.get(p.getEntry());
                // Here, "lang" can only be null too
                if (list == null) {
                    list = new ArrayList<ParamFunc>();
                    resultPersist.put(p.getEntry(), list);
                    lang = new ArrayList<Integer>();
                    mapLangExistent.put(p.getEntry(), lang);
                }
                list.add(p);
                lang.add(p.getLanguage().getId());
            }
        }
        final List<Language> languages = this.languageController.doList();
        if (languages != null && !languages.isEmpty()) {
            for (final Map.Entry<String, List<ParamFunc>> entryParam : resultPersist.entrySet()) {
                final List<Integer> langExistent = mapLangExistent.get(entryParam.getKey());
                for (final Language l : languages) {
                    if (!langExistent.contains(l.getId())) {
                        final ParamFunc p = new ParamFunc();
                        p.setEntry(entryParam.getKey());
                        p.setLanguage(l);
                        p.setParamFuncType(objType);
                        p.setCountry(country);
                        entryParam.getValue().add(p);
                    }
                }
            }
        }
        // Converting and returning the result
        Map<String, List<ParamFuncDto>> result = null;
        if (resultPersist != null && !resultPersist.isEmpty()) {
            result = new HashMap<String, List<ParamFuncDto>>();
            for (final Map.Entry<String, List<ParamFunc>> entryParam : resultPersist.entrySet()) {
                final List<ParamFuncDto> l = new ArrayList<ParamFuncDto>();
                result.put(entryParam.getKey(), l);
                for (final ParamFunc p : entryParam.getValue()) {
                    l.add(ConverterParamFunc.convertDbToDto(p));
                }
            }
        }
        return result;
    }

    /**
     * Saves the parameters.
     * @param the map of parameters
     * @throws DCOException a DCOException
     */
    public Map<String, List<ParamFuncDto>> saveParams(final Map<String, List<ParamFuncDto>> params)
            throws DCOException {
        if (params != null) {
            for (final List<ParamFuncDto> l : params.values()) {
                for (final ParamFuncDto p : l) {
                    if (p.getId() == null) {
                        // Only add non empty parameters (non empty values)
                        if (StringUtils.isNotBlank(p.getValue())) {
                            // Add a new ParamFunc in the base.
                            getEntityManager().merge(ConverterParamFunc.convertDtoToDb(p));

                            final CriteriaBuilder critBuild = getEntityManager().getCriteriaBuilder();
                            final CriteriaQuery<ParamFunc> query = critBuild.createQuery(ParamFunc.class);
                            final Root<ParamFunc> root = query.from(ParamFunc.class);

                            query.where(critBuild.equal(root.get("country"), p.getCountry()), critBuild.equal(root
                                    .get("language").get("id"), p.getLanguage().getId()), critBuild.equal(root
                                    .get("paramFuncType").get("id"), p.getParamFuncType().getId()), critBuild
                                    .equal(root.get("entry"), p.getEntry()), critBuild.equal(root.get("value"),
                                    p.getValue()));

                            final TypedQuery<ParamFunc> typeQuery = getEntityManager().createQuery(query);
                            final List<ParamFunc> result = typeQuery.getResultList();

                            // Set the id of the ParamFunc to the Dto Paramfunc (avoid error while merging
                            // ParamFunc the next time)
                            if (result.size() == Constants.VAR_ONE
                                    && result.get(Constants.VAR_ZERO) instanceof ParamFunc) {
                                final ParamFunc paramLoad = result.get(Constants.VAR_ZERO);
                                p.setId(paramLoad.getId());
                            }
                        }
                    } else {
                        if (StringUtils.isNotBlank(p.getValue())) {
                            getEntityManager().merge(ConverterParamFunc.convertDtoToDb(p));
                        } else {
                            getEntityManager().remove(getEntityManager().find(ParamFunc.class, p.getId()));
                            p.setId(null);
                        }
                    }
                }
            }
        }
        return params;
    }

    /**
     * Loads the entities.
     * @return the list of entities
     * @throws DCOException a DCOException
     */
    public List<LegalEntityDto> loadEntities() throws DCOException {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<LegalEntity> q = cb.createQuery(LegalEntity.class);
        final TypedQuery<LegalEntity> tq = getEntityManager().createQuery(q);
        final List<LegalEntity> entities = tq.getResultList();
        List<LegalEntityDto> result = null;
        if (entities != null) {
            result = new ArrayList<LegalEntityDto>();
            for (final LegalEntity e : entities) {
                result.add(ConverterLegalEntity.convertDbToDto(e));
            }
        }
        return result;
    }

    /**
     * Loads the entities.
     * @return the list of entities
     * @throws DCOException a DCOException
     */
    public List<ParamFuncDto> loadParamByCountryAndLanguage(final Integer type, final String country,
            final Integer language) throws DCOException {

        final List<ParamFuncDto> listParam = new ArrayList<ParamFuncDto>();

        final Query qParams = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.PARAM_LOAD_PARAMS_LANGUAGE));
        qParams.setParameter("type", type);
        qParams.setParameter("country", country);
        qParams.setParameter("language", language);

        final List<ParamFunc> listResult = qParams.getResultList();

        if (!listResult.isEmpty()) {
            for (final ParamFunc p : listResult) {
                listParam.add(ConverterParamFunc.convertDbToDto(p));
            }
        }

        return listParam;
    }

    /**
     * This method allow to load all the type List<ParamFunc> for each locale in countries.
     * @param type
     * @param countries
     * @param language
     * @return
     * @throws DCOException
     */
    public Map<String, List<ParamFuncDto>> loadParamMapByCountryAndLanguage(final Integer type,
            final List<String> countries, final Integer language) throws DCOException {

        final Map<String, List<ParamFuncDto>> map = new HashMap<String, List<ParamFuncDto>>();

        for (final String country : countries) {
            List<ParamFuncDto> listPF = loadParamByCountryAndLanguage(type, country, language);
            final Integer defaultLanguageIdLocal = getDefaultLanguageId();
            if (listPF.isEmpty() && defaultLanguageIdLocal != null && language != null
                    && defaultLanguageIdLocal.compareTo(language) != 0) {
                listPF = loadParamByCountryAndLanguage(type, country, defaultLanguageIdLocal);
            }

            // We also add the empty list, like this, we know that there is no paramFunc for this locale.
            map.put(country, listPF);
        }

        return map;

    }

    /**
     * Saves the entities.
     * @param the list of entities
     * @throws DCOException a DCOException
     */
    public void saveEntities(final List<LegalEntityDto> entities) throws DCOException {
        if (entities != null) {
            for (final LegalEntityDto e : entities) {
                if (e.getAddress() != null) {
                    getEntityManager().merge(ConverterAddress.convertDtoToDb(e.getAddress()));
                }
                getEntityManager().merge(ConverterLegalEntity.convertDtoToDb(e));
            }
        }
    }

    /**
     * Saves the languages.
     * @param the list of languages
     * @throws DCOException a DCOException
     */
    public void saveLanguages(final List<LanguageDto> languages) throws DCOException {
        if (languages != null) {
            for (final LanguageDto l : languages) {
                getEntityManager().merge(ConverterLanguage.convertDtoToDb(l, true));
            }
        }
    }

    /**
     * Loads the commercial messages.
     * @return the list of languages holding the commercial messages
     * @throws DCOException a DCOException
     */
    public List<LanguageDto> loadCommercialMessage() throws DCOException {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Language> q = cb.createQuery(Language.class);
        final Root<Language> l = q.from(Language.class);
        q.where(cb.equal(l.get("userInterface"), true));
        final TypedQuery<Language> tq = getEntityManager().createQuery(q);
        final List<Language> entities = tq.getResultList();
        List<LanguageDto> result = null;
        if (entities != null) {
            result = new ArrayList<LanguageDto>();
            for (final Language e : entities) {
                result.add(ConverterLanguage.convertDbToDto(e, true));
            }
        }
        return result;
    }

    /**
     * Saves the commercial messages.
     * @param the list of languages holding the commercial messages
     * @throws DCOException a DCOException
     */
    public void saveCommercialMessage(final List<LanguageDto> languages) throws DCOException {
        if (languages != null) {
            for (final LanguageDto l : languages) {
                getEntityManager().merge(ConverterLanguage.convertDtoToDb(l, true));
            }
        }
    }

    /**
     * Saves the countries.
     * @param the list of countries
     * @throws DCOException a DCOException
     */
    public void saveCountries(final List<CountryDto> countries) throws DCOException {
        if (countries != null) {
            for (final CountryDto c : countries) {
                getEntityManager().merge(ConverterCountry.convertDtoToDb(c, true));
            }
        }
    }

    /**
     * Delete a parameter
     * @param typeParam
     * @param country
     * @param entry
     */
    public void deleteParam(final Integer typeParam, final String country, final String entry) {

        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ParamFunc> q = cb.createQuery(ParamFunc.class);
        final Root<ParamFunc> l = q.from(ParamFunc.class);

        q.where(cb.equal(l.get("country"), country), cb.equal(l.get("paramFuncType").get("id"), typeParam),
                cb.equal(l.get("entry"), entry));

        final TypedQuery<ParamFunc> tq = getEntityManager().createQuery(q);
        final List<ParamFunc> params = tq.getResultList();
        if (params != null) {
            for (final ParamFunc param : params) {
                getEntityManager().remove(param);
            }
        }
    }

}