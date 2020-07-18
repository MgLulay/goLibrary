package com.mobility.library.info;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("publisher")
public class Publisher {

	  private String systemkey;
	  private String code;
	  private String name;
	  private String phone;
	  private String address;
	  private String contact;
	  private String remark;
	  private int deletedstatus;
	  
	public Publisher() {
		this.systemkey = "0";
		this.code = "";
		this.name = "";
		this.phone = "";
		this.address = "";
		this.contact = "";
		this.remark = "";
		this.deletedstatus = 0;
	}
	
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
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getContact() {
		return contact;
	}
	public int getDeletedstatus() {
		return deletedstatus;
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
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setDeletedstatus(int deletedstatus) {
		this.deletedstatus = deletedstatus;
	}
	  
}
