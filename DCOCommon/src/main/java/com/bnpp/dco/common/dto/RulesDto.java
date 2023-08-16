package com.bnpp.dco.common.dto;

public class RulesDto implements java.io.Serializable{

	private static final long serialVersionUID = -690777941002270169L;
	private Integer Id;
	private SignatoryDto signatory;	
	private SignatoryDto signatory2;
	private CollegeDto college;
	private CollegeDto college2;
	private AccountDto account;
	private Double amountMax;
	private Double amountMin;
	private String typeOperation;
	private String field;
	
	private Integer indexSignatory1;
	private Integer indexSignatory2;
	private Integer indexCollege1;
	private Integer indexCollege2;
	
	
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
	public SignatoryDto getSignatory() {
		return signatory;
	}
	/**
	 * @param signatory the signatory to set
	 */
	public void setSignatory(SignatoryDto signatory) {
		this.signatory = signatory;
	}
	/**
	 * @return the signatory2
	 */
	public SignatoryDto getSignatory2() {
		return signatory2;
	}
	/**
	 * @param signatory the signatory to set
	 */
	public void setSignatory2(SignatoryDto signatory) {
		this.signatory2 = signatory;
	}
	/**
	 * @return the college
	 */
	public CollegeDto getCollege() {
		return college;
	}
	/**
	 * @param college the college to set
	 */
	public void setCollege(CollegeDto college) {
		this.college = college;
	}
	/**
	 * @return the college2
	 */
	public CollegeDto getCollege2() {
		return college2;
	}
	/**
	 * @param college the college to set
	 */
	public void setCollege2(CollegeDto college) {
		this.college2 = college;
	}
	/**
	 * @return the account
	 */
	public AccountDto getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(AccountDto account) {
		this.account = account;
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
	/**
	 * @return the indexSignatory1
	 */
	public Integer getIndexSignatory1() {
		return indexSignatory1;
	}
	/**
	 * @param indexSignatory1 the indexSignatory1 to set
	 */
	public void setIndexSignatory1(Integer indexSignatory1) {
		this.indexSignatory1 = indexSignatory1;
	}
	/**
	 * @return the indexSignatory2
	 */
	public Integer getIndexSignatory2() {
		return indexSignatory2;
	}
	/**
	 * @param indexSignatory2 the indexSignatory2 to set
	 */
	public void setIndexSignatory2(Integer indexSignatory2) {
		this.indexSignatory2 = indexSignatory2;
	}
	/**
	 * @return the indexCollege1
	 */
	public Integer getIndexCollege1() {
		return indexCollege1;
	}
	/**
	 * @param indexCollege1 the indexCollege1 to set
	 */
	public void setIndexCollege1(Integer indexCollege1) {
		this.indexCollege1 = indexCollege1;
	}
	/**
	 * @return the indexCollege2
	 */
	public Integer getIndexCollege2() {
		return indexCollege2;
	}
	/**
	 * @param indexCollege2 the indexCollege2 to set
	 */
	public void setIndexCollege2(Integer indexCollege2) {
		this.indexCollege2 = indexCollege2;
	}

	
}
