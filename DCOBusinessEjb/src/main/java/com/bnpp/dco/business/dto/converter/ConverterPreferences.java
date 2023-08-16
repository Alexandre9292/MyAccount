package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Preferences;
import com.bnpp.dco.common.dto.PreferencesDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterPreferences {
    public static PreferencesDto convertDbToDto(final Preferences db) throws DCOException {
        PreferencesDto result = null;
        if (db != null) {
            result = new PreferencesDto();
            result.setId(db.getId());
            result.setLanguage(ConverterLanguage.convertDbToDto(db.getLanguage(), false));
            result.setDateFormat(ConverterDateFormat.convertDbToDto(db.getDateFormat()));
            result.setFormatAmount(db.getFormatAmount());
        }
        return result;
    }

    public static Preferences convertDtoToDb(final PreferencesDto dto) throws DCOException {

        Preferences pref = null;

        if (dto != null) {

            pref = new Preferences();
            if (dto.getId() != null) {
                pref.setId(dto.getId());
            }
            if (dto.getFormatAmount() != null) {
                pref.setFormatAmount(dto.getFormatAmount());
            }
            if (dto.getFormatAmount() != null) {
                pref.setDateFormat(ConverterDateFormat.convertDtoToDb(dto.getDateFormat()));
            }
            if (dto.getLanguage() != null) {
                pref.setLanguage(ConverterLanguage.convertDtoToDb(dto.getLanguage(), false));
            }

        }

        return pref;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterPreferences() {
    }

}
