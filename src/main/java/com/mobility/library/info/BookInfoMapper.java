package com.mobility.library.info;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BookInfoMapper implements RowMapper<BookInfo>{

	@Override
	public BookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		 BookInfo bookInfo = new BookInfo();
	     bookInfo.setSystemkey(rs.getString("systemkey"));
	     bookInfo.setCode(rs.getString("code"));
	     bookInfo.setName(rs.getString("name"));
	     bookInfo.setAuthor(rs.getString("author"));
	     bookInfo.setPublisher(rs.getString("publisher"));
	     bookInfo.setShortcode(rs.getString("shortcode"));
	     bookInfo.setBarcode(rs.getString("barcode"));
	     bookInfo.setBooktype(rs.getString("booktype"));
	     bookInfo.setAvailable(rs.getInt("available"));
		
		return bookInfo;
	}

}
