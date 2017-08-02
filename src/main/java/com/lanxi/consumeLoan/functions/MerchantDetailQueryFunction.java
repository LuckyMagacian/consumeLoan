package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

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
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(!checkService.checkAuthority(phone, this.getClass().getName())){
            LogFactory.info(this,"没有权限执行该操作!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"没有权限!",null);
        }
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId);
        if (merchant == null ){
            LogFactory.info(this, "商户["+merchantId+"]不存在!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不存在!",null);
        }
        LogFactory.info(this, "商户信息：["+merchant.toJson()+"]!");
    	Attribute<String> attr = new Attribute<String>("merchantId", merchantId);
    	String str = attr.toJson();
    	LogFactory.info(this, "商户json串：["+str+"]!");	
    	List<User> attibutes = dao.selectUserByAttibute(str);
    	LogFactory.info(this, "返回信息：["+attibutes+"]!");	
    	resultMap.put("Users", attibutes);
    	resultMap.put("merchant", merchant);
        LogFactory.info(this, "商户详情查询成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户详情查询成功!",resultMap);
    }
}
