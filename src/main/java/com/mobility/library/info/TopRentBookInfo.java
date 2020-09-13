package com.mobility.library.info;

public class TopRentBookInfo {

	private String booksystemkey;
	private String name;
	private String code;
	private String authorname;
	private int totalCount;	
	
	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public String getBooksystemkey() {
		return booksystemkey;
	}

	public void setBooksystemkey(String booksystemkey) {
		this.booksystemkey = booksystemkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
