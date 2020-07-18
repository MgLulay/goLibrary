/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Rack;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Aung Kyaw Tin
 *
 */
public interface IRackRepository {
	
	int count();

    int save(Rack data);

    int update(Rack data);

    int deleteById(String id);
    
    List<Rack> findAll();
    
    ReturnFormat<Rack> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    Rack findById(String id);
    
    boolean isExists(String code, String id);
}
