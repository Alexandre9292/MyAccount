package com.bnpp.dco.common.dto;

public class UserTableDto implements java.io.Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 6901656825712381792L;
	
	private Integer id;
    private Integer profile;
    private String login;
    private String firstName;
    private String lastName;

	public UserTableDto(final String login, 
            final String firstName, final String lastName) {
        super();
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserTableDto() {
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
}
