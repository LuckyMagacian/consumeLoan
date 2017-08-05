package com.lanxi.consumeLoan.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.consumeLoan.aop.SetEncodeUtf8;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.Function;
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
import com.lanxi.consumeLoan.functions.CustomerManagerApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.MerchantDeleteFunction;
import com.lanxi.consumeLoan.functions.MerchantDetailQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantModifyFunction;
import com.lanxi.consumeLoan.functions.MerchantQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantShelveFunction;
import com.lanxi.consumeLoan.functions.MerchantUnsheleveFunction;
import com.lanxi.consumeLoan.functions.OverdueRecordFunction;
import com.lanxi.consumeLoan.functions.UserAddFunction;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.consumeLoan.manager.UserManager;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.util.utils.ExcelUtil;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.SqlUtilForDB;
import com.lanxi.util.utils.TimeUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;


public class ApplicationTest {
	private ApplicationContext ac;
	private DaoService dao;
	private static final List<Class<?>> functions=new ArrayList<>();
	static{
		functions.add(LoginFunction.class);

	}
	@Before
	public void init(){
		LoggerUtil.init(); 
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
		dao=ac.getBean(DaoService.class);
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
		user2.setPhone("0");
		user2.setRoleName("root");
		UserManager manager=ac.getBean(UserManager.class);
		manager.addAttributesForUser(user2);
		dao.getUserDao().addUser(user2);
	}
	
	@Test 
	public void test5(){
//		DaoService dao=ac.getBean(DaoService.class);
//		Role role=new Role();
//		Map<String, Function> map=ac.getBeansOfType(Function.class);
//		for(Entry<String, Function> each:map.entrySet())
//			role.addAuthority(each.getValue());
//		role.setRoleName("root");
//		dao.getRoleDao().addRole(role);
		
//		List<Role> roles=dao.getRoleDao().selectRoleByClass(new Role());
//		for(Role each:roles){
//			each.addAuthority(AdminMerchantAddFunction.class);
//			dao.getRoleDao().updateRoleByUniqueIndexOnRoleName(each, each.getRoleName());
//		}
		
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("root");
		Map<String, Function> map=ac.getBeansOfType(Function.class);
		for(Entry<String, Function> each:map.entrySet()){
			if(role.getAuthorityObject().contains(each.getValue().getClass().getName()))
				continue;
			else
				role.addAuthority(each.getValue().getClass());
		}
		dao.getRoleDao().updateRoleByUniqueIndexOnRoleName(role, role.getRoleName());
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
	public void test65(){
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
	public void test4(){
		SqlUtilForDB.makeOne(SqlUtilForDB.getTable(SqlUtilForDB.getConnection(), "merchant"), "", null, false, false);
	}
	
	@Test
	public void test55(){
		DaoService dao=ac.getBean(DaoService.class);
		Apply app=(dao.getApplyDao().selectApplyByUniqueIndexOnApplyId("1001"));
		app.setBreakTime(TimeUtil.getPreferDateTime());
		dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(app, app.getApplyId());
		System.out.println((dao.getApplyDao().selectApplyByUniqueIndexOnApplyId("1001")));
	}
	
	@Test
	public void test56(){
		DaoService dao=ac.getBean(DaoService.class);
		List<User> users=dao.selectUserByAttibute(new Attribute<String>("merchantId", "1001").toJson());
		System.out.println(users);
	}
	@Test
	public void test57(){
		RedisCacheServiceInterface redis=ac.getBean(RedisCacheServiceInterface.class);
		String result=redis.set(ConstParam.FUNCTION_NAME_APPLY_ADD+"15068610940","123456",1*60*1000L);
		System.out.println(result);
		System.out.println(redis.get(ConstParam.FUNCTION_NAME_APPLY_ADD+"15068610940"));
	}
	
	@Test
	public void test58() throws IOException{
		DaoService dao=ac.getBean(DaoService.class);
		List<Apply> applies=dao.getApplyDao().selectApplyByClass(new Apply()); 
		Map<String, String> map=new HashMap<>();
		map.put("applyId", "申请编号");
		map.put("name", "申请者姓名");
		map.put("sex", "申请者性别");
		map.put("address", "申请者地址");
		map.put("idNumber", "申请者身份证号码");
		map.put("phone", "申请者手机号码");
//		map.put("verifyCode", "申请时所填验证码");
		map.put("merchantId", "申请商户编号");
		map.put("salesManPhone", "申请商户销售员手机号");
		map.put("applyTime", "申请时间");
		map.put("loanTime", "放款时间");
		map.put("loanMoney", "放款金额");
		map.put("isOverdue", "是否逾期");
		map.put("overdueMoney", "逾期金额");
		map.put("state", "申请状态");
		map.put("brokerageRate", "申请时佣金比例");
		map.put("sharedRate", "申请时分润比例");
		map.put("depositeRate", "申请时保证金比例");
		map.put("breakTime", "违约时间");
		map.put("breakMoney", "违约金额");
		map.put("merchantName", "申请商户名称");
		File file=ExcelUtil.exportExcelFile(applies,map);
		if(!file.exists())
			file.createNewFile();
	}
	
	@Test
	public void test59(){
		List<User> users=dao.getUserDao().selectUserByClass(new User());
		for(User each:users)
			each.setAttributesObject(null);;
		System.out.println(JSONObject.toJSON(users));
	}

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
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("phone", "15068610940");
		map.put("start", 0);
		map.put("size", 2);
		List<Apply> list = bean.selectApplyByPage(map);
		System.err.println(list);
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
		parm.put("start", 0);
		parm.put("size", 2);
		List<Merchant> list = bean.selectMerchantByPage(parm);
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
		SqlUtilForDB.makeOne(SqlUtilForDB.getTable(SqlUtilForDB.getConnection(), "system_account"), "", "", false, false);
	}
    @Test
	public void  userTest(){
		Attribute<String> attribute =new Attribute<String>("state", ConstParam.USER_STATE_WAIT_CHECK);
		String json = attribute.toJson();
		System.err.println(json);
    	List<User> list = dao.getUserDao().selectUserByAttibute(json);
    	System.out.println(list);
    	User user = list.get(0);
    	String attributes = user.getAttributes();
    	System.out.println(attributes);
    	
    	
    	String json2 = new Attribute<String>("state", ConstParam.USER_STATE_NORMAL).toJson();
    	if (attributes.contains(json)) {
			attributes = attributes.replace(json, json2);
			System.err.println(attributes);
			user.setAttributes(attributes);
			System.err.println(user);
		}
    	
    	
	}
    @Test
	public void testsss(){
    	User user=new User();
    	user.addAttribute(new Attribute<String>("merchantName", "善解人衣"));
//    	user.addAttribute(new Attribute<String>("state","110"));
    	System.err.println(dao.selectUserByClassLike(user));
    }
	
}
