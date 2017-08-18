package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
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
		LogFactory.info(this, "管理员["+phone+"]，请求参数：" + args);
		String  isOverdue   =(String) args.get("isOverdue");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> totalMap = new HashMap<String, Object>();
		PageBean page = new PageBean();
		page.setPageSize(Integer.parseInt((String) args.get("pageSize")==null?"1":(String) args.get("pageSize")));
		page.setPageCode(Integer.parseInt((String) args.get("pageCode")==null?"1":(String) args.get("pageCode")));
		LogFactory.info(this, "管理员["+phone+"],请求参数："+args);
		Map<String, Object> parm = new HashMap<String, Object>();
		if(args.get("merchantName") !=null&&((String)args.get("merchantName")).isEmpty()){
			parm.put("merchantName", args.get("merchantName"));
		}
		if(args.get("startTime") !=null&&((String)args.get("startTime")).isEmpty()){
			parm.put("startTime", args.get("startTime"));
		}
		if(args.get("endTime") !=null&&((String)args.get("endTime")).isEmpty()){
			parm.put("endTime", args.get("endTime"));
		}
		if(args.get("customerPhone") !=null&&((String)args.get("customerPhone")).isEmpty()){
			parm.put("customerPhone", args.get("customerPhone"));
		}
		if(isOverdue!=null&&(isOverdue.equals("true"))){
			parm.put("isOverdue", isOverdue);
		}
		List<Apply> applys = dao.selectApplyByParam(parm);
		List<Apply> applys1 =new ArrayList<>();
		
		if(applys ==null || applys.size()<=0){
			LogFactory.info(this, "管理员["+phone+"],没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		BigDecimal brokerageTotal =new BigDecimal(0);//佣金总金额
		//TODO 此处已经修改为消耗风险保证金
		BigDecimal breakMoneyTotal =new BigDecimal(0);//逾期总金额
		BigDecimal serviceChargeTotal =new BigDecimal(0);//服务费总金额
		
		
		for (Apply apply : applys) {
			if (apply.getBrokerage() !=null) {
				brokerageTotal = brokerageTotal.add(apply.getBrokerage());
			}
//			if (apply.getBreakMoney() !=null) {
//				breakMoneyTotal = breakMoneyTotal.add(apply.getBreakMoney());
//			}
			if (apply.getLoseMoney() !=null) {
				breakMoneyTotal = breakMoneyTotal.add(apply.getLoseMoney());
			}
			if (apply.getServiceCharge() !=null) {
				serviceChargeTotal = serviceChargeTotal.add(apply.getServiceCharge());
			}
			if(ConstParam.APPLY_STATE_LOAN.equals(apply.getState())||ConstParam.APPLY_STATE_OVERDUE.equals(apply.getState())||ConstParam.APPLY_STATE_FINISH.equals(apply.getState()))
				applys1.add(apply);
		}
		totalMap.put("serviceChargeTotal", serviceChargeTotal);
		totalMap.put("breakMoneyTotal", breakMoneyTotal);  
		totalMap.put("brokerageTotal", brokerageTotal);
		LogFactory.info(this, "管理员["+phone+"],查询佣金总金额为："+brokerageTotal +",服务费总金额为:" + serviceChargeTotal +",逾期总金额为:" +breakMoneyTotal );
		resultMap.put("total", totalMap);
		
		page.setTotalRecord(applys1.size());	
		parm.put("start", page.getStart());
		parm.put("size", page.getPageSize());
		LogFactory.info(this, "管理员["+phone+"],查询条件为" + parm.toString()+",分页:"+page+" 结果总数:"+applys1.size());
		List<Apply> list = new ArrayList<>();
		for(int i=page.getStart();i<page.getEnd();i++){
			list.add(applys1.get(i));
		}
		for (Apply apply : list) {
			apply.hide5();
		}
		if (args.get("excel") != null) 
			resultMap.put("applys", applys1);
		else
			resultMap.put("applys", list);
		resultMap.put("page", page);
		LogFactory.info(this, "管理员["+phone+"],查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "查询成功!", resultMap);	
	}

}
