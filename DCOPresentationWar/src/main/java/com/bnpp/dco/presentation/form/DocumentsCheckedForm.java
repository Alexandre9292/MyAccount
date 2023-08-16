package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("documentsCheckedForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentsCheckedForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -4085592467717023538L;
	
	private Integer[] documentsChecked;

    public Integer[] getDocumentsChecked() {
        return this.documentsChecked;
    }

    public void setDocumentsChecked(final Integer[] docsChecked) {
        if (docsChecked == null) {
            this.documentsChecked = new Integer[0];
        } else {
            this.documentsChecked = Arrays.copyOf(docsChecked, docsChecked.length);
        }
    }

}
