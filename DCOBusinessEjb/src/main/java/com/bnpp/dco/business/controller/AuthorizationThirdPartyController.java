package com.bnpp.dco.business.controller;

import com.bnpp.dco.business.dto.AuthorizationThirdParty;
import com.bnpp.dco.business.dto.AuthorizationThirdPartyId;
import com.bnpp.dco.business.dto.converter.ConverterAuthorizationThirdPartyId;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyIdDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * AuthorizationThirdPartyController generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */

public class AuthorizationThirdPartyController extends GenericController {

    public void deleteAuthorizationThirdParty(final AuthorizationThirdPartyIdDto authorizationThirdParty)
            throws DCOException {
        final AuthorizationThirdPartyId token = ConverterAuthorizationThirdPartyId
                .convertDtoToDb(authorizationThirdParty);
        if (getEntityManager().find(AuthorizationThirdParty.class, token) != null) {
            getEntityManager().remove(getEntityManager().find(AuthorizationThirdParty.class, token));
        }
    }
}
