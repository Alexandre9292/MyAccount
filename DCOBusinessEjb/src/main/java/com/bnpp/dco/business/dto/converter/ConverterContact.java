package com.bnpp.dco.business.dto.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bnpp.dco.business.dto.Contact;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.common.dto.ContactDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.exception.DCOException;

public class ConverterContact {

	public static ContactDto convertDbToDto(final Contact db, final boolean convertSubObject) throws DCOException {
		ContactDto result = null;
        if (db != null) {
            result = new ContactDto();
            result.setId(db.getId());
            result.setName(db.getName());
            result.setFirstname(db.getFirstname());
            result.setPositionName(db.getPositionName());
            result.setMail(db.getMail());
            result.setTel(db.getTel());
            result.setFax(db.getFax());
            
            if(convertSubObject && db.getEntity() != null){
            	final List<EntityDto> entityList = new ArrayList<EntityDto>();
                for (final Entities entity : db.getEntity()) {
                	entityList.add(ConverterEntity.convertDbToDto(entity, false));
                }
                result.setEntityList(entityList);
            }
            
        }
        return result;
    }
	
	public static Contact convertDtoToDb(final ContactDto dto, final boolean convertSubObject) throws DCOException {
		Contact contact = null;
        if (dto != null) {
        	contact = new Contact();
        	contact.setId(dto.getId());
        	contact.setName(dto.getName());
        	contact.setFirstname(dto.getFirstname());
        	contact.setPositionName(dto.getPositionName());
        	contact.setMail(dto.getMail());
        	contact.setTel(dto.getTel());
        	contact.setFax(dto.getFax());
        	
        	if (convertSubObject && dto.getEntityList() != null) {
                final Set<Entities> entityList = new HashSet<Entities>();
                for (final EntityDto EntityL : dto.getEntityList()) {
                	entityList.add(ConverterEntity.convertDtoToDb(EntityL, false));
                }
                contact.setEntity(entityList);
            }
        }

        return contact;
    }
	
}
