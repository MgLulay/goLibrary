package com.mobility.library.repository;

import com.mobility.library.info.RentalListDetailInfo;
import com.mobility.library.info.ReportCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.info.TopRentBookInfo;
import com.mobility.library.utility.StatusUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public ReturnFormat<RentalListDetailInfo> getDueBookList(ReportCriteriaInfo reportCriteriaInfo){
	ReturnFormat<RentalListDetailInfo> returnFormat = new ReturnFormat<>();
    List<RentalListDetailInfo> reportListDetailInfoList = new ArrayList<>();
    int totalCount= 0;
    
    String sql = "" +
            " select h.refno, member.code, member.name, h.docdate as docdate, h.duedate, h.returndate as returndate," +
            " book.code as bookcode, book.name as bookname, d.quantity, author.name as authorname" +
            " from T001 h inner join T002 d on h.systemkey=d.parentsystemkey" +
            " inner join Member member on h.membersystemkey= member.systemkey" +
            " inner join Book book on d.booksystemkey = book.systemkey" +
            " inner join Author author on d.authorsystemkey = author.systemkey" ;
           
    String whereClause= " WHERE 1=1 AND h.status <> "+ StatusUtil.VOID +" AND h.status <> "+ StatusUtil.RETURN +" AND h.status = "+ StatusUtil.CONFIRM;
    
    if(!reportCriteriaInfo.getDateOptr().equals(StatusUtil.ALL)) {
   	 if(!reportCriteriaInfo.getFromDueDate().equals(StatusUtil.EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(StatusUtil.EQUAL)) {
   		 whereClause += " AND h.duedate = '" + reportCriteriaInfo.getFromDueDate() + "'";
   	 }
   	 else {
   		whereClause += " AND h.duedate < '" + reportCriteriaInfo.getFromDueDate() + "'";
   	 }
    }
    else {
	   whereClause += " AND h.duedate <= '" + reportCriteriaInfo.getFromDueDate() + "'";
    }
    if(!reportCriteriaInfo.getMemberOptr().equals(StatusUtil.ALL)) {
    	if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.EQUAL)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "'";
	 	    }
    	}
    	else if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.CONTAIN)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "%'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '%" + reportCriteriaInfo.getMemberName() + "%'";
	 	    }
    	}
    	else if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.BEGINWITH)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "%'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "%'";
	 	    }
    	}
    	else if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.ENDWITH)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '%" + reportCriteriaInfo.getMemberName() + "'";
	 	    }
    	} 
    }
    sql += whereClause;
	if(reportCriteriaInfo.getPagesize() !=0) {
		int currentPage = reportCriteriaInfo.getCurrentpage();
		int pageSize = reportCriteriaInfo.getPagesize();
		int start = (currentPage - 1) * pageSize;
		sql += " ORDER BY d.bookcode  OFFSET  " + start + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY "; // LIMIT ?, ?
	}else {
		sql += " ORDER BY d.bookcode";
	}
    reportListDetailInfoList = jdbcTemplate.query(sql, (rs,rowNum)-> {
      RentalListDetailInfo rentalListDetailInfo = new RentalListDetailInfo();
      rentalListDetailInfo.setRefno(rs.getString("refno"));
      rentalListDetailInfo.setCode(rs.getString("code"));
      rentalListDetailInfo.setName(rs.getString("name"));
      rentalListDetailInfo.setDocdate(rs.getString("docdate"));
      rentalListDetailInfo.setDuedate(rs.getString("duedate"));
      rentalListDetailInfo.setReturndate(rs.getString("returndate"));
      rentalListDetailInfo.setBookcode(rs.getString("bookcode"));
      rentalListDetailInfo.setBookname(rs.getString("bookname"));
      rentalListDetailInfo.setQuantity(rs.getInt("quantity"));
      rentalListDetailInfo.setAuthorname(rs.getString("authorname"));

      return rentalListDetailInfo;
    });
    returnFormat.setList(reportListDetailInfoList);
	
    if (reportCriteriaInfo.getPagesize() != 0) {
    	sql = "SELECT COUNT(DISTINCT d.systemkey) AS totalCount "
    			+ " FROM T001 h "
	            + " inner join T002 d on h.systemkey=d.parentsystemkey "
	            + " inner join Member member on h.membersystemkey=member.systemkey "
	            + " inner join Book book on d.booksystemkey = book.systemkey "
	            + " inner join Author author on d.authorsystemkey=author.systemkey "
    			+ whereClause ;
    	totalCount= jdbcTemplate.queryForObject(sql, Integer.class);
		returnFormat.setTotalcount(totalCount);
	}
    returnFormat.setMessage("Success");
   return returnFormat;
  }
  
  public ReturnFormat<RentalListDetailInfo> getRequestBookListReport(ReportCriteriaInfo reportCriteriaInfo){
	  ReturnFormat<RentalListDetailInfo> returnFormat = new ReturnFormat<>();
	  int totalCount= 0;
	  List<RentalListDetailInfo> dataList = new ArrayList<>();
	  
	    String sql = " SELECT d.systemkey, d.parentsystemkey, d.booksystemkey, d.bookcode, d.bookname, d.quantity, au.systemkey as authorsystemkey, au.name as authorname, m.name as membername " 
	            + " FROM T001 h "
	            + " inner join T002 d on h.systemkey=d.parentsystemkey "
	            + " inner join Member m on h.membersystemkey=m.systemkey "
	            + " inner join Author au on d.authorsystemkey=au.systemkey " ;
	    String whereClause= " WHERE 1=1 AND h.status <> "+ StatusUtil.VOID +" AND h.status <> "+ StatusUtil.RETURN +" AND h.status = "+ StatusUtil.CONFIRM;
	    
	    if(!reportCriteriaInfo.getDateOptr().equals(StatusUtil.ALL)) {
	    	 if(!reportCriteriaInfo.getFromDueDate().equals(StatusUtil.EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(StatusUtil.EQUAL)) {
	    		 whereClause += " AND h.docdate = '" + reportCriteriaInfo.getFromDueDate() + "'";
	    	 }
	    	 else if(!reportCriteriaInfo.getFromDueDate().equals(StatusUtil.EMPTYSTRING) && !reportCriteriaInfo.getToDueDate().equals(StatusUtil.EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(StatusUtil.BETWEEN)) {
	    		 whereClause += " AND h.docdate BETWEEN '" + reportCriteriaInfo.getFromDueDate() + "' AND '"+ reportCriteriaInfo.getToDueDate() + "'";
	    	 }
	    	 else if(!reportCriteriaInfo.getFromDueDate().equals(StatusUtil.EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(StatusUtil.BEGINWITH)) {
	    		 whereClause += " AND h.docdate LIKE '" + reportCriteriaInfo.getFromDueDate() + "%'";
	    	 }
	    	 else if(!reportCriteriaInfo.getFromDueDate().equals(StatusUtil.EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(StatusUtil.ENDWITH)) {
	    		 whereClause += " AND h.docdate LIKE '%" + reportCriteriaInfo.getFromDueDate() + "'";
	    	 }
	    }
	    
	    if(!reportCriteriaInfo.getBookOptr().equals(StatusUtil.ALL)) {
	    	 if(reportCriteriaInfo.getBookOptr().equals(StatusUtil.EQUAL)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '" + reportCriteriaInfo.getBookCode() + "'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '" + reportCriteriaInfo.getBookName() + "'";
	 	 	    }
	    	 }
	    	 else if(reportCriteriaInfo.getBookOptr().equals(StatusUtil.CONTAIN)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '%" + reportCriteriaInfo.getBookCode() + "%'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '%" + reportCriteriaInfo.getBookName() + "%'";
	 	 	    }
		    }
	    	else if(reportCriteriaInfo.getBookOptr().equals(StatusUtil.BEGINWITH)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '" + reportCriteriaInfo.getBookCode() + "%'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '" + reportCriteriaInfo.getBookName() + "%'";
	 	 	    }
			}
	    	else if(reportCriteriaInfo.getBookOptr().equals(StatusUtil.ENDWITH)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '%" + reportCriteriaInfo.getBookCode() + "'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(StatusUtil.EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '%" + reportCriteriaInfo.getBookName() + "'";
	 	 	    }
			}
	    	 
	    }
	    
	    if(!reportCriteriaInfo.getMemberOptr().equals(StatusUtil.ALL)) {
	    	if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.EQUAL)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "'";
		 	    }
	    	}
	    	else if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.CONTAIN)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "%'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '%" + reportCriteriaInfo.getMemberName() + "%'";
		 	    }
	    	}
	    	else if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.BEGINWITH)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "%'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "%'";
		 	    }
	    	}
	    	else if(reportCriteriaInfo.getMemberOptr().equals(StatusUtil.ENDWITH)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(StatusUtil.EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '%" + reportCriteriaInfo.getMemberName() + "'";
		 	    }
	    	}
	    	 
	    }
	   
	    sql += whereClause;
		if(reportCriteriaInfo.getPagesize() !=0) {
			int currentPage = reportCriteriaInfo.getCurrentpage();
			int pageSize = reportCriteriaInfo.getPagesize();
			int start = (currentPage - 1) * pageSize;
			sql += " ORDER BY d.bookcode  OFFSET  " + start + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY "; // LIMIT ?, ?
		}else {
			sql += " ORDER BY d.bookcode";
		}
		System.out.println(sql);
		dataList = jdbcTemplate.query(sql, (rs,rowNum)-> {
			RentalListDetailInfo result = new RentalListDetailInfo();
			result.setSystemkey(rs.getString("systemkey"));
			result.setParentsystemkey(rs.getString("parentsystemkey"));
			result.setName(rs.getString("membername"));
			result.setBookcode(rs.getString("bookcode"));
			result.setBookname(rs.getString("bookname"));
			result.setAuthorsystemkey(rs.getString("authorsystemkey"));
			result.setAuthorname(rs.getString("authorname"));
			result.setQuantity(rs.getInt("quantity"));
			return result;
		});
		returnFormat.setList(dataList);
		
	    if (reportCriteriaInfo.getPagesize() != 0) {
	    	sql = "SELECT COUNT(DISTINCT d.systemkey) AS totalCount "
	    			+ " FROM T001 h "
		            + " inner join T002 d on h.systemkey=d.parentsystemkey "
		            + " inner join Member m on h.membersystemkey=m.systemkey "
		            + " inner join Author au on d.authorsystemkey=au.systemkey "
	    			+ whereClause ;
	    	totalCount= jdbcTemplate.queryForObject(sql, Integer.class);
			returnFormat.setTotalcount(totalCount);
		}
	    returnFormat.setMessage("Success");
	  return returnFormat;
  }
  
  public ReturnFormat<TopRentBookInfo> getTopRentBook(ReportCriteriaInfo reportCriteriaInfo){
	  
	  ReturnFormat<TopRentBookInfo> returnFormat = new ReturnFormat<>();
	  List<TopRentBookInfo> topRentList = new ArrayList<>();
	  String whereclause = "";
	  String orderclause = "";
	  String groupbyclause = "";
	  int totalcount = 0;
	  String query = "select  b.systemkey, b.name, b.code , author.name as authorname, count(b.systemkey) as TotalRentCount "  
	  				 + " from T001 h inner join T002 d on h.systemkey = d.parentsystemkey " 
	  				 + " inner join Book b on d.booksystemkey = b.systemkey "	  				 
	  				 + " inner join Author author on b.author = author.systemkey "
	  				 + " where  h.status <> " + StatusUtil.VOID;
	  
	  if(reportCriteriaInfo.getFromDocDate() != "") {
		  whereclause += " and h.docdate >= '" + reportCriteriaInfo.getFromDocDate() + "'" ;
	  }
	  
	  if(reportCriteriaInfo.getToDocDate() != "") {
		  whereclause += " and h.docdate <= '" + reportCriteriaInfo.getToDocDate() + "'" ;
	  }
	
	  groupbyclause = " group by  b.systemkey, b.name, b.code, author.name ";
	  if(reportCriteriaInfo.getPagesize() !=0) {
			int currentPage = reportCriteriaInfo.getCurrentpage();
			int pageSize = reportCriteriaInfo.getPagesize();
			int start = (currentPage - 1) * pageSize;
			orderclause += " ORDER BY count(b.systemkey) desc  OFFSET  " + start + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY "; 
		}else {
			orderclause += " ORDER BY count(b.systemkey) desc ";
		}
	  query += whereclause + groupbyclause + orderclause ;
	  topRentList = jdbcTemplate.query(query, (rs, rowNum)-> {
		  TopRentBookInfo topRentBookInfo = new TopRentBookInfo();
		  topRentBookInfo.setBooksystemkey(rs.getString("systemkey"));
		  topRentBookInfo.setCode(rs.getString("code"));
		  topRentBookInfo.setName(rs.getString("name"));
		  topRentBookInfo.setAuthorname(rs.getString("authorname"));
		  topRentBookInfo.setTotalCount(rs.getInt("TotalRentCount"));
		  
		  return topRentBookInfo;
	  }) ;
	  
		returnFormat.setList(topRentList);

		if (reportCriteriaInfo.getPagesize() != 0) {
			query = " select count(*) from "
					+ " (select  b.systemkey, b.name, b.code , author.name as authorname, count(b.systemkey) as TotalRentCount "
					+ "  from T001 h inner join T002 d on h.systemkey = d.parentsystemkey "
					+ " inner join Book b on d.booksystemkey = b.systemkey "
					+ "  inner join Author author on b.author = author.systemkey ";
			query += whereclause + groupbyclause + ") src";
			totalcount= jdbcTemplate.queryForObject(query, Integer.class);
			returnFormat.setTotalcount(totalcount);
		}
		returnFormat.setMessage("Success");
		return returnFormat;
	  
  }
   
  
  
}
