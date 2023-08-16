package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Form to hold summary information.
 */
public class FormSummaryForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 2563269121632528226L;

	/** Map of languages for each country. */
    private Map<String, List<Locale>> mapCountryLangs = new HashMap<String, List<Locale>>();

    /** Map of selected language for each country. */
    private Map<String, String> mapCountrySelectedLang = new HashMap<String, String>();

    /**
     * Getter for mapCountryLangs.
     * @return the mapCountryLangs
     */
    public Map<String, List<Locale>> getMapCountryLangs() {
        return this.mapCountryLangs;
    }

    /**
     * Setter for mapCountryLangs.
     * @param mapCountryLangs the mapCountryLangs to set
     */
    public void setMapCountryLangs(final Map<String, List<Locale>> mapCountryLangs) {
        this.mapCountryLangs = mapCountryLangs;
    }

    /**
     * Getter for mapCountrySelectedLang.
     * @return the mapCountrySelectedLang
     */
    public Map<String, String> getMapCountrySelectedLang() {
        return this.mapCountrySelectedLang;
    }

    /**
     * Setter for mapCountrySelectedLang.
     * @param mapCountrySelectedLang the mapCountrySelectedLang to set
     */
    public void setMapCountrySelectedLang(final Map<String, String> mapCountrySelectedLang) {
        this.mapCountrySelectedLang = mapCountrySelectedLang;
    }
}
