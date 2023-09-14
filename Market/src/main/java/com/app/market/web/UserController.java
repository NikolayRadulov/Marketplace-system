package com.app.market.web;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.market.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UserDetailsService userDetailsService;
	
	public UserController(UserService userService, UserDetailsService userDetailsService) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}

	
	@GetMapping("/loadUsers")
	public String redirectHomeFromLoading() {
		userService.registerInitialUsers();
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String getRegisterPage() {
		return "register.html";
	}
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin.html";
	}
	

}
