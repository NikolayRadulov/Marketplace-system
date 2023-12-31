package com.app.market.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.dto.UserContactDto;
import com.app.market.model.dto.UserProfileOverviewDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.FileEntity;
import com.app.market.model.entity.Rating;
import com.app.market.model.entity.User;
import com.app.market.model.entity.UserRole;
import com.app.market.model.enums.UserRoleEnum;
import com.app.market.repository.FileRepository;
import com.app.market.repository.RatingRepository;
import com.app.market.repository.UserRepository;
import com.app.market.repository.UserRoleRepository;
import com.app.market.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private final UserRoleRepository userRoleRepository;
	private final FileRepository fileRepository;
	private final RatingRepository ratingRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, UserDetailsService userDetailsService, FileRepository fileRepository, RatingRepository ratingRepository) {
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		this.userRoleRepository = userRoleRepository;
		this.fileRepository = fileRepository;
		this.ratingRepository = ratingRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUser(UserRegisterDto userRegisterDto) {
		
		User user = modelMapper.map(userRegisterDto, User.class);
		
		if(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) throw new IllegalArgumentException("User already exists!");
		user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
		user.setCreatedOn(LocalDateTime.now());

		userRepository.save(user);
	}
	
	@Override
	public void registerInitialUsers() {
		if(userRepository.count() != 0) return;
		
		UserRole adminRole;
		UserRole moderatorRole;
		if(userRoleRepository.count() == 0) {
			adminRole = new UserRole(UserRoleEnum.ADMIN);
			moderatorRole = new UserRole(UserRoleEnum.MODERATOR);
			
			userRoleRepository.save(adminRole);
			userRoleRepository.save(moderatorRole);
		}
		else {
			adminRole = userRoleRepository.findByUserRole(UserRoleEnum.ADMIN);
			moderatorRole = userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR);
		}
		
		User adminUser = new User("admin", "admin@gmail.com", "0984589332", passwordEncoder.encode("adminPassword"));
		adminUser.addRole(adminRole);
		adminUser.addRole(moderatorRole);;
		userRepository.save(adminUser);
		
		User moderatorUser = new User("moderator", "moderator@gmail.com", "0984583421", passwordEncoder.encode("moderatorPassword"));
		moderatorUser.addRole(moderatorRole);
		userRepository.save(moderatorUser);
	}

	
	@Override
	public void loginUser(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	public User getByName(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public UserContactDto getById(long id) {
		User user = userRepository.findById(id).get();
		
		return new UserContactDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhoneNumber());
	}

	@Override
	public UserProfileOverviewDto getProfileOverviewById(long id) {
		User user = userRepository.findById(id).get();
		List<Rating> ratings = ratingRepository.findByRatedUser(user);
		
		double rating = 0;
		for (Rating currRating : ratings) {
			rating += currRating.getScore();
		}
		rating /= (double)ratings.size();
		
		UserProfileOverviewDto dto = modelMapper.map(user, UserProfileOverviewDto.class);
		dto.setRating((int)Math.round(rating));
		dto.setRolesCount(user.getRoles().size());
		return dto;
	}

	@Override
	public void setProfilePicture(long userId, UploadFileDto uploadFileDto) throws IOException {
		User profile = userRepository.findById(userId).get();
		
		FileEntity fileEntity = new FileEntity(uploadFileDto.getImg().getContentType(), 
											   uploadFileDto.getImg().getOriginalFilename(),
											   uploadFileDto.getImg().getBytes());
		fileRepository.save(fileEntity);
		profile.setProfilePicture(fileEntity);
		userRepository.save(profile);
	}

	@Override
	public void changeUserAuthority(long userId, String authority) {
		User user = userRepository.findById(userId).get();
		
		switch (authority) {
			case "USER": {
				user.setRoles(new ArrayList<>());
			}
			break;
			case "MODERATOR": {
				ArrayList<UserRole> roles = new ArrayList<>();
				roles.add(userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR));
				user.setRoles(roles);
			}
			break;
			case "ADMIN": {
				ArrayList<UserRole> roles = new ArrayList<>();
				roles.add(userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR));
				roles.add(userRoleRepository.findByUserRole(UserRoleEnum.ADMIN));
				user.setRoles(roles);
			}
			break;
			default: throw new IllegalArgumentException("Unexpected value: " + authority);
		}
		
		userRepository.save(user);
	}

	@Override
	public String getUserAuthority(int size) {
		if(size == 2) return "Admin";
		if(size == 1) return "Moderator";
		
		return "User";
	}

	@Override
	public int getAllUsers() {
		return userRepository.findAll().size();
	}

	@Override
	public int getUserCountByRole(UserRoleEnum userRoleEnum) {
		int count = 0;
		for(User user : userRepository.findAll()) {
			if(user.getRoles().size() == 1) count++;
		}
		return count;
	}

	@Override
	public List<String> getModeratorsUsernames() {
		return this.userRepository.findAll()
				.stream()
				.filter(user -> user.getRoles().size() == 1)
				.map(user -> user.getUsername())
				.collect(Collectors.toList());
	}

}
