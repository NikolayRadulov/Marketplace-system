package com.app.market.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.market.model.entity.Ad;
import com.app.market.service.AdService;

@RequestMapping("/ads")
@RestController
public class AdsRestController {

	private final AdService adService;
	public AdsRestController(AdService adService) {
		this.adService = adService;
	}
	
	
	@GetMapping("/ads_by_category/{categoryName}")
	public ResponseEntity<String> getAdsByCategory(@PathVariable("categoryName")String categoryName) {
		
		List<Ad> ads = adService.getByCategoryName(categoryName);
		
		return new ResponseEntity<String>(adService.exportAds(ads), HttpStatus.OK);
	}

}
