package com.mobility.library.repository;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.MemberInfo;
import com.mobility.library.info.MemberInfoMapper;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository implements IMemberRepository {
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
  public ReturnFormat<MemberInfo> findall(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
    ReturnFormat<MemberInfo> returnFormat = new ReturnFormat<>();
    String whereclause = "";
    int totalcount = 0;
    String sql = " SELECT systemkey, createddate, modifieddate, code, name, starteddate, address, nrcno FROM Member " +
            " WHERE deletedstatus = 0";
    if(!listCriteriaInfo.getCode().equals("")){
      whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%'";
    }
    if(!listCriteriaInfo.getName().equals("")){
      whereclause += " AND name like N'%" + listCriteriaInfo.getName() + "%'";
    }
    sql += whereclause;
    int currentpage = listCriteriaInfo.getCurrentpage();
    int pagesize = listCriteriaInfo.getPagesize();
    int start = (currentpage-1)*pagesize;
    if(pagesize>0){
      sql += " ORDER BY systemkey OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY ";
    } else {
      sql += " ORDER BY systemkey ";
    }
    PreparedStatement selectStatement = connection.prepareStatement(sql);
    ResultSet rs = selectStatement.executeQuery();
    while (rs.next()){
      MemberInfo memberInfo = new MemberInfo();
      memberInfo.setSystemkey(rs.getString("systemkey"));
      memberInfo.setCode(rs.getString("code"));
      memberInfo.setName(rs.getString("name"));
      memberInfo.setStarteddate(rs.getString("starteddate"));
      memberInfo.setAddress(rs.getString("address"));
      memberInfo.setNrcno(rs.getString("nrcno"));
      returnFormat.getList().add(memberInfo);
    }
    sql = "SELECT Count(systemkey) count FROM Member WHERE deletedstatus = 0 ";
    sql += whereclause;
    selectStatement = connection.prepareStatement(sql);
    rs =selectStatement.executeQuery();
    if(rs.next()){
      totalcount = rs.getInt("count");
    }
    returnFormat.setTotalcount(totalcount);

    return returnFormat;
  }

  public MemberInfo findByMemebersyskey(ListCriteriaInfo listCriteriaInfo, Connection connection) throws SQLException {
    MemberInfo memberInfo = new MemberInfo();
    String sql = " SELECT systemkey,starteddate, code, name, address, nrcno FROM Member WHERE deletedstatus <> 1 " +
            "  AND systemkey = " + listCriteriaInfo.getSystemkey();
    PreparedStatement selectstatement = connection.prepareStatement(sql);
    ResultSet rs = selectstatement.executeQuery();
    while (rs.next()) {
      memberInfo.setSystemkey(rs.getString("systemkey"));
      memberInfo.setCode(rs.getString("code"));
      memberInfo.setName(rs.getString("name"));
      memberInfo.setNrcno(rs.getString("nrcno"));
      memberInfo.setAddress(rs.getString("address"));
      memberInfo.setStarteddate(rs.getString("starteddate"));

    }
    return memberInfo;

  }

  public int checkcodeExit(MemberInfo memberInfo, Connection connection) throws SQLException {
    String sql = " SELECT systemkey FROM Member WHERE deletedstatus<> 1 AND code = '" + memberInfo.getCode() + "' AND systemkey <> " +
            memberInfo.getSystemkey();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ResultSet rs= preparedStatement.executeQuery();
    if(rs.next()){
      return 1;
    } else  {
      return 0;
    }
  }

  public int save(MemberInfo memberInfo, Connection connection) throws SQLException {
    String sql = " INSERT INTO Member(systemkey, createddate, modifieddate, code, name, starteddate, address, nrcno, deletedstatus ) values " +
            " (?,?,?,?,?,?,?,?,?)";
    PreparedStatement insertStatement = connection.prepareStatement(sql);
    insertStatement.setString(1, memberInfo.getSystemkey());
    insertStatement.setString(2, memberInfo.getCreateddate());
    insertStatement.setString(3, memberInfo.getModifieddate());
    insertStatement.setString(4, memberInfo.getCode());
    insertStatement.setString(5, memberInfo.getName());
    insertStatement.setString(6, memberInfo.getStarteddate());
    insertStatement.setString(7, memberInfo.getAddress());
    insertStatement.setString(8, memberInfo.getNrcno());
    insertStatement.setInt(9, memberInfo.getDeletedstatus());
    int effectedrow = insertStatement.executeUpdate();
    return effectedrow;
  }

  public int update(MemberInfo memberInfo, Connection connection) throws SQLException {
    String sql = " UPDATE Member set modifieddate=?, code=?, name=?, starteddate=? ,address=? , nrcno=?, deletedstatus=? WHERE " +
            " systemkey = ? ";
    PreparedStatement updateStatement = connection.prepareStatement(sql);
    updateStatement.setString(1, memberInfo.getModifieddate());
    updateStatement.setString(2, memberInfo.getCode());
    updateStatement.setString(3, memberInfo.getName());
    updateStatement.setString(4, memberInfo.getStarteddate());
    updateStatement.setString(5, memberInfo.getAddress());
    updateStatement.setString(6, memberInfo.getNrcno());
    updateStatement.setInt(7, memberInfo.getDeletedstatus());
    updateStatement.setString(8, memberInfo.getSystemkey());
    int effectedrow = updateStatement.executeUpdate();
    return effectedrow;
  }
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from Member", Integer.class);
	}
	
	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update Member set deletedstatus = 4 WHERE systemkey = ? ", id);
	}
	
	@Override
	public ReturnFormat<MemberInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		 ReturnFormat<MemberInfo> returnFormat = new ReturnFormat<>();
		 List<MemberInfo> dataList = new ArrayList<>();
		    String whereclause = "";
		    int totalcount = 0;
		    String sql = " SELECT systemkey, createddate, modifieddate, code, name, starteddate, address, nrcno, deletedstatus FROM Member " +
		            " WHERE deletedstatus <> 1";
		    if(!listCriteriaInfo.getCode().equals("")){
		      whereclause += " AND code like N'%" + listCriteriaInfo.getCode() + "%'";
		    }
		    if(!listCriteriaInfo.getName().equals("")){
		      whereclause += " AND name like N'%" + listCriteriaInfo.getName() + "%'";
		    }
		    sql += whereclause;
		    int currentpage = listCriteriaInfo.getCurrentpage();
		    int pagesize = listCriteriaInfo.getPagesize();
		    int start = (currentpage-1)*pagesize;
		    if(pagesize>0){
		      sql += " ORDER BY systemkey OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY ";
		    } else {
		      sql += " ORDER BY systemkey ";
		    }
		    dataList = jdbcTemplate.query(sql, new MemberInfoMapper());
		    returnFormat.setList(dataList);
		    
		    sql = "SELECT Count(systemkey) count FROM Member WHERE deletedstatus <> 1 ";
		    sql += whereclause;
		    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);
		    returnFormat.setTotalcount(totalcount);

		    return returnFormat;
	}
	
	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM Member WHERE code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int save(MemberInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO Member(systemkey, createddate, modifieddate, code, name, starteddate, address, nrcno, deletedstatus ) values " +
	            " (?,?,?,?,?,?,?,?,?)";
		
		return jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getCreateddate(),
				data.getModifieddate(),
				data.getCode(),
				data.getName(),
				data.getStarteddate(),
				data.getAddress(),
				data.getNrcno(),
				data.getDeletedstatus()
				);
	}
	
	@Override
	public int update(MemberInfo data) {
		// TODO Auto-generated method stub
		 String sql = " UPDATE Member set modifieddate=?, code=?, name=?, starteddate=? ,address=? , nrcno=?, deletedstatus=? WHERE " +
		            " systemkey = ? ";
		 
		return jdbcTemplate.update(sql,
				data.getModifieddate(),
				data.getCode(),
				data.getName(),
				data.getStarteddate(),
				data.getAddress(),
				data.getNrcno(),
				data.getDeletedstatus(),
				data.getSystemkey()
				);
	}
	
	@Override
	public List<MemberInfo> findAll() {
		// TODO Auto-generated method stub
		 String sql = " SELECT systemkey,starteddate, code, name, address, nrcno, deletedstatus FROM Member WHERE deletedstatus <> 1 ";
			return jdbcTemplate.query(sql, new MemberInfoMapper());
	}
	
	@Override
	public MemberInfo findById(String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT systemkey,starteddate, code, name, address, nrcno, deletedstatus FROM Member WHERE deletedstatus <> 1 AND systemkey=? ";
		RowMapper<MemberInfo> rowMapper = new BeanPropertyRowMapper<MemberInfo>(MemberInfo.class);
		MemberInfo member = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return member;
	}

}

