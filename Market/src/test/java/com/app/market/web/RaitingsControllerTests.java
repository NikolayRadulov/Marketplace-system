package com.app.market.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RaitingsControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser("user")
	public void testAddRating() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.post("/ratings/addRating/1").with(csrf()))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/users/profile/1"));
	}
	
	
}
