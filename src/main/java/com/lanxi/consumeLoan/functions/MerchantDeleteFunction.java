package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户删除
 */
@Service
public class MerchantDeleteFunction extends AbstractFunction {
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
        String merchant_id = (String) args.get("merchantId");
        LogFactory.info(this,"管理员["+phone+"],删除的商户id为：" + merchant_id);
        LogFactory.info(this, "不允许删除商户!");
        return new RetMessage(RetCodeEnum.FAIL, "不允许删除商户!", null);
//        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchant_id);
//        if (merchant == null ){
//            LogFactory.info(this, "管理员["+phone+"],删除的商户["+merchant_id+"]不存在!");
//            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不存在!",null);
//        }
//        dao.getMerchantDao().deleteMerchantByUniqueIndexOnMerchantId(merchant_id);
//        LogFactory.info(this, "管理员["+phone+"],商户删除成功!开始删除商户人员");
//        List<User> users=dao.selectUserByAttibute(new Attribute<String>("merchantId",merchant.getMerchantId()));
//        users.stream().forEach(user->{
//        	dao.getUserDao().deleteUserByUniqueIndexOnPhone(user.getPhone());
//        	LogFactory.info(this, "删除商户["+merchant.getMerchantId()+"]人员["+user.getRoleName()+"]["+user.getPhone()+"]成功");
//        });
//        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户删除成功!",null);
    }
}
