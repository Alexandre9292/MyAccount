package com.bnpp.dco.common.dto;

public class DateFormatDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String label = "";
    private String labelDisplay = "";
    private String labelLong = "";

    public DateFormatDto(final Integer id) {
        super();
        this.id = id;
    }

    public DateFormatDto() {
        super();
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getLabel() {
        return this.label;
    }

    public final void setLabel(final String label) {
        this.label = label;
    }

    public String getLabelDisplay() {
        return this.labelDisplay;
    }

    public void setLabelDisplay(final String labelDisplay) {
        this.labelDisplay = labelDisplay;
    }

    public String getLabelLong() {
        return this.labelLong;
    }

    public void setLabelLong(final String labelLong) {
        this.labelLong = labelLong;
    }

}
