package com.sullay.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sullay.model.User;
import com.sullay.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public void create(User user) {
		if (user.getId() == null || user.getCreateTime() == null) {
			user.setCreateTime(new Date());
		}
		userRepository.save(user);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public User findByPassWord(String passWord) {
		return userRepository.findByPassWord(passWord);
	}
}
