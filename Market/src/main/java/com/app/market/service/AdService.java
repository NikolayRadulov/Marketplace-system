package com.app.market.service;

import java.util.List;

import com.app.market.model.entity.Ad;

public interface AdService {

	List<Ad> getByCategoryName(String categoryName);
	String exportAds(List<Ad> ads);
}
