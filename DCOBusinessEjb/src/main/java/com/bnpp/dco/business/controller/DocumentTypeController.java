package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.DocumentType;
import com.bnpp.dco.business.dto.converter.ConverterDocumentType;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.DocumentTypeDto;

public class DocumentTypeController extends GenericController {

    public List<DocumentTypeDto> list() {

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_TYPE_LIST));

        final List<?> result = q.getResultList();
        final List<DocumentTypeDto> resultDocumentType = new ArrayList<DocumentTypeDto>();
        for (final Object object : result) {
            if (object instanceof DocumentType) {
                resultDocumentType.add(ConverterDocumentType.convertDbToDto((DocumentType) object));
            }
        }
        return resultDocumentType;
    }

}
