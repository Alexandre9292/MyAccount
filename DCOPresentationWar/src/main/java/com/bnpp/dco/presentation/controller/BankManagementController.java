package com.bnpp.dco.presentation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.dto.RoleDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.form.BankAccountFilterForm;
import com.bnpp.dco.presentation.form.BankAccountManagementBean;
import com.bnpp.dco.presentation.form.BankAccountManagementForm;
import com.bnpp.dco.presentation.form.BankRoleManagementUser;
import com.bnpp.dco.presentation.form.ManageBankUserForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class BankManagementController extends GenericController {

    /**
     * Logger
     */
    private final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private BankAccountManagementForm bankAccountManagementForm;

    @Autowired
    private BankAccountFilterForm bankAccountFilterForm;

    @Autowired
    private ManageBankUserForm manageBankUserForm;

    @ModelAttribute("bankAccountManagementForm")
    public BankAccountManagementForm getBankAccountManagementForm() {
        return this.bankAccountManagementForm;
    }

    @ModelAttribute("bankAccountFilterForm")
    public BankAccountFilterForm getBankAccountFilterForm() {
        return this.bankAccountFilterForm;
    }

    @ModelAttribute("manageBankUserForm")
    public ManageBankUserForm getManageBankUserForm() {
        return this.manageBankUserForm;
    }

    /**
     * Main Bank user Role Management page.
     * @return all the user without filtering
     */
    @RequestMapping(value = "/bankAccountManagementLoad", method = RequestMethod.GET)
    public final String getbankAccountManagementLoad() {
        loadFilterModel();

        getBankAccountFilterForm().setLogin("");
        getBankAccountFilterForm().setLegalEntity(Constants.VAR_NEG_ONE);
        getBankAccountFilterForm().setRoles(Constants.EMPTY_ARRAY);

        return loadBankUserModel(Constants.PROFILE_BANK, Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD,
                Constants.EMPTY_ARRAY);
    }

    /**
     * Allow to filter the user by userName / legal Entity / Role
     * @param form
     * @param result
     * @return the filtered user
     * @throws InterruptedException
     */
    @RequestMapping(value = "/bankAccountFilter", method = RequestMethod.POST)
    public final String doBankAccountFilter(
            @ModelAttribute("bankAccountFilterForm") final BankAccountFilterForm form, final BindingResult result)
            throws InterruptedException {

        return loadBankUserModel(Constants.PROFILE_BANK, form.getLegalEntity(), form.getLogin(), form.getRoles());
    }

    /**
     * ALlow to record the new selected user's roles
     * @param form
     * @param result
     * @return the new user's roles
     * @throws InterruptedException
     */
    @RequestMapping(value = "/bankAccountManagement", method = RequestMethod.POST)
    public final String doBankAccountManagement(
            @ModelAttribute("bankAccountManagementForm") final BankAccountManagementForm form,
            final BindingResult result) throws InterruptedException {

        final List<UserDto> listUsersDto = new ArrayList<UserDto>();
        boolean error = false;
        for (final Entry<Integer, BankRoleManagementUser> currentEntry : form.getBean().getMap().entrySet()) {
            if (!error) {
                final BankRoleManagementUser userBean = currentEntry.getValue();
                if (userBean.getHasChangeColumn() != null && userBean.getHasChangeColumn()) {
                    final UserDto userDto = userBean.getUserDto();
                    userDto.setRoles(utilSetUserRole(userBean));

                    if (userDto.getRoles().isEmpty()) {
                        addError(this.propertiesHelper.getMessage("page.account.save.bank.user.no.role"));
                        error = true;
                    }

                    listUsersDto.add(userDto);
                }
            }
        }

        if (!error && listUsersDto.size() > 0) {
            try {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_SAVE_ROLE,
                        new Object[] {listUsersDto});
            } catch (final DCOException e) {
                addError(this.propertiesHelper.getMessage("page.account.save.role.fail"));
            }
        }
        addConfirm(this.propertiesHelper.getMessage("page.bank.user.modify.roles.save.success"));

        return loadBankUserModel(Constants.PROFILE_BANK, this.bankAccountFilterForm.getLegalEntity(),
                this.bankAccountFilterForm.getLogin(), this.bankAccountFilterForm.getRoles());
    }

    /**
     * Load the model with the roles's user.
     * @param legalEntity
     * @param login
     * @param roles
     * @return the loaded model
     */
    private String loadBankUserModel(final Integer profile, final int legalEntity, final String login,
            final int[] rolesFilterList) {

        int[] rolesList = ArrayUtils.EMPTY_INT_ARRAY;

        if (rolesFilterList.length > 0) {
            // Allow to add the Admin role if there is a filter on the roles (admin has all the role, so we have to
            // fetch it from the base).
            rolesList = ArrayUtils.add(rolesFilterList, Constants.VAR_ONE);
        }

        try {
            this.bankAccountManagementForm.setUsersList((List<UserDto>) BusinessHelper.call(
                    Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_BANK_LIST, new Object[] {profile, login,
                            legalEntity, rolesList}));
        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

        if (this.bankAccountManagementForm.getUsersList() != null) {
            final BankAccountManagementBean bean = this.bankAccountManagementForm.getBean();
            final Map<Integer, BankRoleManagementUser> map = new HashMap<Integer, BankRoleManagementUser>();
            for (final UserDto user : this.bankAccountManagementForm.getUsersList()) {
                final BankRoleManagementUser brmu = new BankRoleManagementUser();
                brmu.setUserDto(user);
                brmu.switchFromRoleToBoolean();
                map.put(user.getId(), brmu);
            }
            bean.setMap(map);
        }

        return WebConstants.BANK_ACCOUNT_MANAGEMENT;

    }

    /**
     * Load the model with the filter's elements (role/legal entity).
     * @param legalEntity
     * @param login
     * @param roles
     * @return the loaded model
     */
    private void loadFilterModel() {

        try {

            this.bankAccountFilterForm.setRolesList((List<RoleDto>) BusinessHelper.call(Constants.CONTROLLER_ROLE,
                    Constants.CONTROLLER_LIST, new Object[] {}));

            this.bankAccountFilterForm.setLegalEntityList((List<LegalEntityDto>) BusinessHelper.call(
                    Constants.CONTROLLER_LEGAL_ENTITY, Constants.CONTROLLER_LIST,
                    new Object[] {Constants.VAR_NEG_ONE}));

        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

    }

    @RequestMapping(value = "addBankUser", method = RequestMethod.GET)
    public final String addBankUser() {
        resetManageBankUserForm();
        return WebConstants.ADD_NEW_BANK_USER;
    }

    @RequestMapping(value = "doAddBankUser", method = RequestMethod.POST)
    public final String doAddBankUser(@ModelAttribute("manageBankUserForm") final ManageBankUserForm form) {

        final String ret = WebConstants.ADD_NEW_BANK_USER;
        boolean error = false;

        form.getUserDto().setRoles(utilSetUserRole(form));

        if (form.getUserDto().getRoles().isEmpty()) {
            addError(this.propertiesHelper.getMessage("page.account.save.new.bank.user.no.role"));
            error = true;
        }

        if (StringUtils.isBlank(form.getUserDto().getEmail())
                || StringUtils.isBlank(form.getUserDto().getLastName())
                || StringUtils.isBlank(form.getUserDto().getFirstName())

        ) {
            addError(this.propertiesHelper.getMessage("page.preferences.empty.field.error"));
            error = true;
        }

        if (!error && !Pattern.compile(Constants.PATTERN_EMAIL).matcher(form.getUserDto().getEmail()).matches()) {
            error = true;
            addError(this.propertiesHelper.getMessage("page.pattern.email.error"));
        }

        if (!error) {

            form.getUserDto().setLogin(form.getUserDto().getEmail());
            form.getUserDto().setEmail(form.getUserDto().getEmail());
            form.getUserDto().setTel("");
            form.getUserDto().setProfile(Constants.PROFILE_BANK);

            if (form.getLegalEntities() != null) {
                form.getUserDto().setLegalEntities(utilSetUserLegalEntities(form));
            }

            try {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_CREATE_FROM_DTO,
                        new Object[] {form.getUserDto()});
                addConfirm(this.propertiesHelper.getMessage("page.account.add.success"));
            } catch (final DCOException e) {
                if (e.getCode() == Constants.EXCEPTION_CODE_USER_CREATE_ALREADY_EXISTS) {
                    addError(this.propertiesHelper.getMessage("page.account.bank.user.already.exists.info"));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_USER_CREATE_PASSWORD) {
                    addError(this.propertiesHelper.getMessage("page.account.password.not.created.error"));
                    addError(this.propertiesHelper.getMessage("page.account.save.new.bank.user.error"));
                } else if (e.getCode() == Constants.EXCEPTION_CODE_EMAIL) {
                    addError(this.propertiesHelper.getMessage("page.account.save.new.bank.user.error.email"));
                } else {
                    addError(this.propertiesHelper.getMessage("page.account.save.new.bank.user.error"));
                }
                error = true;
            }
        }
        if (error) {
            return ret;
        } else {
            return getbankAccountManagementLoad();
        }
    }

    @RequestMapping(value = "/modifyBankUser", method = RequestMethod.GET)
    public final String modifyBankUser(final Integer id) {
        final Iterator<UserDto> it = getBankAccountManagementForm().getUsersList().iterator();

        UserDto userDto = null;

        while (it.hasNext()) {

            userDto = it.next();
            if (userDto.getId().compareTo(id) == 0) {
                break;
            } else {
                userDto = null;
            }

        }

        if (userDto != null) {
            getManageBankUserForm().setUserDto(userDto);

            final Iterator<LegalEntityDto> legalEntitiesIterator = getManageBankUserForm().getUserDto()
                    .getLegalEntities().iterator();

            getManageBankUserForm().setLegalEntities(Constants.EMPTY_ARRAY);
            int[] tab = ArrayUtils.EMPTY_INT_ARRAY;
            while (legalEntitiesIterator.hasNext()) {
                final LegalEntityDto legalEnt = legalEntitiesIterator.next();
                tab = ArrayUtils.add(tab, legalEnt.getId());
            }
            getManageBankUserForm().setLegalEntities(tab);

            getManageBankUserForm().setRoleSA(false);
            getManageBankUserForm().setRoleMgmtAccnt(false);
            getManageBankUserForm().setRoleMgmtDoc(false);
            getManageBankUserForm().setRoleMgmtParam(false);
            getManageBankUserForm().setRoleViewCltData(false);
            getManageBankUserForm().setRoleViewStat(false);

            // We can't use the method switchFromRoleToBoolean(), because of cglib, due to Spring, so we do it
            // manually.

            final ListIterator<RoleDto> iterator = getManageBankUserForm().getUserDto().getRoles().listIterator();

            while (iterator.hasNext()) {

                final Integer roleId = iterator.next().getId();

                switch (roleId) {
                case Constants.ROLE_SUPER_ADMIN_ID:
                    getManageBankUserForm().setRoleSA(true);
                    break;
                case Constants.ROLE_MGMT_ACCOUNT_ID:
                    getManageBankUserForm().setRoleMgmtAccnt(true);
                    break;
                case Constants.ROLE_MGMT_DOCUMENTS_ID:
                    getManageBankUserForm().setRoleMgmtDoc(true);
                    break;
                case Constants.ROLE_VIEW_CLIENT_DATA_ID:
                    getManageBankUserForm().setRoleViewCltData(true);
                    break;
                case Constants.ROLE_VIEW_STATISTICS_ID:
                    getManageBankUserForm().setRoleViewStat(true);
                    break;
                case Constants.ROLE_MGMT_PARAMETERS_ID:
                    getManageBankUserForm().setRoleMgmtParam(true);
                    break;
                default:
                    break;
                }
            }

        } else {
            addError(this.propertiesHelper.getMessage("page.account.modify.bank.user.load"));
            return loadBankUserModel(Constants.PROFILE_BANK, Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD,
                    Constants.EMPTY_ARRAY);
        }

        return WebConstants.MODIFY_BANK_USER;
    }

    @RequestMapping(value = "/doModifyBankUser", method = RequestMethod.POST, params = "apply")
    public final String doModifyBankUser(@ModelAttribute("manageBankUserForm") final ManageBankUserForm form) {

        String ret = WebConstants.MODIFY_BANK_USER;
        boolean error = false;

        if (StringUtils.isBlank(form.getUserDto().getLastName())
                || StringUtils.isBlank(form.getUserDto().getFirstName())
                || StringUtils.isBlank(form.getUserDto().getEmail())) {
            error = true;
            addError(this.propertiesHelper.getMessage("page.account.modify.bank.user.empty.field.error"));
        }

        if (!error && !Pattern.compile(Constants.PATTERN_EMAIL).matcher(form.getUserDto().getEmail()).matches()) {
            error = true;
            addError(this.propertiesHelper.getMessage("page.account.modify.bank.user.email.error"));
        }

        if (!error) {
            form.getUserDto().setRoles(utilSetUserRole(form));
            if (form.getUserDto().getRoles().isEmpty()) {
                addError(this.propertiesHelper.getMessage("page.account.save.bank.user.no.role"));
                error = true;
            }
        }

        if (!error) {

            if (form.getLegalEntities() != null) {
                form.getUserDto().setLegalEntities(utilSetUserLegalEntities(form));
            }

            try {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_UPDATE,
                        new Object[] {form.getUserDto()});

                ret = "redirect:" + WebConstants.BANK_ACCOUNT_MANAGEMENT_LOAD;
                addWarning(this.propertiesHelper.getMessage("page.account.modify.user.success", new Object[] {form
                        .getUserDto().getLogin()}));
            } catch (final DCOException e) {
                addError(this.propertiesHelper.getMessage("page.account.update.user"));
            }
        }

        return ret;

    }

    @RequestMapping(value = "/doModifyBankUser", method = RequestMethod.POST, params = "init")
    public final String doModifyBankUserInitPassword(
            @ModelAttribute("manageBankUserForm") final ManageBankUserForm form) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_INIT_PASSWORD,
                    new Object[] {form.getUserDto().getId()});
            addConfirm(this.propertiesHelper.getMessage("page.account.password.reset.success"));
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.account.password.not.created.error"));
        }

        return WebConstants.MODIFY_BANK_USER;
    }

    @RequestMapping(value = "/cancelAction", method = RequestMethod.GET)
    public final String doCancel() {
        return loadBankUserModel(Constants.PROFILE_BANK, getBankAccountFilterForm().getLegalEntity(),
                getBankAccountFilterForm().getLogin(), getBankAccountFilterForm().getRoles());
    }

    /**
     * Allow to delete a user.
     * @param form
     * @return the model loaded with existing users.
     */
    @RequestMapping(value = "/doDeleteBankUser", method = RequestMethod.GET)
    public final String doDeleteBankUser(@ModelAttribute("manageBankUserForm") final ManageBankUserForm form) {
        try {
            final Map<UserDto, Integer> map3 = getStatsUsers(Constants.STATS_TYPE_CONNECTION);
            final Map<UserDto, Integer> map4 = getStatsUsers(Constants.STATS_TYPE_SEARCH);
            boolean found = false;
            for (final Entry<UserDto, Integer> entry : map3.entrySet()) {
                if (entry.getKey().getId().compareTo(form.getUserDto().getId()) == 0) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (final Entry<UserDto, Integer> entry : map4.entrySet()) {
                    if (entry.getKey().getId().compareTo(form.getUserDto().getId()) == 0) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_DELETE,
                        new Object[] {form.getUserDto().getId()});
                addConfirm(this.propertiesHelper.getMessage("page.account.delete.user.success")
                        + form.getUserDto().getLogin());
            } else {
                addWarning(this.propertiesHelper.getMessage("page.account.delete.user.warning")
                        + form.getUserDto().getLogin());
            }
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.account.delete.user.error"));
        }

        return loadBankUserModel(Constants.PROFILE_BANK, Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD,
                Constants.EMPTY_ARRAY);
    }

    /**
     * Get the user Stats (type 3 / 4).
     * @param type
     * @throws DCOException
     */
    private Map<UserDto, Integer> getStatsUsers(final Integer type) throws DCOException {
        return (Map<UserDto, Integer>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_STATS_USERS, new Object[] {type});
    }

    /**
     * Method which fill a new List with RoleDto, if the corresponding boolean attribute in the userBean, is true.
     * @param userBean
     * @return the roleDto's list.
     */
    private List<RoleDto> utilSetUserRole(final BankRoleManagementUser userBean) {

        final List<RoleDto> listRolesDto = new ArrayList<RoleDto>();

        final Iterator<RoleDto> it = getBankAccountFilterForm().getRolesList().iterator();

        // If we checked the super admin role, we only store this one
        if (userBean.getRoleSA()) {
            while (it.hasNext()) {
                final RoleDto r = it.next();
                if (Constants.ROLE_SUPER_ADMIN_ID == r.getId()) {
                    listRolesDto.add(r);
                    break;
                }
            }
        } else {
            while (it.hasNext()) {
                final RoleDto r = it.next();
                switch (r.getId()) {

                case Constants.ROLE_SUPER_ADMIN_ID:
                    if (userBean.getRoleSA()) {
                        listRolesDto.add(r);
                    }
                    break;
                case Constants.ROLE_MGMT_ACCOUNT_ID:
                    if (userBean.getRoleMgmtAccnt()) {
                        listRolesDto.add(r);
                    }
                    break;
                case Constants.ROLE_MGMT_DOCUMENTS_ID:
                    if (userBean.getRoleMgmtDoc()) {
                        listRolesDto.add(r);
                    }
                    break;
                case Constants.ROLE_MGMT_PARAMETERS_ID:
                    if (userBean.getRoleMgmtParam()) {
                        listRolesDto.add(r);
                    }
                    break;
                case Constants.ROLE_VIEW_STATISTICS_ID:
                    if (userBean.getRoleViewStat()) {
                        listRolesDto.add(r);
                    }
                    break;
                case Constants.ROLE_VIEW_CLIENT_DATA_ID:
                    if (userBean.getRoleViewCltData()) {
                        listRolesDto.add(r);
                    }
                    break;

                }
            }
        }
        return listRolesDto;
    }

    /**
     * Convert from Integer[] list of LegalEntities's id, to LegalEntitiesDto.
     * @param userBean
     * @return List<LegalEntitiesDto>
     */
    private List<LegalEntityDto> utilSetUserLegalEntities(final BankRoleManagementUser userBean) {

        final List<LegalEntityDto> listLegalEntityDto = new ArrayList<LegalEntityDto>();

        final Iterator<LegalEntityDto> it = getBankAccountFilterForm().getLegalEntityList().iterator();

        while (it.hasNext()) {

            final LegalEntityDto legalEnt = it.next();
            for (final int id : userBean.getLegalEntities()) {

                if (legalEnt.getId() == id) {
                    listLegalEntityDto.add(legalEnt);
                    break;
                }
            }

        }
        return listLegalEntityDto;

    }

    private void resetManageBankUserForm() {

        final UserDto u = new UserDto();
        getManageBankUserForm().setUserDto(u);
        if (getManageBankUserForm().getLegalEntities() != null) {
            getManageBankUserForm().setLegalEntities(Constants.EMPTY_ARRAY);
        }

        getManageBankUserForm().setHasChangeColumn(false);
        getManageBankUserForm().setRoleMgmtAccnt(false);
        getManageBankUserForm().setRoleMgmtDoc(false);
        getManageBankUserForm().setRoleMgmtParam(false);
        getManageBankUserForm().setRoleSA(false);
        getManageBankUserForm().setRoleViewCltData(false);
        getManageBankUserForm().setRoleViewStat(false);
    }
}
