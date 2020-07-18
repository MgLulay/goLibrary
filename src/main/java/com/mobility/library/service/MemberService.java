package com.mobility.library.service;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.MemberInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.MemberRepository;
import com.mobility.library.utility.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

  @Autowired
  MemberRepository memberRepository;

  public ReturnFormat<MemberInfo> findall(ListCriteriaInfo listCriteriaInfo){
    ReturnFormat<MemberInfo> returnFormat = new ReturnFormat<>();
    try {
      return memberRepository.findByCriteria(listCriteriaInfo);

    } catch (Exception ex) {
      returnFormat.setMessage(ex.getMessage());
    }
    return returnFormat;
  }

  public MemberInfo findByMemberSyskey(ListCriteriaInfo listCriteriaInfo){
    MemberInfo memberInfo = new MemberInfo();
    try {
      memberInfo = memberRepository.findById(listCriteriaInfo.getSystemkey());
    } catch (Exception ex){

    }
    return memberInfo;
  }

  public ReturnFormat<MemberInfo> save(MemberInfo memberInfo) {
    ReturnFormat<MemberInfo> returnFormat = new ReturnFormat<>();
    try {
      int resultrow = 0;
      if(memberInfo.getSystemkey().equals("0") || memberInfo.getSystemkey().equals("")){
        memberInfo.setSystemkey(SystemUtility.keygen());
        if(!memberRepository.isExists(memberInfo.getCode(),memberInfo.getSystemkey())){
          resultrow =memberRepository.save(memberInfo);
        } else {
          returnFormat.setMessage("Code Exit.");
        }
      } else {
        if(!memberRepository.isExists(memberInfo.getCode(),memberInfo.getSystemkey())){
          resultrow =memberRepository.update(memberInfo);
        } else {
          returnFormat.setMessage("Code Exit.");
        }
      }
      if(resultrow>0) {
        List<MemberInfo> memberInfoList = new ArrayList<>();
        memberInfoList.add(memberInfo);
        returnFormat.setMessage("Success");
        returnFormat.setTotalcount(memberInfoList.size());
      }

    } catch (Exception ex) {
      returnFormat.setMessage(ex.getMessage());
    }
    return returnFormat;
  }
}
