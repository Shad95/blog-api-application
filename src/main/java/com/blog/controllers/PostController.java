package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payload.ApiResponse;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// create post
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createpost(@RequestBody PostDto postDto, @PathVariable Integer userId ,@PathVariable Integer categoryId) {
		
		PostDto createdPost = (PostDto) this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
		
	}
	
	
	//get post by user
	@GetMapping("/user/{userId}/post") 
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	//get post by category
	@GetMapping("/category/{categoryId}/post") 
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	//get a single post by id
	@GetMapping("/post/{postId}") 
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)  {
		
		PostDto fetchedPost = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(fetchedPost,HttpStatus.OK);
	}
	
	
	//get all post
	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false)String sortBy)  {
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}	
	
	
	// delete a post
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		 this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully !!",true),HttpStatus.OK);
	}
	
	
	// update a post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid@RequestBody PostDto postDto , @PathVariable Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postDto,postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	
	
	// search a post
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		List<PostDto> posts = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	
	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException {
		PostDto postDto = this.postService.getPostById(postId);
	String fileName = this.fileService.uploadImage(path, image);
	postDto.setImageName(fileName);
	PostDto updatedPostDto = postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	
	// method to serve files
	@GetMapping(value ="/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
	
	   InputStream resource =  fileService.getResource(path, imageName);
	   response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	   StreamUtils.copy(resource, response.getOutputStream());
	}
}
