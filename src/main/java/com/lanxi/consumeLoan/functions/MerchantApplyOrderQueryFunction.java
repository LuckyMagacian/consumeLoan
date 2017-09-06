package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.PageBean;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

/**
 * Created by yangyuanjian on 2017/7/13.
 */
@Service
public class MerchantApplyOrderQueryFunction extends AbstractFunction {
	public MerchantApplyOrderQueryFunction() {
		
	}
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

    @SuppressWarnings("unchecked")
	@Override
    public RetMessage excuted(Map<String, Object> args) {
    	String phone = (String) args.get("phone");
    	LogFactory.info(this, "用户["+phone+"]尝试根据条件["+args+"]查询申请订单!");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	Attribute<String> merchantId=(Attribute<String>) user.get("merchantId");
    	if(merchantId==null||merchantId.getValue().isEmpty()){
    		LogFactory.info(this, "用户["+phone+"]尚未绑定商户或不是商户工作人员!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(), "用户尚未绑定商户或不是商户工作人员!", null);
    	}
    	args.put("merchantId", merchantId.getValue());
    	LogFactory.info(this, "用户["+phone+"]查询["+merchantId.getValue()+"]的申请订单");
    	List<Apply> applys=null;
    	if(args.get("special")!=null){
    		args.put("state", ConstParam.APPLY_STATE_LOAN);
    		applys=dao.selectApplyByParam(args);
    		args.put("state", ConstParam.APPLY_STATE_OVERDUE);
    		applys.addAll(dao.getApplyDao().selectApplyByParam(args));
    		args.put("state", ConstParam.APPLY_STATE_FINISH);
    		applys.addAll(dao.getApplyDao().selectApplyByParam(args));
    	}else{
    		applys=dao.getApplyDao().selectApplyByParam(args);
//    		System.err.println(applys.size());
    	}
    	
//    	if (args.get("pageSize") == null && args.get("pageCode") == null) {
//    		LogFactory.info(this, "用户["+phone+"]，导出excel");
//    		Map<String, Object> result=new HashMap<>();
//    		result.put("applys", applys);
//    		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"查询订单成功！", result);
//		}
//    	System.err.println(applys.size());
//    	System.err.println(applys);
    	PageBean page = new PageBean();  
    	int pageSize = Integer.parseInt((String) args.get("pageSize")==null?"1":(String) args.get("pageSize"));
		int pageCode = Integer.parseInt((String) args.get("pageCode")==null?"1":(String) args.get("pageCode"));
		page.setPageSize(pageSize);
		page.setPageCode(pageCode);
		page.setTotalRecord(applys.size());
    	LogFactory.info(this, "用户["+phone+"]根据条件["+args+"]查询到的申请订单["+"暂不显示"+"]");
    	int count=applys.size();
    	BigDecimal moneyAmount=new BigDecimal(0);
    	for(Apply each:applys){
    		moneyAmount=moneyAmount.add(each.getApplyMoney()); 
    	}
    	
    	args.put("start", page.getStart());
    	args.put("size", page.getPageSize()); 
		List<Apply> list =null;
		if(args.get("special")!=null){
			list=new ArrayList<>();
			page.setTotalRecord(applys.size());		
			for(int i=page.getStart();i<page.getEnd();i++){
				list.add(applys.get(i));
			}
//			System.err.println("page:"+page);
//			System.err.println("applys:"+applys);
//			System.err.println("list:"+list);
		}else{
			list=dao.selectApplyByPage(args);
		
		}
		page.setTotalRecord(applys.size());
//		System.err.println(list.size());
    	Map<String,Object> statistics=new HashMap<>();
    	statistics.put("applyAmount", count);
    	statistics.put("moneyAmount", moneyAmount);
    	LogFactory.info(this, "用户["+phone+"]根据条件["+args+"]查询到的申请订单统计结果["+statistics+"]");
    	Map<String, Object> result=new HashMap<>();
    	result.put("applys", list);
    	if(args.get("excel")!=null) {
    		result.put("applys", applys);
    	}
    	result.put("statistics", statistics);
    	result.put("page", page);
    	LogFactory.info(this, "用户["+phone+"]根据条件["+args+"]查询申请订单成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"查询订单成功！", result);
    }
}
