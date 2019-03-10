package com.cdfive.mp3.controller;

import com.cdfive.mp3.po.User;
import com.cdfive.mp3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@Autowired
	private UserService userService;

	@RequestMapping("/hello")
	public @ResponseBody
    String hello() {
		return "hello, world!";
	}

	@ResponseBody
	@RequestMapping("/test")
	public User test(String id) {
		User admin = userService.find(id);
		return admin;
	}

}