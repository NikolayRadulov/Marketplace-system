package com.app.market.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.market.service.CategoryService;
import com.app.market.service.UserService;

@Component
public class DBInitService implements CommandLineRunner {

	private final CategoryService categoryService;
	private final UserService userService;
	
	
	public DBInitService(UserService userService, CategoryService categoryService) {
		this.categoryService = categoryService;
		this.userService = userService;
	}

	@Override
	public void run(String... args) throws Exception {
		userService.registerInitialUsers();
		categoryService.addCategories();
	}

}
