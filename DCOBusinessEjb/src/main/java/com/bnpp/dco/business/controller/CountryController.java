package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Country;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.converter.ConverterCountry;
import com.bnpp.dco.business.dto.converter.ConverterEntity;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.exception.DCOException;

public class CountryController extends GenericController {

	/**
     * LOG
     */
    private final Logger LOG = LoggerFactory.getLogger(EntitiesController.class);

	
    public List<CountryDto> list() throws DCOException {
        final Query q = getEntityManager()
                .createQuery(PropertiesHelper.getMessage(DatabaseConstants.COUNTRY_LIST));

        final List<?> result = q.getResultList();
        final List<CountryDto> resultCountry = new ArrayList<CountryDto>();
        for (final Object object : result) {
            if (object instanceof Country) {
                final Country c = (Country) object;
                resultCountry.add(ConverterCountry.convertDbToDto(c, true));
            }
        }
        return resultCountry;
    }
    
    public List<String> list2() throws DCOException {
        final Query q = getEntityManager()
                .createQuery(PropertiesHelper.getMessage(DatabaseConstants.COUNTRY_LIST2));

        final List<?> result = q.getResultList();
        final List<CountryDto> resultCountry = new ArrayList<CountryDto>();
        final List<String> Country = new ArrayList<String>();
        for (final Object object : result) {
            if (object instanceof Country) {
                final Country c = (Country) object;
                resultCountry.add(ConverterCountry.convertDbToDto(c, true));
                Country.add(c.getLocale());
            }
        }
        return Country;
    }

    public List<CountryDto> getCountryByLegalEntity(final Integer legalEntity) throws DCOException {

        final StringBuilder query = new StringBuilder(PropertiesHelper.getMessage(DatabaseConstants.COUNTRY_LIST));

        if (legalEntity != -1) {
            query.append(PropertiesHelper.getMessage(DatabaseConstants.COUNTRY_FILTER_LEGAL_ENTITY));
        }

        final Query q = getEntityManager().createQuery(query.toString());

        if (legalEntity != -1) {
            q.setParameter("legalEntity", legalEntity);
        }

        final List<?> result = q.getResultList();
        final List<CountryDto> resultCountry = new ArrayList<CountryDto>();
        for (final Object object : result) {
            if (object instanceof Country) {
                final Country c = (Country) object;
                resultCountry.add(ConverterCountry.convertDbToDto(c, true));
            }
        }
        return resultCountry;
    }
    
    public CountryDto getCountryByLocal(final String country) throws DCOException {

    	return ConverterCountry.convertDbToDto(doGetCountryByLocal(country), true);
    }
    

    private Country doGetCountryByLocal(final String country) throws DCOException {
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.COUNTRY_BY_LOCAL));
        q.setParameter("loc", country);
        
        Country c = (Country) q.getSingleResult();
        
        if (c != null) {
        	
        } else {
            final String message = "Unknown Country";
            this.LOG.error(message);
        }
        return c;
    }

}
