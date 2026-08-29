package com.example.springSecurity.contoller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springSecurity.Controller.UserController;
import com.example.springSecurity.Model.MyUser;
import com.example.springSecurity.Service.MyUserDetailService;
import com.example.springSecurity.Service.jwtService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
@Autowired
private MockMvc mockMvc;
@MockitoBean
private MyUserDetailService myService;
@MockitoBean
jwtService ser;
@MockitoBean
AuthenticationManager authManager ;
@Test
void testRegistration() throws Exception {
	String json = """
            {
                "username": "abc",
                "password": "12345",
                "role": "user"
            }
			"""
            ;
	mockMvc.perform(post("/register")
			.with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
	.content(json))
	.andExpect(status().isOk())
	.andExpect(content().string("user added successfully"));
	verify(myService).addUserToRepository(any());
}

@Test
void UserLoginTestSuccess() throws Exception {
	//arrange
	 UserDetails userDetails = User.withUsername("abc")
			.password("12345")
			.roles("user")
			.build();
	
	Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	


	when(authManager.authenticate(any())).thenReturn(authentication);
	when(ser.generateToken(userDetails)).thenReturn("fake-token-generated");
	//assert
	
			
	String JSON="""
			{
			"username":"abc",
			"password":"12345"
			}
			"""
			;
	mockMvc.perform(post("/login")
	.with(csrf())
	.contentType(MediaType.APPLICATION_JSON)
	.content(JSON))
	.andExpect(status().isOk())
	.andExpect(content().string("token generated suucessfully"));
	verify(ser).generateToken(userDetails);
		
}


@Test
void userLoginTestFailure() throws Exception{
	//arrange
	UserDetails userDetails=User.withUsername("abc")
			.password("12345")
			.authorities("user")
			.build();
	Authentication authentication=
			new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	when(authManager.authenticate(any())).thenThrow(new BadCredentialsException("invalid credentilas"));
	//act
	String JSON="""
			{
			"username":"abc",
			"password":"12345"
			}
			"""
			;
	mockMvc.perform(post("/login")
			.with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content(JSON))
			.andExpect(status().isForbidden())
			.andExpect(content().string("Invalid credentials"));
	
	//assertion
	verify(ser,never()).generateToken(any());
	
}
}
