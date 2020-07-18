package com.mobility.library.service;

import com.mobility.library.info.AuthorInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.AuthorRepository;
import com.mobility.library.utility.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
  @Autowired
  AuthorRepository authorRepository;

  public ReturnFormat<AuthorInfo> findall(ListCriteriaInfo listCriteriaInfo){

    try {
      return authorRepository.findByCriteria(listCriteriaInfo);

    } catch (Exception ex) {
      ReturnFormat<AuthorInfo> returnFormat = new ReturnFormat<AuthorInfo>();
      returnFormat.setMessage("Fail");
      return  returnFormat;
    }
  }

  public AuthorInfo readByAuthorSyskey(ListCriteriaInfo listCriteriaInfo) {
    AuthorInfo authorInfo = new AuthorInfo();
    try {
      authorInfo = authorRepository.findById(listCriteriaInfo.getSystemkey());
    } catch (Exception ex){

    }
    return authorInfo;
  }

  public ReturnFormat<AuthorInfo> save(AuthorInfo authorInfo) {
    int effectedrow = 0;
    ReturnFormat<AuthorInfo> returnFormat = new ReturnFormat<AuthorInfo>();
    try {
       if(authorInfo.getSystemkey().equals("0") || authorInfo.getSystemkey().equals("")) {
         authorInfo.setSystemkey(SystemUtility.keygen());
         if(!authorRepository.isExists(authorInfo.getCode(),authorInfo.getSystemkey())) {
           effectedrow = authorRepository.save(authorInfo);
         } else {
           returnFormat.setMessage("Code Exist.");
         }
       } else {
         if(!authorRepository.isExists(authorInfo.getCode(),authorInfo.getSystemkey())) {
           effectedrow = authorRepository.update(authorInfo);
         } else {
           returnFormat.setMessage("Code Exist.");
         }
       }
       if(effectedrow > 0){
         List<AuthorInfo> authorInfoList = new ArrayList<>();
         authorInfoList.add(authorInfo);
         returnFormat.setMessage("Success");
         returnFormat.getList().addAll(authorInfoList);
         returnFormat.setTotalcount(effectedrow);
       }
    } catch (Exception ex) {
      returnFormat.setMessage("Fail");
    }
    return returnFormat;
  }
}
