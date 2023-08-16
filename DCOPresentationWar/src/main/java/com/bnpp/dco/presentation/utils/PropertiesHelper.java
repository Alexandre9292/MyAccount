package com.bnpp.dco.presentation.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Class which is dealing with properties files.
 * @author 643648
 */
@Component
public class PropertiesHelper {

    /** Field messageSource. */
    @Autowired
    private ResourceBundleMessageSource messageSource;

    /**
     * Method to get the associate string to the key.
     * @param key value we want.
     * @return the value associated to the key
     */
    public String getMessage(final String key) {
        return getMessage(key, null);
    }

    /**
     * Method to get the associate string to the key with the given parameters.
     * @param key value we want.
     * @param args the arguments to use.
     * @return the value associated to the key
     */
    public String getMessage(final String key, final Object[] args) {
        String result;
        if (UserHelper.getUserInSession() == null || UserHelper.getUserInSession().getPreferences() == null) {
            result = this.messageSource.getMessage(key, args, Locale.ENGLISH);
        } else {
            result = this.messageSource.getMessage(key, args, UserHelper.getUserInSession().getPreferences()
                    .getLocale());
        }
        return result;
    }
}
