package com.mobility.library.info;

public class RentalListDetailInfo {
	private String refno;
	private String code;
	private String name;
	private String docdate;
	private String duedate;
	private String returndate;
	private String bookcode;
	private String bookname;
	private String systemkey;
	private String parentsystemkey;
	private String authorsystemkey;
	private String authorname;
	private int quantity;

	public RentalListDetailInfo() {
		this.refno = "";
		this.code = "";
		this.name = "";
		this.docdate = "";
		this.duedate = "";
		this.returndate = "";
		this.bookcode = "";
		this.bookname = "";
		this.authorname = "";
		this.quantity = 0;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
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

	public String getReturndate() {
		return returndate;
	}

	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}

	public String getBookcode() {
		return bookcode;
	}

	public void setBookcode(String bookcode) {
		this.bookcode = bookcode;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSystemkey() {
		return systemkey;
	}

	public void setSystemkey(String systemkey) {
		this.systemkey = systemkey;
	}

	public String getParentsystemkey() {
		return parentsystemkey;
	}

	public void setParentsystemkey(String parentsystemkey) {
		this.parentsystemkey = parentsystemkey;
	}

	public String getAuthorsystemkey() {
		return authorsystemkey;
	}

	public void setAuthorsystemkey(String authorsystemkey) {
		this.authorsystemkey = authorsystemkey;
	}
}
