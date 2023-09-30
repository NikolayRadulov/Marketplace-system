package com.app.market.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.market.filter.BlockedUserInterceptor;
import com.app.market.service.BannedUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class MarketApplicationConfiguration {
	
	@Bean
	public Gson getGson() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.setPrettyPrinting()
				.create();
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public BlockedUserInterceptor getBlockedUserInterceptor(BannedUserService bannedService) {
		return new BlockedUserInterceptor(bannedService);
	}
}
