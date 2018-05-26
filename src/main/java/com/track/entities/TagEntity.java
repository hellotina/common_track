package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "tag_info")
public class TagEntity {
	//@GeneratedValue
	@Id
	private String tagid;
	private String tagname;
	
	public String getTagId() {
        return tagid;
    }
    public void setTagId(String tagid) {
        this.tagid = tagid;
    }
    
    public String getTagname(){
    	return tagname;
    }
    public void setTagname(String tagname){
    	this.tagname = tagname;
    }
  
}
