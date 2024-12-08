package com.example.rediscache.service;

import java.util.List;

import com.example.rediscache.exception.UserNotFoundException;
import com.example.rediscache.model.User;
import com.example.rediscache.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;


@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public User save(User user) {
		repository.save(user);
		return user;
	}

	@Cacheable(value = "user")
	public List<User> findAll() {
		return repository.findAll();
	}

	@Cacheable(value = "user", key = "#id", condition="#id>=10")
	public User findUserById(int id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found"));
	}
	@Caching(
			  evict = {@CacheEvict(value = "user", allEntries = true), @CacheEvict(value="user", key="#id")
			}) 
	public String deleteUser(int id) {
		repository.deleteById(id);
		return "User deleted successfully!";
	}

}
