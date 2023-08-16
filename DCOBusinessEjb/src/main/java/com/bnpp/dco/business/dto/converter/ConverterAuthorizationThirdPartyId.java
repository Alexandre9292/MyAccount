package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.AuthorizationThirdPartyId;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyIdDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * ConverterAuthorizationThirdPartyId generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */

public class ConverterAuthorizationThirdPartyId {

    public static AuthorizationThirdPartyIdDto convertDbToDto(final AuthorizationThirdPartyId db)
            throws DCOException {
        AuthorizationThirdPartyIdDto result = null;
        if (db != null) {
            result = new AuthorizationThirdPartyIdDto();
            result.setAccountThirdPartyId(db.getId());
            result.setThirdPartyId(db.getThirdId());
        }
        return result;
    }

    public static AuthorizationThirdPartyId convertDtoToDb(final AuthorizationThirdPartyIdDto dto)
            throws DCOException {
        AuthorizationThirdPartyId form = null;
        if (dto != null) {
            form = new AuthorizationThirdPartyId();
            form.setId(dto.getAccountThirdPartyId());
            form.setThirdId(dto.getThirdPartyId());
        }
        return form;
    }

    private ConverterAuthorizationThirdPartyId() {

    }

}
