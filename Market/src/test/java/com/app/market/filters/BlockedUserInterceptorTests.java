package com.app.market.filters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import com.app.market.filter.BlockedUserInterceptor;
import com.app.market.model.entity.BannedUser;
import com.app.market.service.BannedUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class BlockedUserInterceptorTests {

	@Mock
	private BannedUserService bannedUserService;
	
	private BlockedUserInterceptor toTest;
	
	@BeforeEach
	public void setUp() {
		toTest = new BlockedUserInterceptor(bannedUserService);
	}
	
	@Test
	public void testPostHandleBannedUser() {
		//Arrange
		String username = "someuser";
		
		HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		ModelAndView modelAndView = Mockito.mock(ModelAndView.class);
		
		Principal principal = Mockito.mock(Principal.class);
		BannedUser bannedUser = Mockito.mock(BannedUser.class);
		
		Mockito.when(httpServletRequest.getUserPrincipal()).thenReturn(principal);
		Mockito.when(principal.getName()).thenReturn(username);
		Mockito.when(bannedUserService.checkBannedUser(username)).thenReturn(bannedUser);
		//Act
		try {
			toTest.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Assert
		
		Mockito.verify(modelAndView).setViewName(anyString());
		Mockito.verify(modelAndView).setStatus(any());
	}
	
	@Test
	public void testPostHandleNotBannedUser() {
		//Arrange
		String username = "someuser";
		
		HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		ModelAndView modelAndView = Mockito.mock(ModelAndView.class);
		
		Principal principal = Mockito.mock(Principal.class);
		
		Mockito.when(httpServletRequest.getUserPrincipal()).thenReturn(principal);
		Mockito.when(principal.getName()).thenReturn(username);
		Mockito.when(bannedUserService.checkBannedUser(username)).thenReturn(null);
		//Act
		try {
			toTest.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Assert
		
		Mockito.verifyNoInteractions(modelAndView);;
	}
}
