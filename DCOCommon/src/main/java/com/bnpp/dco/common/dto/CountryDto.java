package com.bnpp.dco.common.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id = -1;
    private Locale locale;
    private LegalEntityDto legalEntity;
    private List<LanguageDto> languages;
    private boolean com_lang_enabled = false;
    private List<RepresentativeDto> representatives;

    public CountryDto() {
    }

    public CountryDto(final Integer id) {
        super();
        this.id = id;
    }
    
    public static class CountryComparatorByName implements Comparator<CountryDto> {
        @Override
        public int compare(final CountryDto o1, final CountryDto o2) {
            return o1.locale.getDisplayName().compareTo(o2.locale.getDisplayName());
        }
    }
    

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final Locale getLocale() {
        return this.locale;
    }

    public final void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public LegalEntityDto getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(final LegalEntityDto legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<LanguageDto> getLanguages() {
        return this.languages;
    }

    public void setLanguages(final List<LanguageDto> languages) {
        this.languages = languages;
    }

    /**
     * @return the com_lang_enabled
     */
    public boolean isCom_lang_enabled() {
        return this.com_lang_enabled;
    }

    /**
     * @param com_lang_enabled the com_lang_enabled to set
     */
    public void setCom_lang_enabled(final boolean com_lang_enabled) {
        this.com_lang_enabled = com_lang_enabled;
    }

	/**
	 * @return the representatives
	 */
	public List<RepresentativeDto> getRepresentatives() {
		return representatives;
	}

	/**
	 * @param representatives the representatives to set
	 */
	public void setRepresentatives(List<RepresentativeDto> representatives) {
		this.representatives = representatives;
	}
}
