package com.example.springSecurity.Service;

import java.util.Collection;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springSecurity.Password;
import com.example.springSecurity.Model.MyUser;
import com.example.springSecurity.Repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UserRepository repository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> user=repository.getUserByUsername(username);
		if(user.isPresent()) {
			var obj=user.get();
			return User.builder()
					.username(obj.getUsername())
					.password(obj.getPassword())
					.roles(obj.getRole())
					.build();
			
		}
		else throw new UsernameNotFoundException(username);
	}
	
	public void addUserToRepository(MyUser myuser) {
		myuser.setPassword(encoder.encode(myuser.getPassword()));
		repository.saveAndFlush(myuser);
		
	}

}


