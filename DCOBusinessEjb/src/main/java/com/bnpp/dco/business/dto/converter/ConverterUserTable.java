package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.User;
import com.bnpp.dco.common.dto.UserTableDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterUserTable {
    public static UserTableDto convertDbToDto(final User db, final boolean convertSubObject) throws DCOException {
    	UserTableDto result = null;
        if (db != null) {
            result = new UserTableDto();
            result.setId(db.getId());
            result.setLogin(db.getLogin());
            result.setFirstName(db.getFirstName());
            result.setLastName(db.getLastName());
        }
        return result;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterUserTable() {
    }

}
