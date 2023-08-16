package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Account;
import com.bnpp.dco.business.dto.AccountForm;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.Representatives;
import com.bnpp.dco.business.dto.ThirdParty;
import com.bnpp.dco.business.dto.User;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AccountFormDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.dto.ThirdPartyDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;

public final class ConverterEntity {
    public static EntityDto convertDbToDto(final Entities db, final boolean convertSubObject) throws DCOException {
        EntityDto result = null;
        if (db != null) {
            result = new EntityDto();
            result.setId(db.getId());
            result.setLabel(db.getLabel());
            result.setBankContact(db.getBankContact());
            result.setAddress(ConverterAddress.convertDbToDto(db.getAddressByAddress()));
            result.setAddressMailing(ConverterAddress.convertDbToDto(db.getAddressByIdAddressMailing()));
            result.setCountry(LocaleUtil.stringToCountry(db.getCountry()));
            result.setCommercialRegister(db.getCommercialRegister());
            result.setTaxInformation(db.getTaxInformation());
            result.setTaxResidenceCode(db.getTaxResidenceCode());
            result.setRegistrationCountry(db.getRegistrationCountry());
            result.setLegalStatus(ConverterParamFunc.convertDbToDto(db.getParamFunc()));
//            result.setThirdParty(ConverterThirdParty.convertDbToDto(db.getThirdParty(), false, true));
//            result.setThirdParty2(ConverterThirdParty.convertDbToDto(db.getThirdParty2(), false, true));
            result.setBoardResolutionDate(db.getBoardResolutionDate());
            result.setNotaryName(db.getNotaryName());
            result.setNotaryCity(db.getNotaryCity());
            result.setIssuanceDate(db.getIssuanceDate());
            result.setPublicDeedReference(db.getPublicDeedReference());
            result.setMercantileInscriptionDate(db.getMercantileInscriptionDate());
            result.setMercantileInscriptionNumber(db.getMercantileInscriptionNumber());
            result.setLegalStatusOther(db.getLegalStatusOther());

            if (convertSubObject) {
                if (db.getUsers() != null) {
                    final List<UserDto> user = new ArrayList<UserDto>();
                    for (final User e : db.getUsers()) {
                        user.add(ConverterUser.convertDbToDto(e, false));
                    }
                    result.setUsers(user);
                }
                if (db.getAccountForms() != null) {
                    final List<AccountFormDto> af = new ArrayList<AccountFormDto>();
                    for (final AccountForm e : db.getAccountForms()) {
                        af.add(ConverterAccountForm.convertDbToDto(e, false));
                    }
                    result.setAccountsForm(af);
                }
//                if (db.getThirdParties() != null) {
//                    final List<ThirdPartyDto> thirdsList = new ArrayList<ThirdPartyDto>();
//                    for (final ThirdParty third : db.getThirdParties()) {
//                        thirdsList.add(ConverterThirdParty.convertDbToDto(third, false, true));
//                    }
//                    result.setThirdParties(thirdsList);
//                }
                if (db.getAccount() != null) {
                    final List<AccountDto> af = new ArrayList<AccountDto>();
                    for (final Account e : db.getAccount()) {
                        af.add(ConverterAccount.convertDbToDto(e, false));
                    }
                    result.setAccountList(af);
                }
            }
            

            if (db.getRepresentativesList() != null) {
                final List<RepresentativeDto> representatives = new ArrayList<RepresentativeDto>();
                for (final Representatives e : db.getRepresentativesList()) {
                	representatives.add(ConvertRepresentative.convertDbToDto(e, false));
                }
                result.setRepresentativesList(representatives);
            }
            
            // new : refonte site
            result.setCountryIncorp(db.getCountryIncorp());
            result.setContact1(ConverterContact.convertDbToDto(db.getContact1(), false));
            result.setContact2(ConverterContact.convertDbToDto(db.getContact2(), false));
        }
        return result;
    }

    public static Entities convertDtoToDb(final EntityDto dto, final boolean convertSubObject) throws DCOException {

        Entities entity = null;

        if (dto != null) {

            entity = new Entities();
            if (dto.getAddress() != null) {
                entity.setAddressByAddress(ConverterAddress.convertDtoToDb(dto.getAddress()));
            }
            if (dto.getAddressMailing() != null) {
                entity.setAddressByIdAddressMailing(ConverterAddress.convertDtoToDb(dto.getAddressMailing()));
            }

            if (dto.getBankContact() != null) {
                entity.setBankContact(dto.getBankContact());
            }

            if (dto.getCountry() != null) {
                entity.setCountry(LocaleUtil.countryToString(dto.getCountry()));
            }

            if (dto.getLabel() != null) {
                entity.setLabel(dto.getLabel());
            }

            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }

            if (dto.getTaxResidenceCode() != null) {
                entity.setTaxResidenceCode(dto.getTaxResidenceCode());
            }

            entity.setCommercialRegister(dto.getCommercialRegister());
            entity.setRegistrationCountry(dto.getRegistrationCountry());
            entity.setTaxInformation(dto.getTaxInformation());
            entity.setParamFunc(ConverterParamFunc.convertDtoToDb(dto.getLegalStatus()));
//            entity.setThirdParty(ConverterThirdParty.convertDtoToDb(dto.getThirdParty(), false, true));
//            entity.setThirdParty2(ConverterThirdParty.convertDtoToDb(dto.getThirdParty2(), false, true));

            entity.setBoardResolutionDate(dto.getBoardResolutionDate());
            entity.setNotaryName(dto.getNotaryName());
            entity.setNotaryCity(dto.getNotaryCity());
            entity.setIssuanceDate(dto.getIssuanceDate());
            entity.setPublicDeedReference(dto.getPublicDeedReference());
            entity.setMercantileInscriptionDate(dto.getMercantileInscriptionDate());
            entity.setMercantileInscriptionNumber(dto.getMercantileInscriptionNumber());
            entity.setLegalStatusOther(dto.getLegalStatusOther());
            
            if (convertSubObject) {
                if (dto.getUsers() != null) {
                    final Set<User> user = new HashSet<User>();
                    for (final UserDto e : dto.getUsers()) {
                        user.add(ConverterUser.convertDtoToDb(e, false));
                    }
                    entity.setUsers(user);
                }
                if (dto.getAccountsForm() != null) {
                    final Set<AccountForm> af = new HashSet<AccountForm>();
                    for (final AccountFormDto e : dto.getAccountsForm()) {
                        af.add(ConverterAccountForm.convertDtoToDb(e));
                    }
                    entity.setAccountForms(af);
                }
//                if (dto.getThirdParties() != null) {
//                    final Set<ThirdParty> thirds = new HashSet<ThirdParty>();
//                    for (final ThirdPartyDto third : dto.getThirdParties()) {
//                        thirds.add(ConverterThirdParty.convertDtoToDb(third, false, true));
//                    }
//                    entity.setThirdParties(thirds);
//                }
                if (dto.getAccountList() != null) {
                    final Set<Account> af = new HashSet<Account>();
                    for (final AccountDto e : dto.getAccountList()) {
                        af.add(ConverterAccount.convertDtoToDb(e,false));
                    }
                    entity.setAccount(af);
                }   
            }

            if (dto.getRepresentativesList() != null) {
                final Set<Representatives> representatives = new HashSet<Representatives>();
                for (final RepresentativeDto e : dto.getRepresentativesList()) {
                	representatives.add(ConvertRepresentative.convertDtoToDb(e,false));
                }
                entity.setRepresentativesList(representatives);
            }
            
            // new : refonte site
            entity.setCountryIncorp(dto.getCountryIncorp());
            entity.setContact1(ConverterContact.convertDtoToDb(dto.getContact1(), false));
            entity.setContact2(ConverterContact.convertDtoToDb(dto.getContact2(), false));
        }

        return entity;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterEntity() {
    }

}
