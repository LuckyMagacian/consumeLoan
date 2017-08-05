package com.lanxi.consumeLoan.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.basic.UserProxy;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户主页
 */

@Service
public class MerchantHomeFunction extends AbstractFunction {
//	public MerchantHomeFunction() {
//		addAttribute(new Attribute<String>("merchantId", ""));
//		addAttribute(new Attribute<String>("shopkeeperId", ""));
//	}
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

    @SuppressWarnings("unchecked")
	@Override
    public RetMessage excuted(Map<String, Object> args) {
    	String phone=(String) args.get("phone");
    	LogFactory.info(this, "用户["+phone+"]尝试获取商户主页信息");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	Attribute<String> merchantId=(Attribute<String>) user.get("merchantId");
    	if(merchantId==null){
    		LogFactory.info(this, "用户["+phone+"]未绑定商户或不是商户工作人员!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"当前用户未绑定商户或不是商户工作人员！",null);
    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId.getValue());
    	LogFactory.info(this, "用户["+phone+"]获取商户信息["+merchant+"]成功!");
    	List<User> users=dao.selectUserByAttibute(new Attribute<String>("merchantId", merchant.getMerchantId()).toJson());
    	List<UserProxy> proxies=new ArrayList<>();
    	for(User each:users)
    		proxies.add(each.toProxy());
    	LogFactory.info(this, "用户["+phone+"]获取商户人员信息["+proxies+"]成功!");
    	LogFactory.info(this, "用户["+phone+"]获取商户主页信息成功!");
    	Map<String, Object> result=new HashMap<>();
    	result.put("merchantInfo", merchant);
    	result.put("employeeInfo", proxies);
    	Object[] objects=new Object[]{merchant,proxies};
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"获取商户主页信息成功！", objects);
    }
}
