package com.lanxi.consumeLoan.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lanxi.util.entity.LogFactory;
@Component
@Aspect
@Order(0)
public class SetEncodeUtf8 {
//	@Pointcut("execution(* com.lanxi.consumeLoan.controller.*.*(javax.servlet.http.HttpServletRequest,..))")
//	private void servletRequest1(){}
//	
//	@Pointcut("execution(* com.lanxi.consumeLoan.controller.*.*(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,..))")
//	private void servletRequest2(){}
//	
	@Pointcut("execution(String com.lanxi.consumeLoan.controller.*.*(..))")
	private void anyMethod(){};
	@Pointcut("execution(void com.lanxi.consumeLoan.controller.*.*(..))")
	private void noReturnMethod(){};
	
	@Before("anyMethod() && args(req,res,..)")
	public void setEncode(HttpServletRequest req,HttpServletResponse res){ 
		try {
			LogFactory.info(this,"使用切面为 httpservlet 设置字符集为utf-8 启用jsonp");  
			req.setCharacterEncoding("utf-8");
			res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE"); 
        	res.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间 
        	res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization"); 
        	res.setHeader("Access-Control-Allow-Origin", "*");
   	  		res.setCharacterEncoding("utf-8");
   	  		res.setContentType("text/html;charset=utf-8");
		} catch (Exception e) {
			throw new RuntimeException("aop切入设置字符集时发生异常", e);
		}
	}
	

	@Before("noReturnMethod() && args(req,res,..)")
	public void setEncode2(HttpServletRequest req,HttpServletResponse res){ 
		try {
			LogFactory.info(this,"使用切面2为 httpservlet 设置字符集为utf-8 启用jsonp");  
			req.setCharacterEncoding("utf-8");
			res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE"); 
        	res.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间 
        	res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization"); 
        	res.setHeader("Access-Control-Allow-Origin", "*");
   	  		res.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			throw new RuntimeException("aop切入设置字符集时发生异常", e);
		}
	}
	
}
