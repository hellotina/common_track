package com.track.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.track.common.ApiResult;
import com.track.entities.TagEntity;
import com.track.entities.UserCommentRelation;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.entities.UserTagRelation;
import com.track.service.TagServiceI;
import com.track.service.UserInfoServiceI;
import com.track.util.HttpRequestUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/tagController")
public class TagController {

	@Autowired
	private TagServiceI tagService;
	@Autowired
	private UserInfoServiceI userInfoService;

	@RequestMapping(value = "/getUserTag/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getUserTag(@PathVariable("id") String id){
		return ApiResult.success(userInfoService.getUserTag(id));
	}
	
	@RequestMapping(value="/createTag",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult createTag(@RequestBody TagEntity tag){
		return ApiResult.success(tagService.createTag(tag));
	}
	
	@RequestMapping(value="/modifyTag",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult modifyTag(@RequestParam("openid") String openid,@RequestParam("ids") String ids){
		tagService.delAllRelation(openid);
		for (String tagid : ids.split(",")) {
			UserTagRelation relation = new UserTagRelation();
			relation.setUserId(openid);
			relation.setTagId(tagid);	
			tagService.createUserTagRelatin(relation);
		}
	
		return ApiResult.success(userInfoService.getUserInfo(openid));
	}
	
	
	@RequestMapping(value="/searchTag",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult modifyTag(@RequestParam("key") String key){
		try {
			String params = new String(key.getBytes("iso8859-1"),"utf-8");
//			params.replace("[", "[[]");
//			params.replace("%", "[%]");
//			params.replace("_", "[_]");
//			params.replace("^", "[^]");
//			params.replace("'", "\'");
			return ApiResult.success(tagService.searchTag(params));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ApiResult.failure("查询失败", "");
		}
		
	}
	
}



