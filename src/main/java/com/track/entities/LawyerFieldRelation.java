package com.track.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lawyer_field_relation")
public class LawyerFieldRelation {
	@Id
	private String id;
	private String lawyerid;
	private int fieldid;
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getLawyerId(){
		return lawyerid;
	}
	public void setLawyerId(String lawyerid){
		this.lawyerid=lawyerid;
    }
    public int getFieldId(){
		return fieldid;
	}
	public void setFieldId(int fieldid){
		this.fieldid=fieldid;
	}
}
