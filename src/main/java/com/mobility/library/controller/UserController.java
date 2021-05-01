/**
 * 
 */
package com.mobility.library.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.UserService;

/**
 * @author AKT
 *
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/all")
	private ReturnFormat<AuthorizedUser> findall(HttpServletRequest request) {
		return userService.findall();
	}

	@PostMapping("/findByCriteria")
	private ReturnFormat<AuthorizedUser> findByCriteria(@RequestBody ListCriteriaInfo listCriteriaInfo) {
		return userService.findByCriteria(listCriteriaInfo);
	}

	@PostMapping("/findUser")
	private AuthorizedUser readBySyskey(@RequestBody ListCriteriaInfo listCriteriaInfo) {
		return userService.readBySyskey(listCriteriaInfo);
	}

	@PostMapping("/save")
	private ReturnFormat<AuthorizedUser> save(@RequestBody AuthorizedUser authorizedInfo) {
		return userService.save(authorizedInfo);
	}
	
	@GetMapping("/delete/{id}")
	public HashMap<String, String> deleteData(@PathVariable("id") String id, HttpServletRequest request) {
		return userService.delete(id);
	}
}
