

package com.boc.form;

import java.util.Set;

/*
Created By SaiMadan on Dec 7, 2016
*/
public class searchBranchForm 
{
	String ntId;
	Set<Integer> urpcid;
	Set<String> masterSetBranchName;
	Set<String> areaName;
	Set<String> branchName;
	Set<String> roleName;
	Set<String> productCategory;
	public String getNtId() {
		return ntId;
	}
	public void setNtId(String ntId) {
		this.ntId = ntId;
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
	
	public Set<String> getMasterSetBranchName() {
		return masterSetBranchName;
	}
	public void setMasterSetBranchName(Set<String> masterSetBranchName) {
		this.masterSetBranchName = masterSetBranchName;
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
	
	
}
