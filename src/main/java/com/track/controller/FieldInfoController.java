package com.track.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.track.common.ApiResult;
import com.track.service.FieldInfoServiceI;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/fieldInfoController")
public class FieldInfoController {

	@Autowired
	private FieldInfoServiceI fieldInfoService;

//	获取全部专长领域
	@RequestMapping(value = "/getAllField", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getAllField(){
		return ApiResult.success(fieldInfoService.findAllField());
	}
	
//	根据openid获取律师专长
	@RequestMapping(value = "/getField/{openid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getField(@PathVariable("openid") String openid){
		return ApiResult.success(fieldInfoService.findLawyerField(openid));
	}
}



