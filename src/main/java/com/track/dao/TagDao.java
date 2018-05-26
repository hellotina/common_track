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
import com.track.entities.UserTagRelation;
import com.track.entities.TagEntity;
import com.track.entities.UserCommentRelation;
import com.track.entities.UserFavoriteRelation;

@Repository
public class TagDao extends HibernateDaoSupport {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public TagEntity findById(String id){
		String hql="from TagEntity where tagid=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		return (TagEntity)query.uniqueResult();
	}
	
	public TagEntity saveTag(TagEntity tag){
		Serializable result = sessionFactory.getCurrentSession().save(tag);		
		return findById((String)result);
	}
	
	@SuppressWarnings("unchecked")
	public List<TagEntity> searchTag(String key){
		String hql ="from TagEntity where tagname like '%"+StringEscapeUtils.escapeSql(key)+"%'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> searchKeyWordsInTag(String[] keyword,String userid){
		String hql="select b.userid from TagEntity as a , UserTagRelation as b where a.tagid = b.tagid and (";
		for(int i=0;i<keyword.length;i++){
			hql+="a.tagname like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%'";
			if(i != keyword.length-1){
				hql+=" or ";
			}
		}
		hql += ") and b.userid <> :userid group by b.userid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		return query.list();
	}
	
	public void delAllRelation(String userid){
		String hql="delete from UserTagRelation where userid=:userid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
	
	public void saveRelation(UserTagRelation relation){
		sessionFactory.getCurrentSession().saveOrUpdate(relation);		
	}
	
	public UserTagRelation isExist(String tagid,String userid){
		String hql="from UserTagRelation where tagid=:tagid and userid=:userid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("tagid", tagid);
		query.setParameter("userid", userid);
		return (UserTagRelation)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserTagRelation> findByUserId(String openid){
		String hql="from UserTagRelation where userid=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", openid);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TagEntity> getHotTag(){
		String hql="select a from TagEntity as a,UserTagRelation as b where a.tagid=b.tagid group by b.tagid order by count(*) desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TagEntity> getNotUsedTag(){
		String hql="from TagEntity as a where (select count(*) as num from UserTagRelation as b where a.tagid = b.tagid) = 0";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
}
