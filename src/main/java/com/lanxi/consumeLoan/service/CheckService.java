package com.lanxi.consumeLoan.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.consumeLoan.consts.StaticParam;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.token.EasyToken;
@Deprecated
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
}
