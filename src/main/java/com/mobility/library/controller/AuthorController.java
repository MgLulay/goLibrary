package com.mobility.library.controller;

import com.mobility.library.configuration.DatabaseProperties;
import com.mobility.library.info.AuthorInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("author")
public class AuthorController {

  @Autowired
  AuthorService authorService;

  @Autowired
  public DatabaseProperties dbProperties;

  @PostMapping("/all")
  private ReturnFormat<AuthorInfo> findall(@RequestBody ListCriteriaInfo listCriteriaInfo) {
    return authorService.findall(listCriteriaInfo);
  }

  @PostMapping("/findAuthor")
  private AuthorInfo readByAuthorSyskey(@RequestBody ListCriteriaInfo listCriteriaInfo) {
    return authorService.readByAuthorSyskey(listCriteriaInfo);
  }

  @PostMapping("/save")
  private ReturnFormat<AuthorInfo> save(@RequestBody AuthorInfo authorInfo){
    return authorService.save(authorInfo);
  }
}
