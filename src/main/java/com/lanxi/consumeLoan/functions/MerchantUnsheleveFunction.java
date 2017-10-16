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
 * Created by yangyuanjian on 2017/7/13.
 * 商户下架
 */
@Service
public class MerchantUnsheleveFunction extends AbstractFunction{
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
        String merchant_id = (String) args.get("merchantId");
    	LogFactory.info(this, "用户["+phone+"],下架的商户id为["+merchant_id+"]");
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchant_id);
        if (merchant == null ){
            LogFactory.info(this, "用户["+phone+"],下架的商户["+merchant_id+"]不存在!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"没有该商户!",null);
        }
        String state = merchant.getState();
        LogFactory.info(this,"用户["+phone+"],下架的商户["+merchant_id+"]的当前状态为["+state+"]");
        if (state.equals(ConstParam.MERCHANT_STATE_SHELVED)){
            merchant.setState(ConstParam.MERCHANT_STATE_UNSHELVED);
            dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant,merchant_id);
//            LogFactory.info(this, "商户下架成功,开始冻结相关正常商户人员");
//            List<User> users=dao.selectUserByAttibute(new Attribute<String>("merchantId",merchant.getMerchantId()));
//            users.stream()
//            .filter(user->{
//            	boolean flag=false;
//            	Attribute<String> userStatus=(Attribute<String>) user.get("status");
//            	Attribute<String> userState=(Attribute<String>) user.get("state");
//            	if(userStatus!=null) {
//            		flag|=ConstParam.USER_STATE_NORMAL.equals(userStatus.getValue());
//            	}
//            	if(userState!=null) {
//            		flag|=ConstParam.USER_STATE_NORMAL.equals(userState.getValue());
//            	}
//            	return flag;
//            })
//            .forEach(user->{
//            	user.set("state", ConstParam.USER_STATE_FREEZE);
//            	user.set("status", ConstParam.USER_STATE_FREEZE);
//            	dao.getUserDao().updateUserByUniqueIndexOnPhone(user, user.getPhone());
//            	LogFactory.info(this, "冻结商户["+merchant.getMerchantId()+":"+merchant.getMerchantName()+"]人员["+user.getRoleName()+"]["+user.getPhone()+"]成功");
//            });
        }else {
            LogFactory.info(this,"用户["+phone+"],下架的商户["+merchant_id+"]不满足下架条件!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不满足下架条件!",null);
        }
        LogFactory.info(this, "用户["+phone+"],下架的商户["+merchant_id+"]下架成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户下架成功!",null);
    }
}
