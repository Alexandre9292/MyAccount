package com.bnpp.dco.presentation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.bnpp.dco.business.ejb.EjbFacadeRemote;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.PreferencesDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.bean.UserSession;
import com.bnpp.dco.presentation.bean.UserSessionPrefs;
import com.bnpp.dco.presentation.form.LoginForm;
import com.bnpp.dco.presentation.form.NewAccountForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.LabelDto;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

/**
 * Controller to allow user connection.
 * @author 643648
 */
@Controller
public class LoginController extends GenericController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private SessionLocaleResolver localeResolver;

    @Autowired
    private NewAccountForm newAccountForm;

    @Autowired
    private LoginForm loginForm;

    @ModelAttribute("newAccountForm")
    public NewAccountForm getNewAccountForm() {
        return this.newAccountForm;
    }

    @ModelAttribute("loginForm")
    public LoginForm getLoginForm() {
        return this.loginForm;
    }

    /**
     * Method to put in session the form.
     * @param model model spring
     * @return the forward
     * @throws IOException
     */
//    @RequestMapping(value = "loginLoad", method = RequestMethod.GET)
//    public final String doLoginLoad(final Model model, final HttpServletRequest request,
//            final HttpServletResponse response) throws IOException {
//        UserHelper.initLocaleFromCookie(request, this.localeResolver);
//
//        // Manually reset the form because of cglib for Strust, which enable to do it in the specific form class.
//        getNewAccountForm().setUserName("");
//        getNewAccountForm().setEmailClient("");
//        getNewAccountForm().setEntity("");
//        getNewAccountForm().setCountry(null);
//
//        try {
//            getNewAccountForm().setCountryEntitiesList(
//                    (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
//                            Constants.CONTROLLER_LIST, null), this.localeResolver.resolveLocale(request));
//
//            final LanguageDto languageDto = (LanguageDto) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
//                    Constants.CONTROLLER_LANGUAGE_GET_BY_ID,
//                    new Object[] {this.localeResolver.resolveLocale(request).getLanguage()});
//            if (languageDto != null && StringUtils.isNotBlank(languageDto.getCommercialMessageClient())) {
//                getLoginForm().setCommercialMessage(languageDto.getCommercialMessageLogin());
//            }
//
//        } catch (final DCOException e) {
//            addError(this.propertiesHelper.getMessage("page.unload.model"));
//        }
//
//        return WebConstants.LOGIN;
//    }

    /**
     * Controller for success login.
     * @return the forward
     */
//    @RequestMapping(value = "loginSuccess")
//    public final String loginSuccess() {
//        try {
//            // Resetting the "locked" flag
//            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_RESET_LOCK,
//                    new Object[] {UserHelper.getUserInSession().getLogin()});
//        } catch (final DCOException e) {
//            addError(this.propertiesHelper.getMessage("page.login.web.message.error"));
//        }
//        // Returning to login page
//        return WebConstants.REDIRECT + WebConstants.HOME_LOAD;
//    }

    /**
     * Controller for failed login.
     * @param request the HttpServeltRequest received
     * @return the forward
     */
//    @RequestMapping(value = "loginFail")
//    public final String loginFail(final HttpServletRequest request) throws NamingException {
//        final AuthenticationException authExc = (AuthenticationException) request.getSession().getAttribute(
//                WebAttributes.AUTHENTICATION_EXCEPTION);
//        // This exception is throw if username could not be found or password is invalid
//        if (authExc instanceof BadCredentialsException) {
//            final String login = (String) authExc.getAuthentication().getPrincipal();
//            try {
//                // Incrementing the number of failed attempts if login exists
//                Object level = BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_ADD_FAIL,
//                        new Object[] {login});
//                if(level.equals(4)){
//                	addError(this.propertiesHelper.getMessage("page.login.web.message.invalid4"));
//                }
//                else {
//                	addError(this.propertiesHelper.getMessage("page.login.web.message.invalid"));
//                }
//                
//            } catch (final DCOException e) {
//                if (e.getCode() == 1) {
//                    // Exception is thrown if the user has reached the max number of failed attempts
//                    LOG.info(new StringBuilder("The user \"").append(login)
//                            .append("\" has tried and failed to log in so the account just got locked").toString());
//                    addError(this.propertiesHelper.getMessage("page.login.web.message.locked1"));
//                } else {
//                    // Other (technical) error
//                    addError(this.propertiesHelper.getMessage("page.login.web.message.error"));
//                }
//            }
//        } else if (authExc instanceof LockedException) {
//            final String login = (String) authExc.getAuthentication().getPrincipal();
//            // The user lock level has reached the maximum
//            LOG.info(new StringBuilder("The user \"").append(login)
//                    .append("\" has tried to log in but the account is locked").toString());
//            addError(this.propertiesHelper.getMessage("page.login.web.message.locked"));
//        } else if (authExc instanceof SessionAuthenticationException) {
//            // Maximum of users connected with the login is reached
//            LOG.info(new StringBuilder("Someone is already logged with the login prompted").toString());
//            addWarning(this.propertiesHelper.getMessage("page.login.web.message.maxAuthenticatedSessionReached"));
//        }
//        // Returning to login page
//        return WebConstants.LOGIN;
//    }

    @RequestMapping(value = "countrySelection", method = RequestMethod.GET)
    public String countrySelection(final String country, final HttpServletResponse response) throws IOException {

        Locale locale = null;

        try {
            locale = LocaleUtil.stringToCountry(country);
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.homeLoad.locale.error"));
        }

        UserHelper.getUserInSession().setLocaleWorkingCountry(locale);
        return WebConstants.REDIRECT + WebConstants.DOCUMENTS_LOAD;
    }

    /**
     * Controller to create a new account
     * @param form
     * @param result
     * @param model
     * @param response
     * @return
     * @throws IOException
     * @throws DCOException
     */
    @RequestMapping(value = "createNewAccount", method = RequestMethod.POST)
    public final String doCreateAccount(@ModelAttribute("newAccountForm") final NewAccountForm form,
            final BindingResult result, final Model model, final HttpServletRequest request,
            final HttpServletResponse response) throws IOException, DCOException {
        String rslt = WebConstants.LOGIN;
        boolean error = false;
        if (StringUtils.isBlank(form.getEmailClient()) || StringUtils.isBlank(form.getUserName())
                || StringUtils.isBlank(form.getEntity()) || StringUtils.isBlank(form.getCountry())) {
            LOG.info("At least one mandatory field is missing");
            addError(this.propertiesHelper.getMessage("page.login.create.empty.field"));
            error = true;
        }

        if (!error
                && !(Pattern.compile(Constants.PATTERN_INPUTS).matcher(form.getUserName()).matches() && Pattern
                        .compile(Constants.PATTERN_INPUTS).matcher(form.getEntity()).matches())) {
            error = true;
            addError(this.propertiesHelper.getMessage("page.form.invalidSpecialCharacters"));
        }

        if (!error && !Pattern.compile(Constants.PATTERN_EMAIL).matcher(form.getEmailClient()).matches()) {
            LOG.info("The email {} does not match emails pattern", form.getEmailClient());
            error = true;
            addError(this.propertiesHelper.getMessage("page.pattern.email.error"));
        }

        if (!error && form.getCountrySort() == null) {
            LOG.info("form.getCountrySort() is null. Trying to retrieve list from database...");
            form.setCountryEntitiesList((List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                    Constants.CONTROLLER_LIST, null), this.localeResolver.resolveLocale(request));
            if (form.getCountrySort() == null) {
                LOG.error("Error when retrieving country list from database");
                addError(this.propertiesHelper.getMessage("page.login.create.account.Legal.entity.empty1"));
                error = true;
            }
            LOG.info("Country list successfully retrieved from database");
        } else if (!error) {
            boolean flag = false;
            for (final AjaxDto ajaxDto : form.getCountrySort()) {
                if (ajaxDto.getLabel2().equalsIgnoreCase(form.getCountry())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                addError(this.propertiesHelper.getMessage("page.login.create.account.Legal.entity.empty2"));
                error = true;
            }
        }
        form.setCountryEntitiesList(null);
        form.setCountrySort(null);
        if (!error) {
            try {
                final UserDto loggedUser = new UserDto();
                loggedUser.setEmail(form.getEmailClient());
                loggedUser.setLogin(form.getUserName());
                loggedUser.setFirstName("");
                loggedUser.setLastName("");
                loggedUser.setTel("");
                loggedUser.setProfile(Constants.PROFILE_CLIENT);
                final EntityDto ent = new EntityDto();
                ent.setLabel(form.getEntity());
                ent.setBankContact("");
                ent.setCountry(LocaleUtil.stringToCountry(form.getCountry()));
                final List<EntityDto> entities = new ArrayList<EntityDto>();
                entities.add(ent);
                loggedUser.setEntities(entities);
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_CREATE_FROM_DTO,
                        new Object[] {loggedUser});
                addConfirm(this.propertiesHelper.getMessage("page.login.web.message.accountCreatedMessage"));
                rslt = WebConstants.REDIRECT + WebConstants.LOGIN_LOAD;
            } catch (final DCOException e) {
                switch (e.getCode()) {
                case Constants.EXCEPTION_CODE_USER_CREATE_ALREADY_EXISTS:
                    addWarning(this.propertiesHelper.getMessage("page.login.create.account.already.exists.info"));
                    break;
                case Constants.EXCEPTION_CODE_USER_CREATE_PASSWORD:
                    addError(this.propertiesHelper
                            .getMessage("page.login.create.account.password.not.created.error"));
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
        if (error && (form.getCountryEntitiesList() == null || form.getCountrySort() == null)) {
            try {
                form.setCountryEntitiesList((List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                        Constants.CONTROLLER_LIST, null), null);
            } catch (final DCOException e) {
                rslt = WebConstants.REDIRECT + WebConstants.LOGIN_LOAD;
            }
        }
        return rslt;
    }

    /**
     * Controller to load the main page
     * @return
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/homeLoad", method = RequestMethod.GET)
    public final String homeLoad(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        fillUserSessionPrefs(UserHelper.getUserInSession().getLogin());

        if (!UserHelper.getUserInSession().isPasswordUpToDate()
                || UserHelper.getUserInSession().getPreferences() == null) {
            UserHelper.changeLocale(request, response, this.localeResolver);
            return WebConstants.REDIRECT_ACTION + WebConstants.PREFERENCES_LOAD;
        } else if (UserHelper.getUserInSession().getProfile() == Constants.PROFILE_BANK) {

            final List<String> roles = new ArrayList<String>();

            final Iterator<GrantedAuthority> it = UserHelper.getUserInSession().getAuthorities().iterator();
            while (it.hasNext()) {
                final GrantedAuthority ga = it.next();
                roles.add(ga.getAuthority());
            }

            UserHelper.changeLocale(request, response, this.localeResolver);

            if (roles.contains(Constants.ROLE_SUPER_ADMIN)) {
                return WebConstants.REDIRECT_ACTION + WebConstants.BANK_ACCOUNT_MANAGEMENT_LOAD;
            } else if (roles.contains(Constants.ROLE_MGMT_PARAMETERS)) {
                return WebConstants.REDIRECT_ACTION + WebConstants.PARAM;
            } else if (roles.contains(Constants.ROLE_MGMT_ACCOUNT)) {
                return WebConstants.REDIRECT_ACTION + WebConstants.CLIENT_MANAGEMENT_LOAD;
            } else if (roles.contains(Constants.ROLE_MGMT_DOCUMENTS)) {
                return WebConstants.REDIRECT_ACTION + WebConstants.DOCUMENTS_LOAD;
            } else if (roles.contains(Constants.ROLE_VIEW_CLIENT_DATA)) {
                return WebConstants.REDIRECT_ACTION + WebConstants.CLIENT_DATA_LOAD;
            } else if (roles.contains(Constants.ROLE_VIEW_STATISTICS)) {
                return WebConstants.REDIRECT_ACTION + WebConstants.STATS_LOAD;
            } else {
                addError(this.propertiesHelper.getMessage("page.login.web.message.no.roles"));
                return WebConstants.REDIRECT_ACTION + WebConstants.LOGIN_LOAD;
            }

        } else {
            // Load the country to show them in tooltips on home page.
            final Locale[] allLocales = Locale.getAvailableLocales();
            final List<Locale> localesToUse = new ArrayList<Locale>();
            final List<String> countriesToUse = new ArrayList<String>();
            final List<LabelDto> countries = new ArrayList<LabelDto>();

            for (final Locale l : allLocales) {
                if (!countriesToUse.contains(l.getCountry())) {
                    countriesToUse.add(l.getCountry());
                    localesToUse.add(l);
                }
            }
            for (final Locale locale : localesToUse) {
                final LabelDto countryLabel = new LabelDto();
                countryLabel.setLabel1(locale.getCountry());
                countryLabel.setLabel2(locale.getDisplayCountry(UserHelper.getUserInSession().getPreferences()
                        .getLocale()));
                countries.add(countryLabel);
            }

            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                    && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
                final Locale locale = new Locale(UserHelper.getUserInSession().getPreferences().getLocale()
                        .getLanguage(), "MD");
                if (locale != null) {
                    final LabelDto countryLabel = new LabelDto();
                    countryLabel.setLabel1(locale.getCountry());
                    countryLabel.setLabel2(locale.getDisplayCountry(UserHelper.getUserInSession().getPreferences()
                            .getLocale()));
                    countries.add(countryLabel);
                }
            }
            getLoginForm().setCountries(countries);

            UserHelper.changeLocale(request, response, this.localeResolver);
            return WebConstants.HOME;
        }
    }

    /**
     * Controller to load the forgotten password page
     * @return the page to forward to
     */
    @RequestMapping(value = "forgottenPasswordLoad")
    public final String forgottenPasswordLoad() {
        return WebConstants.FORGOTTEN_PASSWORD;
    }

    /**
     * Controller to fetch a forgotten password.
     * @return the page to forward to
     */
    @RequestMapping(value = "forgottenPassword")
    public final String forgottenPassword(@RequestParam(required = false) final String login,
            @RequestParam(required = false) final String email) {
        String result = null;
        if (StringUtils.isBlank(login)) {
            addError(this.propertiesHelper.getMessage("page.forgotten.password.login.mandatory"));
            result = WebConstants.FORGOTTEN_PASSWORD;
        } else if (StringUtils.isBlank(email)) {
            addError(this.propertiesHelper.getMessage("page.forgotten.password.email.mandatory"));
            result = WebConstants.FORGOTTEN_PASSWORD;
        } else {
            try {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_FORGOTTEN_PASSWORD,
                        new Object[] {login, email});
                addConfirm(this.propertiesHelper.getMessage("page.forgotten.password.confirm"));
                result = WebConstants.REDIRECT_ACTION + WebConstants.LOGIN_LOAD;
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

    /**
     * Controller to display the legal notices.
     * @return the page to forward to
     */
    @RequestMapping(value = "legalNotices", method = RequestMethod.GET)
    public final String legalNotices() {
        return WebConstants.LEGAL_NOTICES;
    }

    /**
     * Fetch the preferences associated with the user.
     * @param login
     * @return the preferences Dto
     * @throws DCOException
     */
    private void fillUserSessionPrefs(final String login) {
        PreferencesDto prefUser = null;
        try {
            prefUser = (PreferencesDto) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_GET_PREFERENCES, new Object[] {login});
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.preferences.load.preferences.error"));
        }

        final UserSession userSession = UserHelper.getUserInSession();
        if (prefUser != null && userSession != null) {

            UserSessionPrefs prefSession = userSession.getPreferences();
            if (prefSession == null) {
                prefSession = new UserSessionPrefs();
                prefSession.setId(prefUser.getId());
                userSession.setPreferences(prefSession);
            }

            prefSession.setDateFormat(prefUser.getDateFormat());
            prefSession.setLanguageId(prefUser.getLanguage().getId());
        }
    }
}