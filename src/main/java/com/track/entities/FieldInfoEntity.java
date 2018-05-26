package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "field_info")
public class FieldInfoEntity {
	@GeneratedValue
	@Id
	private int id;
	private String name;
	
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName(){
    	return name;
    }
    public void setName(String name){
    	this.name = name;
    }
  
}
