package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
    
	@Autowired
	private CategoryService categoryService;
	
	// create
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid@RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	
	
	// update 
	@PutMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid@RequestBody CategoryDto categoryDto , @PathVariable Integer categoryId) {
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	// delete
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		 this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully !!",true),HttpStatus.OK);
	}
	
	//get
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto fetchedCat = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(fetchedCat,HttpStatus.OK);
	}
	
	
	// get all
	@GetMapping("/category")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> fetchedCatList = this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(fetchedCatList,HttpStatus.OK);
	}
	
	
	
}
