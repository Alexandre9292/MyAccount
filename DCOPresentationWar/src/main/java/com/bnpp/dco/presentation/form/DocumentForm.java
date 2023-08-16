package com.bnpp.dco.presentation.form;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.bnpp.dco.common.dto.DocumentDto;

@Component("documentForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentForm extends DocumentFilterForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 9170019164930273933L;
	
	private MultipartFile file;
    private boolean match;
    private boolean matchAll;
    private boolean confirm;

    private DocumentDto documentToSave;

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    public boolean isMatch() {
        return this.match;
    }

    public void setMatch(final boolean match) {
        this.match = match;
    }

    public boolean isMatchAll() {
        return this.matchAll;
    }

    public void setMatchAll(final boolean matchAll) {
        this.matchAll = matchAll;
    }

    public boolean isConfirm() {
        return this.confirm;
    }

    public void setConfirm(final boolean confirm) {
        this.confirm = confirm;
    }

    public DocumentDto getDocumentToSave() {
        return this.documentToSave;
    }

    public void setDocumentToSave(final DocumentDto documentToSave) {
        this.documentToSave = documentToSave;
    }
}
