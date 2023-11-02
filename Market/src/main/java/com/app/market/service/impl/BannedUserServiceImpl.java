package com.app.market.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		bannedUserRepository.deleteById(bannedUserId);
	}

	@Override
	public void banUser(long userId, long hours) {
		User user = userRepository.findById(userId).get();
		
		if(user.getRoles().size() == 2) throw new IllegalArgumentException("Admins cannot be banned!");
		
		bannedUserRepository.save(new BannedUser(user.getUsername(), LocalDateTime.now().plusHours(hours)));
	}

	@Override
	public int getBannedUsersCount() {
		// TODO Auto-generated method stub
		return bannedUserRepository.findAll().size();
	}

	@Override
	public void clearExpiredBans() {
		List<BannedUser> toRemove = new ArrayList<>();
		for (BannedUser bannedUser : bannedUserRepository.findAll()) {
			if(checkIsBanExpired(bannedUser)) {
				toRemove.add(bannedUser);
			}
		}
		bannedUserRepository.deleteAll(toRemove);
	}
	
	private boolean checkIsBanExpired(BannedUser bannedUser) {
		if(bannedUser == null) return true;
		return bannedUser.getBanExpire().isBefore(LocalDateTime.now());
	}

	@Override
	public BannedUser checkBannedUser(String bannedUserName) {
		BannedUser bannedUser = bannedUserRepository.findByUsername(bannedUserName);
		if(bannedUser == null) return null;
		
		if(checkIsBanExpired(bannedUser)) {
			bannedUserRepository.delete(bannedUser);
			return null;
		}
		
		return bannedUser;
	}

}
