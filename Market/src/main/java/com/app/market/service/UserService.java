package com.app.market.service;

import java.io.IOException;
import java.util.List;

import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.dto.UserContactDto;
import com.app.market.model.dto.UserProfileOverviewDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.User;
import com.app.market.model.enums.UserRoleEnum;

public interface UserService {

	User getByName(String name);
	
	void registerUser(UserRegisterDto userRegisterDto);
	
	void loginUser(String username);

	void registerInitialUsers();
	
	UserContactDto getById(long id);
	
	UserProfileOverviewDto getProfileOverviewById(long id);
	
	void setProfilePicture(long userId, UploadFileDto uploadFileDto) throws IOException;
	
	void changeUserAuthority(long userId, String authority);
	
	String getUserAuthority(int rolesCount);
	
	int getAllUsers();
	
	int getUserCountByRole(UserRoleEnum userRoleEnum);
	
	List<String> getModeratorsUsernames();
}
