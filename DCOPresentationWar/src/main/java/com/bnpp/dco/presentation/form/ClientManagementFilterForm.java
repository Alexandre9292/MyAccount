package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.dto.UserTableDto;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Component(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM)
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ClientManagementFilterForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -450795634303212662L;
	
	private String lastName = Constants.EMPTY_FIELD;
    private String firstName = Constants.EMPTY_FIELD;
    private String login = Constants.EMPTY_FIELD;
    private List<String> entitiesLabelList;
    private String entity;

    private List<UserTableDto> userDtoList;

    private UserDto userToModify;

    public void reset() {
        setLastName(Constants.EMPTY_FIELD);
        setFirstName(Constants.EMPTY_FIELD);
        setLogin(Constants.EMPTY_FIELD);
        setEntity(Constants.EMPTY_FIELD);
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public List<UserTableDto> getUserDtoList() {
        return this.userDtoList;
    }

    public void setUserDtoList(final List<UserTableDto> userDtoList) {
        this.userDtoList = userDtoList;
    }

    public List<String> getEntitiesLabelList() {
        return this.entitiesLabelList;
    }

    public void setEntitiesLabelList(final List<String> entitiesLabelList) {
        this.entitiesLabelList = entitiesLabelList;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(final String entity) {
        this.entity = entity;
    }

    public UserDto getUserToModify() {
        return this.userToModify;
    }

    public void setUserToModify(final UserDto userToModify) {
        this.userToModify = userToModify;
    }

}
