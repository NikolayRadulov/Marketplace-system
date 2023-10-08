package com.app.market.services;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.app.market.model.dto.AdOverviewDto;
import com.app.market.repository.AdRepository;
import com.app.market.repository.CategoryRepository;
import com.app.market.repository.LocationRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.impl.AdServiceImpl;
import com.google.gson.Gson;


@ExtendWith(MockitoExtension.class)
public class AdServiceImplTests {

	@Mock
	private AdRepository adRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private CategoryRepository categoryRepository;
	@Mock
	private LocationRepository locationRepository;
	@Mock
	private Gson gson;
	@Mock
	private ModelMapper modelMapper;
	
	private AdServiceImpl toTest;
	
	@BeforeEach
	void setUp() {
		toTest = new AdServiceImpl(adRepository, gson, modelMapper, userRepository, categoryRepository, locationRepository);
	}
	
	@Test
	public void getByCategoryNameTest() {
		//Arrange
		List<AdOverviewDto> ads = new ArrayList<>();
		Mockito.when(toTest.getByCategoryName("electronics")).thenReturn(ads);
		
		//Act
		List<AdOverviewDto> list = toTest.getByCategoryName("electronics");
		
		//Assert
		Assertions.assertEquals(ads, list);
	}
	
	@Test
	public void exportAdsTest() {
		//Arrange
		
		//Act
		toTest.exportAds(anyList());
		
		//Assert
		Mockito.verify(gson).toJson(anyCollection());
	}
	
	

}
