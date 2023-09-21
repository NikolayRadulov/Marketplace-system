package com.app.market.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.CategoryExportDto;
import com.app.market.model.entity.Category;
import com.app.market.repository.CategoryRepository;
import com.app.market.service.CategoryService;
import com.google.gson.Gson;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final Gson gson;
	private final ModelMapper modelMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson) {
		this.categoryRepository = categoryRepository;
		this.gson = gson;
		this.modelMapper = modelMapper;
	}

	@Cacheable("categories")
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public String exportAllCategories() {
		List<CategoryExportDto> categoryExportDtos = getAllCategories()
				.stream()
				.map(e -> modelMapper.map(e, CategoryExportDto.class))
				.collect(Collectors.toList());
		return gson.toJson(categoryExportDtos);
	}

	@Override
	public void addCategories() {
		if(categoryRepository.count() != 0) return;
		
		String[] categories = {"Sports", "Cars", "Electronics", "Animals", "Furniture", "Mode", "Vacation", "Services", "Tools and machines", "Jobs", "Clothes", "Lessons and courses"};

		for (String categoryName : categories) {
			categoryRepository.save(new Category(categoryName));
		}
	}
}
