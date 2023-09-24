package com.app.market.service.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.market.model.dto.UserContactDto;
import com.app.market.model.dto.UserProfileOverviewDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.User;
import com.app.market.model.entity.UserRole;
import com.app.market.model.enums.UserRoleEnum;
import com.app.market.repository.UserRepository;
import com.app.market.repository.UserRoleRepository;
import com.app.market.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private final UserRoleRepository userRoleRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, UserDetailsService userDetailsService) {
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		this.userRoleRepository = userRoleRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUser(UserRegisterDto userRegisterDto) {
		User user = modelMapper.map(userRegisterDto, User.class);
		user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
		user.setCreatedOn(LocalDateTime.now());

		userRepository.save(user);
		
		loginUser(user.getUsername());
	}
	
	

	@Override
	public void registerInitialUsers() {
		if(userRepository.count() != 0) return;
		
		UserRole adminRole = new UserRole(UserRoleEnum.ADMIN);
		UserRole moderatorRole = new UserRole(UserRoleEnum.MODERATOR);
		
		userRoleRepository.save(adminRole);
		userRoleRepository.save(moderatorRole);
		
		User adminUser = new User("admin", "admin@gmail.com", "0984589332", passwordEncoder.encode("adminPassword"));
		adminUser.getRoles().add(adminRole);
		adminUser.getRoles().add(moderatorRole);
		adminUser.getRoles().forEach(System.out::println);
		userRepository.save(adminUser);
		
		User normalUser = new User("normal", "normal@gmail.com", "0984589392", passwordEncoder.encode("normalPassword"));
		userRepository.save(normalUser);	
	}

	
	@Override
	public void loginUser(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	public User getByName(String name) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(name);
	}

	@Override
	public UserContactDto getById(long id) {
		User user = userRepository.findById(id).get();
		
		return new UserContactDto(user.getId(), user.getEmail(), user.getPhoneNumber());
	}

	@Override
	public UserProfileOverviewDto getProfileOverviewById(long id) {
		User user = userRepository.findById(id).get();
		UserProfileOverviewDto dto = modelMapper.map(user, UserProfileOverviewDto.class);
		return dto;
	}

}
