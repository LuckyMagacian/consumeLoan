package com.lanxi.consumeLoan.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.consumeLoan.entity.Role;
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

public class ApplicationTest2 {
	private ApplicationContext ac;
	private DaoService dao;
	@Before
	public void init(){
		LoggerUtil.init(); 
		ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
		dao=ac.getBean(DaoService.class);
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
		
	}
	@Test
	public void addMerchant(){
		
	}
	@Test
	public void addApply(){
		
	}
	
	
}
