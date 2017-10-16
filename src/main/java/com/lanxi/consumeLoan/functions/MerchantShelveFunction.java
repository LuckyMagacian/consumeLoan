package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
		return null;
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

	@SuppressWarnings("unchecked")
	@Override
	public RetMessage excuted(Map<String, Object> args) {
		String phone=(String) args.get("phone");
		String merchant_id = (String) args.get("merchantId");
		LogFactory.info(this, "用户["+phone+"],上架的商户id为["+merchant_id+"]");
		
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchant_id);
        if (merchant == null ){
            LogFactory.info(this, "用户["+phone+"],上架的商户["+merchant_id+"]为空!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"没有该商户!",null);
        }
        String state = merchant.getState();
        LogFactory.info(this,"用户["+phone+"],上架的商户["+merchant_id+"]的状态当前状态为["+state+"]");
        if (state.equals(ConstParam.MERCHANT_STATE_WAIT_SHELVE) || state.equals(ConstParam.MERCHANT_STATE_UNSHELVED)){
            merchant.setState(ConstParam.MERCHANT_STATE_SHELVED);
            dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant,merchant_id);
//            LogFactory.info(this, "商户上架成功,开始启用相关冻结状态商户人员");
//            List<User> users=dao.selectUserByAttibute(new Attribute<String>("merchantId",merchant.getMerchantId()));
//            users.stream()
//            .filter(user->{
//            	boolean flag=false;
//            	Attribute<String> userStatus=(Attribute<String>) user.get("status");
//            	Attribute<String> userState=(Attribute<String>) user.get("state");
//            	if(userStatus!=null) {
//            		flag|=ConstParam.USER_STATE_FREEZE.equals(userStatus.getValue());
//            	}
//            	if(userState!=null) {
//            		flag|=ConstParam.USER_STATE_FREEZE.equals(userState.getValue());
//            	}
//            	return flag;
//            })
//            .forEach(user->{
//            	user.set("state", ConstParam.USER_STATE_NORMAL);
//            	user.set("status", ConstParam.USER_STATE_NORMAL);
//            	dao.getUserDao().updateUserByUniqueIndexOnPhone(user, user.getPhone());
//            	LogFactory.info(this, "冻结商户["+merchant.getMerchantId()+":"+merchant.getMerchantName()+"]人员["+user.getRoleName()+"]["+user.getPhone()+"]成功");
//            });
        }else {
            LogFactory.info(this,"用户["+phone+"],商户["+merchant_id+"]不满足上架条件!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不满足上架条件!",null);
        }
        LogFactory.info(this, "用户["+phone+"],商户["+merchant_id+"]上架成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户上架成功!",null);
	}

}
