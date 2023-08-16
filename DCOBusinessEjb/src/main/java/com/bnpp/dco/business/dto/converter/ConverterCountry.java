package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Country;
import com.bnpp.dco.business.dto.Language;
import com.bnpp.dco.business.dto.Representatives;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;

public final class ConverterCountry {
    public static CountryDto convertDbToDto(final Country db, final boolean convertSubObject) throws DCOException {
        CountryDto result = null;
        if (db != null) {
            result = new CountryDto();
            result.setId(db.getId());
            result.setLocale(LocaleUtil.stringToCountry(db.getLocale()));
            result.setCom_lang_enabled(db.isCom_lang_enabled());
            if (convertSubObject) {
                if (db.getLegalEntity() != null) {
                    result.setLegalEntity(ConverterLegalEntity.convertDbToDto(db.getLegalEntity()));
                }
                if (db.getLanguages() != null && !db.getLanguages().isEmpty()) {
                    final List<LanguageDto> languages = new ArrayList<LanguageDto>();
                    result.setLanguages(languages);
                    for (final Language l : db.getLanguages()) {
                        languages.add(ConverterLanguage.convertDbToDto(l, false));
                    }
                }
                if (db.getRepresentatives() != null && !db.getRepresentatives().isEmpty()) {
                    final List<RepresentativeDto> representatives = new ArrayList<RepresentativeDto>();
                    for (final Representatives r : db.getRepresentatives()) {
                    	representatives.add(ConvertRepresentative.convertDbToDto(r, false));
                    }
                    result.setRepresentatives(representatives);
                }
            }
        }
        return result;
    }

    public static Country convertDtoToDb(final CountryDto dto, final boolean convertSubObject) throws DCOException {
        Country result = null;
        if (dto != null) {
            result = new Country();
            result.setId(dto.getId());
            result.setLocale(LocaleUtil.countryToString(dto.getLocale()));
            result.setLegalEntity(ConverterLegalEntity.convertDtoToDb(dto.getLegalEntity()));
            result.setCom_lang_enabled(dto.isCom_lang_enabled());
            if (convertSubObject && dto.getLanguages() != null && !dto.getLanguages().isEmpty()) {
                final Set<Language> languages = new HashSet<Language>();
                result.setLanguages(languages);
                for (final LanguageDto l : dto.getLanguages()) {
                    languages.add(ConverterLanguage.convertDtoToDb(l, false));
                }
            }
            if (convertSubObject && dto.getRepresentatives() != null && !dto.getRepresentatives().isEmpty()) {
                final Set<Representatives> representatives = new HashSet<Representatives>();
                for (final RepresentativeDto r : dto.getRepresentatives()) {
                	representatives.add(ConvertRepresentative.convertDtoToDb(r, false));
                }
                result.setRepresentatives(representatives);
            }
        }
        return result;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterCountry() {
    }

}
