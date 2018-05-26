package com.track.dao;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.track.entities.UserInfoEntity;
import com.track.entities.UserLocation;
import com.track.entities.UserCommentRelation;
import com.track.entities.UserFavoriteRelation;

@Repository
public class UserInfoDao extends HibernateDaoSupport {
	
	@Autowired
    private SessionFactory sessionFactory;
	
//	根据用户的openid查询用户信息
	public UserInfoEntity findById(String openid){
		String hql = "from UserInfoEntity where openid=:openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		UserInfoEntity user = (UserInfoEntity) query.uniqueResult();
		return user;
	}
	
//	获取除当前用户外所有用户列表
	@SuppressWarnings("unchecked")
	public List<UserInfoEntity> list(String openid){
		String hql = "from UserInfoEntity where openid <>:openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("openid", openid);
		return query.list();
	}
	
//	保存和修改用户信息
	public void saveOrUpdate(UserInfoEntity user){
		sessionFactory.getCurrentSession().saveOrUpdate(user);		
	}
	
//	保存和修改用户位置
	public void saveOrUpdateLocation(UserLocation location){
		sessionFactory.getCurrentSession().saveOrUpdate(location);
	}

//	根据用户的openid获取其留言板数量
	public int getCommentsNum(String id){
		String hql="from UserCommentRelation where targetuserid=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",id);
		return query.list().size();
	}
	
//	根据用户的openid获取其收藏夹数量
	public int getFavoriteNum(String id){
		String hql="from UserFavoriteRelation where userid=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",id);
		return query.list().size();
	}
	
//	根据用户的openid获取其收藏的用户信息列表
	@SuppressWarnings("unchecked")
	public List<UserFavoriteRelation> getFavoriteList(String id){
		String hql="from UserFavoriteRelation where userid=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",id);
		return query.list();
	}
	
//	取消收藏用户
	public void delUserRelation(String userid,String targetuserid){
		String hql = "delete from UserFavoriteRelation where userid=:userid and targetuserid=:targetuserid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid",userid);
		query.setParameter("targetuserid", targetuserid);
		query.executeUpdate();
	}
	
//	查询唯一用户收藏关系
	public UserFavoriteRelation findUserRelation(String userid,String targetuserid){
		String hql = "from UserFavoriteRelation where userid=:userid and targetuserid=:targetuserid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid",userid);
		query.setParameter("targetuserid", targetuserid);
		return (UserFavoriteRelation)query.uniqueResult();
	}
	
//	收藏用户
	public void addUserRelation(UserFavoriteRelation relation){
		sessionFactory.getCurrentSession().saveOrUpdate(relation);
	}
	
//	根据用户收藏关系id查询关系信息
	public UserFavoriteRelation findByIdUserRelation(String id){
		String hql = "from UserFavoriteRelation where id=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",id);
		return (UserFavoriteRelation)query.uniqueResult();
	}

//	获取除当前用户外所有用户的定位信息
	@SuppressWarnings("unchecked")
	public List<UserLocation> getUserLocationsList(String userid){
		String hql="from UserLocation where userid <>:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",userid);
		return query.list();
	}
	
//	根据用户id获取位置信息
	public UserLocation findLocationByUserId(String userid){
		String hql="from UserLocation where userid =:id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id",userid);
		return (UserLocation)query.uniqueResult();
	}
//	根据地区查询用户列表
	@SuppressWarnings("unchecked")
	public List<UserInfoEntity> findLocationByRegion(String province,String city,String district,String openid){
		String hql="select a from UserInfoEntity as a,UserLocation as b where a.openid<> :userid and a.openid=b.userid and b.province =:province and b.city =:city and b.district =:district";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("province",province);
		query.setParameter("city",city);
		query.setParameter("district",district);
		query.setParameter("userid",openid);
		return query.list();
	}
	
//	根据标签查询用户列表
	@SuppressWarnings("unchecked")
	public List<UserInfoEntity> findUserByTag(String tagid,String openid){
		String hql="select a from UserInfoEntity as a,UserTagRelation as b where a.openid <>:userid and a.openid=b.userid and b.tagid =:tagid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("tagid",tagid);
		query.setParameter("userid",openid);
		return query.list();
	}
//	根据地区查询用户id列表
	@SuppressWarnings("unchecked")
	public List<String> searchKeyWordsInRegion(String[] keyword,String userid){
		String hql="select b.userid from UserLocation as b where b.userid<> :userid and (";
		for(int i=0;i<keyword.length;i++){
			hql+="b.province like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%' or b.city like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%' or b.district like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%'";
			if(i != keyword.length-1){
				hql+=" or ";
			}
		}
		hql += ") group by b.userid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid",userid);
		return query.list();
	}
//	根据用户信息查询用户id列表
	@SuppressWarnings("unchecked")
	public List<String> searchKeyWordsInInfo(String[] keyword,String userid){
		String hql="select b.openid from UserInfoEntity as b where b.openid<> :userid and (";
		for(int i=0;i<keyword.length;i++){
			hql+="b.nickname like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%'"
					+ " or b.realname like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%'"
					+ " or b.occupation like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%'"
					+ " or b.description like '%" + StringEscapeUtils.escapeSql(keyword[i])+"%'";
			if(i != keyword.length-1){
				hql+=" or ";
			}
		}
		hql += ") group by b.openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("userid",userid);
		return query.list();
	}
}

