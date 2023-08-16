package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.LegalEntity;
import com.bnpp.dco.common.dto.LegalEntityDto;

public final class ConverterLegalEntity {
    public static LegalEntityDto convertDbToDto(final LegalEntity db) {
        LegalEntityDto result = null;
        if (db != null) {
            result = new LegalEntityDto();
            result.setId(db.getId());
            result.setLabel(db.getLabel());
            result.setAddress(ConverterAddress.convertDbToDto(db.getIdAddress()));
        }
        return result;
    }

    public static LegalEntity convertDtoToDb(final LegalEntityDto dto) {
        LegalEntity result = null;
        if (dto != null) {
            result = new LegalEntity();
            result.setId(dto.getId());
            result.setIdAddress(ConverterAddress.convertDtoToDb(dto.getAddress()));
            if (dto.getLabel() != null) {
                result.setLabel(dto.getLabel());
            }
        }
        return result;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterLegalEntity() {
    }

}
