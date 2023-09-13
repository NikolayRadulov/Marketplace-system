package com.app.market.service;

import java.util.List;

import com.app.market.model.entity.Category;

public interface CategoryService {

	List<Category> getAllCategories();
	
	String exportAllCategories();
	
	void addCategories();
}
