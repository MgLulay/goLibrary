package com.mobility.library.controller;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.BookRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rent")
public class BookRentController {
  @Autowired
  BookRentService bookRentService;

  @PostMapping("/save")
  public ReturnFormat<RentalHeaderInfo> save(@RequestBody RentalHeaderInfo rentalHeaderInfo){
    return bookRentService.saveBookRent(rentalHeaderInfo);
  }

  @PostMapping("/returnbook")
  public ReturnFormat<RentalHeaderInfo> returnbook(@RequestBody RentalHeaderInfo rentalHeaderInfo){
    return bookRentService.returnBook(rentalHeaderInfo);
  }

  @PostMapping("/getrentlist")
  public ReturnFormat<RentalHeaderInfo> getReturnList(@RequestBody ListCriteriaInfo listCriteriaInfo) {
    return bookRentService.findBookRent(listCriteriaInfo);
  }

  @PostMapping("/readbyRentbookSyskey")
  public RentalHeaderInfo readbyRentbookSyskey(@RequestBody ListCriteriaInfo listCriteriaInfo){
    return bookRentService.findBookRentbySyskey(listCriteriaInfo.getSystemkey());
  }
  
  @PostMapping("/voidRent")
  public ReturnFormat<RentalHeaderInfo> voidRentBook(@RequestBody RentalHeaderInfo rentalHeaderInfo){
    return bookRentService.voidBookRent(rentalHeaderInfo);
  }
}
