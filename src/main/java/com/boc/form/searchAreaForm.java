

package com.boc.form;

import java.util.Set;

/*
Created By SaiMadan on Dec 7, 2016
*/
public class searchAreaForm 
{
	String ntId;
	String firstName;
	String middleName;
	Set<Integer> urpcid;
	Set<String> masterSetAreaName;
	Set<String> areaName;
	Set<String> branchName;
	Set<String> roleName;
	Set<String> productCategory;
	Set<String> masterProductCategory;
	Set<String> existingProductCategorySet;
	public String getNtId() {
		return ntId;
	}
	public void setNtId(String ntId) {
		this.ntId = ntId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Set<String> getAreaName() {
		return areaName;
	}
	public void setAreaName(Set<String> areaName) {
		this.areaName = areaName;
	}
	public Set<String> getBranchName() {
		return branchName;
	}
	public void setBranchName(Set<String> branchName) {
		this.branchName = branchName;
	}
	public Set<String> getMasterSetAreaName() {
		return masterSetAreaName;
	}
	public void setMasterSetAreaName(Set<String> masterSetAreaName) {
		this.masterSetAreaName = masterSetAreaName;
	}
	public Set<String> getRoleName() {
		return roleName;
	}
	public void setRoleName(Set<String> roleName) {
		this.roleName = roleName;
	}
	public Set<String> getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(Set<String> productCategory) {
		this.productCategory = productCategory;
	}
	public Set<Integer> getUrpcid() {
		return urpcid;
	}
	public void setUrpcid(Set<Integer> urpcid) {
		this.urpcid = urpcid;
	}
	public Set<String> getMasterProductCategory() {
		return masterProductCategory;
	}
	public void setMasterProductCategory(Set<String> masterProductCategory) {
		this.masterProductCategory = masterProductCategory;
	}
	public Set<String> getExistingProductCategorySet() {
		return existingProductCategorySet;
	}
	public void setExistingProductCategorySet(Set<String> existingProductCategorySet) {
		this.existingProductCategorySet = existingProductCategorySet;
	}
	
	
}
