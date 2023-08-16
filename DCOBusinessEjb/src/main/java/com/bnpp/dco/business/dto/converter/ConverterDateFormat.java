package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.DateFormat;
import com.bnpp.dco.common.dto.DateFormatDto;

public final class ConverterDateFormat {
    public static DateFormatDto convertDbToDto(final DateFormat db) {
        DateFormatDto result = null;
        if (db != null) {
            result = new DateFormatDto();
            result.setId(db.getId());
            result.setLabel(db.getLabel());
            result.setLabelDisplay(db.getLabelDisplay());
            result.setLabelLong(db.getLabelLong());
        }
        return result;
    }

    public static DateFormat convertDtoToDb(final DateFormatDto dto) {

        DateFormat df = null;

        if (dto != null) {
            df = new DateFormat();
            if (dto.getId() != null) {
                df.setId(dto.getId());
            }
            if (dto.getLabel() != null) {
                df.setLabel(dto.getLabel());
            }
            if (dto.getLabelDisplay() != null) {
                df.setLabelDisplay(dto.getLabelDisplay());
            }
            if (dto.getLabelLong() != null) {
                df.setLabelLong(dto.getLabelLong());
            }
        }

        return df;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterDateFormat() {
    }

}
