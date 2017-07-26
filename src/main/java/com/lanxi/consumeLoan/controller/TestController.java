package com.lanxi.consumeLoan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.util.utils.HttpUtil;

@Controller
@RequestMapping("test")
public class TestController {
	@Resource
	private ApplicationContextProxy ac;
	@RequestMapping(value="login")
	@ResponseBody
	public String login(HttpServletRequest req,HttpServletResponse res){
//		HttpUtil.setEncode(req, null);
		Map<String, Object> args=new HashMap<>();
		args.put("phone", req.getParameter("phone"));
		args.put("password", req.getParameter("password"));
		args.put("validateCode", req.getParameter("validateCode"));
		args.put("ip", HttpUtil.getRealIp(req));
		return ac.getBean(LoginFunction.class).excuted(args).toJson();
	}
	@RequestMapping(value="getPicCode")
	public void getPic(HttpServletRequest req,HttpServletResponse res){
//		HttpUtil.setEncode(req, null);
		MakeValidateCodePicFunction codeFun=ac.getBean(MakeValidateCodePicFunction.class);
		Map<String, Object> map=new HashMap<>();
		map.put("res", res);
		map.put("phone", req.getParameter("phone"));
		codeFun.excuted(map);
	}
}
