package com.bnpp.dco.common.dto;

import org.apache.commons.lang3.StringUtils;

public class AddressDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String fieldOne = "";
    private String fieldTwo = "";
    private String fieldThree = "";
    private String fieldFour = "";
    private String fieldFive = "";
    private String fieldSix = "";
    private String fieldSeven = "";

    public AddressDto() {
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getFieldOne() {
        return this.fieldOne;
    }

    public final void setFieldOne(final String fieldOne) {
        this.fieldOne = fieldOne;
    }

    public final String getFieldTwo() {
        return this.fieldTwo;
    }

    public final void setFieldTwo(final String fieldTwo) {
        this.fieldTwo = fieldTwo;
    }

    public final String getFieldThree() {
        return this.fieldThree;
    }

    public final void setFieldThree(final String fieldThree) {
        this.fieldThree = fieldThree;
    }

    public final String getFieldFour() {
        return this.fieldFour;
    }

    public final void setFieldFour(final String fieldFour) {
        this.fieldFour = fieldFour;
    }

    public final String getFieldFive() {
        return this.fieldFive;
    }

    public final void setFieldFive(final String fieldFive) {
        this.fieldFive = fieldFive;
    }

    public final String getFieldSix() {
        return this.fieldSix;
    }

    public final void setFieldSix(final String fieldSix) {
        this.fieldSix = fieldSix;
    }

    public final String getFieldSeven() {
        return this.fieldSeven;
    }

    public final void setFieldSeven(final String fieldSeven) {
        this.fieldSeven = fieldSeven;
    }

    /**
     * check that field one to four are not Empty
     * @return true if field has not empty
     */
    public Boolean isValid() {
        Boolean result = false;
        if (StringUtils.isNotBlank(getFieldOne()) && StringUtils.isNotBlank(getFieldTwo())
                && StringUtils.isNotBlank(getFieldThree()) && StringUtils.isNotBlank(getFieldFour())) {
            result = true;
        }
        return result;
    }
}
