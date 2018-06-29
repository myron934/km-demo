package com.netease.km.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.km.entity.User;
import com.netease.km.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	@Transactional
	public void save(User u) {
		userMapper.insert2(u);
	}

	@Override
	public User getById(int id) {
		User u=userMapper.getById(id);
		return u;
	}
	
	

}
