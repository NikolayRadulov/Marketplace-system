package com.app.market.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.market.service.CategoryService;

@Controller
public class MainController {
	
	private final CategoryService categoryService;
	
	public MainController(CategoryService categoryService) {
		this.categoryService = categoryService;
		// TODO Auto-generated constructor stub
	}
	
	
	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "index.html";
	}

}
