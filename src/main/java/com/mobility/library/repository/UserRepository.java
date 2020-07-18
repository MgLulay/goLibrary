package com.mobility.library.repository;

import com.mobility.library.info.AuthorizedUser;
import com.mobility.library.info.AuthorizedUserMapper;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IUserRepository;
import com.mobility.library.utility.SystemUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM U001 WHERE deletedstatus <> 4 AND code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from U001 where deletedstatus <> 4 ", Integer.class);
	}

	@Override
	public int save(AuthorizedUser data) {
		// TODO Auto-generated method stub
	    String sql = " INSERT INTO U001 (systemkey,createddate,modifieddate,code,name,password,passcode,userrole,phone,email,deletedstatus) " +
	            " values (?,?,?,?,?,?,?,?,?,?,?)";
	 
	return jdbcTemplate.update(sql,
			data.getSystemkey(),
			SystemUtility.getCurrentDate(),
			SystemUtility.getCurrentDate(),
			data.getUserid(),
			data.getUsername(),
			data.getPassword(),
			data.getPasscode(),
			data.getUserrole(),
			data.getPhone(),
			data.getEmail(),
			data.getDeletedstatus()
			);
	}

	@Override
	public int update(AuthorizedUser data) {
		// TODO Auto-generated method stub
		String sql = " UPDATE U001 SET modifieddate=?, code=?, name=?, password=?, passcode=?, userrole=?,phone=?,email=?, deletedstatus=? WHERE systemkey = ? " ;
		return jdbcTemplate.update(sql,
				SystemUtility.getCurrentDate(),
				data.getUserid(),
				data.getUsername(),
				data.getPassword(),
				data.getPasscode(),
				data.getUserrole(),
				data.getPhone(),
				data.getEmail(),
				data.getDeletedstatus(),
				data.getSystemkey()
				);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update U001 set deletedstatus = 4 WHERE systemkey = ? ", id);
	}

	@Override
	public List<AuthorizedUser> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT systemkey, code, name, password, passcode, userrole, phone, email, deletedstatus FROM U001 WHERE deletedstatus <> 4 ";
		return jdbcTemplate.query(sql, new AuthorizedUserMapper());
	}

	@Override
	public ReturnFormat<AuthorizedUser> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		 ReturnFormat<AuthorizedUser> returnFormat = new ReturnFormat<>();
		 List<AuthorizedUser> dataList = new ArrayList<>();
		 String whereclause = "where 1=1 AND deletedstatus <> 4 ";
		    int totalcount = 0;
		    String sql = " SELECT systemkey, code, name, password, passcode, userrole, phone, email, deletedstatus FROM U001 ";
		    if (!listCriteriaInfo.getCode().equals("")) {
		      whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%' OR name like N'%" + listCriteriaInfo.getCode() + "%'";
		    }
		    if (!listCriteriaInfo.getName().equals("")) {
		      whereclause += " AND name like N'%" + listCriteriaInfo.getName() + "%'";
		    }
		    
		    sql += whereclause;
		    int currentpage = listCriteriaInfo.getCurrentpage();
		    int pagesize = listCriteriaInfo.getPagesize();
		    int start = (currentpage - 1) * pagesize;
		    if (pagesize > 0) {
		      sql += " ORDER BY systemkey OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY ";
		    } else {
		      sql += " ORDER BY systemkey  ";
		    }
		    dataList= jdbcTemplate.query(sql, new AuthorizedUserMapper());
		    sql = " SELECT count(systemkey) as count FROM U001 "+whereclause;
		    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);
		    
	    returnFormat.setTotalcount(totalcount);
	    returnFormat.setList(dataList);
		return returnFormat;
	}

	@Override
	public AuthorizedUser findById(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT systemkey, code, name, password, passcode, userrole, phone, email, deletedstatus FROM U001 WHERE deletedstatus <> 4 AND systemkey =? ";
		AuthorizedUser result = jdbcTemplate.queryForObject(sql, new AuthorizedUserMapper(), id);
		return result;
	}
}
