package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Mapper
public interface FileMapper {

	@Select("SELECT * FROM FILES WHERE userid=#{userid}")
	public List<File> getAllUserFiles(Integer userid);
}
