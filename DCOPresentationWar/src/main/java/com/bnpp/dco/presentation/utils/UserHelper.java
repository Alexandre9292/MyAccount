package com.bnpp.dco.presentation.utils;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.bean.UserSession;

/**
 * Helper class for user.
 */
public final class UserHelper {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(UserHelper.class);

    /** Locale cookie name. */
    private static final String COOKIE_LOCALE_NAME = "dco-locale";

    /** Cookie disclaimer name. */
    private static final String COOKIE_DISCLAIMER_NAME = "dco-cookieDisclaimer";

    /** Cookie max age. */
    private static final int COOKIE_MAX_AGE = 50 * 365 * 24 * 60 * 60;

    public static UserSession getUserInSession() {
        UserSession result = null;
        final Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof UserSession) {
            result = (UserSession) o;
        }
        return result;
    }

    public static void putUserInSession(final UserSession user) {
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void changeLocale(final HttpServletRequest request, final HttpServletResponse response,
            final SessionLocaleResolver localeResolver) {
        final UserSession user = UserHelper.getUserInSession();
        if (user != null && user.getPreferences() != null) {
            final Locale locale = user.getPreferences().getLocale();
            localeResolver.setLocale(request, null, locale);
            try {
                // Checking if the user accepts DCO cookies
                boolean cookiesAccepted = false;
                final Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (final Cookie c : cookies) {
                        if (COOKIE_DISCLAIMER_NAME.equals(c.getName())) {
                            cookiesAccepted = true;
                            break;
                        }
                    }
                }
                if (cookiesAccepted) {
                    final Cookie cookie = new Cookie(COOKIE_LOCALE_NAME, LocaleUtil.languageToString(locale));
                    cookie.setPath(request.getContextPath());
                    cookie.setMaxAge(COOKIE_MAX_AGE);
                    response.addCookie(cookie);
                }
            } catch (final DCOException e) {
                LOG.error("Error while adding the language cookie to the response. Proceeding...");
                // It shouldn't be blocking, so we proceed without propagating the exception
            }
        }
    }

    public static void initLocaleFromCookie(final HttpServletRequest request,
            final SessionLocaleResolver localeResolver) {
        try {
            final Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (final Cookie c : cookies) {
                    if (COOKIE_LOCALE_NAME.equals(c.getName())) {
                        localeResolver.setLocale(request, null, LocaleUtil.stringToLanguage(c.getValue()));
                        break;
                    }
                }
            }
        } catch (final DCOException e) {
            LOG.error("Error while setting the language from the cookie. Proceeding...");
            // It shouldn't be blocking, so we proceed without propagating the exception
        }
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private UserHelper() {
    }

}
