package com.sundy.smart_framework.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static String getRealFileName(String filename) {
		return FilenameUtils.getName(filename);
	}

	public static File createFile(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			File parentDir = file.getParentFile();
			if(!parentDir.exists()){
				FileUtils.forceMkdir(parentDir);
			}
		} catch (IOException e) {
			logger.error("create file failure", e);
			throw new RuntimeException(e);
		}
		return file;
		
	}

}
