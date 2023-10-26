package com.app.market.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.repository.UserRepository;
import com.app.market.service.ReportService;
import com.app.market.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportsControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void setUp() {
		if(userRepository.count() == 0) {
			UserRegisterDto userRegisterDto = new UserRegisterDto("user", "user@abv.bg", "0894536772", "somePassword", "somePassword");
			UserRegisterDto userRegisterDto2 = new UserRegisterDto("user2", "user2@abv.bg", "0892136772", "somePassword", "somePassword");
	
			userService.registerUser(userRegisterDto);
			userService.registerUser(userRegisterDto2);
		}
		
		ImportReportDto importReportDto = new ImportReportDto();
		importReportDto.setReportText("set text");
		reportService.issueReport(importReportDto, 1, 2);
	}
	
	
	@Test
	@WithMockUser("user")
	public void getReportInfoPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/reports/getReportInfo/1"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("report", "userReportDto"))
		.andExpect(MockMvcResultMatchers.view().name("reportOverview"));
	}
	
	@Test
	@WithMockUser("user")
	public void markReportAsRead() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/reports/markReportAsRead/1"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/users/moderators"));
	}
}
