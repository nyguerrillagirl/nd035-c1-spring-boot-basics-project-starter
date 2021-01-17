package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;

@Mapper
public interface FileMapper {

	@Select("SELECT * FROM FILES WHERE userid=#{userid}")
	public List<FileEntity> getAllUserFiles(Integer userid);
	
	@Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	public void insertFile(FileEntity file);
	
	@Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
	public FileEntity findFile(Integer fileId);
	
	@Delete("DELETE FROM FILES where fileId=#{fileId}")
	public void deleteFile(Integer fileId);
	
	
}
