package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.ParamFunc;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterParamFunc {
    public static ParamFuncDto convertDbToDto(final ParamFunc db) throws DCOException {
        ParamFuncDto result = null;
        if (db != null) {
            result = new ParamFuncDto();
            result.setId(db.getId());
            result.setCountry(db.getCountry());
            result.setLanguage(ConverterLanguage.convertDbToDto(db.getLanguage(), false));
            result.setParamFuncType(ConverterParamFuncType.convertDbToDto(db.getParamFuncType()));
            result.setEntry(db.getEntry());
            result.setValue(db.getValue());
        }
        return result;
    }

    public static ParamFunc convertDtoToDb(final ParamFuncDto dto) throws DCOException {
        ParamFunc result = null;
        if (dto != null) {
            result = new ParamFunc();
            result.setId(dto.getId());
            result.setCountry(dto.getCountry());
            result.setLanguage(ConverterLanguage.convertDtoToDb(dto.getLanguage(), false));
            result.setParamFuncType(ConverterParamFuncType.convertDtoToDb(dto.getParamFuncType()));
            result.setEntry(dto.getEntry());
            result.setValue(dto.getValue());
        }
        return result;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterParamFunc() {
    }

}
