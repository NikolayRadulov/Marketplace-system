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

import com.app.market.model.dto.ImportRatingDto;

@SpringBootTest
@AutoConfigureMockMvc
public class RaitingsControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser("user")
	public void testAddRating() throws Exception {
		ImportRatingDto importRatingDto = new ImportRatingDto();
		importRatingDto.setScore(3.4);
		importRatingDto.setComment("Some comment");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/ads/addRating/1").with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.forwardedUrl("/users/profile/1"));
	}
	
	
}
