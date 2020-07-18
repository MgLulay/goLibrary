package com.mobility.library.service;

import com.mobility.library.info.BookInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.BookRepository;
import com.mobility.library.utility.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public ReturnFormat<BookInfo> findAll(){
        ReturnFormat<BookInfo> returnFormat = new ReturnFormat<>();
        List<BookInfo> result = new ArrayList<BookInfo>();
        try{
        	result= bookRepository.findAll();
        	returnFormat.setList(result);
        	returnFormat.setMessage("SUCCESS");
        } catch (Exception ex){
             returnFormat.setMessage(ex.getMessage());
        }
        return returnFormat;
    }
    
    public ReturnFormat<BookInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo){
        ReturnFormat<BookInfo> returnFormat = new ReturnFormat<>();
        try{
          return bookRepository.findByCriteria(listCriteriaInfo);
        } catch (Exception ex){
             returnFormat.setMessage(ex.getMessage());
        }
        return returnFormat;
    }

    public BookInfo findbyBookSyskey(ListCriteriaInfo listCriteriaInfo) {
      try{
    	  return bookRepository.findById(listCriteriaInfo.getSystemkey());
      } catch (Exception ex){
        return new BookInfo();
      }
    }

    public ReturnFormat<BookInfo> save(BookInfo bookInfo){
      ReturnFormat<BookInfo> returnFormat = new ReturnFormat<BookInfo>();
        try{
            int effectedrow = 0;
            if(bookInfo.getSystemkey().equals("0") || bookInfo.getSystemkey().equals("")) {
              String syskey = SystemUtility.keygen();
              bookInfo.setSystemkey(syskey);
              if(!bookRepository.isExists(bookInfo.getCode(),bookInfo.getSystemkey())) {
                 effectedrow = bookRepository.save(bookInfo);
              } else {
                returnFormat.setMessage("Code Exist.");
              }
            }
            else {
              if(!bookRepository.isExists(bookInfo.getCode(),bookInfo.getSystemkey())) {
                 effectedrow = bookRepository.update(bookInfo);
              }  else {
                returnFormat.setMessage("Code Exist.");
              }
            }
            if(effectedrow > 0) {
              List<BookInfo> bookInfoList = new ArrayList<>();
              bookInfoList.add(bookInfo);
              returnFormat.setMessage("Success");
              returnFormat.setTotalcount(effectedrow);
              returnFormat.getList().addAll(bookInfoList);
            }
        } catch (Exception ex){
          returnFormat.setMessage("Fail");
        }

        return returnFormat;
    }


}
