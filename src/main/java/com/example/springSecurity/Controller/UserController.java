package com.example.springSecurity.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springSecurity.Model.MyUser;
import com.example.springSecurity.Model.MyUserDto;
import com.example.springSecurity.Model.MyUserLogin;
import com.example.springSecurity.Service.MyUserDetailService;
import com.example.springSecurity.Service.jwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class UserController {
	@Autowired
	jwtService ser;
	@Autowired
	AuthenticationManager authManager ;
	@Autowired
	MyUserDetailService myService;
@PostMapping("/register")
public ResponseEntity<?> myUserRegister(@Valid @RequestBody MyUserDto myUserDto,BindingResult result ) {
	if(result.hasErrors()) {
		Map<String,String> errors=new HashMap<String,String>();
		for(FieldError error:result.getFieldErrors()) {
			errors.put(error.getField(),error.getDefaultMessage());
		}
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);	
	}
else {
	MyUser myuser=new MyUser();
	myuser.setUsername(myUserDto.getUsername());
	myuser.setPassword(myUserDto.getPassword());
	myuser.setRole(myUserDto.getRole());
	myService.addUserToRepository(myuser);
}
	return ResponseEntity.ok("user added successfully");
}
@PostMapping("/login")
public ResponseEntity<String> UserLoginCon(@RequestBody MyUserLogin request) {

    try {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            String token = ser.generateToken(userDetails);
            return ResponseEntity.ok(token);
        }

    } catch (BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Invalid credentials");
    }

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body("Authentication failed");
}

@GetMapping("/home")
public String home() {
	return "home";
}

@GetMapping("/admin/home")
public String admin() {
	return "admin";
}

@GetMapping("/user/home")
public String user() {
	return "user";
}
}