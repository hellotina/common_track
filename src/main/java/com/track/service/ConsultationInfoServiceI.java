package com.track.service;
import java.util.List;

import com.track.entities.ConsultationInfo;

import net.sf.json.JSONObject;

public interface ConsultationInfoServiceI {
//	保存咨询
	public void saveConsultation(ConsultationInfo consultation);
//	查询咨询列表
	public List<ConsultationInfo> getConsultList(String openid);
//	组装咨询列表数据
	public List<JSONObject> getAllConsultInfoList(List<ConsultationInfo> list);
//	查看待处理咨询数量
	public int getConsultNum();
//	查看待处理咨询列表
	public List<ConsultationInfo> getConsultingList();
//	查询咨询详情页内容
	public JSONObject getConsultIndex(String id);
//	根据id查询咨询
	public ConsultationInfo findConsultById(String id);
//	查询待回复咨询列表
	public List<ConsultationInfo> getLawyerConsultingList(String openid);
}                                                                         	
