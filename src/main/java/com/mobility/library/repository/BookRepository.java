package com.mobility.library.repository;

import com.mobility.library.info.BookInfo;
import com.mobility.library.info.BookInfoMapper;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IBookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BookRepository implements IBookRepository{

	// Spring Boot will create and configure DataSource and JdbcTemplate
	// To use it, just @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate
                .queryForObject("select count(*) from Book where deletedstatus <> 4 ORDER BY code, name ", Integer.class);
	}
	
	@Override
	public int save(BookInfo book) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO " +
	            " Book(systemkey, code, name, booktype, author, purchaseddate,modifieddate,price,issueddate,deletedstatus, available, publisher,shortcode,barcode, createddate ) " +
	            " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		return jdbcTemplate.update(
               sql,
               book.getSystemkey(), 
               book.getCode(),
               book.getName(),
               book.getBooktype(),
               book.getAuthor(),
               book.getPurchaseddate(),
               book.getModifieddate(),
               book.getPrice(),
               book.getIssueddate(),
               book.getDeletestatus(),
               book.getAvailable(),
               book.getPublisher(),
               book.getShortcode(),
               book.getBarcode(),
               book.getCreateddate()
               );
	}
	
	@Override
	public int update(BookInfo book) {
		// TODO Auto-generated method stub
		 String sql = " UPDATE Book " +
		            " set code=?, name=?, booktype=?, author=?, purchaseddate=?, modifieddate=?, price=?, issueddate=?, publisher=?,shortcode=?, barcode=?, deletedstatus=?" +
		            " WHERE systemkey = ? ";
		 //System.out.println("upd "+sql);
		 return jdbcTemplate.update(
	               sql,
	               book.getCode(),
	               book.getName(),
	               book.getBooktype(),
	               book.getAuthor(),
	               book.getPurchaseddate(),
	               book.getModifieddate(),
	               book.getPrice(),
	               book.getIssueddate(),
	               book.getPublisher(),
	               book.getShortcode(),
	               book.getBarcode(),
	               book.getDeletestatus(),
	               book.getSystemkey()
	               );
	}
	
	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update Book set deletedstatus = 4 WHERE systemkey = ? ", id);
	}
	
	@Override
	public List<BookInfo> findAll() {
		// TODO Auto-generated method stub
		 String sql = " SELECT systemkey, code, name, booktype, author, publisher,shortcode, barcode, available FROM Book WHERE deletedstatus <> 4 ORDER BY code, name  ";
		return jdbcTemplate.query(sql, new BookInfoMapper());
	}
	
	@Override
	public List<BookInfo> findByNameAndPrice(String name, BigDecimal price) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(
                "select * from Book where deletedstatus <> 4 AND available <> 1 AND name like ? and price <= ? ORDER BY code, name ",
                new Object[]{"%" + name + "%", price}, new BookInfoMapper()   
        );
	}
	
	@Override
	public BookInfo findById(String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT systemkey, code, name, booktype, author, publisher,shortcode, barcode, available FROM Book where deletedstatus <> 4 AND systemkey =? ";
		RowMapper<BookInfo> rowMapper = new BeanPropertyRowMapper<BookInfo>(BookInfo.class);
		BookInfo book = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return book;
	}
	
	@Override
	public String getNameById(Long id) {
		// TODO Auto-generated method stub
		 return jdbcTemplate.queryForObject(
	                "select name from Book where deletedstatus <> 4 AND available <> 1 AND systemkey = ?",
	                new Object[]{id},
	                String.class
	        );
	}
	
	@Override
	public boolean isExists(String title, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM Book WHERE deletedstatus <> 4 AND code =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{title, id}, Integer.class );
		return (flag > 0) ? true : false;
	}

	@Override
	public ReturnFormat<BookInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		 ReturnFormat<BookInfo> returnFormat = new ReturnFormat<>();
		 List<BookInfo> bookInfoList = new ArrayList<>();
		 String whereclause = "where 1=1 AND b.deletedstatus <> 4 ";
		    int totalcount = 0;
		    String sql = " SELECT b.systemkey, b.code, b.name, b.booktype, b.author, b.publisher, b.shortcode, b.barcode, b.available "
		    		+ "FROM Book b INNER JOIN Author a ON b.author = a.systemkey INNER JOIN Category c ON b.booktype = c.systemkey ";
		    if (!listCriteriaInfo.getCode().equals("")) {
		      whereclause += " AND b.code like N'%" + listCriteriaInfo.getCode() + "%' OR b.name like N'%" + listCriteriaInfo.getCode() + "%'";
		    }
		    if (!listCriteriaInfo.getName().equals("")) {
		      whereclause += " AND b.name like N'%" + listCriteriaInfo.getName() + "%'";
		    }
		    if(!listCriteriaInfo.getAuthor().equals(""))
		    	whereclause += " AND a.code like N'%" + listCriteriaInfo.getAuthor() + "%' OR a.name like N'%"+ listCriteriaInfo.getAuthor() + "%'";
		    
		    if(!listCriteriaInfo.getBooktype().equals(""))
		    	whereclause += " AND c.code like N'%" + listCriteriaInfo.getBooktype() + "%' OR c.name like N'%"+ listCriteriaInfo.getBooktype() + "%'";
		    
		    if (listCriteriaInfo.getAvailableFlag() == 1) {
		    	whereclause += " AND b.available = 1 ";
		    }
		    sql += whereclause;
		    int currentpage = listCriteriaInfo.getCurrentpage();
		    int pagesize = listCriteriaInfo.getPagesize();
		    int start = (currentpage - 1) * pagesize;
		    if (pagesize > 0)
		      sql += " ORDER BY b.code, b.name OFFSET " + start + " ROWS FETCH NEXT " + pagesize + " ROWS ONLY ";
		    else
		      sql += " ORDER BY b.code, b.name  ";
		    
		    bookInfoList= jdbcTemplate.query(sql, new BookInfoMapper());
		    sql = " SELECT count(b.systemkey) as count FROM Book b INNER JOIN Author a ON b.author = a.systemkey INNER JOIN Category c ON b.booktype = c.systemkey "+whereclause;
		    totalcount= jdbcTemplate.queryForObject(sql, Integer.class);
		    
	    returnFormat.setTotalcount(totalcount);
	    returnFormat.setList(bookInfoList);
		return returnFormat;
	}

	@Override
	public int updateStatusById(int status, String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update Book set available =? WHERE deletedstatus <> 4 AND systemkey = ? ", status, id);
	}

}
