package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;


@Mapper
public interface NoteMapper {

	@Select("SELECT * FROM NOTES WHERE userid=#{userid}")
	public List<Note> getAllUserNotes(Integer userid);
		
	@Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "noteid")
	void insertNote(Note note);
	
	@Select("SELECT * FROM NOTES WHERE noteid=#{noteid}")
	public Note findNote(Integer noteid);
	
	@Delete("DELETE FROM NOTES where noteid=#{noteid}")
	public void deleteNote(Integer noteid);
	
	@Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid=#{noteid}")
	public void updateNote(Note note);
	
	
}
