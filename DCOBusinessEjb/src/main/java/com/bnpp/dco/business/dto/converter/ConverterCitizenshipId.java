package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.CitizenshipId;
import com.bnpp.dco.common.dto.CitizenshipIdDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;

/**
 * ConverterCitizenshipId generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */
public class ConverterCitizenshipId {

    public static CitizenshipIdDto convertDbToDto(final CitizenshipId db) throws DCOException {
        CitizenshipIdDto result = null;
        if (db != null) {
            result = new CitizenshipIdDto();
            result.setSignatoryId(db.getSignatoryId());
            result.setCountry(LocaleUtil.stringToCountry(db.getCountry()));
            result.setValue(db.getValue());
        }
        return result;
    }

    public static CitizenshipId convertDtoToDb(final CitizenshipIdDto dto) throws DCOException {
        CitizenshipId form = null;
        if (dto != null) {
            form = new CitizenshipId();
            form.setSignatoryId(dto.getSignatoryId());
            form.setCountry(LocaleUtil.countryToString(dto.getCountry()));
            form.setValue(dto.getValue());
        }
        return form;
    }

    private ConverterCitizenshipId() {

    }
}
