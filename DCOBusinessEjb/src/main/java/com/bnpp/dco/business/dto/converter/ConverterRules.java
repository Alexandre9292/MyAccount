package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Rules;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConverterRules {

	public static RulesDto convertDbToDto(final Rules db, final boolean convertSubObject) throws DCOException {
		RulesDto result = null;
        if (db != null) {
            result = new RulesDto();
            result.setId(db.getId());
            result.setAccount(ConverterAccount.convertDbToDto(db.getAccount(), false));
            if (convertSubObject) {
	            result.setSignatory(ConverterSignatory.convertDbToDto(db.getSignatory(), false));
	            result.setSignatory2(ConverterSignatory.convertDbToDto(db.getSignatory2(), false));
	            result.setCollege(ConverterCollege.convertDbToDto(db.getCollege(), false));
	            result.setCollege2(ConverterCollege.convertDbToDto(db.getCollege2(), false));
            }
            result.setAmountMax(db.getAmountMax());
            result.setAmountMin(db.getAmountMin());
            result.setTypeOperation(db.getTypeOperation());
            result.setField(db.getField());       
        }
        return result;
    }
	
	public static Rules convertDtoToDb(final RulesDto dto, final boolean convertSubObject) throws DCOException {
		Rules rules = null;
        if (dto != null) {
        	rules = new Rules();
        	rules.setId(dto.getId());
        	rules.setAccount(ConverterAccount.convertDtoToDb(dto.getAccount(), false));
        	if (convertSubObject) {
	        	rules.setSignatory(ConverterSignatory.convertDtoToDb(dto.getSignatory(), false));
	        	rules.setSignatory2(ConverterSignatory.convertDtoToDb(dto.getSignatory2(), false));
	        	rules.setCollege(ConverterCollege.convertDtoToDb(dto.getCollege(), false));
	        	rules.setCollege2(ConverterCollege.convertDtoToDb(dto.getCollege2(), false));
        	}
        	rules.setAmountMax(dto.getAmountMax());
        	rules.setAmountMin(dto.getAmountMin());
        	rules.setTypeOperation(dto.getTypeOperation());
        	rules.setField(dto.getField());        
        }
        return rules;
    }
	
}
