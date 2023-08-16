package com.bnpp.dco.business.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.Citizenship;
import com.bnpp.dco.business.dto.College;
import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.business.dto.Signatory;
import com.bnpp.dco.business.dto.User;
import com.bnpp.dco.business.dto.converter.ConverterSignatory;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;

public class SignatoryController extends GenericController{

	public void saveSignatoryFromDto(final SignatoryDto signatoryDto)
            throws DCOException {
		
        final Signatory signatory = ConverterSignatory
                .convertDtoToDb(signatoryDto, false);
        

        Set<Account> accountList = signatory.getAccount();
        if (signatory.getAccount() == null || signatory.getAccount().isEmpty()) {
        	accountList = new HashSet<Account>();
        }
        signatory.setAccount(accountList);
        
        Set<Citizenship> citizenshipList = signatory.getCitizenships();
        if (signatory.getCitizenships() == null || signatory.getCitizenships().isEmpty()) {
        	citizenshipList = new HashSet<Citizenship>();
        }
        signatory.setCitizenships(citizenshipList);
        
        Set<College> collegeList = signatory.getCollege();
        if (signatory.getCollege() == null || signatory.getCollege().isEmpty()) {
        	collegeList = new HashSet<College>();
        }
        signatory.setCollege(collegeList);
        
        Set<Rules> rulesList = signatory.getRules();
        if (signatory.getRules() == null || signatory.getRules().isEmpty()) {
        	rulesList = new HashSet<Rules>();
        }
        signatory.setRules(rulesList);
        
        
        getEntityManager().merge(signatory);
    }
	
	
	public void deleteSignatory(final List<SignatoryDto> list) throws DCOException {

		for(SignatoryDto s : list){
			if(s.getId()!=null){
				if(s.getCollegeList() == null){
					saveSignatoryFromDto(s);
				}
			
				final Signatory signatory = getEntityManager().find(Signatory.class, s.getId());
				getEntityManager().remove(signatory);
			}
		}
    }
	
}
