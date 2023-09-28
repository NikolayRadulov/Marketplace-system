package com.app.market.service;

import java.util.List;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.dto.ReportOverviewDto;

public interface ReportService {

	void issueReport(ImportReportDto importReportDto, long reportedUserId, long senderId);
	
	List<ReportOverviewDto> getAllReports();
}