package com.track.service;
import java.util.List;

import com.track.entities.ArticleInfoEntity;
import com.track.entities.ConsultationInfo;

import net.sf.json.JSONObject;

public interface ArticleInfoServiceI {
//	保存案例
	public String saveArticle(ArticleInfoEntity article);
//	查看文章
	public ArticleInfoEntity findArticleById(String id);
//	查询律师案例文章列表
	public List<ArticleInfoEntity> findLawyerArticle(String openid);
}                                                                         	
