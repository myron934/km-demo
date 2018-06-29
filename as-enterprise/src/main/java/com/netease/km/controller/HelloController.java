package com.netease.km.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netease.km.aspect.ControllerAutoLog;
import com.netease.km.common.DataSourceKey;
import com.netease.km.context.DynamicDataSourceContextHolder;
import com.netease.km.entity.User;
import com.netease.km.service.UserService;

@RestController
@ControllerAutoLog
public class HelloController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
	
	@RequestMapping(value="/login", produces = "application/json;charset=UTF-8")
    public String login(@RequestParam(value="user_id",required=false) String userId) {
        return "{\"msg\":\"成功了\"}";
    }
//	
	
	@RequestMapping(value="/add_user", produces = "application/json;charset=UTF-8")
    public String addUser(@RequestParam(value="user_name") String name) {
		DynamicDataSourceContextHolder.setDataSource(DataSourceKey.test.name());
		User u=new User();
		u.setName(name);
		userService.save(u);
		System.out.println(u.getId());
        return "{\"msg\":\"成功了\"}";
    }
	@RequestMapping(value="/add_user2", produces = "application/json;charset=UTF-8")
	public String addUser2(@RequestParam(value="user_name") String name) {
		DynamicDataSourceContextHolder.setDataSource(DataSourceKey.test2.name());
		User u=new User();
		u.setName(name);
		userService.save(u);
		System.out.println(u.getId());
		return "{\"msg\":\"成功了\"}";
	}
}
