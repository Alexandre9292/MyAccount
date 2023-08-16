package com.bnpp.dco.presentation.bean;

import java.io.Serializable;
import java.util.Locale;

import com.bnpp.dco.common.dto.DateFormatDto;

/**
 * User preferences in session.
 */
public class UserSessionPrefs implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 6187009510340694014L;
	
	private Integer id;
    private Integer languageId;
    private DateFormatDto dateFormat;
    private String formatAmount;
    private Locale locale;
    private String commercialMessageClient;

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final Integer getLanguageId() {
        return this.languageId;
    }

    public final void setLanguageId(final Integer languageId) {
        this.languageId = languageId;
    }

    public final DateFormatDto getDateFormat() {
        return this.dateFormat;
    }

    public final void setDateFormat(final DateFormatDto dateFormat) {
        this.dateFormat = dateFormat;
    }

    public final String getFormatAmount() {
        return this.formatAmount;
    }

    public final void setFormatAmount(final String formatAmount) {
        this.formatAmount = formatAmount;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public String getCommercialMessageClient() {
        return this.commercialMessageClient;
    }

    public void setCommercialMessageClient(final String commercialMessageClient) {
        this.commercialMessageClient = commercialMessageClient;
    }

}
