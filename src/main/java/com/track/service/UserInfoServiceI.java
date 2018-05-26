package com.track.service;
import java.util.List;

import com.track.entities.TagEntity;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.entities.UserLocation;
import com.track.entities.UserTagRelation;

import net.sf.json.JSONObject;

public interface UserInfoServiceI {
	public JSONObject getUserInfo(String openid);
	public List<TagEntity> getUserTag(String openid);
	public UserInfoEntity findById(String openid);
	public List<UserInfoEntity> getUsers(String openid);
	public void saveOrUpdate(UserInfoEntity user);
	public int getCommentsNum(String id);
	public int getFavoriteNum(String id);
	public List<UserFavoriteRelation> getFavoriteList(String id);
	public void delUserRelation(String userid,String targetuserid);
	public void addUserRelation(UserFavoriteRelation relation);
	public UserFavoriteRelation findUserRelation(String userid,String targetuserid);
	public UserFavoriteRelation findByIdUserRelation(String id);
	public List<JSONObject> getUserLocationsList(String userid);
	public JSONObject distanceAndAddress(UserLocation from,UserLocation to);
	public double distance(UserLocation from,UserLocation to);
	public String address(UserLocation location);
	public String zone(UserLocation location);
	public UserLocation findLocationByUserId(String userid);
	public List<UserInfoEntity> findLocationByRegion(String province,String city,String district,String openid);   
	public List<UserInfoEntity> findUserByTag(String tagid,String openid);
	public List<JSONObject> getUserTotalInfo(List<UserInfoEntity> user,String openid);
	public List<UserInfoEntity> search(String[] keyword,String openid);  
	public void saveOrUpdateLocation(UserLocation location);
}                                                                         	
