package com.app.market.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.market.service.CategoryService;

@RestController
public class CategoryController {

	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/loadCategories")
	public String loadCategories() {
		categoryService.addCategories();
		return "redirect:/";
	}

	@GetMapping("/categories/getAll")
	public ResponseEntity<String> getAllCategories() {
		
		return new ResponseEntity<String>(categoryService.exportAllCategories(), HttpStatus.OK);
	}

}
