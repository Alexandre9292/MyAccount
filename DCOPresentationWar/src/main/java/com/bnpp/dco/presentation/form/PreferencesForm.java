package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.dto.DateFormatDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.UserDto;

@Component("preferencesForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PreferencesForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -5789783096803156193L;

	private UserDto userDto;

    private Integer ihmLanguage;
    private Integer ihmDateFormat;

    private String password;
    private String newPassword;
    private String newPasswordConfirm;

    private List<LanguageDto> ihmLanguageList;
    private List<DateFormatDto> ihmDateFormatList;

    public Integer getIhmLanguage() {
        return this.ihmLanguage;
    }

    public void setIhmLanguage(final Integer ihmLanguage) {
        this.ihmLanguage = ihmLanguage;
    }

    public Integer getIhmDateFormat() {
        return this.ihmDateFormat;
    }

    public void setIhmDateFormat(final Integer ihmDateFormat) {
        this.ihmDateFormat = ihmDateFormat;
    }

    public String getNewPasswordConfirm() {
        return this.newPasswordConfirm;
    }

    public void setNewPasswordConfirm(final String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public List<LanguageDto> getIhmLanguageList() {
        return this.ihmLanguageList;
    }

    public void setIhmLanguageList(final List<LanguageDto> ihmLanguageList) {
        this.ihmLanguageList = ihmLanguageList;
    }

    public List<DateFormatDto> getIhmDateFormatList() {
        return this.ihmDateFormatList;
    }

    public void setIhmDateFormatList(final List<DateFormatDto> ihmDateFormatList) {
        this.ihmDateFormatList = ihmDateFormatList;
    }

    public UserDto getUserDto() {
        return this.userDto;
    }

    public void setUserDto(final UserDto userDto) {
        this.userDto = userDto;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

}
