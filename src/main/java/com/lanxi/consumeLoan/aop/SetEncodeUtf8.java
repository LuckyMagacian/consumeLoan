package com.lanxi.consumeLoan.aop;

import java.lang.annotation.Repeatable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import com.lanxi.util.entity.LogFactory;
@Component
@Aspect
public class SetEncodeUtf8 {
//	@Pointcut("execution(* com.lanxi.consumeLoan.controller.*.*(javax.servlet.http.HttpServletRequest,..))")
//	private void servletRequest1(){}
//	
//	@Pointcut("execution(* com.lanxi.consumeLoan.controller.*.*(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,..))")
//	private void servletRequest2(){}
//	
	@Pointcut("execution(* com.lanxi.consumeLoan.controller.*.*(..))")
	private void anyMethod(){};
	
	@Before("anyMethod() && args(req,res)")
	public void setEncode(HttpServletRequest req ,HttpServletResponse res){
		try {
			LogFactory.info(this,"使用切面为 httpservletRequest 设置字符集为utf-8");
			req.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			throw new RuntimeException("aop切入设置字符集时发生异常", e);
		}
	}

	@After("anyMethod()")
	public void after(){
		LogFactory.info(this, "aop 后切!");
	}
}
