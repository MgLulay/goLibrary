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
import com.mobility.library.info.Rack;
import com.mobility.library.info.RackMapper;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IRackRepository;

@Repository
public class RackRepository implements IRackRepository{
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 

	  public ReturnFormat<Rack> findall(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
	    ReturnFormat<Rack> returnFormat = new ReturnFormat<Rack>();
	    int totalcount = 0;
	    String whereclause = "";
	    String sql = " SELECT systemkey, code, name, remark, deletedstatus FROM RACK WHERE deletedstatus <> 1";
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
	    	Rack result = new Rack();
	    	result.setSystemkey(rs.getString("systemkey"));
	    	result.setCode(rs.getString("code"));
	    	result.setName(rs.getString("name"));
	    	result.setRemark(rs.getString("remark"));
	    	result.setDeletedstatus(rs.getInt("deletedstatus"));
	      returnFormat.getList().add(result);
	    }
	    //System.out.println("Author ALL");

	    sql = "SELECT  count(systemkey) count FROM RACK WHERE deletedstatus <> 1" ;
	    sql += whereclause;
	    selectstatement = connection.prepareStatement(sql);
	    rs = selectstatement.executeQuery();
	    if(rs.next()){
	      totalcount = rs.getInt("count");
	    }

	    returnFormat.setTotalcount(totalcount);
	    return  returnFormat;
	  }

	  public Rack readById(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
		  Rack result = new Rack();
	    String sql = " SELECT systemkey, code, name, remark, deletedstatus FROM RACK WHERE deletedstatus <> 1 " +
	            " AND systemkey = " + listCriteriaInfo.getSystemkey();
	    PreparedStatement selectstatement = connection.prepareStatement(sql);
	    ResultSet rs = selectstatement.executeQuery();
	    while (rs.next()){
	    	result.setSystemkey(rs.getString("systemkey"));
	    	result.setCode(rs.getString("code"));
	    	result.setName(rs.getString("name"));
	    	result.setRemark(rs.getString("remark"));
	    	result.setDeletedstatus(rs.getInt("deletedstatus"));
	    }
	    return result;
	  }

	  public int checkCodeExit(Rack data, Connection connection) throws SQLException {
	    String sql = " SELECT systemkey FROM RACK WHERE deletedstatus<>1 AND code ='" + data.getCode() + "' AND systemkey <> " + data.getSystemkey();
	    PreparedStatement statement = connection.prepareStatement(sql);
	    ResultSet rs = statement.executeQuery();
	    if (rs.next()) {
	      return 1;
	    } else {
	      return 0;
	    }
	  }

	  public int save(Rack data, Connection connection) throws SQLException {
	    String sql = " INSERT INTO RACK (systemkey, code, name, remark, deletedstatus) values " +
	            " (?,?,?,?,?) ";
	    System.out.println(sql);
	    PreparedStatement insertStatement = connection.prepareStatement(sql);
	    insertStatement.setString(1,data.getSystemkey());
	    insertStatement.setString(2, data.getCode());
	    insertStatement.setString( 3, data.getName());
	    insertStatement.setString( 4, data.getRemark());
	    insertStatement.setInt(5, data.getDeletedstatus());
	    int effectedrow = insertStatement.executeUpdate();
	    return effectedrow;
	  }

	  public int update(Rack data, Connection connection) throws SQLException {
	    String sql = " UPDATE RACK SET code=?, name=?, remark=?, deletedstatus=? WHERE systemkey = ? " ;
	    PreparedStatement updateStatement = connection.prepareStatement(sql);
	    updateStatement.setString(1, data.getCode());
	    updateStatement.setString( 2, data.getName());
	    updateStatement.setString( 3, data.getRemark());
	    updateStatement.setInt(4, data.getDeletedstatus());
	    updateStatement.setString(5,data.getSystemkey());
	    int effedtedrow = updateStatement.executeUpdate();
	    return effedtedrow;
	  }

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from RACK", Integer.class);
	}

	@Override
	public int save(Rack data) {
		// TODO Auto-generated method stub
		 String sql = " INSERT INTO RACK (systemkey, code, name, remark, deletedstatus) values " +
		            " (?,?,?,?,?) ";
		 
		return jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getCode(),
				data.getName(),
				data.getRemark(),
				data.getDeletedstatus()
				);
	}

	@Override
	public int update(Rack data) {
		// TODO Auto-generated method stub
		String sql = " UPDATE RACK SET code=?, name=?, remark=?, deletedstatus=? WHERE systemkey = ? " ;
		 
		return jdbcTemplate.update(sql,
				data.getCode(),
				data.getName(),
				data.getRemark(),
				data.getDeletedstatus(),
				data.getSystemkey()
				);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update RACK set deletedstatus = 4 WHERE systemkey = ? ", id);
	}

	@Override
	public List<Rack> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT systemkey, code, name, remark, deletedstatus FROM RACK WHERE deletedstatus <> 1 ";
		return jdbcTemplate.query(sql, new RackMapper());
	}

	@Override
	public ReturnFormat<Rack> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		ReturnFormat<Rack> returnFormat = new ReturnFormat<Rack>();
		List<Rack> dataList = new ArrayList<>();
	    int totalcount = 0;
	    String whereclause = "";
	    String sql = " SELECT systemkey, code, name, remark, deletedstatus FROM RACK WHERE deletedstatus <> 1";
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

	    dataList = jdbcTemplate.query(sql, new RackMapper());
	    returnFormat.setList(dataList);
	    

	    sql = "SELECT  count(systemkey) count FROM RACK WHERE deletedstatus <> 1" ;
	    sql += whereclause;
	    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);

	    returnFormat.setTotalcount(totalcount);
	    return  returnFormat;
	}

	@Override
	public Rack findById(String id) {
		// TODO Auto-generated method stub
		 String sql = " SELECT systemkey, code, name, remark, deletedstatus FROM RACK WHERE deletedstatus <> 1 AND systemkey =? ";
		RowMapper<Rack> rowMapper = new BeanPropertyRowMapper<Rack>(Rack.class);
		Rack rack = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return rack;
	}

	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM RACK WHERE code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

}
