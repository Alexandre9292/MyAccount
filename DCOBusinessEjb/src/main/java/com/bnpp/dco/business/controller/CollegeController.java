package com.bnpp.dco.business.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.College;
import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.business.dto.Signatory;
import com.bnpp.dco.business.dto.converter.ConverterCollege;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;
import com.bnpp.dco.common.exception.DCOException;

public class CollegeController extends GenericController{

	public void saveCollegeFromDto(final CollegeDto collegeDto)
            throws DCOException {
        final College college = ConverterCollege
                .convertDtoToDb(collegeDto, false);
        

        Set<Signatory> signatoryList = college.getSignatories();
        if (college.getSignatories() == null || college.getSignatories().isEmpty()) {
        	signatoryList = new HashSet<Signatory>();
        }
        college.setSignatories(signatoryList);
        
        Set<Rules> rulesList = college.getRules();
        if (college.getRules() == null || college.getRules().isEmpty()) {
        	rulesList = new HashSet<Rules>();
        }
        college.setRules(rulesList);
        
                
        getEntityManager().merge(college);
    }
	
	public void deleteCollege(final List<CollegeDto> list) {

		for(CollegeDto c : list){
			if(c.getId()!=null){
				final College college = getEntityManager().find(College.class, c.getId());
				getEntityManager().remove(college);
			}
		}
    }
}
