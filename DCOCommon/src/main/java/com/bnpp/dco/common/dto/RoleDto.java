package com.bnpp.dco.common.dto;

import java.util.ArrayList;
import java.util.List;

public class RoleDto implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String label;
    private List<UserDto> users = new ArrayList<UserDto>();

    public RoleDto() {
    }

    public RoleDto(final Integer id) {
        this.id = id;
    }

    public RoleDto(final Integer id, final String label) {
        this.id = id;
        this.label = label;
    }

    public RoleDto(final String label) {
        this.label = label;
    }

    public RoleDto(final String label, final List<UserDto> users) {
        this.label = label;
        this.users = users;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public List<UserDto> getUsers() {
        return this.users;
    }

    public void setUsers(final List<UserDto> users) {
        this.users = users;
    }
}
