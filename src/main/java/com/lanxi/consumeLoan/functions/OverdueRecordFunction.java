package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by yangyuanjian on 2017/7/13.
 * 逾期记录
 */
@Service
public class OverdueRecordFunction extends AbstractFunction {
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
        BigDecimal breakMoney = new BigDecimal((String) args.get("breakMoney"));
        String merchantId  = (String)args.get("merchantId");
        String applyId  = (String) args.get("applyId");
        String breakTime = (String)args.get("breakTime");
        LogFactory.info(this,"用户["+phone+"],逾期的请求参数为：["+ args.toString()+ "]");
 
        Apply apply = dao.getApplyDao().selectApplyByUniqueIndexOnApplyId(applyId);
        if (apply == null ){
            LogFactory.info(this,"用户["+phone+"],逾期的申请id["+applyId+"]不存在!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"申请不存在!",null);
        }
        apply.setBreakTime(breakTime);
        apply.setBreakMoney(breakMoney);
        dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(apply,applyId);
        Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId);
        Integer breakAmount = merchant.getBreakAmount();
        BigDecimal breakMoneyAmount = merchant.getBreakMoneyAmount();
        if(breakAmount == null){
        	breakAmount = 0;
        }
        LogFactory.info(this,"用户["+phone+"],逾期之前的违约总金额为：" + breakMoneyAmount+ ",逾期之前的违约总人数为："+(breakAmount+ 1));
    
        breakMoneyAmount = breakMoneyAmount.add(breakMoney);
       
        merchant.setBreakAmount(breakAmount + 1);
        merchant.setBreakMoneyAmount(breakMoneyAmount);
        LogFactory.info(this,"用户["+phone+"],逾期之后的违约总金额为：" + breakMoneyAmount+ ",逾期之后的违约总人数为："+(breakAmount+ 1));
        dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchantId);
        
        LogFactory.info(this, "用户["+phone+"],逾期的申请id["+applyId+"]逾期成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"逾期成功!",null);
    }
}
