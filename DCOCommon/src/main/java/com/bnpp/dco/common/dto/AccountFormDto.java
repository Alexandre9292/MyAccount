package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.List;

public class AccountFormDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private EntityDto entity;
    private List<AccountDto> accountDtoList;

    public AccountFormDto(final Integer id, final EntityDto entity) {
        super();
        this.id = id;
        this.entity = entity;
    }

    public AccountFormDto() {
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the entity
     */
    public EntityDto getEntity() {
        return this.entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(final EntityDto entity) {
        this.entity = entity;
    }

    public List<AccountDto> getAccountDtoList() {
        return this.accountDtoList;
    }

    public void setAccountDtoList(final List<AccountDto> accountDtoList) {
        this.accountDtoList = accountDtoList;
    }

}
