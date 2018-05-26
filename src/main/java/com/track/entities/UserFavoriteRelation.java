package com.track.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_favorite_relation")
public class UserFavoriteRelation {
	//@GeneratedValue
	@Id
	private String id;
	private String userid;
	private String targetuserid;
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getUserId(){
		return userid;
	}
	public void setUserId(String userid){
		this.userid=userid;
	}
    public String getTargetUserId(){
		return targetuserid;
	}
	public void setTargetUserId(String targetuserid){
		this.targetuserid=targetuserid;
	}
}
