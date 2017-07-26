package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.User;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户绑定
 */
@Service
public class MerchantBindingFunction extends AbstractFunction {
    @Override
    public RetMessage successNotice() {
        addAttribute(new Attribute<String>("merchantId", ""));
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
    	String merchantId=(String) args.get("merchantId");
    	String phone = (String) args.get("phone");
    	if(!checkService.checkAuthority(phone, this.getClass().getName()))
    		return failNotice();
    	String userJson=(String) args.get("user");
    	User user=JSONObject.parseObject(userJson, User.class);
    	user.set("merchantId", merchantId);
    	dao.getUserDao().updateUserByUniqueIndexOnPhone(user, (String) user.get("phone").getValue());
    	return successNotice();
    }
}
