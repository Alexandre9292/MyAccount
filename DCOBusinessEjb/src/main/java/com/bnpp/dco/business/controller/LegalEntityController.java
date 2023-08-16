package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.LegalEntity;
import com.bnpp.dco.business.dto.converter.ConverterLegalEntity;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.LegalEntityDto;

public class LegalEntityController extends GenericController {

    public List<LegalEntityDto> list(final Integer country) {

        final StringBuilder query = new StringBuilder(
                PropertiesHelper.getMessage(DatabaseConstants.LEGAL_ENTITY_LIST));

        if (country != -1) {
            query.append(DatabaseConstants.JOIN_CLAUSE).append(
                    PropertiesHelper.getMessage(DatabaseConstants.LEGAL_ENTITY_FILTER_COUNTRY));
        }

        final Query q = getEntityManager().createQuery(query.toString());

        if (country != -1) {
            q.setParameter("country", country);
        }

        final List<?> result = q.getResultList();
        final List<LegalEntityDto> listLegalEntityDto = new ArrayList<LegalEntityDto>();
        for (final Object o : result) {
            if (o instanceof LegalEntity) {
                listLegalEntityDto.add(ConverterLegalEntity.convertDbToDto((LegalEntity) o));
            }
        }
        return listLegalEntityDto;
    }

}
