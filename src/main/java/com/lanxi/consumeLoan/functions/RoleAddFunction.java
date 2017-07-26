package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.util.entity.LogFactory;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 *角色增加
 */
@Service
public class RoleAddFunction extends AbstractFunction{
    @Override
    public RetMessage successNotice() {
    	LogFactory.info(this, "添加角色成功!");
        return null;
    }

    @Override
    public RetMessage failNotice() {
    	LogFactory.info(this, "添加角色失败!");
        return null;
    }

    @Override
    public RetMessage showInfo() {
        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public RetMessage excuted(Map<String, Object> args) {
        String name = (String) args.get("name");
        List<String> authority=(List<String>) args.get("authority");
        Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(name);
        if(role!=null){
        	LogFactory.info(this, "角色名["+name+"]已存在!");
        	return failNotice();
        }
        role=new Role();
        role.setRoleName(name);
        role.setAuthorityObject(authority);
        dao.getRoleDao().addRole(role);
        return successNotice();
    }
}
