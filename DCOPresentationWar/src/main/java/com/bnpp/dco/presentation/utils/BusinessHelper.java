package com.bnpp.dco.presentation.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.business.ejb.EjbFacadeRemote;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Helper class for business calls.
 */
public final class BusinessHelper {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessHelper.class);

    /** Name of the EJB facade. */
    private static final String FACADE_NAME = "com.bnpp.dco.business.ejb.EjbFacadeRemote";

    /**
     * Constructor.
     */
    private BusinessHelper() {
    }

    /**
     * Static method to call the business.
     * @param controller the controller to use
     * @param action the action to call on the controller
     * @param args the arguments to pass to the action
     * @return the return objects of the method called, if any
     * @throws DCOException a local or business exception
     */
    public static Object call(final String controller, final String action, final Object[] args)
            throws DCOException {
        InitialContext context = null;
        try {
            context = new InitialContext();
            final EjbFacadeRemote bean = (EjbFacadeRemote) context.lookup(FACADE_NAME);
            return bean.execute(controller, action, args);
        } catch (final NamingException e) {
            final String message = "An error happened while contacting the business";
            LOGGER.error(message, e);
            throw new DCOException(message, e);
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (final NamingException e) {
                    LOGGER.warn("Error while trying to close the context", e);
                }
            }
        }
    }
}
