package com.bnpp.dco.business.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rules", catalog = "dco")
public class Rules implements java.io.Serializable{

	private static final long serialVersionUID = -9082398331837464814L;
	private Integer Id;
	private Signatory signatory;
	private Signatory signatory2;
	private College college;
	private College college2;
	private Account account;
	private Double amountMax;
	private Double amountMin;
	private String typeOperation;
	private String field;
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_ID", nullable = false)
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signatory_ID")
	public Signatory getSignatory() {
		return signatory;
	}
	public void setSignatory(Signatory signatory) {
		this.signatory = signatory;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signatory_ID2")
	public Signatory getSignatory2() {
		return signatory2;
	}
	public void setSignatory2(Signatory signatory2) {
		this.signatory2 = signatory2;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_ID")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_ID2")
	public College getCollege2() {
		return college2;
	}
	public void setCollege2(College college2) {
		this.college2 = college2;
	}
	/**
	 * @return the amountMax
	 */
	@Column(name = "AMOUNT_MAX", nullable = false)
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
	@Column(name = "AMOUNT_MIN", nullable = false)
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
	@Column(name = "TYPE_OPERATION", nullable = false)
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
	@Column(name = "CHAMPS", nullable = false)
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
