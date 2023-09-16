package com.app.market.service;

import com.app.market.model.dto.UserRegisterDto;

public interface UserService {

	void registerUser(UserRegisterDto userRegisterDto);
	
	void loginUser(String username);

	void registerInitialUsers();
}
