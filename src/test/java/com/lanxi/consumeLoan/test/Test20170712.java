package com.lanxi.consumeLoan.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RolePrototype;
import com.lanxi.consumeLoan.basic.UserPrototype;
import com.lanxi.consumeLoan.functions.LoginFunction;

public class Test20170712 {

	
	public static class C1{
		String name;
		public C1(){};
		public C1(String name){
			this.name=name;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	public static class C2 extends C1{
		public C2(){
			
		}
		public C2(String name){
			super(name);
		}
	}


	@Test
	public void test1(){
		Attribute<List<Integer>> old=new Attribute<List<Integer>>();
		List<Integer> list=new ArrayList<Integer>();
		list.add(555);
		old.setName("old");
		old.setValue(list);
		System.out.println(old);
		String oldStr=JSONObject.toJSONString(old);
		System.out.println(oldStr);
		Attribute<?> old1=JSONObject.parseObject(oldStr,Attribute.class);
		System.out.println(old1);
		System.out.println(old1.getValue().getClass());
		old1.fromJson(oldStr);
		System.out.println(old1);
		System.out.println(old1.getValue().getClass());
	}
	@Test
	public void test2(){
		RolePrototype role=new RolePrototype();
		role.setRoleName("销售员");
		role.addAuthority(LoginFunction.class);
		
		UserPrototype user=new UserPrototype();

		UserPrototype user2=new UserPrototype();
		user2.setAttributes(user.getAttributes());
		user2.setRole(user.getRole());
		System.out.println(user2);
	}
}
