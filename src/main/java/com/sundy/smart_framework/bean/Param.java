package com.sundy.smart_framework.bean;

import java.util.Map;

import com.sundy.smart_framework.util.CastUtil;

public class Param {

	private Map<String, Object> paramMap;
	
	public Param(Map<String,Object> paramMap){
		this.paramMap = paramMap;
	}
	
	public Long getLong(String name){
		return CastUtil.castLong(paramMap.get(name));
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}
	
	
}
