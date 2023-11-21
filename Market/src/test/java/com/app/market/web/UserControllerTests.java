package com.app.market.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.util.TestDataService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTests {

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
	public void testRegister() throws Exception {
		
		mockMvc.perform(get("/users/register"))
		.andExpect(status().isOk()).andExpect(view().name("register"));
						
		mockMvc.perform(post("/users/register").with(csrf())
				.param("username", "Ilian2")
				.param("email", "ilian2@abv.bg")
				.param("phoneNumber", "0813431256")
				.param("password", "somePA*93ssword")
				.param("confirmPassword", "somePA*93ssword"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"));
		
		mockMvc.perform(post("/users/register").with(csrf())
				.param("password", "")
				.param("confirmPassword", ""))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().errorCount(8))
		.andExpect(view().name("register"));
	}
	
	@Test
	@WithMockUser(username = "admin", authorities = {"ADMIN","MODERATOR"})
	public void testAdminGetAdminAndModeratorPages() throws Exception {
		mockMvc.perform(get("/users/admins"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin"));
		
		mockMvc.perform(get("/users/moderators"))
		.andExpect(status().isOk())
		.andExpect(view().name("moderatorPage"));
	}
	
	@Test
	@WithMockUser(username = "Dragan", authorities = {"MODERATOR"})
	public void testChangeRole() throws Exception {
		mockMvc.perform(post("/users/changeRole/" + testDataService.getUserIdByUsername("Ilian2")).with(csrf())
				.param("authority", "MODERATOR"))
		.andExpect(status().is3xxRedirection());
	}
	@Test
	@WithMockUser("someUser")
	public void testRandomProfileOverview() throws Exception {
		mockMvc.perform(get("/users/profile/" + testDataService.getUserIdByUsername("Ilian2")).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name("profileOverview"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("user", "isUserAdmin", "isProfileOwned",
															"profileAuthority","userStarRating","ads","report","rating"))
		.andExpect(MockMvcResultMatchers.model().attribute("isProfileOwned", false));
		
	}
	@Test
	@WithMockUser("Ilian2")
	public void testProfileOwnedOverview() throws Exception {
		mockMvc.perform(get("/users/profile/" + testDataService.getUserIdByUsername("Ilian2")))
		.andExpect(status().isOk())
		.andExpect(view().name("profileOverview"))
		.andExpect(MockMvcResultMatchers.model().attribute("isProfileOwned", true));
		
	}
	
	@Test
	@WithMockUser("someUser")
	public void testProfileSearch() throws Exception {
		mockMvc.perform(post("/users/profileSearch").with(csrf())
				.param("profileName", "Ilian2"))
		.andExpect(status().is3xxRedirection());
		
		mockMvc.perform(post("/users/profileSearch").with(csrf())
				.param("profileName", "notExisting"))
		.andExpect(status().isOk())
		.andExpect(view().name("errors/noSuchUser"));
	}
	
	@Test
	@WithMockUser("someUser")
	public void testReportUser() throws Exception {
		mockMvc.perform(post("/users/profileSearch").with(csrf())
				.param("profileName", "Ilian2"))
		.andExpect(status().is3xxRedirection());
		
		mockMvc.perform(post("/users/profileSearch").with(csrf())
				.param("profileName", "notExisting"))
		.andExpect(status().isOk())
		.andExpect(view().name("errors/noSuchUser"));
	}
}
