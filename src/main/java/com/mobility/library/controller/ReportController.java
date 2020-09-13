package com.mobility.library.controller;

import com.mobility.library.info.RentalListDetailInfo;
import com.mobility.library.info.ReportCriteriaInfo;
import com.mobility.library.info.ReturnFormat;
import com.mobility.library.info.TopRentBookInfo;
import com.mobility.library.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("report")
public class ReportController {

	@Autowired
	ReportService reportService;

	@PostMapping("/findBookByDueDate")
	public ReturnFormat<RentalListDetailInfo> findByMemberSyskey(@RequestBody ReportCriteriaInfo reportCriteriaInfo) {
		return reportService.getDueBookList(reportCriteriaInfo);
	}

	@PostMapping("/getReqBookDtlsReport")
	public ReturnFormat<RentalListDetailInfo> getRequestBookListReport(@RequestBody ReportCriteriaInfo criteriaInfo) {
		return reportService.getRequestBookListReport(criteriaInfo);
	}
	
	@PostMapping("/getTopRentBook")
	public ReturnFormat<TopRentBookInfo> getTopRentBook(@RequestBody ReportCriteriaInfo criteriaInfo){
		return reportService.getTopRentBook(criteriaInfo);
	}
}
