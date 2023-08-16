package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.List;

import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.AccountForm;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterAccountForm {

    public static AccountFormDto convertDbToDto(final AccountForm db, final boolean convertSubObject)
            throws DCOException {
        AccountFormDto result = null;
        if (db != null) {

            result = new AccountFormDto();
            result.setId(db.getId());
            result.setEntity(ConverterEntity.convertDbToDto(db.getEntities(), false));

            if (convertSubObject && db.getAccounts() != null) {
                final List<AccountDto> accountDto = new ArrayList<AccountDto>();
                for (final Account e : db.getAccounts()) {
                    accountDto.add(ConverterAccount.convertDbToDto(e, true));
                }
                result.setAccountDtoList(accountDto);
            }
        }
        return result;
    }

    public static AccountForm convertDtoToDb(final AccountFormDto dto) throws DCOException {
        AccountForm form = null;
        if (dto != null) {
            form = new AccountForm();
            form.setId(dto.getId());
            form.setEntities(ConverterEntity.convertDtoToDb(dto.getEntity(), false));
        }
        return form;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterAccountForm() {
    }

}
