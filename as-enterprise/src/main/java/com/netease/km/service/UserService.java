package com.netease.km.service;

import com.netease.km.entity.User;

public interface UserService {
	public void save(User u);
	public User getById(int id);
}
