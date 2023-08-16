package com.bnpp.dco.presentation.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.DateFormatDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.PreferencesDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.PasswordUtil;
import com.bnpp.dco.presentation.bean.UserSession;
import com.bnpp.dco.presentation.form.PreferencesForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class PreferencesController extends GenericController {

    private final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private SessionLocaleResolver localeResolver;

    @Autowired
    private PreferencesForm preferencesForm;

    @ModelAttribute("preferencesForm")
    public PreferencesForm getPreferencesForm() {
        return this.preferencesForm;
    }

    /**
     * Constants to check the password tip by the user in the preferences.
     */
    private static final String REGEXALPHA = "^(.)*[a-z](.)*$";
    private static final String REGEXALPHAUP = "^(.)*[A-Z](.)*$";
    private static final String REGEXNUM = "^(.)*[0-9](.)*$";

    private static final int PASSWORD_MIN_LENGTH = 8;

    /**
     * Method to put form in the session
     * @throws InterruptedException
     */
    @RequestMapping(value = "preferencesLoad", method = RequestMethod.GET)
    public final String getPreferences() throws InterruptedException {
        if (!UserHelper.getUserInSession().isPasswordUpToDate()) {
            addError(this.propertiesHelper.getMessage("page.preferences.uptodate.password"));
        }

        try {
            addDropDownFieldsIntoModel();
            loadModel();

            final UserDto user = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_GET_BY_LOGIN,
                    new Object[] {UserHelper.getUserInSession().getLogin()});
            
            getPreferencesForm().setUserDto(user);

            if (user.getPreferences() == null) {
                // First connexion
                addWarning(this.propertiesHelper.getMessage("page.preferences.user.first.connexion"));
                // CNIL message
                addWarning(this.propertiesHelper.getMessage("page.preferences.user.first.connexion.cnil"));
            }

        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());

            if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES) {
                addError(this.propertiesHelper.getMessage("page.preferences.user.no.entity"));
            }
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }
        return WebConstants.PREFERENCES;
    }

    /**
     * Record User's preferences
     * @param form
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "savePreferences", method = RequestMethod.POST)
    public final String savePreference(@ModelAttribute("preferencesForm") final PreferencesForm form,
            final BindingResult br, final HttpServletRequest request, final HttpServletResponse response) {
        String result = "redirect:" + WebConstants.HOME_LOAD;
        boolean error = false;
        try {
            // Case when the user doesn't have preference yet.
            if (form.getUserDto().getPreferences() == null) {
                form.getUserDto().setPreferences(new PreferencesDto());
            }

            // Date format management
            final Iterator<DateFormatDto> itDF = form.getIhmDateFormatList().iterator();
            DateFormatDto dateFormat = null;
            while (itDF.hasNext()) {
                final DateFormatDto df = itDF.next();
                if (df.getId().compareTo(form.getIhmDateFormat()) == 0) {
                    dateFormat = df;
                    break;
                }
            }
            if (dateFormat == null) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.web.message.dateFormat"));
            } else {
                form.getUserDto().getPreferences().setDateFormat(dateFormat);
            }

            // Languages management
            final Iterator<LanguageDto> itL = form.getIhmLanguageList().iterator();
            LanguageDto language = null;
            while (itL.hasNext()) {
                final LanguageDto l = itL.next();
                if (l.getId().compareTo(form.getIhmLanguage()) == 0) {
                    language = l;
                    break;
                }
            }
            if (language == null) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.web.message.language"));
            } else {
                form.getUserDto().getPreferences().setLanguage(language);
            }

            // Password management
            if (StringUtils.isBlank(form.getPassword()) && form.getUserDto().getPassword2() == null) {
                // First connection: user has to change password
                error = true;
                addWarning(this.propertiesHelper.getMessage("page.preferences.first.connection.change.password"));
            }

            String newPasswordEncoded = null;
            if (StringUtils.isNotBlank(form.getNewPassword())) {
                newPasswordEncoded = PasswordUtil.encode(form.getNewPassword());
            } else {
                newPasswordEncoded = WebConstants.EMPTY_STRING;
            }
            if (StringUtils.isNotBlank(form.getPassword()) || StringUtils.isNotBlank(form.getNewPassword())
                    || StringUtils.isNotBlank(form.getNewPasswordConfirm())) {
                String passwordEncoded = null;
                if (StringUtils.isNotBlank(form.getPassword())) {
                    passwordEncoded = PasswordUtil.encode(form.getPassword());
                } else {
                    passwordEncoded = WebConstants.EMPTY_STRING;
                }
                if (!form.getUserDto().getPassword().equals(passwordEncoded)) {
                    // check oldPassword == password
                    error = true;
                    addWarning(this.propertiesHelper.getMessage("page.preferences.invalid.old.password"));
                } else if (StringUtils.isBlank(form.getNewPassword()) || !checkRegexPassword(form)) {
                    // check newPassword is valid
                    error = true;
                    addWarning(this.propertiesHelper.getMessage("page.preferences.invalid.password"));
                } else if (!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
                    // check newPassword == newPasswordConfirm
                    error = true;
                    addWarning(this.propertiesHelper.getMessage("page.preferences.web.message.matchPassword"));
                } else if (newPasswordEncoded.equals(form.getUserDto().getPassword())
                        || newPasswordEncoded.equals(form.getUserDto().getPassword2())
                        || newPasswordEncoded.equals(form.getUserDto().getPassword3())) {
                    error = true;
                    addWarning(this.propertiesHelper.getMessage("page.preference.duplicate.password"));
                    // check newPassword != 3 previous passwords
                } else if (form.getIhmDateFormat() == -1 || form.getIhmLanguage() == -1) {
                    // check language and date format
                    error = true;
                } else if (br.hasErrors()) {
                    error = true;
                    addError(this.propertiesHelper.getMessage("page.preferences.fetch.preferences.error"));
                }
            }

            if (!error) {
                final List<Boolean> errors = new ArrayList<Boolean>();
                if (form.getUserDto().getEntities() != null && !form.getUserDto().getEntities().isEmpty()) {
                    errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, form.getUserDto().getEntities()
                            .get(0).getBankContact()));
                    errors.add(this.validateFieldPattern(Constants.PATTERN_INPUTS, form.getUserDto().getEntities()
                            .get(0).getLabel()));
                }
                if (errors.contains(Boolean.TRUE)) {
                    addError(this.propertiesHelper.getMessage("page.form.invalidSpecialCharacters"));
                    error = true;
                }
            }

            if (!error && StringUtils.isBlank(form.getUserDto().getEmail())
                    || StringUtils.isBlank(form.getUserDto().getFirstName())
                    || StringUtils.isBlank(form.getUserDto().getLastName())) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
            }
            if (!error && form.getUserDto().getEntities() != null && !form.getUserDto().getEntities().isEmpty()
                    && form.getUserDto().getEntities().get(0) != null
                    && StringUtils.isBlank(form.getUserDto().getEntities().get(0).getLabel())) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
            }
            if (!error
                    && !Pattern.compile(Constants.PATTERN_NAME).matcher(form.getUserDto().getFirstName())
                            .matches()) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.invalid.firstname"));
            }
            if (!error
                    && !Pattern.compile(Constants.PATTERN_NAME).matcher(form.getUserDto().getLastName()).matches()) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.preferences.invalid.lastname"));
            }
            if (!error
                    && !Pattern.compile(Constants.PATTERN_EMAIL).matcher(form.getUserDto().getEmail()).matches()) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.pattern.email.error"));
            }
            
            if (!error && StringUtils.isNotBlank(form.getUserDto().getTel())){
            	if (!error && (form.getUserDto().getTel().charAt(0)<'0' || form.getUserDto().getTel().charAt(0)>'9')) {
                	if(form.getUserDto().getTel().charAt(0) != '+') {
                		error = true;
                		addError(this.propertiesHelper.getMessage("page.pattern.tel.error2"));
                	}
                }
            	if (!error) {
            		for(int i=1; i<form.getUserDto().getTel().length(); i++){
                		if (form.getUserDto().getTel().charAt(i)<'0' || form.getUserDto().getTel().charAt(i)>'9') {
                        	error = true;
                        	addError(this.propertiesHelper.getMessage("page.pattern.tel.error2"));
                        	break;
                		}
                	}
                }
            	if (!error && !Pattern.compile(Constants.PATTERN_TEL).matcher(form.getUserDto().getTel()).matches()){
                	error = true;
                	addError(this.propertiesHelper.getMessage("page.pattern.tel.error"));
                }
            }
            
            if (error) {
                result = WebConstants.PREFERENCES;
            } else {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_SAVE_DATAS_USER,
                        new Object[] {form.getUserDto(), newPasswordEncoded});
                // Relogging the updated user
                final UserDto user = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
                        Constants.CONTROLLER_USER_GET_BY_LOGIN, new Object[] {form.getUserDto().getLogin()});
                UserHelper.putUserInSession(new UserSession(user));
                form.setUserDto(user);
                loadModel();
                UserHelper.changeLocale(request, response, this.localeResolver);
                addConfirm(this.propertiesHelper
                        .getMessage("page.preferences.web.message.successSavedPreferences"));
            }
        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            result = WebConstants.PREFERENCES;
            if (e.getCode() == Constants.EXCEPTION_RECORDING_DATA) {
                addError(this.propertiesHelper.getMessage("recording.data.error"));
            } else if (e.getCode() == Constants.EXCEPTION_ROLLBACK) {
                addError(this.propertiesHelper.getMessage("recording.data.error"));
                addError(this.propertiesHelper.getMessage("canceling.record.data.error"));
            } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES) {
                addError(this.propertiesHelper.getMessage("page.preferences.user.no.entity"));
            } else {
                addError(this.propertiesHelper.getMessage("page.preferences.record.preferences.error"));
            }
        }
        return result;
    }
    
    
    /**
     * User want to leave the application, set deleted boolean to true and update.
     * @param request http request
     * @param response http response
     * @return preferences view
     */
//    @RequestMapping(value = "deleteUserData", method = RequestMethod.GET)
//    public final String deleteUserData(final HttpServletRequest request, final HttpServletResponse response) {
//        try {
//            UserDto userToUpdate = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
//                    Constants.CONTROLLER_USER_GET_BY_LOGIN,
//                    new Object[] {UserHelper.getUserInSession().getLogin()});
//            userToUpdate.setDeleted(Boolean.TRUE);
//        	BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_UPDATE,
//                        new Object[] {userToUpdate});
//                // Relogging the updated user
//                final UserDto user = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
//                        Constants.CONTROLLER_USER_GET_BY_LOGIN, new Object[] {userToUpdate.getLogin()});
//                UserHelper.putUserInSession(new UserSession(user));
//                getPreferencesForm().setUserDto(user);
//                loadModel();
//                UserHelper.changeLocale(request, response, this.localeResolver);
//        } catch (final DCOException e) {
//            this.LOG.error(e.getMessage());
//            addError(this.propertiesHelper.getMessage("page.unload.model"));
//        }
//        return WebConstants.PREFERENCES;
//    }

    /**
     * Fetch from database and put the required drop down list field in the form
     * @param model
     * @throws DCOException
     */
    private void addDropDownFieldsIntoModel() throws DCOException {

        getPreferencesForm().setIhmLanguageList(
                (List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                        Constants.CONTROLLER_LANG_LIST_INTERFACE, null));

        getPreferencesForm().setIhmDateFormatList(
                (List<DateFormatDto>) BusinessHelper.call(Constants.CONTROLLER_DATE_FORMAT,
                        Constants.CONTROLLER_LIST, null));

    }

    /**
     * Fill the form with user attributes and entities's user attributes
     * @param model
     * @throws DCOException
     */
    private void loadModel() {

        final UserSession userSession = UserHelper.getUserInSession();

        if (userSession.getPreferences() != null) {
            this.preferencesForm.setIhmDateFormat(userSession.getPreferences().getDateFormat().getId());
            this.preferencesForm.setIhmLanguage(userSession.getPreferences().getLanguageId());
        } else {
            this.preferencesForm.setIhmDateFormat(Constants.VAR_NEG_ONE);
            this.preferencesForm.setIhmLanguage(Constants.VAR_NEG_ONE);
            for (final LanguageDto language : this.preferencesForm.getIhmLanguageList()) {
                if (language.getLocale().getLanguage().equalsIgnoreCase("en")) {
                    this.preferencesForm.setIhmLanguage(language.getId());
                }
            }
        }

    }

    /**
     * Check the validity of password against regexp pattern.
     * @param form as form of preferences.
     * @return a boolean value set to true if password is valid.
     */
    private boolean checkRegexPassword(final PreferencesForm form) {
        boolean ret = false;
        if (StringUtils.isNotBlank(form.getNewPassword())) {
            final String passwordToCheck = form.getNewPassword();

            final boolean hasAlpha = Pattern.compile(REGEXALPHA).matcher(passwordToCheck).matches();
            final boolean hasAlphaUp = Pattern.compile(REGEXALPHAUP).matcher(passwordToCheck).matches();
            final boolean hasNum = Pattern.compile(REGEXNUM).matcher(passwordToCheck).matches();
            final boolean hasHeightCharsMin = passwordToCheck.length() >= PASSWORD_MIN_LENGTH;
            final boolean hasSpecialChar = StringUtils.containsAny(passwordToCheck, PasswordUtil.SPECIAL_CHARS);
            ret = hasSpecialChar && hasAlpha && hasAlphaUp && hasNum && hasHeightCharsMin;

        }
        return ret;
    }

    /**
     * Validate if field respects given pattern
     * @param patternInputs the pattern to apply
     * @param field field to validate
     * @return true
     */
    private boolean validateFieldPattern(final String patternInputs, final String field) {
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(patternInputs)
                && !Pattern.compile(patternInputs).matcher(field).matches()) {
            return true;
        }
        return false;
    }
}
