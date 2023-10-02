package com.app.market.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.market.model.entity.Ad;
import com.app.market.model.entity.FileEntity;
import com.app.market.service.AdService;

@RequestMapping("/ads")
@RestController
public class AdsRestController {

	private final AdService adService;
	public AdsRestController(AdService adService) {
		this.adService = adService;
	}
	
	
	//@GetMapping("/ads_by_category/{categoryName}")
/*	public ResponseEntity<String> getAdsByCategory(@PathVariable("categoryName")String categoryName) {
		
		List<Ad> ads = adService.getByCategoryName(categoryName);
		
		return new ResponseEntity<String>(adService.exportAds(ads), HttpStatus.OK);
	}
	*/
	@GetMapping("/getImages/{adId}")
	public HttpEntity<byte[]> getAdImages(@PathVariable("adId") long id) {
		Ad ad = adService.findById(id);
		
		FileEntity image = ad.getImage();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(MimeTypeUtils.parseMimeType(image.getContentType())));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "atachment; filename=" + image.getFileName());
		headers.setContentLength(image.getBytes().length);
		
		return new HttpEntity<>(image.getBytes(), headers);
	}
	
	@GetMapping("/getAllAdsCount")
	public HttpEntity<Integer> getAdsCount() {
		return new HttpEntity<Integer>(adService.getAdsCount());
	}

}
