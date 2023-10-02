package com.app.market.web;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.market.service.ReportService;

@RequestMapping("reports")
@RestController
public class ReportRestController {

	private final ReportService reportService;
	
	public ReportRestController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	@GetMapping("/getAllReportsCount")
	public HttpEntity<Integer> getReportsCount() {
		return new HttpEntity<>(reportService.getAllReportsCount());
	}
	

}
