package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String argBreakMoney  = (String) args.get("breakMoney");
        BigDecimal breakMoney = new BigDecimal(argBreakMoney==null||argBreakMoney.isEmpty()?"0":argBreakMoney);
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
        apply.setIsOverdue("true");
        apply.setState(ConstParam.APPLY_STATE_OVERDUE);
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
        if("true".equals(merchant.getIsAssurance())){
        	apply.setLoseMoney(new BigDecimal("0"));
        }else{
        	apply.setLoseMoney(apply.getBreakMoney());
        }
        if(update(apply, merchant))
        	return new RetMessage(RetCodeEnum.SUCCESS.toString(),"违约操作成功!",null);
        else
        	return new RetMessage(RetCodeEnum.FAIL.toString(),"违约操作失败,请重试!",null);
    }
	@Transactional
    public boolean update(Apply apply,Merchant merchant) {
    	try {
			LogFactory.info(this, "尝试更新商户账户!");
			dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchant.getMerchantId());
			LogFactory.info(this, "更新商户违约信息成功!");
			LogFactory.info(this, "尝试更新申请订单!");
			dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(apply, apply.getApplyId());
			LogFactory.info(this, "更新订单违约信息成功!");
			return true;
		} catch (Exception e) {
			return false;
		}
    }
}


