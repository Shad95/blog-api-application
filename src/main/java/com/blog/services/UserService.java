package com.blog.services;

import java.util.List;

import com.blog.payload.UserDto;

public interface UserService {
	
	public UserDto createUser(UserDto userDto);
	public UserDto updateUser(UserDto userDto,Integer userId);
	public UserDto getUserById(Integer userId);
	public List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
