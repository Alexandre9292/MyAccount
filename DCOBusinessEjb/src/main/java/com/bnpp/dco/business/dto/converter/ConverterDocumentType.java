package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.DocumentType;
import com.bnpp.dco.common.dto.DocumentTypeDto;

public final class ConverterDocumentType {
    public static DocumentTypeDto convertDbToDto(final DocumentType db) {
        DocumentTypeDto result = null;
        if (db != null) {
            result = new DocumentTypeDto();
            result.setId(db.getId());
            result.setLabel(db.getLabel());
        }
        return result;
    }

    public static DocumentType convertDtoToDb(final DocumentTypeDto dto) {
        DocumentType documentType = null;
        if (dto != null) {
            documentType = new DocumentType();
            documentType.setId(dto.getId());
            documentType.setLabel(dto.getLabel());
        }
        return documentType;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterDocumentType() {
    }

}
