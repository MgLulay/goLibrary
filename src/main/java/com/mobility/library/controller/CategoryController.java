package com.mobility.library.controller;

import com.mobility.library.info.CategoryInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @GetMapping("/")
  public String welcome() {
    return "Start up Library";
  }

  @PostMapping("/all")
  public ReturnFormat<CategoryInfo> findall(@RequestBody ListCriteriaInfo listCriteriaInfo) {
    return categoryService.findall(listCriteriaInfo);
  }

  @PostMapping("/findType")
  public CategoryInfo findBySyskey(@RequestBody ListCriteriaInfo listCriteriaInfo) {
    return categoryService.findBySyskey(listCriteriaInfo);
  }

  @PostMapping("/save")
  public ReturnFormat<CategoryInfo> save(@RequestBody CategoryInfo categoryInfo) {
    return categoryService.save(categoryInfo);
  }
}
