package com.mobility.library.repository;

import com.mobility.library.info.RentalListDetailInfo;
import com.mobility.library.info.ReportCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.utility.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportRepository {
	private static final String ALL = "all";
	private static final String EQUAL = "eq";
	private static final String BETWEEN = "bt";
	private static final String BEGINWITH = "bw";
	private static final String ENDWITH = "ew";
	private static final String CONTAIN = "c";
	private static final String EMPTYSTRING = "";
	
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
           
    String whereClause= " WHERE 1=1 AND h.status <> "+ Status.VOID +" AND h.status <> "+ Status.RETURN +" AND h.status = "+ Status.CONFIRM;
    
    if(!reportCriteriaInfo.getDateOptr().equals(ALL)) {
   	 if(!reportCriteriaInfo.getFromDueDate().equals(EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(EQUAL)) {
   		 whereClause += " AND h.duedate = '" + reportCriteriaInfo.getFromDueDate() + "'";
   	 }
   	 else {
   		whereClause += " AND h.duedate < '" + reportCriteriaInfo.getFromDueDate() + "'";
   	 }
    }
    else {
	   whereClause += " AND h.duedate <= '" + reportCriteriaInfo.getFromDueDate() + "'";
    }
    if(!reportCriteriaInfo.getMemberOptr().equals(ALL)) {
    	if(reportCriteriaInfo.getMemberOptr().equals(EQUAL)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "'";
	 	    }
    	}
    	else if(reportCriteriaInfo.getMemberOptr().equals(CONTAIN)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "%'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '%" + reportCriteriaInfo.getMemberName() + "%'";
	 	    }
    	}
    	else if(reportCriteriaInfo.getMemberOptr().equals(BEGINWITH)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "%'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "%'";
	 	    }
    	}
    	else if(reportCriteriaInfo.getMemberOptr().equals(ENDWITH)) {
    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
	 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "'";
	 	    }
	 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
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
	    String whereClause= " WHERE 1=1 AND h.status <> "+ Status.VOID +" AND h.status <> "+ Status.RETURN +" AND h.status = "+ Status.CONFIRM;
	    
	    if(!reportCriteriaInfo.getDateOptr().equals(ALL)) {
	    	 if(!reportCriteriaInfo.getFromDueDate().equals(EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(EQUAL)) {
	    		 whereClause += " AND h.docdate = '" + reportCriteriaInfo.getFromDueDate() + "'";
	    	 }
	    	 else if(!reportCriteriaInfo.getFromDueDate().equals(EMPTYSTRING) && !reportCriteriaInfo.getToDueDate().equals(EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(BETWEEN)) {
	    		 whereClause += " AND h.docdate BETWEEN '" + reportCriteriaInfo.getFromDueDate() + "' AND '"+ reportCriteriaInfo.getToDueDate() + "'";
	    	 }
	    	 else if(!reportCriteriaInfo.getFromDueDate().equals(EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(BEGINWITH)) {
	    		 whereClause += " AND h.docdate LIKE '" + reportCriteriaInfo.getFromDueDate() + "%'";
	    	 }
	    	 else if(!reportCriteriaInfo.getFromDueDate().equals(EMPTYSTRING) && reportCriteriaInfo.getDateOptr().equals(ENDWITH)) {
	    		 whereClause += " AND h.docdate LIKE '%" + reportCriteriaInfo.getFromDueDate() + "'";
	    	 }
	    }
	    
	    if(!reportCriteriaInfo.getBookOptr().equals(ALL)) {
	    	 if(reportCriteriaInfo.getBookOptr().equals(EQUAL)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '" + reportCriteriaInfo.getBookCode() + "'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '" + reportCriteriaInfo.getBookName() + "'";
	 	 	    }
	    	 }
	    	 else if(reportCriteriaInfo.getBookOptr().equals(CONTAIN)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '%" + reportCriteriaInfo.getBookCode() + "%'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '%" + reportCriteriaInfo.getBookName() + "%'";
	 	 	    }
		    }
	    	else if(reportCriteriaInfo.getBookOptr().equals(BEGINWITH)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '" + reportCriteriaInfo.getBookCode() + "%'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '" + reportCriteriaInfo.getBookName() + "%'";
	 	 	    }
			}
	    	else if(reportCriteriaInfo.getBookOptr().equals(ENDWITH)) {
	    		if (!reportCriteriaInfo.getBookCode().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookcode like '%" + reportCriteriaInfo.getBookCode() + "'";
	 	 	    }
	 	 	    if (!reportCriteriaInfo.getBookName().equals(EMPTYSTRING)) {
	 	 	    	whereClause += " AND d.bookname like '%" + reportCriteriaInfo.getBookName() + "'";
	 	 	    }
			}
	    	 
	    }
	    
	    if(!reportCriteriaInfo.getMemberOptr().equals(ALL)) {
	    	if(reportCriteriaInfo.getMemberOptr().equals(EQUAL)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "'";
		 	    }
	    	}
	    	else if(reportCriteriaInfo.getMemberOptr().equals(CONTAIN)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "%'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '%" + reportCriteriaInfo.getMemberName() + "%'";
		 	    }
	    	}
	    	else if(reportCriteriaInfo.getMemberOptr().equals(BEGINWITH)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '" + reportCriteriaInfo.getMemberCode() + "%'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.name like '" + reportCriteriaInfo.getMemberName() + "%'";
		 	    }
	    	}
	    	else if(reportCriteriaInfo.getMemberOptr().equals(ENDWITH)) {
	    		if (!reportCriteriaInfo.getMemberCode().equals(EMPTYSTRING)) {
		 	    	whereClause += " AND m.code like '%" + reportCriteriaInfo.getMemberCode() + "'";
		 	    }
		 	    if (!reportCriteriaInfo.getMemberName().equals(EMPTYSTRING)) {
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
  
  
}
