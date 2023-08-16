package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.DateFormat;
import com.bnpp.dco.business.dto.converter.ConverterDateFormat;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.DateFormatDto;

public class DateFormatController extends GenericController {

    public List<DateFormatDto> list() {
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.DATE_FORMAT_LIST));

        final List<?> result = q.getResultList();
        final List<DateFormatDto> resultDateFormat = new ArrayList<DateFormatDto>();
        for (final Object object : result) {
            if (object instanceof DateFormat) {
                resultDateFormat.add(ConverterDateFormat.convertDbToDto((DateFormat) object));
            }
        }
        return resultDateFormat;
    }
}
