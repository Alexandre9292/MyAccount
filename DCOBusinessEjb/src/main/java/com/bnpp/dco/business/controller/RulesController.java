package com.bnpp.dco.business.controller;

import java.util.List;

import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.common.dto.RulesDto;

public class RulesController extends GenericController{
	
	public void deleteRule(final List<RulesDto> list) {
		
		for(RulesDto r : list){
			if(r.getId() != null){
				final Rules rules = getEntityManager().find(Rules.class, r.getId());
				getEntityManager().remove(rules);
			}
		}
    }

}
