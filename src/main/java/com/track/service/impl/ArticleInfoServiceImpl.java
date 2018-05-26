package com.track.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.dao.ArticleInfoDao;
import com.track.dao.ConsultationInfoDao;
import com.track.dao.FieldDao;
import com.track.dao.UserInfoDao;
import com.track.entities.ArticleInfoEntity;
import com.track.entities.ConsultationInfo;
import com.track.entities.FieldInfoEntity;
import com.track.entities.LawyerFieldRelation;
import com.track.service.ArticleInfoServiceI;
import com.track.service.ConsultationInfoServiceI;
import com.track.service.FieldInfoServiceI;

import net.sf.json.JSONObject;

@Service("articleInfoService")
@Transactional
public class ArticleInfoServiceImpl implements ArticleInfoServiceI{
	@Autowired
	private ArticleInfoDao articleInfoDao;
	
//	保存案例
	public String saveArticle(ArticleInfoEntity article){
		return articleInfoDao.saveArticle(article);
	}
//	查看文章
	public ArticleInfoEntity findArticleById(String id){
		return articleInfoDao.findArticleById(id);
	}
//	查询律师案例文章列表
	public List<ArticleInfoEntity> findLawyerArticle(String openid){
		return articleInfoDao.findLawyerArticle(openid);
	}

}
