package com.app.market.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}

}
