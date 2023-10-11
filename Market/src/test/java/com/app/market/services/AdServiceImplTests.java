package com.app.market.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.app.market.model.dto.AdFilterDto;
import com.app.market.model.dto.AdOverviewDto;
import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.entity.Ad;
import com.app.market.repository.AdRepository;
import com.app.market.repository.CategoryRepository;
import com.app.market.repository.FileRepository;
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
	@Mock
	private FileRepository fileRepository;
	
	private AdServiceImpl toTest;
	
	@BeforeEach
	void setUp() {
		toTest = new AdServiceImpl(adRepository, gson, modelMapper, userRepository, categoryRepository, locationRepository, fileRepository);
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
	
	@Test
	public void testAddNewAd() {
		//Arrange
		ImportAdDto importAdDto = Mockito.mock(ImportAdDto.class);

		//Act
		toTest.addNewAdd(anyString(), importAdDto);
		
		//Assert
		Mockito.verify(adRepository).save(any());
		Mockito.verify(locationRepository).save(any());
		Mockito.verify(categoryRepository).getByName(any());
		
	}
	
	@Test
	public void testGetByCategoryNameAndFilters() {
		//Arrange
		AdFilterDto adFilterDto = Mockito.mock(AdFilterDto.class);
		List<AdOverviewDto> adOverviewDtos = new ArrayList<>();
		
		Mockito.when(toTest.getByCategoryNameAndFilters("dsc", adFilterDto)).thenReturn(adOverviewDtos);
		//Act
		List<AdOverviewDto> dtos = toTest.getByCategoryNameAndFilters("dsc", adFilterDto);
		
		//Assert
		Mockito.verify(adRepository).findByCategoryAndFilters(anyString(),anyDouble(), anyDouble());
		
		Assertions.assertEquals(adOverviewDtos, dtos);
	}
	
	@Test
	public void testGetSize() {
		//Arrange
		
		//Act
		toTest.getAdsCount();
		
		//Assert
		Mockito.verify(adRepository).findAll();
	}
	
	@Test
	public void testDeleteAd() {
		//Arrange
		Mockito.when(adRepository.findById(anyLong())).thenReturn(Optional.of(new Ad()));
		//Act
		toTest.deleteAd(anyLong());
		
		//Assert
		Mockito.verify(fileRepository).delete(any());
		Mockito.verify(adRepository).deleteById(anyLong());
	}
}
