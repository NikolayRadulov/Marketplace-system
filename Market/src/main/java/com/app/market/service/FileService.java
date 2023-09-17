package com.app.market.service;

import java.io.IOException;

import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.entity.Ad;

public interface FileService {

	void saveFile(UploadFileDto uploadFileDto, Ad ad) throws IOException;
}
