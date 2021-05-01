package com.mobility.library.service;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.MemberInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.MemberRepository;
import com.mobility.library.utility.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
  public HashMap<String, String> delete(String id) {
		HashMap<String, String> result = new HashMap<>();
		int effectedRow = 0;
		try {
			if(!"0".equals(id) && !"".equals(id)) {
				effectedRow = memberRepository.deleteById(id);
			}else {
				result.put("message", "CAN'T DELETE ");
				return result;
			}
			if (effectedRow > 0) {
				result.put("message", "SUCCESS");
			} else {
				result.put("message", "FAIL");
			}
		} catch (Exception e) {
			result.put("message", "FAIL");
		}
		return result;
	}
}
