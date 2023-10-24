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

import com.app.market.model.dto.UserRegisterDto;
import com.app.market.repository.UserRepository;
import com.app.market.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		UserRegisterDto userRegisterDto = new UserRegisterDto("user", "user@abv.bg", "0894536772", "somePassword", "somePassword");

		if(userRepository.count() == 0) userService.registerUser(userRegisterDto);
	}
	
	@Test
	@WithMockUser(username = "user1")
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
	@WithMockUser(username = "user1")
	public void getForbiddenPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/permissions/forbidden"))
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andExpect(MockMvcResultMatchers.view().name("errors/notAuthorized"));
	}
}
