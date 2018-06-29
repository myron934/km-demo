package com.netease.km.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.km.context.RequestContext;
import com.netease.km.context.RequestContextHolder;
import com.netease.km.rpc.HelloRpc;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private HelloRpc helloRpc;
	@Override
	public void save(String u) {
		RequestContext ctx=RequestContextHolder.getContext();
		helloRpc.addUser(RequestContext.decode(ctx),u);
	}
	@Override
	public String getById(int id) {
		return helloRpc.getUser(id);
	}
	
	
	

}
