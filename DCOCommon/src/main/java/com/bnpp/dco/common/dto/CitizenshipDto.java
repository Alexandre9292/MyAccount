package com.bnpp.dco.common.dto;

/**
 * CitizenshipDto generated manually
 * @author afobatogue@aubay.com
 * @Date 17-12-2014
 */

public class CitizenshipDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private CitizenshipIdDto citizenship;
    private SignatoryDto signatory;

    /**
     * @return the citizenship
     */
    public CitizenshipIdDto getCitizenship() {
        return this.citizenship;
    }

    /**
     * @param citizenship the citizenship to set
     */
    public void setCitizenship(final CitizenshipIdDto citizenship) {
        this.citizenship = citizenship;
    }


	/**
	 * @return the signatory
	 */
	public SignatoryDto getSignatory() {
		return signatory;
	}

	/**
	 * @param signatory the signatory to set
	 */
	public void setSignatory(SignatoryDto signatory) {
		this.signatory = signatory;
	}

}
