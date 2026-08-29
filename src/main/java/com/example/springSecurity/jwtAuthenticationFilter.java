package com.example.springSecurity;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springSecurity.Service.MyUserDetailService;
import com.example.springSecurity.Service.jwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class jwtAuthenticationFilter extends OncePerRequestFilter {
 UserDetails userDetails;
 @Autowired
 jwtService jwtser;
 @Autowired
	MyUserDetailService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token=null;
		String username=null;
		String authHeader=request.getHeader("Authorization");
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token= authHeader.substring(7);
			try { username=jwtser.extractUsername(token);}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
         UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			if(jwtser.istokenValid(token,userDetails)) {
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,
				userDetails.getAuthorities());
				
				
				System.out.println("Username: " + userDetails.getUsername());
				System.out.println("Authorities: " + userDetails.getAuthorities());
						
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
			
		}
			
		filterChain.doFilter(request, response);
	}

}
