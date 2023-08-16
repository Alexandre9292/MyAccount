package com.bnpp.dco.business.utils;

import java.util.ResourceBundle;

/**
 * Class which is dealing with properties files.
 */
public final class PropertiesHelper {

    /** Properties files. */
    private static final java.util.ResourceBundle PROPS;
    static {
        PROPS = ResourceBundle.getBundle("databaseResources");
    }

    /**
     * Constructeur de la classe.
     */
    private PropertiesHelper() {
    }

    /**
     * Gets the message associated to the given key.
     * @param key the message key to find
     * @return the message associated to the given key
     */
    public static String getMessage(final String key) {
        return PROPS.getString(key);
    }
}
