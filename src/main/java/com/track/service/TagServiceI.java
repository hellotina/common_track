package com.track.service;
import java.util.List;

import com.track.entities.TagEntity;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.entities.UserTagRelation;

public interface TagServiceI {
	
	public List<UserTagRelation> findTagByUserId(String openid);
	public TagEntity findTagById(String tagid);
	public TagEntity createTag(TagEntity tag);
	public void createUserTagRelatin(UserTagRelation relation);
	public UserTagRelation isExist(String tagid,String userid);
	public void delAllRelation(String userid);
	public List<TagEntity> searchTag(String key);
	public List<TagEntity> getHotTag(int num);
}
