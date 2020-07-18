/**
 * 
 */
package com.mobility.library.info;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Admin
 *
 */
public class AuthorInfoMapper implements RowMapper<AuthorInfo>{

	@Override
	public AuthorInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		 AuthorInfo authorInfo = new AuthorInfo();
	      authorInfo.setSystemkey(rs.getString("systemkey"));
	      authorInfo.setCode(rs.getString("code"));
	      authorInfo.setName(rs.getString("name"));
	      authorInfo.setDeletedstatus(rs.getInt("deletedstatus"));
		return authorInfo;
	}

}
