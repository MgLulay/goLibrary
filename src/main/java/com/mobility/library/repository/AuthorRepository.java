package com.mobility.library.repository;

import com.mobility.library.info.AuthorInfo;
import com.mobility.library.info.AuthorInfoMapper;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IAuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepository implements IAuthorRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from Author", Integer.class);
	}
	
	@Override
	public int save(AuthorInfo data) {
		// TODO Auto-generated method stub
		 String sql = " INSERT INTO Author(systemkey, code, name, deletedstatus) values " +
		            " (?,?,?,?) ";
		 
		return jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getCode(),
				data.getName(),
				data.getDeletedstatus()
				);
	}
	
	@Override
	public int update(AuthorInfo data) {
		// TODO Auto-generated method stub
		String sql = " UPDATE Author SET code=?, name=?, deletedstatus=? WHERE systemkey = ? " ;
		 
		return jdbcTemplate.update(sql,
				data.getCode(),
				data.getName(),
				data.getDeletedstatus(),
				data.getSystemkey()
				);
	}
	
	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update Author set deletedstatus = 4 WHERE systemkey = ? ", id);
	}
	
	@Override
	public List<AuthorInfo> findAll() {
		// TODO Auto-generated method stub
		 String sql = " SELECT systemkey, code, name, deletedstatus FROM Author WHERE deletedstatus <> 4 ";
			return jdbcTemplate.query(sql, new AuthorInfoMapper());
	}
	
	@Override
	public ReturnFormat<AuthorInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		ReturnFormat<AuthorInfo> returnFormat = new ReturnFormat<AuthorInfo>();
		List<AuthorInfo> dataList = new ArrayList<>();
	    int totalcount = 0;
	    String whereclause = "";
	    String sql = " SELECT systemkey, code, name, deletedstatus FROM Author WHERE deletedstatus <> 1";
	    if(!listCriteriaInfo.getCode().equals("")){
	      whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%' OR name like N'%"
				+  listCriteriaInfo.getCode() + "%' ";
	    }
	    if(!listCriteriaInfo.getName().equals("")){
	      whereclause += " AND name like N'%" + listCriteriaInfo.getName() + "%'";
	    }
	    sql += whereclause;

	    int currentpage = listCriteriaInfo.getCurrentpage();
	    int pagesize =listCriteriaInfo.getPagesize();
	    int start = (currentpage - 1) * pagesize;
	    if(pagesize > 0) {
	      sql +=  " ORDER BY systemkey OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY";
	    } else {
	      sql += " ORDER BY systemkey ";
	    }

	    dataList = jdbcTemplate.query(sql, new AuthorInfoMapper());
	    returnFormat.setList(dataList);
	    

	    sql = "SELECT  count(systemkey) count FROM Author WHERE deletedstatus <> 1" ;
	    sql += whereclause;
	    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);
	    returnFormat.setTotalcount(totalcount);

	    returnFormat.setTotalcount(totalcount);
	    return  returnFormat;
	}
	
	@Override
	public AuthorInfo findById(String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT systemkey, code, name, deletedstatus FROM Author WHERE deletedstatus <> 1 AND systemkey=? ";
		RowMapper<AuthorInfo> rowMapper = new BeanPropertyRowMapper<AuthorInfo>(AuthorInfo.class);
		AuthorInfo author = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return author;
	}
	
	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM Author WHERE code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}



}

