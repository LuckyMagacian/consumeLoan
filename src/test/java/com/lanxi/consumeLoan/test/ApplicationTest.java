package com.lanxi.consumeLoan.test;

import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.consumeLoan.aop.SetEncodeUtf8;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.controller.TestController;
import com.lanxi.consumeLoan.dao.ApplyDao;
import com.lanxi.consumeLoan.dao.MerchantDao;
import com.lanxi.consumeLoan.dao.RoleDao;
import com.lanxi.consumeLoan.dao.UserDao;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.functions.*;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.SqlUtilForDB;
import com.lanxi.util.utils.TimeUtil;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	
	/**
	 *	修改权限
	 */
	@Test 
	public void test5(){
		DaoService dao=ac.getBean(DaoService.class);
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("admin");
		role.addAuthority(CustomerManagerApplyOrderQueryFunction.class);
		dao.getRoleDao().updateRoleByUniqueIndexOnRoleName(role, role.getRoleName());
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
		ac.getBean(TestController.class).getPic(null, null);
		System.out.println(set);
	}
	

	@Test
    public void testMerchantDeleteFunction(){
	    	MerchantDeleteFunction bean = ac.getBean(MerchantDeleteFunction.class);
			Map<String,Object> map =new HashMap<>();
        	map.put("phone","15068610940");
			map.put("merchant_id","1001");
			RetMessage excuted = bean.excuted(map);
			System.out.println(excuted);
    }

	@Test
	public void testMerchantUnsheleveFunction(){
		MerchantUnsheleveFunction bean = ac.getBean(MerchantUnsheleveFunction.class);
		Map<String,Object> map =new HashMap<>();
		map.put("phone","15068610940");
		map.put("merchant_id","1001");
		RetMessage excuted = bean.excuted(map);
		System.out.println(excuted);
	}

	@Test
	public void testMerchantShelveFunction(){
		MerchantShelveFunction bean = ac.getBean(MerchantShelveFunction.class);
		Map<String,Object> map =new HashMap<>();
		map.put("phone","15068610940");
		map.put("merchant_id","1001");
		RetMessage excuted = bean.excuted(map);
		System.out.println(excuted);
	}

	@Test
	public void tesMerchantModifyFunctionFunction(){
		MerchantModifyFunction bean = ac.getBean(MerchantModifyFunction.class);
		Merchant merchant = new Merchant();
		merchant.setMerchantId("1001");
		merchant.setMerchantAddress("aaa");
		Map<String,Object> map =new HashMap<>();
		map.put("phone","15068610940");
		map.put("merchant",merchant.toJson());
		RetMessage excuted = bean.excuted(map);
		System.out.println(excuted);
	}

	@Test
	public void OverdueRecordFunction(){
		OverdueRecordFunction bean = ac.getBean(OverdueRecordFunction.class);
		Map<String,Object> map =new HashMap<>();
		map.put("phone","15068610940");
		map.put("applyId","1001");
		map.put("breakMoney","100");
		map.put("breakTime","aaa");
		RetMessage excuted = bean.excuted(map);
		System.out.println(excuted);
	}
	@Test
	public void ApplyDao() {
		ApplyDao bean = ac.getBean(ApplyDao.class);
		Apply apply = bean.selectApplyByUniqueIndexOnApplyId("1001");
		System.err.println(apply);
		apply.setBreakTime(TimeUtil.getPreferDateTime());
		apply.setAddress("10000000000");
		apply.setBreakMoney(new BigDecimal(100));
		System.err.println(apply);
		bean.updateApplyByUniqueIndexOnApplyId(apply,apply.getApplyId());
		System.err.println(bean.selectApplyByUniqueIndexOnApplyId(apply.getApplyId()));
	}
	@Test
	public void testMerchantDetailQueryFunction() {
		MerchantDetailQueryFunction bean = ac.getBean(MerchantDetailQueryFunction.class);
		Map<String,Object> map =new HashMap<>();
		map.put("phone","15068610940");
		map.put("merchant_id","1001");
		RetMessage retMessage = bean.excuted(map);
		System.out.println(retMessage);
	}
	@Test
	public void testMerchantQueryFunction(){
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("phone", "15068610940");
		map.put("state", "20");
		MerchantQueryFunction bean = ac.getBean(MerchantQueryFunction.class);
		RetMessage message = bean.excuted(map);
			
		System.out.println(message);
	}
	@Test
	public void testMerchantDao(){
		MerchantDao bean = ac.getBean(MerchantDao.class);
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("start_time", "20170720");
		parm.put("end_time", "20170727");
		List<Merchant> list = bean.selectMerchantByParm(parm);
		System.out.println(list);
		
	}
	@Test
	public void testCustomerManagerApplyOrderQueryFunction(){
		DaoService dao=ac.getBean(DaoService.class);
		Map<String, Object> map = new HashMap<>();
		map.put("state", "01");
		List<Merchant> list = dao.getMerchantDao().selectAdminMerchantByParm(map);
		System.err.println(list);
		
	}
	@Test
	public void make(){
		SqlUtilForDB.makeOne(SqlUtilForDB.getTable(SqlUtilForDB.getConnection(), "apply"), "", "", false, false);
	}
}
