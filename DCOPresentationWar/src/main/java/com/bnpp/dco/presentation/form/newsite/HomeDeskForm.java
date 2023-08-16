package com.bnpp.dco.presentation.form.newsite;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.dto.UserDto;

@Component("homeDeskForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HomeDeskForm implements Serializable {
	/** Serial UID */
	private static final long serialVersionUID = 1028893653989533559L;
	
	/** Logger (named on purpose after the form). */
    private static final Logger LOG = LoggerFactory.getLogger(HomeDeskForm.class);
    
    /* Gestion des utilisateurs */
    private UserDto newUser;
    private List<UserDto> managedUsers;
    
	/**
	 * @return the newUser
	 */
	public UserDto getNewUser() {
		return newUser;
	}
	/**
	 * @param newUser the newUser to set
	 */
	public void setNewUser(UserDto newUser) {
		this.newUser = newUser;
	}
	/**
	 * @return the managedUsers
	 */
	public List<UserDto> getManagedUsers() {
		return managedUsers;
	}
	/**
	 * @param managedUsers the managedUsers to set
	 */
	public void setManagedUsers(List<UserDto> managedUsers) {
		this.managedUsers = managedUsers;
	}

}
