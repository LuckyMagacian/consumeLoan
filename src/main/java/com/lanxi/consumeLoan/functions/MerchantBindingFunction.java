package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.dao.UserDao;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

import sun.util.logging.resources.logging;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户绑定
 */
@Deprecated
@Service
public class MerchantBindingFunction extends AbstractFunction {
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
    	String phone = (String) args.get("phone");
    	LogFactory.info(this, "用户["+phone+"]进行用户商户绑定操作!");
    	String merchantId=(String) args.get("merchantId");
    	String userPhone=(String) args.get("userPhone");
    	if(merchantId==null||userPhone==null){
    		LogFactory.info(this, "商户编号["+merchantId+"]或用户手机号[]为空!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"商户编号["+merchantId+"]或用户手机号[]为空！",null);
    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId);
    	if(merchant==null){
    		LogFactory.info(this, "待绑定商户["+merchantId+"]不存在!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"待绑定商户不存在！",null);
    	}
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
    	if(user==null){
    		LogFactory.info(this, "用户不存在,新增用户!");
    		String roleName=(String) args.get("roleName");
    		String name=(String) args.get("userName");
    		user=new User();
    		user.setPhone(userPhone);
    		user.setRoleName(roleName);
    		userManager.addAttributesForUser(user);
    		user.set("name", name);
    		user.set("merchantId",merchantId);
    		dao.getUserDao().addUser(user);
    		LogFactory.info(this, "新增用户["+user+"],且用户["+userPhone+"]绑定商户["+merchantId+"]成功!");
    		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"新增用户,且用户绑定商户成功!",null);
    	}else{
    		LogFactory.info(this, "用户已存在!");
    		if(user.get("merchantId")==null){
    			return new RetMessage(RetCodeEnum.FAIL.toString(),"用户不是商户人员,无法绑定商户!",null);
    		}
    		user.set("merchantId", merchantId);
    		dao.getUserDao().updateUserByUniqueIndexOnPhone(user, user.getPhone());
    		LogFactory.info(this, "用户["+userPhone+"]绑定商户["+merchantId+"]成功!");
    		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"用户绑定商户成功!",null);
    	}
    }
}
