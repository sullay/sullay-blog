package com.sullay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sullay.model.User;

public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
	User findByPassWord(String passWord);
}