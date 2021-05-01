package com.mobility.library.controller;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.MemberInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.service.MemberService;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member")
public class MemberController {
  @Autowired
  MemberService memberService;

  @PostMapping("/all")
  public ReturnFormat<MemberInfo> findall(@RequestBody ListCriteriaInfo listCriteriaInfo){
    return memberService.findall(listCriteriaInfo);
  }

  @PostMapping("/findMember")
  public MemberInfo findByMemberSyskey(@RequestBody ListCriteriaInfo listCriteriaInfo){
    return memberService.findByMemberSyskey(listCriteriaInfo);
  }

  @PostMapping("/save")
  public ReturnFormat<MemberInfo> save(@RequestBody MemberInfo memberInfo){
    return memberService.save(memberInfo);
  }
  @GetMapping("/delete/{id}")
	public HashMap<String, String> deleteData(@PathVariable("id") String id, HttpServletRequest request) {
		return memberService.delete(id);
  }
}
