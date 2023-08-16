package com.bnpp.dco.common.dto;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.bnpp.dco.common.constant.Constants;

public class DocumentDto implements java.io.Serializable {

    private static final long serialVersionUID = 5170888649871193368L;
    private Integer id;
    private DocumentTypeDto documentType;
    private byte[] data;
    private LanguageDto language;
    private CountryDto country;
    private LegalEntityDto legalEntity;
    private String title;
    private String titleWithOutExtension;
    private String titleExtension;
    private Date uptodate;
    private Boolean resident;
    private Boolean xbasV2;

    public DocumentDto() {
        super();
    }

    public DocumentDto(final DocumentTypeDto documentType, final byte[] dataFlow, final LanguageDto language,
            final CountryDto country, final LegalEntityDto legalEntity, final String title, final Boolean resident) {
        super();
        this.documentType = documentType;
        if (dataFlow == null) {
            this.data = new byte[0];
        } else {
            this.data = Arrays.copyOf(dataFlow, dataFlow.length);
        }
        this.language = language;
        this.country = country;
        this.legalEntity = legalEntity;
        this.title = title;
        this.titleWithOutExtension = StringUtils.substringBeforeLast(title, Constants.FILEEXT_SEPARATOR);
        this.titleExtension = StringUtils.substringAfterLast(title, Constants.FILEEXT_SEPARATOR);
        this.resident = resident;
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final DocumentTypeDto getDocumentType() {
        return this.documentType;
    }

    public final void setDocumentType(final DocumentTypeDto documentType) {
        this.documentType = documentType;
    }

    public final byte[] getData() {
        return this.data;
    }

    public final void setData(final byte[] dataFlow) {
        if (dataFlow == null) {
            this.data = new byte[0];
        } else {
            this.data = Arrays.copyOf(dataFlow, dataFlow.length);
        }
    }

    public final LanguageDto getLanguage() {
        return this.language;
    }

    public final void setLanguage(final LanguageDto language) {
        this.language = language;
    }

    public final CountryDto getCountry() {
        return this.country;
    }

    public final void setCountry(final CountryDto country) {
        this.country = country;
    }

    public final String getTitle() {
        return this.title;
    }

    public String getTitleWithOutExtension() {
        final String toSub = this.title;
        return StringUtils.substringBeforeLast(toSub, Constants.FILEEXT_SEPARATOR);
    }

    public final void setTitleWithOutExtension(final String titleWithOutExtension) {
        this.titleWithOutExtension = titleWithOutExtension;
    }

    public String getTitleExtension() {
        final String toSub = this.title;
        return StringUtils.substringAfterLast(toSub, Constants.FILEEXT_SEPARATOR);
    }

    public final void setTitleExtension(final String titleExtension) {
        this.titleExtension = titleExtension;
    }

    public final void setTitle(final String title) {
        this.title = title;
    }

    public final LegalEntityDto getLegalEntity() {
        return this.legalEntity;
    }

    public final void setLegalEntity(final LegalEntityDto legalEntity) {
        this.legalEntity = legalEntity;
    }

    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getUptodate() {
        return this.uptodate;
    }

    public void setUptodate(final Date uptodate) {
        this.uptodate = uptodate;
    }

    public Boolean getResident() {
        return this.resident;
    }

    public void setResident(final Boolean resident) {
        this.resident = resident;
    }

	public final Boolean getXbasV2() {
		return xbasV2;
	}

	public final void setXbasV2(Boolean xbasV2) {
		this.xbasV2 = xbasV2;
	}

}
