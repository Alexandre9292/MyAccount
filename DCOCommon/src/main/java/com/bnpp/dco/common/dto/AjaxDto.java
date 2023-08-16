package com.bnpp.dco.common.dto;

import java.util.Comparator;

public class AjaxDto implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String label;
    private String login;
    private String label2;

    public AjaxDto(final Integer id, final String label, final String login) {
        super();
        this.id = id;
        this.label = label;
        this.login = login;
    }

    public AjaxDto(final Integer id, final String label) {
        super();
        this.id = id;
        this.label = label;
    }

    public AjaxDto(final String label, final String label2) {
        super();
        this.label = label;
        this.label2 = label2;
    }

    public AjaxDto(final String label) {
        super();
        this.label = label;
    }

    /**
     * Contructor.
     */
    public AjaxDto() {
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

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getLabel2() {
        return this.label2;
    }

    public void setLabel2(final String label2) {
        this.label2 = label2;
    }

    public static class OrderByLabel implements Comparator<AjaxDto> {

        @Override
        public int compare(final AjaxDto o1, final AjaxDto o2) {
            return o1.label.compareTo(o2.label);
        }
    }

    public static class OrderByLogin implements Comparator<AjaxDto> {

        @Override
        public int compare(final AjaxDto o1, final AjaxDto o2) {
            return o1.login.compareTo(o2.login);
        }
    }
}
