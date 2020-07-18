package com.mobility.library.service;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalDetailInfo;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.BookRentRepository;
import com.mobility.library.repository.BookRepository;
import com.mobility.library.utility.Status;
import com.mobility.library.utility.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class BookRentService {

	  @Autowired
	  BookRentRepository bookRentRepository;
	  @Autowired
	  BookRepository bookRepository;

	  public ReturnFormat<RentalHeaderInfo> saveBookRent(RentalHeaderInfo rentalHeaderInfo) {
	    int effectedrow = 0;
	    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
	    try {
	      if (rentalHeaderInfo.getSystemkey().equals("0") || rentalHeaderInfo.getSystemkey().equals("")) {
	        rentalHeaderInfo.setSystemkey(SystemUtility.keygen());
	        rentalHeaderInfo.setRefno(bookRentRepository.getMaxRefNo());
	        effectedrow = bookRentRepository.saveRentalHeader(rentalHeaderInfo);
	        if (effectedrow > 0) {
	          effectedrow = saveRentDetail(rentalHeaderInfo);
	        }
	      } else {
	          effectedrow = updateRentHeader(rentalHeaderInfo);
	          if(effectedrow> 0 ) {
	            effectedrow = saveRentDetail(rentalHeaderInfo);
	          }
	      }
	      if (effectedrow > 0) {
	        returnFormat.setMessage("Success");
	        returnFormat.setTotalcount(effectedrow);
	        returnFormat.getList().add(rentalHeaderInfo);
	      } else {
	        returnFormat.setMessage("Fail");
	      }

	    } catch (Exception ex) {
	      returnFormat.setMessage(ex.getMessage());
	    }
	    return returnFormat;
	  }

	  public ReturnFormat<RentalHeaderInfo> returnBook(RentalHeaderInfo rentalHeaderInfo) {
	    int effectedrow = 0;
	    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
	    try {
	     effectedrow = bookRentRepository.returnBook(rentalHeaderInfo);
	     if(effectedrow>0){
	       for(RentalDetailInfo rentalDetailInfo: rentalHeaderInfo.getRentalDetailInfoList()) {
	         effectedrow = bookRepository.updateStatusById(1, rentalDetailInfo.getBooksystemkey());
	       }
	       if(effectedrow>0){
	         returnFormat.setMessage("Success");
	       }
	     }
	    } catch (Exception ex) {
	      returnFormat.setMessage(ex.getMessage());
	    }
	    return returnFormat;
	  }

	  public int updateRentHeader(RentalHeaderInfo rentalHeaderInfo) throws SQLException {
	    int effectedrow = 0;
	    effectedrow = bookRentRepository.updateRentalHeader(rentalHeaderInfo);
	    if (effectedrow > 0) {
	      effectedrow = bookRentRepository.deleteRentalDetail(rentalHeaderInfo.getSystemkey());
	    }
	    return effectedrow;
	  }

	  public int saveRentDetail(RentalHeaderInfo rentalHeaderInfo) throws SQLException {

	    int effectedrow = 0;
	    for (int i = 0; i < rentalHeaderInfo.getRentalDetailInfoList().size(); i++) {
	      RentalDetailInfo rentalDetailInfo = rentalHeaderInfo.getRentalDetailInfoList().get(i);
	      rentalDetailInfo.setSystemkey(SystemUtility.keygen());
	      rentalDetailInfo.setParentsystemkey(rentalHeaderInfo.getSystemkey());
	      effectedrow = bookRentRepository.saveRentalDetail(rentalDetailInfo);
	      if((effectedrow > 0) && rentalHeaderInfo.getStatus() == Status.CONFIRM){
	          effectedrow = bookRepository.updateStatusById(Status.UNAVAILABLE, rentalDetailInfo.getBooksystemkey());
	      }
	    }
	    return effectedrow;
	  }

	  public ReturnFormat<RentalHeaderInfo> findBookRent(ListCriteriaInfo criteriaInfo) {
	    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
	    try {
	      returnFormat = bookRentRepository.findByCriteria(criteriaInfo);

	    } catch (Exception ex) {
	      returnFormat.setMessage(ex.getMessage());
	    }
	    return returnFormat;
	  }

	  public RentalHeaderInfo findBookRentbySyskey(String systemkey) {
	    try {
	      return bookRentRepository.findById(systemkey);

	    } catch (Exception ex) {
	       return null;
	    }
	  }
	  
	  public ReturnFormat<RentalHeaderInfo> voidBookRent(RentalHeaderInfo rentalInfo) {
		    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
		    int effectrow = 0;
		    try {
		      effectrow = bookRentRepository.deleteById(rentalInfo.getSystemkey());
		      if(effectrow > 0) {
		    	  returnFormat.setMessage("Success");
		      }else {
		    	  returnFormat.setMessage("Fail");
		      }

		    } catch (Exception ex) {
		      returnFormat.setMessage(ex.getMessage());
		    }
		    return returnFormat;
	 }
}
