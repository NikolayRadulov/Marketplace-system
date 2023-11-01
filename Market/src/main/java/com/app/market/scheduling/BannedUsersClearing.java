package com.app.market.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.market.service.BannedUserService;

@Component
public class BannedUsersClearing {

	private final BannedUserService bannedUserService;
	
	public BannedUsersClearing(BannedUserService bannedUserService) {
		this.bannedUserService = bannedUserService;
	}
	

	@Scheduled(cron = "0 0 12 * * ?")
	public void clearExpiredBans() {
		bannedUserService.clearExpiredBans();
	}
	
}
