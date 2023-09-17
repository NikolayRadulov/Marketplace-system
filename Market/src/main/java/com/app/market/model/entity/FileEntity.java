package com.app.market.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class FileEntity extends BaseEntity {

	@Column(name = "content_type")
	private String contentType;
	
	@Column(nullable = false, name = "file_name")
	private String fileName;
	
	@Lob
	@Column(length = Integer.MAX_VALUE)
	private byte[] bytes;
	
	@ManyToOne
	private Ad ad;
	
	public FileEntity(String contentType, String fileName, byte[] bytes) {
		this.contentType = contentType;
		this.fileName = fileName;
		this.bytes = bytes;
	}


	public FileEntity() {
		// TODO Auto-generated constructor stub
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public Ad getAd() {
		return ad;
	}


	public void setAd(Ad ad) {
		this.ad = ad;
	}

}
