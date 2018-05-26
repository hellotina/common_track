package com.track.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.dao.TagDao;
import com.track.dao.UserInfoDao;
import com.track.entities.TagEntity;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.entities.UserTagRelation;
import com.track.service.TagServiceI;
import com.track.service.UserInfoServiceI;

import net.sf.json.JSONObject;

@Service("tagService")
@Transactional
public class TagServiceImpl implements TagServiceI{
	@Autowired
	private TagDao tagDao;
	
	public List<UserTagRelation> findTagByUserId(String openid){
		return tagDao.findByUserId(openid);
	}
	
	public TagEntity findTagById(String tagid){
		return tagDao.findById(tagid);
	}
	
	public TagEntity createTag(TagEntity tag){
		return tagDao.saveTag(tag);
	}
	
	public void createUserTagRelatin(UserTagRelation relation){
		tagDao.saveRelation(relation);
	}
	
	public UserTagRelation isExist(String tagid,String userid){
		return tagDao.isExist(tagid, userid);
	}
	public void delAllRelation(String userid){
		tagDao.delAllRelation(userid);
	}
	
	public List<TagEntity> searchTag(String key){
		return tagDao.searchTag(key);
	}
	
	public List<TagEntity> getHotTag(int num){
		List<TagEntity> list = tagDao.getHotTag();
		List<TagEntity> listn = tagDao.getNotUsedTag();
		int[] index = new int[num-list.size()];
		for(int k=0;k<index.length;k++){
			index[k]=-1;
		}
		if(list.size()<num){
			Random random = new Random();
			int i = 0;
			do{
				int n = random.nextInt(listn.size());
				int j=0;
				for(j=0;j<index.length;j++){
					if(n==index[j]){
						break;
					}
				}
				if(j == index.length){
					index[i] = n;
					list.add(listn.get(n));
				}
				else{
					i--;
				}
				i++;
			}while(list.size()<num);
		}
		return list;
	}
}
