package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.AuthorizationThirdParty;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.dto.AuthorizationThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConverterAccountThirdParty {

    public static AccountThirdPartyDto convertDbToDto(final AccountThirdParty db) throws DCOException {
        AccountThirdPartyDto result = null;
        if (db != null) {
            result = new AccountThirdPartyDto();
            result.setThirdParty(ConverterThirdParty.convertDbToDto(db.getThirdParty(), false, true));
            result.setAccount(ConverterAccount.convertDbToDto(db.getAccount(), false));
            result.setAmountLimit(db.getAmountLimit());
            result.setDeviseAmountLimit(db.getDeviseAmountLimit());
            result.setStatusAmountLimit(db.getStatusAmountLimit());
            result.setPowerType(db.getPowerType());
            result.setPublicDeedReference(db.getPublicDeedReference());
            result.setBoardResolutionDate(db.getBoardResolutionDate());
            result.setSignatureAuthorization(ConverterParamFunc.convertDbToDto(db.getParamFunc()));
            result.setId(db.getId());
            if (db.getAuthorizedList() != null && !db.getAuthorizedList().isEmpty()) {
                final List<AuthorizationThirdPartyDto> thirdList = new ArrayList<AuthorizationThirdPartyDto>();
                for (final AuthorizationThirdParty third : db.getAuthorizedList()) {
                    thirdList.add(ConverterAuthorizationThirdParty.convertDbToDto(third));
                }
                result.setAuthorizedList(thirdList);
            }
        }
        return result;
    }

    public static AccountThirdParty convertDtoToDb(final AccountThirdPartyDto dto) throws DCOException {
        AccountThirdParty form = null;
        if (dto != null) {
            form = new AccountThirdParty();
            form.setThirdParty(ConverterThirdParty.convertDtoToDb(dto.getThirdParty(), false, true));
            form.setAccount(ConverterAccount.convertDtoToDb(dto.getAccount(), false));
            form.setAmountLimit(dto.getAmountLimit());
            form.setDeviseAmountLimit(dto.getDeviseAmountLimit());
            form.setStatusAmountLimit(dto.getStatusAmountLimit());
            form.setPowerType(dto.getPowerType());
            form.setPublicDeedReference(dto.getPublicDeedReference());
            form.setBoardResolutionDate(dto.getBoardResolutionDate());
            form.setParamFunc(ConverterParamFunc.convertDtoToDb(dto.getSignatureAuthorization()));
            if (dto.getId() != null) {
                form.setId(dto.getId());
            }
            if (dto.getAuthorizedList() != null && !dto.getAuthorizedList().isEmpty()) {
                final Set<AuthorizationThirdParty> thirdList = new HashSet<AuthorizationThirdParty>();
                for (final AuthorizationThirdPartyDto third : dto.getAuthorizedList()) {
                    thirdList.add(ConverterAuthorizationThirdParty.convertDtoToDb(third));
                }
                form.setAuthorizedList(thirdList);
            }
        }

        return form;
    }

    private ConverterAccountThirdParty() {

    }

}
