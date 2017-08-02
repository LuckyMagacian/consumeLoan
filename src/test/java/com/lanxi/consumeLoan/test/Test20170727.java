package com.lanxi.consumeLoan.test;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.utils.SqlUtilForDB;

public class Test20170727 {
	@Test
	public void test1(){
		String string="{'name':'name','type':'java.lang.String','value':'中国移动'}";
		Attribute<?> attribute=new Attribute<>().fromJson(string);
		System.out.println(attribute);
	}
	@Test
	public void test2(){
		System.out.println(SqlUtilForDB.getConnection());
	}
}
