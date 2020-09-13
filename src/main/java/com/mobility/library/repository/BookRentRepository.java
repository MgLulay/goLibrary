package com.mobility.library.repository;

import com.mobility.library.info.BookInfo;
import com.mobility.library.info.BookInfoMapper;
import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalDetailInfo;
import com.mobility.library.info.RentalDetailMapper;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.RentalHeaderMapper;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IRentRepository;
import com.mobility.library.utility.StatusUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BookRentRepository implements IRentRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from T001", Integer.class);
	}
	
	@Override
	public String getMaxRefNo() {
		// TODO Auto-generated method stub
		String maxrefno = "1";
	    int tempnum = 0;
	    String sql = " SELECT ISNULL(MAX(CAST(refno AS INT)),0)  as refno FROM T001";
	    tempnum = jdbcTemplate.queryForObject(sql, Integer.class);
	   
	    maxrefno = String.format("%04d", (tempnum+1));
	    return maxrefno;
	}
	
	@Override
	public int saveRentalHeader(RentalHeaderInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO T001(systemkey,docdate,duedate,returndate,membersystemkey,refno,status,userid,username,remark) " +
	            " values (?,?,?,?,?,?,?,?,?,?) ";
	   
	    int effectedrow = jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getDocdate(),
				data.getDuedate(),
				data.getReturndate(),
				data.getMembersyskey(),
				data.getRefno(),
				data.getStatus(),
				data.getUserid(),
				data.getUsername(),
				data.getRemark()
				);
	    return effectedrow;
	}
	
	@Override
	public int saveRentalDetail(RentalDetailInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO T002(systemkey,parentsystemkey,bookcode,bookname,booksystemkey,authorname,authorsystemkey,quantity) " +
	            " values(?,?,?,?,?,?,?,?) ";
	    
	    int effectedrow = jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getParentsystemkey(),
				data.getBookcode(),
				data.getBookname(),
				data.getBooksystemkey(),
				data.getAuthorname(),
				data.getAuthorsystemkey(),
				data.getQuantity()
				);
	    return effectedrow;
	}
	
	@Override
	public int updateRentalHeader(RentalHeaderInfo data) {
		// TODO Auto-generated method stub
		 String sql = " UPDATE T001 set docdate=?,duedate=?,returndate=?,membersystemkey=?,refno=?,status=?,userid=?,username=?,remark=? " +
		            " WHERE  systemkey = ?";
		   
		    int effectedrow = jdbcTemplate.update(sql,
					data.getDocdate(),
					data.getDuedate(),
					data.getReturndate(),
					data.getMembersyskey(),
					data.getRefno(),
					data.getStatus(),
					data.getUserid(),
					data.getUsername(),
					data.getRemark(),
					data.getSystemkey()
					);
		    return effectedrow;
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update T001 set status =? WHERE systemkey =? ", StatusUtil.VOID, id);
	}
	
	@Override
	public int deleteRentalDetail(String parentId) {
		// TODO Auto-generated method stub
		String sql = " DELETE T002 WHERE parentsystemkey = ? ";
		return jdbcTemplate.update(sql,parentId);
	}
	
	@Override
	public List<RentalHeaderInfo> findAll() {
		// TODO Auto-generated method stub
		 String sql = " SELECT h.systemkey, h.refno, h.docdate, h.duedate, h.returndate, h.status,h.remark, m.systemkey as membersyskey, m.name as membername" +
		            " FROM T001 h inner join Member m on h.membersystemkey=m.systemkey WHERE AND h.status <> "+ StatusUtil.VOID +"";
		return jdbcTemplate.query(sql, new RentalHeaderMapper());
	}
	
	@Override
	public ReturnFormat<RentalHeaderInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		 ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
		 List<RentalHeaderInfo> dataList = new ArrayList<>();
		    int totalCount= 0;
		    
		    String sql = " SELECT h.systemkey, h.docdate, h.duedate, h.returndate, m.systemkey as membersyskey, m.name as membername, h.refno, h.status, h.remark " +
		            " FROM T001 h inner join Member m on h.membersystemkey=m.systemkey " ;
		    String whereClause= " WHERE 1=1 AND h.status <> "+ StatusUtil.VOID +"";
		    if (!listCriteriaInfo.getDocdate().equals("")) {
		    	whereClause += " AND h.docdate = '" + listCriteriaInfo.getDocdate() + "'";
		    }
		    if (!listCriteriaInfo.getDuedate().equals("")) {
		    	whereClause += " AND h.duedate = '" + listCriteriaInfo.getDuedate() + "'";
		    }
		    if (!listCriteriaInfo.getCode().equals("")) {
		    	whereClause += " AND h.refno like '%" + listCriteriaInfo.getCode() + "%'";
		    }
		    if (listCriteriaInfo.getStatus() !=0) {
		    	whereClause += " AND h.status = " + listCriteriaInfo.getStatus();
		    }
		    
		    sql += whereClause;
			if(listCriteriaInfo.getPagesize() !=0) {
				int currentPage = listCriteriaInfo.getCurrentpage();
				int pageSize = listCriteriaInfo.getPagesize();
				int start = (currentPage - 1) * pageSize;
				sql += " ORDER BY h.docdate desc, h.refno desc OFFSET  " + start + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY "; // LIMIT ?, ?
			}else {
				sql += " ORDER BY h.docdate desc, h.refno desc";
			}
			dataList = jdbcTemplate.query(sql, new RentalHeaderMapper());
			returnFormat.setList(dataList);
			
		    if (listCriteriaInfo.getPagesize() != 0) {
		    	sql = "SELECT COUNT(DISTINCT h.systemkey) AS totalCount FROM T001 h inner join Member m on h.membersystemkey=m.systemkey "+ whereClause ;
		    	totalCount= jdbcTemplate.queryForObject(sql, Integer.class);
				returnFormat.setTotalcount(totalCount);
			}
		    returnFormat.setMessage("Success");
		    return returnFormat;

	}
	
	@Override
	public RentalHeaderInfo findById(String id) {
		// TODO Auto-generated method stub
		    String sql = " SELECT h.systemkey, h.refno, h.docdate, h.duedate, h.returndate, h.status,h.remark, m.systemkey as membersyskey, m.name as membername" +
		            " FROM T001 h inner join Member m on h.membersystemkey=m.systemkey WHERE h.status <> "+ StatusUtil.VOID +" AND h.systemkey =? ";
		    
		    RowMapper<RentalHeaderInfo> rowMapper = new BeanPropertyRowMapper<RentalHeaderInfo>(RentalHeaderInfo.class);
		    RentalHeaderInfo rentalHeaderInfo = jdbcTemplate.queryForObject(sql, rowMapper, id);
		    rentalHeaderInfo.setRentalDetailInfoList(getRentBookDetail(id));
		    //System.out.println("member: "+rentalHeaderInfo.getMembersyskey());
		    return rentalHeaderInfo;
	}
	
	@Override
	public List<RentalDetailInfo> getRentBookDetail(String parentId) {
		// TODO Auto-generated method stub
	    String sql = " SELECT d.systemkey, d.parentsystemkey,d.booksystemkey, d.bookcode, book.name, au.systemkey as authorsystemkey, au.name as authorname, d.quantity " +
	            " from T001 h inner join T002 d on h.systemkey=d.parentsystemkey " +
	            " inner join Book book on d.booksystemkey=book.systemkey " +
	            " inner join Author au on authorsystemkey=au.systemkey where d.parentsystemkey =? ";
	    
	    return jdbcTemplate.query(sql, new Object[]{parentId}, new RentalDetailMapper());
	}
	
	@Override
	public int returnBook(RentalHeaderInfo rentalHeaderInfo) {
		// TODO Auto-generated method stub
		String sql = " UPDATE T001 SET status= ?,  returndate =? WHERE membersystemkey = ?";
	    return jdbcTemplate.update(sql,
	    		StatusUtil.RETURN,
	    		rentalHeaderInfo.getReturndate(),
	    		rentalHeaderInfo.getMembersyskey()
				);
	}
	
	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM T001 WHERE refno =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public BookInfo checkAvailableBook(List<RentalDetailInfo> list) {
		String sql = " SELECT systemkey, code, name, booktype, author, publisher,shortcode, barcode, available FROM Book WHERE deletedstatus <> 4 AND available = 1 ORDER BY code, name  ";
		List<BookInfo> rList = jdbcTemplate.query(sql, new BookInfoMapper());
		Set<String> acceptableKey = 
				list.stream()
			         .map(RentalDetailInfo::getBooksystemkey)
			         .collect(Collectors.toSet());
		List<BookInfo> temp = rList.stream()
				.filter(c -> acceptableKey.contains(c.getSystemkey()))
				.collect(Collectors.toList());
		
		return (temp.size() > 0) ? temp.get(0) : null;
	}
}
