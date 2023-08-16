package com.bnpp.dco.common.dto;

public class DocumentTypeDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String label;

    public DocumentTypeDto() {
        super();
    }

    public DocumentTypeDto(final Integer id) {
        super();
        this.id = id;
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

}
