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
public class CategoryInfoMapper implements RowMapper<CategoryInfo>{

	@Override
	public CategoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		  CategoryInfo categoryInfo = new CategoryInfo();
	      categoryInfo.setSystemkey(rs.getString("systemkey"));
	      categoryInfo.setCode(rs.getString("code"));
	      categoryInfo.setName(rs.getString("name"));
	      categoryInfo.setRemark(rs.getString("remark"));
	      categoryInfo.setDeletedstatus(rs.getInt("deletedstatus"));
		return categoryInfo;
	}

}
