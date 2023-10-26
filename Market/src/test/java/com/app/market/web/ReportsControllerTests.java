package com.app.market.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.entity.User;
import com.app.market.repository.UserRepository;
import com.app.market.service.ReportService;

@SpringBootApplication
@AutoConfigureMockMvc
public class ReportsControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void getReportInfoPage() throws Exception {
		
		userRepository.save(new User());
		userRepository.save(new User());
		
		ImportReportDto importReportDto = new ImportReportDto();
		importReportDto.setReportText("set text");
		reportService.issueReport(importReportDto, 1, 2);
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/reports/markReportAsRead/1"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("report", "userReportDto"));
	}
}
