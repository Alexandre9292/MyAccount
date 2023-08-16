package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.LegalEntity;
import com.bnpp.dco.business.dto.Role;
import com.bnpp.dco.business.dto.User;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.RoleDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterUser {
    public static UserDto convertDbToDto(final User db, final boolean convertSubObject) throws DCOException {
        UserDto result = null;
        if (db != null) {
            result = new UserDto();
            result.setId(db.getId());
            result.setProfile(db.getProfile());
            result.setPreferences(ConverterPreferences.convertDbToDto(db.getPreferences()));
            result.setLogin(db.getLogin());
            result.setPassword(db.getPassword());
            result.setFirstName(db.getFirstName());
            result.setLastName(db.getLastName());
            result.setEmail(db.getEmail());
            result.setTel(db.getTel());
            result.setDatePassword(db.getDatePassword());
            result.setPassword2(db.getPassword2());
            result.setPassword3(db.getPassword3());
            result.setLockLevel(db.getLockLevel());
            result.setLocked(db.isLocked());
            result.setXbasV2(db.isXbasV2());
            result.setDeleted(db.isDeleted());
            if (convertSubObject) {
                if (db.getEntitieses() != null) {
                    final List<EntityDto> entities = new ArrayList<EntityDto>();
                    for (final Entities e : db.getEntitieses()) {
                        entities.add(ConverterEntity.convertDbToDto(e, false));
                    }
                    result.setEntities(entities);
                }
                if (db.getLegalEntities() != null) {
                    final List<LegalEntityDto> les = new ArrayList<LegalEntityDto>();
                    for (final LegalEntity le : db.getLegalEntities()) {
                        les.add(ConverterLegalEntity.convertDbToDto(le));
                    }
                    result.setLegalEntities(les);
                }
            }
            if (db.getRoles() != null) {
                final List<RoleDto> roles = new ArrayList<RoleDto>();
                for (final Role r : db.getRoles()) {
                    roles.add(ConverterRole.convertDbToDto(r));
                }
                result.setRoles(roles);
            }

        }
        return result;
    }

    public static User convertDtoToDb(final UserDto dto, final boolean convertSubObject) throws DCOException {
        User user = null;

        if (dto != null) {
            user = new User();
            if (dto.getProfile() != null) {
                user.setProfile(dto.getProfile());
            }
            if (dto.getId() != null) {
                user.setId(dto.getId());
            }
            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }
            if (dto.getFirstName() != null) {
                user.setFirstName(dto.getFirstName());
            }
            if (dto.getLastName() != null) {
                user.setLastName(dto.getLastName());
            }
            if (dto.getLogin() != null) {
                user.setLogin(dto.getLogin());
            }
            if (dto.getTel() != null) {
                user.setTel(dto.getTel());
            }
            if (dto.getPassword() != null) {
                user.setPassword(dto.getPassword());
            }
            if (dto.getPassword2() != null) {
                user.setPassword2(dto.getPassword2());
            }
            if (dto.getPassword3() != null) {
                user.setPassword3(dto.getPassword3());
            }
            if (dto.getDatePassword() != null) {
                user.setDatePassword(dto.getDatePassword());
            }
            user.setLockLevel(dto.getLockLevel());
            user.setLocked(dto.isLocked());
            user.setXbasV2(dto.isXbasV2());
            user.setDeleted(dto.isDeleted());
            if (convertSubObject) {
                if (dto.getEntities() != null) {
                    final Set<Entities> entitieses = new HashSet<Entities>(0);
                    for (final EntityDto entityDto : dto.getEntities()) {
                        entitieses.add(ConverterEntity.convertDtoToDb(entityDto, false));
                    }
                    user.setEntitieses(entitieses);
                }
                if (dto.getLegalEntities() != null) {
                    final Set<LegalEntity> les = new HashSet<LegalEntity>(0);
                    for (final LegalEntityDto le : dto.getLegalEntities()) {
                        les.add(ConverterLegalEntity.convertDtoToDb(le));
                    }
                    user.setLegalEntities(les);
                }

            }

            if (dto.getRoles() != null) {
                final Set<Role> roles = new HashSet<Role>(0);
                for (final RoleDto roleDto : dto.getRoles()) {
                    roles.add(ConverterRole.convertDtoToDb(roleDto));
                }
                user.setRoles(roles);
            }

            if (dto.getPreferences() != null) {
                user.setPreferences(ConverterPreferences.convertDtoToDb(dto.getPreferences()));
            }

        }

        return user;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterUser() {
    }

}
