/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Aung Kyaw Tin
 * @version 1.0.1
 *
 */
public interface IUserRepository {

	int count();

    int save(AuthorizedUser data);

    int update(AuthorizedUser data);

    int deleteById(String id);
    
    List<AuthorizedUser> findAll();
    
    ReturnFormat<AuthorizedUser> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    AuthorizedUser findById(String id);
    
    boolean isExists(String code, String id);
}
