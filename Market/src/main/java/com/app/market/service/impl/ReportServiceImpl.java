package com.app.market.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.dto.ReportOverviewDto;
import com.app.market.model.entity.Report;
import com.app.market.model.entity.User;
import com.app.market.repository.ReportRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;


	public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository,
			ModelMapper modelMapper) {
		this.reportRepository = reportRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}



	@Override
	public void issueReport(ImportReportDto importReportDto, long reportedUserId, long senderId) {
		// TODO: Add user filter user senderUser = userRepository.findById(senderId).get();
		User reportedUser = userRepository.findById(reportedUserId).get();
		Report report = modelMapper.map(importReportDto, Report.class);
		report.setReportedUser(reportedUser);

		reportRepository.save(report);
	}



	@Override
	public List<ReportOverviewDto> getAllReports() {
		return this.reportRepository.findAll().stream()
				.map(report -> this.mapReportToOverview(report))
				.collect(Collectors.toList());
	}
	
	private ReportOverviewDto mapReportToOverview(Report report) {
		return new ReportOverviewDto(report.getId(), report.getReportedUser().getId(), report.getReportText());
	}

}