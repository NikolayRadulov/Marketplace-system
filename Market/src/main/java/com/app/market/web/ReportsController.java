package com.app.market.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.market.model.dto.ReportOverviewDto;
import com.app.market.service.ReportService;

@RequestMapping("/reports")
@Controller
public class ReportsController {

	private final ReportService reportService;
	public ReportsController(ReportService reportService) {
		this.reportService = reportService;
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/getReportInfo/{id}")
	public String getReportInfoPage(Model model, @PathVariable("id")long id) {
		ReportOverviewDto reportOverviewDto = reportService.getById(id);
		
		model.addAttribute("report", reportOverviewDto);
		model.addAttribute("reportedUserId", reportOverviewDto.getReportedUserId());
		
		return "reportOverview";
	}
	
	@GetMapping("/markReportAsRead/{id}")
	public String redirectToModeratorPanel(@PathVariable("id")long id) {
		reportService.removeReport(id);
		return "redirect:/users/moderators";
	}

}
