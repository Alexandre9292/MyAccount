package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.AuthorizationThirdParty;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * ConverterAuthorizationThirdParty generated manually
 * @author afobatogue@aubay.com
 * @Date 24-12-2014
 */

public class ConverterAuthorizationThirdParty {

    public static AuthorizationThirdPartyDto convertDbToDto(final AuthorizationThirdParty db) throws DCOException {
        AuthorizationThirdPartyDto result = null;
        if (db != null) {
            result = new AuthorizationThirdPartyDto();
            result.setId(ConverterAuthorizationThirdPartyId.convertDbToDto(db.getId()));
            if (db.getAccountThirdParty() == null) {
                final AccountThirdParty atp = new AccountThirdParty();
                db.setAccountThirdParty(atp);
            }
            // result.setAccountThirdParty(ConverterAccountThirdParty.convertDbToDto(db.getAccountThirdParty()));
        }
        return result;
    }

    public static AuthorizationThirdParty convertDtoToDb(final AuthorizationThirdPartyDto dto) throws DCOException {
        AuthorizationThirdParty authorizationThirdParty = null;
        if (dto != null) {
            authorizationThirdParty = new AuthorizationThirdParty();
            authorizationThirdParty.setId(ConverterAuthorizationThirdPartyId.convertDtoToDb(dto.getId()));
            authorizationThirdParty.setAccountThirdParty(ConverterAccountThirdParty.convertDtoToDb(dto
                    .getAccountThirdParty()));
        }
        return authorizationThirdParty;
    }

    private ConverterAuthorizationThirdParty() {

    }

}
