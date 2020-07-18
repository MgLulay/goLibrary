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
public class RentalHeaderMapper implements RowMapper<RentalHeaderInfo>{

	@Override
	public RentalHeaderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		RentalHeaderInfo rentalHeaderInfo = new RentalHeaderInfo();
	      rentalHeaderInfo.setSystemkey(rs.getString("systemkey"));
	      rentalHeaderInfo.setDocdate(rs.getString("docdate"));
	      rentalHeaderInfo.setDuedate(rs.getString("duedate"));
	      rentalHeaderInfo.setReturndate(rs.getString("returndate"));
	      rentalHeaderInfo.setMembersyskey(rs.getString("membersyskey"));
	      rentalHeaderInfo.setMembername(rs.getString("membername"));
	      rentalHeaderInfo.setRefno(rs.getString("refno"));
	      rentalHeaderInfo.setStatus(rs.getInt("status"));
	      rentalHeaderInfo.setRemark(rs.getString("remark"));
		return rentalHeaderInfo;
	}

}
