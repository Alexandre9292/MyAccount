package com.bnpp.dco.business.ejb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.business.controller.AccountController;
import com.bnpp.dco.business.controller.AccountFormController;
import com.bnpp.dco.business.controller.AccountThirdPartyController;
import com.bnpp.dco.business.controller.AuthorizationThirdPartyController;
import com.bnpp.dco.business.controller.CitizenshipController;
import com.bnpp.dco.business.controller.CollegeController;
import com.bnpp.dco.business.controller.ContactController;
import com.bnpp.dco.business.controller.CountryController;
import com.bnpp.dco.business.controller.DateFormatController;
import com.bnpp.dco.business.controller.DocumentController;
import com.bnpp.dco.business.controller.DocumentTypeController;
import com.bnpp.dco.business.controller.EntitiesController;
import com.bnpp.dco.business.controller.GenericController;
import com.bnpp.dco.business.controller.LanguageController;
import com.bnpp.dco.business.controller.LegalEntityController;
import com.bnpp.dco.business.controller.ParamController;
import com.bnpp.dco.business.controller.RepresentativeController;
import com.bnpp.dco.business.controller.RoleController;
import com.bnpp.dco.business.controller.RulesController;
import com.bnpp.dco.business.controller.SignatoryController;
import com.bnpp.dco.business.controller.StatsController;
import com.bnpp.dco.business.controller.ThirdPartyController;
import com.bnpp.dco.business.controller.UserController;
import com.bnpp.dco.business.utils.mail.MailUtil;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.exception.DCOException;

/**
 * Session Bean implementation class EjbFacade.
 */
@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
public final class EjbFacade implements EjbFacadeRemote {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EjbFacade.class);

    /** Field em. */
    @PersistenceContext
    private final EntityManager em = null;

    /** Field mailSession. */
    @Resource(name = "mail/dco")
    private final Session mailSession = null;

    /** Field mailUtil. */
    private final MailUtil mailUtil = MailUtil.getInstance();

    /** Field controllers. */
    private static final Map<String, GenericController> CONTROLLERS;

    static {
        CONTROLLERS = new HashMap<String, GenericController>();

        final UserController userController = new UserController();
        final AccountController accountController = new AccountController();
        final StatsController statsController = new StatsController();
        userController.setStatsController(statsController);
        accountController.setStatsController(statsController);

        CONTROLLERS.put(Constants.CONTROLLER_USER, userController);
        CONTROLLERS.put(Constants.CONTROLLER_ACCOUNT, accountController);
        CONTROLLERS.put(Constants.CONTROLLER_STATISTICS, statsController);

        CONTROLLERS.put(Constants.CONTROLLER_DOCUMENT, new DocumentController());
        CONTROLLERS.put(Constants.CONTROLLER_LANGAGE, new LanguageController());
        CONTROLLERS.put(Constants.CONTROLLER_COUNTRY, new CountryController());
        CONTROLLERS.put(Constants.CONTROLLER_DATE_FORMAT, new DateFormatController());
        CONTROLLERS.put(Constants.CONTROLLER_DOCUMENT_TYPE, new DocumentTypeController());
        CONTROLLERS.put(Constants.CONTROLLER_ENTITIES, new EntitiesController());
        final ParamController pc = new ParamController();
        pc.setLanguageController((LanguageController) CONTROLLERS.get(Constants.CONTROLLER_LANGAGE));
        CONTROLLERS.put(Constants.CONTROLLER_PARAM, pc);
        CONTROLLERS.put(Constants.CONTROLLER_ROLE, new RoleController());
        CONTROLLERS.put(Constants.CONTROLLER_ACCOUNT_THIRD_PARTY, new AccountThirdPartyController());
        CONTROLLERS.put(Constants.CONTROLLER_ACCOUNT_FORM, new AccountFormController());
        CONTROLLERS.put(Constants.CONTROLLER_THIRD_PARTY, new ThirdPartyController());
        CONTROLLERS.put(Constants.CONTROLLER_LEGAL_ENTITY, new LegalEntityController());
        CONTROLLERS.put(Constants.CONTROLLER_CITIZENSHIP, new CitizenshipController());
        CONTROLLERS.put(Constants.CONTROLLER_AUTHORIZATION_THIRD_PARTY, new AuthorizationThirdPartyController());
        
        // New
        CONTROLLERS.put(Constants.CONTROLLER_CONTACT, new ContactController());
        CONTROLLERS.put(Constants.CONTROLLER_REPRESENTATIVE, new RepresentativeController());
        CONTROLLERS.put(Constants.CONTROLLER_SIGNATORY, new SignatoryController());
        CONTROLLERS.put(Constants.CONTROLLER_COLLEGE, new CollegeController());
        CONTROLLERS.put(Constants.CONTROLLER_RULES, new RulesController());
    }

    /**
     * Default constructor.
     */
    public EjbFacade() {
    }

    @PostConstruct
    public void init() {
        this.mailUtil.setSession(this.mailSession);
        for (final GenericController ctl : CONTROLLERS.values()) {
            ctl.setEntityManager(this.em);
            ctl.setMailUtil(this.mailUtil);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Object execute(final String controller, final String action, final Object[] args) throws DCOException {
        final GenericController ctl = CONTROLLERS.get(controller);
        if (ctl == null) {
            final StringBuilder message = new StringBuilder("The given controller could not be found: ");
            message.append(controller);
            LOGGER.error(message.toString());
            throw new DCOException(message.toString());
        }
        Class<?>[] argTypes = null;
        if (args != null) {
            argTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    argTypes[i] = args[i].getClass();
                }
            }
        }
        final Class<?> ctlClass = ctl.getClass();
        Method method = null;
        // Finding the right method
        for (final Method m : ctlClass.getMethods()) {
            if (m.getName().equals(action)) {
                // The method has the right name
                final Class<?>[] paramTypes = m.getParameterTypes();
                if (argTypes == null && paramTypes.length == 0 || argTypes.length == paramTypes.length) { // NOSONAR
                    // The method has the right number of arguments
                    boolean bInvalidArgType = false;
                    for (int i = 0; i < paramTypes.length; i++) {
                        final Class<?> pt = paramTypes[i];
                        if (argTypes[i] != null && !pt.isAssignableFrom(argTypes[i])) {
                            // We found an invalid argument type
                            bInvalidArgType = true;
                            break;
                        }
                    }
                    if (!bInvalidArgType) {
                        // The method is the right one
                        method = m;
                        break;
                    }
                }
            }
        }
        // We haven't found a matching method
        if (method == null) {
            final StringBuilder message = new StringBuilder(
                    "The given action could not be found in the controller: ");
            message.append(action);
            LOGGER.error(message.toString());
            throw new DCOException(message.toString());
        }
        Object result = null;
        try {
            result = method.invoke(ctl, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            final StringBuilder message = new StringBuilder(
                    "An exception occured when calling the given controller#action: ");
            message.append(controller);
            message.append("#");
            message.append(action);
            LOGGER.error(message.toString(), e);
            if (e instanceof InvocationTargetException && e.getCause() != null
                    && e.getCause() instanceof DCOException) {
                throw (DCOException) e.getCause();
            } else {
                throw new DCOException(message.toString(), e);
            }
        }
        return result;
    }
}
