package com.app.market.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.repository.AdRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.AdService;
import com.app.market.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class AdControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdService adService;
	
	@Autowired
	private AdRepository adRepository;
	
	@BeforeAll
	public void setUp() {
		tearDown();
		UserRegisterDto userRegisterDto = new UserRegisterDto("user", "user@abv.bg", "0894536772", "somePassword", "somePassword");
		
		userService.registerUser(userRegisterDto);
		
		ImportAdDto validImportAdDto = new ImportAdDto("valid", "dto", new MockMultipartFile("someName", new byte[255]), 456.43, "some description", "burgas", "meden rudnik", null);

		adService.addNewAdd("user", validImportAdDto);
	}
	
	@AfterAll
	public void tearDown() {
		adRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testAddAd() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/addAd"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("addAd"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("categories", "contactUserDto", "importAdDto"));
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testDeleteAd() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.delete("/ads/deleteAd/1"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testGetAd() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/getAdInfo/5"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adInfoPage"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ad", "user", "isProfileOwned"));
	}
}
