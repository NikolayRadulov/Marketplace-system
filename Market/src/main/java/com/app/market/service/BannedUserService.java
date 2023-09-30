package com.app.market.service;

import com.app.market.model.entity.BannedUser;

public interface BannedUserService {

	BannedUser findByUsername(String username);
	
	void expireBan(long bannedUserId);
	
	void banUser(long userId, long hours);
}
