package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.PageBean;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

@Service
public class AdminMerchantQueryFunction extends AbstractFunction{

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
		int pageCode = Integer.parseInt((String)args.get("pageCode")==null?"1":(String)args.get("pageCode"));
		int pageSize = Integer.parseInt((String)args.get("pageSize")==null?"1":(String)args.get("pageSize"));
		PageBean page = new PageBean();
		page.setPageSize(pageSize);
		page.setPageCode(pageCode);
		
		Map<String, Object> parm = new HashMap<String, Object>();
		if(args.get("isAssurance") != "" && args.get("isAssurance") !=null){
			parm.put("isAssurance", args.get("isAssurance"));
		}
		if(args.get("customerManagerPhone") != "" && args.get("customerManagerPhone") !=null){
			parm.put("customerManagerPhone", args.get("customerManagerPhone"));
		}
		if(args.get("state") != "" && args.get("state") !=null){
			parm.put("state", args.get("state"));
		}
		if(args.get("merchantName") != "" && args.get("merchantName") !=null){
			parm.put("merchantName", args.get("merchantName"));
		}
		if(args.get("merchantType") != "" && args.get("merchantType") !=null){
			parm.put("merchantType", args.get("merchantType"));
		}
		if(args.get("startTime") != "" && args.get("startTime") !=null){
			parm.put("startTime", args.get("startTime"));
		}
		if(args.get("endTime") != "" && args.get("endTime") !=null){
			parm.put("endTime", args.get("endTime"));
		}
		LogFactory.info(this, "管理员["+phone+"],请求参数：" + args.toString());
		List<Merchant> merchants = dao.getMerchantDao().selectMerchantByParm(parm);
//		if(merchants ==null || merchants.size()<=0){
//			LogFactory.info(this, "管理员["+phone+"],没查询到数据!");
//			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
//		}
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
		LogFactory.info(this, "管理员["+phone+"],查询商家总数为：" + merchantTotal + ",申请总额为："
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
		
		
		page.setTotalRecord(merchants.size());		
		parm.put("start", page.getStart());
		parm.put("size", page.getPageSize());
		List<Merchant> list = dao.getMerchantDao().selectMerchantByPage(parm);
		for (Merchant merchant : list) {
			merchant.hide4();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(args.get("excel")!=null)
			resultMap.put("merchants", dao.getMerchantDao().selectMerchantByParm(parm));
		else 
			resultMap.put("merchants", list);
		resultMap.put("total", totalMap);
		resultMap.put("page", page);
		
		LogFactory.info(this, "管理员["+phone+"],商户查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "商户查询成功!", resultMap);	
	}

}
