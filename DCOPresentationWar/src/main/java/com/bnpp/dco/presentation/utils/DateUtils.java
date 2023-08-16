package com.bnpp.dco.presentation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    /**
     * Return the parsed given date, according to the user's preferences, or null if an error occurs.
     * @return
     */
    public static Date validate(final String input) {
        Date result = null;
        try {
        	if(UserHelper.getUserInSession().getPreferences() == null){
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                final Date parsedDate = sdf.parse(input);
                final int parsedDateYear = parsedDate.getYear() + 1900;
                if (0 < parsedDateYear && parsedDateYear < 9999) {
                    result = parsedDate;
                }
        	} else if(UserHelper.getUserInSession().getPreferences().getDateFormat() == null){
        		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                final Date parsedDate = sdf.parse(input);
                final int parsedDateYear = parsedDate.getYear() + 1900;
                if (0 < parsedDateYear && parsedDateYear < 9999) {
                    result = parsedDate;
                }
        	} else{
        		final String formatDateUser = UserHelper.getUserInSession().getPreferences().getDateFormat()
                        .getLabel();
                final SimpleDateFormat sdf = new SimpleDateFormat(formatDateUser);
                sdf.setLenient(false);
                final Date parsedDate = sdf.parse(input);
                final int parsedDateYear = parsedDate.getYear() + 1900;
                if (0 < parsedDateYear && parsedDateYear < 9999) {
                    result = parsedDate;
                }
        	}
        } catch (final ParseException e) {
            LOG.warn(e.getMessage());
        }
        return result;
    }

    /**
     * transform date object in string with date format setted by user preferences.
     * @param date : date object.
     * @return date in string.
     */
    public static String date2String(final Date date) {
        String result = null;
        if (date != null) {
            result = new SimpleDateFormat(UserHelper.getUserInSession().getPreferences().getDateFormat()
                    .getLabel()).format(date);
        }
        return result;
    }
}
