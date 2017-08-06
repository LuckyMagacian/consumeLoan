package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.TimeUtil;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

/**
 * Created by yangyuanjian on 2017/7/11.
 * 登录接口
 */
@Service
public class LoginFunction extends AbstractFunction{
//    public LoginFunction(){
//        addAttribute(new Attribute<String>("password","123456"));
//        addAttribute(new Attribute<String>("name", ""));
//        addAttribute(new Attribute<String>("createTime",TimeUtil.getDateTime()));
//        addAttribute(new Attribute<String>("state",ConstParam.USER_STATE_WAIT_CHECK));
//        addAttribute(new Attribute<String>("createBy",""));
//    }

    @Override
    public RetMessage successNotice() {
    	LogFactory.info(this, "登录成功!");
        return null;
    }

    @Override
    public RetMessage failNotice() {
    	LogFactory.info(this, "登录失败!");
        return null;
    }

    @Override
    public RetMessage showInfo() {
        return null;
    }

    @Override
    public RetMessage excuted(Map<String,Object> args) {
        String phone=(String) args.get("phone");
        String password=(String) args.get("password");
        String validateCode=(String) args.get("validateCode");
        if(phone==null||password==null||validateCode==null){
        	LogFactory.info(this, "手机号码["+phone+"]或密码["+password+"]或验证码["+validateCode+"]为空!");
        	return new RetMessage(RetCodeEnum.FAIL.toString(),"请检查输入内容是否为空!",null);
        }
        LogFactory.info(this, "手机号["+phone+"],密码["+password+"],验证码["+validateCode+"]非空校验通过!");
        String ip=(String) args.get("ip");
        //验证码与ip绑定
        String code=redisService.get(ConstParam.FUNCTION_NAME_LOGIN+ip);
        if(!validateCode.toLowerCase().equals(code)){
        	LogFactory.info(this, "验证码错误!输入验证码["+validateCode+"],缓存验证码["+code+"]");
        	return new RetMessage(RetCodeEnum.FAIL.toString(),"图形验证码校验失败!请检查输入!",null);
        }
        User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
        String state=(String) user.get("state").getValue();
        switch (state) {
		case ConstParam.USER_STATE_FREEZE:
			LogFactory.info(this, "用户["+phone+"]已被冻结,无法登录!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "您的帐号已被冻结!", null);
		case ConstParam.USER_STATE_REJECT:
			LogFactory.info(this, "用户["+phone+"]审核不通过,无法登录!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "您的帐号审核尚未通过!", null);
		case ConstParam.USER_STATE_WAIT_CHECK:
			LogFactory.info(this, "用户["+phone+"]还未审核,无法登录!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "您的帐号正在审核中!", null);
//		case ConstParam.USER_STATE_LOGIN:break;
		default:break;
		}
//        if(user==null){
//        	LogFactory.info(this, "用户["+phone+"]不存在!");
//        	return new RetMessage(RetCodeEnum.FAIL.toString(),"用户不存在!请检查帐号是否正确!",null);
//        }
//        LogFactory.info(this, "用户["+phone+"]存在性校验通过!");

        redisService.delete(ConstParam.FUNCTION_NAME_LOGIN+ip);
        LogFactory.info(this, "验证码校验通过!");
        if(!password.equals(user.get("password").getValue())){
        	LogFactory.info(this, "密码错误!输入密码["+password+"],真实密码["+user.get("password").getValue()+"]");
        	return new RetMessage(RetCodeEnum.FAIL.toString(),"密码错误!",null);
        }
        LogFactory.info(this, "用户["+phone+"]密码校验通过!");
        if(ip==null){
        	throw new RuntimeException("user ip can't be null !");
        }  
        redisService.set(ConstParam.USER_STATE_LOGIN+phone, ip);  
        LogFactory.info(this, "用户["+phone+"]ip锁定["+ip+"]!"); 
        LogFactory.info(this, "用户["+phone+user.toProxy()+"]于["+TimeUtil.getPreferDateTime()+"]登录本系统!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"登录成功!",user.toProxy().toUser());
    }
}

