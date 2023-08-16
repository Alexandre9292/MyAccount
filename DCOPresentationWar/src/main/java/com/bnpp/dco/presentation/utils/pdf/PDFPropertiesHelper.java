package com.bnpp.dco.presentation.utils.pdf;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Class which is dealing with properties files.
 * @author 643648
 */
@Component
public class PDFPropertiesHelper {

    /** Field messageSource. */
    @Autowired
    private ResourceBundleMessageSource messageSource;

    /**
     * Method to get the associate string to the key.
     * @param key value we want.
     * @param locale the locale to use.
     * @return the value associated to the key
     */
    public String getMessage(final String key, final Locale locale) {
        return getMessage(key, null, locale);
    }

    /**
     * Method to get the associate string to the key with the given parameters.
     * @param key value we want.
     * @param args the arguments to use.
     * @param locale the locale to use.
     * @return the value associated to the key
     */
    public String getMessage(final String key, final Object[] args, final Locale locale) {
        return this.messageSource.getMessage(key, args, locale);
    }
}
