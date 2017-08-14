package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.SystemAccount;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.RandomUtil;
import com.lanxi.util.utils.TimeUtil;

/**
 * Created by yangyuanjian on 2017/7/13.
 */
@Service
public class ApplyOrderAddFunction extends AbstractFunction{
//    public ApplyOrderAddFunction(){
//        addAttribute(new Attribute<String>("merchantId",""));
//        addAttribute(new Attribute<String>("salesManId",""));
//        addAttribute(new Attribute<String>("merchantName",""));
//    }

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

    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    public RetMessage excuted(Map<String, Object> args) {
    	List<String> specialPhones=new ArrayList<>();
    	specialPhones.add("15757129562");
    	specialPhones.add("18368720758");
    	specialPhones.add("18667041905");
    	specialPhones.add("18557536069");
    	specialPhones.add("13456915077");
    	specialPhones.add("15024634281");
    	
    	String phone=(String) args.get("phone");
    	LogFactory.info(this, "用户["+phone+"]尝试添加新订单!");
    	String applyJson=(String) args.get("apply");
    	if(applyJson==null||applyJson.isEmpty()){
    		LogFactory.info(this, "用户["+phone+"]添加的订单["+applyJson+"]为空");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"订单内容为空!",null);
    	}
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
    	Attribute<String> merchantId=(Attribute<String>) user.get("merchantId");
    	if(merchantId==null){
    		LogFactory.info(this, "用户["+phone+"]未绑定商户或不是商户工作人员!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"用户未绑定商户或不是商户工作人员!",null);
    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId.getValue());
    	if(merchant==null){
    		LogFactory.info(this, "用户["+phone+"]绑定的商户["+merchantId.getValue()+"]不存在!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"用户绑定的商户不存在!",null);
    	}
    	if(!merchant.getState().equals(ConstParam.MERCHANT_STATE_SHELVED)){
    		LogFactory.info(this, "用户["+phone+"]绑定的商户["+merchantId.getValue()+"]状态不是正常的上架状态["+merchant.getState()+"],无法添加新的申请!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"用户绑定的商户状态异常,无法添加新申请!",null);
    	}
    	
    	Apply apply=JSONObject.parseObject(applyJson, Apply.class);
    	String userPhone=apply.getPhone();
    	if(userPhone==null){
    		LogFactory.info(this, "用户["+phone+"]添加的订单["+applyJson+"]未传入申请人的手机号!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"未传入申请者手机号!",null);
    	}
    	if(apply.getApplyMoney().compareTo(new BigDecimal("500000"))>0||apply.getApplyMoney().compareTo(new BigDecimal("5000"))<0) {
    		LogFactory.info(this, "用户["+apply.getPhone()+"]申请金额["+apply.getApplyMoney()+"]超出范围[5000-500000]");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"申请金额超出范围!申请失败!",null);
    	}
    	if(apply.getApplyMoney().remainder(new BigDecimal("1000")).compareTo(new BigDecimal("0.00000"))!=0){
    		LogFactory.info(this, "用户["+apply.getPhone()+"]申请金额["+apply.getApplyMoney()+"]不是整千!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"申请金额不是整千!申请失败!",null);
    	}

    	
    	
    	String cacheCode=redisService.get(ConstParam.FUNCTION_NAME_APPLY_ADD+userPhone.trim());
    	if(!specialPhones.contains(userPhone)) {
    		if(!checkService.isPhone(apply.getPhone())) {
    			LogFactory.info(this, "申请人["+apply.getCustomerManagerName()+"]手机号码["+apply.getPhone()+"]校验不通过！");
    			return new RetMessage(RetCodeEnum.FAIL,"申请人号码格式校验不通过！",ConstParam.TEST_FLAG?checkService.getPhoneInfo(apply.getPhone()):null);
    		}
    		if(!checkService.isId(apply.getIdNumber())) {
    			LogFactory.info(this, "申请人["+apply.getCustomerManagerName()+"]身份证号码["+apply.getIdNumber()+"]校验不通过！");
    			return new RetMessage(RetCodeEnum.FAIL,"申请人身份证号码格式校验不通过！",ConstParam.TEST_FLAG?checkService.getIdInfo(apply.getApplyId()):null);
    		}
	    	if(!apply.getVerifyCode().equals(cacheCode)){
	    		LogFactory.info(this, "用户["+phone+"]添加的订单申请者手机验证码校验不通过!输入验证码["+apply.getVerifyCode()+"],缓存验证码["+cacheCode+"]");
	    		return new RetMessage(RetCodeEnum.FAIL.toString(),"手机验证码校验不通过!",null);
	    	}
    	}
    	LogFactory.info(this, "用户["+phone+"]添加的订单["+userPhone+"]手机验证码校验通过!删除缓存验证码!");
    	SystemAccount systemAccount=dao.getSystemAccountDao().selectSystemAccountByUniqueIndexOnAccountId("1001");
    	redisService.delete(ConstParam.FUNCTION_NAME_APPLY_ADD+userPhone.trim());
    	Map<String, Object> param=new HashMap<String, Object>();
    	Date date=new Date();
    	date.setMonth(date.getMonth()-3);
    	param.put("userPhone", userPhone);
    	param.put("start_time",new SimpleDateFormat("yyyyMMddHHmmss").format(date));
    	param.put("end_time",TimeUtil.getDateTime());
    	List<Apply> applys=dao.getApplyDao().selectApplyByParam(param);
    	
    	//TODO  测试专用手机号不经过3个月校验 待删除
    	if(!specialPhones.contains(userPhone))
    	if(!applys.isEmpty()) {
    		StringBuffer buffer=new StringBuffer();
    		for(Apply each:applys) {
    			buffer.append(each.getApplyTime()+",");
    		}
    		LogFactory.info(this, "申请人员["+userPhone+"]在["+buffer.substring(0, buffer.length()-1)+"]有申请记录,无法再次申请!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(), "用户最近三个月有申请记录,无法再次申请!", null);
    	}

    	apply.setApplyTime(TimeUtil.getDateTime());
    	apply.setState(ConstParam.APPLY_STATE_WAIT_CHECK);
    	apply.setApplyId(TimeUtil.getDate()+TimeUtil.getNanoTime()+RandomUtil.getRandomNumber(6));
    	apply.setMerchantId(merchantId.getValue());
    	apply.setSalesManPhone(phone);
    	apply.setBrokerageRate(merchant.getBrokerageRate());
    	apply.setDepositeRate(merchant.getDepositeRate());
    	apply.setServiceRate(systemAccount.getServiceChargeRate()); 
    	apply.setMerchantName(merchant.getMerchantName());
    	apply.setMerchantType(merchant.getMerchantType());
    	apply.setIsAssurance(merchant.getIsAssurance());
    	apply.setCustomerManagerName(merchant.getCustomerManagerName());
    	apply.setCustomerManagerPhone(merchant.getCustomerManagerPhone());
    	merchant.setApplyAmount(merchant.getApplyAmount()+1); 
    	merchant.setApplyMoneyAmount(merchant.getApplyMoneyAmount().add(apply.getApplyMoney()));
    	dao.getApplyDao().addApply(apply);
    	LogFactory.info(this, "用户["+phone+"]在商户["+merchantId.getValue()+"]中添加申请["+apply.getApplyId()+"]成功!");
    	dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchantId.getValue());
    	String smsTemplate=ConfigManager.get("sms", "customerManagerNotice");
    	smsTemplate=smsTemplate.replace("[merchantName]", merchant.getMerchantName());
    	smsTemplate=smsTemplate.replace("[money]", apply.getApplyMoney().toString());
      	smsTemplate=smsTemplate.replace("[name]", apply.getName());
//      	smsTemplate=smsTemplate.replace("[id]", apply.getIdNumber());
      	smsTemplate=smsTemplate.replace("[phone]", apply.getPhone());
      	smsTemplate=smsTemplate.replaceAll("[time]", apply.getApplyTime());
      	smsService.sendSms(smsTemplate, merchant.getCustomerManagerPhone());
      	LogFactory.info(this, "给客户经理["+merchant.getCustomerManagerName()+":"+merchant.getCustomerManagerPhone()+"]发送通知短信["+smsTemplate+"]");
    	
      	smsTemplate=ConfigManager.get("sms", "customerNotice");
      	smsTemplate=smsTemplate.replace("[name]", apply.getName());
      	smsTemplate=smsTemplate.replace("[customerManager]", apply.getCustomerManagerName());
      	smsTemplate=smsTemplate.replace("[phone]", apply.getCustomerManagerPhone());
      	smsService.sendSms(smsTemplate, apply.getPhone());
      	LogFactory.info(this, "给用户["+apply.getName()+":"+apply.getPhone()+"]发送通知短信["+smsTemplate+"]");
    	
      	LogFactory.info(this, "用户["+phone+"]所在商户["+merchantId.getValue()+"]更新商户申请统计信息成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(), "添加申请成功!", apply.getApplyId());
    }
}
