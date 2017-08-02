package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
/*
 * 客户经理商家列表
 */
@Service
public class CustomerManagerMerchantQueryFunction extends AbstractFunction {

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
		if (phone == null || phone =="") {
			LogFactory.info(this, "客户经理["+phone+"],传入手机号码为空!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "客户经理手机号为空!", null);
		}
		Merchant merchant =new Merchant();
		merchant.setCustomerManagerPhone(phone);
		List<Merchant> merchants = dao.getMerchantDao().selectMerchantByClass(merchant);
	
		LogFactory.info(this, "客户经理["+phone+"]的商家列表为["+ merchants+"]" );
		int merchantTotal = merchants.size();
		BigDecimal applyMoneyAmountTotal = new BigDecimal(0);
		Integer applyAmountTotal = 0;
		Integer loanAmountTotal = 0;
		BigDecimal loanMoneyAmountTotal = new BigDecimal(0);
		Integer breakAmountTotal = 0;
		BigDecimal breakMoneyAmountTotal = new BigDecimal(0);

		for (Merchant mc : merchants) {
			mc.hide2();
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
		LogFactory.info(this, "管理员["+phone+"],商家总数为：" + merchantTotal + ",申请总额为："
				+ applyMoneyAmountTotal + ",申请总人数为：" + applyAmountTotal
				+ ",放款总额为：" + loanMoneyAmountTotal + ",放款总人数为："
				+ loanAmountTotal + ",违约总金额为：" + breakMoneyAmountTotal
				+ ",违约总人数：" + breakAmountTotal);
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
		
		LogFactory.info(this, "客户经理["+phone+"]商家列表查询成功");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "客户经理申请订单列表查询成功!", resultMap);
	}
}
