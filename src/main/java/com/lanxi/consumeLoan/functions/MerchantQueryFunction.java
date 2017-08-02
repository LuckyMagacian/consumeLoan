package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
		String phone = (String) args.get("phone");
		if (!checkService.checkAuthority(phone, this.getClass().getName())) {
			LogFactory.info(this, "没有权限执行该操作!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没有权限!", null);
		}
		String state = (String) args.get("state");
		String merchantName = (String) args.get("merchantName");
		String merchantType = (String) args.get("merchantType");
		String isAssurance = (String) args.get("isAssurance");
		String start_time = (String) args.get("start_time");
		String end_time = (String) args.get("end_time");
		Map<String , Object> parm = new HashMap<String, Object>();
		if(state !=null && state != ""){
			parm.put("state", state);
		}
		if(merchantName !=null && merchantName != ""){
			parm.put("merchantName", merchantName);
		}
		if(merchantType !=null && merchantType != ""){
			parm.put("merchantType", merchantType);
		}
		if(isAssurance !=null && isAssurance != ""){
			parm.put("isAssurance", isAssurance);
		}
		if(start_time !=null && start_time != ""){
			parm.put("start_time",start_time);
		}
		if(end_time !=null && end_time != ""){
			parm.put("end_time",end_time);
		}
		LogFactory.info(this, "请求参数："+ parm.toString());
		List<Merchant> merchants = dao.getMerchantDao().selectMerchantByParm(parm);
		if(merchants ==null || merchants.size()<=0){
			LogFactory.info(this, "没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		int merchantTotal = merchants.size();
		BigDecimal applyMoneyAmountTotal = new BigDecimal(0);
		Integer applyAmountTotal = 0;
		Integer loanAmountTotal = 0;
		BigDecimal loanMoneyAmountTotal = new BigDecimal(0);
		Integer breakAmountTotal = 0;
		BigDecimal breakMoneyAmountTotal = new BigDecimal(0);

		for (Merchant mc : merchants) {
			applyMoneyAmountTotal = applyMoneyAmountTotal.add(mc
					.getApplyMoneyAmount());// 申请总额
			applyAmountTotal += mc.getApplyAmount();// 申请总人数
			loanAmountTotal += mc.getLoanAmount();// 放款总人数
			loanMoneyAmountTotal = loanMoneyAmountTotal.add(mc
					.getLoanMoneyAmount());// 放款总额
			breakAmountTotal += mc.getBreakAmount();// 违约总人数
			breakMoneyAmountTotal = breakMoneyAmountTotal.add(mc
					.getBreakMoneyAmount());// 违约总金额
		}
		LogFactory.info(this, "商家总数为：" + merchantTotal + ",申请总额为："
				+ applyMoneyAmountTotal + ",申请总人数为：" + applyAmountTotal
				+ ",放款总额为：" + loanMoneyAmountTotal + ",放款总人数为："
				+ loanAmountTotal + ",违约总金额为：" + breakMoneyAmountTotal
				+ ",违约总人数：" + breakAmountTotal + "");
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("merchantTotal", merchantTotal);
		totalMap.put("applyMoneyAmountTotal", applyMoneyAmountTotal);
		totalMap.put("applyAmountTotal", applyAmountTotal);
		totalMap.put("loanMoneyAmountTotal", loanMoneyAmountTotal);
		totalMap.put("loanAmountTotal", loanAmountTotal);
		totalMap.put("breakMoneyAmountTotal", breakMoneyAmountTotal);
		totalMap.put("breakAmountTotal", breakAmountTotal);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("merchants", merchants);
		resultMap.put("total", totalMap);
		
		LogFactory.info(this, "商户查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "商户查询成功!", resultMap);	
    }
}
