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
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.functions.AdminMerchantAddFunction;
import com.lanxi.consumeLoan.functions.AdminUserAddFunction;
import com.lanxi.consumeLoan.functions.AdminUserQueryFunction;
import com.lanxi.consumeLoan.functions.ApplyOrderAddFunction;
import com.lanxi.consumeLoan.functions.ChangePasswordFunction;
import com.lanxi.consumeLoan.functions.CustomerShopEmployeeAddFunction;
import com.lanxi.consumeLoan.functions.LoginFunction;
import com.lanxi.consumeLoan.functions.MakeValidateCodePicFunction;
import com.lanxi.consumeLoan.functions.MerchantApplyOrderQueryFunction;
import com.lanxi.consumeLoan.functions.MerchantBindingFunction;
import com.lanxi.consumeLoan.functions.MerchantHomeFunction;
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
	@RequestMapping(value="merchantApplyQueryExport",produces = {"application/json;charset=UTF-8"})
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
			String state=req.getParameter("state");
			args.put("state", ConstParam.APPLY_STATE_LOAN);
			String startTime=req.getParameter("startTime");
			if(startTime!=null&&!startTime.isEmpty())
				args.put("startTime", startTime);
			String endTime=req.getParameter("endTime");
			if(endTime!=null&&!endTime.isEmpty())
				args.put("endTime", endTime);
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
	    		moneyAmount=moneyAmount.add(each.getLoanMoney());
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
	@RequestMapping(value="merchantBrokerageQueryExport",produces = {"application/json;charset=UTF-8"})
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
			String state=req.getParameter("state");
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
	
}
