package com.bnpp.dco.presentation.controller.newsite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.DateFormatDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.PreferencesDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.PasswordUtil;
import com.bnpp.dco.presentation.controller.GenericController;
import com.bnpp.dco.presentation.form.newsite.NewLoginForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class NewLoginController extends GenericController {

private static final Logger LOG = LoggerFactory.getLogger(AccountDetailController.class);
	
	@Autowired
    private PropertiesHelper propertiesHelper;
	
	@Autowired
    private NewLoginForm newLoginForm;

	/**
	 * @return the loginForm
	 */
	@ModelAttribute("newLoginForm")
	public NewLoginForm getNewLoginForm() {
		return newLoginForm;
	}
	
	/**
	 * TEMPORARY
     * Chargement de la page de sign up
     * @return the page to forward to
     */
    @RequestMapping(value = "signupLoad", method = RequestMethod.GET)
    public final String signupLoad() {
        return WebConstants.NEW + WebConstants.SIGNUP ;
    }
    
    /**
     * TEMPORARY
     * Chargement de la page de d�tail pour une cr�ation
     * @return the page to forward to
     */
    @RequestMapping(value = "newLoginLoad", method = RequestMethod.GET)
    public final String newLoginLoad() {
        return WebConstants.NEW + WebConstants.LOGIN ;
    }
    
    /**
     * Chargement de la page de d�tail pour une cr�ation
     * @return the page to forward to
     */
    @RequestMapping(value = "loginLoad", method = RequestMethod.GET)
    public final String loginLoad() {
        return WebConstants.NEW + WebConstants.LOGIN ;
    }
    
    /**
     * TEMPORARY
     * Chargement de la page de d�tail pour une cr�ation
     * @return the page to forward to
     */
    @RequestMapping(value = "newForgottenPasswordLoad", method = RequestMethod.GET)
    public final String newForgottenPasswordLoad() {
        return WebConstants.NEW + WebConstants.FORGOTTEN_PASSWORD ;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Controller for success login.
     * @return the forward
     */
    @RequestMapping(value = "loginSuccess")
    public final String loginSuccess() {
        try {
            // Resetting the "locked" flag
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_RESET_LOCK,
                    new Object[] {UserHelper.getUserInSession().getLogin()});
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.login.web.message.error"));
        }
        // Returning to login page
        return WebConstants.REDIRECT_ACTION + WebConstants.DETAIL_COMPANY_LOAD;
    }

    /**
     * Controller for failed login.
     * @param request the HttpServeltRequest received
     * @return the forward
     */
    @RequestMapping(value = "loginFail")
    public final String loginFail(final HttpServletRequest request) throws NamingException {
        final AuthenticationException authExc = (AuthenticationException) request.getSession().getAttribute(
                WebAttributes.AUTHENTICATION_EXCEPTION);
        // This exception is throw if username could not be found or password is invalid
        if (authExc instanceof BadCredentialsException) {
            final String login = (String) authExc.getAuthentication().getPrincipal();
            try {
                // Incrementing the number of failed attempts if login exists
                Object level = BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_ADD_FAIL,
                        new Object[] {login});
                if(level.equals(4)){
                	addError(this.propertiesHelper.getMessage("page.login.web.message.invalid4"));
                }
                else {
                	addError(this.propertiesHelper.getMessage("page.login.web.message.invalid"));
                }
                
            } catch (final DCOException e) {
                if (e.getCode() == 1) {
                    // Exception is thrown if the user has reached the max number of failed attempts
                    LOG.info(new StringBuilder("The user \"").append(login)
                            .append("\" has tried and failed to log in so the account just got locked").toString());
                    addError(this.propertiesHelper.getMessage("page.login.web.message.locked1"));
                } else {
                    // Other (technical) error
                    addError(this.propertiesHelper.getMessage("page.login.web.message.error"));
                }
            }
        } else if (authExc instanceof LockedException) {
            final String login = (String) authExc.getAuthentication().getPrincipal();
            // The user lock level has reached the maximum
            LOG.info(new StringBuilder("The user \"").append(login)
                    .append("\" has tried to log in but the account is locked").toString());
            addError(this.propertiesHelper.getMessage("page.login.web.message.locked"));
        } else if (authExc instanceof SessionAuthenticationException) {
            // Maximum of users connected with the login is reached
            LOG.info(new StringBuilder("Someone is already logged with the login prompted").toString());
            addWarning(this.propertiesHelper.getMessage("page.login.web.message.maxAuthenticatedSessionReached"));
        }
        // Returning to login page
        return WebConstants.NEW + WebConstants.LOGIN;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Controller to create a new user account
     * @param form
     * @param result
     * @param model
     * @param response
     * @return
     * @throws IOException
     * @throws DCOException
     */
    @RequestMapping(value = "newUserAccount", method = RequestMethod.POST)
    public final String newUserAccount(@ModelAttribute("newLoginForm") final NewLoginForm form) throws IOException, DCOException {
    	
        String rslt = WebConstants.NEW + WebConstants.SIGNUP;
        boolean error = false;
        
        if (StringUtils.isBlank(form.getUserMail()) || 
        		StringUtils.isBlank(form.getPassword()) || 
        		StringUtils.isBlank(form.getPasswordConfirmation())) {
            LOG.info("At least one mandatory field is missing");
            addError(this.propertiesHelper.getMessage("page.login.create.empty.field"));
            error = true;
        }

        if (!error && !Pattern.compile(Constants.PATTERN_EMAIL).matcher(form.getUserMail()).matches()) {
            LOG.info("The email {} does not match emails pattern", form.getUserMail());
            error = true;
            addError(this.propertiesHelper.getMessage("page.pattern.email.error"));
        }
        
        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            LOG.info("Password is not the same");
            addError(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
            error = true;
        }

        if (!error) {
            try {
                final UserDto loggedUser = new UserDto();
                loggedUser.setEmail(form.getUserMail());
                loggedUser.setLogin(form.getUserMail());
                loggedUser.setPassword(form.getPassword());
                loggedUser.setFirstName("");
                loggedUser.setLastName("");
                loggedUser.setTel("");
                loggedUser.setProfile(Constants.PROFILE_CLIENT);
                
                final List<EntityDto> entities = new ArrayList<EntityDto>();
                loggedUser.setEntities(entities);
                
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_CREATE_FROM_DTO,
                        new Object[] {loggedUser});
                
                addConfirm(this.propertiesHelper.getMessage("page.login.web.message.accountCreatedMessage"));
                rslt = WebConstants.REDIRECT + WebConstants.NEW_LOGIN_LOAD;
            } catch (final DCOException e) {
                switch (e.getCode()) {
                case Constants.EXCEPTION_CODE_USER_CREATE_ALREADY_EXISTS:
                    addWarning(this.propertiesHelper.getMessage("page.login.create.account.already.exists.info"));
                    break;
                case Constants.EXCEPTION_CODE_USER_CREATE_PASSWORD:
                    addError(this.propertiesHelper.getMessage("page.login.create.account.password.not.created.error"));
                    addError(this.propertiesHelper.getMessage("page.login.create.account.error"));
                    break;
                case Constants.EXCEPTION_RECORDING_DATA:
                    addError(this.propertiesHelper.getMessage("page.login.recording.data.error"));
                    break;
                case Constants.EXCEPTION_CODE_EMAIL:
                    addError(this.propertiesHelper.getMessage("page.login.recording.data.email.error"));
                    break;
                default:
                    addError(this.propertiesHelper.getMessage("page.login.create.account.error"));
                    break;
                }
                LOG.error(e.getMessage());
                error = true;
            }
        }
        return rslt;
    }
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Controller to fetch a forgotten password.
     * @return the page to forward to
     */
    @RequestMapping(value = "resetPassword")
    public final String forgottenPassword(@RequestParam(required = false) final String email) {
        String result = null;
        if (StringUtils.isBlank(email)) {
            addError(this.propertiesHelper.getMessage("page.forgotten.password.email.mandatory"));
            result = WebConstants.NEW + WebConstants.FORGOTTEN_PASSWORD;
        } else {
            try {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_FORGOTTEN_PASSWORD,
                        new Object[] {email, email});
                addConfirm(this.propertiesHelper.getMessage("page.forgotten.password.confirm"));
                result = WebConstants.REDIRECT_ACTION + WebConstants.NEW_LOGIN_LOAD;
            } catch (final DCOException e) {
                result = WebConstants.FORGOTTEN_PASSWORD;
                if (e.getCode() == Constants.EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_NO_USER) {
                    addError(this.propertiesHelper.getMessage("page.forgotten.password.user.not.exist"));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_ID_EMAIL_NO_MATCH) {
                    addError(this.propertiesHelper.getMessage("page.forgotten.password.no.match"));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_LOCKED) {
                    addError(this.propertiesHelper.getMessage("page.forgotten.password.account.locked"));
                } else {
                    addError(this.propertiesHelper.getMessage("page.forgotten.password.error"));
                }
            }
        }
        return result;
    }
	
}
