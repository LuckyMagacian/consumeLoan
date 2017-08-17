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

/**
 * 客户经理申请列表查询
 * @author lx
 *
 */
@Service
public class AdminApplyQueryFunction extends AbstractFunction{

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
		
		PageBean page = new PageBean();
		page.setPageSize(Integer.parseInt((String) args.get("pageSize")==null?"1":(String) args.get("pageSize")));
		page.setPageCode(Integer.parseInt((String) args.get("pageCode")==null?"1":(String) args.get("pageCode")));
		Map<String, Object> parm = new HashMap<String, Object>();
		
		if(args.get("isAssurance") != "" && args.get("isAssurance") !=null){
			parm.put("isAssurance", (String)args.get("isAssurance"));
		}
		if(args.get("userPhone") != "" && args.get("userPhone") !=null){
			parm.put("userPhone", (String)args.get("userPhone"));
		}
		if(args.get("merchantName") != "" && args.get("merchantName") !=null){
			parm.put("merchantName", (String)args.get("merchantName"));
		}
		if(args.get("start_time") != "" && args.get("start_time") !=null){
			parm.put("start_time",(String)args.get("start_time"));
		}
		if(args.get("end_time") != "" && args.get("end_time") !=null){
			parm.put("end_time", (String)args.get("end_time"));
		}
		if(args.get("state") != "" && args.get("state") !=null){
			parm.put("state", (String)args.get("state"));
		}
		
		List<Apply> applys = dao.getApplyDao().selectApplyByParam(parm);
		if(applys ==null || applys.size()<=0){
			LogFactory.info(this, "管理员["+phone+"],没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		page.setTotalRecord(applys.size());		
		parm.put("start", page.getStart());
		parm.put("size", page.getPageSize());
		List<Apply> list = dao.getApplyDao().selectApplyByPage(parm);
		for (Apply apply : list) {
			apply.hide4();
		}
		Map<String, Object> resultMap =new HashMap<>();
		if(args.get("excel")!=null)
			resultMap.put("applys", applys);
		else
			resultMap.put("applys", list);
		resultMap.put("page", page);
		LogFactory.info(this, "管理员["+phone+"],商户查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "商户查询成功!", resultMap);	
	}

}
