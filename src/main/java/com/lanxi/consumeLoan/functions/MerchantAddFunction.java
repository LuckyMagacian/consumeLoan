package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 *商户增加
 */
@Service
public class MerchantAddFunction extends AbstractFunction {
	public MerchantAddFunction() {
		addAttribute(new Attribute<String>("customerManagerId", ""));
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

    @SuppressWarnings("unchecked")
	@Override
    public RetMessage excuted(Map<String, Object> args) {
    	String phone=(String) args.get("phone");
    	if(!checkService.checkAuthority(phone, this.getClass().getName()))
    		return failNotice();
    	String merchantJson=(String) args.get("merchant");
    	Merchant merchant=JSONObject.parseObject(merchantJson,Merchant.class);
    	merchant.setMerchantId(TimeUtil.getDate()+TimeUtil.getNanoTime());
    	dao.getMerchantDao().addMerchant(merchant);
    	
    	String shopKeeperJson=(String) args.get("shopKeepers");
    	List<JSONObject> shopKeepers=JSONObject.parseObject(shopKeeperJson, ArrayList.class);
    	Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("shopKeeper");
    	if(role==null){
    		return failNotice();
    	}
    	for(JSONObject each:shopKeepers){
    		String userPhone=each.getString("phone");
    		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
    		if(user!=null)
    			return failNotice();
    		user=new User();
    		user.setRoleName(role.getRoleName());
    		userManager.addAttributesForUser(user);
    		user.set("merchantId", merchant.getMerchantId());
    		dao.getUserDao().addUser(user);
    	}
    	
    	String salesManJson=(String)args.get("salesMan");
    	List<JSONObject> salesMan=JSONObject.parseObject(salesManJson,ArrayList.class);
    	role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("salesMan");
    	if(role==null){
    		return failNotice();
    	}
    	for(JSONObject each:shopKeepers){
    		String userPhone=each.getString("phone");
    		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
    		if(user!=null)
    			return failNotice();
    		user=new User();
    		user.setRoleName(role.getRoleName());
    		userManager.addAttributesForUser(user);
    		user.set("merchantId", merchant.getMerchantId());
    		dao.getUserDao().addUser(user);
    	}
        return successNotice();
    }
}
