package com.app.market.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.AdFilterDto;
import com.app.market.model.dto.AdOverviewDto;
import com.app.market.model.dto.ExportAdDto;
import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.entity.Ad;
import com.app.market.model.entity.Location;
import com.app.market.model.entity.User;
import com.app.market.repository.AdRepository;
import com.app.market.repository.CategoryRepository;
import com.app.market.repository.LocationRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.AdService;
import com.google.gson.Gson;

@Service
public class AdServiceImpl implements AdService {

	private final AdRepository adRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final LocationRepository locationRepository;
	private final Gson gson;
	private final ModelMapper modelMapper;
	
	public AdServiceImpl(AdRepository adRepository, Gson gson, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, LocationRepository locationRepository) {
		this.adRepository = adRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
		this.gson = gson;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<AdOverviewDto> getByCategoryName(String categoryName) {
		List<AdOverviewDto> adOverviewDtos = new ArrayList<>();
		for(Ad ad : adRepository.findByCategory(categoryName)) {
			adOverviewDtos.add(new AdOverviewDto(ad.getId() ,ad.getName(),ad.getLocation().getCity() + ", " + ad.getLocation().getAddress(), ad.getPrice().doubleValue()));
		}
		return adOverviewDtos;
	}

	@Override
	public String exportAds(List<Ad> ads) {
		List<ExportAdDto> exportAdDtos = ads.stream().map(dto -> modelMapper.map(dto, ExportAdDto.class)).collect(Collectors.toList());
		return gson.toJson(exportAdDtos);
	}

	@Override
	public Ad addNewAdd(String username, ImportAdDto importAdDto) {
		User user = userRepository.findByUsername(username);
		
		Ad ad = new Ad(importAdDto.getTitle(), new BigDecimal(importAdDto.getPrice()), importAdDto.getDescription());
		Location location = new Location(importAdDto.getCity(), importAdDto.getAddress(), importAdDto.getCityZone());
		
		locationRepository.save(location);
		
		ad.setOwner(user);
		ad.setCategory(categoryRepository.getByName(importAdDto.getCategory()));
		ad.setLocation(location);
		
		return adRepository.save(ad);
	}

	@Override
	public Ad findById(long id) {
		return adRepository.findById(id).get();
	}

	@Override
	public List<AdOverviewDto> getByCategoryNameAndFilters(String categoryName, AdFilterDto adFilterDto) {
		List<AdOverviewDto> adOverviewDtos = new ArrayList<>();
		for(Ad ad : adRepository.findByCategoryAndFilters(categoryName, adFilterDto.getMinPrice(), adFilterDto.getMaxPrice())) {
			adOverviewDtos.add(new AdOverviewDto(ad.getId() ,ad.getName(),ad.getLocation().getCity() + ", " + ad.getLocation().getAddress(), ad.getPrice().doubleValue()));
		}
		return adOverviewDtos;
	}

	@Override
	public AdOverviewDto findOverviewById(long id) {
		Ad ad = adRepository.findById(id).get();
		
		AdOverviewDto adOverviewDto = new AdOverviewDto(id, ad.getName(), ad.getLocation().getCity(), ad.getPrice().doubleValue());
		return adOverviewDto;
	}

}
