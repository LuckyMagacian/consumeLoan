package com.lanxi.consumeLoan.aop;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

@Component
@Aspect
@Order(10)
public class AuthorityCheck {
	@Resource
	private DaoService dao;
	@Pointcut("execution(* com.lanxi.consumeLoan.functions.*.excuted(..))")
	private void anyMethod(){};
	
	@Around("anyMethod() && args(args)")
	public RetMessage checkAuthority(ProceedingJoinPoint point,Map<String, Object> args){
		LogFactory.info(this, "使用切面校验权限!");
		String targetName=AopContext.currentProxy().getClass().getName().replace("class", "").trim();
		targetName=targetName.substring(0, targetName.indexOf('$'));
		String phone=(String) args.get("phone");  
		if(!targetName.equals("com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction")){
			if(phone==null){
				LogFactory.info(this, "权限校验时,用户["+phone+"]为null");
				return new RetMessage(RetCodeEnum.FAIL.toString(), "用户手机号为空!请检查输入!", null);
			}
			LogFactory.info(this, "用户["+phone+"]尝试访问["+targetName+"]接口");
			User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
			if(user==null){
				LogFactory.info(this, "用户["+phone+"]不存在!");
				return new RetMessage(RetCodeEnum.FAIL.toString(), "用户不存在!", null);
			}
			LogFactory.info(this, "用户["+phone+"]存在性校验通过!");
			String roleName=user.getRoleName();
			Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
			List<String> authority=role.getAuthorityObject();	
			if(!authority.contains(targetName)){
				LogFactory.info(this, "用户["+phone+"]无权访问["+targetName+"]接口!");
				return new RetMessage(RetCodeEnum.FAIL.toString(),"用户["+phone+"]无权访问["+targetName+"]接口!",null);
			}
			LogFactory.info(this, "用户["+phone+"]接口["+targetName+"]权限校验通过!");
		}
		try {
			return (RetMessage) point.proceed(new Object[]{args});
		} catch (Throwable e) {
			LogFactory.error(this,"用户["+phone+"]接口["+targetName+"]权限校验时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(), "权限校验时发生异常!", null);
		}
	}
}
