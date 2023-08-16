package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.Citizenship;
import com.bnpp.dco.business.dto.College;
import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.business.dto.Signatory;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.CitizenshipDto;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConverterSignatory {

	public static SignatoryDto convertDbToDto(final Signatory db, final boolean convertSubObject) throws DCOException {
		SignatoryDto result = null;
        if (db != null) {
            result = new SignatoryDto();
            result.setId(db.getId());
            result.setLegalEntityName(db.getLegalEntityName());
            result.setPositionName(db.getPositionName());
            result.setHomeAddress(ConverterAddress.convertDbToDto(db.getAddressByIdHomeAdress()));
            result.setName(db.getName());
            result.setFirstname(db.getFirstName());
            result.setBirthDate(db.getBirthDate());
            result.setBirthPlace(db.getBirthPlace());
            result.setEmail(db.getEmail());
            result.setRole(db.getRole());
            result.setTel(db.getTel());
            result.setFax(db.getFax());
            result.setReferenceId(db.getReferenceId());
            result.setNationality(db.getNationality());
            
            
//            if(convertSubObject && db.getCitizenships() != null){
//            	final List<CitizenshipDto> citizenshipList = new ArrayList<CitizenshipDto>();
//                for (final Citizenship citizenship : db.getCitizenships()) {
//                	citizenshipList.add(ConverterCitizenship.convertDbToDto(citizenship));
//                }
//                result.setCitizenshipsList(citizenshipList);
//            }
            
            if(db.getCollege() != null){
            	final List<CollegeDto> collegeList = new ArrayList<CollegeDto>();
                for (final College college : db.getCollege()) {
                	collegeList.add(ConverterCollege.convertDbToDto(college, false));
                }
                result.setCollegeList(collegeList);
            }
            
            if(db.getRules() != null){
            	final List<RulesDto> rulesList = new ArrayList<RulesDto>();
                for (final Rules rules : db.getRules()) {
                	rulesList.add(ConverterRules.convertDbToDto(rules, false));
                }
                result.setRulesList(rulesList);
            }
            
            if(db.getAccount() != null){
            	final List<AccountDto> accountList = new ArrayList<AccountDto>();
                for (final Account account : db.getAccount()) {
                	accountList.add(ConverterAccount.convertDbToDto(account, false));
                }
                result.setAccount(accountList);
            }            
        }
        return result;
    }
	
	public static Signatory convertDtoToDb(final SignatoryDto dto, final boolean convertSubObject) throws DCOException {
		Signatory signatory = null;
        if (dto != null) {
        	signatory = new Signatory();
        	signatory.setId(dto.getId());
        	signatory.setLegalEntityName(dto.getLegalEntityName());
        	signatory.setPositionName(dto.getPositionName());
        	signatory.setAddressByIdHomeAdress(ConverterAddress.convertDtoToDb(dto.getHomeAddress()));
        	signatory.setName(dto.getName());
        	signatory.setFirstName(dto.getFirstname());
            signatory.setBirthDate(dto.getBirthDate());
            signatory.setBirthPlace(dto.getBirthPlace());
            signatory.setEmail(dto.getEmail());
            signatory.setRole(dto.getRole());
            signatory.setTel(dto.getTel());
            signatory.setFax(dto.getFax());
            signatory.setReferenceId(dto.getReferenceId());
            signatory.setNationality(dto.getNationality());
            
            
//            if(convertSubObject && dto.getCitizenshipsList() != null){
//            	final Set<Citizenship> citizenshipList = new HashSet<Citizenship>();
//                for (final CitizenshipDto citizenship : dto.getCitizenshipsList()) {
//                	citizenshipList.add(ConverterCitizenship.convertDtoToDb(citizenship));
//                }
//                signatory.setCitizenships(citizenshipList);
//            }
            
            if(convertSubObject && dto.getCollegeList() != null){
            	final Set<College> collegeList = new HashSet<College>();
                for (final CollegeDto college : dto.getCollegeList()) {
                	collegeList.add(ConverterCollege.convertDtoToDb(college, false));
                }
                signatory.setCollege(collegeList);
            }
            
            if(convertSubObject && dto.getRulesList() != null){
            	final Set<Rules> rulesList = new HashSet<Rules>();
                for (final RulesDto rules : dto.getRulesList()) {
                	rulesList.add(ConverterRules.convertDtoToDb(rules, false));
                }
                signatory.setRules(rulesList);
            }
            
            if(convertSubObject && dto.getAccount() != null){
            	final Set<Account> accountList = new HashSet<Account>();
                for (final AccountDto account : dto.getAccount()) {
                	accountList.add(ConverterAccount.convertDtoToDb(account, false));
                }
                signatory.setAccount(accountList);
            }            
        }
        return signatory;
    }	
}
