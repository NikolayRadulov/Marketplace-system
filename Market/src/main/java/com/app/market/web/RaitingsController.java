package com.app.market.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.market.model.dto.ImportRatingDto;
import com.app.market.service.RatingService;

import jakarta.validation.Valid;

@RequestMapping("/ratings")
@Controller
public class RaitingsController {

	private final RatingService ratingService;
	
	public RaitingsController(RatingService ratingService) {
		this.ratingService = ratingService;
	}
	
	
	@PostMapping("/addRating/{id}")
	public String addRating(@AuthenticationPrincipal UserDetails userDetails ,@PathVariable("id")long id, @Valid ImportRatingDto importRatingDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("importRatingDto", importRatingDto);
			return "redirect:/users/profile/"+id;
		}
		ratingService.addNewUserRaiting(id, importRatingDto, userDetails);
		
		return "redirect:/users/profile/"+id; 
	}

}
