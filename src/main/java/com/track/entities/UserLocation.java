package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_location_info")
public class UserLocation {
	//@GeneratedValue
	@Id
	private String userid;
	private float lng;
	private float lat;
	private Date lastlogintime;
	private String province;
	private String city;
	private String district;

	public String getUserId(){
		return userid;
	}
	public void setUserId(String userid){
		this.userid=userid;
    }

	public float getLng(){
		return lng;
	}
	public void setLng(float lng){
		this.lng =lng;
	}
	public float getLat(){
		return lat;
	}
	public void setLat(float lat){
		this.lat =lat;
	}
	
	public Date getLastlogintime(){
		return lastlogintime;
	}
	public void setLastlogintime(Date lastlogintime){
		this.lastlogintime = lastlogintime;
	}
	public String getProvince(){
		return province;
	}
	public void setProvince(String province){
		this.province = province;
	}
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city = city;
	}
	public String getDistrict(){
		return district;
	}
	public void setDistrict(String district){
		this.district = district;
	}
}
