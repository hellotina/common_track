package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "comment_info")
public class CommentInfoEntity {
	//@GeneratedValue
	@Id
	private String id;
	private Date time;
	private String content;
	
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getTime(){
    	return time;
    }
    public void setTime(Date time){
    	this.time = time;
    }
    
    public String getContent(){
    	return content;
    }
    public void setContent(String content){
    	this.content = content;
    }
  
}
