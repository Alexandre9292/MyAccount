package com.bnpp.dco.common.dto;

public class PreferencesDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private LanguageDto language;
    private DateFormatDto dateFormat;
    private String formatAmount = "";

    public PreferencesDto(final LanguageDto language, final DateFormatDto dateFormat) {
        super();
        if (language != null) {
            this.language = language;
        }
        if (dateFormat != null) {
            this.dateFormat = dateFormat;
        }
    }

    public PreferencesDto() {
        super();
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final LanguageDto getLanguage() {
        return this.language;
    }

    public final void setLanguage(final LanguageDto language) {
        this.language = language;
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

}
