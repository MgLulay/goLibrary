package com.mobility.library.info;

public class ReportCriteriaInfo {
	private String dateOptr;
	private String fromDueDate;
	private String toDueDate;
	private String fromDocDate;
	private String toDocDate;
	private String memberOptr;
	private String memberCode;
	private String memberName;
	private String bookOptr;
	private String bookCode;
	private String bookName;
	private int status;
	private int currentpage;
	private int pagesize;

	public ReportCriteriaInfo() {
		//super();
		this.fromDueDate = "";
		this.toDueDate = "";
		this.fromDocDate ="";
		this.toDocDate = "";
		this.memberCode = "";
		this.memberName = "";
		this.bookCode = "";
		this.bookName = "";
		this.status = 0;
		this.currentpage = 1;
		this.pagesize = 0;
		this.memberOptr = "all";
		this.bookOptr = "all";
		this.dateOptr = "all";
	}

	
	public String getFromDocDate() {
		return fromDocDate;
	}


	public void setFromDocDate(String fromDocDate) {
		this.fromDocDate = fromDocDate;
	}


	public String getToDocDate() {
		return toDocDate;
	}


	public void setToDocDate(String toDocDate) {
		this.toDocDate = toDocDate;
	}


	public String getDateOptr() {
		return dateOptr;
	}

	public void setDateOptr(String dateOptr) {
		this.dateOptr = dateOptr;
	}

	public String getFromDueDate() {
		return fromDueDate;
	}

	public void setFromDueDate(String fromDueDate) {
		this.fromDueDate = fromDueDate;
	}

	public String getToDueDate() {
		return toDueDate;
	}

	public void setToDueDate(String toDueDate) {
		this.toDueDate = toDueDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getMemberOptr() {
		return memberOptr;
	}

	public void setMemberOptr(String memberOptr) {
		this.memberOptr = memberOptr;
	}

	public String getBookOptr() {
		return bookOptr;
	}

	public void setBookOptr(String bookOptr) {
		this.bookOptr = bookOptr;
	}

}
