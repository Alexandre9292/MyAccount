package com.bnpp.dco.business.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bnpp.dco.business.utils.mail.MailUtil;

/**
 * Generic controller for all controllers.
 * @param <E>
 */
public class GenericController {
    /** Field entityManager. */
    private EntityManager entityManager = null;
    /** Field mailUtil. */
    private MailUtil mailUtil = null;

    /**
     * Getter for entityManager.
     * @return the entityManager
     */
    public final EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * Setter for entityManager.
     * @param newEntityManager the entityManager to set
     */
    public final void setEntityManager(final EntityManager newEntityManager) {
        this.entityManager = newEntityManager;
    }

    /**
     * Getter for mailUtil.
     * @return the mailUtil
     */
    public MailUtil getMailUtil() {
        return this.mailUtil;
    }

    /**
     * Setter for mailUtil.
     * @param newMailUtil the mailUtil to set
     */
    public void setMailUtil(final MailUtil newMailUtil) {
        this.mailUtil = newMailUtil;
    }

    /**
     * Gets a single result, if any, from a given query.
     * @param q the query to use
     * @return the single object, if any
     */
    protected final Object getSingleResult(final Query q) {
        Object obj = null;
        final List<Object> results = q.getResultList();
        if (results != null && results.size() != 0) {
            obj = results.get(0);
        }
        return obj;
    }
}
