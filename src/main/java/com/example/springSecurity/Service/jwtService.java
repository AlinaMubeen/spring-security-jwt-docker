package com.example.springSecurity.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class jwtService {
	
	@Value("${jwt.secretKey}")
	String Secret_Key;
	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Secret_Key.getBytes());
	}
	String token=null;
	String username=null;
	final int Expiration_Time=1000*60*60;
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + Expiration_Time))
				.signWith(getSigningKey())
				.compact();
			
	}
	
	public String extractUsername(String token) {
		return username=extractAllClaims(token).getSubject();
	}

	public  Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}
	
	public boolean isTokenExpired(String token) {
		return 
				extractAllClaims(token).getExpiration().before(new Date());
	}

	public boolean istokenValid(String token,UserDetails userDetails) {
			String name=userDetails.getUsername();
			return 
					name.equals(extractAllClaims(token).getSubject()) && !isTokenExpired(token);
		}
	}

