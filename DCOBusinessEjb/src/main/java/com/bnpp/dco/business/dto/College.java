package com.bnpp.dco.business.dto;

import static javax.persistence.GenerationType.IDENTITY;

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

@Entity
@Table(name = "college", catalog = "dco")
public class College implements java.io.Serializable{

	private static final long serialVersionUID = -7889883332162207628L;
	private Integer id;
	private String name;
	private Set signatories = new HashSet(0);
	private Set rules = new HashSet(0);
	private Account accountG;

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "NAME", length = 45)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_ID", nullable = false)
	public Account getAccountG() {
		return accountG;
	}
	public void setAccountG(Account accountG) {
		this.accountG = accountG;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "college_signatories", catalog = "dco", joinColumns = {@JoinColumn(name = "college_ID", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "signatory_ID", nullable = false, updatable = false)})
//    @JoinTable(name = "college_signatories", catalog = "dco", joinColumns = {@JoinColumn(name = "college_ID", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "signatory_ID", nullable = false)})
    public Set<Signatory> getSignatories() {
		return signatories;
	}
	public void setSignatories(Set signatories) {
		this.signatories = signatories;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "college")
	public Set<Rules> getRules() {
		return rules;
	}

	public void setRules(Set rules) {
		this.rules = rules;
	}
}
