package com.mobility.library.info;

public class BookInfo {

	private String systemkey;
	private String createddate;
	private String modifieddate;
	private String code;
	private String name;
	private String booktype;
	private String author;
	private String purchaseddate;
	private String price;
	private String issueddate;
	private int deletestatus;
	private int available;
	private String publisher;
	private String barcode;
	private String shortcode;

	public BookInfo() {
		this.systemkey = "0";
		this.createddate = "";
		this.code = "";
		this.name = "";
		this.booktype = "";
		this.author = "";
		this.purchaseddate = "";
		this.modifieddate = "";
		this.price = "0";
		this.issueddate = "";
		this.deletestatus = 0;
		this.available = 0;
		this.publisher = "";
		this.barcode = "";
		this.shortcode = "";
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public int getDeletestatus() {
		return deletestatus;
	}

	public void setDeletestatus(int deletestatus) {
		this.deletestatus = deletestatus;
	}

	public String getSystemkey() {
		return systemkey;
	}

	public String getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
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

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPurchaseddate() {
		return purchaseddate;
	}

	public void setPurchaseddate(String purchaseddate) {
		this.purchaseddate = purchaseddate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIssueddate() {
		return issueddate;
	}

	public void setIssueddate(String issueddate) {
		this.issueddate = issueddate;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
}
