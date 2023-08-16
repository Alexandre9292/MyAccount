package com.bnpp.dco.presentation.form.newsite;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("newLoginForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NewLoginForm implements Serializable {

	/** Serial UID */
	private static final long serialVersionUID = 5994813764487643258L;

	/** Logger (named on purpose after the form). */
    private static final Logger LOG = LoggerFactory.getLogger(NewLoginForm.class);
    
    /** login. */
    private String userMail;

    /** password. */
    private String password;
    private String passwordConfirmation;
    
    /** other */
    private boolean rememberMe;
    
	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}
	
	/**
	 * @param username the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the passwordConfirmation
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	
	/**
	 * @param passwordConfirmation the passwordConfirmation to set
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * @return the rememberMe
	 */
	public boolean isRememberMe() {
		return rememberMe;
	}

	/**
	 * @param rememberMe the rememberMe to set
	 */
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
