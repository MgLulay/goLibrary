package com.mobility.library.controller;

import com.mobility.library.info.BookInfo;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.BookService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;
    
    @RequestMapping("/")
	public String welcome() {
		return "index";
	}

    @PostMapping("/all")
    private ReturnFormat<BookInfo> findbook(@RequestBody  ListCriteriaInfo listCriteriaInfo) {
        return bookService.findByCriteria(listCriteriaInfo);
    }
    
    @GetMapping("/getBookList")
    private ReturnFormat<BookInfo> findallbook(HttpServletRequest request) {
        return bookService.findAll();
    }

    @PostMapping("/findBook")
    private BookInfo findbySyskey(@RequestBody ListCriteriaInfo listCriteriaInfo ) {
      return bookService.findbyBookSyskey(listCriteriaInfo);
    }

    @PostMapping("/save")
    private ReturnFormat<BookInfo> save(@RequestBody BookInfo bookInfo){
      return bookService.save(bookInfo);
    }
}
