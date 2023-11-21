package com.app.market.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.app.market.service.CategoryService;
import com.app.market.service.UserService;

@Controller
public class MainController {
	
	private final CategoryService categoryService;
	private final UserService userService;
	
	public MainController(CategoryService categoryService, UserService userService) {
		this.categoryService = categoryService;
		this.userService = userService;
	}
	
	
	@GetMapping("/")
	public String getHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("isOnMainPage", true);
		
		if (userDetails != null) model.addAttribute("userId", userService.getByName(userDetails.getUsername()).getId());
		return "index";
	}
	
	@GetMapping("/permissions/forbidden")
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public String pageNotVisible() {
		return "errors/notAuthorized";
	}
	
	@GetMapping("/error")
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public String getErrorPage() {
		return "error.html";
	}

}
