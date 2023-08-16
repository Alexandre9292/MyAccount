package com.bnpp.dco.presentation.utils.pdf;

import com.bnpp.dco.common.dto.AccountDto;
import com.bnpp.dco.common.dto.CollegeDto;
import com.bnpp.dco.common.dto.SignatoryDto;

public class PDFBeanRules {

	private Integer Id;
	private PDFBeanSignatory signatory;	
	private PDFBeanSignatory signatory2;
	private PDFBeanCollege college;
	private PDFBeanCollege college2;
	private Double amountMax;
	private Double amountMin;
	private String typeOperation;
	private String field;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		Id = id;
	}
	/**
	 * @return the signatory
	 */
	public PDFBeanSignatory getSignatory() {
		return signatory;
	}
	/**
	 * @param signatory the signatory to set
	 */
	public void setSignatory(PDFBeanSignatory signatory) {
		this.signatory = signatory;
	}
	/**
	 * @return the signatory2
	 */
	public PDFBeanSignatory getSignatory2() {
		return signatory2;
	}
	/**
	 * @param signatory2 the signatory2 to set
	 */
	public void setSignatory2(PDFBeanSignatory signatory2) {
		this.signatory2 = signatory2;
	}
	/**
	 * @return the college
	 */
	public PDFBeanCollege getCollege() {
		return college;
	}
	/**
	 * @param college the college to set
	 */
	public void setCollege(PDFBeanCollege college) {
		this.college = college;
	}
	/**
	 * @return the college2
	 */
	public PDFBeanCollege getCollege2() {
		return college2;
	}
	/**
	 * @param college2 the college2 to set
	 */
	public void setCollege2(PDFBeanCollege college2) {
		this.college2 = college2;
	}
	/**
	 * @return the amountMax
	 */
	public Double getAmountMax() {
		return amountMax;
	}
	/**
	 * @param amountMax the amountMax to set
	 */
	public void setAmountMax(Double amountMax) {
		this.amountMax = amountMax;
	}
	/**
	 * @return the amountMin
	 */
	public Double getAmountMin() {
		return amountMin;
	}
	/**
	 * @param amountMin the amountMin to set
	 */
	public void setAmountMin(Double amountMin) {
		this.amountMin = amountMin;
	}
	/**
	 * @return the typeOperation
	 */
	public String getTypeOperation() {
		return typeOperation;
	}
	/**
	 * @param typeOperation the typeOperation to set
	 */
	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	
}
