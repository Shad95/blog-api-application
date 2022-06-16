package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    
	@Autowired
	private UserService userService;
	
	//create a user
	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid@RequestBody UserDto userDto) {
		
	UserDto createdUserDto = userService.createUser(userDto);
	
	return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	
	// update a user
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid@RequestBody UserDto userDto,@PathVariable Integer userId) {
		
	UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
	
	return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
	}
	
	// delete a user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
	return new ResponseEntity<ApiResponse>(new ApiResponse("User Successfully Deleted",true),HttpStatus.OK);
	}
	
	//get all users 
		@GetMapping("/user")
		public ResponseEntity<List<UserDto>> getAllUsers() {
			
			return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
				
		}
		
		//get single users 
				@GetMapping("/user/{userId}")
				public ResponseEntity<UserDto>  getSingleUser(@PathVariable Integer userId) {
					
					return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
						
				}
}
