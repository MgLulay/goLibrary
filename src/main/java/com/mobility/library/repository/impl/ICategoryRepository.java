/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.CategoryInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Aung Kyaw Tin
 *
 */
public interface ICategoryRepository {

	int count();

    int save(CategoryInfo data);

    int update(CategoryInfo data);

    int deleteById(String id);
    
    List<CategoryInfo> findAll();
    
    ReturnFormat<CategoryInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    CategoryInfo findById(String id);
    
    boolean isExists(String code, String id);
}
