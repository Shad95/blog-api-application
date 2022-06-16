package com.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entities.Role;

public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4 , message = "name must be of atleast 4 characters")
	private String name;
	
	@NotEmpty
	@Size(min = 4 , max = 10 , message = "password should be minimum 4 characters and maximum 10 characters long !!")
	private String password;
	
	@Email(message = "entered email address is invalid !!")
	private String email;
	
	@NotEmpty
	private String about;
	
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	private Set<RoleDto> roles = new HashSet<>();

	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}

	
}
