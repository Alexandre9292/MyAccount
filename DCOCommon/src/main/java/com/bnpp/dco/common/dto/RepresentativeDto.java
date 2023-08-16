package com.bnpp.dco.common.dto;

import java.io.Serializable;
import java.util.List;

public class RepresentativeDto implements Serializable{

	private static final long serialVersionUID = -1845997829092002355L;
	private Integer id;
	private String name;
	private String firstname;
	private List<EntityDto> entityList;
	private List<CountryDto> countryList;
	
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
	 * @return the firstName
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * @return the entityList
	 */
	public List<EntityDto> getEntityList() {
		return entityList;
	}
	/**
	 * @param entityList the entityList to set
	 */
	public void setEntityList(List<EntityDto> entityList) {
		this.entityList = entityList;
	}
	/**
	 * @return the countryList
	 */
	public List<CountryDto> getCountryList() {
		return countryList;
	}
	/**
	 * @param countryList the countryList to set
	 */
	public void setCountryList(List<CountryDto> countryList) {
		this.countryList = countryList;
	}

	
}
