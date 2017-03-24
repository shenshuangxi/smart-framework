package com.sundy.smart_framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StreamUtil {

	private static final Logger logger = LoggerFactory.getLogger(QueryHelper.class);
	
	public static String getString(InputStream is){
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line=br.readLine())!=null){
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error("get string failure",e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	public static void copyStream(InputStream is, OutputStream os) {
		
		try {
			int length;
			byte[] buffer = new byte[4*1024];
			while((length=is.read(buffer,0,buffer.length))!=-1){
				os.write(buffer, 0, length);
			}
			os.flush();
		} catch (IOException e) {
			logger.error("copy stream failure", e);
			throw new RuntimeException(e);
		}finally{
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				logger.error("close stream failure", e);
			}
		}
		
	}
	
	
	
	
	
}
