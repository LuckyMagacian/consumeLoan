package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 登出接口
 */
@Service
public class LogoutFunction extends AbstractFunction {
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
    	LogFactory.info(this, "用户["+phone+"]退出登录!");
    	if(redisService.get(ConstParam.USER_STATE_LOGIN+phone)!=null) {
    		LogFactory.info(this, "删除用户["+phone+"]登录缓存!");
    		redisService.delete(ConstParam.USER_STATE_LOGIN+phone);
    	}else {
    		LogFactory.info(this, "用户["+phone+"]尚未登录,无法登出!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(), "尚未登录，无法退出!", null);
    	}
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"登出成功!", null);
    }
}
