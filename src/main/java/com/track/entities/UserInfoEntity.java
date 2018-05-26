package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "user_info")
public class UserInfoEntity {
	@Id
	private String openid;
	private String nickname;
	private String realname;
	private int gender;
	private Date birth;
	private String description;
	private String avatarUrl;
	private String occupation;
	private String QRcode;
	
    public String getNickname(){
    	return nickname;
    }
    public void setNickname(String nickname){
    	this.nickname = nickname;
    }
    
    public String getRealname(){
    	return realname;
    }
    public void setRealname(String realname){
    	this.realname = realname;
    }
    
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    
    public Date getBirth(){
    	return birth;
    }
    public void setBirth(Date birth){
    	this.birth = birth;
    }
    
    public String getDescription(){
    	return description;
    }
    public void setDescription(String description){
    	this.description = description;
    }
    
    public String getOpenid(){
    	return openid;
    }
    public void setOpenid(String openid){
    	this.openid=openid;
    }
    
    public String getAvatarUrl(){
    	return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl){
    	this.avatarUrl=avatarUrl;
    }
    public String getOccupation(){
    	return occupation;
    }
    public void setOccupation(String occupation){
    	this.occupation = occupation;
    }
    public String getQRcode(){
    	return QRcode;
    }
    public void setQRcode(String QRcode){
    	this.QRcode=QRcode;
    }
}
