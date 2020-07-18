package com.mobility.library.info;

public class RentalDetailInfo {
  private String systemkey;
  private String parentsystemkey;
  private String bookcode;
  private String bookname;
  private String booksystemkey;
  private String authorname;
  private String authorsystemkey;
  private int quantity;

  public RentalDetailInfo() {
    this.systemkey = "";
    this.parentsystemkey = "";
    this.bookcode = "";
    this.bookname = "";
    this.booksystemkey = "";
    this.authorname = "";
    this.authorsystemkey = "";
    this.quantity = 0;

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

  public void setParentsystemkey(String partentsystemkey) {
    this.parentsystemkey = partentsystemkey;
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

  public String getBooksystemkey() {
    return booksystemkey;
  }

  public void setBooksystemkey(String booksystemkey) {
    this.booksystemkey = booksystemkey;
  }

  public String getAuthorname() {
    return authorname;
  }

  public void setAuthorname(String authorname) {
    this.authorname = authorname;
  }

  public String getAuthorsystemkey() {
    return authorsystemkey;
  }

  public void setAuthorsystemkey(String authorsystemkey) {
    this.authorsystemkey = authorsystemkey;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
