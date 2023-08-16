package com.bnpp.dco.presentation.form;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("manageBankUserForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ManageBankUserForm extends BankRoleManagementUser implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 5130488971495151916L;

}
