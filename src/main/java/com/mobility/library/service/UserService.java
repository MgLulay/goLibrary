/**
 * 
 */
package com.mobility.library.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.UserRepository;
import com.mobility.library.utility.SystemUtility;

/**
 * @author AKT
 *
 */
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public ReturnFormat<AuthorizedUser> findall() {
		ReturnFormat<AuthorizedUser> returnFormat = new ReturnFormat<AuthorizedUser>();
		List<AuthorizedUser> result = new ArrayList<AuthorizedUser>();
		try {
			result = userRepository.findAll();
			returnFormat.setList(result);
			returnFormat.setMessage("SUCCESS");
			return returnFormat;

		} catch (Exception ex) {
			returnFormat.setMessage("Fail");
			return returnFormat;
		}
	}

	public ReturnFormat<AuthorizedUser> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		try {
			return userRepository.findByCriteria(listCriteriaInfo);

		} catch (Exception ex) {
			ReturnFormat<AuthorizedUser> returnFormat = new ReturnFormat<AuthorizedUser>();
			returnFormat.setMessage("Fail");
			return returnFormat;
		}
	}

	public AuthorizedUser readBySyskey(ListCriteriaInfo listCriteriaInfo) {
		AuthorizedUser data = new AuthorizedUser();
		try {
			data = userRepository.findById(listCriteriaInfo.getSystemkey());
		} catch (Exception ex) {

		}
		return data;
	}

	public ReturnFormat<AuthorizedUser> save(AuthorizedUser data) {
		int effectedrow = 0;
		ReturnFormat<AuthorizedUser> returnFormat = new ReturnFormat<AuthorizedUser>();
		try {
			if (data.getSystemkey().equals("0") || data.getSystemkey().equals("")) {
				data.setSystemkey(SystemUtility.keygen());
				if (!userRepository.isExists(data.getUserid(), data.getSystemkey())) {
					effectedrow = userRepository.save(data);
				} else {
					returnFormat.setMessage("Code Exist.");
				}
			} else {
				if (!userRepository.isExists(data.getUserid(), data.getSystemkey())) {
					effectedrow = userRepository.update(data);
				} else {
					returnFormat.setMessage("Code Exist.");
				}
			}
			if (effectedrow > 0) {
				List<AuthorizedUser> dataList = new ArrayList<>();
				dataList.add(data);
				returnFormat.setMessage("Success");
				returnFormat.getList().addAll(dataList);
				returnFormat.setTotalcount(effectedrow);
			}
		} catch (Exception ex) {
			returnFormat.setMessage("Fail");
		}
		return returnFormat;
	}
	
	public HashMap<String, String> delete(String id) {
		HashMap<String, String> result = new HashMap<>();
		int effectedRow = 0;
		try {
			if(!"0".equals(id) && !"".equals(id)) {
				effectedRow = userRepository.deleteById(id);
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
