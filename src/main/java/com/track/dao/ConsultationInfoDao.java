package com.track.dao;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.track.entities.ConsultationInfo;
import com.track.entities.LawyerFieldRelation;
import com.track.entities.UserInfoEntity;

import net.sf.json.JSONObject;

@Repository
public class ConsultationInfoDao extends HibernateDaoSupport {
	
	@Autowired
    private SessionFactory sessionFactory;
//	保存咨询
	public void saveConsultation(ConsultationInfo consultation){
		sessionFactory.getCurrentSession().saveOrUpdate(consultation);
	}
	
//	查询咨询列表
	@SuppressWarnings("unchecked")
	public List<ConsultationInfo> getConsultList(String openid){
		String hql = "from ConsultationInfo where userid=:openid and rootId=-1";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		return query.list();	
	}
	
//	查询待回复咨询列表
	@SuppressWarnings("unchecked")
	public List<ConsultationInfo> getLawyerConsultingList(String openid){
		String hql = "from ConsultationInfo where rootId=-1 and status=1 and fieldid in (select a.fieldid from LawyerFieldRelation as a where lawyerid = :openid) or lawyerid=:openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		return query.list();	
	}
	
//	查看待处理咨询数量
	public int getConsultNum(){
		String hql = "from ConsultationInfo where status=0 and rootId=-1";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list().size();	
	}
	
//	查看待处理咨询列表
	@SuppressWarnings("unchecked")
	public List<ConsultationInfo> getConsultingList(){
		String hql = "from ConsultationInfo where status=0 and rootId=-1";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();	
	}
	
//	根据id查询咨询
	public ConsultationInfo findConsultById(String id){
		String hql = "from ConsultationInfo where id=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		return (ConsultationInfo)query.uniqueResult();
	}
	
//	根据rootId查询咨询
	@SuppressWarnings("unchecked")
	public List<ConsultationInfo> findConsultByRootId(String id){
		String hql = "from ConsultationInfo where rootid=:id order by time asc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		return query.list();
	}	

}
