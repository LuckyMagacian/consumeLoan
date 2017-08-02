package com.lanxi.consumeLoan.functions;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.PictureVerifyUtil;
/**
 * 创建图形验证码接口
 * @author yangyuanjian
 *
 */
@Service
public class MakeValidateCodePicFunction extends AbstractFunction{

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
		HttpServletResponse res=(HttpServletResponse) args.get("res");
		String phone= (String) args.get("phone");
		LogFactory.info(this, "用户["+phone+"]请求图形验证码");
		if(res==null||phone==null){
			LogFactory.info(this, "用户["+phone+"]或servlet["+res+"]为null!请求拒绝!");
			return failNotice();
		}
		String code= PictureVerifyUtil.sendVerifyCode(res).toLowerCase();
		LogFactory.info(this, "用户["+phone+"]的验证码为["+code+"]");
		redisService.set(ConstParam.FUNCTION_NAME_LOGIN+phone, code,60000L);
		LogFactory.info(this, "用户["+phone+"]的验证码["+code+"]已缓存,有效期[60秒]");
		return successNotice();
	}
	
}
