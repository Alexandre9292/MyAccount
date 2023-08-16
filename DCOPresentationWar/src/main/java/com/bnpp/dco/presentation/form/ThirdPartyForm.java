package com.bnpp.dco.presentation.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AddressDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.ThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("thirdPartyForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ThirdPartyForm implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 4616745790298547003L;

	private static List<Locale> nationalityList;

    private EntityDto entity;

    private ThirdPartyDto thirdPartyDto;

    private AddressDto homeAddress;

    private ParamFuncDto typeThirdParty;

    private List<ThirdPartyDto> thirdPartyList;

    private List<String> citizenshipList;

    private Integer thirdPartySelect;

    private String legalEntityName;

    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String birthDate;

    private String nationality;

    private String idReference;

    private String amountLimit;
    private String deviseAmountLimit;

    private String typeAccount;

    static {
        nationalityList = new ArrayList<Locale>();
        final List<String> alreadyAdded = new ArrayList<String>();
        for (final String string : Locale.getISOCountries()) {
            final Locale l = new Locale("", string);
            if (StringUtils.isNotBlank(l.getCountry()) && !alreadyAdded.contains(l.getCountry())) {
                nationalityList.add(l);
                alreadyAdded.add(l.getCountry());
            }
        }
    }

    /**
     * @throws DCOException
     */
    public void init() throws DCOException {
        final List<ThirdPartyDto> thirdPartyList = (List<ThirdPartyDto>) BusinessHelper.call(
                Constants.CONTROLLER_THIRD_PARTY, Constants.CONTROLLER_THIRD_PARTY_BY_USER_LOGIN,
                new Object[] {UserHelper.getUserInSession().getLogin()});
        setThirdPartyList(thirdPartyList);

        setThirdPartySelect(Constants.VAR_NEG_ONE);
        setThirdPartyDto(null);
        setBirthYear(null);
        setBirthMonth(null);
        setBirthDay(null);
        setBirthDate(null);
        final ThirdPartyDto thirdParty = new ThirdPartyDto();
        thirdParty.setTel(Constants.PLUS);
        thirdParty.setFax(Constants.PLUS);
        setThirdPartyDto(thirdParty);
    }

    /**
     * @return the thirdPartySelect
     */
    public Integer getThirdPartySelect() {
        return this.thirdPartySelect;
    }

    /**
     * @param thirdPartySelect the thirdPartySelect to set
     */
    public void setThirdPartySelect(final Integer thirdPartySelect) {
        this.thirdPartySelect = thirdPartySelect;
    }

    /**
     * @return the thirdPartyList
     */
    public List<ThirdPartyDto> getThirdPartyList() {
        return this.thirdPartyList;
    }

    /**
     * @param thirdPartyList the thirdPartyList to set
     */
    public void setThirdPartyList(final List<ThirdPartyDto> thirdPartyList) {
        for (final ThirdPartyDto tpDto : thirdPartyList) {
            tpDto.setSplittedNames();
        }
        this.thirdPartyList = thirdPartyList;
    }

    /**
     * @return the nationalityList
     */
    public List<Locale> getNationalityList() {
        return nationalityList;
    }

    /**
     * @param thirdPartyDto the thirdPartyDto to set
     */
    public void setThirdPartyDto(final ThirdPartyDto thirdPartyDto) {
        this.thirdPartyDto = thirdPartyDto;
    }

    /**
     * @return the thirdPartyDto
     */
    public ThirdPartyDto getThirdPartyDto() {
        return this.thirdPartyDto;
    }

    /**
     * @return the homeAddress
     */
    public AddressDto getHomeAddress() {
        return this.homeAddress;
    }

    /**
     * @param homeAddress the homeAddress to set
     */
    public void setHomeAddress(final AddressDto homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * @return the typeThirdParty
     */
    public ParamFuncDto getTypeThirdParty() {
        return this.typeThirdParty;
    }

    /**
     * @param typeThirdParty the typeThirdParty to set
     */
    public void setTypeThirdParty(final ParamFuncDto typeThirdParty) {
        this.typeThirdParty = typeThirdParty;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return this.nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }
    
    /**
     * @return the birthYear
     */
    public String getBirthYear() {
        return this.birthYear;
    }

    /**
     * @param birthYear the birthYear to set
     */
    public void setBirthYear(final String birthYear) {
        this.birthYear = birthYear;
    }
    
    /**
     * @return the birthMonth
     */
    public String getBirthMonth() {
        return this.birthMonth;
    }

    /**
     * @param birthMonth the birthMonth to set
     */
    public void setBirthMonth(final String birthMonth) {
        this.birthMonth = birthMonth;
    }
    
    /**
     * @return the birthDay
     */
    public String getBirthDay() {
        return this.birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(final String birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the idReference
     */
    public String getIdReference() {
        return this.idReference;
    }

    /**
     * @param idReference the idReference to set
     */
    public void setIdReference(final String idReference) {
        this.idReference = idReference;
    }

    /**
     * @return the amountLimit
     */
    public String getAmountLimit() {
        return this.amountLimit;
    }

    /**
     * @param amountLimit the amountLimit to set
     */
    public void setAmountLimit(final String amountLimit) {
        this.amountLimit = amountLimit;
    }

    /**
     * @return the typeAccount
     */
    public String getTypeAccount() {
        return this.typeAccount;
    }

    /**
     * @param typeAccount the typeAccount to set
     */
    public void setTypeAccount(final String typeAccount) {
        this.typeAccount = typeAccount;
    }

    /**
     * @return the legalEntityName
     */
    public String getLegalEntityName() {
        return this.legalEntityName;
    }

    /**
     * @param legalEntityName the legalEntityName to set
     */
    public void setLegalEntityName(final String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }

    /**
     * Allow to check that the user have at lease one THIRD_CONTACT but no more than two, at least one
     * THIRD_LEGAL_REPRESENTATIVE, at least one THIRD_SIGNATORY
     */
    public boolean checkNumberThirdParty() {
        Boolean result = false;
        final int[] counts = {Constants.VAR_ZERO, Constants.VAR_ZERO, Constants.VAR_ZERO};
        for (final ThirdPartyDto thirdPartyDto : getThirdPartyList()) {
            if (thirdPartyDto.getCorrespondantType() != null
                    && thirdPartyDto.getCorrespondantType().compareTo(Constants.THIRD_CONTACT) == Constants.VAR_ZERO) {
                counts[0]++;
            }

            if (thirdPartyDto.getCorrespondantTypeTwo() != null
                    && thirdPartyDto.getCorrespondantTypeTwo().compareTo(Constants.THIRD_SIGNATORY) == Constants.VAR_ZERO) {
                counts[1]++;
            }

            if (thirdPartyDto.getCorrespondantTypeThree() != null
                    && thirdPartyDto.getCorrespondantTypeThree().compareTo(Constants.THIRD_LEGAL_REPRESENTATIVE) == Constants.VAR_ZERO) {
                counts[2]++;
            }

            if (Constants.THIRD_CONTACT_MAX_NUMBER < counts[0]) {
                break;
            }
        }
        if (Constants.VAR_ZERO < counts[0] && Constants.THIRD_CONTACT_MAX_NUMBER >= counts[0]
                && Constants.VAR_ZERO < counts[1] && Constants.VAR_ZERO < counts[2]) {
            result = true;
        }
        return result;
    }

    /**
     * @return the entity
     */
    public EntityDto getEntity() {
        return this.entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(final EntityDto entity) {
        this.entity = entity;
    }

    /**
     * @return the citizenshipss
     */
    public List<String> getCitizenshipList() {
        return this.citizenshipList;
    }

    /**
     * @param citizenshipList the citizenshipss to set
     */
    public void setCitizenshipList(final List<String> citizenshipList) {
        this.citizenshipList = citizenshipList;
    }

	public String getDeviseAmountLimit() {
		return deviseAmountLimit;
	}

	public void setDeviseAmountLimit(String deviseAmountLimit) {
		this.deviseAmountLimit = deviseAmountLimit;
	}

}
