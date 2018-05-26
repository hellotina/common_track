package com.track.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.dao.FieldDao;
import com.track.entities.FieldInfoEntity;
import com.track.entities.LawyerFieldRelation;
import com.track.service.FieldInfoServiceI;

@Service("fieldInfoService")
@Transactional
public class FieldInfoServiceImpl implements FieldInfoServiceI{
	@Autowired
	private FieldDao fieldInfoDao;
//	查询所有专长
	public List<FieldInfoEntity> findAllField(){
		return fieldInfoDao.findAllField();
	}
	
//	保存律师专长关系
	public void saveLawyerFieldRelation(LawyerFieldRelation relation){
		fieldInfoDao.saveLawyerFieldRelation(relation);
	}
//	查询律师专长列表
	public List<FieldInfoEntity> findLawyerField(String openid){
		return fieldInfoDao.findLawyerField(openid);
	}
//	删除律师所有专长
	public void delLawyerFieldRelation(String openid){
		fieldInfoDao.delLawyerFieldRelation(openid);
	}
}
