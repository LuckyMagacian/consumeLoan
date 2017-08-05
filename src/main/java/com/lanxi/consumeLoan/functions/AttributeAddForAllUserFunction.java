package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.util.utils.TimeUtil;
@Component
public class AttributeAddForAllUserFunction extends AbstractFunction{
	public AttributeAddForAllUserFunction() {
			addAttribute(new Attribute<String>("phone", ""));
			addAttribute(new Attribute<String>("name", ""));
		 	addAttribute(new Attribute<String>("password","123456"));
	        addAttribute(new Attribute<String>("state",ConstParam.USER_STATE_WAIT_CHECK));
	        addAttribute(new Attribute<String>("createBy",""));
	        addAttribute(new Attribute<String>("createTime",TimeUtil.getDateTime()));
	}
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
		// TODO Auto-generated method stub
		return null;
	}

}
