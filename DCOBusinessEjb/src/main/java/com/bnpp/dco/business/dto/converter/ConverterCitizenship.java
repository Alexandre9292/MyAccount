package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Citizenship;
import com.bnpp.dco.common.dto.CitizenshipDto;
import com.bnpp.dco.common.exception.DCOException;

/**
 * ConverterCitizenship generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */
public class ConverterCitizenship {

    public static CitizenshipDto convertDbToDto(final Citizenship db) throws DCOException {
        CitizenshipDto result = null;
        if (db != null) {
            result = new CitizenshipDto();
            result.setCitizenship(ConverterCitizenshipId.convertDbToDto(db.getId()));
            result.setSignatory(ConverterSignatory.convertDbToDto(db.getSignatory(), false));
        }
        return result;
    }

    public static Citizenship convertDtoToDb(final CitizenshipDto dto) throws DCOException {
        Citizenship form = null;
        if (dto != null) {
            form = new Citizenship();
            form.setId(ConverterCitizenshipId.convertDtoToDb(dto.getCitizenship()));
            form.setSignatory(ConverterSignatory.convertDtoToDb(dto.getSignatory(), false));
        }

        return form;
    }

    private ConverterCitizenship() {

    }
}
