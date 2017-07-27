package com.lanxi.consumeLoan.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.Args;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.functions.ChangePasswordFunction;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction;
import com.lanxi.consumeLoan.functions.MerchantBindingFunction;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.HttpUtil;

@Controller
@RequestMapping("test")
public class TestController {
	@Resource
	private ApplicationContextProxy ac;
	
	@RequestMapping(value="test",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String test1(HttpServletRequest req,HttpServletResponse res){ 
		try {
			System.out.println(JSONObject.toJSONString(req.getParameterMap()));
			return JSONObject.toJSONString(req.getParameterMap());
		} catch (Exception e) {
			LogFactory.error(this, "测试时发生异常!");
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"测试时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="bindUser",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String bindUser(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantBindingFunction fun=ac.getBean(MerchantBindingFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone", req.getParameter("phone"));
			args.put("userPhone", req.getParameter("userPhone"));
			args.put("userName", req.getParameter("userName"));
			args.put("merchantId", req.getParameter("merchantId"));
			args.put("roleName", req.getParameter("roleName"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]绑定商户时发生异常!");
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"绑定时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="login",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String login(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			LoginFunction fun=ac.getBean(LoginFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone", req.getParameter("phone"));
			args.put("password", req.getParameter("password"));
			args.put("validateCode", req.getParameter("validateCode"));
			args.put("ip", HttpUtil.getRealIp(req));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]登录时发生异常!");
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"登录时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="getPicCode",produces = {"application/json;charset=UTF-8"})
	public void getPic(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MakeValidateCodePicFunction fun=ac.getBean(MakeValidateCodePicFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("res", res);
			args.put("phone", req.getParameter("phone"));
			fun.excuted(args);
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]获取图片验证码时发生异常!");
		}
	}
	@RequestMapping(value="changePassword",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String changePassword(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			ChangePasswordFunction fun=ac.getBean(ChangePasswordFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",req.getParameter("phone"));
			args.put("newPassword",req.getParameter("newPassword"));
			args.put("passwordRepeat",req.getParameter("passwordRepeat"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]修改密码时发生异常!");
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"修改密码时发生异常!",null).toJson();
		}
	}

}
