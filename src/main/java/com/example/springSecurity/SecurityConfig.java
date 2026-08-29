package com.example.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springSecurity.Service.MyUserDetailService;

@Configuration
public class SecurityConfig {
	@Autowired
	jwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	Password password;
/*	@Bean
 public PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
 }
 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) {
		return security
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth
						.requestMatchers("/login","/register","/home").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasRole("USER")
						.anyRequest().authenticated())
						.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
								.build();
						
				
	}
	@Bean
	public AuthenticationProvider authenticationProvider(MyUserDetailService myUserDetailService) {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(myUserDetailService);
		provider.setPasswordEncoder(password.passwordEncoder());
		return provider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	 
}