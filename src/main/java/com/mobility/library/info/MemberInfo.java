package com.mobility.library.info;

public class MemberInfo {
	private String systemkey;
	private String createddate;
	private String modifieddate;
	private String code;
	private String name;
	private String starteddate;
	private String address;
	private String nrcno;
	private String gender;
	private String dob;
	private String phone;
	private String email;
	private int deletedstatus;

	public MemberInfo() {
		this.systemkey = "";
		this.createddate = "";
		this.modifieddate = "";
		this.code = "";
		this.name = "";
		this.starteddate = "";
		this.address = "";
		this.nrcno = "";
		this.gender = "";
		this.dob = "";
		this.phone = "";
		this.email = "";
		this.deletedstatus = 0;
	}

	public String getSystemkey() {
		return systemkey;
	}

	public void setSystemkey(String systemkey) {
		this.systemkey = systemkey;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStarteddate() {
		return starteddate;
	}

	public void setStarteddate(String starteddate) {
		this.starteddate = starteddate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNrcno() {
		return nrcno;
	}

	public void setNrcno(String nrcno) {
		this.nrcno = nrcno;
	}

	public int getDeletedstatus() {
		return deletedstatus;
	}

	public void setDeletedstatus(int deletedstatus) {
		this.deletedstatus = deletedstatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
