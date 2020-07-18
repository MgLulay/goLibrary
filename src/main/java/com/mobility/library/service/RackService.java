package com.mobility.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Rack;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.RackRepository;
import com.mobility.library.utility.SystemUtility;

@Service
public class RackService {

	 @Autowired
	private RackRepository rankRepository;

	  public ReturnFormat<Rack> findall(ListCriteriaInfo listCriteriaInfo){

	    try {
	      return rankRepository.findByCriteria(listCriteriaInfo);

	    } catch (Exception ex) {
	      ReturnFormat<Rack> returnFormat = new ReturnFormat<Rack>();
	      returnFormat.setMessage("Fail");
	      return  returnFormat;
	    }
	  }

	  public Rack readById(ListCriteriaInfo listCriteriaInfo) {
		  Rack result = new Rack();
	    try {
	    	result = rankRepository.findById(listCriteriaInfo.getSystemkey());
	    } catch (Exception ex){

	    }
	    return result;
	  }

	  public ReturnFormat<Rack> save(Rack data) {
	    int effectedrow = 0;
	    ReturnFormat<Rack> returnFormat = new ReturnFormat<>();
	    try {
	       if(data.getSystemkey().equals("0") || data.getSystemkey().equals("")) {
	    	   data.setSystemkey(SystemUtility.keygen());
	         if(!rankRepository.isExists(data.getCode(),data.getSystemkey())) {
	           effectedrow = rankRepository.save(data);
	         } else {
	           returnFormat.setMessage("Code Exist.");
	         }
	       } else {
	         if(!rankRepository.isExists(data.getCode(),data.getSystemkey())) {
	           effectedrow = rankRepository.update(data);
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
