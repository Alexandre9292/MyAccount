package com.bnpp.dco.presentation.form;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("statsForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StatsForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -4317879949922400724L;
	
	private int typeStat;
    private String csv;
    private String fileName;

    public int getTypeStat() {
        return this.typeStat;
    }

    public void setTypeStat(final int typeStat) {
        this.typeStat = typeStat;
    }

    public String getCsv() {
        return this.csv;
    }

    public void setCsv(final String csv) {
        this.csv = csv;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

}