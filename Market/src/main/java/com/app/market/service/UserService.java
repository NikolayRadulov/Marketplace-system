package com.app.market.service;

import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.User;

public interface UserService {

	User getByName(String name);
	
	void registerUser(UserRegisterDto userRegisterDto);
	
	void loginUser(String username);

	void registerInitialUsers();
}
