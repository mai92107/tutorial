package com.example.rediscache.controller;

import java.util.List;

import com.example.rediscache.model.User;
import com.example.rediscache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public User save(@RequestBody User user) {
		return userService.save(user);
	}
	
	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@GetMapping("/findById/{id}")
	public User findById(@PathVariable int id) {
		return userService.findUserById(id);
	}

	@DeleteMapping("/remove/{id}")
	public String remove(@PathVariable int id) {
		return userService.deleteUser(id);
	}
	
}
