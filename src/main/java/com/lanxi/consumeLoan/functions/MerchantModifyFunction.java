package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
/**
 * 修改商户
 * @author yangyuanjian
 *
 */
@Service
public class MerchantModifyFunction extends AbstractFunction {

	@Override
	public RetMessage successNotice() {
		LogFactory.info(this, "修改商户成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"修改商户成功!",null);
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
		Merchant merchant= (Merchant)args.get("merchant");
		LogFactory.info(this,"用户["+phone+"],修改的商户 "+ merchant.toString()+"");
		Merchant temp=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchant.getMerchantId());
		
		if (merchant.getMerchantType() != null && merchant.getMerchantType() !="") {
			temp.setMerchantType(merchant.getMerchantType());
		}
		if (merchant.getMerchantName() != null && merchant.getMerchantName() !="") {
			temp.setMerchantName(merchant.getMerchantName());
		}
		if (merchant.getMerchantAddress() != null && merchant.getMerchantAddress() !="") {
			temp.setMerchantAddress(merchant.getMerchantAddress());
		}
		if (merchant.getIsAssurance() != null && merchant.getIsAssurance() !="") {
			temp.setIsAssurance(merchant.getIsAssurance());
		}
		if (merchant.getProvideDeposit() != null && merchant.getProvideDeposit() !="") {
			temp.setProvideDeposit(merchant.getProvideDeposit());
		}
		if (merchant.getDepositeRate() != null ) {
			temp.setDepositeRate(merchant.getDepositeRate());
		}
		if (merchant.getIsShared() != null && merchant.getIsShared() !="") {
			temp.setIsShared(merchant.getIsShared());
		}
		if (merchant.getSharedRate() != null) {
			temp.setSharedRate(merchant.getSharedRate());
		}
		if (merchant.getCustomerManagerPhone() != null && merchant.getCustomerManagerPhone() !="") {
			temp.setCustomerManagerPhone(merchant.getCustomerManagerPhone());
		}
		if (merchant.getCustomerManagerName() != null && merchant.getCustomerManagerName() !="") {
			temp.setCustomerManagerName(merchant.getCustomerManagerName());
		}
		if(temp==null){
			LogFactory.info(this,"用户["+phone+"],修改的商户["+merchant.getMerchantId()+"]不存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"没有该商户!",null);
		}
		LogFactory.info(this,"用户["+phone+"],修改的商户 "+ merchant.toString()+"");
		dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(temp,merchant.getMerchantId());
		LogFactory.info(this, "用户["+phone+"],修改商户成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"修改商户成功!",null);
	}

}
