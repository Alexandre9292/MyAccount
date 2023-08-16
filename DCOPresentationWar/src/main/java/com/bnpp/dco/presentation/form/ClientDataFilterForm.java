package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Component(WebConstants.CLIENT_DATA_FILTER_FORM)
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ClientDataFilterForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -5791144685309328623L;
	
	private List<AjaxDto> entitiesLabelList;
    private List<AjaxDto> clients;
    private String login = Constants.EMPTY_FIELD;
    private String entity = Constants.EMPTY_FIELD;
    private EntityDto entityClient;
    private List<EntityDto> entities;
    private List<AccountDto> accountEntity;
    private List<AjaxDto> exclusionUserEntities;

    public List<AjaxDto> getEntitiesLabelList() {
        return this.entitiesLabelList;
    }

    public void setEntitiesLabelList(final List<AjaxDto> entitiesLabelList) {
        this.entitiesLabelList = entitiesLabelList;
    }

    public List<AjaxDto> getClients() {
        return this.clients;
    }

    public void setClients(final List<AjaxDto> clients) {
        this.clients = clients;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(final String entity) {
        this.entity = entity;
    }

    public EntityDto getEntityClient() {
        return this.entityClient;
    }

    public void setEntityClient(final EntityDto entityClient) {
        this.entityClient = entityClient;
    }

    public List<AccountDto> getAccountEntity() {
        return this.accountEntity;
    }

    public void setAccountEntity(final List<AccountDto> accountEntity) {
        this.accountEntity = accountEntity;
    }

    public List<AjaxDto> getExclusionUserEntities() {
        return this.exclusionUserEntities;
    }

    public void setExclusionUserEntities(final List<AjaxDto> exclusionUserEntities) {
        this.exclusionUserEntities = exclusionUserEntities;
    }

    public List<EntityDto> getEntities() {
        return this.entities;
    }

    public void setEntities(final List<EntityDto> entities) {
        this.entities = entities;
    }

}
