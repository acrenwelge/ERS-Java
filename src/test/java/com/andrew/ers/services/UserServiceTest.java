package com.andrew.ers.services;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.andrew.ers.exceptions.ResourceNotFoundException;
import com.andrew.ers.exceptions.UserAlreadyExistsException;
import com.andrew.ers.model.AppUser;
import com.andrew.ers.repositories.UserRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class UserServiceTest {
	
	@Mock
	public UserRepo mockUserRepo;
	
	@InjectMocks
	public UserService userService;
	
	@Test(expected=ResourceNotFoundException.class)
	public void testUserNotFound() {
		given(mockUserRepo.findById(1L)).willReturn(Optional.ofNullable(null));
		userService.deactivateUser(1L);
	}
	
	@Test(expected=UserAlreadyExistsException.class)
	public void testRegistrationNegative() {
		given(mockUserRepo.exists(Mockito.any())).willReturn(true);
		AppUser u = new AppUser();
		u.setUsername("acrenwelge");
		userService.registerNewUser(u);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testFindByUsernameNegative() {
		given(mockUserRepo.findByUsername(Mockito.anyString())).willReturn(null);
		userService.loadUserByUsername("acrenwelge");
	}
}
