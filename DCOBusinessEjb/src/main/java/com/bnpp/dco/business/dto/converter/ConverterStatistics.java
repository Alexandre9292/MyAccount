package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Statistics;
import com.bnpp.dco.common.dto.StatisticsDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConverterStatistics {

    public static StatisticsDto convertDbToDto(final Statistics db) throws DCOException {

        StatisticsDto result = null;

        if (db != null) {
            result = new StatisticsDto();
            result.setId(db.getId());
            result.setNumber(db.getNumber());
            result.setTypeStat(db.getTypeStat());

            if (db.getCountry() != null) {
                result.setCountry(ConverterCountry.convertDbToDto(db.getCountry(), false));
            }

            if (db.getLanguage() != null) {
                result.setLanguage(ConverterLanguage.convertDbToDto(db.getLanguage(), false));
            }

            if (db.getDocumentType() != null) {
                result.setDocumentType(ConverterDocumentType.convertDbToDto(db.getDocumentType()));
            }

            if (db.getLegalEntity() != null) {
                result.setLegalEntity(ConverterLegalEntity.convertDbToDto(db.getLegalEntity()));
            }

            if (db.getUser() != null) {
                result.setUser(ConverterUser.convertDbToDto(db.getUser(), false));
            }

        }
        return result;
    }

    private ConverterStatistics() {
    }

}
