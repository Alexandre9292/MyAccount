package com.bnpp.dco.business.dto;

// Generated 2 sept. 2013 15:02:16 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Address generated by hbm2java
 */
@Entity
@Table(name = "address", catalog = "dco")
public class Address implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String fieldOne;
    private String fieldTwo;
    private String fieldThree;
    private String fieldFour;
    private String fieldFive;
    private String fieldSix;
    private String fieldSeven;

    public Address() {
    }

    public Address(final String fieldOne) {
        this.fieldOne = fieldOne;
    }

    public Address(final String fieldOne, final String fieldTwo, final String fieldThree, final String fieldFour,
            final String fieldFive, final String fieldSix, final String fieldSeven) {
        this.fieldOne = fieldOne;
        this.fieldTwo = fieldTwo;
        this.fieldThree = fieldThree;
        this.fieldFour = fieldFour;
        this.fieldFive = fieldFive;
        this.fieldSix = fieldSix;
        this.fieldSeven = fieldSeven;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Column(name = "FIELD_ONE", nullable = false, length = 100)
    public String getFieldOne() {
        return this.fieldOne;
    }

    public void setFieldOne(final String fieldOne) {
        this.fieldOne = fieldOne;
    }

    @Column(name = "FIELD_TWO", length = 100)
    public String getFieldTwo() {
        return this.fieldTwo;
    }

    public void setFieldTwo(final String fieldTwo) {
        this.fieldTwo = fieldTwo;
    }

    @Column(name = "FIELD_THREE", length = 100)
    public String getFieldThree() {
        return this.fieldThree;
    }

    public void setFieldThree(final String fieldThree) {
        this.fieldThree = fieldThree;
    }

    @Column(name = "FIELD_FOUR", length = 100)
    public String getFieldFour() {
        return this.fieldFour;
    }

    public void setFieldFour(final String fieldFour) {
        this.fieldFour = fieldFour;
    }

    @Column(name = "FIELD_FIVE", length = 100)
    public String getFieldFive() {
        return this.fieldFive;
    }

    public void setFieldFive(final String fieldFive) {
        this.fieldFive = fieldFive;
    }

    @Column(name = "FIELD_SIX", length = 100)
    public String getFieldSix() {
        return this.fieldSix;
    }

    public void setFieldSix(final String fieldSix) {
        this.fieldSix = fieldSix;
    }

    @Column(name = "FIELD_SEVEN", length = 100)
    public String getFieldSeven() {
        return this.fieldSeven;
    }

    public void setFieldSeven(final String fieldSeven) {
        this.fieldSeven = fieldSeven;
    }
}
