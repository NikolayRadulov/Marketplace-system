package com.app.market.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.app.market.model.dto.UploadFileDto;
import com.app.market.model.dto.UserProfileOverviewDto;
import com.app.market.model.dto.UserRegisterDto;
import com.app.market.model.entity.User;
import com.app.market.model.entity.UserRole;
import com.app.market.repository.FileRepository;
import com.app.market.repository.RatingRepository;
import com.app.market.repository.UserRepository;
import com.app.market.repository.UserRoleRepository;
import com.app.market.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserServiceTests {
	
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private UserRoleRepository userRoleRepository;
	@Mock
	private FileRepository fileRepository;
	@Mock
	private RatingRepository ratingRepository;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private UserServiceImpl toTest;
	
	@BeforeEach
	public void setUp() {
		toTest = new UserServiceImpl(userRepository, modelMapper, passwordEncoder, userRoleRepository, 
				userDetailsService, fileRepository, ratingRepository);
	}
	
	@Test
	public void testRegisterUser() {
		//Arrange
		User mockUser = Mockito.mock(User.class);
		mockUser.setUsername(USERNAME);
		UserRegisterDto userRegisterDto = Mockito.mock(UserRegisterDto.class);
		
		Mockito.when(modelMapper.map(userRegisterDto, User.class)).thenReturn(mockUser);
		Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
		Mockito.when(userRegisterDto.getPassword()).thenReturn(PASSWORD);
		
		//Act
		toTest.registerUser(userRegisterDto);
		
		//Assert
		Mockito.verify(modelMapper).map(userRegisterDto, User.class);
		Mockito.verify(passwordEncoder).encode(PASSWORD);
		Mockito.verify(mockUser).setCreatedOn(any());
		Mockito.verify(userRepository).save(any());
	}
	
	@Test
	public void testRegisterInitialUsers() {
		//Arrange
		
		//Act
		toTest.registerInitialUsers();
		
		//Assert
		Mockito.verify(userRepository, times(2)).save(any());
		Mockito.verify(userRoleRepository, times(2)).save(any());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetProfileOverviewById() {
		//Arrange
		User mockUser = Mockito.mock(User.class);
		UserProfileOverviewDto mockUserProfileOverviewDto = Mockito.mock(UserProfileOverviewDto.class);
		mockUserProfileOverviewDto.setRating(0);
		mockUserProfileOverviewDto.setRolesCount(0);
		
		List<UserRole> roles = Mockito.mock(ArrayList.class);
		
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(mockUser));
		Mockito.when(ratingRepository.findByRatedUser(mockUser)).thenReturn(new ArrayList<>());
		Mockito.when(mockUser.getRoles()).thenReturn(roles);
		Mockito.when(modelMapper.map(mockUser, UserProfileOverviewDto.class)).thenReturn(mockUserProfileOverviewDto);
		
		
		
		//Act
		UserProfileOverviewDto actual = toTest.getProfileOverviewById(1);
		
		//Assert
		Mockito.verify(modelMapper).map(mockUser, UserProfileOverviewDto.class);
		Mockito.verify(mockUserProfileOverviewDto, times(2)).setRating(anyInt());
		Mockito.verify(mockUserProfileOverviewDto, times(2)).setRolesCount(anyInt());
		
		Assertions.assertEquals(mockUserProfileOverviewDto, actual);
	}
	
	@Test
	public void testSetProfilePicture() {
		//Arrange
		User mockUser = Mockito.mock(User.class);
		UploadFileDto uploadFileDto = Mockito.mock(UploadFileDto.class);
		
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(mockUser));
		Mockito.when(uploadFileDto.getImg()).thenReturn(Mockito.mock(MultipartFile.class));
		
		//Act
		try {
			toTest.setProfilePicture(1, uploadFileDto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		Mockito.verify(fileRepository).save(any());
		Mockito.verify(mockUser).setProfilePicture(any());
		Mockito.verify(userRepository).save(any());
	}
	
	@Test
	public void testChangeUserRoleToUser() {
		//Arrange
		User mockUser = Mockito.mock(User.class);
		
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(mockUser));
		
		//Act
		toTest.changeUserAuthority(1, "USER");
		
		//Assert
		Mockito.verify(userRepository).save(any());
		Mockito.verify(mockUser).setRoles(anyList());
	}
	
}
