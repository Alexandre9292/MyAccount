package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Document;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.exception.DCOException;

public final class ConverterDocument {
    public static DocumentDto convertDbToDto(final Document db, final boolean fetchData) throws DCOException {
        DocumentDto result = null;
        if (db != null) {
            result = new DocumentDto();
            result.setId(db.getId());
            result.setCountry(ConverterCountry.convertDbToDto(db.getCountry(), false));
            result.setLanguage(ConverterLanguage.convertDbToDto(db.getLanguage(), false));
            result.setDocumentType(ConverterDocumentType.convertDbToDto(db.getDocumentType()));
            result.setTitle(db.getTitle());
            result.setLegalEntity(ConverterLegalEntity.convertDbToDto(db.getLegalEntity()));
            result.setUptodate(db.getUptodate());
            result.setResident(db.getResident());
            result.setXbasV2(db.getXbasV2());
            if (fetchData) {
                result.setData(db.getData());
            }
        }
        return result;
    }

    public static Document convertDtoToDb(final DocumentDto dto) throws DCOException {
        Document document = null;
        if (dto != null) {
            document = new Document();
            document.setCountry(ConverterCountry.convertDtoToDb(dto.getCountry(), false));
            document.setDocumentType(ConverterDocumentType.convertDtoToDb(dto.getDocumentType()));
            document.setLanguage(ConverterLanguage.convertDtoToDb(dto.getLanguage(), false));
            document.setTitle(dto.getTitle());
            document.setData(dto.getData());
            document.setId(dto.getId());
            document.setLegalEntity(ConverterLegalEntity.convertDtoToDb(dto.getLegalEntity()));
            document.setUptodate(dto.getUptodate());
            document.setResident(dto.getResident());
            document.setXbasV2(dto.getXbasV2());
        }

        return document;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterDocument() {
    }

}
