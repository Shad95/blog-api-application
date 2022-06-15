package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {

	// create post
	PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);
	
	
	//update post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	
	//delete post
	void deletePost(Integer postId);
	
	
	// get one post
	PostDto getPostById(Integer postId);
	
	
	//get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy);
	
	
	// get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	// get all post by user
	List<PostDto> getPostsByUser(Integer postId);

	
	// search posts
	List<PostDto> searchPosts(String keyword);
	
}
