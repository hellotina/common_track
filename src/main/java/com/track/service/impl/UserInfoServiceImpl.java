package com.track.service.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.dao.TagDao;
import com.track.dao.UserInfoDao;
import com.track.entities.TagEntity;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.entities.UserLocation;
import com.track.entities.UserTagRelation;
import com.track.service.UserInfoServiceI;
import com.track.util.HttpRequestUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoServiceI{
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private TagDao tagDao;
	
	public JSONObject getUserInfo(String openid){
		UserInfoEntity resUser = userInfoDao.findById(openid);
		JSONObject result = new JSONObject();
    	result = JSONObject.fromObject(resUser);
    	result.put("tag", getUserTag(openid));
    	return result;
	}
	
	public List<TagEntity> getUserTag(String openid){
		List<UserTagRelation> relationList = tagDao.findByUserId(openid);
		List<TagEntity> result=new ArrayList<TagEntity>();
		for(UserTagRelation item : relationList){
			String tagid=item.getTagId();
			result.add(tagDao.findById(tagid));
		}
		return result;
	}
	
	public UserInfoEntity findById(String openid){
		return userInfoDao.findById(openid);
	}
	
	public List<UserInfoEntity> getUsers(String openid){
		return userInfoDao.list(openid);
	}
	
	public void saveOrUpdate(UserInfoEntity user){
		userInfoDao.saveOrUpdate(user);
	}
	
	public int getCommentsNum(String id){
		return userInfoDao.getCommentsNum(id);
	}
	public int getFavoriteNum(String id){
		return userInfoDao.getFavoriteNum(id);
	}
	
	public List<UserFavoriteRelation> getFavoriteList(String id){
		return userInfoDao.getFavoriteList(id);
	}
	
	public void delUserRelation(String userid,String targetuserid){
		userInfoDao.delUserRelation(userid, targetuserid);
	}
	
	public void addUserRelation(UserFavoriteRelation relation){
		userInfoDao.addUserRelation(relation);
	}
	
	public UserFavoriteRelation findUserRelation(String userid,String targetuserid){
		return userInfoDao.findUserRelation(userid, targetuserid);
	}
	
	public UserFavoriteRelation findByIdUserRelation(String id){
		return userInfoDao.findByIdUserRelation(id);
	}
	
	public List<JSONObject> getUserLocationsList(String userid){
		List<UserLocation> list = userInfoDao.getUserLocationsList(userid);
		List<JSONObject> result =new ArrayList<JSONObject>();
    	
		for(UserLocation item:list){
			String id = item.getUserId();
			JSONObject temp = new JSONObject();
			temp=JSONObject.fromObject(item);
	    	temp.put("userInfo", getUserInfo(id));
	    	result.add(temp);
		}
		return result;
	}

	public JSONObject distanceAndAddress(UserLocation from,UserLocation to){
		  JSONObject returnResult = new JSONObject();
		  returnResult.put("distance", distance(from,to));
		  returnResult.put("address", address(to));
		  return returnResult;
	}
	
	public double distance(UserLocation from,UserLocation to){
		  String GetDistanceUrl = "http://apis.map.qq.com/ws/distance/v1/";
		  String distancePara = "mode=walking&key=MKMBZ-EPIKK-GZ3JA-ALXD6-BZ5VZ-BOBBG&";
		  String fromStr = "from="+from.getLat()+","+from.getLng();
		  String toStr = "to="+to.getLat()+","+to.getLng();
		  distancePara +=fromStr+"&"+toStr;
		  String disResult=HttpRequestUtil.sendGet(GetDistanceUrl,distancePara);  
		  JSONObject disResponseJson = JSONObject.fromObject(disResult);
		  double value = disResponseJson.getJSONObject("result").getJSONArray("elements").getJSONObject(0).getDouble("distance")/1000;
		  //保留两位小数
		  BigDecimal bg = new BigDecimal(value).setScale(2, RoundingMode.UP);
		  return bg.doubleValue();
	}
	
	public String address(UserLocation location){
		String GetAddressUrl = "http://apis.map.qq.com/ws/geocoder/v1/";
		String addressPara = "key=MKMBZ-EPIKK-GZ3JA-ALXD6-BZ5VZ-BOBBG&location="+location.getLat()+","+location.getLng();
		String addResult=HttpRequestUtil.sendGet(GetAddressUrl,addressPara);  
		JSONObject addResponseJson = JSONObject.fromObject(addResult);
		return addResponseJson.getJSONObject("result").getString("address");
	}
	
	public String zone(UserLocation location){
		String res = location.getProvince()+" "+location.getCity()+" "+location.getDistrict();
		return res;
	}
	
	
	public UserLocation findLocationByUserId(String userid){
		return userInfoDao.findLocationByUserId(userid);
	}
	
	public List<UserInfoEntity> findLocationByRegion(String province,String city,String district,String openid){
		return userInfoDao.findLocationByRegion(province, city, district,openid);
	}
	
	public List<UserInfoEntity> findUserByTag(String tagid,String openid){
		return userInfoDao.findUserByTag(tagid,openid);
	}
	
	public  List<JSONObject> getUserTotalInfo(List<UserInfoEntity> user,String openid){
		String GetDistanceUrl = "http://apis.map.qq.com/ws/distance/v1/";
		String distancePara = "mode=walking&key=MKMBZ-EPIKK-GZ3JA-ALXD6-BZ5VZ-BOBBG&";
		UserLocation from = findLocationByUserId(openid);
		String fromStr = "from="+from.getLat()+","+from.getLng();
		String toStr="to=";
//		  组合终点参数
		for(UserInfoEntity item : user){
			String userid = item.getOpenid();
			UserLocation location = findLocationByUserId(userid);
			String temp = location.getLat()+","+location.getLng();
			toStr += temp+";";
		}
		distancePara +=fromStr+"&"+toStr;
		distancePara = distancePara.substring(0,distancePara.length() - 1);
		  
//		  请求计算距离
		  String disResult=HttpRequestUtil.sendGet(GetDistanceUrl,distancePara);  
		  JSONObject disResponseJson = JSONObject.fromObject(disResult);
		  JSONArray data = disResponseJson.getJSONObject("result").getJSONArray("elements");
		  List<JSONObject> result =new ArrayList<JSONObject>();
		  if(data.size()>0){  
			  for(int i=0;i<data.size();i++){  
//				  距离精确到小数点后两位
				  JSONObject job = data.getJSONObject(i);
				  double distance = job.getDouble("distance")/1000;
				  BigDecimal bg = new BigDecimal(distance).setScale(2, RoundingMode.UP);
//				  获取tag列表
				  List<UserTagRelation> tagrelationlist = tagDao.findByUserId(user.get(i).getOpenid());
				  List<TagEntity> taglist = new ArrayList<TagEntity>();
				  for(UserTagRelation tag : tagrelationlist){
					  taglist.add(tagDao.findById(tag.getTagId()));
				  }
				  UserLocation location = findLocationByUserId(user.get(i).getOpenid());
//				  组合数据
				  JSONObject temp = new JSONObject();
				  temp.put("user",user.get(i));
				  temp.put("tag",taglist);
				  temp.put("location", location);
				  temp.put("distance",bg.doubleValue());
				  result.add(temp);
			  }  
		  }
		  
		  return result;
	}

	public List<UserInfoEntity> search(String[] keyword,String openid){
//		搜索
		List<String> useridList1 = tagDao.searchKeyWordsInTag(keyword,openid);
		List<String> useridList2 = userInfoDao.searchKeyWordsInRegion(keyword, openid);
		List<String> useridList3 = userInfoDao.searchKeyWordsInInfo(keyword, openid);
//		组合
		List<String> alluserId=new ArrayList<String>();
		alluserId.addAll(useridList1);
		alluserId.addAll(useridList2);
		alluserId.addAll(useridList3);
//		去重
		LinkedHashSet<String> set = new LinkedHashSet<String>(alluserId.size());
	    set.addAll(alluserId);
	    alluserId.clear();
	    alluserId.addAll(set);
//	    查询用户实体
		List<UserInfoEntity> result=new ArrayList<UserInfoEntity>();
		for(String userid:alluserId){
			result.add(userInfoDao.findById(userid));
		}
		return result;
		
	}
	
	public void saveOrUpdateLocation(UserLocation location){
		userInfoDao.saveOrUpdateLocation(location);
	}
}
