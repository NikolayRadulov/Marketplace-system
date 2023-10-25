package com.app.market.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.repository.UserRepository;
import com.app.market.service.AdService;
import com.app.market.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdService adService;
	
	@BeforeAll
	public void setUp() {
		if(userRepository.count() != 0) return;
		
		UserRegisterDto userRegisterDto = new UserRegisterDto("user", "user@abv.bg", "0894536772", "somePassword", "somePassword");
		ImportAdDto validImportAdDto = new ImportAdDto("valid", "dto", new MockMultipartFile("someName", new byte[255]), 456.43, "some description", "burgas", "meden rudnik", null);
		
		userService.registerUser(userRegisterDto);
		adService.addNewAdd("user", validImportAdDto);
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testAddAd() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/addAd"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("addAd"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("categories", "contactUserDto", "importAdDto"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/ads/addAd").with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("addAd"));
		
	}
	
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testSearchAdd() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/ads/search").param("text", "someText").with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adsOverview"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ads", "filters"));
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testDeleteAd() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.delete("/ads/deleteAd/1"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testGetAdInfo() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/getAdInfo/2"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adInfoPage"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ad", "user", "isProfileOwned"));
	}
	
	@Test
	@WithMockUser(username = "user", authorities = {"MODERATOR","ADMIN"})
	public void testGetOverviewPage() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/ads_by_category/someCategory"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adsOverview"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ads", "categoryName", "filters"));
	}
}
