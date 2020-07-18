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
public class RentalDetailMapper implements RowMapper<RentalDetailInfo>{

	@Override
	public RentalDetailInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		 RentalDetailInfo rentalDetailInfo = new RentalDetailInfo();
	      rentalDetailInfo.setSystemkey(rs.getString("systemkey"));
	      rentalDetailInfo.setParentsystemkey(rs.getString("parentsystemkey"));
	      rentalDetailInfo.setBooksystemkey(rs.getString("booksystemkey"));
	      rentalDetailInfo.setBookcode(rs.getString("bookcode"));
	      rentalDetailInfo.setBookname(rs.getString("name"));
	      rentalDetailInfo.setAuthorsystemkey(rs.getString("authorsystemkey"));
	      rentalDetailInfo.setAuthorname(rs.getString("authorname"));
	      rentalDetailInfo.setQuantity(rs.getInt("quantity"));
		return rentalDetailInfo;
	}

}
