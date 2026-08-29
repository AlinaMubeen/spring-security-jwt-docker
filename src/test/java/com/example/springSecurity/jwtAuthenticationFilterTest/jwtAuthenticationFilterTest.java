package com.example.springSecurity.jwtAuthenticationFilterTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.springSecurity.jwtAuthenticationFilter;
import com.example.springSecurity.Service.MyUserDetailService;
import com.example.springSecurity.Service.jwtService;

import jakarta.servlet.http.HttpServletRequest;
@ExtendWith(MockitoExtension.class)
public class jwtAuthenticationFilterTest {
	@Mock
	UserDetails userDetails;
	 @Mock
	 jwtService jwtser;
	 @Mock
		MyUserDetailService userDetailsService;
	 @Mock
	 HttpServletRequest request;
	 @InjectMocks
	 jwtAuthenticationFilter filter;
	 @Test
	 void isTheTokenGeneratedContainsHeader(){
		 //arrange
		String authHeader="Bearer 12334225";
		when(request.getHeader("Authorization")).thenReturn("Bearer 12334225");
		//act
		String header=request.getHeader("Authorization");
		String token=header.substring(7);
		 //assertion
		assertEquals("12334225",token);
		
				
	 }
	 
	 @Test
	 void validJetAuthenticateTheUser() {
		 //arrange
		 UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,
					userDetails.getAuthorities());
		 
		 // Act
		    SecurityContextHolder.getContext().setAuthentication(authentication);

		    // Assert
		    assertEquals(
		    		authentication,SecurityContextHolder.getContext().getAuthentication()
		    );

		
	 }
		

}
