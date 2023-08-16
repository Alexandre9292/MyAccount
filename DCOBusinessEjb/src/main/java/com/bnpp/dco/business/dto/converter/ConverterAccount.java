package com.bnpp.dco.business.dto.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.College;
import com.bnpp.dco.business.dto.ParamFunc;
import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.business.dto.Signatory;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterAccount {

    public static AccountDto convertDbToDto(final Account db, final boolean convertSubObject) throws DCOException {
        AccountDto result = null;
        if (db != null) {
            result = new AccountDto();
            result.setId(db.getId());

            result.setCurrency(ConverterParamFunc.convertDbToDto(db.getParamFuncByIdCurrency()));
            result.setPeriodicity(ConverterParamFunc.convertDbToDto(db.getParamFuncByIdPeriodicity()));
            result.setChannelParam(ConverterParamFunc.convertDbToDto(db.getParamFuncByIdChannel()));
            result.setTypeAccount(ConverterParamFunc.convertDbToDto(db.getParamFuncByIdTypeAccount()));
            result.setAccountForm(ConverterAccountForm.convertDbToDto(db.getAccountForm(), false));
            result.setAddress(ConverterAddress.convertDbToDto(db.getAddress()));
            result.setReference(db.getReference());
            result.setCountryAccount(ConverterCountry.convertDbToDto(db.getCountryAccount(), false));
            result.setCountry(db.getCountry());
            result.setCommercialRegister(db.getRegisterCompanyNumber());
            result.setVatNumber(db.getVatNumberAccount());
            result.setCommunicationLanguage(ConverterLanguage.convertDbToDto(db.getIdComLanguage(), false));
            result.setBranchName(db.getBranchName());
            result.setName(db.getName());
            
            result.setCreationDate(db.getCreationDate());
            result.setEditDate(db.getEditDate());
            // TO DO : prendre en compte le format de date des pr�f�rences
            if (result.getCreationDate() != null && result.getEditDate() != null) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            result.setCreationDateString(dateFormat.format(result.getCreationDate()));
	            result.setEditDateString(dateFormat.format(result.getEditDate()));
            }
            
            result.setPourcentage(db.getPourcentage());
            result.setStrategyDocument(db.getStrategyDocument());
            result.setChannel(db.getChannel());
            result.setEntity(ConverterEntity.convertDbToDto(db.getEntity(), false));

            if (db.getStatementTypes() != null) {
                final List<ParamFuncDto> paramFList = new ArrayList<ParamFuncDto>();
                for (final ParamFunc paramF : db.getStatementTypes()) {
                    paramFList.add(ConverterParamFunc.convertDbToDto(paramF));
                }
                result.setStatementTypes(paramFList);
            }

//            if (convertSubObject && db.getAccountThirdParties() != null) {
//                final List<AccountThirdPartyDto> accountThirdPartyDto = new ArrayList<AccountThirdPartyDto>();
//                for (final AccountThirdParty e : db.getAccountThirdParties()) {
//
//                    accountThirdPartyDto.add(ConverterAccountThirdParty.convertDbToDto(e));
//                }
//                result.setAccountThirdPartyList(accountThirdPartyDto);
//            }
            
            
            if (convertSubObject && db.getCollege() != null) {
                final List<CollegeDto> collegeDto = new ArrayList<CollegeDto>();
                for (final College e : db.getCollege()) {
                	collegeDto.add(ConverterCollege.convertDbToDto(e, true));
                }
                result.setCollegeList(collegeDto);
            }
            
            
            if (convertSubObject && db.getRules() != null) {
                final List<RulesDto> rulesDto = new ArrayList<RulesDto>();
                for (final Rules e : db.getRules()) {

                	rulesDto.add(ConverterRules.convertDbToDto(e, true));
                }
                result.setRulesList(rulesDto);
            }
            if (convertSubObject && db.getSignatory() != null) {
                final List<SignatoryDto> signatoryDto = new ArrayList<SignatoryDto>();
                for (final Signatory e : db.getSignatory()) {

                	signatoryDto.add(ConverterSignatory.convertDbToDto(e, false));
                }
                result.setSignatoriesList(signatoryDto);
            }
        }
        return result;
    }

    public static Account convertDtoToDb(final AccountDto dto, final boolean convertSubObject) throws DCOException {
        Account account = null;
        if (dto != null) {
            account = new Account();
            account.setId(dto.getId());
            account.setParamFuncByIdCurrency(ConverterParamFunc.convertDtoToDb(dto.getCurrency()));
            account.setCountryAccount(ConverterCountry.convertDtoToDb(dto.getCountryAccount(), false));
            account.setParamFuncByIdPeriodicity(ConverterParamFunc.convertDtoToDb(dto.getPeriodicity()));
            account.setParamFuncByIdChannel(ConverterParamFunc.convertDtoToDb(dto.getChannelParam()));
            account.setParamFuncByIdTypeAccount(ConverterParamFunc.convertDtoToDb(dto.getTypeAccount()));
            account.setAccountForm(ConverterAccountForm.convertDtoToDb(dto.getAccountForm()));
            account.setAddress(ConverterAddress.convertDtoToDb(dto.getAddress()));
            account.setReference(dto.getReference());
            account.setCountry(dto.getCountry());
            account.setRegisterCompanyNumber(dto.getCommercialRegister());
            account.setVatNumberAccount(dto.getVatNumber());
            account.setIdComLanguage(ConverterLanguage.convertDtoToDb(dto.getCommunicationLanguage(), false));
            account.setBranchName(dto.getBranchName());
            account.setName(dto.getName());
            account.setCreationDate(dto.getCreationDate());
            account.setEditDate(dto.getEditDate());
            account.setPourcentage(dto.getPourcentage());
            account.setStrategyDocument(dto.getStrategyDocument());
            account.setChannel(dto.getChannel());
            account.setEntity(ConverterEntity.convertDtoToDb(dto.getEntity(), false));

            if (dto.getStatementTypes() != null && !dto.getStatementTypes().isEmpty()) {
                final Set<ParamFunc> paramFList = new HashSet<ParamFunc>();
                for (final ParamFuncDto paramF : dto.getStatementTypes()) {
                    paramFList.add(ConverterParamFunc.convertDtoToDb(paramF));
                }
                account.setStatementTypes(paramFList);
            }
                       
            
            if (convertSubObject && dto.getCollegeList() != null && !dto.getCollegeList().isEmpty()) {
                final Set<College> college = new HashSet<College>();
                for (final CollegeDto e : dto.getCollegeList()) {

                	college.add(ConverterCollege.convertDtoToDb(e, true));
               }
                account.setCollege(college);
            }
            
            
            if (convertSubObject && dto.getRulesList() != null && !dto.getRulesList().isEmpty()) {
                final Set<Rules> rules = new HashSet<Rules>();
                for (final RulesDto e : dto.getRulesList()) {

                	rules.add(ConverterRules.convertDtoToDb(e, true));
                }
                account.setRules(rules);
            }
            if (convertSubObject && dto.getSignatoriesList() != null && !dto.getSignatoriesList().isEmpty()) {
                final Set<Signatory> signatory = new HashSet<Signatory>();
                for (final SignatoryDto e : dto.getSignatoriesList()) {

                	signatory.add(ConverterSignatory.convertDtoToDb(e, true));
                }
                account.setSignatory(signatory);
            }
        }

        return account;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterAccount() {
    }

}
