package com.app.market.web;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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
		model.addAttribute("contactUserDto", userService.getById(user.getId()));
		
		if(!model.containsAttribute("importAdDto"))model.addAttribute("importAdDto", new ImportAdDto());
		
		return "addAd";
	}

	@PostMapping("/search")
	public String searchAds(Model model, @RequestParam("text")String text) {
		model.addAttribute("ads", adService.findByTitleContaining(text));
		model.addAttribute("filters", false);
		return "adsOverview";
	}
	
	@PostMapping("/addAd")
	public String redirectToHome(Model model, @AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("importAdDto") ImportAdDto importAdDto, BindingResult bindingResult) throws IOException {
		
		if(importAdDto.getImage() != null && importAdDto.getImage().getSize() > 1048576) {
			return "redirect:/error";
		}
		
		if(bindingResult.hasErrors()) {
			User user = userService.getByName(userDetails.getUsername());
			model.addAttribute("categories", categoryService.getAllCategories());
			model.addAttribute("importAdDto", importAdDto);
			model.addAttribute("contactUserDto", userService.getById(user.getId()));
			return "addAd";
		}
		
		
		Ad currentAd = adService.addNewAdd(userDetails.getUsername(), importAdDto);
		
		fileService.saveFile(new UploadFileDto(importAdDto.getImage()), currentAd);	
		
		return "redirect:/";
	}
	
	@DeleteMapping("/deleteAd/{id}")
	public String deleteAd(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id")long id) {
		User deleter = userService.getByName(userDetails.getUsername());
		if(!adService.findById(id).getOwner().getUsername().equals(userDetails.getUsername()) && deleter.getRoles().size() < 2) {
			return "redirect:/ads/forbidden";
		}
		
		adService.deleteAd(id);
		
		return "redirect:/";
	}
	
	@GetMapping("/forbidden")
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public String getForbidenPage() {
		return "/errors/notAuthorized.html";
	}
	
	@GetMapping("/getAdInfo/{id}") 
	public String getAdOverviewPage(Model model,@AuthenticationPrincipal UserDetails userDetails ,@PathVariable("id")long id) {
		boolean isProfileOwned = userDetails.getUsername().equals(adService.findById(id).getOwner().getUsername());

		model.addAttribute("ad", adService.findOverviewById(id));
		model.addAttribute("user", userService.getById(adService.getOwnerId(id)));
		model.addAttribute("isProfileOwned", isProfileOwned);
		model.addAttribute("isDeleteAuthorized", isProfileOwned || userService.getByName(userDetails.getUsername()).getRoles().size() == 2);
		return "adInfoPage";
	}
	
	@GetMapping("/ads_by_category/{categoryName}")
	public String getOverviewPage(Model model, @PathVariable("categoryName") String categoryName, AdFilterDto adFilterDto) {
		model.addAttribute("ads", adService.getByCategoryName(categoryName));
		model.addAttribute("categoryName", categoryName); 
		model.addAttribute("filters", true);
		
		return "adsOverview";	
	}
	
	@PostMapping("/ads_by_category/{categoryName}")
	public String getOverviewPageWithFilters(Model model, @PathVariable("categoryName") String categoryName, AdFilterDto adFilterDto) {
		model.addAttribute("ads", adService.getByCategoryNameAndFilters(categoryName, adFilterDto));
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("filters", true);
		
		return "adsOverview";	
	}
	
	
	
}
