package com.bnpp.dco.business.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "representative", catalog = "dco")
public class Representatives implements java.io.Serializable {
	
	private static final long serialVersionUID = 8972685543765962542L;
	private Integer id;
	private String name;
	private String firstName;
	private Set entity = new HashSet(0);
	private Set country = new HashSet(0);
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "entity_representative", catalog = "dco", joinColumns = {@JoinColumn(name = "representative_ID", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "entities_ID", nullable = false, updatable = false)})
    public Set<Entities> getEntity() {
		return entity;
	}

	public void setEntity(Set entity) {
		this.entity = entity;
	}

	/**
	 * @return the country
	 */
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "country_representatives", catalog = "dco", joinColumns = {@JoinColumn(name = "representative_ID", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "country_ID", nullable = false, updatable = false)})
    public Set<Country> getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Set country) {
		this.country = country;
	}
	
	

}
