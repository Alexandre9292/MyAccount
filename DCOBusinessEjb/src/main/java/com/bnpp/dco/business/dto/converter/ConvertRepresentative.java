package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Country;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.Representatives;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConvertRepresentative {
	
	public static RepresentativeDto convertDbToDto(final Representatives db, final boolean convertSubObject) throws DCOException {
		RepresentativeDto result = null;
        if (db != null) {
            result = new RepresentativeDto();
            result.setId(db.getId());
            result.setName(db.getName());
            result.setFirstname(db.getFirstName());
            
            if(convertSubObject && db.getEntity() != null){
            	final List<EntityDto> entityList = new ArrayList<EntityDto>();
                for (final Entities entityL : db.getEntity()) {
                	entityList.add(ConverterEntity.convertDbToDto(entityL, false));
                }
                result.setEntityList(entityList);
            }
            
            if(/*convertSubObject && */db.getCountry() != null){
            	final List<CountryDto> countryList = new ArrayList<CountryDto>();
                for (final Country countryL : db.getCountry()) {
                	countryList.add(ConverterCountry.convertDbToDto(countryL, false));
                }
                result.setCountryList(countryList);
            }

        }
        return result;
    }
	
	public static Representatives convertDtoToDb(final RepresentativeDto dto, final boolean convertSubObject) throws DCOException {
		Representatives representatives = null;
        if (dto != null) {
        	representatives = new Representatives();
        	representatives.setId(dto.getId());
        	representatives.setName(dto.getName());
        	representatives.setFirstName(dto.getFirstname());
        	
        	if (convertSubObject && dto.getEntityList() != null) {
                final Set<Entities> entityList = new HashSet<Entities>();
                for (final EntityDto EntityL : dto.getEntityList()) {
                	entityList.add(ConverterEntity.convertDtoToDb(EntityL, false));
                }
                representatives.setEntity(entityList);
            }
        	
        	if (/*convertSubObject && */dto.getCountryList() != null) {
                final Set<Country> countryList = new HashSet<Country>();
                for (final CountryDto CountryL : dto.getCountryList()) {
                	countryList.add(ConverterCountry.convertDtoToDb(CountryL, false));
                }
                representatives.setCountry(countryList);
            }
        }

        return representatives;
    }

}
