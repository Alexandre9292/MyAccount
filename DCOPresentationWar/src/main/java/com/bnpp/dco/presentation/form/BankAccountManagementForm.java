package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.dto.UserDto;

@Component("bankAccountManagementForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BankAccountManagementForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 1908187772294689496L;
	
	// We cannot post in a map directly in the form (because of Spring proxying) => object
    private BankAccountManagementBean bean = new BankAccountManagementBean();
    private List<UserDto> usersList;

    public BankAccountManagementForm() {
    }

    public BankAccountManagementBean getBean() {
        return this.bean;
    }

    public void setBean(final BankAccountManagementBean bean) {
        this.bean = bean;
    }

    public List<UserDto> getUsersList() {
        return this.usersList;
    }

    public void setUsersList(final List<UserDto> usersList) {
        this.usersList = usersList;
    }
}
