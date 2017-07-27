package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 商户上架
 * @author yangyuanjian
 *
 */
@Service
public class MerchantShelveFunction extends AbstractFunction {

	@Override
	public RetMessage successNotice() {
        LogFactory.info(this, "商户上架成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户上架成功!",null);
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
		String merchant_id = (String) args.get("merchat_id");
		if(!checkService.checkAuthority(phone, this.getClass().getName())){
			LogFactory.info(this,"没有权限执行该操作!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"没有权限!",null);
		}
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchant_id);
        if (merchant == null ){
            LogFactory.info(this, "商户["+merchant_id+"]为空!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"没有该商户!",null);
        }
        String state = merchant.getState();
        LogFactory.info(this,"商户["+merchant_id+"],的状态为["+state+"]");
        if (state.equals(ConstParam.MERCHANT_STATE_WAIT_SHELVE) || state.equals(ConstParam.MERCHANT_STATE_UNSHELVED)){
            merchant.setState(ConstParam.MERCHANT_STATE_SHELVED);
            dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant,merchant_id);
        }else {
            LogFactory.info(this,"商户["+merchant_id+"]不满足上架条件!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不满足上架条件!",null);
        }
        return successNotice();
	}

}
