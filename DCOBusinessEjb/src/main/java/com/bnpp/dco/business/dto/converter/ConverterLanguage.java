package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Country;
import com.bnpp.dco.business.dto.Language;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;

public final class ConverterLanguage {
    public static LanguageDto convertDbToDto(final Language db, final boolean convertSubObject)
            throws DCOException {
        LanguageDto result = null;
        if (db != null) {
            result = new LanguageDto();
            result.setId(db.getId());
            result.setLocale(LocaleUtil.stringToLanguage(db.getLocale()));
            result.setUserInterface(db.isUserInterface());
            result.setCommercialMessageLogin(db.getCommercialMessageLogin());
            result.setCommercialMessageClient(db.getCommercialMessageClient());
            if (convertSubObject && db.getCountries() != null && !db.getCountries().isEmpty()) {
                final List<CountryDto> countries = new ArrayList<CountryDto>();
                result.setCountries(countries);
                for (final Country c : db.getCountries()) {
                    countries.add(ConverterCountry.convertDbToDto(c, false));
                }
            }
        }
        return result;
    }

    public static Language convertDtoToDb(final LanguageDto dto, final boolean convertSubObject)
            throws DCOException {
        Language result = null;
        if (dto != null) {
            result = new Language();
            result.setId(dto.getId());
            result.setLocale(LocaleUtil.languageToString(dto.getLocale()));
            result.setUserInterface(dto.isUserInterface());
            result.setCommercialMessageLogin(dto.getCommercialMessageLogin());
            result.setCommercialMessageClient(dto.getCommercialMessageClient());
            if (convertSubObject && dto.getCountries() != null && !dto.getCountries().isEmpty()) {
                final Set<Country> countries = new HashSet<Country>();
                result.setCountries(countries);
                for (final CountryDto c : dto.getCountries()) {
                    countries.add(ConverterCountry.convertDtoToDb(c, false));
                }
            }
        }
        return result;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterLanguage() {
    }

}
