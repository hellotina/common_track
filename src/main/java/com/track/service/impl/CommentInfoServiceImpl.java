package com.track.service.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.dao.CommentInfoDao;
import com.track.dao.UserInfoDao;
import com.track.entities.CommentInfoEntity;
import com.track.entities.UserCommentRelation;
import com.track.entities.UserInfoEntity;
import com.track.service.CommentInfoServiceI;

import net.sf.json.JSONObject;



@Service("commentInfoService")
@Transactional
public class CommentInfoServiceImpl implements CommentInfoServiceI{
	@Autowired
	private CommentInfoDao commentInfoDao;
	@Autowired
	private UserInfoDao userInfoDao;
	
	//查询被留言人的留言板列表
	public List<UserCommentRelation> getList(String openid){
		return commentInfoDao.findByTargetUser(openid);
	}
	public CommentInfoEntity getCommentsById(String id){
		return commentInfoDao.getCommentsById(id);
	}
	public JSONObject getCommentInfo(String commentid,String userid){
		//用户信息
		UserInfoEntity user = userInfoDao.findById(userid);
		JSONObject userInfo = new JSONObject();
		userInfo.put("openid", user.getOpenid());
		userInfo.put("nickname", user.getNickname());
		userInfo.put("avatarUrl", user.getAvatarUrl());
		//留言信息
		CommentInfoEntity comment = commentInfoDao.getCommentsById(commentid);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = df.format(comment.getTime());
		JSONObject commentInfo = new JSONObject();
		commentInfo.put("id", comment.getId());
		commentInfo.put("content",comment.getContent());
		commentInfo.put("time", time);
		//组返回信息
		JSONObject commentData = new JSONObject();
		commentData.put("comment",commentInfo);
		commentData.put("user", userInfo);
		return commentData;
	}
	
	public JSONObject saveComment(CommentInfoEntity comment,String userid,String targetuserid){
		JSONObject result = new JSONObject();
		CommentInfoEntity c = commentInfoDao.saveComment(comment);
		result.put("comment", c);
		UserCommentRelation relation = new UserCommentRelation();
		relation.setCommentId(c.getId());
		relation.setUserId(userid);
		relation.setTargetUserId(targetuserid);
		relation.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		result.put("commentRelation", commentInfoDao.saveCommentRelation(relation));
		return result;
	}
}
