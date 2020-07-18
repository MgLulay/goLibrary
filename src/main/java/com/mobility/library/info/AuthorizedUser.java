package com.mobility.library.info;

public class AuthorizedUser {
	private String systemkey;
	private String userid;
	private String username;
	private String password;
	private String passcode;
	private String phone;
	private String email;
	private String userrole;
	private int deletedstatus;

	public AuthorizedUser() {
		this.systemkey = "";
		this.userid = "";
		this.username = "";
		this.password = "";
		this.passcode = "";
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

	public int getDeletedstatus() {
		return deletedstatus;
	}

	public void setDeletedstatus(int deletedstatus) {
		this.deletedstatus = deletedstatus;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
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

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

}
