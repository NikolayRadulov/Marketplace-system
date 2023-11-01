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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.model.dto.ImportAdDto;
import com.app.market.service.AdService;
import com.app.market.util.TestDataService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class AdControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AdService adService;
	
	@Autowired
	private TestDataService testDataService;
	
	@BeforeAll
	public void setUp() {
		testDataService.initUsers();
		
		ImportAdDto validImportAdDto = new ImportAdDto("valid", "dto", new MockMultipartFile("someName", new byte[255]), 456.43, "some description", "burgas", "meden rudnik", null);
		adService.addNewAdd("Dragan", validImportAdDto);
	}
	
	@AfterAll
	public void tearDown() {
		testDataService.tearDownDB();
	}
	
	@Test
	@WithMockUser(username = "Dragan", authorities = {"MODERATOR","ADMIN"})
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
	@WithMockUser(username = "Dragan", roles = "USER")
	public void testSearchAdd() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/ads/search").param("text", "someText").with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adsOverview"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ads", "filters"));
	}
	
	@Test
	@WithMockUser(username = "Dragan", authorities = {"MODERATOR","ADMIN"})
	public void testDeleteAd() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.delete("/ads/deleteAd/"+testDataService.getAdIdByTitleAndOwner("valid", "Dragan")))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "Dragan", authorities = {"MODERATOR","ADMIN"})
	public void testGetAdInfo() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/getAdInfo/"+testDataService.getAdIdByTitleAndOwner("valid", "Dragan")))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adInfoPage"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ad", "user", "isProfileOwned"));
	}
	
	@Test
	@WithMockUser(username = "Dragan", authorities = {"MODERATOR","ADMIN"})
	public void testGetOverviewPage() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/ads/ads_by_category/someCategory"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("adsOverview"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("ads", "categoryName", "filters"));
	}
}
