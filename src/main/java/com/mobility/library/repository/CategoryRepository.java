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
//	 
//  public ReturnFormat<CategoryInfo> findall(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
//    ReturnFormat<CategoryInfo> returnFormat = new ReturnFormat<>();
//    String whereclause = "";
//    int totalcount = 0;
//    String sql = " SELECT c.systemkey, c.code, c.name FROM Category c WHERE c.deletedstatus <> 1 ";
//    if (!listCriteriaInfo.getCode().equals("")) {
//      whereclause += " AND c.code like N'%" + listCriteriaInfo.getCode() + "%'";
//    }
//    if (listCriteriaInfo.getName().equals("")) {
//      whereclause += " AND c.name like N'%" + listCriteriaInfo.getName() + "%'";
//    }
//    sql += whereclause;
//
//    int currentpage = listCriteriaInfo.getCurrentpage();
//    int pagesize = listCriteriaInfo.getPagesize();
//    int start = (currentpage - 1) * pagesize;
//    if (pagesize > 0) {
//      sql += " ORDER BY systemkey OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY";
//    } else {
//      sql += " ORDER BY systemkey ";
//    }
//    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//    ResultSet resultSet = preparedStatement.executeQuery();
//    while (resultSet.next()) {
//      CategoryInfo categoryInfo = new CategoryInfo();
//      categoryInfo.setSystemkey(resultSet.getString("systemkey"));
//      categoryInfo.setCode(resultSet.getString("code"));
//      categoryInfo.setName(resultSet.getString("name"));
//      returnFormat.getList().add(categoryInfo);
//    }
//
//    sql = " SELECT count(systemkey) count FROM Category WHERE deletedstatus <> 1";
//    preparedStatement = connection.prepareStatement(sql);
//    ResultSet rs = preparedStatement.executeQuery();
//    if(rs.next()){
//      totalcount = rs.getInt("count");
//    }
//    returnFormat.setTotalcount(totalcount);
//    return returnFormat;
//
//  }
//
//  public int checkcodeExit(CategoryInfo categoryInfo, Connection connection) throws SQLException {
//    String sql = " SELECT systemkey FROM Category WHERE deletedstatus<> 1 AND code = '" + categoryInfo.getCode() + "' AND systemkey <> " +
//            categoryInfo.getSystemkey();
//    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//    ResultSet rs = preparedStatement.executeQuery();
//    if (rs.next()) {
//      return 1;
//    } else {
//      return 0;
//    }
//  }
//
//  public CategoryInfo findByTypeSyskey(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
//    CategoryInfo categoryInfo = new CategoryInfo();
//    String sql = " SELECT systemkey, code, name FROM Category WHERE deletedstatus <> 1 " +
//            " AND systemkey = " + listCriteriaInfo.getSystemkey();
//    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//    ResultSet rs = preparedStatement.executeQuery();
//    while (rs.next()) {
//      categoryInfo.setSystemkey(rs.getString("systemkey"));
//      categoryInfo.setName(rs.getString("name"));
//      categoryInfo.setCode(rs.getString("code"));
//    }
//    return categoryInfo;
//  }
//
//  public int save(CategoryInfo categoryInfo, Connection connection) throws SQLException {
//    String sql = " INSERT INTO Category(systemkey,code,name,createddate, modifieddate,deletedstatus) VALUES (?,?,?,?,?,?)";
//    PreparedStatement insertStatement = connection.prepareStatement(sql);
//    insertStatement.setString(1, categoryInfo.getSystemkey());
//    insertStatement.setString(2, categoryInfo.getCode());
//    insertStatement.setString(3, categoryInfo.getName());
//    insertStatement.setString(4, categoryInfo.getCreateddate());
//    insertStatement.setString(5, categoryInfo.getModifieddate());
//    insertStatement.setInt(6, categoryInfo.getDeletedstatus());
//    int effectedrow = insertStatement.executeUpdate();
//    return effectedrow;
//  }
//
//  public int update(CategoryInfo categoryInfo, Connection connection) throws SQLException {
//    String sql = " UPDATE Category set name=?, modifieddate=?, deletedstatus=? where systemkey=? ";
//    PreparedStatement updateStatement = connection.prepareStatement(sql);
//    updateStatement.setString(1, categoryInfo.getName());
//    updateStatement.setString(2, categoryInfo.getModifieddate());
//    updateStatement.setInt(3, categoryInfo.getDeletedstatus());
//    updateStatement.setString(4, categoryInfo.getSystemkey());
//    int effectedrow = updateStatement.executeUpdate();
//    return effectedrow;
//  }
	
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
		    if (listCriteriaInfo.getName().equals("")) {
		      whereclause += " AND c.name like N'%" + listCriteriaInfo.getName() + "%'";
		    }
		    sql += whereclause;

		    int currentpage = listCriteriaInfo.getCurrentpage();
		    int pagesize = listCriteriaInfo.getPagesize();
		    int start = (currentpage - 1) * pagesize;
		    if (pagesize > 0) {
		      sql += " ORDER BY code OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY";
		    } else {
		      sql += " ORDER BY code ";
		    }
		   
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
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}
}
