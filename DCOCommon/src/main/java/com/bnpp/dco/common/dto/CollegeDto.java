package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.List;

public class CollegeDto implements Serializable, Comparable<CollegeDto> {

	private static final long serialVersionUID = 2834208334385431340L;
	private Integer id;
	private String name;
	private List<SignatoryDto> signatoriesList;
	private List<RulesDto> rulesList;
	private AccountDto account;
	private Integer nbSignatory;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public AccountDto getAccount() {
		return account;
	}
	public void setAccount(AccountDto account) {
		this.account = account;
	}
	/**
	 * @return the signatoriesList
	 */
	public List<SignatoryDto> getSignatoriesList() {
		return signatoriesList;
	}
	/**
	 * @param signatoriesList the signatoriesList to set
	 */
	public void setSignatoriesList(List<SignatoryDto> signatoriesList) {
		this.signatoriesList = signatoriesList;
	}
	/**
	 * @return the rulesList
	 */
	public List<RulesDto> getRulesList() {
		return rulesList;
	}
	/**
	 * @param rulesList the rulesList to set
	 */
	public void setRulesList(List<RulesDto> rulesList) {
		this.rulesList = rulesList;
	}
	/**
	 * @return the nbSignatory
	 */
	public Integer getNbSignatory() {
		return nbSignatory;
	}
	/**
	 * @param nbSignatory the nbSignatory to set
	 */
	public void setNbSignatory(Integer nbSignatory) {
		this.nbSignatory = nbSignatory;
	}
	@Override
	public int compareTo(CollegeDto c) {
		if (this.getId() == c.getId() || this.getId() == null || c.getId() == null) {
			return 0;
		}
		if (this.getId() < c.getId()) {
			return -1;
        } else {
            return 1;
        }
	}	
}
