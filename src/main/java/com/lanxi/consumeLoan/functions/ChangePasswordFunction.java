package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.entity.LogFactory;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 */
@Service
public class ChangePasswordFunction extends AbstractFunction {
    @Override
    public RetMessage successNotice() {
    	LogFactory.info(this, "修改密码成功!");
        return null;
    }

    @Override
    public RetMessage failNotice() {
    	LogFactory.info(this, "修改密码失败!");
        return null;
    }

    @Override
    public RetMessage showInfo() {
    	
        return null;
    }

    @Override
    public RetMessage excuted(Map<String, Object> args) {
    	String phone=(String) args.get("phone");
    	String newPassword=(String) args.get("newPassword");
    	String passwordRepeat=(String) args.get("passwordRepeat");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	if(user==null){    		
    		LogFactory.info(this, "用户["+phone+"]不存在");
    		return failNotice();
    	}
    	if(newPassword==null||passwordRepeat==null){
    		LogFactory.info(this, "新密码["+newPassword+"]或重复密码["+passwordRepeat+"]为空");
    		return failNotice();
    	}
    	if(!newPassword.equals(passwordRepeat)){
    		LogFactory.info(this, "新密码["+newPassword+"]重复密码["+passwordRepeat+"]不一致");
    		return failNotice();
    	}
    	user.set("password", newPassword);
    	dao.getUserDao().updateUserByUniqueIndexOnPhone(user, phone);
    	return successNotice();
    }
}
