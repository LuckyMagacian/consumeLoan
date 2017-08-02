package com.lanxi.consumeLoan.functions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
@Service
public class AdminApplyQueryFunction extends AbstractFunction{

	@Override
	public RetMessage successNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage failNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage showInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage excuted(Map<String, Object> args) {
		String phone = (String) args.get("phone");
		if (!checkService.checkAuthority(phone, this.getClass().getName())) {
			LogFactory.info(this, "没有权限执行该操作!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没有权限!", null);
		}
		Map<String, Object> parm = new HashMap<String, Object>();
		if(args.get("isAssurance") != "" && args.get("isAssurance") !=null){
			parm.put("isAssurance", args.get("isAssurance"));
		}
		if(args.get("userPhone") != "" && args.get("userPhone") !=null){
			parm.put("userPhone", args.get("userPhone"));
		}
		if(args.get("merchantName") != "" && args.get("merchantName") !=null){
			parm.put("merchantName", args.get("merchantName"));
		}
		if(args.get("start_time") != "" && args.get("start_time") !=null){
			parm.put("start_time", args.get("start_time"));
		}
		if(args.get("end_time") != "" && args.get("end_time") !=null){
			parm.put("end_time", args.get("end_time"));
		}
		LogFactory.info(this, "请求参数：" + parm.toString());
		List<Apply> applys = dao.getApplyDao().selectApplyByParam(parm);
		if(applys ==null || applys.size()<=0){
			LogFactory.info(this, "没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		
		Map<String, Object> resultMap =new HashMap<>();
		resultMap.put("applys", applys);
		LogFactory.info(this, "商户查询成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "商户查询成功!", resultMap);	
	}

}
