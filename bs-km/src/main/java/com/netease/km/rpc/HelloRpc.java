package com.netease.km.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.netease.km.aspect.FeignClientAutoLog;

import feign.Headers;


@FeignClient(name="as-enterprise")
@Headers({"Content-Type: application/json","Accept: application/json"})
@FeignClientAutoLog
public interface HelloRpc {
	@RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);
	
	@RequestMapping(value="/login", produces = "application/json;charset=UTF-8")
    public String login(@RequestParam(value="userId",required=false) String userId,  @RequestParam(value="age",required=false) String age);
	
	@RequestMapping(value="/add_user", produces = "application/json;charset=UTF-8")
    public String addUser(@RequestParam(value="_ctx",required=false) String ctx, @RequestParam(value="user_name") String name);
}
