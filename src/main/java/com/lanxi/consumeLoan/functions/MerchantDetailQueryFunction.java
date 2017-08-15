package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.basic.UserProxy;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户详情查询
 */
@Service
public class MerchantDetailQueryFunction extends AbstractFunction {
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
        String merchantId = (String) args.get("merchantId");
        LogFactory.info(this,"管理员["+phone+"],查看商户详情的id为：" + merchantId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId);
        if (merchant == null ){
            LogFactory.info(this, "管理员["+phone+"],查询的商户id:["+merchantId+"]不存在!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不存在!",null);
        }
        LogFactory.info(this, "管理员["+phone+"],查询到的商户信息为：["+merchant.toJson()+"]!");
        merchant.hide3();
        LogFactory.info(this, "管理员["+phone+"],隐藏商户信息 之后的信息为：["+merchant.toJson()+"]!");
    	Attribute<String> attr = new Attribute<String>("merchantId", merchantId);
    	String str = attr.toJson();
    	LogFactory.info(this, "管理员["+phone+"],查询的商户idjson串为：["+str+"]!");	
    	List<User> attibutes = dao.selectUserByAttibute(str);
    	List<UserProxy> Proxy = new ArrayList<>();
    	for (User user : attibutes) {
			 Proxy.add(user.toProxy());
		}
    	LogFactory.info(this, "管理员["+phone+"],商户的担保人信息为：["+attibutes+"]!");	
    	resultMap.put("Users", Proxy);
    	resultMap.put("merchant", merchant);
        LogFactory.info(this, "管理员["+phone+"],商户详情查询成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户详情查询成功!",resultMap);
    }
}
