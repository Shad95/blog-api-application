package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiException;
import com.blog.payload.JwtAuthRequest;
import com.blog.payload.JwtAuthResponse;
import com.blog.payload.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	    String token = this.jwtTokenHelper.generateToken(userDetails);
	    
	    JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
	    jwtAuthResponse.setToken(token);
	    
	    return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
	    }


	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch(BadCredentialsException e) {
			System.out.println("Invalid details");
			
			throw new ApiException("Invalid username or password !!");
		}
		
	}
	
	// register a new user
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		
	UserDto	 registeredUser = userService.registerNewUser(userDto);
	
	return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	
}
