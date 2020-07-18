package com.mobility.library.info;

public class Rack {
	private String systemkey;
	private String code;
	private String name;
	private String remark;
	private int deletedstatus;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSystemkey() {
		return systemkey;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setSystemkey(String systemkey) {
		this.systemkey = systemkey;
	}
	public void setCode(String code) {
		this.code = code;
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
