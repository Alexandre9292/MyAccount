package com.bnpp.dco.presentation.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.bnpp.dco.presentation.form.MessagesForm;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

/**
 * Generic controller to be extended by all the presentation controllers.
 */
@Component
public class GenericController extends AbstractHandlerExceptionResolver {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(GenericController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    /**
     * The messages bean.
     */
    @Autowired
    private MessagesForm messages;

    /**
     * Getter for the messages to be automatically add to the model.
     * @return the messages bean
     */
    @ModelAttribute("messages")
    public MessagesForm getMessages() {
        return this.messages;
    }

    /**
     * Resets all the messages.
     */
    public void resetMessages() {
        this.messages.setErrors(null);
        this.messages.setWarnings(null);
        this.messages.setConfirms(null);
    }

    /**
     * Adds an error to the messages to display.
     * @param error the error to add
     */
    public void addError(final String error) {
        if (this.messages.getErrors() == null) {
            this.messages.setErrors(new ArrayList<String>());
        }
        this.messages.getErrors().add(error);
    }

    /**
     * Adds a warning to the messages to display.
     * @param warning the warning to add
     */
    public void addWarning(final String warning) {
        if (this.messages.getWarnings() == null) {
            this.messages.setWarnings(new ArrayList<String>());
        }
        this.messages.getWarnings().add(warning);
    }

    /**
     * Adds a confirm to the messages to display.
     * @param confirm the confirm to add
     */
    public void addConfirm(final String confirm) {
        if (this.messages.getConfirms() == null) {
            this.messages.setConfirms(new ArrayList<String>());
        }
        this.messages.getConfirms().add(confirm);
    }

    @Override
    public ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler, final Exception e) {
        ModelAndView result = null;
        if (e instanceof MaxUploadSizeExceededException) {
            // Document upload size exceeded
            addError(this.propertiesHelper.getMessage("page.document.multifile.size.error"));
            result = new ModelAndView("redirect:/action/returnDocumentError", null);
        } else {
            result = new ModelAndView(WebConstants.PAGE_EXCEPTION, null);
        }
        LOG.error("Resolver Exception Interception : ", e);
        return result;
    }

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(MessagesForm messages) {
		this.messages = messages;
	}
    
    
}
