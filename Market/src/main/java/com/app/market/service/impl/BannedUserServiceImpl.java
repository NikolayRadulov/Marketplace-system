package com.app.market.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.app.market.model.entity.BannedUser;
import com.app.market.model.entity.User;
import com.app.market.repository.BannedUserRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.BannedUserService;

@Service
public class BannedUserServiceImpl implements BannedUserService {

	private final UserRepository userRepository;
	private final BannedUserRepository bannedUserRepository;
	
	public BannedUserServiceImpl(BannedUserRepository bannedUserRepository, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.bannedUserRepository = bannedUserRepository;
	}

	@Override
	public BannedUser findByUsername(String username) {
		return bannedUserRepository.findByUsername(username);
	}

	@Override
	public void expireBan(long bannedUserId) {
		BannedUser bannedUser = bannedUserRepository.findById(bannedUserId).get();
		bannedUserRepository.delete(bannedUser);
	}

	@Override
	public void banUser(long userId, long hours) {
		User user = userRepository.findById(userId).get();
		bannedUserRepository.save(new BannedUser(user.getUsername(), LocalDateTime.now().plusHours(hours)));
	}

}
