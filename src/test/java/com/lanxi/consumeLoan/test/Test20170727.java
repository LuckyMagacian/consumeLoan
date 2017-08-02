package com.lanxi.consumeLoan.test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.Attribute;

import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.utils.BeanUtil;


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
	public void test2() throws IllegalArgumentException, IllegalAccessException{
		Apply apply=new Apply();
		List<Field> fields=BeanUtil.getFieldListNoStatic(apply);
		for(Field each:fields){
			each.setAccessible(true);
			if(each.getType().equals(String.class))
				each.set(apply, "10086");
			else if(each.getType().equals(BigDecimal.class))
				each.set(apply, new BigDecimal("10000"));
		}
		System.out.println(JSONObject.toJSON(apply));
	}
	@Test
	public void test3(){
		BigDecimal a=new BigDecimal(0);
		BigDecimal b=new BigDecimal(50);
		a=a.add(b);
		System.out.println(a.doubleValue());
	}
	public void test22(){
		System.out.println(SqlUtilForDB.getConnection());

	}
}
