/**
 * 
 */
package com.mobility.library.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalDetailInfo;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.BookRentRepository;
import com.mobility.library.repository.BookRepository;
import com.mobility.library.repository.BookReturnRepository;
import com.mobility.library.utility.Status;
import com.mobility.library.utility.SystemUtility;

/**
 * @author Admin
 *
 */

@Service
public class BookReturnService {

	  @Autowired
	  BookReturnRepository returnRepository;
	  
	  @Autowired
	  BookRepository bookRepository;
	  
	  @Autowired
	  BookRentRepository bookRentRepository;

	  public ReturnFormat<RentalHeaderInfo> saveBookReturn(RentalHeaderInfo rentalHeaderInfo) {
	    int effectedrow = 0;
	    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
	    try {
	      if (rentalHeaderInfo.getSystemkey().equals("0") || rentalHeaderInfo.getSystemkey().equals("")) {
	        rentalHeaderInfo.setSystemkey(SystemUtility.keygen());
	        rentalHeaderInfo.setRefno(returnRepository.getMaxRefNo());
	        effectedrow = returnRepository.saveReturnHeader(rentalHeaderInfo);
	        if (effectedrow > 0) {
	          effectedrow = saveReturnDetail(rentalHeaderInfo);
	        }
	      } else {
	          effectedrow = updateReturnHeader(rentalHeaderInfo);
	          if(effectedrow> 0 ) {
	            effectedrow = saveReturnDetail(rentalHeaderInfo);
	          }
	      }
	      if (effectedrow > 0) {
	    	  if(rentalHeaderInfo.getStatus() == Status.CONFIRM) {
	    		  effectedrow = bookRentRepository.returnBook(rentalHeaderInfo);
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

//	  public ReturnFormat<RentalHeaderInfo> returnBook(RentalHeaderInfo rentalHeaderInfo) {
//	    int effectedrow = 0;
//	    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
//	    try {
//	     effectedrow = returnRepository.returnBook(rentalHeaderInfo);
//	     if(effectedrow>0){
//	       for(RentalDetailInfo rentalDetailInfo: rentalHeaderInfo.getRentalDetailInfoList()) {
//	         effectedrow = bookRepository.updateStatusById(1, rentalDetailInfo.getBooksystemkey());
//	       }
//	       if(effectedrow>0){
//	         returnFormat.setMessage("Success");
//	       }
//	     }
//	    } catch (Exception ex) {
//	      returnFormat.setMessage(ex.getMessage());
//	    }
//	    return returnFormat;
//	  }

	  public int updateReturnHeader(RentalHeaderInfo rentalHeaderInfo) throws SQLException {
	    int effectedrow = 0;
	    effectedrow = returnRepository.updateReturnHeader(rentalHeaderInfo);
	    if (effectedrow > 0) {
	      effectedrow = returnRepository.deleteReturnDetail(rentalHeaderInfo.getSystemkey());
	    }
	    return effectedrow;
	  }

	  public int saveReturnDetail(RentalHeaderInfo rentalHeaderInfo) throws SQLException {

	    int effectedrow = 0;
	    for (int i = 0; i < rentalHeaderInfo.getRentalDetailInfoList().size(); i++) {
	      RentalDetailInfo rentalDetailInfo = rentalHeaderInfo.getRentalDetailInfoList().get(i);
	      rentalDetailInfo.setSystemkey(SystemUtility.keygen());
	      rentalDetailInfo.setParentsystemkey(rentalHeaderInfo.getSystemkey());
	      effectedrow = returnRepository.saveReturnDetail(rentalDetailInfo);
	      if((effectedrow > 0) && rentalHeaderInfo.getStatus() == Status.CONFIRM){
	          effectedrow = bookRepository.updateStatusById(Status.AVAILABLE, rentalDetailInfo.getBooksystemkey());
	      }
	    }
	    return effectedrow;
	  }

	  public ReturnFormat<RentalHeaderInfo> findBookReturn(ListCriteriaInfo criteriaInfo) {
	    ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
	    try {
	      returnFormat = returnRepository.findByCriteria(criteriaInfo);

	    } catch (Exception ex) {
	      returnFormat.setMessage(ex.getMessage());
	    }
	    return returnFormat;
	  }

	  public RentalHeaderInfo findBookRentbySyskey(String systemkey) {
	    try {
	      return returnRepository.findById(systemkey);

	    } catch (Exception ex) {
	       return null;
	    }
	  }
	
	//----------------------------------------------
	  
	  public List<RentalDetailInfo> findBookByMember(String mSystemkey) {
		    try {
		      return returnRepository.getReturnBookByMember(mSystemkey);
		    } catch (Exception ex) {
		       return null;
		    }
	  }

	  public ReturnFormat<RentalHeaderInfo> findRentalReferenceByMember(ListCriteriaInfo listCriteriaInfo){
	  	try {
		  		String memberkey = listCriteriaInfo.getSystemkey();
		  		int currentPage = listCriteriaInfo.getCurrentpage();
		  		int pageSize = listCriteriaInfo.getPagesize();
		  		return returnRepository.getRentalReferenceByMember(memberkey,currentPage,pageSize);
			} catch (Exception ex) {
				return null;
			}
	  }

}
