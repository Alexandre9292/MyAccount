package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.ParamFuncType;
import com.bnpp.dco.common.dto.ParamFuncTypeDto;

public final class ConverterParamFuncType {
    public static ParamFuncTypeDto convertDbToDto(final ParamFuncType db) {
        ParamFuncTypeDto result = null;
        if (db != null) {
            result = new ParamFuncTypeDto();
            result.setId(db.getId());
            result.setLabelDefault(db.getLabelDefault());
        }
        return result;
    }

    public static ParamFuncType convertDtoToDb(final ParamFuncTypeDto dto) {
        ParamFuncType result = null;
        if (dto != null) {
            result = new ParamFuncType();
            result.setId(dto.getId());
            result.setLabelDefault(dto.getLabelDefault());
        }
        return result;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterParamFuncType() {
    }

}
