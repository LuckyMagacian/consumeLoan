package com.lanxi.consumeLoan.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.consumeLoan.aop.SetEncodeUtf8;
import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.controller.TestController;
import com.lanxi.consumeLoan.dao.RoleDao;
import com.lanxi.consumeLoan.dao.UserDao;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.RoleAddFunction;
import com.lanxi.consumeLoan.functions.UserAddFunction;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.consumeLoan.service.DaoServiceImpl;
import com.lanxi.util.utils.FileUtil;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.SqlUtilForDB;

public class ApplicationTest {
	private ApplicationContext ac;
	private static final List<Class<?>> functions=new ArrayList<>();
	static{
		functions.add(LoginFunction.class);

	}
	@Before
	public void init(){
		LoggerUtil.init(); 
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
	}
	@Test
	public void test1(){
		ApplicationContextProxy proxy=ac.getBean(ApplicationContextProxy.class);
		UserAddFunction addUser=proxy.getBean(UserAddFunction.class);
		Map<String, Object> args=new HashMap<>();
		args.put("phone", "10086");
		args.put("roleName", proxy.getBean(RoleDao.class).selectRoleByUniqueIndexOnRoleName("salesMan").getRoleName());
		addUser.excuted(args);
	}
	
	@Test
	public void test2() throws ClassNotFoundException{
		UserDao user=ac.getBean(UserDao.class);
		User user2=new User();
		user2.setPhone("10086");
		System.out.println(user.selectUserByClass(user2).get(0).get("password").getValue());
	}
	
	@Test 
	public void test5(){
		RoleAddFunction fun=ac.getBean(RoleAddFunction.class);
		Map<String, Object> args=new HashMap<>();
		args.put("name", "salesMan");
		List<String> list=new ArrayList<>();
		list.add(LoginFunction.class.getName());
//		Map<String, Function> funs=ac.getBeansOfType(Function.class);
//		for(Entry<String, Function> each:funs.entrySet())
//			list.add(each.getValue().getClass().getName());
		args.put("authority", list);
		fun.excuted(args);
	}
	
	@Test
	public void test3(){
		RedisCacheServiceInterface redis=ac.getBean(RedisCacheServiceInterface.class);
		redis.set(ConstParam.PROJECT_NAME+"10086","123456",10000);
		LoginFunction login=ac.getBean(LoginFunction.class);
		Map<String, Object> args=new HashMap<>();
		args.put("phone", "10086");
		args.put("password", "123456");
		args.put("validateCode", "123456");
		login.excuted(args);
	}
	
	@Test
	public void test10(){
		LoginFunction login=ac.getBean(LoginFunction.class);
		Map<String, Object> args=new HashMap<>();
		args.put("phone", "15068610940");
		args.put("password", "123456");
		args.put("validateCode", "6z0I");
		args.put("ip", "192.168.17.62");
		login.excuted(args);
	}
	
	@Test
	public void test11(){
		SetEncodeUtf8 set=ac.getBean(SetEncodeUtf8.class);
		ac.getBean(TestController.class).login(null, null);
		ac.getBean(TestController.class).getPic(null, null);
		System.out.println(set);
	}
	
	@Test
	public void test4(){
		SqlUtilForDB.makeOne(SqlUtilForDB.getTable(SqlUtilForDB.getConnection(), "merchant"), "", "", false,false);;
	}
	
}
