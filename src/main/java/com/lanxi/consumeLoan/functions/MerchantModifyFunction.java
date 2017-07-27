package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage showInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage excuted(Map<String, Object> args) {
		String phone=(String) args.get("phone");
		if(!checkService.checkAuthority(phone, this.getClass().getName())){
			LogFactory.info(this,"没有权限执行该操作!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"没有权限!",null);
		}
		String merchantJson=(String) args.get("merchant");
		Merchant merchant= JSONObject.parseObject(merchantJson,Merchant.class);
		LogFactory.info(this,"商户 "+ merchant.toString()+"");
		if (merchant == null ){
			LogFactory.info(this,"商户["+merchant.getMerchantId()+"]不存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"没有该商户!",null);
		}
		dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant,merchant.getMerchantId());

		return successNotice();
	}

}
