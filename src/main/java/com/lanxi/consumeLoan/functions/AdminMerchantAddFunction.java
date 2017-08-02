package com.lanxi.consumeLoan.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.basic.UserProxy;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.RandomUtil;
import com.lanxi.util.utils.TimeUtil;

/**
 * Created by yangyuanjian on 2017/7/13.
 *商户增加
 */
@Service
public class AdminMerchantAddFunction extends AbstractFunction {
	public AdminMerchantAddFunction() {
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
//    	if(!checkService.checkAuthority(phone, this.getClass().getName()))
//    		return failNotice();
    	LogFactory.info(this, "用户["+phone+"]尝试添加商户!");
    	String merchantJson=(String) args.get("merchant");
    	LogFactory.info(this, "用户["+phone+"]添加的商户信息["+merchantJson+"]");
    	if(merchantJson==null||merchantJson.isEmpty()){
    		LogFactory.info(this, "用户["+phone+"]添加的商户信息为空!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"商户信息为空,添加失败!",null);
    	}
    	Merchant merchant=JSONObject.parseObject(merchantJson,Merchant.class);
    	merchant.setMerchantId(TimeUtil.getDate()+TimeUtil.getNanoTime()+RandomUtil.getRandomNumber(6));
    	merchant.setState(ConstParam.MERCHANT_STATE_WAIT_CHECK);
    	merchant.setCustomerManagerPhone(phone);
    	dao.getMerchantDao().addMerchant(merchant);
    	LogFactory.info(this, "用户["+phone+"]添加商户["+merchant.getMerchantId()+"]成功!");
    	String shopKeeperJson=(String) args.get("shopKeepers");
    	if(shopKeeperJson!=null&&!shopKeeperJson.isEmpty()){
    		LogFactory.info(this, "用户["+phone+"]尝试添加店长["+shopKeeperJson+"]");
	    	List<JSONObject> shopKeepers=JSONObject.parseObject(shopKeeperJson, ArrayList.class);
	    	Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("shopKeeper");
	    	if(role==null){
	    		LogFactory.info(this, "用户["+phone+"]添加店长,店长角色不存在!");
	    		return new RetMessage(RetCodeEnum.FAIL.toString(), "添加商户成功,添加店长时发现店长角色不存在!", null);
	    	}
	    	for(JSONObject each:shopKeepers){
	    		String userPhone=each.getString("phone");
	    		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
	    		if(user!=null)
	    			return failNotice();
	    		user=new User();
	   			user.set("name", each.getString("name"));
	    		user.setRoleName(role.getRoleName());
	    		userManager.addAttributesForUser(user);
	    		user.set("merchantId", merchant.getMerchantId());
	    		dao.getUserDao().addUser(user);
	    	}
	    	LogFactory.info(this, "用户["+phone+"]添加店长成功!");
    	}
    	
    	String salesManJson=(String)args.get("salesMans");
    	if(salesManJson!=null&&!salesManJson.isEmpty()){
    		List<JSONObject> salesMan=JSONObject.parseObject(salesManJson,ArrayList.class);
    		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName("salesMan");
    		if(role==null){
    			LogFactory.info(this, "用户["+phone+"]添加销售员,销售员角色不存在!");
	    		return new RetMessage(RetCodeEnum.FAIL.toString(), "添加商户成功,添加销售员时发现销售员角色不存在!", null);
    		}
    		for(JSONObject each:salesMan){
    			String userPhone=each.getString("phone");
    			User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
    			if(user!=null)
    				return failNotice();
    			user=new User();
    			user.setRoleName(role.getRoleName());
    			user.set("name", each.getString("name"));
    			userManager.addAttributesForUser(user);
    			user.set("merchantId", merchant.getMerchantId());
    			dao.getUserDao().addUser(user);
    		}
    		LogFactory.info(this, "用户["+phone+"]添加销售员成功!");
    	}
    	User temp=new User();
    	temp.setRoleName("salesMan");
    	List<User> salesMans=dao.getUserDao().selectUserByClass(temp);
    	List<UserProxy> salesMansProxy=new ArrayList<>();
    	for(User each:salesMans){
    		salesMansProxy.add(each.toProxy());
    	}
    	temp.setRoleName("shopKeeper");
    	List<User> shopkeepers=dao.getUserDao().selectUserByClass(temp);
    	List<UserProxy> shopkeepersProxy=new ArrayList<>();
    	for(User each:shopkeepers){
    		shopkeepersProxy.add(each.toProxy());
    	}
    	Map<String, Object> result=new HashMap<String, Object>();
    	result.put("salesMans", salesMansProxy);
    	result.put("shopKeepers", shopkeepersProxy);
    	result.put("merchant", merchant);
        return new RetMessage(RetCodeEnum.SUCCESS.toString(), "添加成功!", result);
    }
}
