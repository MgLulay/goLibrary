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
public class RackMapper implements RowMapper<Rack>{

	@Override
	public Rack mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Rack result = new Rack();
    	result.setSystemkey(rs.getString("systemkey"));
    	result.setCode(rs.getString("code"));
    	result.setName(rs.getString("name"));
    	result.setRemark(rs.getString("remark"));
    	result.setDeletedstatus(rs.getInt("deletedstatus"));
		return result;
	}

}
