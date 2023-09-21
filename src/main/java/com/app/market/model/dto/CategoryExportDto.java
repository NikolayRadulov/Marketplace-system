package com.app.market.model.dto;

import com.google.gson.annotations.Expose;

public class CategoryExportDto {

	@Expose
	private String name;
	
	public CategoryExportDto() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
