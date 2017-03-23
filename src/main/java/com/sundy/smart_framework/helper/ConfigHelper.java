package com.sundy.smart_framework.helper;

import java.util.Properties;

import com.sundy.smart_framework.ConfigConstant;
import com.sundy.smart_framework.util.PropsUtil;

public class ConfigHelper {

	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
	
	/**
	 * 数据库驱动
	 * @return
	 */
	public static String getJdbcDriver(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}
	
	/**
	 * 数据库url
	 * @return
	 */
	public static String getJdbcUrl(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}
	
	/**
	 * 数据库访问用户名
	 * @return
	 */
	public static String getJdbcUsername(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}
	
	/**
	 * 数据库访问密码
	 * @return
	 */
	public static String getJdbcPassword(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}
	
	/**
	 * 应用基础包名
	 * @return
	 */
	public static String getAppBasePackage(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}
	
	/**
	 * 应用jsp路径
	 * @return
	 */
	public static String getAppJspPath(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
	}
	
	/**
	 * 应用静态资源路径
	 * @return
	 */
	public static String getAppAssetPath(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH,"/asset/");
	}
	
}
