package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.entity.LogFactory;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 用户增加
 */
@Service
public class UserAddFunction extends AbstractFunction {
    @Override
    public RetMessage successNotice() {
    	LogFactory.info(this, "添加用户成功!");
        return null;
    }

    @Override
    public RetMessage failNotice() {
    	LogFactory.info(this, "添加用户失败!");
        return null;
    }

    @Override
    public RetMessage showInfo() {
        return null;
    }

    @Override
    public RetMessage excuted(Map<String, Object> args) {
    	String roleName=(String) args.get("roleName");
    	Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
    	if(role==null){
    		LogFactory.info(this, "角色["+roleName+"]不存在!");
    		return failNotice();
    	}
    	User user=new User();
    	user.setRoleName(role.getRoleName());
    	user.setPhone(args.get("phone")+"");
    	for(String each:role.getAuthorityObject()){
    		Function fun=(Function) applicationContextProxy.getBean(each);
    		user.addAttribute(fun.getAttributes());
    	}
    	dao.getUserDao().addUser(user);
        return successNotice();
    }
}
