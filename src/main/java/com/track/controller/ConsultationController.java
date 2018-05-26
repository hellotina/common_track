package com.track.controller;

import java.io.File;
import java.io.IOException;
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
import com.track.entities.ConsultationInfo;
import com.track.entities.LawyerFieldRelation;
import com.track.entities.LawyerInfoEntity;
import com.track.entities.UserFavoriteRelation;
import com.track.entities.UserInfoEntity;
import com.track.service.ConsultationInfoServiceI;
import com.track.service.FieldInfoServiceI;
import com.track.service.UserInfoServiceI;
import com.track.util.EncodingTool;
import com.track.util.HttpRequestUtil;
import com.track.util.uploadAction;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/consultationController")
public class ConsultationController {

	@Autowired
	private ConsultationInfoServiceI consultationInfoService;
	@Autowired
	private UserInfoServiceI userInfoService;

//	保存咨询信息
	@RequestMapping(value = "/saveConsult", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult saveConsult(@RequestBody JSONObject params){
		ConsultationInfo consult=new ConsultationInfo();
		consult.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		consult.setTime(new Date());
		consult.setContent(params.getString("content"));
		consult.setStatus(params.getInt("status"));
		consult.setIsLawyer(params.getInt("isLawyer"));
		consult.setRootId(params.getString("rootId"));
		consult.setParentId(params.getString("parentId"));
		consult.setUserId(params.getString("userid"));
		consult.setFieldId(-1);
		consult.setLawyerId(params.getString("lawyerid"));
		consultationInfoService.saveConsultation(consult);
		return ApiResult.success("");
	}
	
//	关闭咨询
	@RequestMapping(value = "/closeConsult/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult closeConsult(@PathVariable("id") String id){
		ConsultationInfo consult=consultationInfoService.findConsultById(id);
		consult.setStatus(2);
		consultationInfoService.saveConsultation(consult);
		return ApiResult.success("");
	}
	
//	获取咨询列表
	@RequestMapping(value = "/getConsultList/{openid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getConsultList(@PathVariable("openid") String openid){
		List<ConsultationInfo> list = consultationInfoService.getConsultList(openid);
		return ApiResult.success(consultationInfoService.getAllConsultInfoList(list));
	}
	
//	查看待处理咨询数量
	@RequestMapping(value="/getConsultNum",method=RequestMethod.GET)
	@ResponseBody
	public ApiResult getConsultNum(){
		return ApiResult.success(consultationInfoService.getConsultNum());
	}
	
//	查看待处理咨询列表
	@RequestMapping(value="/getConsultingList",method=RequestMethod.GET)
	@ResponseBody
	public ApiResult getConsultingList(){
		List<ConsultationInfo> list = consultationInfoService.getConsultingList();
		return ApiResult.success(consultationInfoService.getAllConsultInfoList(list));
	}

//	查看待回复咨询列表
	@RequestMapping(value="/getLawyerConsultingList/{openid}",method=RequestMethod.GET)
	@ResponseBody
	public ApiResult getLawyerConsultingList(@PathVariable("openid") String id){
		List<ConsultationInfo> list = consultationInfoService.getLawyerConsultingList(id);
		return ApiResult.success(consultationInfoService.getAllConsultInfoList(list));
	}
	
//	查询咨询详情页内容
	@RequestMapping(value="/getConsultIndex/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ApiResult getConsultIndex(@PathVariable("id") String id){
		return ApiResult.success(consultationInfoService.getConsultIndex(id));
	}
	
//	转派咨询
	@RequestMapping(value = "/sendConsult", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult sendConsult(@RequestBody JSONObject params){
		String id = params.getString("id");
		int fieldid = params.getInt("fieldid");
		ConsultationInfo consult = consultationInfoService.findConsultById(id);
		consult.setFieldId(fieldid);
		consult.setStatus(1);
		consultationInfoService.saveConsultation(consult);
		return ApiResult.success("");
	}
	

}



