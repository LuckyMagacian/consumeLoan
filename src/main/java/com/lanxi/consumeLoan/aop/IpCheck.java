package com.lanxi.consumeLoan.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.service.CheckService;
import com.lanxi.consumeLoan.service.DaoService;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.HttpUtil;

@Aspect
@Component
@Order(100)
public class IpCheck {
	@Pointcut("execution(public String com.lanxi.consumeLoan.controller.*.*(..))")
	private void anyMethod(){};
	@Resource
	private RedisCacheServiceInterface redisService;
	@Resource
	private CheckService checkService;
	@Resource
	private DaoService dao;
	@Around("anyMethod() && args(req,res)")
	public String ipCheck(ProceedingJoinPoint point,HttpServletRequest req,HttpServletResponse res){
			LogFactory.info(this,"使用切面进行ip和用户登录状态校验!");		
			String ip=HttpUtil.getRealIp(req);
			String phone=req.getParameter("phone");
			String userPhone=req.getParameter("userPhone");
			String pageSize=req.getParameter("pageSize");
			String pageCode=req.getParameter("pageCode");
			LogFactory.info(this, "用户["+phone+"]进行登录校验!");
//			if(phone!=null&&!phone.isEmpty())
//			if(!checkService.isPhone(phone)) {
//    			LogFactory.info(this, "登录手机号码["+phone+"]校验不通过！");
//    			return new RetMessage(RetCodeEnum.FAIL.toString(),"登录手机号码格式校验不通过！",ConstParam.TEST_FLAG?checkService.getPhoneInfo(phone):null).toJson();
//    		}

			if(userPhone!=null&&!userPhone.isEmpty()) {
//				if(!checkService.isPhone(userPhone)) {
//	    			LogFactory.info(this, "手机号码["+userPhone+"]校验不通过！");
//	    			return new RetMessage(RetCodeEnum.FAIL.toString(),"手机号码格式校验不通过！",ConstParam.TEST_FLAG?checkService.getPhoneInfo(userPhone):null).toJson();
//	    		}
			}
			if(pageCode!=null&&!pageCode.isEmpty()) {
				if(pageCode.equals("0"))
					pageCode="1";
			}
			if(pageSize!=null&&!pageSize.isEmpty()) {
				if(pageSize.equals("0"))
					pageSize="1";
			}
			if(phone==null){
				return new RetMessage(RetCodeEnum.FAIL.toString(),"用户为空!",null).toJson();
			}
			
			LogFactory.info(this, "用户["+phone+"]非空校验通过!");
			User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
			if(user==null){
				LogFactory.info(this, "用户["+phone+"]不存在!");
				return new RetMessage(RetCodeEnum.FAIL.toString(),"用户不存在!",null).toJson();
			}
			LogFactory.debug(this, "用户["+phone+"]存在性校验通过!");
			System.out.println("flag 1");
			String cacheIp=redisService.get(ConstParam.USER_STATE_LOGIN+phone);
			if(cacheIp==null){
				LogFactory.info(this, "用户["+phone+"]未登录!");
				return new RetMessage(RetCodeEnum.WARNING.toString(),"用户尚未登录!",null).toJson();
			}
			LogFactory.info(this, "用户["+phone+"]登录ip["+ip+"]缓存ip["+cacheIp+"]!");
//			System.err.println(ConstParam.USER_STATE_LOGIN+phone);
//			System.err.println("cacheIP:"+cacheIp);
			if(!cacheIp.equals(ip)){
				LogFactory.info(this, "用户["+phone+"]登录ip["+ip+"]于缓存ip["+cacheIp+"]不匹配,删除缓存ip["+cacheIp+"],要求用户重新登录!");
				System.err.println("flag 2");
				redisService.delete(ConstParam.USER_STATE_LOGIN+phone);
				return new RetMessage(RetCodeEnum.WARNING.toString(),"用户在其他地点登录!",null).toJson();
			}
			try {
				LogFactory.info(this, "用户["+phone+"]登录校验通过!");
				return (String) point.proceed(new Object[]{req,res});
			}catch (Throwable e) {
				LogFactory.error(this,"用户["+phone+"]登录校验时发生异常!",e);
				return new RetMessage(RetCodeEnum.EXCEPTION.toString(), "用户登录校验时发生异常!", null).toJson();
			}
	}
}
