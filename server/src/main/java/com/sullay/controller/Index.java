package com.sullay.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class Index {
	@GetMapping("/")
	public String index() {
		return "sully_new_blog";
	}
}
