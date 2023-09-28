package com.app.market.service;

import com.app.market.model.dto.ImportReportDto;

public interface ReportService {

	void issueReport(ImportReportDto importReportDto, long reportedUserId, long senderId);
}
