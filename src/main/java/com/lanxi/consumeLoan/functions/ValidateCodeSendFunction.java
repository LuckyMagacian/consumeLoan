package com.lanxi.consumeLoan.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.RandomUtil;

/**
 * 手机验证码发送
 * 
 * @author yangyuanjian
 *
 */
@Service
public class ValidateCodeSendFunction extends AbstractFunction {

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
		List<String> specialPhones = new ArrayList<>();
//		specialPhones.add("15757129562");
//		specialPhones.add("18368720758");
//		specialPhones.add("18667041905");
//		specialPhones.add("18557536069");
//		specialPhones.add("13456915077");
//		specialPhones.add("15024634281");
		String phone = (String) args.get("phone");
		String userPhone = (String) args.get("userPhone");
		if (specialPhones.contains(userPhone)) {
			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "短信发送成功!", null);
		}
		userPhone = userPhone.trim();
		LogFactory.info(this, "用户[" + phone + "]尝试为[" + userPhone + "]发送短信验证码!");
		if (userPhone == null) {
			LogFactory.info(this, "接收手机号码[" + userPhone + "]为空");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "短信接收手机号码为空!", null);
		}
		String sms = ConfigManager.get("sms", "validateSms");
		String code = RandomUtil.getRandomNumber(6);
		String lifeStr = ConfigManager.get("param", "validateCodeLife");
		LogFactory.info(this, "发送给[" + userPhone + "]的短信模版[" + sms + "]");
		Integer life = lifeStr == null ? 5 : Integer.parseInt(lifeStr);
		LogFactory.info(this, "发送给[" + userPhone + "]的验证码[" + code + "],短信有效期为[" + life + "]分钟!");
		sms = sms.replace("[code]", code);
		sms = sms.replace("[minute]", life + "");
		LogFactory.info(this, "发送给[" + userPhone + "]的短信为[" + sms + "]!");
		String result = redisService.set(ConstParam.FUNCTION_NAME_APPLY_ADD + userPhone.trim(), code,
				life * 60 * 1000L);
		LogFactory.info(this, "发送给用户[" + userPhone + "]的短信缓存结果[" + result + "]");
		result = smsService.sendSms(sms, userPhone);
		LogFactory.info(this, "发送给用户[" + userPhone + "]的短信发送结果[" + result + "]");
		if (result.contains("0000"))
			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "短信发送成功!", null);
		else
			return new RetMessage(RetCodeEnum.FAIL.toString(), "短信发送失败!", null);
	}

}
