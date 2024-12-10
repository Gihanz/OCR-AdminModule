package com.boc.model;
// Generated Jun 23, 2016 11:37:27 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProductDocumentChecklistMapping generated by hbm2java
 */
@Entity
@Table(name = "PRODUCT_DOCUMENT_CHECKLIST_MAPPING", schema = "WFCONFIG")
public class ProductDocumentChecklistMapping implements java.io.Serializable {

	private int pdcid;
	private DocumentTypeBase documentTypeBase;
	private ProductBase productBase;
	private char scanRequired;
	private Character mandatory;

	public ProductDocumentChecklistMapping() {
	}

	public ProductDocumentChecklistMapping(int pdcid, DocumentTypeBase documentTypeBase, ProductBase productBase,
			char scanRequired) {
		this.pdcid = pdcid;
		this.documentTypeBase = documentTypeBase;
		this.productBase = productBase;
		this.scanRequired = scanRequired;
	}

	public ProductDocumentChecklistMapping(int pdcid, DocumentTypeBase documentTypeBase, ProductBase productBase,
			char scanRequired, Character mandatory) {
		this.pdcid = pdcid;
		this.documentTypeBase = documentTypeBase;
		this.productBase = productBase;
		this.scanRequired = scanRequired;
		this.mandatory = mandatory;
	}

	@Id

	@Column(name = "PDCID", unique = true, nullable = false)
	public int getPdcid() {
		return this.pdcid;
	}

	public void setPdcid(int pdcid) {
		this.pdcid = pdcid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID", nullable = false)
	public DocumentTypeBase getDocumentTypeBase() {
		return this.documentTypeBase;
	}

	public void setDocumentTypeBase(DocumentTypeBase documentTypeBase) {
		this.documentTypeBase = documentTypeBase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public ProductBase getProductBase() {
		return this.productBase;
	}

	public void setProductBase(ProductBase productBase) {
		this.productBase = productBase;
	}

	@Column(name = "SCAN_REQUIRED", nullable = false, length = 1)
	public char getScanRequired() {
		return this.scanRequired;
	}

	public void setScanRequired(char scanRequired) {
		this.scanRequired = scanRequired;
	}

	@Column(name = "MANDATORY", length = 1)
	public Character getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(Character mandatory) {
		this.mandatory = mandatory;
	}

}
