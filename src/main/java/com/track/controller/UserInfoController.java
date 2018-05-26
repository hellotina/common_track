package com.track.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.track.common.ApiResult;
import com.track.entities.TagEntity;
import com.track.entities.UserCommentRelation;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.entities.UserLocation;
import com.track.entities.UserTagRelation;
import com.track.service.TagServiceI;
import com.track.service.UserInfoServiceI;
import com.track.util.EncodingTool;
import com.track.util.HttpRequestUtil;
import com.track.util.uploadAction;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/userInfoController")
public class UserInfoController {

	@Autowired
	private UserInfoServiceI userInfoService;
	@Autowired
	private TagServiceI tagService;

//	用户登录&自动注册
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult Login(@RequestBody String params){
		String app_id = "wx596e7ba76a1ac0da";
		String app_secret = "de171a9f4113e740578d3c771871a239";
	    String GetOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session";
	    //获取参数code
	    JSONObject paramsJson = JSONObject.fromObject(params);
	    String para ="appid="+app_id+"&secret="+app_secret+"&js_code="+paramsJson.getString("code")+"&grant_type=authorization_code";
	    String openIdResult=HttpRequestUtil.sendGet(GetOpenIdUrl,para);  
	    JSONObject openIdJson = JSONObject.fromObject(openIdResult);
	    if(openIdJson.containsKey("openid")){
	    	String openid=openIdJson.getString("openid");
	    	JSONObject userinfo = paramsJson.getJSONObject("userInfo");
	    	UserInfoEntity user=new UserInfoEntity();
	    	user.setOpenid(openid);
			user.setNickname(EncodingTool.encodeStr(EncodingTool.filterEmoji(userinfo.getString("nickName"))));
	    	user.setAvatarUrl(userinfo.getString("avatarUrl"));
	    	user.setGender(userinfo.getInt("gender"));
	    	user.setBirth(new Date());
	    	UserInfoEntity u = userInfoService.findById(openid);
	    	if(u == null){
	    		userInfoService.saveOrUpdate(user);
	    	}
	    	return ApiResult.success(userInfoService.getUserInfo(openid));
	    }
	    else{
	    	String errorMsg = openIdJson.getString("errmsg");
	    	int errcode=0;
	    	try {
	    		errcode = Integer.parseInt(openIdJson.getString("errcode"));
	    	   
	    	} catch (NumberFormatException e) {
	    	    e.printStackTrace();
	    	}
	       return ApiResult.failure(errorMsg, errcode);
	    }
	}
	
//	根据openid获取用户信息
	@RequestMapping(value = "/getUserInfo/{openid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getUserInfo(@PathVariable("openid") String openid){
		return ApiResult.success(userInfoService.getUserInfo(openid));
	}
	
//	根据openid获取用户留言板数量
	@RequestMapping(value = "/getCommentsNum/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getCommentsNum(@PathVariable("id") String id){
		return ApiResult.success(userInfoService.getCommentsNum(id));
	}
//	根据openid获取用户收藏夹数量
	@RequestMapping(value = "/getFavoriteNum/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getFavoriteNum(@PathVariable("id") String id){
		return ApiResult.success(userInfoService.getFavoriteNum(id));
	}
	
//	取消用户收藏
	@RequestMapping(value = "/delUserRelation", method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResult delUserRelation(@RequestParam("userid") String userid,@RequestParam("targetuserid") String targetuserid){
		userInfoService.delUserRelation(userid,targetuserid);
		return ApiResult.success("");
	}
	
//	用户收藏
	@RequestMapping(value = "/addUserRelation", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult addUserRelation(@RequestBody JSONObject relationJSON){
		String targetuserid = relationJSON.getString("targetuserid");
		String userid = relationJSON.getString("userid");
		UserFavoriteRelation relation = new UserFavoriteRelation();
		relation.setTargetUserId(targetuserid);
		relation.setUserId(userid);
		UserFavoriteRelation r = userInfoService.findUserRelation(userid, targetuserid);
		if(r == null){
			relation.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			userInfoService.addUserRelation(relation);
			return ApiResult.success(userInfoService.findUserRelation(userid, targetuserid));
		}
		else{
			return ApiResult.failure("关系已存在", r);
		}
	}
	
//	判断用户收藏关系是否存在
	@RequestMapping(value = "/getUserRelationFlag", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult getUserRelationFlag(@RequestBody JSONObject relationJSON){
		return ApiResult.success(userInfoService.findUserRelation(relationJSON.getString("userid"), relationJSON.getString("targetuserid")));
	}
	
//	根据用户openid获取其收藏用户信息列表
	@RequestMapping(value = "/getFavoriteList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getFavoriteList(@PathVariable("id") String id){
		List<UserFavoriteRelation> relationList = userInfoService.getFavoriteList(id);
		List<Object> result=new ArrayList<Object>();
		JSONObject value = new JSONObject();
		for(UserFavoriteRelation item : relationList){
			String userid = item.getTargetUserId();
			value.put("user", userInfoService.findById(userid));
			value.put("distance", userInfoService.distance(userInfoService.findLocationByUserId(id), userInfoService.findLocationByUserId(userid)));
			List<UserTagRelation> tagrelationlist = tagService.findTagByUserId(userid);
			List<TagEntity> taglist = new ArrayList<TagEntity>();
			for(UserTagRelation tag : tagrelationlist){
				taglist.add(tagService.findTagById(tag.getTagId()));
			}
			value.put("tag",taglist);
			result.add(value);
		}
		return ApiResult.success(result);
	}
	
//	修改用户信息
	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult modifyInfo(@RequestBody UserInfoEntity user){
		String openid = user.getOpenid();
		UserInfoEntity u = userInfoService.findById(openid);
		String nickname = user.getNickname();
		String realname = user.getRealname();
		int gender = user.getGender();
		Date birth = user.getBirth();
		String description = user.getDescription();
		String occupation = user.getOccupation();
		if(nickname != null && nickname.length() != 0){
			u.setNickname(nickname);
		}
		if(realname != null && realname.length() != 0){
			u.setRealname(realname);
		}
		if(gender>0){
			u.setGender(gender);
		}
		if(birth != null){
			u.setBirth(birth);
		}
		if(description != null && description.length() !=0){
			u.setDescription(description);
		}
		if(occupation !=null && occupation.length()!=0){
			u.setOccupation(occupation);
		}
		userInfoService.saveOrUpdate(u);
		return ApiResult.success(userInfoService.findById(openid));
		
	}

//	查询除当前用户外的其他用户定位信息
	@RequestMapping(value = "/getUserLocations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getUserLocationsList(@PathVariable("id") String id){
		return ApiResult.success(userInfoService.getUserLocationsList(id));
	}
	
//	获取用户地址和距离
	@RequestMapping(value = "/getDistance", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getDistance(@RequestParam("userid") String userid,@RequestParam("targetuserid") String targetuserid){
		UserLocation from = userInfoService.findLocationByUserId(userid);
		UserLocation to = userInfoService.findLocationByUserId(targetuserid);
		return ApiResult.success(userInfoService.distanceAndAddress(from, to));
	}

//	获取用户主页信息
	@RequestMapping(value = "/getUserIndexInfo/{openid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getUserIndexInfo(@PathVariable("openid") String openid){
		JSONObject result = new JSONObject();
		result.put("userinfo",userInfoService.getUserInfo(openid));
		UserLocation location = userInfoService.findLocationByUserId(openid);
		result.put("lastlogintime", location.getLastlogintime());
		result.put("zone",userInfoService.zone(location));
		return ApiResult.success(result);
	}
	
//	搜索
	@RequestMapping(value = "/search/{openid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult search(@RequestParam("range") String range,@PathVariable("openid") String openid,
			@RequestParam(value="tagId",required=false) String tagId,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="province",required=false) String province,
			@RequestParam(value="city",required=false) String city,
			@RequestParam(value="district",required=false) String district){

		List<UserInfoEntity> userlist = new ArrayList<UserInfoEntity>();
		if(range.equals("all")){
			if(keyword == null || keyword.length() <= 0){
				userlist=userInfoService.getUsers(openid);
			}	
			else{
				keyword=EncodingTool.encodeStr(keyword);
				String[] keywords = keyword.split(" ");
				userlist = userInfoService.search(keywords,openid);
			}
		}
		else if(range.equals("tag")){
			userlist = userInfoService.findUserByTag(tagId,openid);
		}
		else if(range.equals("region")){
			province=EncodingTool.encodeStr(province);
			city=EncodingTool.encodeStr(city);
			district=EncodingTool.encodeStr(district);
			userlist=userInfoService.findLocationByRegion(province, city, district,openid);
		}
		if(userlist.size()>0){
			List<JSONObject> userTotalInfoList = userInfoService.getUserTotalInfo(userlist, openid);
			return ApiResult.success(userTotalInfoList);
		}
		else{
			return ApiResult.success("[]");
		}
		
	}

//	保存用户位置
	@RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult saveLocation(@RequestBody JSONObject locationJSON){
//		设置userid
		UserLocation location = new UserLocation();
		location.setUserId(locationJSON.getString("userid"));
//		查询区域
		String GetAddressUrl = "http://apis.map.qq.com/ws/geocoder/v1/";
		String regionPara = "key=MKMBZ-EPIKK-GZ3JA-ALXD6-BZ5VZ-BOBBG&location="+locationJSON.getString("lat")+","+locationJSON.getString("lng");
		String regionResult=HttpRequestUtil.sendGet(GetAddressUrl,regionPara);  
		JSONObject regionResponseJson = JSONObject.fromObject(regionResult);
		JSONObject region= regionResponseJson.getJSONObject("result").getJSONObject("address_component");
		location.setProvince(region.getString("province"));
		location.setCity(region.getString("city"));
		location.setDistrict(region.getString("district"));
//		记录时间
		location.setLastlogintime(new Date());
//		坐标转换
		String transUrl="http://apis.map.qq.com/ws/coord/v1/translate";
		String para="type=1&key=MKMBZ-EPIKK-GZ3JA-ALXD6-BZ5VZ-BOBBG&locations="+locationJSON.getString("lat")+","+locationJSON.getString("lng");
		String result=HttpRequestUtil.sendGet(transUrl,para);  
		JSONObject resultJson = JSONObject.fromObject(result);
		location.setLat((float)resultJson.getJSONArray("locations").getJSONObject(0).getDouble("lat"));
		location.setLng((float)resultJson.getJSONArray("locations").getJSONObject(0).getDouble("lng"));
//		保存
		userInfoService.saveOrUpdateLocation(location);
		return ApiResult.success(userInfoService.findLocationByUserId(location.getUserId()));
		
	}
	
//	获取热门标签
	@RequestMapping(value = "/getHotTag", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getHotTag(){
		return ApiResult.success(tagService.getHotTag(10));
	}
	
//	上传文件
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult upload(MultipartFile file,String user,String field,HttpServletRequest request) throws IOException{
	    if(file.isEmpty()){   
	   		return ApiResult.failure("文件为空", "error");
	    }else{   
	    	if(file.getContentType().indexOf("image")>-1){
	    		String realPath = request.getSession().getServletContext().getRealPath("/upload");   
		    	String fileName = uploadAction.upload(file,realPath);	
		 
		    	UserInfoEntity u = userInfoService.findById(user);
		    	if(field.equals("QRcode")){
		    		u.setQRcode("upload/"+fileName);
		    	}
		    	else if(field.equals("avatarUrl")){
		    		u.setAvatarUrl("upload/"+fileName);
		    	}
		    	 userInfoService.saveOrUpdate(u);
			   return ApiResult.success("upload/"+fileName);
	    	}
	    	else
	    		return ApiResult.failure("上传文件格式错误", "content-type must be image");
	    
	    }   
	}
	
//	删除文件
	@RequestMapping(value="/delPic",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult delPic(@RequestBody JSONObject param){
		String fileName=param.getString("fileName");
		String realpath = System.getProperty("user.dir")+"/src/main/webapp/"+fileName;
		return ApiResult.success(new File(realpath).delete());
	}
}



