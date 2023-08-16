package com.bnpp.dco.common.dto;

import java.util.Date;
import java.util.List;

public class UserDto implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer profile;
    private PreferencesDto preferences;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;
    private Date datePassword;
    private String password2;
    private String password3;
    private int lockLevel;
    private boolean locked;
    private List<EntityDto> entities;
    private List<RoleDto> roles;
    private List<LegalEntityDto> legalEntities;
    
    /** Checkbox to know if user is XBAS V2 or not. */
    private boolean xbasV2;
    
    /** Flag to know if user wants to be delete. */
    private boolean deleted;

	public UserDto(final PreferencesDto preferences, final String login, final String password,
            final String firstName, final String lastName, final String email, final String tel) {
        super();
        if (preferences != null) {
            this.preferences = preferences;
        }
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
    }

    public UserDto() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getProfile() {
        return this.profile;
    }

    public void setProfile(final Integer profile) {
        this.profile = profile;
    }

    public PreferencesDto getPreferences() {
        return this.preferences;
    }

    public void setPreferences(final PreferencesDto preferences) {
        this.preferences = preferences;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
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

    public List<EntityDto> getEntities() {
        return this.entities;
    }

    public void setEntities(final List<EntityDto> entities) {
        this.entities = entities;
    }

    public List<RoleDto> getRoles() {
        return this.roles;
    }

    public void setRoles(final List<RoleDto> roles) {
        this.roles = roles;
    }

    public final List<LegalEntityDto> getLegalEntities() {
        return this.legalEntities;
    }

    public final void setLegalEntities(final List<LegalEntityDto> legalEntities) {
        this.legalEntities = legalEntities;
    }

    public final Date getDatePassword() {
        return this.datePassword;
    }

    public final void setDatePassword(final Date datePassword) {
        this.datePassword = datePassword;
    }

    public final String getPassword2() {
        return this.password2;
    }

    public final void setPassword2(final String password2) {
        this.password2 = password2;
    }

    public final String getPassword3() {
        return this.password3;
    }

    public final void setPassword3(final String password3) {
        this.password3 = password3;
    }

    public int getLockLevel() {
        return this.lockLevel;
    }

    public void setLockLevel(final int lockLevel) {
        this.lockLevel = lockLevel;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

	public final boolean isXbasV2() {
		return xbasV2;
	}

	public final void setXbasV2(boolean xbasV2) {
		this.xbasV2 = xbasV2;
	}

	public final boolean isDeleted() {
		return deleted;
	}

	public final void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
