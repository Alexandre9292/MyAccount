package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.converter.ConverterEntity;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.exception.DCOException;

public final class EntitiesController extends GenericController {

    /**
     * LOG
     */
    private final Logger LOG = LoggerFactory.getLogger(EntitiesController.class);

    /**
     * Return the first occurrence of an user's entityDto.
     * @param login
     * @return
     * @throws DCOException
     */
    public EntityDto getEntityByUserLogin(final String login) throws DCOException {

        return ConverterEntity.convertDbToDto(doGetEntityByUserLogin(login), true);
    }

    /**
     * Get the first occurrence of an user's entity
     * @param login
     * @return
     * @throws DCOException
     */
    private Entities doGetEntityByUserLogin(final String login) throws DCOException {

        Entities entities = null;
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.USER_ENTITIES));
        q.setParameter("login", login);
        final List<?> listResult = q.getResultList();

        if (!listResult.isEmpty() && listResult.get(0) instanceof Entities) {
            entities = (Entities) listResult.get(0);
        } else {
            final String message = "The user does not has entities";
            this.LOG.error(message);
        }

        return entities;
    }
    
    /**
     * Return entity corresponding to id.
     * @param entityId
     * @return
     * @throws DCOException
     */
    public EntityDto getEntityById(final Integer entityId) throws DCOException {
        return ConverterEntity.convertDbToDto(doGetEntityById(entityId), true);
    }
    
    /**
     * Get the first occurrence of an user's entity
     * @param login
     * @return
     * @throws DCOException
     */
    private Entities doGetEntityById(final Integer entityId) throws DCOException {
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.ENTITY_BY_ID));
        q.setParameter("entityId", entityId);
        
        Entities entity = (Entities) q.getSingleResult();
        
        if (entity != null) {
        	
        } else {
            final String message = "Unknown entity ID";
            this.LOG.error(message);
        }
        return entity;
    }

    /**
     * Return all the user's entitiesDto.
     * @param login
     * @return
     * @throws DCOException
     */
    public List<EntityDto> getEntitiesByUserLogin(final String login) throws DCOException {
        List<EntityDto> entitiesDto = null;
        final List<Entities> entities = doGetEntitiesByUserLogin(login);
        if (entities != null) {
            entitiesDto = new ArrayList<EntityDto>();
            for (final Entities e : entities) {
                entitiesDto.add(ConverterEntity.convertDbToDto(e, true));
            }
        }
        return entitiesDto;
    }

    /**
     * Get all the user's entities, find by user's login, gave in parameter..
     * @param login
     * @return
     * @throws DCOException
     */
    private List<Entities> doGetEntitiesByUserLogin(final String login) throws DCOException {

        List<Entities> entities = null;
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.USER_ENTITIES));
        q.setParameter("login", login);
        final List<?> listResult = q.getResultList();

        if (!listResult.isEmpty()) {
            entities = new ArrayList<Entities>();
            for (final Object o : listResult) {
                if (o instanceof Entities) {
                    entities.add((Entities) o);
                }
            }
        } else {
            final String message = "The user does not has entities";
            this.LOG.error(message);
            // throw new DCOException(message, Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES);
        }

        return entities;
    }

    /**
     * Get all the entities matching with the corresponding label.
     * @param label
     * @return
     * @throws DCOException
     */
    public List<EntityDto> doGetEntityDtoByLabel(final String label) throws DCOException {

        final List<EntityDto> entityDto = new ArrayList<EntityDto>();
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.ENTITIES_LABEL));
        q.setParameter("label", label);
        final List<Object> result = q.getResultList();

        for (final Object o : result) {
            if (o instanceof Entities) {
                entityDto.add(ConverterEntity.convertDbToDto((Entities) o, true));
            }
        }

        return entityDto;
    }

    /**
     * Get all the distinct entities's name.
     * @return
     * @throws DCOException
     */
    public List<String> listLabel() throws DCOException {

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.ENTITIES_LABEL_LIST));

        final List<?> listResult = q.getResultList();
        final List<String> resultEntities = new ArrayList<String>();

        for (final Object object : listResult) {
            if (object instanceof String && !resultEntities.contains(object)) {
                resultEntities.add((String) object);
            }
        }

        return resultEntities;

    }

    /**
     * Update an entity.
     * @param entityDto
     * @return
     * @throws DCOException
     * @throws NamingException
     */
    public EntityDto updateEntities(final EntityDto entityDto) throws DCOException, NamingException {
        Entities entity = null;
        Entities returnEntity = null;

        if (entityDto != null) {

            entity = ConverterEntity.convertDtoToDb(entityDto, true);

            if (entity == null) {
                throw new DCOException("The user doesn't have entity.",
                        Constants.EXCEPTION_CODE_USER_DONT_HAVE_ENTITIES);
            }

            if (entity.getAddressByAddress() != null) {
                getEntityManager().merge(entity.getAddressByAddress());
            }
            if (entity.getAddressByIdAddressMailing() != null) {
                getEntityManager().merge(entity.getAddressByIdAddressMailing());
            }
            if (entity.getContact1() != null) {
                getEntityManager().merge(entity.getContact1());
            }
            if (entity.getContact2() != null) {
                getEntityManager().merge(entity.getContact2());
            }
            
            returnEntity = getEntityManager().merge(entity);
            getEntityManager().flush();
        }
        

        return ConverterEntity.convertDbToDto(returnEntity, true);
    }
    
    /**
     * Update an entity from client management.
     * @param entityDto
     * @throws DCOException
     */
    public void updateEntitiesFromClientManagement(final EntityDto entityDto) throws DCOException {
        Entities entity = ConverterEntity.convertDtoToDb(entityDto, true);
        final Entities entityDB = getEntityManager().find(Entities.class, entity.getId());
        entityDB.setBankContact(entity.getBankContact());
        getEntityManager().merge(entityDB);
    }
    
}
