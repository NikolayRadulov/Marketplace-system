package com.app.market.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.market.model.entity.FileEntity;
import com.app.market.service.UserService;

@RequestMapping("/users")
@RestController
public class UsersRestController {

	private final UserService userService;
	
	public UsersRestController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("/getProfilePicture/{profileName}")
	public HttpEntity<byte[]> getAdImages(@PathVariable("profileName") String profileName) {
		
		FileEntity image = userService.getByName(profileName).getProfilePicture();
		
		if(image == null) return null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(MimeTypeUtils.parseMimeType(image.getContentType())));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "atachment; filename=" + image.getFileName());
		headers.setContentLength(image.getBytes().length);
		
		return new HttpEntity<>(image.getBytes(), headers);
	}

}
