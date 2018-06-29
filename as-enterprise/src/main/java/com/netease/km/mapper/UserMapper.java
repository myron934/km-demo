package com.netease.km.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.netease.km.entity.User;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM user")
	@Results({
		@Result(property = "id",  column = "id"),
		@Result(property = "name", column = "name"),
	})
	public List<User> listAll();
	
	@Select("SELECT * FROM user where id==#{id}")
	public User getById(int id);
	
	@Insert("INSERT INTO user(name) VALUES(#{name})")
	public void insert(User u);
	
	/**
	 * 可以获取新插入数据的主键id
	 * @param u
	 * @return
	 */
	@Insert("INSERT INTO user(name) VALUES(#{name})")
	@Options(useGeneratedKeys=true,keyProperty="id") 
	public int insert2(User u);
	
	@Update("UPDATE user SET phone=#{phone}, email=#{email} WHERE id=#{id}")
	public void update(User resms);
	
	@Delete("DELETE FROM user WHERE id=#{id}")
	public void delete(int id);
}
