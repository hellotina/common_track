package com.track.common;

public class ApiResult {
	private boolean status;
    private String msg;
    private Object data;
    
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getMsg(){
    	return msg;
    }
    public void setMsg(String msg){
    	this.msg = msg;
    }
    
    public Object getData(){
    	return data;
    }
    public void setData(Object data){
    	this.data = data;
    }
    
   
    ApiResult(boolean s,String m,Object d){
    	status = s;
    	msg = m;
    	data = d;
    }
    
    /**
     * 请求成功返回
     */
    public static ApiResult success(Object data) {
        return new ApiResult(true,"请求成功",data);

    }

    /**
     * 请求失败返回
     */
    public static ApiResult failure(String errorMsg, Object data) {
        return new ApiResult(false, "请求失败: [ " + errorMsg + " ]", data);

    }
}
