package com.boc.model;
// Generated Jun 23, 2016 11:37:27 AM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SecurityTypeBase generated by hbm2java
 */
@Entity
@Table(name = "SECURITY_TYPE_BASE", schema = "WFCONFIG", uniqueConstraints = @UniqueConstraint(columnNames = "NATURE_OF_SECURITY"))
public class SecurityTypeBase implements java.io.Serializable {

	private int securityTypeId;
	private String natureOfSecurity;
	private Set<SecurityTypeDocumentChecklistMapping> securityTypeDocumentChecklistMappings = new HashSet<SecurityTypeDocumentChecklistMapping>(
			0);
	private Set<ProductSecurityTypeMapping> productSecurityTypeMappings = new HashSet<ProductSecurityTypeMapping>(0);

	public SecurityTypeBase() {
	}

	public SecurityTypeBase(int securityTypeId, String natureOfSecurity) {
		this.securityTypeId = securityTypeId;
		this.natureOfSecurity = natureOfSecurity;
	}

	public SecurityTypeBase(int securityTypeId, String natureOfSecurity,
			Set<SecurityTypeDocumentChecklistMapping> securityTypeDocumentChecklistMappings,
			Set<ProductSecurityTypeMapping> productSecurityTypeMappings) {
		this.securityTypeId = securityTypeId;
		this.natureOfSecurity = natureOfSecurity;
		this.securityTypeDocumentChecklistMappings = securityTypeDocumentChecklistMappings;
		this.productSecurityTypeMappings = productSecurityTypeMappings;
	}

	@Id

	@Column(name = "SECURITY_TYPE_ID", unique = true, nullable = false)
	public int getSecurityTypeId() {
		return this.securityTypeId;
	}

	public void setSecurityTypeId(int securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	@Column(name = "NATURE_OF_SECURITY", unique = true, nullable = false, length = 50)
	public String getNatureOfSecurity() {
		return this.natureOfSecurity;
	}

	public void setNatureOfSecurity(String natureOfSecurity) {
		this.natureOfSecurity = natureOfSecurity;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "securityTypeBase")
	public Set<SecurityTypeDocumentChecklistMapping> getSecurityTypeDocumentChecklistMappings() {
		return this.securityTypeDocumentChecklistMappings;
	}

	public void setSecurityTypeDocumentChecklistMappings(
			Set<SecurityTypeDocumentChecklistMapping> securityTypeDocumentChecklistMappings) {
		this.securityTypeDocumentChecklistMappings = securityTypeDocumentChecklistMappings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "securityTypeBase")
	public Set<ProductSecurityTypeMapping> getProductSecurityTypeMappings() {
		return this.productSecurityTypeMappings;
	}

	public void setProductSecurityTypeMappings(Set<ProductSecurityTypeMapping> productSecurityTypeMappings) {
		this.productSecurityTypeMappings = productSecurityTypeMappings;
	}

}
