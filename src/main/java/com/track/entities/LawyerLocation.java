package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lawyer_location_info")
public class LawyerLocation {
//	@GeneratedValue
	@Id
	private String lawyerid;
	private float lng;
	private float lat;
	private String province;
	private String city;
	private String district;

	public String getLawyerId(){
		return lawyerid;
	}
	public void setLawyerId(String lawyerid){
		this.lawyerid=lawyerid;
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
