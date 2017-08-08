package com.lanxi.consumeLoan.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.dao.ApplyDao;
import com.lanxi.consumeLoan.dao.MerchantDao;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.functions.AdminApplyQueryFunction;
import com.lanxi.consumeLoan.functions.AdminChargeQueryFunction;
import com.lanxi.consumeLoan.functions.AdminMerchantAddFunction;
import com.lanxi.consumeLoan.functions.AdminMerchantQueryFunction;
import com.lanxi.consumeLoan.functions.AdminUserAddFunction;
import com.lanxi.consumeLoan.functions.AdminUserCheckBackFunction;
import com.lanxi.consumeLoan.functions.AdminUserCheckFunction;
import com.lanxi.consumeLoan.functions.AdminUserDeleteFunction;
import com.lanxi.consumeLoan.functions.AdminUserModifyFunction;
import com.lanxi.consumeLoan.functions.AdminUserQueryFunction;
import com.lanxi.consumeLoan.functions.AdminUserStateUpdateFunction;
import com.lanxi.consumeLoan.functions.ApplyOrderAddFunction;
import com.lanxi.consumeLoan.functions.ChangePasswordFunction;
import com.lanxi.consumeLoan.functions.CustomerManagerApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.CustomerManagerUserQueryFunction;
import com.lanxi.consumeLoan.functions.CustomerShopEmployeeAddFunction;
import com.lanxi.consumeLoan.functions.LoanFunction;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction;
import com.lanxi.consumeLoan.functions.MerchantApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantBindingFunction;
import com.lanxi.consumeLoan.functions.MerchantDeleteFunction;
import com.lanxi.consumeLoan.functions.MerchantDetailQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantHomeFunction;
import com.lanxi.consumeLoan.functions.MerchantModifyFunction;
import com.lanxi.consumeLoan.functions.MerchantQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantShelveFunction;
import com.lanxi.consumeLoan.functions.MerchantUnsheleveFunction;
import com.lanxi.consumeLoan.functions.OverdueRecordFunction;
import com.lanxi.consumeLoan.functions.RejectFunction;
import com.lanxi.consumeLoan.functions.SalesManHomeFunction;
import com.lanxi.consumeLoan.functions.ValidateCodeSendFunction;
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
			System.out.println(req.getParameter("laosiji"));
			System.out.println(JSONObject.parseObject(req.getParameter("laosiji")));
			return JSONObject.toJSONString(req.getParameterMap());
		} catch (Exception e) {
			LogFactory.error(this, "测试时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"测试时发生异常!",null).toJson();
		}
	} 

	@Deprecated
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
			LogFactory.error(this, "用户["+phone+"]绑定商户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"绑定时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="login",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	protected String login(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			System.out.println(phone);
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
	@RequestMapping(value="getPicCode")
	protected void getPic(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MakeValidateCodePicFunction fun=ac.getBean(MakeValidateCodePicFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("res", res);
			//从绑定帐号改为绑定ip
			args.put("phone", HttpUtil.getRealIp(req));
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
			args.put("oldPassword", req.getParameter("oldPassword"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]修改密码时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"修改密码时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="getMerchantHomeInfo",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMerchantHomeInfo(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			MerchantHomeFunction fun=ac.getBean(MerchantHomeFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]获取商户主页信息时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"获取商户主页信息时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="getSalesManHomeInfo",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getSalesManHomeInfo(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			SalesManHomeFunction fun=ac.getBean(SalesManHomeFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]获取销售员主页信息时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"获取销售员主页信息时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="addApply",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addApply(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			ApplyOrderAddFunction fun=ac.getBean(ApplyOrderAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("apply",req.getParameter("apply"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]获取销售员主页信息时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"获取销售员主页信息时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="sendValidateCode",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String sendValidateCode(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			ValidateCodeSendFunction fun=ac.getBean(ValidateCodeSendFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]为["+req.getParameter("userPhone")+"]发送短信验证码时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"发送短信验证码时发生异常!",null).toJson();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="merchantApplyQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String merchantApplyQuery(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			MerchantApplyOrderQueryFunction fun=ac.getBean(MerchantApplyOrderQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			String userPhone=req.getParameter("userPhone");
			if(userPhone!=null&&!userPhone.isEmpty())
				args.put("userPhone", userPhone);
			String state=req.getParameter("state");
			if(state!=null&&!state.isEmpty())
				args.put("state", state);
			String startTime=req.getParameter("startTime");
			if(startTime!=null&&!startTime.isEmpty())
				args.put("startTime", startTime);
			String endTime=req.getParameter("endTime");
			if(endTime!=null&&!endTime.isEmpty())
				args.put("endTime", endTime);
			String pageSize=req.getParameter("pageSize");
			if(pageSize!=null&&!pageSize.isEmpty())
				args.put("pageSize", pageSize);
			String pageCode=req.getParameter("pageCode");
			if(pageCode!=null&&!pageCode.isEmpty())
				args.put("pageCode", pageCode);
			RetMessage result= fun.excuted(args);
			if(!result.getCode().equals(RetCodeEnum.SUCCESS.toString())){
				LogFactory.info(this, "用户["+phone+"]商户订单查询失败直接返回!");
				return result.toJson();
			}
			List<Apply> list=(List<Apply>)((Map<String,Object>)result.getResult()).get("applys");
			if(list.isEmpty()){
				LogFactory.info(this, "用户["+phone+"]商户订单查询结果为空直接返回!");
				return result.toJson();
			}
			for(Apply each:list){
				each.hide2();
			}
			LogFactory.info(this, "去除无用字段信息!");
			return result.toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]商户订单查询时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"商户订单查询时发生异常!",null).toJson();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="merchantApplyQueryExport")
	public void merchantApplyQueryExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			MerchantApplyOrderQueryFunction fun=ac.getBean(MerchantApplyOrderQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			String userPhone=req.getParameter("userPhone");
			if(userPhone!=null&&!userPhone.isEmpty())
				args.put("userPhone", userPhone);
			String state=req.getParameter("state");
			if(state!=null&&!state.isEmpty())
				args.put("state", state);
			String startTime=req.getParameter("startTime");
			if(startTime!=null&&!startTime.isEmpty())
				args.put("startTime", startTime);
			String endTime=req.getParameter("endTime");
			if(endTime!=null&&!endTime.isEmpty())
				args.put("endTime", endTime);
			RetMessage message=fun.excuted(args);
			if(!message.getCode().equals(RetCodeEnum.SUCCESS.toString())){
				LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询失败,不生成excel文件!");
				return ;
			}
			List<Apply> list=(List<Apply>)((Map<String,Object>)message.getResult()).get("applys");
			for(Apply each:list){
				switch (each.getState()) {
				case ConstParam.APPLY_STATE_WAIT_CHECK:each.setState("待审核");break;
				case ConstParam.APPLY_STATE_REJECT:each.setState("已驳回");break;
				case ConstParam.APPLY_STATE_LOAN:each.setState("已放款");break;
				case ConstParam.APPLY_STATE_OVERDUE:each.setState("已违约");break;
				case ConstParam.APPLY_STATE_FINISH:each.setState("已完成");break;
				default:each.setState("未知");break;
				}
			}
			LogFactory.info(this, "用户["+phone+"]已获得商户订单查询结果列表!");
			Map<String, String> map=new HashMap<>();
			map.put("applyId", "申请编号");
			map.put("name", "申请者姓名");
//			map.put("sex", "申请者性别");
//			map.put("address", "申请者地址");
			map.put("idNumber", "申请者身份证号码");
			map.put("phone", "申请者手机号码");
			map.put("applyMoney", "申请金额");
//			map.put("verifyCode", "申请时所填验证码");
//			map.put("merchantId", "申请商户编号");
//			map.put("salesManPhone", "申请商户销售员手机号");
			map.put("applyTime", "申请时间");
//			map.put("loanTime", "放款时间");
//			map.put("loanMoney", "放款金额");
//			map.put("isOverdue", "是否逾期");
//			map.put("overdueMoney", "逾期金额");
			map.put("state", "申请状态");
//			map.put("brokerageRate", "申请时佣金比例");
//			map.put("sharedRate", "申请时分润比例");
//			map.put("depositeRate", "申请时保证金比例");
//			map.put("breakTime", "违约时间");
//			map.put("breakMoney", "违约金额");
//			map.put("merchantName", "申请商户名称");
//			map.put("brokerage", "佣金");
			LogFactory.info(this, "用户["+phone+"]已生成商户订单查询excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(list, map, res.getOutputStream());
			LogFactory.info(this, "用户["+phone+"]商户订单查询excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]导出商户订单查询excel时发生异常!",e);
		}
	}
	
	@RequestMapping(value="merchantShelve",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String merchantShelve(HttpServletRequest req,HttpServletResponse res){
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
	public String merchantUnShelve(HttpServletRequest req,HttpServletResponse res){
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
	public String merchantDetailQuery(HttpServletRequest req,HttpServletResponse res){
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
	public String merchantDelete(HttpServletRequest req,HttpServletResponse res){
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
	public String merchantQuery(HttpServletRequest req,HttpServletResponse res){
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
			args.put("pageCode",req.getParameter("pageCode"));
			args.put("pageSize",req.getParameter("pageSize"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "查询商户列表时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"查询商户列表时发生异常!",null).toJson();
		}
	}

	@RequestMapping(value="overdueRecord",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String overdueRecord(HttpServletRequest req,HttpServletResponse res){
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
	public String adminApplyQueryFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminApplyQueryFunction fun=ac.getBean(AdminApplyQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("pageSize", req.getParameter("pageSize"));
			args.put("pageCode", req.getParameter("pageCode"));
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
	public String adminChargeQueryFunction(HttpServletRequest req,HttpServletResponse res){
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
			if(req.getParameter("pageSize") !=null && req.getParameter("pageSize") != ""){
				args.put("pageSize",req.getParameter("pageSize"));
			}
			if(req.getParameter("pageCode") !=null && req.getParameter("pageCode") != ""){
				args.put("pageCode",req.getParameter("pageCode"));
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
			args.put("pageSize",req.getParameter("pageSize"));
			args.put("pageCode",req.getParameter("pageCode"));
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
	public String customerManagerApplyOrderQuery(HttpServletRequest req,HttpServletResponse res){
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
			args.put("pageCode",req.getParameter("pageCode"));
			args.put("pageSize",req.getParameter("pageSize")); 
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "客户经理查询时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"客户经理查询时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="merchantQueryFunctionExport")
	public void merchantQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			MerchantDao fun=ac.getBean(MerchantDao.class);
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
		
			List<Merchant> list=fun.selectAdminMerchantByParm(args);
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

	@RequestMapping(value="customerManagerApplyOrderQueryExport")
	public void customerManagerApplyOrderQueryExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			ApplyDao fun=ac.getBean(ApplyDao.class);
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
			
			List<Apply> list=fun.selectApplyByParam(args);
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
	@RequestMapping(value="merchantBrokerageQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String merchantBrokerageQuery(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试进行商户佣金查询!");
			MerchantApplyOrderQueryFunction fun=ac.getBean(MerchantApplyOrderQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			String userPhone=req.getParameter("userPhone");
			if(userPhone!=null&&!userPhone.isEmpty())
				args.put("userPhone", userPhone);
//			String state=req.getParameter("state");
			args.put("state", ConstParam.APPLY_STATE_LOAN);
			String startTime=req.getParameter("startTime");
			if(startTime!=null&&!startTime.isEmpty())
				args.put("startTime", startTime);
			String endTime=req.getParameter("endTime");
			if(endTime!=null&&!endTime.isEmpty())
				args.put("endTime", endTime);
			String pageSize=req.getParameter("pageSize");
			if(pageSize!=null&&!pageSize.isEmpty())
				args.put("pageSize", pageSize);
			String pageCode=req.getParameter("pageCode");
			if(pageCode!=null&&!pageCode.isEmpty())
				args.put("pageCode", pageCode);
			RetMessage result= fun.excuted(args);
			if(!result.getCode().equals(RetCodeEnum.SUCCESS.toString())){
				LogFactory.info(this, "用户["+phone+"]商户佣金查询失败,直接返回查询结果!");
				return result.toJson();
			}
			Map<String, Object> temp=((Map<String,Object>)result.getResult());
			temp.remove("statistics");
			LogFactory.info(this, "用户["+phone+"]尝试进行商户佣金查询,移除订单查询接口生成的统计数据!");
			List<Apply> applys=(List<Apply>) temp.get("applys");
			int count=applys.size();
	    	BigDecimal moneyAmount=new BigDecimal(0);
	    	BigDecimal brokerageAmount=new BigDecimal(0);
	    	for(Apply each:applys){
	    		if(each.getLoanMoney()!=null)
	    			moneyAmount=moneyAmount.add(each.getLoanMoney());
	    		if(each.getBreakMoney()!=null)
	    			brokerageAmount=brokerageAmount.add(each.getBrokerage());
	    	}
	    	Map<String,Object> statistics=new HashMap<>();
	    	statistics.put("applyAmount", count);
	    	statistics.put("moneyAmount", moneyAmount);
	    	statistics.put("brokerageAmount", brokerageAmount);
	    	temp.put("statistics", statistics);
	    	LogFactory.info(this, "用户["+phone+"]尝试进行商户佣金查询,添加新统计数据["+statistics+"]完成!返回佣金查询结果!");
			if(applys.isEmpty()){
				LogFactory.info(this, "用户["+phone+"]商户订单查询结果为空直接返回!");
				return result.toJson();
			}
			for(Apply each:applys){
				each.hide1();
			}
			LogFactory.info(this, "去除无用字段信息!"); 
	    	return result.toJson();
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]商户佣金查询时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"商户佣金查询时发生异常!",null).toJson();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="merchantBrokerageQueryExport")
	@ResponseBody
	public void merchantBrokerageQueryExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试进行商户佣金查询导出!");
			MerchantApplyOrderQueryFunction fun=ac.getBean(MerchantApplyOrderQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			String userPhone=req.getParameter("userPhone");
			if(userPhone!=null&&!userPhone.isEmpty())
				args.put("userPhone", userPhone);
//			String state=req.getParameter("state");
			args.put("state", ConstParam.APPLY_STATE_LOAN);
			String startTime=req.getParameter("startTime");
			if(startTime!=null&&!startTime.isEmpty())
				args.put("startTime", startTime);
			String endTime=req.getParameter("endTime");
			if(endTime!=null&&!endTime.isEmpty())
				args.put("endTime", endTime);
			RetMessage result= fun.excuted(args);
			if(!result.getCode().equals(RetCodeEnum.SUCCESS.toString())){
				LogFactory.info(this, "用户["+phone+"]商户佣金查询失败,不生成excel文件!");
			}
			Map<String, Object> temp=((Map<String,Object>)result.getResult());
			List<Apply> applys=(List<Apply>) temp.get("applys");
			for(Apply each:applys){
				switch (each.getState()) {
				case ConstParam.APPLY_STATE_WAIT_CHECK:each.setState("待审核");break;
				case ConstParam.APPLY_STATE_REJECT:each.setState("已驳回");break;
				case ConstParam.APPLY_STATE_LOAN:each.setState("已放款");break;
				case ConstParam.APPLY_STATE_OVERDUE:each.setState("已违约");break;
				case ConstParam.APPLY_STATE_FINISH:each.setState("已完成");break;
				default:each.setState("未知");break;
				}
			}
			Map<String, String> map=new HashMap<>();
			map.put("applyId", "申请编号");
			map.put("name", "申请者姓名");
			map.put("idNumber", "申请者身份证号码");
			map.put("phone", "申请者手机号码");
			map.put("applyMoney", "申请金额");
			map.put("applyTime", "申请时间");
			map.put("loanTime", "放款时间");
			map.put("loanMoney", "放款金额");
			map.put("state", "申请状态");
			map.put("brokerage", "佣金");
			LogFactory.info(this, "用户["+phone+"]已生成商户佣金查询excel文件!"); 
			res.setContentType("octets/stream");
			res.setHeader("Content-Disposition", "attachment;fileName="+TimeUtil.getDateTime()+".xls");
			ExcelUtil.exportExcelFile(applys, map, res.getOutputStream());
			LogFactory.info(this, "用户["+phone+"]商户佣金查询excel文件发送完成!");
		} catch (Exception e) {
			LogFactory.error(this, "用户["+phone+"]商户佣金查询excele导出时发生异常!",e);
		}
	}
		

	@RequestMapping(value="addMerchantAndEmployees",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addMerchantAndEmployees(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			AdminMerchantAddFunction fun=ac.getBean(AdminMerchantAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("merchant",req.getParameter("merchant"));
			args.put("shopKeepers",req.getParameter("shopKeepers"));
			args.put("salesMans",req.getParameter("salesMans"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]添加商户及员工时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员添加商户及员工时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="adminMerchantQueryFunctionExport")
	public void adminMerchantQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			MerchantDao fun=ac.getBean(MerchantDao.class);
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
			List<Merchant> list=fun.selectMerchantByParm(args);
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
	
	
	@RequestMapping(value="userQuery",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String userQuery(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {  
			AdminUserQueryFunction fun=ac.getBean(AdminUserQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName",req.getParameter("roleName"));
			args.put("startTime",req.getParameter("startTime"));
			args.put("endTime",req.getParameter("endTime"));
			args.put("merchantName", req.getParameter("merchantName"));
			args.put("state", req.getParameter("state"));
			args.put("pageSize",req.getParameter("pageSize"));
			args.put("pageCode",req.getParameter("pageCode"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]查询用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员查询用户时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="shopKeeperAdd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String shopKeeperAdd(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			CustomerShopEmployeeAddFunction fun=ac.getBean(CustomerShopEmployeeAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName","shopKeeper");
			args.put("name",req.getParameter("name"));
			args.put("merchantId",req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "客户经理["+phone+"]添加店长时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"客户经理添加店长时发生异常!",null).toJson();
		}
	}
	
	
	@RequestMapping(value="salesManAdd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String salesManAdd(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			CustomerShopEmployeeAddFunction fun=ac.getBean(CustomerShopEmployeeAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName","salesMan");
			args.put("name",req.getParameter("name"));
			args.put("merchantId",req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "客户经理["+phone+"]添加销售员时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"客户经理添加销售员时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="customerManagerAdd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String customerManagerAdd(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			AdminUserAddFunction fun=ac.getBean(AdminUserAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName","customerManager");
			args.put("name",req.getParameter("name"));
			args.put("netAddress",req.getParameter("netAddress"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]添加客户经理时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员添加客户经理时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="adminAdd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminAdd(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			AdminUserAddFunction fun=ac.getBean(AdminUserAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName","admin");
			args.put("name",req.getParameter("name"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]添加管理员时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员添加管理员时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="adminApplyQueryFunctionExport")
	public void adminApplyQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			ApplyDao fun=ac.getBean(ApplyDao.class);
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
			List<Apply> list=fun.selectApplyByParam(args);
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
	
	
	
	@RequestMapping(value="adminChargeQueryFunctionExport")
	public void adminChargeQueryFunctionExport(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			LogFactory.info(this, "用户["+phone+"]尝试导出商户订单查询内容为excel文件!");
			ApplyDao fun=ac.getBean(ApplyDao.class);
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
			List<Apply> list = fun.selectApplyByParam(args);
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
	public String loanFunction(HttpServletRequest req,HttpServletResponse res){
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
	public String rejectFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			RejectFunction fun=ac.getBean(RejectFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("applyId",req.getParameter("applyId"));
			args.put("reason",req.getParameter("reason"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "驳回时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"驳回时时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="adminUserCheckFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminUserCheckFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminUserCheckFunction fun=ac.getBean(AdminUserCheckFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]审核用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"审核用户时发生异常！",null).toJson();
		}
	}
	
	@RequestMapping(value="customerManagerUserQueryFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String customerManagerUserQueryFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			CustomerManagerUserQueryFunction fun=ac.getBean(CustomerManagerUserQueryFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			if (req.getParameter("userPhone") != null && req.getParameter("userPhone") !="") {
				args.put("userPhone",req.getParameter("userPhone"));
			}
			if (req.getParameter("startTime") != null && req.getParameter("startTime") !="") {
				args.put("startTime",req.getParameter("startTime"));
			}
			if (req.getParameter("endTime") != null && req.getParameter("endTime") !="") {
				args.put("endTime",req.getParameter("endTime"));
			}
			if (req.getParameter("merchantName") != null && req.getParameter("merchantName") !="") {
				args.put("merchantName",req.getParameter("merchantName"));
			}
			if (req.getParameter("pageCode") != null && req.getParameter("pageCode") !="") {
				args.put("pageCode",req.getParameter("pageCode"));
			}
			if (req.getParameter("pageSize") != null && req.getParameter("pageSize") !="") {
				args.put("pageSize",req.getParameter("pageSize"));
			}
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "查询用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"查询用户时发生异常!",null).toJson();
		}
	}
	
	
	@RequestMapping(value="merchantModifyFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String merchantModifyFunction(HttpServletRequest req,HttpServletResponse res){
		Merchant merchant =new Merchant();
		
		String phone=req.getParameter("phone");
		try {
			MerchantModifyFunction fun=ac.getBean(MerchantModifyFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			merchant.setMerchantId(req.getParameter("merchantId"));
			if (req.getParameter("merchantType") != null && req.getParameter("merchantType") !="") {
				merchant.setMerchantType(req.getParameter("merchantType"));
			}
			if (req.getParameter("merchantName") != null && req.getParameter("merchantName") !="") {
				merchant.setMerchantName(req.getParameter("merchantName"));
			}
			if (req.getParameter("merchantAddress") != null && req.getParameter("merchantAddress") !="") {
				merchant.setMerchantAddress(req.getParameter("merchantAddress"));
			}
			if (req.getParameter("isAssurance") != null && req.getParameter("isAssurance") !="") {
				merchant.setIsAssurance(req.getParameter("isAssurance"));
			}
			if (req.getParameter("provideDeposit") != null && req.getParameter("provideDeposit") !="") {
				merchant.setProvideDeposit(req.getParameter("provideDeposit"));
			}
			if (req.getParameter("depositeRate") != null && req.getParameter("depositeRate") !="") {
				BigDecimal depositeRate = new BigDecimal(req.getParameter("depositeRate"));
				merchant.setDepositeRate(depositeRate);
			}
			if (req.getParameter("isShared") != null && req.getParameter("isShared") !="") {
				merchant.setIsShared(req.getParameter("isShared"));
			}
			if (req.getParameter("sharedRate") != null && req.getParameter("sharedRate") !="") {
				BigDecimal depositeRate = new BigDecimal(req.getParameter("sharedRate"));
				merchant.setSharedRate(new BigDecimal(req.getParameter("sharedRate")));
			}
			if (req.getParameter("customerManagerPhone") != null && req.getParameter("customerManagerPhone") !="") {
				merchant.setCustomerManagerPhone(req.getParameter("customerManagerPhone"));
			}
			if (req.getParameter("customerManagerName") != null && req.getParameter("customerManagerName") !="") {
				merchant.setCustomerManagerName(req.getParameter("customerManagerName"));
			}
			args.put("merchant", merchant);
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "查询用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"查询用户时发生异常!",null).toJson();
		}
	}
	@RequestMapping(value="adminUserCheckBackFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminUserCheckBackFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminUserCheckBackFunction fun=ac.getBean(AdminUserCheckBackFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]退回用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"退回用户时发生异常！",null).toJson();
		}
	}
	
	@RequestMapping(value="adminUserDeleteFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminUserDeleteFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminUserDeleteFunction fun=ac.getBean(AdminUserDeleteFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]删除用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"删除用户时发生异常！",null).toJson();
		}
	}
	
	@RequestMapping(value="adminUserStateUpdateFunction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminUserStateUpdateFunction(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try {
			AdminUserStateUpdateFunction fun=ac.getBean(AdminUserStateUpdateFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("status", req.getParameter("status"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]开启或冻结用户时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"开启或冻结用户时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="userModify",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminUserModify(HttpServletRequest req,HttpServletResponse res) {
		String phone=req.getParameter("phone");
		try {
			AdminUserModifyFunction fun=ac.getBean(AdminUserModifyFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("password", req.getParameter("password"));
			args.put("name", req.getParameter("name"));
			args.put("netAddress", req.getParameter("netAddress"));
			args.put("merchantId", req.getParameter("merchantId"));
			args.put("merchantName", req.getParameter("merchantName"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]修改用户信息时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"修改用户信息时发生异常!",null).toJson();
		}
	}

	@RequestMapping(value="adminShopKeeperAdd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminShopKeeperAdd(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			AdminUserAddFunction fun=ac.getBean(AdminUserAddFunction.class);
			Map<String, Object> args=new HashMap<>(); 
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName",ConstParam.USER_ROLE_NAME_SHOP_KEEPER);
			args.put("name",req.getParameter("name"));
			args.put("merchantId", req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]添加商户负责人["+req.getParameter("userPhone")+"]时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员添加商户负责人时发生异常!",null).toJson();
		}
	}
	
	@RequestMapping(value="adminSaleManAdd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String adminSaleManAdd(HttpServletRequest req,HttpServletResponse res){
		String phone=req.getParameter("phone");
		try { 
			AdminUserAddFunction fun=ac.getBean(AdminUserAddFunction.class);
			Map<String, Object> args=new HashMap<>();
			args.put("phone",phone);
			args.put("userPhone",req.getParameter("userPhone"));
			args.put("roleName",ConstParam.USER_ROLE_NAME_SALESMAN);
			args.put("name",req.getParameter("name"));
			args.put("merchantId", req.getParameter("merchantId"));
			return fun.excuted(args).toJson();
		} catch (Exception e) {
			LogFactory.error(this, "管理员["+phone+"]添加商户销售员["+req.getParameter("userPhone")+"]时发生异常!",e);
			return new RetMessage(RetCodeEnum.EXCEPTION.toString(),"管理员添加商户销售员时发生异常!",null).toJson();
		}
	}
}
