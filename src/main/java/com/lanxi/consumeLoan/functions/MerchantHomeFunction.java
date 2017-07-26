package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.entity.LogFactory;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户主页
 */

@Service
public class MerchantHomeFunction extends AbstractFunction {
	public MerchantHomeFunction() {
		addAttribute(new Attribute<String>("merchantId", ""));
		addAttribute(new Attribute<String>("shopkeeperId", ""));
	}
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
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	if(user==null){
    		LogFactory.info(this, "用户["+phone+"]不存在!");
    		return failNotice();
    	}
    	Attribute<?> merchantId=user.get("merchantId");
    	Attribute<?> shopkeeperId=user.get("shopkeeperId");
    	if(merchantId==null){
    		LogFactory.info(this, "用户["+phone+"]尚未绑定商户!");
    		return failNotice();
    	}
    	if(shopkeeperId==null){
    		LogFactory.info(this, "用户["+phone+"]不是店长!");
    		return failNotice();
    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId((String) merchantId.getValue());
    	String result=JSONObject.toJSONString(merchant);
        return successNotice();
    }
}
