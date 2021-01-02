package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;


@Mapper
public interface NoteMapper {

	@Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
	public List<Note> getAllUserNotes(Integer userid);
	
}
