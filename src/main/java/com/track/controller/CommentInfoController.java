package com.track.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.track.common.ApiResult;
import com.track.entities.CommentInfoEntity;
import com.track.entities.UserCommentRelation;
import com.track.entities.UserInfoEntity;
import com.track.service.CommentInfoServiceI;
import com.track.util.HttpRequestUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/commentInfoController")
public class CommentInfoController {

	@Autowired
	private CommentInfoServiceI commentInfoService;
	
	
	@RequestMapping(value = "/getCommentList/{openid}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getCommentList(@PathVariable("openid") String openid){
		List<UserCommentRelation> relationList = commentInfoService.getList(openid);
		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		for(UserCommentRelation item : relationList) {
			  String userId = item.getUserId();
			  String commentId = item.getCommentId();
			  result.add(commentInfoService.getCommentInfo(commentId, userId));
		}
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/saveComment",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult saveComment(@RequestBody JSONObject paramsJson){
		String userid = paramsJson.getString("userid");
		String targetuserid = paramsJson.getString("targetuserid");
		String content = paramsJson.getString("content");
		CommentInfoEntity comment = new CommentInfoEntity();
		comment.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		comment.setContent(content);
		comment.setTime(new Date());
		return ApiResult.success(commentInfoService.saveComment(comment, userid, targetuserid));
	}
}



