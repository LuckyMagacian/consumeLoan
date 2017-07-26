package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;

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
    	if(redisService.get(ConstParam.USER_STATE_LOGIN+phone)!=null)
    		redisService.delete(ConstParam.USER_STATE_LOGIN+phone);
    	else
    		return failNotice();
        return successNotice();
    }
}
