/**
 * 
 */
package com.mobility.library.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import com.mobility.library.info.BookInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;

/**
 * @author Admin
 *
 */
public interface IBookRepository {

	 int count();

    int save(BookInfo book);

    int update(BookInfo book);

    int deleteById(String id);
    
    int updateStatusById(int status, String id);

    List<BookInfo> findAll();

    List<BookInfo> findByNameAndPrice(String name, BigDecimal price);
    
    ReturnFormat<BookInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo);

    BookInfo findById(String id);

    String getNameById(Long id);
    
    boolean isExists(String title, String id);
}
