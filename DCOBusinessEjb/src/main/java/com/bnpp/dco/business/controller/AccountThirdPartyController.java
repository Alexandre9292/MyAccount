package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.AuthorizationThirdParty;
import com.bnpp.dco.business.dto.ThirdParty;
import com.bnpp.dco.business.dto.converter.ConverterAccountThirdParty;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;

public class AccountThirdPartyController extends GenericController {

    public void createAccountThirdPartyFromDto(final AccountThirdPartyDto accountThirdPartyDto)
            throws DCOException {

        final AccountThirdParty accountThirdParty = ConverterAccountThirdParty
                .convertDtoToDb(accountThirdPartyDto);

        final Account account = getEntityManager().find(Account.class, accountThirdParty.getAccount().getId());
        final ThirdParty tP = getEntityManager().find(ThirdParty.class, accountThirdParty.getThirdParty().getId());

        // Binding the tP to the Account
//        Set<AccountThirdParty> accountThirdPartiesSetForAccount = account.getAccountThirdParties();
//        if (accountThirdParty.getAccount().getAccountThirdParties() == null) {
//            accountThirdPartiesSetForAccount = new HashSet<AccountThirdParty>();
//        }
//        accountThirdPartiesSetForAccount.add(accountThirdParty);
//        account.setAccountThirdParties(accountThirdPartiesSetForAccount);

        // Binding to the third party to the account.
        Set<AccountThirdParty> accountThirdPartiesSetForThirdParty = tP.getAccountThirdParties();

        if (accountThirdPartiesSetForThirdParty == null) {
            accountThirdPartiesSetForThirdParty = new HashSet<AccountThirdParty>();
        }
        accountThirdPartiesSetForThirdParty.add(accountThirdParty);
        tP.setAccountThirdParties(accountThirdPartiesSetForThirdParty);

        accountThirdParty.setAccount(account);
        accountThirdParty.setThirdParty(tP);
        getEntityManager().persist(accountThirdParty);
        getEntityManager().merge(account);
        getEntityManager().merge(tP);
        getEntityManager().flush();
    }

    public void deleteAccountThirdParty(final Integer id) {
        final AccountThirdParty accountThirdParty = getEntityManager().find(AccountThirdParty.class, id);
        if (accountThirdParty != null) {
            getEntityManager().remove(accountThirdParty);
        }
    }

    public List<AccountThirdPartyDto> getAccountThirdPartyByAccountId(final Integer idAccount) throws DCOException {

        final List<AccountThirdPartyDto> accountThirdPartysRet = new ArrayList<AccountThirdPartyDto>();
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.ACCOUNT_THIRD_PARTY_ACCOUNT_ID));

        q.setParameter("idAccount", idAccount);

        final List<?> listResult = q.getResultList();
        if (!listResult.isEmpty()) {
            final List<AccountThirdParty> accountThirdPartys = (List<AccountThirdParty>) listResult;

            for (final AccountThirdParty acc : accountThirdPartys) {
                accountThirdPartysRet.add(ConverterAccountThirdParty.convertDbToDto(acc));
            }

        } else {
            return null;
        }

        return accountThirdPartysRet;

    }

    public AccountThirdPartyDto getAccountThirdPartyByAccountThirdPartyId(final Integer idAccount,
            final Integer idThirdParty) throws DCOException {

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.ACCOUNT_THIRD_PARTY));

        q.setParameter("idAccount", idAccount);
        q.setParameter("idThirdParty", idThirdParty);

        final List<?> listResult = q.getResultList();
        if (!listResult.isEmpty()) {
            final AccountThirdParty accountThirdPartys = (AccountThirdParty) listResult.get(0);

            final AccountThirdPartyDto accountThirdPartysRet = ConverterAccountThirdParty
                    .convertDbToDto(accountThirdPartys);
            return accountThirdPartysRet;
        } else {
            return null;
        }

    }

    public void saveAuthorizedThirdPartyListFromDto(final AccountThirdPartyDto accountThirdPartyDto)
            throws DCOException {
        final AccountThirdParty accountThirdParty = ConverterAccountThirdParty
                .convertDtoToDb(accountThirdPartyDto);

        Set<AuthorizationThirdParty> authorList = accountThirdParty.getAuthorizedList();
        if (accountThirdParty.getAuthorizedList() == null || accountThirdParty.getAuthorizedList().isEmpty()) {
            authorList = new HashSet<AuthorizationThirdParty>();
        }
        accountThirdParty.setAuthorizedList(authorList);
        getEntityManager().merge(accountThirdParty);
    }

}
