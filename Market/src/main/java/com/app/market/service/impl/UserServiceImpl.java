package com.app.market.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.market.model.entity.User;
import com.app.market.model.entity.UserRole;
import com.app.market.model.enums.UserRoleEnum;
import com.app.market.repository.UserRepository;
import com.app.market.repository.UserRoleRepository;
import com.app.market.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUser() {
		// TODO Auto-generated method stub

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
}
