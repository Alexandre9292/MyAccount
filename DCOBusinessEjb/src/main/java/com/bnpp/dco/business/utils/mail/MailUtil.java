package com.bnpp.dco.business.utils.mail;

import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Mail util.
 */
public class MailUtil {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);

    /** Mail messages. */
    private static ResourceBundle messages = ResourceBundle.getBundle("mail");

    /** Session. */
    private Session session;

    /** Singleton instance. */
    private static MailUtil INSTANCE;

    /** Singleton getter. */
    public static final MailUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MailUtil();
        }
        return INSTANCE;
    }

    /**
     * getMessage.
     * @param key as String
     * @return String
     */
    public static String getMessage(final String key) {
        return messages.getString(key);
    }

    /**
     * getMessage.
     * @param key as String
     * @param params as Object[]
     * @return String
     */
    public static String getMessage(final String key, final Object[] params) {
        final String message = getMessage(key);
        return MessageFormat.format(message, params);
    }

    /**
     * Utility function to send an email.
     * @param to the target email to set
     * @param subject the subject to set
     * @param body the body message to set
     * @throws DCOException
     */
    public void sendEmail(final String[] to, final String subject, final String body) throws DCOException {
        final MimeMessage message = new MimeMessage(this.session);
        try {
            message.setFrom(new InternetAddress(this.session.getProperty("mail.from")));
            for (final String t : to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(t));
            }
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);
            Transport.send(message);
        } catch (final MessagingException e) {
            final String msg = "An error occured while sending email";
            LOG.error(msg, e);
            throw new DCOException(msg, e, Constants.EXCEPTION_CODE_EMAIL);
        }
    }

    /**
     * Send a mail to the user after creation.
     * @param to receiver address
     * @param userId user id
     * @param password password
     * @throws DCOException
     */
    public void sendUserCreationConfirmation(final String to, final String userId, final String password)
            throws DCOException {
        final String subject = getMessage("user.creation.confirmation.subject");
        final String body = getMessage("user.creation.confirmation.body", new String[] {userId, password});
        final String signature = getMessage("signature");
        final StringBuilder sbBody = new StringBuilder(body).append(signature);
        sendEmail(new String[] {to}, subject, sbBody.toString());
    }

    /**
     * Send a mail to the user after password reset.
     * @param to receiver address
     * @param userId user id
     * @param password password
     * @throws DCOException
     */
    public void sendUserResetPassword(final String to, final String userId, final String password)
            throws DCOException {
        final String subject = getMessage("user.password.reset.subject");
        final String body = getMessage("user.password.reset.body", new String[] {userId, password});
        final String signature = getMessage("signature");
        final StringBuilder sbBody = new StringBuilder(body).append(signature);
        sendEmail(new String[] {to}, subject, sbBody.toString());
    }

    /**
     * Getter for session.
     * @return the session
     */
    public Session getSession() {
        return this.session;
    }

    /**
     * Setter for session
     * @param session the session to set
     */
    public void setSession(final Session session) {
        this.session = session;
    }
}
