package com.bnpp.dco.business.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.Country;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.Representatives;
import com.bnpp.dco.business.dto.converter.ConvertRepresentative;
import com.bnpp.dco.business.dto.converter.ConverterAccount;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.exception.DCOException;

public class RepresentativeController extends GenericController{

	public void saveRepresentativeFromDto(final RepresentativeDto representativeDto)
            throws DCOException {
        final Representatives representative = ConvertRepresentative.convertDtoToDb(representativeDto, true);
        if (representative == null) {
            throw new DCOException("The user doesn't have representative.", Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES);
        }
        getEntityManager().merge(representative);
        getEntityManager().flush();
        getEntityManager().clear();
		getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }
	
	public void deleteRepresentative(final List<RepresentativeDto> list) {

		for(RepresentativeDto r : list){
			final Query q = getEntityManager().createQuery(
	                PropertiesHelper.getMessage(DatabaseConstants.DELETE_COLLEGE));
	        q.setParameter("representativeID", r.getId());
		}
//        final College c = getEntityManager().find(College.class, id);
//        if (c != null) {
//            getEntityManager().remove(c);
//        }
    }
}
