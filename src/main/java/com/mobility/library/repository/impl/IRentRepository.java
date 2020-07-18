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
 * @author Aung Kyaw Tin
 *
 */
public interface IRentRepository {


	int count();
	
	String getMaxRefNo();

    int saveRentalHeader(RentalHeaderInfo data);
    
    int saveRentalDetail(RentalDetailInfo data);

    int updateRentalHeader(RentalHeaderInfo data);
    
    int deleteById(String id);
    
    int deleteRentalDetail(String parentId);
    
    List<RentalHeaderInfo> findAll();
    
    ReturnFormat<RentalHeaderInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    RentalHeaderInfo findById(String id);
    
    List<RentalDetailInfo> getRentBookDetail(String parentId);
    
    int returnBook(RentalHeaderInfo rentalHeaderInfo);
    
    boolean isExists(String code, String id);
    
}
