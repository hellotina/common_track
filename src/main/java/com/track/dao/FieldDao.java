package com.track.dao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.track.entities.UserInfoEntity;
import com.track.entities.FieldInfoEntity;
import com.track.entities.LawyerFieldRelation;
import com.track.entities.UserFavoriteRelation;

@Repository
public class FieldDao extends HibernateDaoSupport {
	
	@Autowired
    private SessionFactory sessionFactory;
	
//	查询所有专长
	@SuppressWarnings("unchecked")
	public List<FieldInfoEntity> findAllField(){
		String hql = "from FieldInfoEntity";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();	
	}
//	保存律师专长关系
	public void saveLawyerFieldRelation(LawyerFieldRelation relation){
		sessionFactory.getCurrentSession().save(relation);
	}
//	删除律师所有专长
	public void delLawyerFieldRelation(String openid){
		String hql="delete from LawyerFieldRelation where lawyerid=:openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		query.executeUpdate();
	}
//	查询律师专长列表
	@SuppressWarnings("unchecked")
	public List<FieldInfoEntity> findLawyerField(String openid){
		String hql = "select a from FieldInfoEntity as a,LawyerFieldRelation as b where a.id=b.fieldid and b.lawyerid=:openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		return query.list();	
	}
}
