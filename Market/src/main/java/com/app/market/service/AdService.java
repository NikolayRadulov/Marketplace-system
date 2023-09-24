package com.app.market.service;

import java.util.List;

import com.app.market.model.dto.AdFilterDto;
import com.app.market.model.dto.AdOverviewDto;
import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.entity.Ad;

public interface AdService {

	List<AdOverviewDto> getByCategoryName(String categoryName);
	
	List<AdOverviewDto> getByCategoryNameAndFilters(String categoryName, AdFilterDto adFilterDto);
	
	String exportAds(List<Ad> ads);
	
	Ad addNewAdd(String username, ImportAdDto importAdDto);
	
	Ad findById(long id);
	
	AdOverviewDto findOverviewById(long id);
	
	List<AdOverviewDto> findByUser(long ownerId);
	
	public long getOwnerId(long adId);
}
