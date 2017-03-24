package com.sundy.smart_framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sundy.smart_framework.util.CastUtil;
import com.sundy.smart_framework.util.CollectionUtil;
import com.sundy.smart_framework.util.StringUtil;

public class Param {

	
	private List<FormParam> formParams;
	private List<FileParam> fileParams;
	
	public Param(List<FormParam> formParams) {
		this.formParams = formParams;
	}
	
	public Param(List<FormParam> formParams, List<FileParam> fileParams) {
		this.formParams = formParams;
		this.fileParams = fileParams;
	}
	
	public Map<String, Object> getFieldMap(){
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		if(CollectionUtil.isNotEmpty(formParams)){
			for(FormParam formParam : formParams){
				String fieldName = formParam.getFieldName();
				Object fieldValue = formParam.getFieldValue();
				if(fieldMap.containsKey(fieldName)){
					fieldValue = fieldMap.get(fieldValue) + StringUtil.SEPARATOR + fieldValue;
				}
				fieldMap.put(fieldName, fieldValue);
			}
		}
		return fieldMap;
	}

	
	public Map<String,List<FileParam>> getFileMap(){
		Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
		if(CollectionUtil.isNotEmpty(fileParams)){
			for(FileParam fileParam : fileParams){
				String fieldName = fileParam.getFieldName();
				List<FileParam> fileParamList;
				if(fileMap.containsKey(fieldName)){
					fileParamList = fileMap.get(fieldName);
				}else{
					fileParamList = new ArrayList<FileParam>();
					fileMap.put(fieldName, fileParamList);
				}
				fileParamList.add(fileParam);
			}
		}
		return fileMap;
	}
	
	public List<FileParam> getFileList(String fieldName){
		return getFileMap().get(fieldName);
	}
	
	public FileParam getFile(String fieldName){
		List<FileParam> fileParams = getFileMap().get(fieldName);
		if(CollectionUtil.isNotEmpty(fileParams)&&fileParams.size()==1){
			return fileParams.get(0);
		}
		return null;
	}
	
	public boolean isEmpty(){
		return CollectionUtil.isEmpty(formParams)&&CollectionUtil.isEmpty(fileParams);
	}
	
	public String getString(String name){
		return CastUtil.castString(getFieldMap().get(name));
	}
	
	public String getDouble(String name){
		return CastUtil.castString(getFieldMap().get(name));
	}
	
	public Long getLong(String name){
		return CastUtil.castLong(getFieldMap().get(name));
	}
	
	public Integer getInt(String name){
		return CastUtil.castInt(getFieldMap().get(name));
	}
	
	public Boolean getBoolean(String name){
		return CastUtil.castBoolean(getFieldMap().get(name));
	}


}
