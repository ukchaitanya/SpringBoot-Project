package com.example.demoproject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demoproject.user.User;

@Component
public class UserDao {
	
	private static List<User> users = new ArrayList<>();
	private static int userCount = 3;
	
	static {
		users.add(new User(1,"Abhi", new Date()));
		users.add(new User(2,"Mahi", new Date()));
		users.add(new User(3,"Sam", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		
		if(user.getId()==null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public User findByID(int id) {
		
		for(User user:users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
public User deleteUserByID(int id) {
		
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId()==id) {
				iterator.remove();
				return user;
			}
		}	
		return null;
	}
	
	
}
