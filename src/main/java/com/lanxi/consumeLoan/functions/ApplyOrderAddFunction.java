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
import com.lanxi.consumeLoan.entity.SystemAccount;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.RandomUtil;
import com.lanxi.util.utils.TimeUtil;

/**
 * Created by yangyuanjian on 2017/7/13.
 */
@Service
public class ApplyOrderAddFunction extends AbstractFunction{
    public ApplyOrderAddFunction(){
        addAttribute(new Attribute<String>("merchantId",""));
        addAttribute(new Attribute<String>("salesManId",""));
        addAttribute(new Attribute<String>("merchantName",""));
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
    	LogFactory.info(this, "用户["+phone+"]尝试添加新订单!");
    	String applyJson=(String) args.get("apply");
    	if(applyJson==null||applyJson.isEmpty()){
    		LogFactory.info(this, "用户["+phone+"]添加的订单["+applyJson+"]为空");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"订单内容为空!",null);
    	}
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	Attribute<String> merchantId=(Attribute<String>) user.get("merchantId");
    	if(merchantId==null){
    		LogFactory.info(this, "用户["+phone+"]未绑定商户或不是商户工作人员!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"用户未绑定商户或不是商户工作人员!",null);
    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId.getValue());
    	if(merchant==null){
    		LogFactory.info(this, "用户["+phone+"]绑定的商户["+merchantId.getValue()+"]不存在!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"用户绑定的商户不存在!",null);
    	}
    	if(!merchant.getState().equals(ConstParam.MERCHANT_STATE_SHELVED)){
    		LogFactory.info(this, "用户["+phone+"]绑定的商户["+merchantId.getValue()+"]状态不是正常的上架状态["+merchant.getState()+"],无法添加新的申请!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"用户绑定的商户状态异常,无法添加新申请!",null);
    	}
    	
    	Apply apply=JSONObject.parseObject(applyJson, Apply.class);
    	String userPhone=apply.getPhone();
    	if(userPhone==null){
    		LogFactory.info(this, "用户["+phone+"]添加的订单["+applyJson+"]未传入申请人的手机号!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"未传入申请者手机号!",null);
    	}
    	String cacheCode=redisService.get(ConstParam.FUNCTION_NAME_APPLY_ADD+userPhone.trim());
    	if(!apply.getVerifyCode().equals(cacheCode)){
    		LogFactory.info(this, "用户["+phone+"]添加的订单申请者手机验证码校验不通过!输入验证码["+apply.getVerifyCode()+"],缓存验证码["+cacheCode+"]");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"手机验证码校验不通过!",null);
    	}
    	LogFactory.info(this, "用户["+phone+"]添加的订单["+userPhone+"]手机验证码校验通过!删除缓存验证码!");
    	SystemAccount systemAccount=dao.getSystemAccountDao().selectSystemAccountByClass(new com.lanxi.consumeLoan.entity.SystemAccount()).get(0);
    	redisService.delete(ConstParam.FUNCTION_NAME_APPLY_ADD+userPhone.trim());
    	apply.setApplyTime(TimeUtil.getDateTime());
    	apply.setState(ConstParam.APPLY_STATE_WAIT_CHECK);
    	apply.setApplyId(TimeUtil.getDate()+TimeUtil.getNanoTime()+RandomUtil.getRandomNumber(6));
    	apply.setMerchantId(merchantId.getValue());
    	apply.setSalesManPhone(phone);
    	apply.setBrokerageRate(merchant.getBrokerageRate());
    	apply.setDepositeRate(merchant.getDepositeRate());
    	apply.setServiceRate(systemAccount.getServiceChargeRate()); 
    	merchant.setApplyAmount(merchant.getApplyAmount()+1);
    	merchant.setApplyMoneyAmount(merchant.getApplyMoneyAmount().add(apply.getApplyMoney()));
    	dao.getApplyDao().addApply(apply);
    	LogFactory.info(this, "用户["+phone+"]在商户["+merchantId.getValue()+"]中添加申请["+apply.getApplyId()+"]成功!");
    	dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchantId.getValue());
    	LogFactory.info(this, "用户["+phone+"]所在商户["+merchantId.getValue()+"]更新商户申请统计信息成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(), "添加申请成功!", null);
    }
}
