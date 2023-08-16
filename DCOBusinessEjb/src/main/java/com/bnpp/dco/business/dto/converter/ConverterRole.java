package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Role;
import com.bnpp.dco.common.dto.RoleDto;

public final class ConverterRole {
    public static RoleDto convertDbToDto(final Role db) {
        RoleDto result = null;
        if (db != null) {
            result = new RoleDto();
            result.setId(db.getId());
            result.setLabel(db.getLabel());
        }
        return result;
    }

    public static Role convertDtoToDb(final RoleDto dto) {
        Role role = null;
        if (dto != null) {
            role = new Role();
            if (dto.getId() != null) {
                role.setId(dto.getId());
            }
            if (dto.getLabel() != null) {
                role.setLabel(dto.getLabel());
            }
        }
        return role;

    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterRole() {
    }

}
