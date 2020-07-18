/**
 * 
 */
package com.mobility.library.info;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Aung Kyaw Tin
 *
 */
public class MemberInfoMapper implements RowMapper<MemberInfo>{

	@Override
	public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		  MemberInfo memberInfo = new MemberInfo();
	      memberInfo.setSystemkey(rs.getString("systemkey"));
	      memberInfo.setCode(rs.getString("code"));
	      memberInfo.setName(rs.getString("name"));
	      memberInfo.setStarteddate(rs.getString("starteddate"));
	      memberInfo.setAddress(rs.getString("address"));
	      memberInfo.setNrcno(rs.getString("nrcno"));
	      memberInfo.setDeletedstatus(rs.getInt("deletedstatus"));
		return memberInfo;
	}

}
