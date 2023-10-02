package com.app.market.service;

import java.util.List;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.dto.ReportOverviewDto;

public interface ReportService {

	void issueReport(ImportReportDto importReportDto, long reportedUserId, long senderId);
	
	List<ReportOverviewDto> getAllReports();
	
	ReportOverviewDto getById(long id);
	
	void removeReport(long id);
	
	void removeAllUserReports(long userId);
	
	int getAllReportsCount();
}