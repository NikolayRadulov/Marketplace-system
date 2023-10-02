package com.app.market.filter;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.app.market.model.entity.BannedUser;
import com.app.market.service.BannedUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BlockedUserInterceptor implements HandlerInterceptor {

	private final BannedUserService bannedUserService;
	
	public BlockedUserInterceptor(BannedUserService bannedUsersService) {
		this.bannedUserService = bannedUsersService;
	}


	@Override
	public void postHandle(HttpServletRequest httpServletRequest, 
		HttpServletResponse httpServletResponse,  
		Object handler, ModelAndView modelAndView) throws Exception {
		Principal principal = httpServletRequest.getUserPrincipal();
		if(principal == null) return;
		
		BannedUser bannedUser = bannedUserService.checkBannedUser(principal.getName());
		
		if(bannedUser != null) {
			if(modelAndView == null) modelAndView = new ModelAndView();
			modelAndView.setStatus(HttpStatus.FORBIDDEN);
			modelAndView.addObject("expirationDate", bannedUser.getBanExpire());
			modelAndView.setViewName("errors/bannedAccount");
		}
		
	}
}
