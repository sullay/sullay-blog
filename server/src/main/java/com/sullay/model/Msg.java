package com.sullay.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
@Data
public class Msg {
	//状态码  200-成功 400-失败
	private int code;
	//提示信息
	private String msg;
	//用户要返回给浏览器的数据
	private Map<String, Object> data=new HashMap<String, Object>();
	public static Msg success(){
		Msg result=new Msg();
		result.setCode(200);
		result.setMsg("操作成功!");
		return result;
	}
	public static Msg fail(){
		Msg result=new Msg();
		result.setCode(400);
		result.setMsg("操作失败!");
		return result;
	}
	public Msg add(String key,Object value){
		this.getData().put(key, value);
		return this;
		
	}
	
}
