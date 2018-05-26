package com.track.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.dao.ConsultationInfoDao;
import com.track.dao.FieldDao;
import com.track.dao.UserInfoDao;
import com.track.entities.ConsultationInfo;
import com.track.entities.FieldInfoEntity;
import com.track.entities.LawyerFieldRelation;
import com.track.entities.LawyerInfoEntity;
import com.track.entities.UserInfoEntity;
import com.track.service.ConsultationInfoServiceI;
import com.track.service.FieldInfoServiceI;

import net.sf.json.JSONObject;

@Service("consultationInfoService")
@Transactional
public class ConsultationInfoServiceImpl implements ConsultationInfoServiceI{
	@Autowired
	private ConsultationInfoDao consultationInfoDao;
	@Autowired
	private UserInfoDao userInfoDao;
	
//	保存咨询
	public void saveConsultation(ConsultationInfo consultation){
		consultationInfoDao.saveConsultation(consultation);
	}
//	查询咨询列表
	public List<ConsultationInfo> getConsultList(String openid){
		return consultationInfoDao.getConsultList(openid);
		
	}
//	组装咨询列表数据
	public List<JSONObject> getAllConsultInfoList(List<ConsultationInfo> list){
		List<JSONObject> result = new ArrayList<JSONObject>();
		for(ConsultationInfo item:list){
			JSONObject temp = new JSONObject();
			String openid = item.getUserId();
			temp.put("user", userInfoDao.findById(openid));
			temp.put("consult", item);
			result.add(temp);
		}
		return result;
	}
	
//	查看待处理认证数量
	public int getConsultNum(){
		return consultationInfoDao.getConsultNum();
	}	
//	查看待处理咨询列表
	public List<ConsultationInfo> getConsultingList(){
		return consultationInfoDao.getConsultingList();
	}
	
//	根据id查询咨询
	public ConsultationInfo findConsultById(String id){
		return consultationInfoDao.findConsultById(id);
	}
//	查询咨询详情页内容
	public JSONObject getConsultIndex(String id){
		JSONObject result = new JSONObject();
		//查询主咨询
		ConsultationInfo main = consultationInfoDao.findConsultById(id);
		result.put("mainConsult", main);
		result.put("user", userInfoDao.findById(main.getUserId()));
		//查询回复列表
		List<JSONObject> sub = new ArrayList<JSONObject>();
		List<ConsultationInfo> consultList = consultationInfoDao.findConsultByRootId(id);
		for(ConsultationInfo item : consultList){
			JSONObject temp = new JSONObject();
			//组咨询信息
			temp.put("consult",item);
			//组用户信息			
			String userid =item.getUserId();
			int isLawyer =item.getIsLawyer();
			if(isLawyer==1){
				LawyerInfoEntity lawyer = userInfoDao.findLawyerById(userid);
				temp.put("userinfo", lawyer);
			}
			else{
				UserInfoEntity user = userInfoDao.findById(userid);
				temp.put("userinfo", user);
			}
			//组回复用户名
			if(item.getParentId().equals(item.getRootId())){
				temp.put("replyName", "");
			}
			else{
				ConsultationInfo c = consultationInfoDao.findConsultById(item.getParentId());
				String name = "";
				if(c.getIsLawyer()==1){
					name = userInfoDao.findLawyerById(c.getUserId()).getRealname();
				}
				else{
					name = userInfoDao.findById(c.getUserId()).getNickname();
				}
				temp.put("replyName", name);
			}
			
			sub.add(temp);
		}
		result.put("subConsult", sub);
		return result;
		
	}
	
//	查询待回复咨询列表
	public List<ConsultationInfo> getLawyerConsultingList(String openid){
		return consultationInfoDao.getLawyerConsultingList(openid);
	}
}
