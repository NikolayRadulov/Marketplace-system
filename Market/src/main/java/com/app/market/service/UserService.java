package com.app.market.service;

import java.io.IOException;

import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.dto.UserContactDto;
import com.app.market.model.dto.UserProfileOverviewDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.User;

public interface UserService {

	User getByName(String name);
	
	void registerUser(UserRegisterDto userRegisterDto);
	
	void loginUser(String username);

	void registerInitialUsers();
	
	UserContactDto getById(long id);
	
	UserProfileOverviewDto getProfileOverviewById(long id);
	
	void setProfilePicture(long userId, UploadFileDto uploadFileDto) throws IOException;
	
	void changeUserAuthority(long userId, String authority);
}
