/**
 * 
 */
package com.mobility.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalDetailInfo;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.BookReturnService;

/**
 * @author Aung Kyaw Tin
 *
 */
@RestController
@RequestMapping("return")
public class BookReturnController {
	
	 @Autowired
	 BookReturnService bookReturnService;

	  @PostMapping("/save")
	  public ReturnFormat<RentalHeaderInfo> save(@RequestBody RentalHeaderInfo rentalHeaderInfo){
	    return bookReturnService.saveBookReturn(rentalHeaderInfo);
	  }

//	  @PostMapping("/returnbook")
//	  public ReturnFormat<RentalHeaderInfo> returnbook(@RequestBody RentalHeaderInfo rentalHeaderInfo){
//	    return bookReturnService.returnBook(rentalHeaderInfo);
//	  }

	  @PostMapping("/getReturnList")
	  public ReturnFormat<RentalHeaderInfo> getReturnList(@RequestBody ListCriteriaInfo listCriteriaInfo) {
	    return bookReturnService.findBookReturn(listCriteriaInfo);
	  }

	  @PostMapping("/readbySyskey")
	  public RentalHeaderInfo readbyRentbookSyskey(@RequestBody ListCriteriaInfo listCriteriaInfo){
	    return bookReturnService.findBookRentbySyskey(listCriteriaInfo.getSystemkey());
	  }
	  
	  @PostMapping("/getBookByMember")
	  public List<RentalDetailInfo> getDataByMember(@RequestBody ListCriteriaInfo listCriteriaInfo){
	    return bookReturnService.findBookByMember(listCriteriaInfo.getSystemkey());
	  }

	  @PostMapping("/getRentalRefByMember")
	  public  ReturnFormat<RentalHeaderInfo> getRentalHeaderInfo(@RequestBody ListCriteriaInfo listCriteriaInfo){
	  	return bookReturnService.findRentalReferenceByMember(listCriteriaInfo);
	  }
}
