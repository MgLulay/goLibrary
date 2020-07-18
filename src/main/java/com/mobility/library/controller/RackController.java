package com.mobility.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Rack;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.RackService;


@RestController
@RequestMapping("rack")
public class RackController {

	@Autowired
	RackService rackService;

	  @PostMapping("/all")
	  private ReturnFormat<Rack> findall(@RequestBody ListCriteriaInfo listCriteriaInfo) {
	    return rackService.findall(listCriteriaInfo);
	  }

	  @PostMapping("/findRack")
	  private Rack readById(@RequestBody ListCriteriaInfo listCriteriaInfo) {
	    return rackService.readById(listCriteriaInfo);
	  }

	  @PostMapping("/save")
	  private ReturnFormat<Rack> save(@RequestBody Rack data){
	    return rackService.save(data);
	  }
}
