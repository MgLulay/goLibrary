package com.mobility.library.service;

import com.mobility.library.info.CategoryInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.CategoryRepository;
import com.mobility.library.utility.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public ReturnFormat<CategoryInfo> findall(ListCriteriaInfo listCriteriaInfo) {
    ReturnFormat<CategoryInfo> returnFormat = new ReturnFormat<>();
    try {
      return categoryRepository.findByCriteria(listCriteriaInfo);

    } catch (Exception ex) {
       returnFormat.setMessage(ex.getMessage());
    }
    return returnFormat;

  }

  public CategoryInfo findBySyskey(ListCriteriaInfo listCriteriaInfo){
    CategoryInfo categoryInfo = new CategoryInfo();
    try {
      categoryInfo = categoryRepository.findById(listCriteriaInfo.getSystemkey());
    } catch (Exception ex){

    }
    return categoryInfo;
  }

  public ReturnFormat<CategoryInfo> save(CategoryInfo categoryInfo) {
    ReturnFormat<CategoryInfo> returnFormat = new ReturnFormat<>();
    //returnFormat.setMessage("Fail");
    try {
      int resultrow = 0;
      if (categoryInfo.getSystemkey().equals("") || categoryInfo.getSystemkey().equals("0")) {
        String systemkey = SystemUtility.keygen();
        categoryInfo.setSystemkey(systemkey);
        if (!categoryRepository.isExists(categoryInfo.getCode(),categoryInfo.getSystemkey())) {
          resultrow = categoryRepository.save(categoryInfo);
        } else {
          returnFormat.setMessage("Code Exist.");
        }
      } else {
    	  System.out.println("hello"+categoryInfo.getSystemkey());
        if (!categoryRepository.isExists(categoryInfo.getCode(),categoryInfo.getSystemkey())) {
          resultrow = categoryRepository.update(categoryInfo);
        } else {
          returnFormat.setMessage("Code Exist.");
        }
      }
      if (resultrow > 0) {
        List<CategoryInfo> newList = new ArrayList<>();
        newList.add(categoryInfo);
        returnFormat.setMessage("Success");
        returnFormat.setTotalcount(resultrow);
        returnFormat.getList().addAll(newList);
      }
      return returnFormat;
    } catch (Exception ex) {
      return returnFormat;
    }
  }


}
