package com.mobility.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Publisher;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.PublisherRepository;
import com.mobility.library.utility.SystemUtility;

@Service
public class PublisherService {

	 @Autowired
	private PublisherRepository publisherRepository;

	  public ReturnFormat<Publisher> findall(ListCriteriaInfo listCriteriaInfo){

	    try {
	      return publisherRepository.findByCriteria(listCriteriaInfo);

	    } catch (Exception ex) {
	      ReturnFormat<Publisher> returnFormat = new ReturnFormat<Publisher>();
	      returnFormat.setMessage("Fail");
	      return  returnFormat;
	    }
	  }

	  public Publisher readById(ListCriteriaInfo listCriteriaInfo) {
		  Publisher result = new Publisher();
	    try {
	    	result = publisherRepository.findById(listCriteriaInfo.getSystemkey());
	    } catch (Exception ex){

	    }
	    return result;
	  }

	  public ReturnFormat<Publisher> save(Publisher data) {
	    int effectedrow = 0;
	    ReturnFormat<Publisher> returnFormat = new ReturnFormat<Publisher>();
	    try {
	       if(data.getSystemkey().equals("0") || data.getSystemkey().equals("")) {
	    	   data.setSystemkey(SystemUtility.keygen());
	         if(!publisherRepository.isExists(data.getCode(),data.getSystemkey())) {
	           effectedrow = publisherRepository.save(data);
	         } else {
	           returnFormat.setMessage("Code Exist.");
	         }
	       } else {
	         if(!publisherRepository.isExists(data.getCode(),data.getSystemkey())) {
	           effectedrow = publisherRepository.update(data);
	         } else {
	           returnFormat.setMessage("Code Exist.");
	         }
	       }
	       if(effectedrow > 0){
	         returnFormat.setMessage("Success");
	         returnFormat.setTotalcount(effectedrow);
	       }
	    } catch (Exception ex) {
	      returnFormat.setMessage("Fail");
	    }
	    return returnFormat;
	  }
}
