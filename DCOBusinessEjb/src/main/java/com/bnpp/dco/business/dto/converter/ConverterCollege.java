package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.College;
import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.business.dto.Signatory;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConverterCollege {

	public static CollegeDto convertDbToDto(final College db, final boolean convertSubObject) throws DCOException {
		CollegeDto result = null;
        if (db != null) {
            result = new CollegeDto();
            result.setId(db.getId());
            result.setName(db.getName());
            result.setAccount(ConverterAccount.convertDbToDto(db.getAccountG(), false));
            
            if(convertSubObject && db.getSignatories() != null){
            	final List<SignatoryDto> signatoryList = new ArrayList<SignatoryDto>();
                for (final Signatory signatory : db.getSignatories()) {
                	signatoryList.add(ConverterSignatory.convertDbToDto(signatory, false));
                }
                result.setSignatoriesList(signatoryList);
            }
            
            if(db.getRules() != null){
            	final List<RulesDto> rulesList = new ArrayList<RulesDto>();
                for (final Rules rules : db.getRules()) {
                	rulesList.add(ConverterRules.convertDbToDto(rules, false));
                }
                result.setRulesList(rulesList);
            }            
        }
        return result;
    }
	
	public static College convertDtoToDb(final CollegeDto dto, final boolean convertSubObject) throws DCOException {
		College college = null;
        if (dto != null) {
        	college = new College();
        	college.setId(dto.getId());
        	college.setName(dto.getName());
        	
        	//if(convertSubObject){
        		college.setAccountG(ConverterAccount.convertDtoToDb(dto.getAccount(), false));
        	//}
            
            if(dto.getSignatoriesList() != null && convertSubObject){
            	final Set<Signatory> signatoryList = new HashSet<Signatory>();
                for (final SignatoryDto signatory : dto.getSignatoriesList()) {
                	signatoryList.add(ConverterSignatory.convertDtoToDb(signatory, false));
                }
                college.setSignatories(signatoryList);
            }
            
            if(dto.getRulesList() != null && convertSubObject){
            	final Set<Rules> rulesList = new HashSet<Rules>();
                for (final RulesDto rules : dto.getRulesList()) {
                	rulesList.add(ConverterRules.convertDtoToDb(rules, false));
                }
                college.setRules(rulesList);
            }            
        }
        return college;
    }
	
}
