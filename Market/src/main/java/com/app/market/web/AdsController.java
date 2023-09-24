package com.app.market.web;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.market.model.dto.AdFilterDto;
import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.entity.Ad;
import com.app.market.model.entity.User;
import com.app.market.service.AdService;
import com.app.market.service.CategoryService;
import com.app.market.service.FileService;
import com.app.market.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ads")
public class AdsController {

	private final CategoryService categoryService;
	private final AdService adService;
	private final FileService fileService;
	private final UserService userService;
	
	public AdsController(CategoryService categoryService, FileService fileService, AdService adService, UserService userService) {
		this.categoryService = categoryService;
		this.adService = adService;
		this.fileService = fileService;
		this.userService = userService;
	}
	
	@GetMapping("/addAd")
	public String getPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.getByName(userDetails.getUsername());
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("userContactDto", user);
		
		if(!model.containsAttribute("importAdDto"))model.addAttribute("importAdDto", new ImportAdDto());
		
		return "addAd.html";
	}

	@PostMapping("/addAd")
	public String redirectToHome(Model model, @AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("importAdDto") ImportAdDto importAdDto, BindingResult bindingResult) throws IOException {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("importAdDto", importAdDto);
			return "addAd.html";
		}
		
		Ad currentAd = adService.addNewAdd(userDetails.getUsername(), importAdDto);
		
		fileService.saveFile(new UploadFileDto(importAdDto.getImage()), currentAd);	
		
		return "redirect:/";
	}
	
	@GetMapping("/getAdInfo/{id}") 
	public String getAdOverviewPage(Model model, @PathVariable("id")long id) {
		model.addAttribute("ad", adService.findOverviewById(id));
		model.addAttribute("user", userService.getById(adService.getOwnerId(id)));
		return "adInfoPage.html";
	}
	
	@GetMapping("/ads_by_category/{categoryName}")
	public String getOverviewPage(Model model, @PathVariable("categoryName") String categoryName, AdFilterDto adFilterDto) {
		if(adFilterDto.getMaxPrice() == 0 || adFilterDto.getMinPrice() == 0)model.addAttribute("ads", adService.getByCategoryName(categoryName));
		else model.addAttribute("ads", adService.getByCategoryNameAndFilters(categoryName, adFilterDto));
		model.addAttribute("categoryName", categoryName);
		
		return "adsOverview.html"; 
	}
	
	
	
}
