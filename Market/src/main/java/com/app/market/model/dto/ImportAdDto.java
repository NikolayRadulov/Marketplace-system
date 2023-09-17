package com.app.market.model.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ImportAdDto {

	@Size(min = 4, max = 50)
	private String title;

	private String category;
	
	private MultipartFile image1;
	private MultipartFile image2;
	private MultipartFile image3;
	private MultipartFile image4;
	private MultipartFile image5;
	
	@Positive
	private double price;
	
	@Size(min = 30, max = 3000)
	private String description;
	
	private String city;
	private String address;
	private String cityZone;	
	
	public ImportAdDto() {
		// TODO Auto-generated constructor stub
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public MultipartFile getImage1() {
		return image1;
	}


	public void setImage1(MultipartFile image1) {
		this.image1 = image1;
	}


	public MultipartFile getImage2() {
		return image2;
	}


	public void setImage2(MultipartFile image2) {
		this.image2 = image2;
	}


	public MultipartFile getImage3() {
		return image3;
	}


	public void setImage3(MultipartFile image3) {
		this.image3 = image3;
	}


	public MultipartFile getImage4() {
		return image4;
	}


	public void setImage4(MultipartFile image4) {
		this.image4 = image4;
	}


	public MultipartFile getImage5() {
		return image5;
	}


	public void setImage5(MultipartFile image5) {
		this.image5 = image5;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCityZone() {
		return cityZone;
	}


	public void setCityZone(String cityZone) {
		this.cityZone = cityZone;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
