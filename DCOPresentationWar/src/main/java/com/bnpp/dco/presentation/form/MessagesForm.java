package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Bean holding the different messages to display on screen.
 */
@Component("messagesForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessagesForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -3862947773184611035L;
	
	/** Field errors. */
    private List<String> errors;
    /** Field warnings. */
    private List<String> warnings;
    /** Field confirms. */
    private List<String> confirms;

    /**
     * Getter for errors.
     * @return the errors
     */
    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Setter for errors.
     * @param errors the errors to set
     */
    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    /**
     * Getter for warnings.
     * @return the warnings
     */
    public List<String> getWarnings() {
        return this.warnings;
    }

    /**
     * Setter for warnings.
     * @param warnings the warnings to set
     */
    public void setWarnings(final List<String> warnings) {
        this.warnings = warnings;
    }

    /**
     * Getter for confirms.
     * @return the confirms
     */
    public List<String> getConfirms() {
        return this.confirms;
    }

    /**
     * Setter for confirms.
     * @param confirms the confirms to set
     */
    public void setConfirms(final List<String> confirms) {
        this.confirms = confirms;
    }
}
