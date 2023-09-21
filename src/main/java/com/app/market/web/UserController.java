package com.app.market.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.market.model.dto.UserRegisterDto;
import com.app.market.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
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
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin.html";
	}
	

}
