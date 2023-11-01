package com.app.market.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
public class RaitingsControllerTests {
	
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
	@WithMockUser("user")
	public void testAddRating() throws Exception {		
		long id = testDataService.getUserIdByUsername("Dragan");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/ratings/addRating/"+id).with(csrf()))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/users/profile/"+id));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/ratings/addRating/"+id).with(csrf())
				.param("score", "4.3")
				.param("comment", "someComment"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.model().errorCount(0))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/users/profile/"+id));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/ratings/addRating/"+id).with(csrf())
				.param("score", "4.3")
				.param("comment", "someComment"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.model().errorCount(0))
		.andExpect(MockMvcResultMatchers.flash().attributeExists("alreadyRated"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/users/profile/"+id));
	}
	
	
}
