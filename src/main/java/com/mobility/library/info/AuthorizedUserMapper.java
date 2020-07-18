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
public class AuthorizedUserMapper implements RowMapper<AuthorizedUser>{

	@Override
	public AuthorizedUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		AuthorizedUser authorized = new AuthorizedUser();
		authorized.setSystemkey(rs.getString("systemkey"));
		authorized.setUserid(rs.getString("code"));
		authorized.setUsername(rs.getString("name"));
		authorized.setPassword(rs.getString("password"));
		authorized.setPasscode(rs.getString("passcode"));
		authorized.setPhone(rs.getString("phone"));
		authorized.setEmail(rs.getString("email"));
		authorized.setUserrole(rs.getString("userrole"));
		authorized.setDeletedstatus(rs.getInt("deletedstatus"));
		return authorized;
	}

}
