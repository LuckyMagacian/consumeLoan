package com.lanxi.consumeLoan.service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.consts.StaticParam;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.token.EasyToken;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.LoggerUtil.LogLevel;
import com.lanxi.util.utils.OtherUtil.IdAnalyst;
import com.lanxi.util.utils.OtherUtil.PhoneNumAnalyst;

@Service
public class CheckService {
	/**dao服务*/
	@Resource
	private DaoService dao;
	/**redis缓存服务*/
	@Resource
	private RedisCacheServiceInterface redisService;
	/**
	 * 校验用户是否拥有访问对应接口的权限
	 * @param phone 		用户手机号码
	 * @param functionName	接口名称
	 * @return	true 		用户有权访问
	 * 			false 		用户无权访问
	 */
	public Boolean checkAuthority(String phone,String functionName){
		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
		String roleName=user.getRoleName();
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
		List<String> authority=role.getAuthorityObject();
		if(authority!=null)
			return authority.contains(functionName);
		else
			return false;
	}
	/**
	 * 校验用户是否已经登录,且查看ip是否符合
	 * @param phone
	 * @param ip
	 * @return
	 */
	public Boolean checkUserHasLogin(String phone,String ip){
		String key=StaticParam.PROJECT_NAME+phone;
		String value=redisService.get(key);
		if(value==null)
			return false;
		if(!value.equals(ip))
			return false;
		return true;
	}
	
	public EasyToken analysisUserToken(String tokenStr){
		EasyToken token=EasyToken.verifyTokenRenew(tokenStr);
		return token;
	}
	
	public boolean isPhoneNew(String phone) {
		long start=System.currentTimeMillis();
		try {
			FutureTask<Boolean> future=new FutureTask<>(()->{
				if(!phone.matches("1[0-9]{10}"))
					return false;
				PhoneNumAnalyst analyst=new PhoneNumAnalyst(phone);
				LogFactory.debug(this, analyst.getPhoneInfo());
				if(analyst.getAddress()==null||analyst.getCompany()==null)
					return false;
				return true;
			});
			new Thread(future).start();
			return future.get(1000,TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			LogFactory.debug(this,"手机号码校验时发生异常", e);
			return isPhoneNew(phone);
		}finally {
			System.out.println(System.currentTimeMillis()-start);
		}
	}
	
	public boolean isPhone(String phone) {
		
		
		try {
			if(!phone.matches("1[0-9]{10}"))
				return false;
			PhoneNumAnalyst analyst=new PhoneNumAnalyst(phone);
			LogFactory.debug(this, analyst.getPhoneInfo());
			if(analyst.getAddress()==null||analyst.getCompany()==null)
				return false;
			return true;
		} catch (Exception e) {
			LogFactory.info(this,"手机号码校验时发生异常", e);
			return true;
		}
	}
	
	public String getPhoneInfo(String phone) {
		try {
			if(!phone.matches("1[0-9]{10}"))
				return "手机号码不是11位数字！";
			PhoneNumAnalyst analyst=new PhoneNumAnalyst(phone);
			return analyst.getPhoneInfo();
		} catch (Exception e) {
			LogFactory.info(this,"手机号码校验时发生异常", e);
			return null;
		}
	}
	
	public boolean isId(String idNum) {
		if(!ConstParam.TEST_FLAG)
			throw new IllegalArgumentException("非测试模式下，当前方法不可用！");
		try {
			if(!idNum.matches("[0-9]{17}[0-9xX]{1}"))
				return false;
			IdAnalyst analyst=new IdAnalyst(idNum);
			LogFactory.debug(this, analyst.getIdInfo());
			return analyst.validateId();
		} catch (Exception e) {
			LogFactory.info(this,"身份证校验时发生异常", e);
			return true;
		}
	}
	
	public String getIdInfo(String idNum) {
		if(!ConstParam.TEST_FLAG)
			throw new IllegalArgumentException("非测试模式下，当前方法不可用！");
		try {
			if(!idNum.matches("([0-9]{15})||([0-9]{17}[0-9xX]{1})")) 
				return "身份证号码不符合15位或18位！";
			IdAnalyst analyst=new IdAnalyst(idNum);
			return analyst.getIdInfo();
		} catch (Exception e) {
			LogFactory.info(this,"身份证校验时发生异常", e);
			return null;
		}
	}
	
	@Test
	public void test2() {
		LoggerUtil.setLogLevel(LogLevel.DEBUG);
		LoggerUtil.init();
		System.out.println(isPhone("18058909090"));
		System.out.println(isId("440203197510313925"));
	}
}
