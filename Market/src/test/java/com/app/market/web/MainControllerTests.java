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

import com.app.market.util.TestDataService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class MainControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TestDataService testDataService;
	
	@BeforeAll
	public void setUp() {
		testDataService.initUsers();
	}
	
	@AfterAll
	public void tearDown() {
		testDataService.tearDownDB();
	}
	
	@Test
	@WithMockUser(username = "Dragan")
	public void getMainPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("index"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("categories","isOnMainPage","userId"));
	}
	
	@Test
	public void getMainPageWithoutLogin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("index"))
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("userId"));
	}
	
	@Test
	@WithMockUser(username = "Dragan")
	public void getForbiddenPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/permissions/forbidden"))
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andExpect(MockMvcResultMatchers.view().name("errors/notAuthorized"));
	}
}
