package com.lanxi.consumeLoan.functions;

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

@Service
public class CustomerManagerApplyOrderQueryFunction extends AbstractFunction {

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
		PageBean page = new PageBean();
		int pageCode = Integer.parseInt((String)args.get("pageCode")==null?"1":(String)args.get("pageCode"));
		int pageSize = Integer.parseInt((String)args.get("pageSize")==null?"1":(String)args.get("pageSize"));
		page.setPageSize(pageSize);
		page.setPageCode(pageCode);
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("customerPhone", phone);
//		List<Apply> applyList = dao.getApplyDao().selectApplyByParam(parm);
//		if (applyList == null || applyList.size()<=0) {
//			LogFactory.info(this, "客户经理["+phone+"],没查询到数据!");
//			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "没查询到数据!", null);
//		}
		
		
		if(args.get("name") != "" && args.get("name") !=null){
			parm.put("name", (String)args.get("name"));
		}
		if(args.get("userPhone") != "" && args.get("userPhone") !=null){
			parm.put("userPhone", args.get("userPhone"));
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
		
		LogFactory.info(this, "客户经理["+phone+"],请求参数：" + parm.toString());
		List<Apply> applys = dao.selectApplyByParam(parm);
//		if(applys ==null || applys.size()<=0){
//			LogFactory.info(this, "客户经理["+phone+"],没查询到数据!");
//			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "没查询到数据!", null);
//		}
		page.setTotalRecord(applys.size());		
		parm.put("start", page.getStart());
		parm.put("size", page.getPageSize());
		List<Apply> list = dao.selectApplyByPage(parm);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (args.get("excel") != null) 
			resultMap.put("applys", applys);
		else
			resultMap.put("applys", list);
		
		resultMap.put("page", page);
		LogFactory.info(this, "客户经理["+phone+"],客户经理申请订单列表查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "客户经理申请订单列表查询成功!", resultMap);
	}
}
