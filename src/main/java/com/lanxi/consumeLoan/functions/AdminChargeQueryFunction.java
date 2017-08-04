package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.PageBean;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
/**
 *管理员--账户管理
 * @author lx
 *
 */
@Service
public class AdminChargeQueryFunction extends AbstractFunction{

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
		String  isOverdue   =(String) args.get("isOverdue");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> totalMap = new HashMap<String, Object>();
		PageBean page = new PageBean();
		page.setPageSize(Integer.parseInt((String) args.get("pageSize")));
		page.setPageCode(Integer.parseInt((String) args.get("pageCode")));
		Map<String, Object> parm = new HashMap<String, Object>();
		if(args.get("merchantName") != "" && args.get("merchantName") !=null){
			parm.put("merchantName", args.get("merchantName"));
		}
		if(args.get("start_time") != "" && args.get("start_time") !=null){
			parm.put("start_time", args.get("start_time"));
		}
		if(args.get("end_time") != "" && args.get("end_time") !=null){
			parm.put("end_time", args.get("end_time"));
		}
		if(args.get("customerPhone") != "" && args.get("customerPhone") !=null){
			parm.put("customerPhone", args.get("customerPhone"));
		}
		if(isOverdue.equals("true") || isOverdue =="true"){
			parm.put("isOverdue", isOverdue);
		}
		List<Apply> applys = dao.getApplyDao().selectApplyByParam(parm);
		LogFactory.info(this, "管理员["+phone+"],查询条件为" + parm.toString());
		if(applys ==null || applys.size()<=0){
			LogFactory.info(this, "管理员["+phone+"],没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		BigDecimal brokerageTotal =new BigDecimal(0);//佣金总金额
		BigDecimal breakMoneyTotal =new BigDecimal(0);//逾期总金额
		BigDecimal serviceChargeTotal =new BigDecimal(0);//服务费总金额
		for (Apply apply : applys) {
			if (apply.getBrokerage() !=null) {
				brokerageTotal = brokerageTotal.add(apply.getBrokerage());
			}
			if (apply.getBreakMoney() !=null) {
				breakMoneyTotal = breakMoneyTotal.add(apply.getBreakMoney());
			}
			if (apply.getBrokerage() !=null) {
				serviceChargeTotal = serviceChargeTotal.add(apply.getServiceCharge());
			}
		}
		totalMap.put("serviceChargeTotal", serviceChargeTotal);
		totalMap.put("breakMoneyTotal", breakMoneyTotal);
		totalMap.put("brokerageTotal", brokerageTotal);
		LogFactory.info(this, "管理员["+phone+"],查询佣金总金额为："+brokerageTotal +",服务费总金额为:" + serviceChargeTotal +",逾期总金额为:" +breakMoneyTotal );
		resultMap.put("total", totalMap);
		
		page.setTotalRecord(applys.size());		
		parm.put("start", page.getStart());
		parm.put("size", page.getPageSize());
		List<Apply> list = dao.getApplyDao().selectApplyByPage(parm);
		for (Apply apply : list) {
			apply.hide5();
		}
		resultMap.put("applys", list);
		LogFactory.info(this, "管理员["+phone+"],查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "查询成功!", resultMap);	
	}

}
