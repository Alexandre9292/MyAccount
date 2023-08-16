package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.AccountForm;
import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.College;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.ParamFunc;
import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.business.dto.Signatory;
import com.bnpp.dco.business.dto.converter.ConverterAccount;
import com.bnpp.dco.business.dto.converter.ConverterCollege;
import com.bnpp.dco.business.dto.converter.ConverterSignatory;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;

public class AccountController extends GenericController {

	private StatsController statsController;

	public StatsController getStatsController() {
		return this.statsController;
	}

	public void setStatsController(final StatsController statsController) {
		this.statsController = statsController;
	}

	public AccountDto createAccountFromDto(final AccountDto accountDto) throws DCOException {

		final Account account = ConverterAccount.convertDtoToDb(accountDto, true);

		Set<ParamFunc> statementTypes = account.getStatementTypes();
		if (account.getStatementTypes() == null || account.getStatementTypes().isEmpty()) {
			statementTypes = new HashSet<ParamFunc>();
		}
		account.setStatementTypes(statementTypes);

		Set<Rules> rulesList = account.getRules();
		if (account.getRules() == null || account.getRules().isEmpty()) {
			rulesList = new HashSet<Rules>();
		}
		account.setRules(rulesList);

		Set<College> collegeList = account.getCollege();
		if (account.getCollege() == null || account.getCollege().isEmpty()) {
			collegeList = new HashSet<College>();
		}
		account.setCollege(collegeList);

		Set<Signatory> signatoryList = account.getSignatory();
		if (account.getSignatory() == null || account.getSignatory().isEmpty()) {
			signatoryList = new HashSet<Signatory>();
		}
		account.setSignatory(signatoryList);

		Account returnAccount = getEntityManager().merge(account);
		getEntityManager().flush();

		return ConverterAccount.convertDbToDto(returnAccount, false);

	}

	public List<AccountDto> list() throws DCOException {
		final Query q = getEntityManager().createQuery(PropertiesHelper.getMessage(DatabaseConstants.ACCOUNT_LIST));

		final List<?> result = q.getResultList();
		final List<AccountDto> resultAccount = new ArrayList<AccountDto>();
		for (final Object object : result) {
			if (object instanceof Account) {
				final Account c = (Account) object;
				resultAccount.add(ConverterAccount.convertDbToDto(c, true));
			}
		}
		return resultAccount;
	}

	/**
	 * Get account data from its id
	 * 
	 * @param idAccountForm
	 * @return
	 * @throws DCOException
	 */
	public AccountDto getAccountById(final Integer idAccount) throws DCOException {
		AccountDto accountRes = new AccountDto();
		final Query q = getEntityManager()
				.createQuery(PropertiesHelper.getMessage(DatabaseConstants.ENTITY_ACCOUNT_BY_ID));
		q.setParameter("idAccount", idAccount);

		Account result = (Account) q.getSingleResult();
		if (result != null) {
			accountRes = ConverterAccount.convertDbToDto(result, true);
		} else {
			return null;
		}
		return accountRes;
	}

	/**
	 * Get all the account from the entity identified by its form id
	 * @param idAccountForm
	 * @return
	 * @throws DCOException
	 */
	public List<AccountDto> getAccountByAccountFormId(final Integer idAccountForm) throws DCOException {

		final List<AccountDto> accountsRet = new ArrayList<AccountDto>();

		final Query q = getEntityManager()
				.createQuery(PropertiesHelper.getMessage(DatabaseConstants.ACCOUNT_BY_ACCOUNT_FORM_LIST));

		q.setParameter("idAccountForm", idAccountForm);

		final List<?> listResult = q.getResultList();
		if (!listResult.isEmpty()) {
			final List<Account> accounts = (List<Account>) listResult;

			for (final Account acc : accounts) {
				accountsRet.add(ConverterAccount.convertDbToDto(acc, false));	// true
			}

		} else {
			return null;
		}

		return accountsRet;

	}
	
	/**
	 * Get all the accounts from the same country
	 * @param country
	 * @return
	 */
	public List<AccountDto> getAccountsFromCountry(final String country, final Integer idAccountForm,
			final Integer idAccount) throws DCOException {
		final List<AccountDto> accountsRet = new ArrayList<AccountDto>();
		
		
		
		final Query q = getEntityManager()
				.createQuery(PropertiesHelper.getMessage(DatabaseConstants.ACCOUNTS_SAME_COUNTRY));
		q.setParameter("country", country);
		q.setParameter("idAccountForm", idAccountForm);
		if (idAccount == null) {
			q.setParameter("idAccount", -1);
		} else {
			q.setParameter("idAccount", idAccount);
		}
		
		final List<?> listResult = q.getResultList();
		if (!listResult.isEmpty()) {
			final List<Account> accounts = (List<Account>) listResult;
			for (final Account acc : accounts) {
				accountsRet.add(ConverterAccount.convertDbToDto(acc, true));
			}
		} else {
			return null;
		}
		
		return accountsRet;
	}

	/**
	 * Allow to save the specified list of account + allow to increment the
	 * stats in function of the saved countries's accounts.
	 * 
	 * @param accounts
	 * @throws DCOException
	 * @throws NamingException
	 */
	public void saveAccount(final List<AccountDto> accounts, final Integer accountFormId)
			throws DCOException, NamingException {
		Account account = null;
		if (accounts != null && !accounts.isEmpty()) {

			final AccountForm af = getEntityManager().find(AccountForm.class, accountFormId);

			for (final AccountDto acc : accounts) {
				if (acc.getReference() != null && StringUtils.isNotBlank(acc.getReference())) {
					account = ConverterAccount.convertDtoToDb(acc, true);
					if (account.getAddress() != null) {
						if (account.getId() == null) {
							getEntityManager().persist(account.getAddress());
						} else {
							getEntityManager().merge(account.getAddress());
						}
					}
					if (af != null && af.getAccounts() != null && account.getId() == null) {
						// Link the new account to the accountForm
						af.getAccounts().add(account);
						getEntityManager().persist(account);
						getEntityManager().flush();
					} else {
						getEntityManager().merge(account);
					}
				}
			}
			getEntityManager().merge(af);

			// Link the entity to the accountForm
			if (af != null && af.getEntities() != null) {
				final Entities entity = getEntityManager().find(Entities.class, af.getEntities().getId());
				entity.getAccountForms().add(af);
				getEntityManager().merge(entity);
			}
		}
	}

	/**
	 * Allow to delete an account
	 * 
	 * @param id
	 */
	public void removeAccount(final Integer id) {

		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AccountThirdParty> q = cb.createQuery(AccountThirdParty.class);
		final Root<AccountThirdParty> pf = q.from(AccountThirdParty.class);

		q.where(cb.equal(pf.get("account").get("id"), id));
		final TypedQuery<AccountThirdParty> tq = getEntityManager().createQuery(q);
		final List<AccountThirdParty> result = tq.getResultList();

		for (final Object object : result) {
			if (object instanceof AccountThirdParty) {
				getEntityManager().remove(object);
			}
		}

		/*********************/

		final Account account = getEntityManager().find(Account.class, id);
		// if (account.getAccountThirdParties() != null) {
		// for (final AccountThirdParty act : account.getAccountThirdParties())
		// {
		// getEntityManager().remove(act);
		// }
		// }

		final AccountForm af = getEntityManager().find(AccountForm.class, account.getAccountForm().getId());
		final Iterator<Account> itAccount = af.getAccounts().iterator();
		final Set<Account> accounts = new HashSet<Account>();
		while (itAccount.hasNext()) {
			final Account accountDB = itAccount.next();

			if (accountDB.getId() != null && account.getId() != null
					&& accountDB.getId().compareTo(account.getId()) != 0) {
				accounts.add(accountDB);
			}
		}
		af.setAccounts(accounts);
		getEntityManager().merge(af);

		getEntityManager().remove(account);

	}

	/**
	 * Allow to increment the number of printedDoc by Country.
	 * 
	 * @param accounts
	 * @throws DCOException
	 */
	public void incrementPrintedDocByCountry(final List<AccountDto> accounts) throws DCOException {
		if (accounts != null) {
			for (final AccountDto account : accounts) {
				if (account != null && account.getCountry() != null) {
					getStatsController().incrementPrintedDocByCountry(ConverterAccount.convertDtoToDb(account, false),
							Constants.STATS_TYPE_PRINT_COUNTRY);
				}
			}
		}
	}

	/**
	 * Update an entity.
	 * 
	 * @param accountDto
	 * @return
	 * @throws DCOException
	 * @throws NamingException
	 */
	public AccountDto updateAccount(final AccountDto accountDto) throws DCOException, NamingException {
		Account account = null;

		if (accountDto != null) {
			
			/* STOCKAGE AVANT SAUVEGARDES */
			List<SignatoryDto> signatoryListTemp = new ArrayList<SignatoryDto>();
			if (accountDto.getSignatoriesList() != null && !accountDto.getSignatoriesList().isEmpty()) {
				for (SignatoryDto s : accountDto.getSignatoriesList()) {
					if (s.getCollegeList() != null) {
						for (CollegeDto c : s.getCollegeList()) {
							c.setAccount(accountDto);
						}
					}
					signatoryListTemp.add(s);
				}
			}

			List<CollegeDto> collegeListTemp = new ArrayList<CollegeDto>();
			if (accountDto.getCollegeList() != null && !accountDto.getCollegeList().isEmpty()) {
				for (CollegeDto c : accountDto.getCollegeList()) {
					c.setAccount(accountDto);
					if (accountDto.getSignatoriesList() != null) {
						for (SignatoryDto s : accountDto.getSignatoriesList()) {
							if (s.getCollegeList() != null) {
								for (CollegeDto cs : s.getCollegeList()) {
									if (cs.getName().equals(c.getName())) {
										if (c.getSignatoriesList() == null) {
											c.setSignatoriesList(new ArrayList<SignatoryDto>());
										}
										c.getSignatoriesList().add(s);
									}
								}
							}
						}
					}
					collegeListTemp.add(c);
				}
			}

			List<RulesDto> rulesListTemp = new ArrayList<RulesDto>();
			if (accountDto.getRulesList() != null && !accountDto.getRulesList().isEmpty()) {
				for (RulesDto r : accountDto.getRulesList()) {
					r.setAccount(accountDto);
					rulesListTemp.add(r);
				}
			}

			/* PREMIERE SAUVEGARDE LES GROUPES */
			accountDto.setRulesList(null);
			accountDto.setCollegeList(collegeListTemp);
			accountDto.setSignatoriesList(null);

			List<CollegeDto> registeredCollege = new ArrayList<CollegeDto>();
			for (CollegeDto c : accountDto.getCollegeList()) {
				if(c.getId() == null){
					College returnCol = getEntityManager().merge(ConverterCollege.convertDtoToDb(c, false));
					getEntityManager().flush();
					registeredCollege.add(ConverterCollege.convertDbToDto(returnCol, false));
				}
				else {
					registeredCollege.add(c);
				}
			}
			AccountDto returnAccountDto = accountDto;
			returnAccountDto.setCollegeList(registeredCollege);

			/* DEUXIEME SAUVEGARDE POUR LES SIGNATORIES */
			List<CollegeDto> collegesList = returnAccountDto.getCollegeList();
			Collections.sort(collegesList);

			List<SignatoryDto> signatoriesListToSave = new ArrayList<SignatoryDto>();
			if (signatoryListTemp != null && !signatoryListTemp.isEmpty()) {
				for (SignatoryDto s : signatoryListTemp) {
					if (s.getCollegeIndexes() != null) {
						List<CollegeDto> subcribedColleges = new ArrayList<CollegeDto>();
						for (Integer index : s.getCollegeIndexes()) {
							subcribedColleges.add(collegesList.get(index));
						}
						s.setCollegeList(subcribedColleges);
					}
					signatoriesListToSave.add(s);
				}
			}
			
			List<SignatoryDto> signatoriesSaved = new ArrayList<SignatoryDto>();
			for (SignatoryDto s : signatoriesListToSave) {
				if(s.getId() == null){
					Signatory returnSig = getEntityManager().merge(ConverterSignatory.convertDtoToDb(s, true));
					getEntityManager().flush();
					signatoriesSaved.add(ConverterSignatory.convertDbToDto(returnSig, true));
				} else {
					signatoriesSaved.add(s);
				}
			}
			AccountDto returnAccountDto2 = accountDto;
			returnAccountDto2.setSignatoriesList(signatoriesSaved);

			/* TROISIEME SAUVEGARDE POUR LES RULES */
			List<SignatoryDto> signatories = returnAccountDto2.getSignatoriesList();
			List<CollegeDto> colleges = returnAccountDto2.getCollegeList();
			Collections.sort(colleges);
			Collections.sort(signatories);

			List<RulesDto> rulesListToSave = new ArrayList<RulesDto>();
			if (rulesListTemp != null && !rulesListTemp.isEmpty()) {
				for (RulesDto r : rulesListTemp) {
					if (r.getIndexSignatory1() != null) {
						r.setSignatory(signatories.get(r.getIndexSignatory1()));
					}
					if (r.getIndexSignatory2() != null) {
						r.setSignatory2(signatories.get(r.getIndexSignatory2()));
					}
					if (r.getIndexCollege1() != null) {
						r.setCollege(colleges.get(r.getIndexCollege1()));
					}
					if (r.getIndexCollege2() != null) {
						r.setCollege2(colleges.get(r.getIndexCollege2()));
					}
					rulesListToSave.add(r);
				}
			}
			returnAccountDto2.setRulesList(rulesListToSave);

			account = ConverterAccount.convertDtoToDb(returnAccountDto2, true);
			if (account == null) {
				throw new DCOException("The user doesn't have account.",
						Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES);
			}
			Account returnAccount3 = getEntityManager().merge(account);
			getEntityManager().flush();

			getEntityManager().clear();
			// getEntityManager().close();
			getEntityManager().getEntityManagerFactory().getCache().evictAll();

//			AccountDto sol1 = getAccountById(returnAccount3.getId());
//			AccountDto sol2 = ConverterAccount.convertDbToDto(returnAccount3, true);
//			sol2.setCollegeList(sol1.getCollegeList());
			// return getAccountById(returnAccount3.getId());
			return ConverterAccount.convertDbToDto(returnAccount3, true);
		}
		return null;
	}
	
	/** 
	 * Simple update for account 
	 * @param accountDto
	 * @return
	 * @throws DCOException
	 * @throws NamingException
	 */
	public AccountDto simpleUpdate(final AccountDto accountDto) throws DCOException, NamingException {
		Account returnAccount = getEntityManager().merge(ConverterAccount.convertDtoToDb(accountDto, true));
		getEntityManager().flush();
		getEntityManager().clear();
		getEntityManager().getEntityManagerFactory().getCache().evictAll();
		return ConverterAccount.convertDbToDto(returnAccount, true);
	}
}
