package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Language;
import com.bnpp.dco.business.dto.converter.ConverterLanguage;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Controller managing the operations on languages.
 */
public final class LanguageController extends GenericController {

    /**
     * Lists all the languages from the database.
     * @return the list of languages converted to DTO objects
     * @throws DCOException
     */
    public List<LanguageDto> list() throws DCOException {
        final List<LanguageDto> resultLanguages = new ArrayList<LanguageDto>();
        for (final Language l : doList()) {
            resultLanguages.add(ConverterLanguage.convertDbToDto(l, true));
        }
        return resultLanguages;
    }

    /**
     * Actually load the objects from the database.
     * @return the list of languages
     * @throws DCOException
     */
    public List<Language> doList() {
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.LANGUAGE_LIST));
        return q.getResultList();
    }

    /**
     * Lists all the languages flagged as user interface languages from the database.
     * @return the list of languages converted to DTO objects
     * @throws DCOException
     */
    public List<LanguageDto> listInterface() throws DCOException {
        final List<LanguageDto> resultLanguages = new ArrayList<LanguageDto>();
        for (final Language l : doListInterface()) {
            resultLanguages.add(ConverterLanguage.convertDbToDto(l, true));
        }
        return resultLanguages;
    }

    /**
     * Actually load the objects from the database.
     * @return the list of languages
     * @throws DCOException
     */
    public List<Language> doListInterface() {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Language> q = cb.createQuery(Language.class);
        final Root<Language> l = q.from(Language.class);
        q.where(cb.equal(l.get("userInterface"), true));
        final TypedQuery<Language> tq = getEntityManager().createQuery(q);
        return tq.getResultList();
    }

    /**
     * Get a language by its Id.
     * @throws DCOException
     */
    public LanguageDto getLanguageById(final String locale) throws DCOException {
        LanguageDto ret = null;
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.LANGUAGE_LOCALE));
        q.setParameter("locale", locale);
        if (q.getResultList() != null && q.getResultList().size() > 0
                && q.getResultList().get(Constants.VAR_ZERO) instanceof Language) {
            ret = ConverterLanguage.convertDbToDto((Language) q.getResultList().get(Constants.VAR_ZERO), false);
        }
        return ret;
    }
}