package com.app.market.model.dto;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

public class ExportAdDto {

	@Expose
	private String name;
	
	@Expose
	private BigDecimal price;
	
	@Expose
	private String description;
	
	public ExportAdDto() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
