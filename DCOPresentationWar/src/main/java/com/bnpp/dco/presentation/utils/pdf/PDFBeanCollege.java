package com.bnpp.dco.presentation.utils.pdf;

import java.util.List;

import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.dto.SignatoryDto;

public class PDFBeanCollege {

	private Integer id;
	private String name;
	private List<PDFBeanSignatory> signatoriesList;
	private List<PDFBeanRules> rulesList;
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
	/**
	 * @return the signatoriesList
	 */
	public List<PDFBeanSignatory> getSignatoriesList() {
		return signatoriesList;
	}
	/**
	 * @param signatoriesList the signatoriesList to set
	 */
	public void setSignatoriesList(List<PDFBeanSignatory> signatoriesList) {
		this.signatoriesList = signatoriesList;
	}
	/**
	 * @return the rulesList
	 */
	public List<PDFBeanRules> getRulesList() {
		return rulesList;
	}
	/**
	 * @param rulesList the rulesList to set
	 */
	public void setRulesList(List<PDFBeanRules> rulesList) {
		this.rulesList = rulesList;
	}
	
}
