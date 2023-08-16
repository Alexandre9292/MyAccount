package com.bnpp.dco.presentation.controller;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.dto.UserTableDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.form.ClientManagementFilterForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class ClientManagementController extends GenericController {

    /**
     * Logger
     */
    private final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private ClientManagementFilterForm clientManagementFilterForm;

    @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM)
    public ClientManagementFilterForm getClientManagementFilterForm() {
        return this.clientManagementFilterForm;
    }

    /**
     * Main Client user Management page.
     * @return all the user without filtering
     */
    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_LOAD, method = RequestMethod.GET)
    public final String getClientAccountManagementLoad(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {
        form.reset();

        try {
            form.setEntitiesLabelList((List<String>) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                    Constants.CONTROLLER_ENTITIES_LABEL_LIST, new Object[] {}));

            form.setUserDtoList((List<UserTableDto>) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_CLIENT_LIST, new Object[] {form.getLastName(), form.getFirstName(),
                            form.getLogin(), form.getEntity() != null ? form.getEntity() : Constants.EMPTY_FIELD}));

        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

        return WebConstants.CLIENT_MANAGEMENT;
    }

    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_FILTER_FORM_CONTROLLER, method = RequestMethod.POST)
    public final String clientManagementFilter(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {
        try {
            form.setUserDtoList((List<UserTableDto>) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_CLIENT_LIST, new Object[] {form.getLastName(), form.getFirstName(),
                            form.getLogin(), form.getEntity()}));
        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

        return WebConstants.CLIENT_MANAGEMENT;
    }

    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_MODIFY_LOAD, method = RequestMethod.GET)
    public final String modifyClientUserLoad(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form,
            final Integer id) {
        final Iterator<UserTableDto> it = form.getUserDtoList().iterator();

        UserTableDto userToIter;
        while (it.hasNext()) {
        	userToIter = it.next();
            if (userToIter.getId().compareTo(id) == 0) {
            	try {
	                form.setUserToModify((UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
	                		Constants.CONTROLLER_USER_GET_BY_LOGIN, new Object[] {userToIter.getLogin()}));
            	} catch (final DCOException e) {
                    this.LOG.error(e.getMessage());
                    addError(this.propertiesHelper.getMessage("page.unload.model"));
                }
            	break;
            }
        }

        return WebConstants.CLIENT_MANAGEMENT_MODIFY;

    }

    /**
     * Allow to lock an client user
     * @param form
     * @return management client user page
     */
    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_LOCK_CONTROLLER, method = RequestMethod.GET)
    public final String lockClientUser(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {
        form.getUserToModify().setLocked(true);
        try {
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_SAVE_DATAS_USER,
                    new Object[] {form.getUserToModify(), Constants.EMPTY_FIELD});
            addWarning(this.propertiesHelper.getMessage("page.account.client.lock", new Object[] {form
                    .getUserToModify().getLastName()}));
        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.account.update.user"));
        }

        return WebConstants.CLIENT_MANAGEMENT;
    }

    /**
     * Allow to unlock an client user
     * @param form
     * @return management client user page
     */
    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_UNLOCK_CONTROLLER, method = RequestMethod.GET)
    public final String unlockClientUserLoad(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {
        form.getUserToModify().setLocked(false);
        try {
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_SAVE_DATAS_USER,
                    new Object[] {form.getUserToModify(), Constants.EMPTY_FIELD});
            addWarning(this.propertiesHelper.getMessage("page.account.client.unlock", new Object[] {form
                    .getUserToModify().getLastName()}));
        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.account.update.user"));
        }

        return WebConstants.CLIENT_MANAGEMENT;
    }

    /**
     * Allow to modify a user'Client in the client management
     * @param form
     * @return
     */
    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_MODIFY_CONTROLLER, method = RequestMethod.POST)
    public final String doModifyClientUser(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {

        String ret = WebConstants.CLIENT_MANAGEMENT;
        boolean error = false;
        // Check required fields
        if (StringUtils.isBlank(form.getUserToModify().getEmail())
                || StringUtils.isBlank(form.getUserToModify().getTel())) {
            error = true;
            addError(this.propertiesHelper.getMessage("page.account.modify.bank.user.empty.field.error"));
        }
        // Check pattern tel/email
        if (!error) {
            if (!Pattern.compile(Constants.PATTERN_TEL).matcher(form.getUserToModify().getTel()).matches()) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.pattern.tel.error"));
            }
            if (!Pattern.compile(Constants.PATTERN_EMAIL).matcher(form.getUserToModify().getEmail()).matches()) {
                error = true;
                addError(this.propertiesHelper.getMessage("page.pattern.email.error"));
            }
        }
        if (!error) {
            try {
                if (form.getUserToModify().getEntities() != null) {
                    if (form.getUserToModify().getEntities().size() > Constants.VAR_ZERO) {
                        BusinessHelper.call(Constants.CONTROLLER_ENTITIES, Constants.CONTROLLER_ENTITIES_UPDATE_FROM_CLIENT_MANAGEMENT,
                                new Object[] {form.getUserToModify().getEntities().get(Constants.VAR_ZERO)});
                    }
                }

                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_SAVE_DATAS_USER,
                        new Object[] {form.getUserToModify(), Constants.EMPTY_FIELD});

                addWarning(this.propertiesHelper.getMessage("page.account.modify.user.success", new Object[] {form
                        .getUserToModify().getLogin()}));

            } catch (final DCOException e) {
                this.LOG.error(e.getMessage());
                addError(this.propertiesHelper.getMessage("page.account.update.user.error"));
            }
        }
        if (error) {
            ret = WebConstants.CLIENT_MANAGEMENT_MODIFY;
        }
        return ret;

    }

    /**
     * Allow to remove a client user in the management user.
     * @param form
     * @return
     */
    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_REMOVE_CONTROLLER, method = RequestMethod.GET)
    public final String doRemoveClientUser(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_DELETE, new Object[] {form
                    .getUserToModify().getId()});

            addWarning(this.propertiesHelper.getMessage("page.account.client.remove", new Object[] {form
                    .getUserToModify().getLastName()}));

        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.account.delete.user.error"));
        }

        try {
            form.setEntitiesLabelList((List<String>) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                    Constants.CONTROLLER_ENTITIES_LABEL_LIST, new Object[] {}));

            form.setUserDtoList((List<UserTableDto>) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_CLIENT_LIST, new Object[] {Constants.EMPTY_FIELD,
                            Constants.EMPTY_FIELD, Constants.EMPTY_FIELD, Constants.EMPTY_FIELD}));

        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

        return WebConstants.CLIENT_MANAGEMENT;
    }

    @RequestMapping(value = WebConstants.CLIENT_MANAGEMENT_RESET_PASSWORD_CONTROLLER, method = RequestMethod.GET)
    public final String doResetPasswordClientUserLoad(
            @ModelAttribute(WebConstants.CLIENT_MANAGEMENT_FILTER_FORM) final ClientManagementFilterForm form) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_INIT_PASSWORD,
                    new Object[] {form.getUserToModify().getId()});
            addWarning(this.propertiesHelper.getMessage("page.account.rest.password.success", new Object[] {form
                    .getUserToModify().getLastName()}));
        } catch (final DCOException e) {
            if (e.getCode() == Constants.EXCEPTION_CODE_USER_CREATE_PASSWORD) {
                addError(this.propertiesHelper.getMessage("page.account.password.not.created.error"));
            }
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.account.reset.user.passwor.error"));
        }

        return WebConstants.CLIENT_MANAGEMENT;
    }
}
