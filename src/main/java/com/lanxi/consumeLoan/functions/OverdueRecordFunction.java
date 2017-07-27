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
        LogFactory.info(this, "修改违约金和违约时间成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"修改违约金和违约时间成功!",null);
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
        System.out.println(args.toString());
        String phone=(String) args.get("phone");
        BigDecimal breakMoney = new BigDecimal((String) args.get("breakMoney"));
        String merchantId  = (String)args.get("merchant_id");
        String applyId  = (String) args.get("applyId");
        String breakTime = (String)args.get("breakTime");
        if(!checkService.checkAuthority(phone, this.getClass().getName())){
            LogFactory.info(this,"没有权限执行该操作!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"没有权限!",null);
        }
        Apply apply = dao.getApplyDao().selectApplyByUniqueIndexOnApplyId(applyId);
        if (apply == null ){
            LogFactory.info(this,"申请["+apply.getApplyId()+"]不存在!");
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
        merchant.setBreakAmount(breakAmount + 1);
        merchant.setBreakMoneyAmount(breakMoneyAmount.add(breakMoney));
        dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchantId);
        return successNotice();
    }
}
