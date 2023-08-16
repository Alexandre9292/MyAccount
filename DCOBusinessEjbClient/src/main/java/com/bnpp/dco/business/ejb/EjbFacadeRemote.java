package com.bnpp.dco.business.ejb;

import com.bnpp.dco.common.exception.DCOException;

/**
 * Interface to call business server.
 * @author 643648
 */
public interface EjbFacadeRemote {

    /**
     * Execute the given method of the given controller with the given arguments.
     * @param controller the controller to use
     * @param action the action to call on the controller
     * @param args the arguments to pass to the action
     * @return the return values of the action, if any
     * @throws DCOException a DCOException with detailed message
     */
    Object execute(final String controller, final String action, final Object[] args) throws DCOException;
}
