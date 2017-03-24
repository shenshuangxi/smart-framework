package com.sundy.smart_framework.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sundy.smart_framework.bean.FormParam;
import com.sundy.smart_framework.bean.Param;
import com.sundy.smart_framework.util.ArrayUtil;
import com.sundy.smart_framework.util.CodecUtil;
import com.sundy.smart_framework.util.StreamUtil;
import com.sundy.smart_framework.util.StringUtil;

public class RequestHelper {

	public static Param createParam(HttpServletRequest request) throws IOException{
		List<FormParam> formParams = new ArrayList<FormParam>();
		formParams.addAll(parseParameterNames(request));
		formParams.addAll(parseInputStream(request));
		return new Param(formParams);
	}

	private static List<FormParam> parseInputStream(
			HttpServletRequest request) {
		List<FormParam> formParams = new ArrayList<FormParam>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String key = paramNames.nextElement();
			String value = request.getParameter(key);
			formParams.add(new FormParam(key, value));
		}
		return formParams;
	}

	private static List<FormParam> parseParameterNames(HttpServletRequest request) throws IOException {
		List<FormParam> formParams = new ArrayList<FormParam>();
		String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
		if(StringUtil.isNotEmpty(body)){
			String[] params = body.split("&");
			if(ArrayUtil.isNotEmpty(params)){
				for(String param : params){
					String[] array = param.split("=");
					if(ArrayUtil.isNotEmpty(array)&&array.length==2){
						String paramName = array[0];
						String paramValue = array[1];
						formParams.add(new FormParam(paramName, paramValue));
					}
				}
			}
		}
		return formParams;
	}
	
}
