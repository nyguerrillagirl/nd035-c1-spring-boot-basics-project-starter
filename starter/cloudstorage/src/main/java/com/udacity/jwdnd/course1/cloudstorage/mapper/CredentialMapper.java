package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialEntity;

@Mapper
public interface CredentialMapper {

	@Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
	public List<CredentialEntity> getAllUserCredentials(Integer userid);
		
	@Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid} AND url=#{url} AND username=#{username}")
	public CredentialEntity findMatchingUseridUrlAndUsername(Integer userid, String url, String username);
	
	@Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialid")
	public void insertCredential(CredentialEntity credential);
	
	@Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialid}")
	public CredentialEntity findCredential(Integer credentialid);
	
	@Delete("DELETE FROM CREDENTIALS where credentialid=#{credentialid}")
	public void deleteCredential(Integer credentialid);
	
	@Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE credentialid=#{credentialid}")
	public void updateCredential(CredentialEntity credential);
}
