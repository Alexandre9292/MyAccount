package com.bnpp.dco.presentation.form;

import java.util.Map;

public class BankAccountManagementBean {
    private Map<Integer, BankRoleManagementUser> map;

    public BankAccountManagementBean() {
    }

    public Map<Integer, BankRoleManagementUser> getMap() {
        return this.map;
    }

    public void setMap(final Map<Integer, BankRoleManagementUser> map) {
        this.map = map;
    }
}
