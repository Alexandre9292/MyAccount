package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.Citizenship;
import com.bnpp.dco.business.dto.ThirdParty;
import com.bnpp.dco.common.dto.AccountThirdPartyDto;
import com.bnpp.dco.common.dto.CitizenshipDto;
import com.bnpp.dco.common.dto.ThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;

public final class ConverterThirdParty {

    public static ThirdPartyDto convertDbToDto(final ThirdParty db, final boolean convertSubObject,
            final boolean convertCitizenship) throws DCOException {
        ThirdPartyDto result = null;
        if (db != null) {
            result = new ThirdPartyDto();
            result.setId(db.getId());
            result.setLegalEntityName(db.getLegalEntityName());
            result.setBirthDate(db.getBirthDate());
            result.setBirthPlace(db.getBirthPlace());
            result.setFax(db.getFax());
            result.setMail(db.getMail());
            result.setFirstName(db.getFirstName());
            result.setName(db.getName());
            result.setIdReference(db.getReferenceId());
            result.setTel(db.getTel());
            result.setNationality(LocaleUtil.stringToCountry(db.getNationality()));
            if (convertCitizenship && db.getCitizenships() != null) {
                final List<CitizenshipDto> citizenship = new ArrayList<CitizenshipDto>();
                for (final Citizenship citizen : db.getCitizenships()) {
                    citizenship.add(ConverterCitizenship.convertDbToDto(citizen));
                }
                result.setCitizenships(citizenship);
            }
            result.setHomeAddress(ConverterAddress.convertDbToDto(db.getAddressByIdHomeAdress()));
            result.setPositionName(db.getPositionName());
            result.setCorrespondantType(db.getCorrespondantType());
            result.setCorrespondantTypeTwo(db.getCorrespondantTypeTwo());
            result.setCorrespondantTypeThree(db.getCorrespondantTypeThree());

            if (convertSubObject) {
                result.setUser(ConverterUser.convertDbToDto(db.getUser(), true));
                if (db.getAccountThirdParties() != null) {
                    final List<AccountThirdPartyDto> accountThirdPartyDto = new ArrayList<AccountThirdPartyDto>();
                    for (final AccountThirdParty e : db.getAccountThirdParties()) {
                        accountThirdPartyDto.add(ConverterAccountThirdParty.convertDbToDto(e));
                    }
                    result.setAccountThirdPartyList(accountThirdPartyDto);
                }
            }
        }
        return result;
    }

    public static ThirdParty convertDtoToDb(final ThirdPartyDto dto, final boolean convertSubObject,
            final boolean convertCitizenship) throws DCOException {
        ThirdParty thirdParty = null;
        if (dto != null) {
            thirdParty = new ThirdParty();
            thirdParty.setId(dto.getId());
            thirdParty.setLegalEntityName(dto.getLegalEntityName());
            thirdParty.setBirthDate(dto.getBirthDate());
            thirdParty.setBirthPlace(dto.getBirthPlace());
            thirdParty.setFax(dto.getFax());
            thirdParty.setMail(dto.getMail());
            thirdParty.setFirstName(dto.getFirstName());
            thirdParty.setName(dto.getName());
            thirdParty.setReferenceId(dto.getIdReference());
            thirdParty.setTel(dto.getTel());
            thirdParty.setNationality(LocaleUtil.countryToString(dto.getNationality()));
            if (convertCitizenship && dto.getCitizenships() != null) {
                final Set<Citizenship> citizenship = new HashSet<Citizenship>();
                for (final CitizenshipDto citizen : dto.getCitizenships()) {
                    citizenship.add(ConverterCitizenship.convertDtoToDb(citizen));
                }
                thirdParty.setCitizenships(citizenship);
            }
            thirdParty.setAddressByIdHomeAdress(ConverterAddress.convertDtoToDb(dto.getHomeAddress()));
            thirdParty.setPositionName(dto.getPositionName());
            thirdParty.setCorrespondantType(dto.getCorrespondantType());
            thirdParty.setCorrespondantTypeTwo(dto.getCorrespondantTypeTwo());
            thirdParty.setCorrespondantTypeThree(dto.getCorrespondantTypeThree());
            if (convertSubObject) {
                thirdParty.setUser(ConverterUser.convertDtoToDb(dto.getUser(), true));
                if (dto.getAccountThirdPartyList() != null) {
                    final Set<AccountThirdParty> accountThirdParty = new HashSet<AccountThirdParty>();
                    for (final AccountThirdPartyDto e : dto.getAccountThirdPartyList()) {
                        accountThirdParty.add(ConverterAccountThirdParty.convertDtoToDb(e));
                    }
                    thirdParty.setAccountThirdParties(accountThirdParty);
                }
            }
        }

        return thirdParty;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterThirdParty() {
    }

}
