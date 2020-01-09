package com.example.demoproject.controller;

import java.net.URI;
import java.util.List;

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
import com.example.demoproject.user.User;
import com.example.demoproject.user.UserNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDao users;
	
	@GetMapping(path= "/users")
	public List<User> getAllUsers() {
		return users.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public User getUserByID(@PathVariable int id) {
		User user = users.findByID(id);
		if(user==null) {
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
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		
		
		System.out.println("In create user method");
		
		User savedUser = users.save(user);
		
		URI location = ServletUriComponentsBuilder.
		fromCurrentRequest().
		path("/{id}").
		buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
		//return "Created";
	}
	
	@DeleteMapping(path="/users/{id}")
	public User deleteByID(@PathVariable int id) {
		User user = users.deleteUserByID(id);
		if(user==null) {
			throw new UserNotFoundException("id- "+id);
		}
		//return users.findByID(id);
		return user;
	}
	
	

}
