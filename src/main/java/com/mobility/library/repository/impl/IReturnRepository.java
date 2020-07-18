/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalDetailInfo;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Admin
 *
 */
public interface IReturnRepository {


	int count();
	
	String getMaxRefNo();

    int saveReturnHeader(RentalHeaderInfo data);
    
    int saveReturnDetail(RentalDetailInfo data);

    int updateReturnHeader(RentalHeaderInfo data);
    
    int deleteReturnDetail(String parentId);
    
    int deleteById(String id);
    
    List<RentalHeaderInfo> findAll();
    
    ReturnFormat<RentalHeaderInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo);
    
    ReturnFormat<RentalHeaderInfo> getRentalReferenceByMember(String memberkey, int currentPage, int pageSize);

    RentalHeaderInfo findById(String id);
    
    List<RentalDetailInfo> getReturnBookDetail(String parentId);
    
    int returnBook(RentalHeaderInfo rentalHeaderInfo);
    
    List<RentalDetailInfo> getReturnBookByMember(String memberId);
    
    boolean isExists(String code, String id);
    
}
