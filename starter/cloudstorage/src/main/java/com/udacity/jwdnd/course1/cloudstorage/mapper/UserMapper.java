package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM USERS WHERE username = #{username}")
	User getUserByName(String username);
	
	@Select("SELECT * FROM USERS WHERE userid = #{userid}")
	User getUserById(Integer userid);
	
	@Select("SELECT * FROM USERS")
	List<User> getAllUsers();
	
	@Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
	@Options(useGeneratedKeys = true, keyProperty = "userid")
	Integer insert(User user);
	
	@Delete("DELETE FROM USERS WHERE userid = #{userid}")
	void delete(Integer userid);

}
