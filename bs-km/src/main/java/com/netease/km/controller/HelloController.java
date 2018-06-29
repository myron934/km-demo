package com.netease.km.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netease.km.aspect.ControllerAutoLog;
import com.netease.km.service.UserService;

@RestController
@ControllerAutoLog
@RequestMapping(value="/hello", produces = "application/json;charset=UTF-8")
public class HelloController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/get_user")
	public String getUser(@RequestParam(value="user_id") int userId) {
        return userService.getById(userId);
    }
	
	@RequestMapping(value="/login", produces = "application/json;charset=UTF-8")
    public String login(@RequestParam(value="user_id",required=false) String userId) {
        return "{\"msg\":\"成功了\"}";
    }
	
	@RequestMapping(value="/add_user", produces = "application/json;charset=UTF-8")
    public String addUser(@RequestParam(value="user_name") String name) {
		userService.save(name);
        return "{\"msg\":\"成功了\"}";
    }
	@RequestMapping(value="/add_user2", produces = "application/json;charset=UTF-8")
	public String addUser2(@RequestParam(value="user_name") String name) {
		userService.save(name);
        return "{\"msg\":\"成功了\"}";
	}
	
}
