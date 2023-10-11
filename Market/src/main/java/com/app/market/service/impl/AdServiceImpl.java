package com.app.market.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.app.market.repository.FileRepository;
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
	private final FileRepository fileRepository;
	
	public AdServiceImpl(AdRepository adRepository, Gson gson, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, LocationRepository locationRepository, FileRepository fileRepository) {
		this.adRepository = adRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
		this.gson = gson;
		this.modelMapper = modelMapper;
		this.fileRepository = fileRepository;
	}

	@Override
	public List<AdOverviewDto> getByCategoryName(String categoryName) {
		return getDtos(adRepository.findByCategory(categoryName));
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
		ad.setPostedOn(LocalDateTime.now());
		
		return adRepository.save(ad);
	}

	@Override
	public Ad findById(long id) {
		return adRepository.findById(id).get();
	}

	@Override
	public List<AdOverviewDto> getByCategoryNameAndFilters(String categoryName, AdFilterDto adFilterDto) {
		return getDtos(adRepository.findByCategoryAndFilters(categoryName, adFilterDto.getMinPrice(), adFilterDto.getMaxPrice()));
	}

	@Override
	public long getOwnerId(long adId) {
		return this.adRepository
				.findById(adId)
				.get()
				.getOwner()
				.getId();
	}
	
	@Override
	public AdOverviewDto findOverviewById(long id) {
		Ad ad = adRepository.findById(id).get();
		
		AdOverviewDto adOverviewDto = new AdOverviewDto(id, ad.getName(), ad.getLocation().getCity(), ad.getPrice().doubleValue(),ad.getDescription());
		adOverviewDto.setDate(ad.getPostedOn());
		return adOverviewDto;
	}
	
	@Override
	public List<AdOverviewDto> findByUser(long ownerId) {
		// TODO Auto-generated method stub
		return getDtos(adRepository.findByOwner(userRepository.findById(ownerId).get()));
	}
	
	
	private List<AdOverviewDto> getDtos(List<Ad> ads) {
		List<AdOverviewDto> adOverviewDtos = new ArrayList<>();
		
		for(Ad ad :ads ) {
			adOverviewDtos.add(new AdOverviewDto(ad.getId() ,ad.getName(),ad.getLocation().getCity() + ", " + ad.getLocation().getAddress(), ad.getPrice().doubleValue(), ad.getDescription()));
		}
		
		return adOverviewDtos;
	}

	@Override
	public int getAdsCount() {
		return adRepository.findAll().size();
	}

	@Override
	public void deleteAd(long id) {
		Ad ad = adRepository.findById(id).get();
		this.fileRepository.delete(ad.getImage());
		this.adRepository.deleteById(id);
	}

	

}
