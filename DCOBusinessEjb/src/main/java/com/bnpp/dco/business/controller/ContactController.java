package com.bnpp.dco.business.controller;

import java.util.HashSet;
import java.util.Set;

import com.bnpp.dco.business.dto.Contact;
import com.bnpp.dco.business.dto.Entities;
import com.bnpp.dco.business.dto.converter.ConverterContact;
import com.bnpp.dco.common.dto.ContactDto;
import com.bnpp.dco.common.exception.DCOException;

public class ContactController extends GenericController{

	
	public void saveContactFromDto(final ContactDto contactDto)
            throws DCOException {
        final Contact contact = ConverterContact
                .convertDtoToDb(contactDto, false);

        Set<Entities> entityList = contact.getEntity();
        if (contact.getEntity() == null || contact.getEntity().isEmpty()) {
        	entityList = new HashSet<Entities>();
        }
        contact.setEntity(entityList);
        getEntityManager().merge(contact);
    }
	
}
