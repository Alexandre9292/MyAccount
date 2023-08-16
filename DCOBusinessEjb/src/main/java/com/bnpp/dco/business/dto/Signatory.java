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
@Table(name = "signatory", catalog = "dco")
public class Signatory implements java.io.Serializable {

	private static final long serialVersionUID = 8972685543765962542L;
	private Integer id;
	private String legalEntityName;
	private String positionName;
	private Address addressByIdHomeAdress;
	private User user;
	private String name;
	private String firstName;
	private Date birthDate;
	private String birthPlace;
	private String email;
	private String tel;
	private String fax;
	private String referenceId;
	private String nationality;
	private Set citizenships = new HashSet(0);
	private Set college = new HashSet(0);
	private Set rules = new HashSet(0);
	private Set account = new HashSet(0);
	private String role;
	
	
	
	public Signatory() {
    }

    public Signatory(final Address addressByIdHomeAdress, final User user, final String name,
            final String firstName, final Integer correspondantType) {
        this.addressByIdHomeAdress = addressByIdHomeAdress;
        this.user = user;
        this.name = name;
        this.firstName = firstName;
    }

    public Signatory(final String positionName, final String legalEntityName,
            final Address addressByIdHomeAdress, final User user, final String name, final String firstName,
            final Date birthDate, final String birthPlace, final String email, final String tel, final String fax,
            final String referenceId, final String nationality, final Set citizenships) {
        this.legalEntityName = legalEntityName;
        this.positionName = positionName;
        this.addressByIdHomeAdress = addressByIdHomeAdress;
        this.user = user;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.email = email;
        this.tel = tel;
        this.fax = fax;
        this.referenceId = referenceId;
        this.nationality = nationality;
        this.citizenships = citizenships;
    }
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Column(name = "POSITION_NAME", length = 50)
    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(final String positionName) {
        this.positionName = positionName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_ID", nullable = false)
    public Address getAddressByIdHomeAdress() {
        return this.addressByIdHomeAdress;
    }

    public void setAddressByIdHomeAdress(final Address addressByIdHomeAdress) {
        this.addressByIdHomeAdress = addressByIdHomeAdress;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTH_DATE", length = 19)
    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "BIRTH_PLACE", length = 100)
    public String getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
	 * @return the email
	 */
    @Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

    @Column(name = "TEL", length = 100)
    public String getTel() {
        return this.tel;
    }

    public void setTel(final String tel) {
        this.tel = tel;
    }

    @Column(name = "FAX", length = 45)
    public String getFax() {
        return this.fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    @Column(name = "REFERENCE_ID", length = 45)
    public String getReferenceId() {
        return this.referenceId;
    }

    public void setReferenceId(final String referenceId) {
        this.referenceId = referenceId;
    }

    @Column(name = "NATIONALITY", length = 10)
    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "signatory")
    public Set<Citizenship> getCitizenships() {
        return this.citizenships;
    }

    public void setCitizenships(final Set citizenships) {
        this.citizenships = citizenships;
    }

    /**
     * @return the legalEntityName
     */
    @Column(name = "LEGAL_ENTITY_NAME", length = 50)
    public String getLegalEntityName() {
        return this.legalEntityName;
    }

    /**
     * @param legalEntityName the legalEntityName to set
     */
    public void setLegalEntityName(final String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "college_signatories", catalog = "dco", joinColumns = {@JoinColumn(name = "signatory_ID", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "college_ID", nullable = false)})
	//    @JoinTable(name = "college_signatories", catalog = "dco", joinColumns = {@JoinColumn(name = "signatory_ID", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "college_ID", nullable = false)})
    public Set<College> getCollege() {
		return college;
	}

	public void setCollege(Set college) {
		this.college = college;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "signatory")
	public Set<Rules> getRules() {
		return rules;
	}

	public void setRules(Set rules) {
		this.rules = rules;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_signatories", catalog = "dco", joinColumns = {@JoinColumn(name = "signatory_ID", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "account_ID", nullable = false, updatable = false)})
    public Set<Account> getAccount() {
		return account;
	}

	public void setAccount(Set account) {
		this.account = account;
	}

	/**
	 * @return the role
	 */
	@Column(name = "role", length = 10)
    public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
