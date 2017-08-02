package com.lanxi.consumeLoan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.functions.CustomerManagerMerchantQueryFunction;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

@Controller
@RequestMapping("manager")
public class ManagerController {
	@Resource
	private ApplicationContextProxy ac;
	
	@RequestMapping(value="customerManagerMerchantQueryFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String customerManagerMerchantQueryFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			CustomerManagerMerchantQueryFunction fun=ac.getBean(CustomerManagerMerchantQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone", req.getParameter("phone"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]查询该商家列表时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"查询该商家列表时发生异常!",null).toJson();
		}
	}

}
