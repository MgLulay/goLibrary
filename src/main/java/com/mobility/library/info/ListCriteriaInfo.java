package com.mobility.library.info;

public class ListCriteriaInfo {
	private String code;
	private String name;
	private String docdate;
	private String duedate;
	private String membersyskey;
	private String systemkey;
	private String author;
	private String booktype;
	private int status;
	private int availableFlag;
	private int currentpage;
	private int pagesize;

	public ListCriteriaInfo() {
		this.code = "";
		this.name = "";
		this.docdate = "";
		this.duedate = "";
		this.systemkey = "0";
		this.membersyskey = "";
		this.author = "";
		this.booktype = "";
		this.status = 0;
		this.availableFlag = 0;
		this.currentpage = 0;
		this.pagesize = 0;
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

	public String getMembersyskey() {
		return membersyskey;
	}

	public void setMembersyskey(String membersyskey) {
		this.membersyskey = membersyskey;
	}

	public String getSystemkey() {
		return systemkey;
	}

	public void setSystemkey(String systemkey) {
		this.systemkey = systemkey;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(int availableFlag) {
		this.availableFlag = availableFlag;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
}
