package com.mobility.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.Publisher;
import com.mobility.library.info.PublisherMapper;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IPublisherRepository;

@Repository
public class PublisherRepository implements IPublisherRepository {
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 

	  public ReturnFormat<Publisher> findall(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
	    ReturnFormat<Publisher> returnFormat = new ReturnFormat<>();
	    int totalcount = 0;
	    String whereclause = "";
	    String sql = " SELECT systemkey, code, name, address, phone, contact, remark, deletedstatus FROM PUBLISHER WHERE deletedstatus <> 1";
	    if(!listCriteriaInfo.getCode().equals("")){
	      whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%'";
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

	    PreparedStatement selectstatement = connection.prepareStatement(sql);
	    ResultSet rs = selectstatement.executeQuery();
	    while (rs.next()){
	    	Publisher result = new Publisher();
	    	result.setSystemkey(rs.getString("systemkey"));
	    	result.setCode(rs.getString("code"));
	    	result.setName(rs.getString("name"));
	    	result.setAddress(rs.getString("address"));
	    	result.setPhone(rs.getString("phone"));
	    	result.setContact(rs.getString("contact"));
	    	result.setRemark(rs.getString("remark"));
	    	result.setDeletedstatus(rs.getInt("deletedstatus"));
	      returnFormat.getList().add(result);
	    }
	    //System.out.println("Author ALL");

	    sql = "SELECT  count(systemkey) count FROM PUBLISHER WHERE deletedstatus <> 1" ;
	    sql += whereclause;
	    selectstatement = connection.prepareStatement(sql);
	    rs = selectstatement.executeQuery();
	    if(rs.next()){
	      totalcount = rs.getInt("count");
	    }

	    returnFormat.setTotalcount(totalcount);
	    return  returnFormat;
	  }

	  public Publisher readById(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
		  Publisher result = new Publisher();
	    String sql = " SELECT systemkey, code, name, address, phone, contact, remark, deletedstatus FROM PUBLISHER WHERE deletedstatus <> 1 " +
	            " AND systemkey = " + listCriteriaInfo.getSystemkey();
	    PreparedStatement selectstatement = connection.prepareStatement(sql);
	    ResultSet rs = selectstatement.executeQuery();
	    while (rs.next()){
	    	result.setSystemkey(rs.getString("systemkey"));
	    	result.setCode(rs.getString("code"));
	    	result.setName(rs.getString("name"));
	    	result.setAddress(rs.getString("address"));
	    	result.setPhone(rs.getString("phone"));
	    	result.setContact(rs.getString("contact"));
	    	result.setRemark(rs.getString("remark"));
	    	result.setDeletedstatus(rs.getInt("deletedstatus"));
	    }
	    return result;
	  }

	  public int checkCodeExit(Publisher data, Connection connection) throws SQLException {
	    String sql = " SELECT systemkey FROM PUBLISHER WHERE deletedstatus<>1 AND code ='" + data.getCode() + "' AND systemkey <> " + data.getSystemkey();
	    PreparedStatement statement = connection.prepareStatement(sql);
	    ResultSet rs = statement.executeQuery();
	    if (rs.next()) {
	      return 1;
	    } else {
	      return 0;
	    }
	  }

	  public int save(Publisher data, Connection connection) throws SQLException {
	    String sql = " INSERT INTO PUBLISHER(systemkey, code, name, address, phone, contact, remark, deletedstatus) values " +
	            " (?,?,?,?,?,?,?,?) ";
	    System.out.println(sql);
	    PreparedStatement insertStatement = connection.prepareStatement(sql);
	    insertStatement.setString(1,data.getSystemkey());
	    insertStatement.setString(2, data.getCode());
	    insertStatement.setString( 3, data.getName());
	    insertStatement.setString(4,data.getAddress());
	    insertStatement.setString(5, data.getPhone());
	    insertStatement.setString( 6, data.getContact());
	    insertStatement.setString( 7, data.getRemark());
	    insertStatement.setInt(8, data.getDeletedstatus());
	    int effectedrow = insertStatement.executeUpdate();
	    return effectedrow;
	  }

	  public int update(Publisher data, Connection connection) throws SQLException {
	    String sql = " UPDATE PUBLISHER SET code=?, name=?, address=?, phone=?, contact=?, remark=?, deletedstatus=? WHERE systemkey = ? " ;
	    PreparedStatement updateStatement = connection.prepareStatement(sql);
	    updateStatement.setString(1, data.getCode());
	    updateStatement.setString( 2, data.getName());
	    updateStatement.setString(3, data.getAddress());
	    updateStatement.setString(4, data.getPhone());
	    updateStatement.setString(5, data.getContact());
	    updateStatement.setString(6, data.getRemark());
	    updateStatement.setInt(7, data.getDeletedstatus());
	    updateStatement.setString(8,data.getSystemkey());
	    int effedtedrow = updateStatement.executeUpdate();
	    return effedtedrow;
	  }

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from PUBLISHER", Integer.class);
	}

	@Override
	public int save(Publisher data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO PUBLISHER(systemkey, code, name, address, phone, contact, remark, deletedstatus) values " +
	            " (?,?,?,?,?,?,?,?) ";
		return jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getCode(),
				data.getName(),
				data.getAddress(),
				data.getPhone(),
				data.getContact(),
				data.getRemark(),
				data.getDeletedstatus()
				);
	}

	@Override
	public int update(Publisher data) {
		// TODO Auto-generated method stub
		String sql = " UPDATE PUBLISHER SET code=?, name=?, address=?, phone=?, contact=?, remark=?, deletedstatus=? WHERE systemkey = ? " ;
		return jdbcTemplate.update(sql,
					data.getCode(),
					data.getName(),
					data.getAddress(),
					data.getPhone(),
					data.getContact(),
					data.getRemark(),
					data.getDeletedstatus(),
					data.getSystemkey()
					);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update PUBLISHER set deletedstatus = 4 WHERE systemkey = ? ", id);
	}

	@Override
	public List<Publisher> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT systemkey, code, name, address, phone, contact, remark, deletedstatus FROM PUBLISHER WHERE deletedstatus <> 1 ";
		return jdbcTemplate.query(sql, new PublisherMapper());
	}

	@Override
	public ReturnFormat<Publisher> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		ReturnFormat<Publisher> returnFormat = new ReturnFormat<>();
		List<Publisher> dataList = new ArrayList<>();
	    int totalcount = 0;
	    String whereclause = "";
	    String sql = " SELECT systemkey, code, name, address, phone, contact, remark, deletedstatus FROM PUBLISHER WHERE deletedstatus <> 1";
	    if(!listCriteriaInfo.getCode().equals("")){
	      whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%'";
	    }
	    if(!listCriteriaInfo.getName().equals("")){
	      whereclause += " AND name like N'%" + listCriteriaInfo.getName() + "%'";
	    }
	    sql += whereclause;

	    int currentpage = listCriteriaInfo.getCurrentpage();
	    int pagesize =listCriteriaInfo.getPagesize();
	    int start = (currentpage - 1) * pagesize;
	    if(pagesize > 0) {
	      sql +=  " ORDER BY code, name OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY";
	    } else {
	      sql += " ORDER BY code, name ";
	    }
	    dataList = jdbcTemplate.query(sql, new PublisherMapper());
	    returnFormat.setList(dataList);
	    

	    sql = "SELECT  count(systemkey) count FROM PUBLISHER WHERE deletedstatus <> 1" ;
	    sql += whereclause;
	    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);

	    returnFormat.setTotalcount(totalcount);
	    return  returnFormat;
	}

	@Override
	public Publisher findById(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT systemkey, code, name, address, phone, contact, remark, deletedstatus FROM PUBLISHER WHERE deletedstatus <> 1 " +
	            " AND systemkey =? " ;
		RowMapper<Publisher> rowMapper = new BeanPropertyRowMapper<Publisher>(Publisher.class);
		Publisher publisher = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return publisher;
	}

	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM PUBLISHER WHERE code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

}
