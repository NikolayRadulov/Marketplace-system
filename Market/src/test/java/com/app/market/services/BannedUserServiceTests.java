package com.app.market.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.app.market.model.entity.BannedUser;
import com.app.market.model.entity.User;
import com.app.market.repository.BannedUserRepository;
import com.app.market.repository.UserRepository;
import com.app.market.service.impl.BannedUserServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BannedUserServiceTests {

	@Mock
	private UserRepository userRepository;
	@Mock
	private BannedUserRepository bannedUserRepository;
	
	private BannedUserServiceImpl toTest;
	
	@BeforeEach
	public void setUp() {
		toTest = new BannedUserServiceImpl(bannedUserRepository, userRepository);
	}
	
	@Test
	public void testFindByUsername() {
		//Arrange
		BannedUser testUser = new BannedUser();
		testUser.setUsername("somename");
		Mockito.when(bannedUserRepository.findByUsername("somename")).thenReturn(testUser);
		
		//Act
		BannedUser actual = toTest.findByUsername("somename");
		
		//Assert
		
		Mockito.verify(bannedUserRepository).findByUsername(anyString());
		
		Assertions.assertEquals(testUser, actual);
	}
	
	@Test
	public void testExpireBan() {
		//Arrange
		
		//Act
		toTest.expireBan(anyLong());
		
		//Assert
		
		Mockito.verify(bannedUserRepository).deleteById(anyLong());
		
	}
	
	@Test
	public void testBanUser() {
		//Arrange
		Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
		//Act
		toTest.banUser(1, 1);
		
		//Assert
		
		Mockito.verify(bannedUserRepository).save(any());
		
	}
	
	@Test
	public void clearExpiredBans() {
		//Arrange
		Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
		//Act
		toTest.clearExpiredBans();;
		
		//Assert
		
		Mockito.verify(bannedUserRepository).deleteAll(anyList());
		
	}
	
	@Test
	public void testCheckBannedUser() {
		//Arrange
		String existingName = "exist";
		String existingName2 = "exist2";
		String nonExisting = "missing";
		
		BannedUser testBannedUser = new BannedUser();
		testBannedUser.setUsername(existingName);
		testBannedUser.setBanExpire(LocalDateTime.now().plusDays(1));
		
		BannedUser testBannedUser2 = new BannedUser();
		testBannedUser2.setUsername(existingName2);
		testBannedUser2.setBanExpire(LocalDateTime.now());
		
		Mockito.when(bannedUserRepository.findByUsername(existingName)).thenReturn(testBannedUser);
		Mockito.when(bannedUserRepository.findByUsername(existingName2)).thenReturn(testBannedUser2);
		Mockito.when(bannedUserRepository.findByUsername(nonExisting)).thenReturn(null);
		
		//Act
		BannedUser bannedUser1 = toTest.checkBannedUser(existingName);
		BannedUser bannedUser2 = toTest.checkBannedUser(nonExisting);
		BannedUser bannedUser3 = toTest.checkBannedUser(existingName2);
		//Assert
		
		Assertions.assertEquals(bannedUser1, testBannedUser);
		Assertions.assertEquals(bannedUser2, null);
		Assertions.assertEquals(bannedUser3, null);
	}
}
