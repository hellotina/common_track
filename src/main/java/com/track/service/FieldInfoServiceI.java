package com.track.service;
import java.util.List;
import com.track.entities.FieldInfoEntity;
import com.track.entities.LawyerFieldRelation;

public interface FieldInfoServiceI {
//	查询所有专长
	public List<FieldInfoEntity> findAllField();
//	保存律师专长关系
	public void saveLawyerFieldRelation(LawyerFieldRelation relation);
//	查询律师专长列表
	public List<FieldInfoEntity> findLawyerField(String openid);
//	删除律师所有专长
	public void delLawyerFieldRelation(String openid);
}                                                                         	
