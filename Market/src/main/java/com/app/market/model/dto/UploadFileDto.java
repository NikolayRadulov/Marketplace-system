package com.app.market.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileDto {

	private MultipartFile img;

	public UploadFileDto(MultipartFile img) {
		this.img = img;
	}

	public UploadFileDto() {
		// TODO Auto-generated constructor stub
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

}
