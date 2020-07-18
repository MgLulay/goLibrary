package com.mobility.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Publisher;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.PublisherService;

@RestController
@RequestMapping("publisher")
public class PublisherController {

	@Autowired
	PublisherService publisherService;

	  @PostMapping("/all")
	  private ReturnFormat<Publisher> findall(@RequestBody ListCriteriaInfo listCriteriaInfo) {
	    return publisherService.findall(listCriteriaInfo);
	  }

	  @PostMapping("/findPublisher")
	  private Publisher readById(@RequestBody ListCriteriaInfo listCriteriaInfo) {
	    return publisherService.readById(listCriteriaInfo);
	  }

	  @PostMapping("/save")
	  private ReturnFormat<Publisher> save(@RequestBody Publisher data){
	    return publisherService.save(data);
	  }
}
