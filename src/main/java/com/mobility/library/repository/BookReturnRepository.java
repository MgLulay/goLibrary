/**
 * 
 */
package com.mobility.library.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mobility.library.info.ListCriteriaInfo;
import com.mobility.library.info.RentalDetailInfo;
import com.mobility.library.info.RentalDetailMapper;
import com.mobility.library.info.RentalHeaderInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.repository.impl.IReturnRepository;
import com.mobility.library.utility.SystemUtility;

/**
 * @author Admin
 *
 */

@Repository
public class BookReturnRepository implements IReturnRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from R001 where status <> 2 ", Integer.class);
	}

	@Override
	public String getMaxRefNo() {
		// TODO Auto-generated method stub
		String maxrefno = "1";
	    int tempnum = 0;
	    String sql = " SELECT ISNULL(MAX(CAST(refno AS INT)),0)  as refno FROM R001";
	    tempnum = jdbcTemplate.queryForObject(sql, Integer.class);
	   
	    maxrefno = String.format("%04d", (tempnum+1));
	    return maxrefno;
	}

	@Override
	public int saveReturnHeader(RentalHeaderInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO R001(systemkey, docdate, returndate, secondkey, membersystemkey, refno, status, userid, username, remark, createddate, modifieddate, deletedstatus ) " +
	            " values (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	   
	    int effectedrow = jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getDocdate(),
				data.getReturndate(),
				data.getSecondkey(),
				data.getMembersyskey(),
				data.getRefno(),
				data.getStatus(),
				data.getUserid(),
				data.getUsername(),
				data.getRemark(),
				SystemUtility.getCurrentDate()+"",
				SystemUtility.getCurrentDate()+"",
				0
				);
	    return effectedrow;
	}

	@Override
	public int saveReturnDetail(RentalDetailInfo data) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO R002(systemkey, parentsystemkey, bookcode, bookname, booksystemkey, authorname, authorsystemkey, quantity, userid, username, createddate, modifieddate, deletedstatus) " +
	            " values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	    int effectedrow = jdbcTemplate.update(sql,
				data.getSystemkey(),
				data.getParentsystemkey(),
				data.getBookcode(),
				data.getBookname(),
				data.getBooksystemkey(),
				data.getAuthorname(),
				data.getAuthorsystemkey(),
				data.getQuantity(),
				"",
				"",
				SystemUtility.getCurrentDate()+"",
				SystemUtility.getCurrentDate()+"",
				0
				);
	    return effectedrow;
	}

	@Override
	public int updateReturnHeader(RentalHeaderInfo data) {
		// TODO Auto-generated method stub
		 String sql = " UPDATE R001 set modifieddate=?, docdate=?, returndate=?, secondkey=?, membersystemkey=?, refno=?, status=?, userid=?, username=?, remark=? " +
		            " WHERE  systemkey = ?";
		   
		    int effectedrow = jdbcTemplate.update(sql,
		    		SystemUtility.getCurrentDate()+"",
					data.getDocdate(),
					data.getReturndate(),
					data.getSecondkey(),
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
	public int deleteReturnDetail(String parentId) {
		// TODO Auto-generated method stub
		String sql = " DELETE R002 WHERE parentsystemkey = ? ";
		return jdbcTemplate.update(sql,parentId);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update R001 set status = 2 WHERE systemkey = ? ", id);
	}

	@Override
	public List<RentalHeaderInfo> findAll() {
		// TODO Auto-generated method stub
		 String sql = " SELECT h.systemkey, h.refno, h.docdate, h.returndate, h.status,h.remark, h.secondkey, m.systemkey as membersyskey, m.name as membername" +
		            " FROM R001 h inner join Member m on h.membersystemkey=m.systemkey WHERE h.status <> 2 ";
		return jdbcTemplate.query(sql, (rs,rowNum)-> {
			RentalHeaderInfo result = new RentalHeaderInfo();
			result.setSystemkey(rs.getString("systemkey"));
			result.setRefno(rs.getString("refno"));
			result.setDocdate(rs.getString("docdate"));
			result.setReturndate(rs.getString("returndate"));
			result.setStatus(rs.getInt("status"));
			result.setRemark(rs.getString("remark"));
			result.setSecondkey(rs.getString("secondkey"));
			result.setMembersyskey(rs.getString("membersyskey"));
			result.setMembername(rs.getString("membername"));
			return result;
		});
	}

	@Override
	public ReturnFormat<RentalHeaderInfo> findByCriteria(ListCriteriaInfo listCriteriaInfo) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				 ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
				 List<RentalHeaderInfo> dataList = new ArrayList<>();
				    int totalCount= 0;
				    
				    String sql = " SELECT h.systemkey, h.docdate, h.returndate, h.secondkey, m.systemkey as membersyskey, m.name as membername, h.refno, h.status, h.remark " +
				            " FROM R001 h inner join Member m on h.membersystemkey=m.systemkey " ;
				    String whereClause= " WHERE 1=1 AND h.status <> 2";
				    if (!listCriteriaInfo.getDocdate().equals("")) {
				    	whereClause += " AND h.docdate = '" + listCriteriaInfo.getDocdate() + "'";
				    }
				    
				    if (!listCriteriaInfo.getCode().equals("")) {
				    	whereClause += " AND h.refno like '%" + listCriteriaInfo.getCode() + "%'";
				    }
				    if (listCriteriaInfo.getStatus() != -1 && listCriteriaInfo.getStatus() != 2) {
				    	whereClause += " AND h.status = " + listCriteriaInfo.getStatus();
				    }
				    sql += whereClause;
					if(listCriteriaInfo.getPagesize() !=0) {
						int currentPage = listCriteriaInfo.getCurrentpage();
						int pageSize = listCriteriaInfo.getPagesize();
						int start = (currentPage - 1) * pageSize;
						sql += " ORDER BY h.docdate desc, h.refno desc  OFFSET  " + start + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY "; // LIMIT ?, ?
					}else {
						sql += " ORDER BY h.docdate desc, h.refno desc";
					}
					
					dataList = jdbcTemplate.query(sql, (rs,rowNum)-> {
						RentalHeaderInfo result = new RentalHeaderInfo();
						result.setSystemkey(rs.getString("systemkey"));
						result.setRefno(rs.getString("refno"));
						result.setDocdate(rs.getString("docdate"));
						result.setReturndate(rs.getString("returndate"));
						result.setStatus(rs.getInt("status"));
						result.setRemark(rs.getString("remark"));
						result.setSecondkey(rs.getString("secondkey"));
						result.setMembersyskey(rs.getString("membersyskey"));
						result.setMembername(rs.getString("membername"));
						return result;
					});
					returnFormat.setList(dataList);
					
				    if (listCriteriaInfo.getPagesize() != 0) {
				    	sql = "SELECT COUNT(DISTINCT h.systemkey) AS totalCount FROM R001 h inner join Member m on h.membersystemkey=m.systemkey "+ whereClause ;
				    	totalCount= jdbcTemplate.queryForObject(sql, Integer.class);
						returnFormat.setTotalcount(totalCount);
					}
				    returnFormat.setMessage("Success");
				    return returnFormat;

	}

	@Override
	public RentalHeaderInfo findById(String id) {
		// TODO Auto-generated method stub
		 String sql = " SELECT h.systemkey, h.refno, h.docdate, h.returndate, h.status,h.remark, h.secondkey, m.systemkey as membersyskey, m.name as membername" +
		            " FROM R001 h inner join Member m on h.membersystemkey=m.systemkey WHERE h.status <> 2 AND h.systemkey =? ";
		    
		    RowMapper<RentalHeaderInfo> rowMapper = new BeanPropertyRowMapper<RentalHeaderInfo>(RentalHeaderInfo.class);
		    RentalHeaderInfo rentalHeaderInfo = jdbcTemplate.queryForObject(sql, rowMapper, id);
		    rentalHeaderInfo.setRentalDetailInfoList(getReturnBookDetail(id));
		    return rentalHeaderInfo;
	}

	@Override
	public List<RentalDetailInfo> getReturnBookDetail(String parentId) {
		// TODO Auto-generated method stub
		 String sql = " SELECT d.systemkey, d.parentsystemkey,d.booksystemkey, d.bookcode, book.name, au.systemkey as authorsystemkey, au.name as authorname, d.quantity " +
		            " from R001 h inner join R002 d on h.systemkey=d.parentsystemkey " +
		            " inner join Book book on d.booksystemkey=book.systemkey " +
		            " inner join Author au on authorsystemkey=au.systemkey where d.parentsystemkey =? ";
		    
		    return jdbcTemplate.query(sql, new Object[]{parentId}, new RentalDetailMapper());
	}

	@Override
	public int returnBook(RentalHeaderInfo rentalHeaderInfo) {
		// TODO Auto-generated method stub
		String sql = " UPDATE R001 SET status= ?,  returndate =? WHERE Systemkey = ?";
		   
	    return jdbcTemplate.update(sql,
	    		rentalHeaderInfo.getStatus(),
	    		rentalHeaderInfo.getReturndate(),
	    		rentalHeaderInfo.getSystemkey()
				);
	}

	@Override
	public boolean isExists(String code, String id) {
		// TODO Auto-generated method stub
		String sql = " SELECT count(*) FROM R001 WHERE status <> 2 AND  refno =? AND systemkey <> ? ";
		int flag = jdbcTemplate.queryForObject(sql, new Object[]{code, id}, Integer.class );
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<RentalDetailInfo> getReturnBookByMember(String memberId) {
		// TODO Auto-generated method stub
		String sql = "SELECT d.systemkey, d.parentsystemkey,d.booksystemkey, d.bookcode, book.name, au.systemkey as authorsystemkey, au.name as authorname, d.quantity " +
	            " from T001 h inner join T002 d on h.systemkey=d.parentsystemkey " +
	            " inner join Book book on d.booksystemkey=book.systemkey " +
	            " inner join Author au on authorsystemkey=au.systemkey where h.status = 1 AND h.membersystemkey =? ";

	    return jdbcTemplate.query(sql, new Object[]{memberId}, new RentalDetailMapper());
	}

	@Override
	public ReturnFormat<RentalHeaderInfo> getRentalReferenceByMember(String memberkey, int currentPage, int pageSize) {

		ReturnFormat<RentalHeaderInfo> returnFormat = new ReturnFormat<>();
		List<RentalHeaderInfo> dataList = new ArrayList<>();
		int totalCount= 0;
		// TODO Auto-generated method stub
		String sql = "  SELECT h.refno, h.systemkey, h.docdate, h.duedate, h.status, member.name, member.systemkey " +
						" FROM T001 h  " +
						" inner join Member member on h.membersystemkey= member.systemkey	" +
						" where h.status =1 and member.systemkey = " + memberkey ;
		
		if(pageSize !=0) {
			int start = (currentPage - 1) * pageSize;
			sql += " ORDER BY h.docdate, h.refno  OFFSET  " + start + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY "; // LIMIT ?, ?
		}else {
			sql += " ORDER BY h.docdate, h.refno";
		}
		//System.out.println(sql);
		dataList = jdbcTemplate.query(sql, (rs,rowNum)-> {
			RentalHeaderInfo result = new RentalHeaderInfo();
			result.setSystemkey(rs.getString("systemkey"));
			result.setRefno(rs.getString("refno"));
			result.setStatus(rs.getInt("status"));
			result.setDocdate(rs.getString("docdate"));
			result.setDuedate(rs.getString("duedate"));
			result.setMembersyskey(rs.getString("systemkey"));
			result.setMembername(rs.getString("name"));
			return result;
		});
		
		if (pageSize != 0) {
			String totSql = "SELECT COUNT(DISTINCT h.systemkey) AS totalCount FROM T001 h inner join Member m on h.membersystemkey=m.systemkey  where h.status =1 and m.systemkey = " + memberkey  ;
			totalCount = jdbcTemplate.queryForObject(totSql, Integer.class);
			returnFormat.setTotalcount(totalCount);
		}
	    returnFormat.setMessage("Success");
		returnFormat.setList(dataList);

		return returnFormat;
	}

}
