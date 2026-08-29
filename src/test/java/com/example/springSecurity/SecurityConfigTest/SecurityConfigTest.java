package com.example.springSecurity.SecurityConfigTest;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springSecurity.Password;
import com.example.springSecurity.SecurityConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.springSecurity.jwtAuthenticationFilter;
import com.example.springSecurity.Service.MyUserDetailService;
@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

	@Mock
	jwtAuthenticationFilter jwtAuthenticationFilter;
	@Mock
	Password password;
	@Mock
	MyUserDetailService myUserDetailService;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	AuthenticationManager authenticationManager;
	@InjectMocks
	SecurityConfig config;
	@Test
	void isAuthenticationProviderProvidesSecurity() {
		//arrange
		when(password.passwordEncoder()).thenReturn(passwordEncoder);
		//act
		AuthenticationProvider provider=config.authenticationProvider(myUserDetailService);
		//assertion
		assertNotNull(provider);
	}
	
	
}
