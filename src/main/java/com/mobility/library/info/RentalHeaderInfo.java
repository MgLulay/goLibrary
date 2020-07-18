package com.mobility.library.info;

import java.util.ArrayList;
import java.util.List;

public class RentalHeaderInfo {
  private String systemkey;
  private String refno;
  private String docdate;
  private String duedate;
  private String returndate;
  private String secondkey;
  private String membersyskey;
  private String membername;
  private int status;
  private String userid;
  private String username;
  private String remark;
  private List<RentalDetailInfo> rentalDetailInfoList;

  public RentalHeaderInfo() {
    this.systemkey = "0";
    this.refno = "";
    this.docdate = "";
    this.duedate = "";
    this.returndate = "";
    this.secondkey = "0";
    this.membersyskey = "0";
    this.membername = "";
    this.status = 0;
    this.userid = "";
    this.username = "";
    this.remark = "";
    this.rentalDetailInfoList = new ArrayList<>();
  }

  public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public String getMembername() {
    return membername;
  }

  public void setMembername(String membername) {
    this.membername = membername;
  }

  public String getSystemkey() {
    return systemkey;
  }

  public void setSystemkey(String systemkey) {
    this.systemkey = systemkey;
  }

  public String getRefno() {
    return refno;
  }

  public void setRefno(String referenceno) {
    this.refno = referenceno;
  }

  public String getDocdate() {
    return docdate;
  }

  public void setDocdate(String docdate) {
    this.docdate = docdate;
  }

  public String getDuedate() {
    return duedate;
  }

  public void setDuedate(String duedate) {
    this.duedate = duedate;
  }

  public String getReturndate() {
    return returndate;
  }

  public void setReturndate(String returndate) {
    this.returndate = returndate;
  }

  public String getSecondkey() {
	return secondkey;
}

public void setSecondkey(String secondkey) {
	this.secondkey = secondkey;
}

public String getMembersyskey() {
    return membersyskey;
  }

  public void setMembersyskey(String membersyskey) {
    this.membersyskey = membersyskey;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
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

  public List<RentalDetailInfo> getRentalDetailInfoList() {
    return rentalDetailInfoList;
  }

  public void setRentalDetailInfoList(List<RentalDetailInfo> rentalDetailInfoList) {
    this.rentalDetailInfoList = rentalDetailInfoList;
  }
}
