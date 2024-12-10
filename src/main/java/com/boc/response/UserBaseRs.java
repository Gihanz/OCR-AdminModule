

package com.boc.response;

import java.util.Set;

import com.boc.model.BranchBase;

/*
Created By SaiMadan on Nov 17, 2016
*/
public class UserBaseRs 
{

	private int uid;
	private int bid;
	private String ntId;
	private String ntDomain;
	private String firstName;
	private String middleName;
	private String lastName;
	private char isActive;
	private Short editflag;
	Set<String> branchName;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getNtId() {
		return ntId;
	}
	public void setNtId(String ntId) {
		this.ntId = ntId;
	}
	public String getNtDomain() {
		return ntDomain;
	}
	public void setNtDomain(String ntDomain) {
		this.ntDomain = ntDomain;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public Short getEditflag() {
		return editflag;
	}
	public void setEditflag(Short editflag) {
		this.editflag = editflag;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	
}
