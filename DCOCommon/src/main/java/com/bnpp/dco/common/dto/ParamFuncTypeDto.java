package com.bnpp.dco.common.dto;

import java.util.ArrayList;
import java.util.List;

public class ParamFuncTypeDto implements java.io.Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String labelDefault;
    private List<ParamFuncDto> paramFuncs = new ArrayList<ParamFuncDto>();

    public ParamFuncTypeDto() {
    }

    public ParamFuncTypeDto(final String labelDefault) {
        this.labelDefault = labelDefault;
    }

    public ParamFuncTypeDto(final String labelDefault, final List<ParamFuncDto> paramFuncs) {
        this.labelDefault = labelDefault;
        this.paramFuncs = paramFuncs;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getLabelDefault() {
        return this.labelDefault;
    }

    public void setLabelDefault(final String labelDefault) {
        this.labelDefault = labelDefault;
    }

    public List<ParamFuncDto> getParamFuncs() {
        return this.paramFuncs;
    }

    public void setParamFuncs(final List<ParamFuncDto> paramFuncs) {
        this.paramFuncs = paramFuncs;
    }
}
