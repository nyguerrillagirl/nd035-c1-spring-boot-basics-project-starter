package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteEntity;


@Mapper
public interface NoteMapper {

	@Select("SELECT * FROM NOTES WHERE userid=#{userid}")
	public List<NoteEntity> getAllUserNotes(Integer userid);
		
	@Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "noteid")
	public void insertNote(NoteEntity note);
	
	@Select("SELECT * FROM NOTES WHERE noteid=#{noteid}")
	public NoteEntity findNote(Integer noteid);
	
	@Delete("DELETE FROM NOTES where noteid=#{noteid}")
	public void deleteNote(Integer noteid);
	
	@Delete("DELETE FROM NOTES where userid=#{userid}")
	public void deleteAllUserNotes(Integer userid);
	
	@Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid=#{noteid}")
	public void updateNote(NoteEntity note);
	
	
}
