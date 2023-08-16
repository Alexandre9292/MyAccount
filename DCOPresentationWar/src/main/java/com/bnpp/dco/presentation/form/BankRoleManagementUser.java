package com.bnpp.dco.presentation.form;

import java.util.Arrays;
import java.util.ListIterator;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.RoleDto;
import com.bnpp.dco.common.dto.UserDto;

public class BankRoleManagementUser {

    private Boolean hasChangeColumn;
    private Boolean roleSA;
    private Boolean roleMgmtAccnt;
    private Boolean roleMgmtDoc;
    private Boolean roleViewCltData;
    private Boolean roleViewStat;
    private Boolean roleMgmtParam;

    private int[] legalEntities;

    private UserDto userDto;

    public BankRoleManagementUser() {
    }

    public void switchFromRoleToBoolean() {
        final ListIterator<RoleDto> iterator = this.userDto.getRoles().listIterator();

        while (iterator.hasNext()) {

            final Integer toto = iterator.next().getId();

            switch (toto) {
            case Constants.ROLE_SUPER_ADMIN_ID:
                this.roleSA = true;
                break;
            case Constants.ROLE_MGMT_ACCOUNT_ID:
                this.roleMgmtAccnt = true;
                break;
            case Constants.ROLE_MGMT_DOCUMENTS_ID:
                this.roleMgmtDoc = true;
                break;
            case Constants.ROLE_VIEW_CLIENT_DATA_ID:
                this.roleViewCltData = true;
                break;
            case Constants.ROLE_VIEW_STATISTICS_ID:
                this.roleViewStat = true;
                break;
            case Constants.ROLE_MGMT_PARAMETERS_ID:
                this.roleMgmtParam = true;
                break;
            default:
                break;
            }
        }
    }

    public Boolean getRoleSA() {
        return this.roleSA;
    }

    public void setRoleSA(final Boolean roleSA) {
        this.roleSA = roleSA;
    }

    public Boolean getRoleMgmtAccnt() {
        return this.roleMgmtAccnt;
    }

    public void setRoleMgmtAccnt(final Boolean roleMgmtAccnt) {
        this.roleMgmtAccnt = roleMgmtAccnt;
    }

    public Boolean getRoleMgmtDoc() {
        return this.roleMgmtDoc;
    }

    public void setRoleMgmtDoc(final Boolean roleMgmtDoc) {
        this.roleMgmtDoc = roleMgmtDoc;
    }

    public Boolean getRoleViewCltData() {
        return this.roleViewCltData;
    }

    public void setRoleViewCltData(final Boolean roleViewCltData) {
        this.roleViewCltData = roleViewCltData;
    }

    public Boolean getRoleViewStat() {
        return this.roleViewStat;
    }

    public void setRoleViewStat(final Boolean roleViewStat) {
        this.roleViewStat = roleViewStat;
    }

    public Boolean getRoleMgmtParam() {
        return this.roleMgmtParam;
    }

    public void setRoleMgmtParam(final Boolean roleMgmtParam) {
        this.roleMgmtParam = roleMgmtParam;
    }

    public Boolean getHasChangeColumn() {
        return this.hasChangeColumn;
    }

    public void setHasChangeColumn(final Boolean hasChangeColumn) {
        this.hasChangeColumn = hasChangeColumn;
    }

    public UserDto getUserDto() {
        return this.userDto;
    }

    public void setUserDto(final UserDto userDto) {
        this.userDto = userDto;
    }

    public int[] getLegalEntities() {
        return this.legalEntities;
    }

    public void setLegalEntities(final int[] legalEntArray) {
        if (legalEntArray == null) {
            this.legalEntities = new int[0];
        } else {
            this.legalEntities = Arrays.copyOf(legalEntArray, legalEntArray.length);
        }
    }

}
