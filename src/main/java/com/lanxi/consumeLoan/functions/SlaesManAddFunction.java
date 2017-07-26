package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 销售员增加
 */
@Service
public class SlaesManAddFunction extends AbstractFunction{
    @Override
    public RetMessage successNotice() {
        return null;
    }

    @Override
    public RetMessage failNotice() {
        return null;
    }

    @Override
    public RetMessage showInfo() {
        return null;
    }

    @Override
    public RetMessage excuted(Map<String, Object> args) {
    	String phone=(String) args.get("phone");
    	if(!checkService.checkAuthority(phone, this.getClass().getName())){
    		return failNotice();
    	}
    	String userPhone=(String) args.get("userPhone");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	if(user!=null){
    		return failNotice();
    	}
    	Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("salesMan");
    	if(role==null){
    		return failNotice();
    	}
    	user=new User();
    	user.setRoleName(role.getRoleName());
    	userManager.addAttributesForUser(user);
    	dao.getUserDao().addUser(user);
        return successNotice();
    }
}
