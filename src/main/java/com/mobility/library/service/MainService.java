/**
 * 
 */
package com.mobility.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.repository.MainRepository;

/**
 * @author AKT
 *
 */
@Service
public class MainService {
	@Autowired
	private MainRepository mainRepository;
	
	public AuthorizedUser getLogin(AuthorizedUser data) {
	      try{
	    	  return mainRepository.loginUser(data.getUserid(),data.getPassword());
	      } catch (Exception ex){
	        return null;
	      }
	}
}
