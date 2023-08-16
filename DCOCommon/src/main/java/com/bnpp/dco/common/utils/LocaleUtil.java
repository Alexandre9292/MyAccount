package com.bnpp.dco.common.utils;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.bnpp.dco.common.exception.DCOException;

/**
 * Locale util.
 */
public final class LocaleUtil {

    /**
     * Utility method to parse database string language to locale object.
     * @param string the String to use
     * @return the Locale
     * @throws DCOException
     */
    public static final Locale stringToLanguage(final String string) throws DCOException {
        Locale result = null;
        if (StringUtils.isNotBlank(string)) {
            result = new Locale(string, "");
        }
        return result;
    }

    /**
     * Utility method to convert locale to database language.
     * @param locale the Locale to use
     * @return the resulting string
     * @throws DCOException
     */
    public static final String languageToString(final Locale locale) throws DCOException {
        String result = null;
        if (locale != null) {
            result = locale.getLanguage();
        }
        return result;
    }

    /**
     * Utility method to parse database string country to locale object.
     * @param string the String to use
     * @return the Locale
     * @throws DCOException
     */
    public static final Locale stringToCountry(final String string) throws DCOException {
        Locale result = null;
        if (StringUtils.isNotBlank(string)) {
            result = new Locale("", string);
        }
        return result;
    }

    /**
     * Utility method to convert locale to database country.
     * @param locale the Locale to use
     * @return the resulting string
     * @throws DCOException
     */
    public static final String countryToString(final Locale locale) throws DCOException {
        String result = null;
        if (locale != null) {
            result = locale.getCountry();
        }
        return result;
    }

    /**
     * Private constructor to protect utils class.
     */
    private LocaleUtil() {
    }

}
