package com.mobility.library.service;

import com.mobility.library.info.RentalListDetailInfo;
import com.mobility.library.info.ReportCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.info.TopRentBookInfo;
import com.mobility.library.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReportService {

  @Autowired
  ReportRepository reportRepository;

  public ReturnFormat<RentalListDetailInfo> getDueBookList(ReportCriteriaInfo reportCriteriaInfo) {
    try {
      return reportRepository.getDueBookList(reportCriteriaInfo);
    } catch (Exception ex){
      return null;
    }
  }
  
  public ReturnFormat<RentalListDetailInfo> getRequestBookListReport(ReportCriteriaInfo criteriaInfo) {
	    ReturnFormat<RentalListDetailInfo> returnFormat = new ReturnFormat<>();
	    try {
	      returnFormat = reportRepository.getRequestBookListReport(criteriaInfo);

	    } catch (Exception ex) {
	      returnFormat.setMessage(ex.getMessage());
	    }
	 return returnFormat;
  }
  
	public ReturnFormat<TopRentBookInfo> getTopRentBook(ReportCriteriaInfo criteriaInfo) {
		ReturnFormat<TopRentBookInfo> returnFormat = new ReturnFormat<>();
		try {
			returnFormat = reportRepository.getTopRentBook(criteriaInfo);

		} catch (Exception ex) {
			returnFormat.setMessage(ex.getMessage());
		}
		return returnFormat;
	}
  
}
