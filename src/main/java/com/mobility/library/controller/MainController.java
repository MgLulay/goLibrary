/**
 * 
 */
package com.mobility.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.service.MainService;

/**
 * @author AKT
 *
 */
@RestController
@RequestMapping("main")
public class MainController {
	@Autowired
	private MainService mainService;
	
	@PostMapping("/login")
    public AuthorizedUser loginUser(@RequestBody AuthorizedUser data){
      return mainService.getLogin(data);
    }
}
