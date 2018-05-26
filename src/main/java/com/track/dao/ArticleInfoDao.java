package com.track.dao;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.track.entities.ArticleInfoEntity;
import com.track.entities.ConsultationInfo;
import com.track.entities.FieldInfoEntity;
import com.track.entities.UserInfoEntity;

@Repository
public class ArticleInfoDao extends HibernateDaoSupport {
	
	@Autowired
    private SessionFactory sessionFactory;
//	查询律师案例文章列表
	@SuppressWarnings("unchecked")
	public List<ArticleInfoEntity> findLawyerArticle(String openid){
		String hql = "from ArticleInfoEntity where lawyerid=:openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		return query.list();	
	}
//	保存案例文章
	public String saveArticle(ArticleInfoEntity article){
		Serializable result = sessionFactory.getCurrentSession().save(article);
		return (String)result;
	}
	
//	查看文章
	public ArticleInfoEntity findArticleById(String id){
		String hql = "from ArticleInfoEntity where id=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		return (ArticleInfoEntity)query.uniqueResult();
	}
	

}
