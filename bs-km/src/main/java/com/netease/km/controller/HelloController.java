package com.netease.km.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netease.km.aspect.ControllerAutoLog;
import com.netease.km.rpc.HelloRpc;
import com.netease.km.service.UserService;

@RestController
@ControllerAutoLog
public class HelloController {
	@Autowired
	UserService userService;
	
	@Autowired
	private HelloRpc helloRpc;
	
	@RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
	
	@ControllerAutoLog(required="false")
	@RequestMapping(value="/login", produces = "application/json;charset=UTF-8")
    public String login(@RequestParam(value="user_id",required=false) String userId) {
		helloRpc.login("alice", "alice_19");
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
