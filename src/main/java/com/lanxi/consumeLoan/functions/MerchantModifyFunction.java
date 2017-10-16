package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.stereotype.Service;

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
		if(temp==null){
			LogFactory.info(this,"用户["+phone+"],修改的商户["+merchant.getMerchantId()+"]不存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"没有该商户!",null);
		}
		if (merchant.getMerchantType() != null && merchant.getMerchantType() !="") {
			switch (merchant.getMerchantType()) {
			case ConstParam.MERCHANT_TYPE_HOUSEHOLD:break;
			case ConstParam.MERCHANT_TYPE_ELECTRIC_APPLIANCE:break;
			case ConstParam.MERCHANT_TYPE_NUMERAL:break;
			case ConstParam.MERCHANT_TYPE_ENTERAINMENT:break;
			case ConstParam.MERCHANT_TYPE_JEWELS:break;
			case ConstParam.MERCHANT_TYPE_MEDICAL_TREATMENT:break;
			case ConstParam.MERCHANT_TYPE_BEAUTY:break;
			case ConstParam.MERCHANT_TYPE_MOTOR_CAR:break;
			case ConstParam.MERCHANT_TYPE_EDUCATION:break;
			case ConstParam.MERCHANT_TYPE_OTHERS:break;		
			default:LogFactory.info(this, "用户["+phone+"]修改商户["+merchant.getMerchantId()+"]信息时,商户类型不存在!");return new RetMessage(RetCodeEnum.FAIL, "修改失败,商户类型不存在!", null);
		}   
			temp.setMerchantType(merchant.getMerchantType());
		}
		if (merchant.getMerchantName() != null && merchant.getMerchantName() !="") {
			temp.setMerchantName(merchant.getMerchantName());
		}
		if (merchant.getMerchantAddress() != null && merchant.getMerchantAddress() !="") {
			temp.setMerchantAddress(merchant.getMerchantAddress());
		}
		if (merchant.getIsAssurance() != null && merchant.getIsAssurance() !="") {
			if(!("true".equals(merchant.getIsAssurance())||"false".equals(merchant.getIsAssurance()))) {
				LogFactory.info(this, "用户["+phone+"]修改商户["+merchant.getMerchantId()+"]信息时,是否担保信息不正确!");
				return new RetMessage(RetCodeEnum.FAIL, "修改失败,是否担保信息不正确!", null);
			}
			temp.setIsAssurance(merchant.getIsAssurance());
		}
		if (merchant.getProvideDeposit() != null && merchant.getProvideDeposit() !="") {
			temp.setProvideDeposit(merchant.getProvideDeposit());
		}
		if (merchant.getDepositeRate() != null ) {
			temp.setDepositeRate(merchant.getDepositeRate());
		}
		if (merchant.getIsShared() != null && merchant.getIsShared() !="") {
			if(!("true".equals(merchant.getIsShared())||"false".equals(merchant.getIsShared()))) {
				LogFactory.info(this, "用户["+phone+"]修改商户["+merchant.getMerchantId()+"]信息时,是否分润信息不正确!");
				return new RetMessage(RetCodeEnum.FAIL, "修改失败,是否分润信息不正确!", null);
			}
			temp.setIsShared(merchant.getIsShared());
		}
		if (merchant.getSharedRate() != null) {
			temp.setSharedRate(merchant.getSharedRate());
			temp.setBrokerageRate(merchant.getSharedRate());
		}
		if (merchant.getCustomerManagerPhone() != null && merchant.getCustomerManagerPhone() !="") {
			User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(merchant.getCustomerManagerPhone());
			if(user==null) {
				LogFactory.info(this, "用户["+phone+"]修改商户["+merchant.getMerchantId()+"]信息时,客户经理不存在!");
				return new RetMessage(RetCodeEnum.FAIL, "修改失败,客户经理不存在!", null);
			}
			if(!merchant.getCustomerManagerName().equals(user.get("name").getValue())) {
				LogFactory.info(this, "用户["+phone+"]修改商户["+merchant.getMerchantId()+"]信息时,客户经理姓名不一致!");
				return new RetMessage(RetCodeEnum.FAIL, "修改失败,客户经理姓名不一致!", null);
			}
			if(!ConstParam.USER_STATE_NORMAL.equals(user.get("state").getValue())) {
				LogFactory.info(this, "用户["+phone+"]修改商户["+merchant.getMerchantId()+"]信息时,客户经理状态不是正常状态!");
				return new RetMessage(RetCodeEnum.FAIL, "修改失败,该客户经理状态不为正常状态!", null);
			}
			temp.setCustomerManagerPhone(merchant.getCustomerManagerPhone());
			temp.setCustomerManagerName((String) user.get("name").getValue());
		}
//		if (merchant.getCustomerManagerName() != null && merchant.getCustomerManagerName() !="") {
//			temp.setCustomerManagerName(merchant.getCustomerManagerName());
//		}
		LogFactory.info(this,"用户["+phone+"],修改的商户 "+ merchant.toString()+"");
		dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(temp,merchant.getMerchantId());
		LogFactory.info(this, "用户["+phone+"],修改商户成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"修改商户成功!",null);
	}

	
}
