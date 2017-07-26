package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 商户查询
 */
@Service
public class MerchantQueryFunction extends AbstractFunction {
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
        String phone =(String) args.get("phone");
        if(!checkService.checkAuthority(phone, this.getClass().getName())){
        	return failNotice();
        }
        String merchantName=(String) args.get("merchantName");
        return successNotice();
    }
}
