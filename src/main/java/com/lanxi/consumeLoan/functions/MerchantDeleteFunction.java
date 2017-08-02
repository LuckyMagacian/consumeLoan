package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户删除
 */
@Service
public class MerchantDeleteFunction extends AbstractFunction {
    @Override
    public RetMessage successNotice() {
        LogFactory.info(this, "商户删除成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"商户删除成功!",null);
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
        if(!checkService.checkAuthority(phone, this.getClass().getName())){
            LogFactory.info(this,"没有权限执行该操作!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"没有权限!",null);
        }
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchant_id);
        if (merchant == null ){
            LogFactory.info(this, "商户["+merchant_id+"]不存在!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不存在!",null);
        }
        dao.getMerchantDao().deleteMerchantByUniqueIndexOnMerchantId(merchant_id);
        return successNotice();
    }
}
