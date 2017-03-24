package com.sundy.smart_framework.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseHelper {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
	
	private static ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	
	private static BasicDataSource dataSource;
	
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWROD;
	
	static{
		Properties props = PropsUtil.loadProps("config.properties");
		DRIVER = props.getProperty("jdbc.driver");
		URL = props.getProperty("jdbc.url");
		USERNAME = props.getProperty("jdbc.username");
		PASSWROD = props.getProperty("jdbc.passwrod");
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DRIVER);
		dataSource.setUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWROD);
	}
	
	public static void beginTransaction(){
		Connection conn = getConnection();
		if(conn!=null){
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				logger.error("commit transaction failure", e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	public static void commitTransaction(){
		Connection conn = getConnection();
		if(conn!=null){
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				logger.error("commit transaction failure", e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	public static void rollbackTransaction(){
		Connection conn = getConnection();
		if(conn!=null){
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				logger.error("rollback transaction failure", e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	public static Connection getConnection(){
		Connection conn = CONNECTION_HOLDER.get();
		if(conn==null){
			try {
				conn = dataSource.getConnection();
				CONNECTION_HOLDER.set(conn);
			} catch (SQLException e) {
				logger.error("get connection failure", e);
				throw new RuntimeException(e);
			}
		}
		
		return conn;
	}
	
	public static void closeConnection(){
		Connection conn = CONNECTION_HOLDER.get();
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("close connection failure", e);
			}finally{
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
}
