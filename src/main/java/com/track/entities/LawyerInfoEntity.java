package com.track.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "lawyer_info")
public class LawyerInfoEntity {
	@Id
	private String id;
	private String openid;
	private String realname;
	private String avatarUrl;
	private String lawfirm;
	private String licenseNum;
	private String address;
	private String mobile;
	private String Qrcode;
	private int status;

	public String getOpenid(){
		return openid;
	}
	public void setOpenId(String openid){
		this.openid=openid;
	}
    public String getId(){
    	return id;
    }
    public void setId(String id){
    	this.id=id;
    }
    public String getRealname(){
    	return realname;
    }
    public void setRealname(String realname){
    	this.realname = realname;
    }
    
    public String getAvatarUrl(){
    	return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl){
    	this.avatarUrl=avatarUrl;
    }
    
    public String getLawfirm(){
    	return lawfirm;
    }
    public void setLawfirm(String lawfirm){
    	this.lawfirm=lawfirm;
    }
    public String getLicenseNum(){
    	return licenseNum;
    }
    public void setLicenseNum(String licenseNum){
    	this.licenseNum=licenseNum;
    }
    public String getAddress(){
    	return address;
    }
    public void setAddress(String address){
    	this.address=address;
    }
    public String getMobile(){
    	return mobile;
    }
    public void setMobile(String mobile){
    	this.mobile=mobile;
    }
    public String getQrcode(){
    	return Qrcode;
    }
    public void setQrcode(String Qrcode){
    	this.Qrcode=Qrcode;
    }
    public int getStatus(){
    	return status;
    }
    public void setStatus(int status){
    	this.status=status;
    }
    
  
}
