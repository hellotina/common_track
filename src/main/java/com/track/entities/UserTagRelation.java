package com.track.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_tag_relation")
public class UserTagRelation {
	@GeneratedValue
	@Id
	private String id;
	private String userid;
	private String tagid;
	
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
    public String getTagId(){
		return tagid;
	}
	public void setTagId(String tagid){
		this.tagid=tagid;
	}
}
