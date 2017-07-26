package com.lanxi.consumeLoan.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.utils.LoggerUtil;

public class Test20170719 {
	
	public Connection getConn()throws Exception{
		LoggerUtil.init();
		ConfigManager.loadConfigs();
//		System.out.println(ConfigManager.getPropertiesFileNames());
//		String url="jdbc:mysql://192.168.17.62:3306/consumeloan";
//		String user="yyj";
//		String password="131056";
		String url=ConfigManager.get("jdbc","url");
		String user=ConfigManager.get("jdbc","username");
		String password=ConfigManager.get("jdbc","password");
		String driver=ConfigManager.get("jdbc","driver");
		return DriverManager.getConnection(url, user, password);
	}
	@Before
	public void init(){
		
	}
	
	@Test
	public void test1() throws Exception{
		String sql="select * from user";
		Connection conn=getConn();
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		ResultSetMetaData metaData=rs.getMetaData();
		int columnCount=metaData.getColumnCount();
		System.out.println(columnCount);
		while(rs.next()){
			for(int i=1;i<=columnCount;i++)
				System.out.println(rs.getString(i));
		}
		conn.close();
	}
	
}
