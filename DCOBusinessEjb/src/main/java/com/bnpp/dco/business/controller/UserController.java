package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.AccountThirdParty;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.Preferences;
import com.bnpp.dco.business.dto.Role;
import com.bnpp.dco.business.dto.ThirdParty;
import com.bnpp.dco.business.dto.User;
import com.bnpp.dco.business.dto.converter.ConverterPreferences;
import com.bnpp.dco.business.dto.converter.ConverterRole;
import com.bnpp.dco.business.dto.converter.ConverterUser;
import com.bnpp.dco.business.dto.converter.ConverterUserTable;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.PreferencesDto;
import com.bnpp.dco.common.dto.RoleDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.dto.UserTableDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.PasswordUtil;

/**
 * Controller for User actions.
 */
public final class UserController extends GenericController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static final int BASIC_PWD_LENGTH = 8;
    private StatsController statsController;

    public StatsController getStatsController() {
        return this.statsController;
    }

    public void setStatsController(final StatsController statsController) {
        this.statsController = statsController;
    }

    /**
     * Get the user form login/password.
     * @param login the username
     * @param password the password
     * @return the identified object
     * @throws DCOException a DCOException
     */
    public UserDto login(final String login, final String password) throws DCOException {
        User user;
        final Query q = getEntityManager().createQuery(PropertiesHelper.getMessage(DatabaseConstants.USER_LOGIN));
        q.setParameter("login", login);
        final List<?> listResult = q.getResultList();
        if (!listResult.isEmpty()) {
            user = (User) listResult.get(0);
        } else {
            final String message = new StringBuilder("The user \"").append(login).append("\" does not exist")
                    .toString();
            LOG.error(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_LOGIN_NOT_EXIST);

        }

        if (!user.getPassword().equals(password)) {
            final String message = "Invalid password";
            LOG.error(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_LOGIN_WRONG_PASSWORD);
        }

        return ConverterUser.convertDbToDto(user, true);
    }

    public UserDto getUserByLogin(final String login) throws DCOException {
        return ConverterUser.convertDbToDto(doGetUserByLogin(login), true);
    }

    private User doGetUserByLogin(final String login) throws DCOException {

        final Query q = getEntityManager().createQuery(PropertiesHelper.getMessage(DatabaseConstants.USER_LOGIN));
        q.setParameter("login", login);
        final List<?> listResult = q.getResultList();
        if (!listResult.isEmpty()) {
            return (User) listResult.get(0);
        } else {
            final String message = new StringBuilder("The user \"").append(login).append("\" does not exist")
                    .toString();
            LOG.error(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_LOGIN_NOT_EXIST);

        }
    }

    public PreferencesDto getPreferencesByUserLogin(final String login) throws DCOException {

        return ConverterPreferences.convertDbToDto(doGetPreferencesByUserLogin(login));

    }

    private Preferences doGetPreferencesByUserLogin(final String login) {

        Preferences pref = null;
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.USER_PREFERENCES));
        q.setParameter("login", login);
        final List<?> listResult = q.getResultList();

        if (!listResult.isEmpty() && listResult.get(0) instanceof Preferences) {
            pref = (Preferences) listResult.get(0);
        } else {
            final String message = new StringBuilder("The user \"").append(login)
                    .append("\" does not has preferences").toString();
            LOG.error(message);
        }

        return pref;
    }

    /**
     * Generate a new user (with a generated password)
     * @param userDto the DTO bean to use
     * @return the newly created user
     */
    public UserDto createUserFromDto(final UserDto userDto) throws DCOException {

    	final String password = userDto.getPassword();
    	userDto.setPassword(null);

        final User user = ConverterUser.convertDtoToDb(userDto, true);

        final Query q = getEntityManager().createQuery(PropertiesHelper.getMessage(DatabaseConstants.USER_LOGIN));
        q.setParameter("login", userDto.getLogin());

        final List<?> listResult = q.getResultList();

        if (listResult.isEmpty()) {
            // final String password = PasswordUtil.createPassword(BASIC_PWD_LENGTH);
            final String encodedPassword = PasswordUtil.encode(password);

            setUserPasswords(user, encodedPassword);

            if (user.getProfile() == Constants.PROFILE_CLIENT && user.getEntitieses() != null) {
                final Iterator<Entities> it = user.getEntitieses().iterator();
                if (it.hasNext()) {
                    final Entities e = it.next();
                    getEntityManager().persist(e);
                }
            }

            getEntityManager().persist(user);

            final UserDto ret = login(user.getLogin(), encodedPassword);
            try {
                getMailUtil().sendUserCreationConfirmation(user.getEmail(), user.getLogin(), password);
            } catch (final DCOException e) {
                // Mailing failed
                getEntityManager().remove(user);
                final StringBuilder sbMessage = new StringBuilder(
                        "Error while sending account creation confirmation email for identifier : ");
                sbMessage.append(user.getLogin());
                LOG.error(sbMessage.toString(), e);
                throw new DCOException(e.getMessage(), Constants.EXCEPTION_CODE_EMAIL);
            }
            return ret;

        } else {
            final String message = "The user already exist";
            LOG.error(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_CREATE_ALREADY_EXISTS);
        }
    }

    public UserDto updateUser(final UserDto userDto) {
        User user = null;
        try {
            user = ConverterUser.convertDtoToDb(userDto, true);
            return ConverterUser.convertDbToDto(getEntityManager().merge(user), true);
        } catch (final DCOException e) {
            return null;
        }
    }

    public void forgottenPassword(final String login, final String email) throws DCOException {
        final User userDb = doFindUserByLogin(login);
        if (userDb == null) {
            final String message = new StringBuilder(
                    "A request for a forgotten password has been sent for login \"").append(login)
                    .append("\" but the user does not exist").toString();
            LOG.info(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_NO_USER);
        }
        if (!userDb.getEmail().equals(email)) {
            final String message = new StringBuilder(
                    "A request for a forgotten password has been sent for login \"").append(login)
                    .append("\" and email \"").append(email).append("\" but these don't match").toString();
            LOG.info(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_ID_EMAIL_NO_MATCH);
        }
        if (userDb.isLocked()) {
            final String message = new StringBuilder(
                    "A request for a forgotten password has been sent for login \"").append(login)
                    .append("\" but the account is locked").toString();
            LOG.info(message);
            throw new DCOException(message, Constants.EXCEPTION_CODE_USER_FORGOTTEN_PASSWORD_LOCKED);
        }
        initUserPassword(userDb.getId());
    }

    public void initUserPassword(final Integer userId) throws DCOException {
        final String password = PasswordUtil.createPassword(BASIC_PWD_LENGTH);
        final String encodedPassword = PasswordUtil.encode(password);
        final User user = getEntityManager().find(User.class, userId);
        setUserPasswords(user, encodedPassword);
        // Resetting the lock
        user.setLocked(false);
        // Setting the lock level so the user has to change it
        user.setLockLevel(Constants.USER_LOCK_NB_ATTEMPTS);
        // Saving the updated user
        getEntityManager().merge(user);

        // Sending the new password to the user
        // Here the user is not null
        try {
            getMailUtil().sendUserResetPassword(user.getEmail(), user.getLogin(), password);
        } catch (final DCOException e) {
            // Mailing failed
            final StringBuilder sbMessage = new StringBuilder(
                    "Error while sending reset password email for identifier : ");
            sbMessage.append(user.getLogin());
            LOG.error(sbMessage.toString(), e);
            throw e;
        }
    }

    public void deleteUser(final Integer id) {

        final User u = getEntityManager().find(User.class, id);
        if (u != null) {
            if (u.getThirdParties() != null) {
                for (final ThirdParty tp : u.getThirdParties()) {
                    if (tp.getAccountThirdParties() != null) {
                        for (final AccountThirdParty atp : tp.getAccountThirdParties()) {
                            getEntityManager().remove(atp);
                        }
                    }
                    getEntityManager().remove(tp);
                }
            }
            if (u.getPreferences() != null) {
                getEntityManager().remove(u.getPreferences());
            }
            getEntityManager().remove(u);
        }
    }

    /**
     * Gets the list of all the users.
     * @return the list of users
     * @throws DCOException
     */
    public List<UserDto> list() throws DCOException {
        final Query q = getEntityManager().createQuery(PropertiesHelper.getMessage(DatabaseConstants.USER_LIST));

        final List<?> result = q.getResultList();
        final List<UserDto> resultUsers = new ArrayList<UserDto>();
        for (final Object object : result) {
            if (object instanceof User) {
                resultUsers.add(ConverterUser.convertDbToDto((User) object, true));
            }
        }
        return resultUsers;
    }

    public List<UserDto> bankUserList(final Integer profile, final String username, final Integer legalEntity,
            final int[] roleList) throws DCOException {

        final StringBuilder query = new StringBuilder(PropertiesHelper.getMessage(DatabaseConstants.USER_LIST));
        boolean and = false, or = false;

        // First, we add the join condition to the query.
        if (roleList.length > 0) {
            query.append(DatabaseConstants.JOIN_CLAUSE).append(
                    PropertiesHelper.getMessage(DatabaseConstants.USER_JOIN_ROLE));
        }

        if (legalEntity != -1) {
            query.append(DatabaseConstants.JOIN_CLAUSE).append(
                    PropertiesHelper.getMessage(DatabaseConstants.USER_JOIN_LEGAL_ENTITY));
            // PropertiesHelper.getMessage(DatabaseConstants.USER_JOIN_COUNTRY_LEGAL_ENTITY));
        }

        if (roleList.length > 0) {
            for (int i = 0; i < roleList.length; i++) {
                if (or) {
                    query.append(DatabaseConstants.OR_CLAUSE);
                } else {
                    query.append(DatabaseConstants.WHERE_CLAUSE).append(DatabaseConstants.LEFT_BRAQUET);
                    or = true;
                    and = true;
                }
                query.append(PropertiesHelper.getMessage(DatabaseConstants.USER_FILTER_ROLE)).append(i + 1);
            }
            query.append(DatabaseConstants.RIGHT_BRAQUET);
        }

        if (profile != -1) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_PROFILE));
        }

        if (legalEntity != -1) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            // query.append(PropertiesHelper.getMessage(DatabaseConstants.USER_FILTER_COUNTRY_LEGAL_ENTITY));
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_LEGAL_ENTITY));
        }

        if (StringUtils.isNotBlank(username)) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.USER_FILTER_LOGIN));
        }

        final Query q = getEntityManager().createQuery(query.toString());

        if (profile != -1) {
            q.setParameter("profile", profile);
        }
        if (legalEntity != -1) {
            q.setParameter("legalEntity", legalEntity);
        }
        if (StringUtils.isNotBlank(username)) {
            q.setParameter("login", "%" + username + "%");
        }

        for (int i = 0; i < roleList.length; i++) {
            q.setParameter(i + 1, roleList[i]);
        }

        final List<?> result = q.getResultList();
        final List<UserDto> resultUsers = new ArrayList<UserDto>();
        for (final Object object : result) {
            if (object instanceof User) {
                resultUsers.add(ConverterUser.convertDbToDto((User) object, true));
            }
        }
        return resultUsers;

    }

    /**
     * Allow to fetch the user client for the client management
     * @param profile
     * @param username
     * @param entityLabel
     * @param lastname
     * @param firstname
     * @return the list of filtered userDto
     * @throws DCOException
     */
    public List<UserTableDto> clientUserList(final String lastname, final String firstname, final String username,
            final String entityLabel) throws DCOException {

        final StringBuilder query = new StringBuilder(PropertiesHelper.getMessage(DatabaseConstants.USER_LIST));
        boolean and = false;

        if (StringUtils.isNotBlank(entityLabel)) {
            query.append(DatabaseConstants.JOIN_CLAUSE)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.USER_JOIN_ENTITY))
                    .append(DatabaseConstants.WHERE_CLAUSE)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_ENTITY));
            and = true;
        }

        if (and) {
            query.append(DatabaseConstants.AND_CLAUSE);
        } else {
            query.append(DatabaseConstants.WHERE_CLAUSE);
            and = true;
        }
        query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_PROFILE));

        if (StringUtils.isNotBlank(username)) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.USER_FILTER_LOGIN));
        }

        if (StringUtils.isNotBlank(firstname)) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.USER_FILTER_FIRSTNAME));
        }

        if (StringUtils.isNotBlank(lastname)) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.USER_FILTER_LASTNAME));
        }

        final Query q = getEntityManager().createQuery(query.toString());

        q.setParameter("profile", Constants.PROFILE_CLIENT);

        if (StringUtils.isNotBlank(entityLabel)) {
            q.setParameter("entity", entityLabel);
        }
        if (StringUtils.isNotBlank(username)) {
            q.setParameter("login", "%" + username + "%");
        }
        if (StringUtils.isNotBlank(firstname)) {
            q.setParameter("firstname", "%" + firstname + "%");
        }
        if (StringUtils.isNotBlank(lastname)) {
            q.setParameter("lastname", "%" + lastname + "%");
        }

        final List<?> result = q.getResultList();
        List<UserTableDto> resultUsers = new ArrayList<UserTableDto>();
        for (final Object object : result) {
            if (object instanceof User) {
                resultUsers.add(ConverterUserTable.convertDbToDto((User) object, true));
            }
        }
        return resultUsers;
    }

    /**
     * Method to fetch the mapping (1<->n) between users and entities.
     * @return
     */
    public List<AjaxDto> getUserEntitiesExclusion() {

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.USER_ENTITIES_EXCLUSION));
        final List<Object[]> result = q.getResultList();
        final List<AjaxDto> dtoReturn = new ArrayList<AjaxDto>();

        for (final Object[] object : result) {
            final Integer id = (Integer) object[0];
            final String entity = (String) object[1];
            final String login = (String) object[2];
            final AjaxDto ajaxDto = new AjaxDto(id, entity, login);
            dtoReturn.add(ajaxDto);
        }

        return dtoReturn;
    }

    public void saveDatasUser(final UserDto userDto, final String newPassword) throws DCOException {
        final User user = ConverterUser.convertDtoToDb(userDto, true);
        // Only updating the password if needed
        if (StringUtils.isNotBlank(newPassword) && newPassword!=null) {
            setUserPasswords(user, newPassword);
        }
        
        
        if (user.getPreferences() != null) {
            getEntityManager().merge(user.getPreferences());
        }
        
        user.setLogin(user.getEmail());
        
        // Resetting the lock level for the case when is has just been unlocked
        user.setLockLevel(0);
        getEntityManager().merge(user);
        // Merging the entities explicitly to save their details
        if (user.getProfile() == Constants.PROFILE_CLIENT && user.getEntitieses() != null) {
            final Set<Entities> entities = new HashSet<Entities>();
            for (final Entities entity : user.getEntitieses()) {
                final Entities entityDB = getEntityManager().find(Entities.class, entity.getId());
                entity.setUsers(entityDB.getUsers());
                entity.setAccountForms(entityDB.getAccountForms());
                entity.setParamTeches(entityDB.getParamTeches());
                entity.setThirdParties(entityDB.getThirdParties());
                getEntityManager().merge(entity);
            }
            user.setEntitieses(entities);
        }
    }

    public void saveRoleUser(final List<UserDto> usersDtoList) {

        for (final UserDto userDto : usersDtoList) {
            // final User user = ConverterUser.convertDtoToDb(userDto);
            final User user = getEntityManager().find(User.class, userDto.getId());

            if (userDto.getRoles() != null) {
                final Set<Role> roles = new HashSet<Role>();
                for (final RoleDto roleDto : userDto.getRoles()) {
                    roles.add(ConverterRole.convertDtoToDb(roleDto));
                }
                user.setRoles(roles);
            }

            getEntityManager().merge(user);
        }
    }

    private void setUserPasswords(final User user, final String newPassword) {

        final Date today = new Date(Calendar.getInstance().getTimeInMillis());
        user.setDatePassword(today);

        user.setPassword3(user.getPassword2());
        user.setPassword2(user.getPassword());
        user.setPassword(newPassword);

    }

    /**
     * Utility function that fetches a user from login and returns null if it could not be found.
     * @param login
     * @return the login of the user
     */
    private User doFindUserByLogin(final String login) {
        User result = null;
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<User> q = cb.createQuery(User.class);
        final Root<User> u = q.from(User.class);
        q.where(cb.equal(u.get("login"), login));
        final TypedQuery<User> tq = getEntityManager().createQuery(q);
        final List<User> usersDb = tq.getResultList();
        if (usersDb != null && usersDb.size() != 0) {
            result = usersDb.get(0);
        }
        return result;
    }

    /**
     * Increments the number of failed attempts to log. Throws an exception if the max number has been reached
     * @param login the login of the user
     * @throws DCOException if the max number of failed attempts has been reached
     */
    public int addFail(final String login) throws DCOException {
        final User userDb = doFindUserByLogin(login);
        if (userDb != null) {
            userDb.setLockLevel(userDb.getLockLevel() + 1);
            if (userDb.getLockLevel() >= Constants.USER_LOCK_NB_ATTEMPTS) {
                userDb.setLocked(true);
            }
            getEntityManager().merge(userDb);
            if (userDb.isLocked()) {
                final String message = new StringBuilder("The user with identifier \"").append(login)
                        .append("\" has reached the maximum numer of failed connection attempts => Locked")
                        .toString();
                LOG.info(message);
                throw new DCOException(message, 1);
            }
            return userDb.getLockLevel();
        }
        return -1;
    }

    /**
     * Resets the lock level of a user and increment the number of connection for the logged user.
     * @param login the login of the user
     * @throws DCOException if the user does not exist
     */
    public void resetLock(final String login) throws DCOException {
        final User userDb = doFindUserByLogin(login);
        if (userDb != null) {
            // If the password doesn't need to be changed, resetting the lock level
            if (!userDb.isLocked() && userDb.getLockLevel() < Constants.USER_LOCK_NB_ATTEMPTS) {
                userDb.setLockLevel(0);
                getEntityManager().merge(userDb);
                getStatsController().incrementUserStat(userDb, Constants.STATS_TYPE_CONNECTION, null);
            }
        } else {
            final String message = new StringBuilder("The user with identifier \"").append(login)
                    .append("\" could not be unlocked as it does not exist").toString();
            LOG.info(message);
            throw new DCOException(message);
        }
    }

    /**
     * Allow to increment the stats of the user, when this one is searching document.
     * @throws DCOException
     */
    public void incrementUserStatSearchDoc(final String login) throws DCOException {
        getStatsController().incrementUserStat(doGetUserByLogin(login), Constants.STATS_TYPE_SEARCH, null);
    }

    /**
     * Get the user stats.
     * @param type
     * @throws DCOException
     */
    public Map<UserDto, Integer> getStatsUsers(final int type) throws DCOException {
        return getStatsController().getStatsUsers(type);
    }

}
