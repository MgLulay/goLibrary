package com.mobility.library.repository;

import com.mobility.library.info.CategoryInfo;
import com.mobility.library.info.CategoryInfoMapper;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.ICategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {

	 @Autowired
	 private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from Category", Integer.class);
	}
	
	@Override
	public int save(CategoryInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO Category(systemkey,code,name,createddate, modifieddate,deletedstatus, remark) VALUES (?,?,?,?,?,?,?)";
		
		return jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getCode(),
				data.getName(),
				data.getCreateddate(),
				data.getModifieddate(),
				data.getDeletedstatus(),
				data.getRemark()
				);
	}
	
	@Override
	public int update(CategoryInfo data) {
		// TODO Auto-generated method stub
		String sql = " UPDATE Category set code=?, name=?, modifieddate=?, deletedstatus=?, remark=? where systemkey=? ";
		System.out.println(sql);
		return jdbcTemplate.update(sql,
				data.getCode(),
				data.getName(),
				data.getModifieddate(),
				data.getDeletedstatus(),
				data.getRemark(),
				data.getSystemkey()
				);
	}
	
	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update Category set deletedstatus = 4 WHERE systemkey = ? ", id);
	}
	
	@Override
	public List<CategoryInfo> findAll() {
		// TODO Auto-generated method stub
		 String sql = " SELECT systemkey, code, name, deletedstatus, remark FROM Category WHERE deletedstatus <> 4 ";
		return jdbcTemplate.query(sql, new CategoryInfoMapper());
	}
	
	@Override
	public ReturnFormat<CategoryInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		 ReturnFormat<CategoryInfo> returnFormat = new ReturnFormat<>();
		 List<CategoryInfo> dataList = new ArrayList<>();
		    String whereclause = "WHERE c.deletedstatus <> 4 ";
		    int totalcount = 0;
		    String sql = " SELECT c.systemkey, c.code, c.name, c.remark, c.deletedstatus FROM Category c ";
		    if (!listCriteriaInfo.getCode().equals("")) {
		      whereclause += " AND c.code like N'%" + listCriteriaInfo.getCode() + "%'";
		    }
		    if (!listCriteriaInfo.getName().equals("")) {
		      whereclause += " AND c.name like N'%" + listCriteriaInfo.getName() + "%'";
		    }
		    sql += whereclause;

		    int currentpage = listCriteriaInfo.getCurrentpage();
		    int pagesize = listCriteriaInfo.getPagesize();
		    int start = (currentpage - 1) * pagesize;
		    if (pagesize > 0)
		      sql += " ORDER BY code OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY";
		    else
		      sql += " ORDER BY code ";
		    
		    dataList = jdbcTemplate.query(sql, new CategoryInfoMapper());
		    returnFormat.setList(dataList);
		    
		    sql = " SELECT count(systemkey) as count FROM Category c "+whereclause;
		    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);
		    
		    returnFormat.setTotalcount(totalcount);
		    return returnFormat;
	}
	
	@Override
	public CategoryInfo findById(String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT systemkey, code, name, deletedstatus, remark FROM Category WHERE deletedstatus <> 1 AND systemkey=? ";
		RowMapper<CategoryInfo> rowMapper = new BeanPropertyRowMapper<CategoryInfo>(CategoryInfo.class);
		CategoryInfo category = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return category;
	}
	
	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM Category WHERE code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		
		return (flag > 0) ? true : false;
	}
}
