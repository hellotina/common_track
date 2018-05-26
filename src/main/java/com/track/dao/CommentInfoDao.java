package com.track.dao;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.track.entities.UserInfoEntity;
import com.track.entities.UserCommentRelation;
import com.track.entities.CommentInfoEntity;

@Repository
public class CommentInfoDao extends HibernateDaoSupport {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<UserCommentRelation> findByTargetUser(String openid){
		String hql = "select a from UserCommentRelation as a,CommentInfoEntity as b where a.commentid=b.id and a.targetuserid=:targetuserid order by b.time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("targetuserid",openid);
		return query.list();
	}
	
	public CommentInfoEntity getCommentsById(String id){
		String hql = "from CommentInfoEntity where id=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",id);
		return (CommentInfoEntity)query.uniqueResult();
	}
	
	public UserCommentRelation getCommentRelationById(String id){
		String hql = "from UserCommentRelation where id=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",id);
		return (UserCommentRelation)query.uniqueResult();
	}
	
	public CommentInfoEntity saveComment(CommentInfoEntity comment){
		Serializable result = sessionFactory.getCurrentSession().save(comment);		
		return getCommentsById((String)result);
	}
	
	public UserCommentRelation saveCommentRelation(UserCommentRelation relation){
		Serializable result = sessionFactory.getCurrentSession().save(relation);		
		return getCommentRelationById((String)result);
	}

}
