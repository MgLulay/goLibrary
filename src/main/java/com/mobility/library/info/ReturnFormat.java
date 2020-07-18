package com.mobility.library.info;

import java.util.ArrayList;
import java.util.List;

public class ReturnFormat<T> {
  private String message;
  private List<T> list;
  private int totalcount;

  public ReturnFormat() {
    this.message = "";
    this.list = new ArrayList<>();
    this.totalcount = 0;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public int getTotalcount() {
    return totalcount;
  }

  public void setTotalcount(int totalcount) {
    this.totalcount = totalcount;
  }
}
