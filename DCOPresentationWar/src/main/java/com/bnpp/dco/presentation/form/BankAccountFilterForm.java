package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.RoleDto;

@Component("bankAccountFilterForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BankAccountFilterForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -2424952979087836214L;
	
	private String login = "";
    private int[] roles = {};
    private int legalEntity = -1;

    private List<RoleDto> rolesList;
    private List<LegalEntityDto> legalEntityList;

    public BankAccountFilterForm() {

    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public int[] getRoles() {
        return this.roles;
    }

    public void setRoles(final int[] rolesTab) {
        if (rolesTab == null) {
            this.roles = new int[0];
        } else {
            this.roles = Arrays.copyOf(rolesTab, rolesTab.length);
        }
    }

    public Integer getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(final Integer legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<RoleDto> getRolesList() {
        return this.rolesList;
    }

    public void setRolesList(final List<RoleDto> rolesList) {
        this.rolesList = rolesList;
    }

    public List<LegalEntityDto> getLegalEntityList() {
        return this.legalEntityList;
    }

    public void setLegalEntityList(final List<LegalEntityDto> legalEntityList) {
        this.legalEntityList = legalEntityList;
    }

}
