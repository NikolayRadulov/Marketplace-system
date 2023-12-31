package com.app.market.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.market.filter.BlockedUserInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	private final BlockedUserInterceptor blockedUserInterceptor;
	
	public WebConfiguration(BlockedUserInterceptor blockedUserInterceptor) {
		this.blockedUserInterceptor = blockedUserInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(blockedUserInterceptor);
	}

}
