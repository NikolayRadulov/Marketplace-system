package com.app.market.model.dto;

public class AdFilterDto {

	private double minPrice;
	private double maxPrice;
	
	public AdFilterDto(double minPrice, double maxPrice) {
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public AdFilterDto() {
		// TODO Auto-generated constructor stub
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

}
