package com.bnpp.dco.business.controller;

import com.bnpp.dco.business.dto.Citizenship;
import com.bnpp.dco.business.dto.CitizenshipId;
import com.bnpp.dco.business.dto.converter.ConverterCitizenshipId;
import com.bnpp.dco.common.dto.CitizenshipIdDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * CitizenshipController generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */

public class CitizenshipController extends GenericController {

    public void deleteCitizenship(final CitizenshipIdDto id) throws DCOException {
        final CitizenshipId token = ConverterCitizenshipId.convertDtoToDb(id);
        if (getEntityManager().find(Citizenship.class, token) != null) {
            getEntityManager().remove(getEntityManager().find(Citizenship.class, token));
        }
    }
}
