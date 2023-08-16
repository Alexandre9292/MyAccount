package com.bnpp.dco.presentation.form;

import java.util.Map;

public class AccountStatementTypesBean {
    private Map<Integer, Integer[]> map;

    public AccountStatementTypesBean() {
    }

    public Map<Integer, Integer[]> getMap() {
        return this.map;
    }

    public void setMap(final Map<Integer, Integer[]> map) {
        this.map = map;
    }
}
