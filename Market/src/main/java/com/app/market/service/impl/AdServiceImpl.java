package com.app.market.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.ExportAdDto;
import com.app.market.model.entity.Ad;
import com.app.market.repository.AdRepository;
import com.app.market.service.AdService;
import com.google.gson.Gson;

@Service
public class AdServiceImpl implements AdService {

	private final AdRepository adRepository;
	private final Gson gson;
	private final ModelMapper modelMapper;
	
	public AdServiceImpl(AdRepository adRepository, Gson gson, ModelMapper modelMapper) {
		this.adRepository = adRepository;
		this.gson = gson;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<Ad> getByCategoryName(String categoryName) {
		return adRepository.findByCategory(categoryName);
	}

	@Override
	public String exportAds(List<Ad> ads) {
		List<ExportAdDto> exportAdDtos = ads.stream().map(dto -> modelMapper.map(dto, ExportAdDto.class)).collect(Collectors.toList());
		return gson.toJson(exportAdDtos);
	}

}
