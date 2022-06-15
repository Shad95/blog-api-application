package com.blog.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

    //create a new post
	@Override
	public PostDto createPost(PostDto postDto , Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User id",userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		post.setCategory(category);
		post.setUser(user);
		Post savedPost = postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}
    
	
	//update a post by id
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id",postId));	
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}
	
	
   //delete a post by id
	@Override
	public void deletePost(Integer postId) {
	Post fetchedPost = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id",postId));
	postRepo.delete(fetchedPost);
		
	}
	

	//get a single post by id
	@Override
	public PostDto getPostById(Integer postId) {
		Post fetchedPost = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id",postId));
		return this.modelMapper.map(fetchedPost, PostDto.class);
	}
    
	
	// get all the posts
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getNumberOfElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	
	//get posts by category
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category id",categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
	//get posts by user
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
	    User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	List<Post> posts = this.postRepo.findByTitleContaining(keyword);
	List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class) ).collect(Collectors.toList());
		return postDtos;
	}

}
