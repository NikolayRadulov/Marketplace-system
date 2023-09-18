package com.app.market.model.dto;

public class DownloadFileDto {

	private String fileName;
	private String contentType;
	private byte[] data;
	
	public DownloadFileDto(String fileName, String contentType, byte[] data) {
		this.fileName = fileName;
		this.contentType = contentType;
		this.data = data;
	}

	public DownloadFileDto() {
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
