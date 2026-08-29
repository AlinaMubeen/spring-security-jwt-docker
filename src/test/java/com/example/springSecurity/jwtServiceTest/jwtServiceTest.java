package com.example.springSecurity.jwtServiceTest;

import javax.crypto.SecretKey;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.springSecurity.Service.jwtService;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
public class jwtServiceTest {
	jwtService serv=new jwtService();
	@Test
	void isKeyGenerated() {
	//arrange
		ReflectionTestUtils.setField(serv,"Secret_Key","5688982639iwhndyg632892@34hwue8wh76890277362773672");
		
	//act
	SecretKey actual=serv.getSigningKey();	
//assertion
	 assertNotNull(actual);
	}
	
	@Mock
	UserDetails userDetails ;
	@Test
	void areAllClaimsGetExtractedTest() {
		//arrange
		ReflectionTestUtils.setField(serv,"Secret_Key","5688982639iwhndyg632892@34hwue8wh76890277362773672");
		when(userDetails.getUsername() ).thenReturn("abc");
		String token=serv.generateToken(userDetails);
		//act
		Claims claims=serv.extractAllClaims(token);
		//assertion
		assertNotNull(claims);
	}
	
	@Test 
	void isTokenGeneratedSuccessfully(){
		//arrange
		ReflectionTestUtils.setField(serv,"Secret_Key","5688982639iwhndyg632892@34hwue8wh76890277362773672");
		when(userDetails.getUsername() ).thenReturn("abc");
		//act
		String token=serv.generateToken(userDetails);
		//assertion
		assertNotNull(token);
	}
	
}
