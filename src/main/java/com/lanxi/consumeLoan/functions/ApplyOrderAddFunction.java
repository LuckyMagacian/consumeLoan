package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.utils.TimeUtil;

/**
 * Created by yangyuanjian on 2017/7/13.
 */
@Service
public class ApplyOrderAddFunction extends AbstractFunction{
    public ApplyOrderAddFunction(){
        addAttribute(new Attribute<String>("merchantId",""));
        addAttribute(new Attribute<String>("salesManId",""));
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
    	String applyJson=(String) args.get("apply");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	Attribute<String> merchantId=(Attribute<String>) user.get("merchantId");
    	if(merchantId==null){
    		return failNotice();
    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId.getValue());
    	if(merchant==null){
    		return failNotice();
    	}
    	Apply apply=JSONObject.parseObject(applyJson, Apply.class);
    	apply.setApplyTime(TimeUtil.getDateTime());
    	apply.setState(ConstParam.APPLY_STATE_WAIT_CHECK);
    	apply.setApplyId(TimeUtil.getDate()+TimeUtil.getNanoTime());
    	apply.setMerchantId(merchantId.getValue());
    	apply.setSalesManPhone(phone);
    	apply.setBrokerageRate(merchant.getBrokerageRate());
    	apply.setDepositeRate(merchant.getDepositeRate());
    	apply.setSharedRate(merchant.getSharedRate());
    	
    	merchant.setApplyAmount(merchant.getApplyAmount()+1);
    	merchant.setApplyMoneyAmount(merchant.getApplyMoneyAmount().add(apply.getApplyMoney()));
    	dao.getApplyDao().addApply(apply);
    	dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchantId.getValue());
        return successNotice();
    }
}
