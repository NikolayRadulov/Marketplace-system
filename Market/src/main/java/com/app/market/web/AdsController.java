package com.app.market.web;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.market.model.dto.ImportAdDto;
import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.entity.Ad;
import com.app.market.model.entity.User;
import com.app.market.service.AdService;
import com.app.market.service.CategoryService;
import com.app.market.service.FileService;
import com.app.market.service.UserService;

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
		
		return "addAd.html";
	}

	@PostMapping("/addAd")
	public String redirectToHome(@AuthenticationPrincipal UserDetails userDetails, ImportAdDto importAdDto) throws IOException {
		
		Ad currentAd = adService.addNewAdd(userDetails.getUsername(), importAdDto);
		
		fileService.saveFile(new UploadFileDto(importAdDto.getImage1()), currentAd);
		fileService.saveFile(new UploadFileDto(importAdDto.getImage2()), currentAd);
		fileService.saveFile(new UploadFileDto(importAdDto.getImage3()), currentAd);
		fileService.saveFile(new UploadFileDto(importAdDto.getImage4()), currentAd);
		fileService.saveFile(new UploadFileDto(importAdDto.getImage5()), currentAd);
		
		
		return "redirect:/";
	}
}
