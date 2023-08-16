package com.bnpp.dco.presentation.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.RoleDto;
import com.bnpp.dco.common.dto.UserDto;

/**
 * User in session.
 */
public class UserSession extends org.springframework.security.core.userdetails.User {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;
    private String password;
    private Integer profile;
    private String entity;
    private Locale localeEntity;
    private UserSessionPrefs preferences;
    private boolean locked;
    private boolean passwordUpToDate;
    private boolean xbasV2;
    private boolean deleted;
    private Locale localeWorkingCountry;

    public UserSession(final UserDto u) {
        super(u.getLogin(), u.getPassword(), true, true, true, !u.isLocked(), buildGrantedAuthorities(u));
        this.login = u.getLogin();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.email = u.getEmail();
        this.tel = u.getTel();
        this.password = u.getPassword();
        this.profile = u.getProfile();
        if (u.getEntities() != null) {
            if (u.getEntities().size() > 0) {
                this.entity = u.getEntities().get(Constants.VAR_ZERO).getLabel();
                if (u.getEntities().get(Constants.VAR_ZERO).getCountry() != null) {
                    this.localeEntity = u.getEntities().get(Constants.VAR_ZERO).getCountry();
                }
            }
        }
        if (u.getPreferences() != null) {
            this.preferences = new UserSessionPrefs();
            this.preferences.setId(u.getPreferences().getId());
            this.preferences.setFormatAmount(u.getPreferences().getFormatAmount());
            this.preferences.setDateFormat(u.getPreferences().getDateFormat());
            this.preferences.setLanguageId(u.getPreferences().getLanguage().getId());
            this.preferences.setLocale(u.getPreferences().getLanguage().getLocale());
            this.preferences.setCommercialMessageClient(u.getPreferences().getLanguage()
                    .getCommercialMessageClient());
        }
        this.locked = u.isLocked();
        this.xbasV2 = u.isXbasV2();
        this.deleted = u.isDeleted();
        // Max attemps exceeded => user has to change password
        if (u.getLockLevel() >= Constants.USER_LOCK_NB_ATTEMPTS) {
            this.passwordUpToDate = false;
        } else {
            Date todayMinus = new Date();
            final Calendar cal = Calendar.getInstance();
            cal.setTime(todayMinus);
            cal.add(Calendar.DAY_OF_MONTH, Constants.VAR_NEG_NINETY);
            todayMinus = cal.getTime();
            this.passwordUpToDate = todayMinus.before(u.getDatePassword());
        }
    }

    private static List<GrantedAuthority> buildGrantedAuthorities(final UserDto user) {
        final List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        if (user.getRoles() != null) {
            for (final RoleDto role : user.getRoles()) {
                result.add(new SimpleGrantedAuthority(role.getLabel()));
            }
        }
        return result;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(final String tel) {
        this.tel = tel;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getProfile() {
        return this.profile;
    }

    public void setProfile(final Integer profile) {
        this.profile = profile;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(final String entity) {
        this.entity = entity;
    }

    public UserSessionPrefs getPreferences() {
        return this.preferences;
    }

    public void setPreferences(final UserSessionPrefs preferences) {
        this.preferences = preferences;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public final boolean isLocked() {
        return this.locked;
    }

    public final void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public final boolean isPasswordUpToDate() {
        return this.passwordUpToDate;
    }

    public final void setPasswordUpToDate(final boolean passwordUpToDate) {
        this.passwordUpToDate = passwordUpToDate;
    }

    public final Locale getLocaleWorkingCountry() {
        return this.localeWorkingCountry;
    }

    public final void setLocaleWorkingCountry(final Locale localeWorkingCountry) {
        this.localeWorkingCountry = localeWorkingCountry;
    }

    public final Locale getLocaleEntity() {
        return this.localeEntity;
    }

    public final void setLocaleEntity(final Locale localeEntity) {
        this.localeEntity = localeEntity;
    }

    /**
     * Customized getter to prevent too long rendering on First Name & Last Name.
     * @return the truncated Last Name.
     */
    public String getSplitFirstName() {
        if (this.firstName != null && this.firstName.length() > 1) {
            final StringBuilder sb = new StringBuilder(String.valueOf(this.firstName.charAt(0))).append('.');
            return sb.toString();
        } else {
            return this.firstName;
        }
    }

    /**
     * Customized getter to prevent too long rendering on Last Name.
     * @return the truncated Last Name.
     */
    public String getSplitLastName() {
        if (this.lastName != null && this.lastName.length() > 15) {
            return this.lastName.substring(0, 14);
        } else {
            return this.lastName;
        }
    }

	public final boolean isXbasV2() {
		return xbasV2;
	}

	public final void setXbasV2(final boolean xbasV2) {
		this.xbasV2 = xbasV2;
	}

	public final boolean isDeleted() {
		return deleted;
	}

	public final void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
