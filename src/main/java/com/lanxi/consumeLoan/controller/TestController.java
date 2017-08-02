package com.lanxi.consumeLoan.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.functions.AdminApplyQueryFunction;
import com.lanxi.consumeLoan.functions.AdminChargeQueryFunction;
import com.lanxi.consumeLoan.functions.AdminMerchantQueryFunction;
import com.lanxi.consumeLoan.functions.ChangePasswordFunction;
import com.lanxi.consumeLoan.functions.CustomerManagerApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.LoanFunction;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction;
import com.lanxi.consumeLoan.functions.MerchantBindingFunction;
import com.lanxi.consumeLoan.functions.MerchantDeleteFunction;
import com.lanxi.consumeLoan.functions.MerchantDetailQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantShelveFunction;
import com.lanxi.consumeLoan.functions.MerchantUnsheleveFunction;
import com.lanxi.consumeLoan.functions.OverdueRecordFunction;
import com.lanxi.consumeLoan.functions.RejectFunction;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.ExcelUtil;
import com.lanxi.util.utils.HttpUtil;
import com.lanxi.util.utils.TimeUtil;

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
			LogFactory.error(this, "用户["+phone+"]登录时发生异常!",e);
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
			LogFactory.error(this, "用户["+phone+"]获取图片验证码时发生异常!",e);
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
			LogFactory.error(this, "用户["+phone+"]修改密码时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"修改密码时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="merchantShelve",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String merchantShelve(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantShelveFunction fun=ac.getBean(MerchantShelveFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("merchantId",req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "商户["+req.getParameter("merchantId")+"]上架时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"商户上架时发生异常!",null).toJson();
		}
	}

	@RequestMapping(value="merchantUnShelve",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String merchantUnShelve(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantUnsheleveFunction fun=ac.getBean(MerchantUnsheleveFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("merchantId",req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "商户["+req.getParameter("merchantId")+"]下架时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"商户下架时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="merchantDetailQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String merchantDetailQuery(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantDetailQueryFunction fun=ac.getBean(MerchantDetailQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("merchantId",req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "查询商户["+req.getParameter("merchantId")+"]详情时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"查询商户详情时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="merchantDelete",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String merchantDelete(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantDeleteFunction fun=ac.getBean(MerchantDeleteFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("merchantId",req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "商户["+req.getParameter("merchantId")+"]上架时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"商户上架时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="merchantQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String merchantQuery(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantQueryFunction fun=ac.getBean(MerchantQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("state",req.getParameter("state"));
			args.put("merchantName",req.getParameter("merchantName"));
			args.put("merchantType",req.getParameter("merchantType"));
			args.put("isAssurance",req.getParameter("isAssurance"));
			args.put("start_time",req.getParameter("startTime"));
			args.put("end_time",req.getParameter("endTime"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "查询商户列表时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"查询商户列表时发生异常!",null).toJson();
		}
	}

	@RequestMapping(value="overdueRecord",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String overdueRecord(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			OverdueRecordFunction fun=ac.getBean(OverdueRecordFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("breakMoney",req.getParameter("breakMoney"));
			args.put("merchantId",req.getParameter("merchantId"));
			args.put("applyId",req.getParameter("applyId"));
			args.put("breakTime",req.getParameter("breakTime"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "申请["+req.getParameter("applyId")+"]违约时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"申请违约时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="adminApplyQueryFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String adminApplyQueryFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminApplyQueryFunction fun=ac.getBean(AdminApplyQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("userPhone") !=null && req.getParameter("userPhone") != ""){
				args.put("userPhone", req.getParameter("userPhone"));
			}
			if(req.getParameter("isAssurance") !=null && req.getParameter("isAssurance") != ""){
				args.put("isAssurance", req.getParameter("isAssurance"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员查询申请订单列表时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员查询申请订单列表时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="adminChargeQueryFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String adminChargeQueryFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminChargeQueryFunction fun=ac.getBean(AdminChargeQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("customerPhone") !=null && req.getParameter("customerPhone") != ""){
				args.put("customerPhone", req.getParameter("customerPhone"));
			}
			if(req.getParameter("isOverdue") !=null && req.getParameter("isOverdue") != ""){
				args.put("isOverdue", req.getParameter("isOverdue"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员查询申请订单列表时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员查询申请订单列表时发生异常!",null).toJson();
		}
	}
	
	
	
	
	@RequestMapping(value="adminMerchantQueryFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminMerchantQueryFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			AdminMerchantQueryFunction fun=ac.getBean(AdminMerchantQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			
			if(req.getParameter("state") !=null && req.getParameter("state") != ""){
				args.put("state", req.getParameter("state"));
			}
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("merchantType") !=null && req.getParameter("merchantType") != ""){
				args.put("merchantType", req.getParameter("merchantType"));
			}
			if(req.getParameter("isAssurance") !=null && req.getParameter("isAssurance") != ""){
				args.put("isAssurance", req.getParameter("isAssurance"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			if(req.getParameter("customerManagerPhone") !=null && req.getParameter("customerManagerPhone") != ""){
				args.put("customerManagerPhone",req.getParameter("customerManagerPhone"));
			}
			return fun.excuted(args).toJson();
			} catch (Exception e) {
				LogFactory.error(this, "管理员查询商家列表时发生异常!",e);
				return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员查询商家列表时发生异常!",null).toJson();
			}
		}
		
	
	@RequestMapping(value="customerManagerApplyOrderQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String customerManagerApplyOrderQuery(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			CustomerManagerApplyOrderQueryFunction fun=ac.getBean(CustomerManagerApplyOrderQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("merchantName",req.getParameter("merchantName"));
			args.put("merchantType",req.getParameter("merchantType"));
			args.put("name",req.getParameter("name"));
			args.put("state",req.getParameter("state"));
			args.put("start_time",req.getParameter("startTime"));
			args.put("end_time",req.getParameter("endTime"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "客户经理查询时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"客户经理查询时发生异常!",null).toJson();
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="merchantQueryFunctionExport",produces = {"application/json;charset=UTF-8"})
	public void merchantQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			MerchantQueryFunction fun=ac.getBean(MerchantQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			
			if(req.getParameter("state") !=null && req.getParameter("state") != ""){
				args.put("state", req.getParameter("state"));
			}
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("merchantType") !=null && req.getParameter("merchantType") != ""){
				args.put("merchantType", req.getParameter("merchantType"));
			}
			if(req.getParameter("isAssurance") !=null && req.getParameter("isAssurance") != ""){
				args.put("isAssurance", req.getParameter("isAssurance"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			RetMessage message=fun.excuted(args);
			List<Merchant> list=(List<Merchant>)((Map<String,Object>)message.getResult()).get("merchants");
			LogFactory.info (this, "用户["+phone+"]已获得商户查询结果列表!");
			Map<String, String> map=new HashMap<>();
			map.put("merchantId", "商家编号");
			map.put("merchantName", "商家名称");
			map.put("merchantType", "商家类型");
			map.put("merchantAddress", "经营地址");
			map.put("partnerTime", "合作时间");
			map.put("isAssurance", "是否担保");
			map.put("state", "商家状态");
			LogFactory.info (this, "用户["+phone+"]已生成excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(list, map, res.getOutputStream());
			LogFactory.info (this, "用户["+phone+"]excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]导出商户查询excel时发生异常!",e);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="customerManagerApplyOrderQueryExport",produces = {"application/json;charset=UTF-8"})
	public void customerManagerApplyOrderQueryExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			CustomerManagerApplyOrderQueryFunction fun=ac.getBean(CustomerManagerApplyOrderQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			String userPhone=req.getParameter("userPhone");
			if(userPhone!=null&&!userPhone.isEmpty())
				args.put("userPhone", userPhone);
			String merchantName=req.getParameter("merchantName");
			if(merchantName!=null&&!merchantName.isEmpty())
				args.put("merchantName", merchantName);
			String merchantType=req.getParameter("merchantType");
			if(merchantType!=null&&!merchantType.isEmpty())
				args.put("merchantType", merchantType);
			String name=req.getParameter("name");
			if(name!=null&&!name.isEmpty())
				args.put("name", name);
			String state=req.getParameter("state");
			if(state!=null&&!state.isEmpty())
				args.put("state", state);
			String start_time=req.getParameter("startTime");
			if(start_time!=null&&!start_time.isEmpty())
				args.put("start_time", start_time);
			String end_time=req.getParameter("endTime");
			if(end_time!=null&&!end_time.isEmpty())
				args.put("end_time", end_time);
			RetMessage message=fun.excuted(args);
			List<Apply> list=(List<Apply>)((Map<String,Object>)message.getResult()).get("applys");
			LogFactory.info (this, "用户["+phone+"]已获得商户订单查询结果列表!");
			Map<String, String> map=new HashMap<>();
			map.put("applyId", "申请编号");
			map.put("name", "申请者姓名");
			map.put("phone", "申请者手机号码");
			map.put("idNumber", "申请者身份证号码");
			map.put("merchantName", "申请商户名称");
			map.put("merchantType", "申请商户类别");
			map.put("applyMoney", "申请金额");
			map.put("applyTime", "申请时间");
			map.put("isAssurance", "是否担保");
			map.put("state", "申请状态");
			map.put("loanTime", "放款时间");
			map.put("loanMoney", "放款金额");
			map.put("breakTime", "违约时间");
			map.put("breakMoney", "违约金额");
			LogFactory.info (this, "用户["+phone+"]已生成excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(list, map, res.getOutputStream());
			LogFactory.info (this, "用户["+phone+"]excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]导出商户订单查询excel时发生异常!",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="adminMerchantQueryFunctionExport",produces = {"application/json;charset=UTF-8"})
	public void adminMerchantQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			AdminMerchantQueryFunction fun=ac.getBean(AdminMerchantQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			
			if(req.getParameter("state") !=null && req.getParameter("state") != ""){
				args.put("state", req.getParameter("state"));
			}
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("merchantType") !=null && req.getParameter("merchantType") != ""){
				args.put("merchantType", req.getParameter("merchantType"));
			}
			if(req.getParameter("isAssurance") !=null && req.getParameter("isAssurance") != ""){
				args.put("isAssurance", req.getParameter("isAssurance"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			if(req.getParameter("customerManagerPhone") !=null && req.getParameter("customerManagerPhone") != ""){
				args.put("customerManagerPhone",req.getParameter("customerManagerPhone"));
			}
			RetMessage message=fun.excuted(args);
			List<Merchant> list=(List<Merchant>)((Map<String,Object>)message.getResult()).get("merchants");
			LogFactory.info (this, "用户["+phone+"]已获得商户查询结果列表!");
			Map<String, String> map=new HashMap<>();
			map.put("applyId", "申请编号");
			map.put("name", "申请者姓名");
			map.put("phone", "申请者手机号码");
			map.put("idNumber", "申请者身份证号码");
			map.put("merchantName", "申请商户名称");
			map.put("merchantType", "申请商户类别");
			map.put("applyMoney", "申请金额");
			map.put("applyTime", "申请时间");
			map.put("isAssurance", "是否担保");
			map.put("state", "申请状态");
			map.put("loanTime", "客户经理手机号码");
			map.put("loanMoney", "客户经理姓名");
			LogFactory.info (this, "用户["+phone+"]已生成excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(list, map, res.getOutputStream());
			LogFactory.info (this, "用户["+phone+"]excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]导出管理员商户列表查询查询excel时发生异常!",e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="adminApplyQueryFunctionExport",produces = {"application/json;charset=UTF-8"})
	public void adminApplyQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			AdminApplyQueryFunction fun=ac.getBean(AdminApplyQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("userPhone") !=null && req.getParameter("userPhone") != ""){
				args.put("userPhone", req.getParameter("userPhone"));
			}
			if(req.getParameter("isAssurance") !=null && req.getParameter("isAssurance") != ""){
				args.put("isAssurance", req.getParameter("isAssurance"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			RetMessage message=fun.excuted(args);
			List<Merchant> list=(List<Merchant>)((Map<String,Object>)message.getResult()).get("applys");
			LogFactory.info (this, "用户["+phone+"]已获得商户查询结果列表!");
			Map<String, String> map=new HashMap<>();
			map.put("merchantId", "商家编号");
			map.put("merchantName", "商家名称");
			map.put("merchantAddress", "经营地址");
			map.put("partnerTime", "合作时间");
			map.put("isAssurance", "是否担保");
			map.put("state", "商家状态");
			map.put("customerManagerName", "客户经理姓名");
			map.put("customerManagerPhone", "客户经理手机号码");
			LogFactory.info (this, "用户["+phone+"]已生成excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(list, map, res.getOutputStream());
			LogFactory.info (this, "用户["+phone+"]excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]导出管理员商户列表查询查询excel时发生异常!",e);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="adminChargeQueryFunctionExport",produces = {"application/json;charset=UTF-8"})
	public void adminChargeQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			AdminChargeQueryFunction fun=ac.getBean(AdminChargeQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			
			if(req.getParameter("merchantName") !=null && req.getParameter("merchantName") != ""){
				args.put("merchantName", req.getParameter("merchantName"));
			}
			if(req.getParameter("customerPhone") !=null && req.getParameter("customerPhone") != ""){
				args.put("customerPhone", req.getParameter("customerPhone"));
			}
			if(req.getParameter("isOverdue") !=null && req.getParameter("isOverdue") != ""){
				args.put("isOverdue", req.getParameter("isOverdue"));
			}
			if(req.getParameter("startTime") !=null && req.getParameter("startTime") != ""){
				args.put("start_time",req.getParameter("startTime"));
			}
			if(req.getParameter("endTime") !=null && req.getParameter("endTime") != ""){
				args.put("end_time",req.getParameter("endTime"));
			}
			RetMessage message=fun.excuted(args);
			List<Apply> list=(List<Apply>)((Map<String,Object>)message.getResult()).get("applys");
			LogFactory.info (this, "用户["+phone+"]已获得商户查询结果列表!");
			Map<String, String> map=new HashMap<>();
			map.put("applyId", "申请编号");
			map.put("name", "姓名");
			map.put("phone", "手机号码");
			map.put("idNumber", "身份证号码");
			map.put("applyMoney", "申请金额");
			map.put("merchantName", "所属商家名称");
			map.put("merchantType", "所属商家类别");
			
			map.put("loanMoney", "放款金额");
			map.put("loanTime", "放款时间");
			map.put("brokerage", "佣金金额");
			map.put("serviceCharge", "服务费金额");
			map.put("breakMoney", "消耗保证金金额");
			LogFactory.info (this, "用户["+phone+"]已生成excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(list, map, res.getOutputStream());
			LogFactory.info (this, "用户["+phone+"]excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]导出管理员商户列表查询查询excel时发生异常!",e);
		}
	}
	
	
	@RequestMapping(value="loanFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String loanFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			LoanFunction fun=ac.getBean(LoanFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("applyId",req.getParameter("applyId"));
			args.put("loanMoney",req.getParameter("loanMoney"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "放款时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"放款时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="rejectFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String rejectFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			RejectFunction fun=ac.getBean(RejectFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("applyId",req.getParameter("applyId"));
			args.put("reason",req.getParameter("reason"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "驳回时时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"驳回时时发生异常!",null).toJson();
		}
	}
	
}
