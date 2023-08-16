package com.bnpp.dco.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Password util.
 */
public final class PasswordUtil {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(PasswordUtil.class);
    public static final String SPECIAL_CHARS = "+-*/!@#$%^&*();,:^?@#{}&[]=";

    /**
     * Utility method to encode a password
     * @param password the password to use
     * @return the encoded password
     * @throws DCOException
     */
    public static final String encode(final String password) throws DCOException {
        final StringBuffer result = new StringBuffer();
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] mdbytes = md.digest(password.getBytes());
            for (final byte mdbyte : mdbytes) {
                result.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (final NoSuchAlgorithmException e) {
            final String message = "Error while encoding password";
            LOG.error(message, e);
            throw new DCOException(message, e, Constants.EXCEPTION_CODE_USER_CREATE_PASSWORD);
        }
        return result.toString();
    }

    /**
     * Unique method to generate a random new password.
     * @param length as int for length of generated password (minus 2 for standard constraints).
     * @return a generated password.
     */
    public static final String createPassword(final int length) {

        final StringBuilder generatedPwd = new StringBuilder();
        // generate AlphaNum chars
        generatedPwd.append(RandomStringUtils.randomAlphanumeric(length - 4));
        // generate at least one Uppercase Alpha char
        generatedPwd.append(RandomStringUtils.randomAlphanumeric(1).toUpperCase());
        // generate at least on Lowercase Alpha char
        generatedPwd.append(RandomStringUtils.randomAlphanumeric(1).toLowerCase());
        // generate at least one number
        generatedPwd.append(RandomStringUtils.randomNumeric(1));
        // generate at least one special char
        generatedPwd.append(RandomStringUtils.random(1, SPECIAL_CHARS));
        // shuffle loop to random previous concat
        final List<String> charsOfPwd = Arrays.asList(generatedPwd.toString().split(""));
        Collections.shuffle(charsOfPwd);

        final StringBuilder pwdShuffled = new StringBuilder();
        for (final String sign : charsOfPwd) {
            pwdShuffled.append(sign);
        }
        return pwdShuffled.toString();
    }

    /**
     * Private constructor to protect utils class.
     */
    private PasswordUtil() {
    }

}
