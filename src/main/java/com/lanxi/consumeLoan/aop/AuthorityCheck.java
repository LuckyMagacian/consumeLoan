package com.lanxi.consumeLoan.aop;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

@Component
@Aspect
public class AuthorityCheck {
	@Resource
	private DaoService dao;
	@Pointcut("execution(* com.lanxi.consumeLoan.functions.*.excuted(..))")
	private void anyMethod(){};
	
	@Before("anyMethod() && args(args)")
	public RetMessage checkAuthority(Map<String, Object> args){
		LogFactory.info(this, "使用切面校验权限!");
		String targetName=AopContext.currentProxy().getClass().getName().replace("class", "").trim();
		System.out.println(targetName.substring(0, targetName.indexOf('$')));
		String phone=(String) args.get("phone"); 
		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
		String roleName=user.getRoleName();
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
		List<String> authority=role.getAuthorityObject();
		return new RetMessage(RetCodeEnum.FAIL.toString(),"无权访问!",null);
	}
}
