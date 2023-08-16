package com.bnpp.dco.business.controller;

import java.util.List;

import javax.naming.NamingException;
import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.AccountForm;
import com.bnpp.dco.business.dto.converter.ConverterAccountForm;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.exception.DCOException;

public class AccountFormController extends GenericController {

    public AccountFormDto saveAccountForm(final AccountFormDto accountFormDto) throws DCOException,
            NamingException {
        AccountFormDto accRet = getAccountFormEntity(accountFormDto.getEntity().getId());
        if (accRet == null) {
            final AccountForm accountForm = ConverterAccountForm.convertDtoToDb(accountFormDto);
            getEntityManager().persist(accountForm);
            accRet = getAccountFormEntity(accountFormDto.getEntity().getId());
        }
        return accRet;
    }

    public AccountFormDto getAccountFormEntity(final Integer idEntity) throws DCOException {
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.ACCOUNT_FORM_LIST));
        q.setParameter("entity", idEntity);
        final List<?> listResult = q.getResultList();
        if (!listResult.isEmpty()) {
            final AccountForm accountForm = (AccountForm) listResult.get(0);
            return ConverterAccountForm.convertDbToDto(accountForm, false);
        } else {
            return null;
        }
    }
}
