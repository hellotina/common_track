package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article_info")
public class ArticleInfoEntity {
	@Id
	private String id;
	private Date time;
	private String casecontent;
	private String viewpoint;
	private String judgment;
	private String lawyerid;
	private String title;
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public Date getTime(){
		return time;
	}
	public void setTime(Date time){
		this.time=time;
	}
	public String getCaseContent(){
		return casecontent;
	}
	public void setCaseContent(String casecontent){
		this.casecontent=casecontent;
	}
	public String getViewpoint(){
		return viewpoint;
	}
	public void setViewpoint(String viewpoint){
		this.viewpoint=viewpoint;
	}
	public String getJudgment(){
		return judgment;
	}
	public void setJudgment(String judgment){
		this.judgment=judgment;
	}
    public String getLawyerId(){
		return lawyerid;
	}
	public void setLawyerId(String lawyerid){
		this.lawyerid=lawyerid;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title=title;
	}
}
