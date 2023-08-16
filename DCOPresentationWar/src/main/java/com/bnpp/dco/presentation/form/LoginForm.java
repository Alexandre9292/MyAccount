package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.presentation.utils.LabelDto;

/**
 * Class to validate the login form page.
 */

@Component("loginForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -3058248507073209758L;

	/** login. */
    private String username;

    /** password. */
    private String password;

    private String commercialMessage;

    private List<LabelDto> countries;

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param newUsername the username to set
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param newPassword the password to set
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    public List<LabelDto> getCountries() {
        return this.countries;
    }

    public void setCountries(final List<LabelDto> countries) {
        this.countries = countries;
    }

    public String getCommercialMessage() {
        return this.commercialMessage;
    }

    public void setCommercialMessage(final String commercialMessage) {
        this.commercialMessage = commercialMessage;
    }

}
