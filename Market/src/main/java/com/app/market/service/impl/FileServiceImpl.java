package com.app.market.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.app.market.model.dto.DownloadFileDto;
import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.entity.Ad;
import com.app.market.model.entity.FileEntity;
import com.app.market.repository.FileRepository;
import com.app.market.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;
	
	public FileServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public void saveFile(UploadFileDto uploadFileDto, Ad ad) throws IOException {
	
		var file = uploadFileDto.getImg();
		
		FileEntity fileEntity = new FileEntity(file.getContentType(), file.getOriginalFilename(), file.getBytes());
		fileEntity.setAd(ad);
		
		fileRepository.save(fileEntity);
	}

	@Override
	public DownloadFileDto getFile(long id) {
		FileEntity fileEntity = fileRepository.findById(id).orElseThrow();
		
		return new DownloadFileDto(fileEntity.getFileName(), fileEntity.getContentType(), fileEntity.getBytes());
	}

}
