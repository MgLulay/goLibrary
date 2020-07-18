/**
 * 
 */
package com.mobility.library.repository.impl;

import java.util.List;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.MemberInfo;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Aung Kyaw Tin
 *
 */
public interface IMemberRepository {

	int count();

    int save(MemberInfo data);

    int update(MemberInfo data);

    int deleteById(String id);
    
    List<MemberInfo> findAll();
    
    ReturnFormat<MemberInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    MemberInfo findById(String id);
    
    boolean isExists(String code, String id);
    
}
