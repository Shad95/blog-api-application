package com.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDto {

	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4 , message = "category title cannot be less than 4 characters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10 , message = "category description cannot be less than 10 characters")
	private String categoryDescription;

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
