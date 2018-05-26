package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "consultation_info")
public class ConsultationInfo {
	@Id
	private String id;
	private Date time;
	private String content;
	private String parentId;
	private String rootId;
	private String userid;
	private int isLawyer;
	private int status;
	private int fieldid;
	private String lawyerid;
	
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
    
    public String getParentId(){
    	return parentId;
    }
    public void setParentId(String parentId){
    	this.parentId=parentId;
    }
    public String getRootId(){
    	return rootId;
    }
    public void setRootId(String rootId){
    	this.rootId=rootId;
    }
    public String getUserId(){
    	return userid;
    }
    public void setUserId(String userid){
    	this.userid=userid;
    }
    public int getIsLawyer(){
    	return isLawyer;
    }
    public void setIsLawyer(int isLawyer){
    	this.isLawyer=isLawyer;
    }
    public int getStatus(){
    	return status;
    }
    public void setStatus(int status){
    	this.status=status;
    }
    public int getFieldId(){
    	return fieldid;
    }
    public void setFieldId(int fieldid){
    	this.fieldid = fieldid;
    }
    public String getLawyerId(){
    	return lawyerid;
    }
    public void setLawyerId(String lawyerid){
    	this.lawyerid=lawyerid;
    }
  
}
