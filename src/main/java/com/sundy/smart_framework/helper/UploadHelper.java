package com.sundy.smart_framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sundy.smart_framework.bean.FileParam;
import com.sundy.smart_framework.bean.FormParam;
import com.sundy.smart_framework.bean.Param;
import com.sundy.smart_framework.util.CollectionUtil;
import com.sundy.smart_framework.util.FileUtil;
import com.sundy.smart_framework.util.StreamUtil;
import com.sundy.smart_framework.util.StringUtil;

public class UploadHelper {

	private static final Logger logger = LoggerFactory.getLogger(UploadHelper.class);
	
	private static ServletFileUpload servletFileUpload;
	
	public static void init(ServletContext servletContext){
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if(uploadLimit!=0){
			servletFileUpload.setSizeMax(uploadLimit*1024*1024);
		}
	}
	
	public static boolean isMultipart(HttpServletRequest request){
		return ServletFileUpload.isMultipartContent(request);
	}
	
	public static Param createParam(HttpServletRequest request){
		List<FormParam> formParams = new ArrayList<FormParam>();
		List<FileParam> fileParams = new ArrayList<FileParam>();
		
		try {
			Map<String, List<FileItem>> map = servletFileUpload.parseParameterMap(request);
			if(CollectionUtil.isNotEmpty(map)){
				for(Map.Entry<String, List<FileItem>> entry : map.entrySet()){
					String fieldName = entry.getKey();
					List<FileItem> fileItems = entry.getValue();
					if(CollectionUtil.isNotEmpty(fileItems)){
						for(FileItem fileItem : fileItems){
							if(fileItem.isFormField()){
								String fieldValue = fileItem.getString("UTF-8");
								formParams.add(new FormParam(fieldName, fieldValue));
							}else{
								String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
								if(StringUtil.isNotEmpty(fileName)){
									long fileSize = fileItem.getSize();
									String contentType = fileItem.getContentType();
									InputStream inputStream = fileItem.getInputStream();
									fileParams.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
								}
							}
						}
					}
					
				}
			}
			
		} catch (FileUploadException | IOException e) {
			logger.error("create param failure", e);
			throw new RuntimeException(e);
		}
		return new Param(formParams,fileParams);
	}
	
	public static void uploadFile(String basePath, FileParam fileParam){
		try {
			if(fileParam!=null){
				String filePath = basePath + fileParam.getFileName();
				FileUtil.createFile(filePath);
				InputStream is = new BufferedInputStream(fileParam.getInputStream());
				OutputStream os = new BufferedOutputStream(new FileOutputStream(filePath));
				StreamUtil.copyStream(is,os);
			}
		} catch (FileNotFoundException e) {
			logger.error("upload file failure", e);
			throw new RuntimeException(e);
		}
	}
	
	public static void uploadFile(String basePath, List<FileParam> fileParams){
		try {
			if(CollectionUtil.isNotEmpty(fileParams)){
				for(FileParam fileParam : fileParams){
					uploadFile(basePath, fileParam);
				}
			}
		} catch (Exception e) {
			logger.error("upload file failure", e);
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
