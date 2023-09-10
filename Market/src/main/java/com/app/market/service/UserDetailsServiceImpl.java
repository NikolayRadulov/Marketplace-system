package com.app.market.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.market.model.entity.UserRole;
import com.app.market.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return getDatails(this.userRepository.findByUsername(username));
	}

	private UserDetails getDatails(com.app.market.model.entity.User userEntity) {
		
		return new User(userEntity.getUsername(), userEntity.getPassword(), getAuthorities(userEntity.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<UserRole> roles) {
		return roles.stream().map(this::getAuthority).collect(Collectors.toList());
	}
	
	public GrantedAuthority getAuthority(UserRole role) {
		return new SimpleGrantedAuthority(role.getUserRole().name());
		
	} 
}
