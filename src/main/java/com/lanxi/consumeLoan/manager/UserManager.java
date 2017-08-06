package com.lanxi.consumeLoan.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.consumeLoan.consts.StaticParam;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.SystemAccount;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.consumeLoan.service.DaoService;
/**
 * 用户管理类
 * 用于操作用户对象
 * @author yangyuanjian
 *
 */
@Service
public class UserManager {
	/**dao服务*/
	@Resource
	private DaoService dao;
	/**spring 上下文*/
	@Resource
	private ApplicationContextProxy ac;
	/**
	 * 为用户添加属性
	 * @param user 用户
	 * @return 用户
	 */
	public User addAttributesForUser(User user){
		String roleName=user.getRoleName();
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
		List<String> authority=role.getAuthorityObject();
		for(String each:authority){
			Function fun=(Function) ac.getBean(each);
			user.addAttribute(fun.getAttributes());
		}
		return user;
	}
}
