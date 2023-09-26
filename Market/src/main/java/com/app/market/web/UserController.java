package com.app.market.web;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.market.model.dto.ImportRatingDto;
import com.app.market.model.dto.ImportReportDto;
import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.dto.UserProfileOverviewDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.service.AdService;
import com.app.market.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final AdService adService;
	
	public UserController(UserService userService, AdService adService) {
		this.userService = userService;
		this.adService = adService;
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}
	
	@PostMapping("/login-error")
	public String getLoginPage(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("invalidCredentials", true);
		return "redirect:/users/login";
	}
	
	@PostMapping("/addProfilePicture/{id}")
	public String setProfilePicture(@PathVariable("id")long id, UploadFileDto uploadFileDto) {
		try {
			userService.setProfilePicture(id, uploadFileDto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/users/profile/"+id;
	}

	
	@GetMapping("/loadUsers")
	public String redirectHomeFromLoading() {
		userService.registerInitialUsers();
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		if(!model.containsAttribute("userRegisterDto")) model.addAttribute("userRegisterDto", new UserRegisterDto());
		return "register.html";
	}
	
	@PostMapping("/register")
	public String getHomePage(Model model, @Valid @ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto, BindingResult bindingResult, HttpSession httpSession) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("userRegisterDto", userRegisterDto);
			model.addAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
			return "register.html";
		}
		userService.registerUser(userRegisterDto);
		httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		
		return "redirect:/";
	}
	
	@PostMapping("changeRole/{id}")
	public String changeUserRole(@PathVariable("id") long userId, @RequestParam("authority")String authority) {
		userService.changeUserAuthority(userId, authority);
		
		return "redirect:/users/profile/"+userId;
	}
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin.html";
	}
	
	@GetMapping("profile/{id}")
	public String getOverviewPage(Model model, @PathVariable("id")long id, @AuthenticationPrincipal UserDetails userDetails) {
		UserProfileOverviewDto userProfileOverviewDto = userService.getProfileOverviewById(id);
		boolean isProfileOwned = userDetails.getUsername().equals(userProfileOverviewDto.getUsername());

		model.addAttribute("user", userProfileOverviewDto);
		model.addAttribute("isUserAdmin", userProfileOverviewDto.getRolesCount() != 2 || isProfileOwned);
		model.addAttribute("isProfileOwned", isProfileOwned);
		model.addAttribute("profileAuthority", userService.getUserAuthority(userProfileOverviewDto.getRolesCount()));
		System.out.println(userProfileOverviewDto.getRating());
		model.addAttribute("userStarRating", userProfileOverviewDto.getRating());
		
		model.addAttribute("ads", adService.findByUser(id));
		model.addAttribute("report", new ImportReportDto());
		model.addAttribute("rating", new ImportRatingDto());
		
		return "profileOverview.html";
	}
	
	@PostMapping("/profileSearch")
	public String redirectToProfile(@RequestParam("profileName")String username) {
		return "redirect:/users/profile/" + userService.getByName(username).getId();
	}
	

}
