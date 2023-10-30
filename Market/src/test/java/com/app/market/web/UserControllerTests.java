package com.app.market.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.market.repository.ReportRepository;
import com.app.market.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@AfterAll
	public void setUp() {
		if(userRepository.count() != 0) {
			reportRepository.deleteAll();
			userRepository.deleteAll();
		}
	}
	
	@Test
	public void testRegister() throws Exception {
		
		mockMvc.perform(get("/users/register"))
		.andExpect(status().isOk()).andExpect(view().name("register"));
						
		mockMvc.perform(post("/users/register").with(csrf())
				.param("username", "user")
				.param("email", "example@abv.bg")
				.param("phoneNumber", "0889453256")
				.param("password", "somePassword")
				.param("confirmPassword", "somePassword"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"));
		
		mockMvc.perform(post("/users/register").with(csrf())
				.param("password", "")
				.param("confirmPassword", ""))
		.andExpect(status().isOk())
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
	@WithMockUser(username = "moderator", authorities = {"MODERATOR"})
	public void testChangeRole() throws Exception {
		mockMvc.perform(post("/users/changeRole/1").with(csrf())
				.param("authority", "MODERATOR"))
		.andExpect(status().is3xxRedirection());
	}
	@Test
	@WithMockUser("someUser")
	public void testRandomProfileOverview() throws Exception {
		mockMvc.perform(get("/users/profile/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("profileOverview"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("user", "isUserAdmin", "isProfileOwned",
															"profileAuthority","userStarRating","ads","report","rating"))
		.andExpect(MockMvcResultMatchers.model().attribute("isProfileOwned", false));
		
	}
	@Test
	@WithMockUser("user")
	public void testProfileOwnedOverview() throws Exception {
		mockMvc.perform(get("/users/profile/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("profileOverview"))
		.andExpect(MockMvcResultMatchers.model().attribute("isProfileOwned", true));
		
	}
	
	@Test
	@WithMockUser("someUser")
	public void testProfileSearch() throws Exception {
		mockMvc.perform(post("/users/profileSearch").with(csrf())
				.param("profileName", "user"))
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
				.param("profileName", "user"))
		.andExpect(status().is3xxRedirection());
		
		mockMvc.perform(post("/users/profileSearch").with(csrf())
				.param("profileName", "notExisting"))
		.andExpect(status().isOk())
		.andExpect(view().name("errors/noSuchUser"));
	}
}
