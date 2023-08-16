package com.bnpp.dco.presentation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.dto.UserTableDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.form.ClientDataFilterForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class ClientDataController extends GenericController {

    /**
     * Logger
     */
    private final Logger LOG = LoggerFactory.getLogger(ClientDataController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private ClientDataFilterForm clientDataFilterForm;

    @ModelAttribute(WebConstants.CLIENT_DATA_FILTER_FORM)
    public ClientDataFilterForm getClientDataFilterForm() {
        return this.clientDataFilterForm;
    }

    /**
     * Main Client Data Filter page.
     * @return all the clients and all the entities, in the form.
     */
    @RequestMapping(value = WebConstants.CLIENT_DATA_LOAD, method = RequestMethod.GET)
    public final String getClientDataFilterLoad(
            @ModelAttribute(WebConstants.CLIENT_DATA_FILTER_FORM) final ClientDataFilterForm form) {
        try {

            form.setEntityClient(null);
            form.setAccountEntity(null);
            form.setEntity(Constants.EMPTY_FIELD);
            form.setLogin(Constants.EMPTY_FIELD);

            final List<String> entitiesList = (List<String>) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                    Constants.CONTROLLER_ENTITIES_LABEL_LIST, new Object[] {});

            // Fill the AjaxDto object with only all the entities's label.
            final List<AjaxDto> entitiesAjaxDtoList = new ArrayList<AjaxDto>();
            for (final String entityLabel : entitiesList) {
                entitiesAjaxDtoList.add(new AjaxDto(entityLabel));
            }
            Collections.sort(entitiesAjaxDtoList, new AjaxDto.OrderByLabel());
            form.setEntitiesLabelList(entitiesAjaxDtoList);

            final List<UserTableDto> clientsList = (List<UserTableDto>) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_CLIENT_LIST, new Object[] {Constants.EMPTY_FIELD,
                            Constants.EMPTY_FIELD, form.getLogin(), form.getEntity()});

            // Fill the AjaxDto object with only all the users's id + users's login.
            final List<AjaxDto> clientsAjaxDtoList = new ArrayList<AjaxDto>();
            for (final UserTableDto userDto : clientsList) {
                clientsAjaxDtoList.add(new AjaxDto(userDto.getId(), Constants.EMPTY_FIELD, userDto.getLogin()));
            }
            Collections.sort(clientsAjaxDtoList, new AjaxDto.OrderByLogin());
            form.setClients(clientsAjaxDtoList);

            form.setExclusionUserEntities(getUserEntitiesExclusion());

        } catch (final DCOException e) {
            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

        return WebConstants.CLIENT_DATA;
    }

    /**
     * Main Client Data page.
     * @return the form with the corresponding entity / account / thirds, matching with the selected client or/and
     *         entity.
     */
    @RequestMapping(value = WebConstants.CLIENT_DATA_FILTER_CONTROLLER, method = RequestMethod.POST)
    public final String getClientDataLoad(
            @ModelAttribute(WebConstants.CLIENT_DATA_FILTER_FORM) final ClientDataFilterForm form) {
        try {

            form.setEntities(null);
            form.setEntityClient(null);
            form.setAccountEntity(null);
            List<EntityDto> entityDtoList = null;

            if (StringUtils.isNotBlank(form.getLogin())) {
                entityDtoList = getEntitiesByUserLogin(form.getLogin());
            } else if (StringUtils.isNotBlank(form.getEntity())) {
                entityDtoList = doGetEntitiesDtoByLabel(form.getEntity());
            } else {
                addWarning(this.propertiesHelper.getMessage("page.client.data.no.filter"));
            }

            if (StringUtils.isNotBlank(form.getLogin()) || StringUtils.isNotBlank(form.getEntity())) {
                if (entityDtoList == null) {
                    addWarning(this.propertiesHelper.getMessage("page.client.data.no.entity"));
                } else {
                    form.setEntities(entityDtoList);
                }
            }
        } catch (final DCOException e) {

            this.LOG.error(e.getMessage());
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }
        return WebConstants.CLIENT_DATA;
    }

    /**
     * Fetch the entities associated with the user.
     * @param login
     * @return
     * @throws DCOException
     */
    private List<EntityDto> getEntitiesByUserLogin(final String login) throws DCOException {
        return (List<EntityDto>) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                Constants.CONTROLLER_ENTITIES_ALL_GET_BY_USER_LOGIN, new Object[] {login});
    }

    /**
     * Fetch the entities corresponding with the label.
     * @param login
     * @return
     * @throws DCOException
     */
    private List<EntityDto> doGetEntitiesDtoByLabel(final String label) throws DCOException {
        return (List<EntityDto>) BusinessHelper.call(Constants.CONTROLLER_ENTITIES,
                Constants.CONTROLLER_ENTITIES_GET_BY_LABEL, new Object[] {label});
    }

    private List<AjaxDto> getUserEntitiesExclusion() throws DCOException {
        return (List<AjaxDto>) BusinessHelper.call(Constants.CONTROLLER_USER,
                Constants.CONTROLLER_USER_ENTITIES_EXCLUSION, new Object[] {});
    }

    @RequestMapping(value = "getEntityFromUser", method = RequestMethod.GET)
    public final @ResponseBody
    List<AjaxDto> getEntityFromUser(final Integer id) throws DCOException {

        List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();

        if (id.intValue() == -1) {
            ajaxList = getClientDataFilterForm().getEntitiesLabelList();
        } else {
            for (final AjaxDto ajaxDto : getClientDataFilterForm().getExclusionUserEntities()) {
                if (ajaxDto.getId().compareTo(id) == 0) {
                    ajaxList.add(ajaxDto);
                }
            }
        }
        return ajaxList;
    }

    @RequestMapping(value = "getUserFromEntity", method = RequestMethod.GET)
    public final @ResponseBody
    List<AjaxDto> getUserFromEntity(final String label) throws DCOException {

        List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();

        if (StringUtils.isEmpty(label)) {
            ajaxList = getClientDataFilterForm().getClients();
        } else {
            for (final AjaxDto ajaxDto : getClientDataFilterForm().getExclusionUserEntities()) {
                if (ajaxDto.getLabel().equals(label)) {
                    ajaxList.add(ajaxDto);
                }
            }
        }
        return ajaxList;
    }

}
