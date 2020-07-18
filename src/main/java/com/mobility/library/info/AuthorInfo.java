package com.mobility.library.info;

public class AuthorInfo {

  private String systemkey;
  private String code;
  private String name;
  private int deletedstatus;
  private String createddate;
  private String modifieddate;

  public AuthorInfo() {
    this.systemkey = "";
    this.code = "";
    this.name = "";
    this.deletedstatus = 0;
    this.createddate = "";
    this.modifieddate = "";
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

  public String getSystemkey() {
    return systemkey;
  }

  public void setSystemkey(String systemkey) {
    this.systemkey = systemkey;
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

  public int getDeletedstatus() {
    return deletedstatus;
  }

  public void setDeletedstatus(int deletedstatus) {
    this.deletedstatus = deletedstatus;
  }
}
