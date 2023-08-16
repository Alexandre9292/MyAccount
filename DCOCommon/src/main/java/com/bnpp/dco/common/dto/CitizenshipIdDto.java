package com.bnpp.dco.common.dto;

import java.util.Locale;

/**
 * CitizenshipIdDto generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */

public class CitizenshipIdDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer signatoryId;
    private Locale country;
    private String value;


    /**
     * @return the country
     */
    public Locale getCountry() {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(final Locale country) {
        this.country = country;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

	/**
	 * @return the signatoryId
	 */
	public Integer getSignatoryId() {
		return signatoryId;
	}

	/**
	 * @param signatoryId the signatoryId to set
	 */
	public void setSignatoryId(Integer signatoryId) {
		this.signatoryId = signatoryId;
	}

}
