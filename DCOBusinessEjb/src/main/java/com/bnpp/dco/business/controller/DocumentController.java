package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.Document;
import com.bnpp.dco.business.dto.converter.ConverterDocument;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.exception.DCOException;

public final class DocumentController extends GenericController {

    @SuppressWarnings("unchecked")
    public List<DocumentDto> list(final Integer country, final Integer language, final Integer documentType,
            final Integer legalEntity, final String documentTitle, final Integer[] ids, final Boolean resident,
            final Boolean fetchData, final Boolean xbasV2) throws DCOException {

        final StringBuilder query = new StringBuilder(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_LIST));
        boolean and = false;

        if (country != -1) {
            query.append(DatabaseConstants.WHERE_CLAUSE).append(DatabaseConstants.LEFT_BRAQUET)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_COUNTRY_NULL))
                    .append(DatabaseConstants.OR_CLAUSE)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_COUNTRY))
                    .append(DatabaseConstants.RIGHT_BRAQUET);
            and = true;
        }
        if (language != null && language != -1) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(DatabaseConstants.LEFT_BRAQUET)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_LANGUAGE))
                    .append(DatabaseConstants.OR_CLAUSE)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_LANGUAGE_NULL))
                    .append(DatabaseConstants.RIGHT_BRAQUET);
        }
        if (legalEntity != -1) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(DatabaseConstants.LEFT_BRAQUET)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_LEGAL_ENTITY_LANGUAGE))
                    .append(DatabaseConstants.OR_CLAUSE)
                    .append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_LEGAL_ENTITY_NULL))
                    .append(DatabaseConstants.RIGHT_BRAQUET);
        }
        if (ids != null && ids.length != 0 && !(ids.length == 1 && ids[0] == -1)) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_ID));
        }
        if (documentType != -1) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_DOCUMENT_TYPE));
        }
        if (StringUtils.isNotBlank(documentTitle)) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
                and = true;
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_TITLE));
        }
        if (resident != null && resident == true) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_RESIDENT));
        } else if (resident != null && resident == false) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
            }
            query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_NON_RESIDENT));
        }
        
        if (xbasV2 != null) {
            if (and) {
                query.append(DatabaseConstants.AND_CLAUSE);
            } else {
                query.append(DatabaseConstants.WHERE_CLAUSE);
            }
            if(xbasV2) {
            	query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_XBAS));
            } else {
            	query.append(PropertiesHelper.getMessage(DatabaseConstants.DOCUMENT_FILTER_NON_XBAS));
            }
        }

        final Query q = getEntityManager().createQuery(query.toString());

        if (country != -1) {
            q.setParameter("country", country);
        }
        if (language != null && language != -1) {
            q.setParameter("language", language);
        }
        if (legalEntity != -1) {
            q.setParameter("legalEntity", legalEntity);
        }
        if (ids != null && ids.length != 0 && !(ids.length == 1 && ids[0] == -1)) {
            q.setParameter("ids", Arrays.asList(ids));
        }
        if (documentType != -1) {
            q.setParameter("documentType", documentType);
        }
        if (StringUtils.isNotBlank(documentTitle)) {
            q.setParameter("documentTitle", "%" + documentTitle + "%");
        }

        final List<Object> result = q.getResultList();
        final List<DocumentDto> resultDocument = new ArrayList<DocumentDto>();
        for (final Object object : result) {

            if (object instanceof Document) {
                resultDocument.add(ConverterDocument.convertDbToDto((Document) object, fetchData));
            }

        }
        return resultDocument;

    }

    /**
     * Method to update an existing document.
     * @param documentDto document to update.
     * @throws DCOException
     */
    public void modifyDocument(final DocumentDto document) throws DCOException {
        final Document documentDB = ConverterDocument.convertDtoToDb(document);
        documentDB.setUptodate(new Date());
        getEntityManager().merge(documentDB);
    }

    public void saveDocument(final DocumentDto documentDto) throws DCOException {
        final Document document = ConverterDocument.convertDtoToDb(documentDto);

        final Integer country = document.getCountry() == null ? Constants.VAR_NEG_ONE : document.getCountry()
                .getId();

        final Integer language = document.getLanguage() == null ? Constants.VAR_NEG_ONE : document.getLanguage()
                .getId();

        final Integer legalEntity = document.getLegalEntity() == null ? Constants.VAR_NEG_ONE : document
                .getLegalEntity().getId();

        final List<DocumentDto> documentList = list(country, language, document.getDocumentType().getId(),
                legalEntity, document.getTitle(), new Integer[] {Constants.VAR_NEG_ONE}, document.getResident(), false, document.getXbasV2());

        for (final DocumentDto docDto : documentList) {
            getEntityManager().remove(getEntityManager().find(Document.class, docDto.getId()));
        }

        document.setUptodate(new Date());
        getEntityManager().merge(document);
    }

    public void deleteDocument(final Integer id) {
        final Document d = getEntityManager().find(Document.class, id);
        getEntityManager().remove(d);
    }

}