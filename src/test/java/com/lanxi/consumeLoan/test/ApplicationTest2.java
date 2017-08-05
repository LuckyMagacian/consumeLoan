package com.lanxi.consumeLoan.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.functions.AdminApplyQueryFunction;
import com.lanxi.consumeLoan.functions.AdminChargeQueryFunction;
import com.lanxi.consumeLoan.functions.AdminMerchantAddFunction;
import com.lanxi.consumeLoan.functions.AdminMerchantQueryFunction;
import com.lanxi.consumeLoan.functions.AdminUserAddFunction;
import com.lanxi.consumeLoan.functions.AdminUserCheckFunction;
import com.lanxi.consumeLoan.functions.AdminUserQueryFunction;
import com.lanxi.consumeLoan.functions.ApplyOrderAddFunction;
import com.lanxi.consumeLoan.functions.AttributeAddForAllUserFunction;
import com.lanxi.consumeLoan.functions.AttributeAddForCustomerManagerFunction;
import com.lanxi.consumeLoan.functions.AttributeAddForSalesManFunction;
import com.lanxi.consumeLoan.functions.AttributeAddForShopKeeperFunction;
import com.lanxi.consumeLoan.functions.ChangePasswordFunction;
import com.lanxi.consumeLoan.functions.CustomerManagerApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.CustomerManagerUserQueryFunction;
import com.lanxi.consumeLoan.functions.CustomerShopEmployeeAddFunction;
import com.lanxi.consumeLoan.functions.LoanFunction;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.LogoutFunction;
import com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction;
import com.lanxi.consumeLoan.functions.MerchantAddFunction;
import com.lanxi.consumeLoan.functions.MerchantApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantBindingFunction;
import com.lanxi.consumeLoan.functions.MerchantDeleteFunction;
import com.lanxi.consumeLoan.functions.MerchantDetailQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantHomeFunction;
import com.lanxi.consumeLoan.functions.MerchantModifyFunction;
import com.lanxi.consumeLoan.functions.MerchantQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantShelveFunction;
import com.lanxi.consumeLoan.functions.MerchantUnsheleveFunction;
import com.lanxi.consumeLoan.functions.OverdueRecordFunction;
import com.lanxi.consumeLoan.functions.RejectFunction;
import com.lanxi.consumeLoan.functions.RoleAddFunction;
import com.lanxi.consumeLoan.functions.SalesManHomeFunction;
import com.lanxi.consumeLoan.functions.SlaesManAddFunction;
import com.lanxi.consumeLoan.functions.SystemAccountQueryFunction;
import com.lanxi.consumeLoan.functions.SystemHomeFunction;
import com.lanxi.consumeLoan.functions.UserAddFunction;
import com.lanxi.consumeLoan.functions.ValidateCodeSendFunction;
import com.lanxi.consumeLoan.manager.UserManager;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.TimeUtil;

public class ApplicationTest2 {
	private ApplicationContext ac;
	private DaoService dao;
	private UserManager manager;
	@Before
	public void init(){
		LoggerUtil.init(); 
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
		dao=ac.getBean(DaoService.class);
		manager=ac.getBean(UserManager.class);
	}
	
	@Test
	public void AddRole(){
		//添加通用权限接口
		List<Class<? extends Function>> common=new ArrayList<>();
		common.add(LoginFunction.class);
		common.add(AttributeAddForAllUserFunction.class);
		common.add(ChangePasswordFunction.class);
		common.add(LoginFunction.class);
		common.add(LogoutFunction.class);
		common.add(MakeValidateCodePicFunction.class);
		common.add(ValidateCodeSendFunction.class);
		//添加销售员权限接口
		List<Class<? extends Function>> salesMan=new ArrayList<>();
		salesMan.add(ApplyOrderAddFunction.class);
		salesMan.add(AttributeAddForSalesManFunction.class);
		salesMan.add(SalesManHomeFunction.class);
		salesMan.addAll(common);
		//添加店长（店铺负责人）权限接口
		List<Class<? extends Function>> shopKeeper=new ArrayList<>();
		shopKeeper.add(ApplyOrderAddFunction.class);
		shopKeeper.add(AttributeAddForShopKeeperFunction.class);
		shopKeeper.add(MerchantApplyOrderQueryFunction.class);
		shopKeeper.add(MerchantHomeFunction.class);
		shopKeeper.addAll(common);
		//添加客户经理权限接口
		List<Class<? extends Function>> customerManager=new ArrayList<>();
		customerManager.add(AttributeAddForCustomerManagerFunction.class);
		customerManager.add(CustomerManagerApplyOrderQueryFunction.class);
		customerManager.add(CustomerManagerUserQueryFunction.class);
		customerManager.add(CustomerShopEmployeeAddFunction.class);
		customerManager.add(MerchantBindingFunction.class);
		//TODO 
		customerManager.add(MerchantDetailQueryFunction.class);
		customerManager.add(SlaesManAddFunction.class);
		customerManager.add(MerchantQueryFunction.class);
		customerManager.addAll(common);
		//添加管理员权限接口
		List<Class<? extends Function>> admin=new ArrayList<>();
		admin.add(AdminApplyQueryFunction.class);
		admin.add(AdminChargeQueryFunction.class);
		admin.add(AdminMerchantQueryFunction.class);
		admin.add(AdminMerchantAddFunction.class);
		admin.add(AdminUserAddFunction.class);
		admin.add(AdminUserCheckFunction.class);		
		admin.add(AdminUserQueryFunction.class);
		admin.add(LoanFunction.class);
		admin.add(MerchantAddFunction.class);
		admin.add(MerchantDeleteFunction.class);
		admin.add(MerchantDetailQueryFunction.class);
		admin.add(MerchantModifyFunction.class);
		admin.add(MerchantQueryFunction.class);
		admin.add(MerchantShelveFunction.class);
		admin.add(MerchantUnsheleveFunction.class);
		admin.add(OverdueRecordFunction.class);
		admin.add(RejectFunction.class);
		admin.add(RoleAddFunction.class);
		admin.add(SystemAccountQueryFunction.class);
		admin.add(SystemHomeFunction.class);
		admin.add(UserAddFunction.class);
		admin.addAll(common);
		//添加root权限接口
		List<Function> root=new ArrayList<>();
		Map<String, Function> funs=ac.getBeansOfType(Function.class);
		root.addAll(new ArrayList<Function>(funs.values()));
		
		//创建销售员角色
		Role role=new Role();
		role.setRoleName("salesMan");
		for(Class<? extends Function> each:salesMan)
			role.addAuthority(each);
		dao.getRoleDao().addRole(role);
		role.setAuthorityObject(new ArrayList<String>());
		//创建店长（店铺负责人）角色
		role.setRoleName("shopKeeper");
		for(Class<? extends Function> each:shopKeeper)
			role.addAuthority(each);
		dao.getRoleDao().addRole(role);
		role.setAuthorityObject(new ArrayList<String>());
		//创建客户经理角色
		role.setRoleName("customerManager");
		for(Class<? extends Function> each:customerManager)
			role.addAuthority(each);
		dao.getRoleDao().addRole(role);
		role.setAuthorityObject(new ArrayList<String>());
		//创建管理员角色
		role.setRoleName("admin");
		for(Class<? extends Function> each:admin)
			role.addAuthority(each);
		dao.getRoleDao().addRole(role);
		role.setAuthorityObject(new ArrayList<String>());
		//创建root角色
		role.setRoleName("root");
		role.addAuthority(root);
		dao.getRoleDao().addRole(role);
		role.setAuthorityObject(new ArrayList<String>());
	}
	
	@Test
	public void addUser(){
		User user=new User();
//销售员属性
//		user.setRoleName("salesMan");
//		manager.addAttributesForUser(user);
//		user.setPhone("1003");
//		user.set("phone", "1003");
//		user.set("salesManId", "1003");
//		user.set("name", "测试帐号销售员1003");
//		user.set("merchantId", "1002");
//		user.set("merchantName", "测试商户1002");
//		user.set("createBy", "yyj");
//		dao.getUserDao().addUser(user);
//店长(商户负责人)属性		
//		user.setRoleName("shopKeeper");
//		manager.addAttributesForUser(user);
//		user.setPhone("2003");
//		user.set("shopKeeperId", "2003");
//		user.set("phone", "2003");
//		user.set("name", "测试帐号店长2003");
//		user.set("merchantId", "1002");
//		user.set("merchantName", "测试商户1002");
//		user.set("createBy", "yyj");
//		dao.getUserDao().addUser(user);
//客户经理属性
//		user.setRoleName("customerManager");
//		manager.addAttributesForUser(user);
//		user.setPhone("3003");
//		user.set("phone", "3003");
//		user.set("customerManagerId", "3003");
//		user.set("netAddress", "测试地址3003");
//		user.set("name", "测试帐号客户经理3003");
//		user.set("createBy", "yyj");
//		dao.getUserDao().addUser(user);
//管理员属性
//		user.setRoleName("admin");
//		manager.addAttributesForUser(user);
//		user.setPhone("4003");
//		user.set("phone", "4003");
//		user.set("name", "测试帐号管理员4003");
//		user.set("createBy", "yyj");
//		dao.getUserDao().addUser(user);
//root属性
		user.setRoleName("root");
		manager.addAttributesForUser(user);
		user.setPhone("5003");
		user.set("phone", "5003");
		user.set("adminId", "5003");
		user.set("salesManId", "5003");
		user.set("shopKeeperId", "5003");
		user.set("customerManagerId", "5003");
		user.set("name", "测试帐号root5003");
		user.set("merchantId", "1003");
		user.set("merchantName", "测试商户1003");
		user.set("netAddress", "测试地址5003");
		user.set("createBy", "yyj");
		dao.getUserDao().addUser(user);
//		System.err.println(user.toProxy().toUser());
//		System.err.println(user.toProxy().toAdmin());
//		System.err.println(user.toProxy().toShopKeeper());
//		System.err.println(user.toProxy().toSalesMan());
//		System.err.println(user.toProxy().toCustomerManager());
	}
	@Test
	public void addMerchant(){
		Merchant merchant=new Merchant();
		merchant.setMerchantId("1003");
		merchant.setMerchantName("测试商户1003");
		merchant.setMerchantType("1003");
		merchant.setIsAssurance("true");
		merchant.setIsShared("true");
		merchant.setMerchantAddress("测试地址1003");
		merchant.setPartnerTime(TimeUtil.getDateTime());
		merchant.setProvideDeposit("false");
		merchant.setSharedRate(new BigDecimal("0.1"));
		merchant.setState(ConstParam.MERCHANT_STATE_WAIT_CHECK);
		merchant.setStopTime("");
		
		merchant.setCustomerManagerName("测试帐号客户经理3003");
		merchant.setCustomerManagerPhone("3003");
		
		merchant.setApplyAmount(0);
		merchant.setApplyMoneyAmount(new BigDecimal("0.00000"));
		
		merchant.setBreakAmount(0);
		merchant.setBreakMoneyAmount(new BigDecimal("0.00000"));
		
		merchant.setBrokerageAmount(new BigDecimal("0.00000"));
		merchant.setBrokerageLess(new BigDecimal("0.00000"));
		merchant.setBrokerageRate(new BigDecimal("0.2"));
		
		merchant.setDepositeAmount(new BigDecimal("0.00000"));
		merchant.setDepositeBalance(new BigDecimal("0.00000"));
		merchant.setDepositeRate(new BigDecimal("0.2"));
		
		merchant.setLoanAmount(0);
		merchant.setLoanMoneyAmount(new BigDecimal("0.00000"));
		
		dao.getMerchantDao().addMerchant(merchant);
		System.err.println(merchant);
	}
	@Test
	public void addApply(){
		ApplyOrderAddFunction addFunction=new ApplyOrderAddFunction();
	}
	
	@Test
	public void userQuery(){
		User user=new User();
//		user.setPhone("3001");
		user.setRoleName("salesMan");
		user.addAttribute(new Attribute<String>("state", "01"));
		user.addAttribute(new Attribute<String>("merchantName", "测试商户1001"));
		System.err.println(dao.selectUserByClassLike(user));
	}
	
}
