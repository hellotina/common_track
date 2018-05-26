package com.track.service;
import java.util.List;

import com.track.entities.CommentInfoEntity;
import com.track.entities.UserCommentRelation;

import net.sf.json.JSONObject;


public interface CommentInfoServiceI {
	//查询被留言人的留言板列表
	public List<UserCommentRelation> getList(String openid);
	public CommentInfoEntity getCommentsById(String id);
	public JSONObject getCommentInfo(String commentid,String userid);
	public JSONObject saveComment(CommentInfoEntity comment,String userid,String targetuserid);
}
