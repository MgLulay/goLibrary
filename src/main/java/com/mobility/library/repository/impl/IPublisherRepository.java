/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Publisher;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Admin
 *
 */
public interface IPublisherRepository {


	int count();

    int save(Publisher data);

    int update(Publisher data);

    int deleteById(String id);
    
    List<Publisher> findAll();
    
    ReturnFormat<Publisher> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    Publisher findById(String id);
    
    boolean isExists(String code, String id);
    
}
