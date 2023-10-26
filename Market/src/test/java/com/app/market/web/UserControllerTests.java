package com.app.market.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.app.market.model.dto.UserRegisterDto;
import com.app.market.repository.UserRepository;
import com.app.market.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@BeforeEach
	public void setUp() {
		
	}
	
	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(get("/users/register"))
		.andExpect(status().isOk()).andExpect(view().name("register"));
		
		UserRegisterDto userRegisterDto = new UserRegisterDto("user5", "user5@abv.bg", "0844536772", "somePassword", "somePassword");
		
		System.out.println(userRegisterDto.getPassword());
		
		mockMvc.perform(post("/users/register").with(csrf()).
				flashAttr("userRegisterDto", userRegisterDto))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"));
		
		mockMvc.perform(post("/users/register").with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("register"));
	}
}
