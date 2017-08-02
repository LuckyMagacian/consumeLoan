package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.TimeUtil;

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
    	String oldPassword=(String) args.get("oldPassword");
    	String newPassword=(String) args.get("newPassword");
    	String passwordRepeat=(String) args.get("passwordRepeat");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
//    	if(user==null){    		
//    		LogFactory.info(this, "用户["+phone+"]不存在");
//    		return failNotice();
//    	}
    	if(newPassword==null||passwordRepeat==null||oldPassword==null){
    		LogFactory.info(this, "用户["+phone+"]新密码["+newPassword+"]或重复密码["+passwordRepeat+"]或旧密码["+oldPassword+"]为空");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"新密码或重复密码或旧密码为空,请检查输入!",null);
    	}
    	if(!oldPassword.equals(user.get("password").getValue())){
    		LogFactory.info(this, "用户["+phone+"]旧密码["+oldPassword+"]错误!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"旧密码错误,请检查输入!",null);
    	}
    	if(newPassword==null||passwordRepeat==null){
    		LogFactory.info(this, "新密码["+newPassword+"]或重复密码["+passwordRepeat+"]为空");
    		return failNotice();
    	}
    	if(!newPassword.equals(passwordRepeat)){
    		LogFactory.info(this, "用户["+phone+"]新密码["+newPassword+"]重复密码["+passwordRepeat+"]不一致");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"新密码与重复密码不一致,请检查输入!",null);
    	}
    	LogFactory.info(this, "用户["+phone+"]于["+TimeUtil.getPreferDateTime()+"]修改密码["+user.get("password").getValue()+"]为["+newPassword+"]!");
    	user.set("password", newPassword);
    	dao.getUserDao().updateUserByUniqueIndexOnPhone(user, phone);
    	return new RetMessage(RetCodeEnum.SUCCESS.toString(),"密码修改成功!",null);
    }
}
