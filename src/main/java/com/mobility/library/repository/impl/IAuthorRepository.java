/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.AuthorInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Aung Kyaw Tin
 *
 */
public interface IAuthorRepository {
	
	int count();

    int save(AuthorInfo data);

    int update(AuthorInfo data);

    int deleteById(String id);
    
    List<AuthorInfo> findAll();
    
    ReturnFormat<AuthorInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    AuthorInfo findById(String id);
    
    boolean isExists(String code, String id);
}
