package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Role;
import com.bnpp.dco.business.dto.converter.ConverterRole;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.RoleDto;

public class RoleController extends GenericController {

    public List<RoleDto> list() {
        final Query q = getEntityManager().createQuery(PropertiesHelper.getMessage(DatabaseConstants.ROLE_LIST));
        final List<?> result = q.getResultList();
        final List<RoleDto> listRoleDto = new ArrayList<RoleDto>();
        for (final Object o : result) {
            if (o instanceof Role) {
                listRoleDto.add(ConverterRole.convertDbToDto((Role) o));
            }
        }
        return listRoleDto;
    }
}
