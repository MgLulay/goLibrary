package com.mobility.library.info;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PublisherMapper implements RowMapper<Publisher>{

	@Override
	public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Publisher result = new Publisher();
    	result.setSystemkey(rs.getString("systemkey"));
    	result.setCode(rs.getString("code"));
    	result.setName(rs.getString("name"));
    	result.setAddress(rs.getString("address"));
    	result.setPhone(rs.getString("phone"));
    	result.setContact(rs.getString("contact"));
    	result.setRemark(rs.getString("remark"));
    	result.setDeletedstatus(rs.getInt("deletedstatus"));
		return result;
	}

}
