package com.example.demoproject.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/*import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;*/

import com.example.demoproject.dao.UserDao;
import com.example.demoproject.user.Post;
import com.example.demoproject.user.PostRepository;
import com.example.demoproject.user.User;
import com.example.demoproject.user.UserNotFoundException;
import com.example.demoproject.user.UserRepository;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserDao users;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path= "/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{id}")
	public Optional<User> getUserByID(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id- "+id);
		}
		
		/*
		 * EntityModel<User> entityModel = new EntityModel<User>(user);
		 * WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		 * 
		 * entityModel.add(linkTo.withRel("all-users"));
		 */
		
		return user;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		
		
		System.out.println("In create user method");
		
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.
		fromCurrentRequest().
		path("/{id}").
		buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
		//return "Created";
	}
	
	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteByID(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(path= "/jpa/users/{id}/posts")
	public List<Post> getAllPosts(@PathVariable int id) {
		
		Optional<User> optionalUser = userRepository.findById(id); 
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("id -"+id);
		}
		return optionalUser.get().getPosts();
	}
	
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		
		
		Optional<User> optionalUser = userRepository.findById(id); 
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("id -"+id);
		}
		
		User user = optionalUser.get();
		
		post.setUser(user);
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.
		fromCurrentRequest().
		path("/{id}").
		buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	

}
