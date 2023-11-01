package com.app.market.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.service.ReportService;
import com.app.market.util.TestDataService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ReportsControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private TestDataService testDataService;
		
	@BeforeAll
	public void setUp() {
		testDataService.initUsers();
		
		long user1Id = testDataService.getUserIdByUsername("Ivan");
		long user2Id = testDataService.getUserIdByUsername("Dragan");
		
		ImportReportDto importReportDto = new ImportReportDto();
		importReportDto.setReportText("set text");
		reportService.issueReport(importReportDto, user1Id, user2Id);
	}
	
	@AfterAll
	public void tearDown() {
		testDataService.tearDownDB();
	}
	
	@Test
	@WithMockUser(username = "Ivan", authorities = "MODERATOR")
	public void getReportInfoPage() throws Exception {
		
		long user1Id = testDataService.getUserIdByUsername("Ivan");
		long user2Id = testDataService.getUserIdByUsername("Dragan");
		
		mockMvc.perform(MockMvcRequestBuilders.get("/reports/getReportInfo/"+testDataService.getReportIdByUsersIds(user1Id, user2Id)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("report", "reportedUserId"))
		.andExpect(MockMvcResultMatchers.view().name("reportOverview"));
	}
	
	@Test
	@WithMockUser(username = "Dragan", authorities = "MODERATOR")
	public void markReportAsRead() throws Exception {	
		
		long user1Id = testDataService.getUserIdByUsername("Ivan");
		long user2Id = testDataService.getUserIdByUsername("Dragan");
		
		mockMvc.perform(MockMvcRequestBuilders.get("/reports/markReportAsRead/"+testDataService.getReportIdByUsersIds(user1Id, user2Id)))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/users/moderators"));
	}
}
