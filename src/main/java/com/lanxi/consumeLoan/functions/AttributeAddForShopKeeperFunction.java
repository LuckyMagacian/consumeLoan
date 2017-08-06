package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
@Component
public class AttributeAddForShopKeeperFunction extends AbstractFunction{
	public AttributeAddForShopKeeperFunction() {
		addAttribute(new Attribute<String>("shopKeeperId", ""));
		addAttribute(new Attribute<String>("merchantId",""));
		addAttribute(new Attribute<String>("merchantName",""));
		addAttribute(new Attribute<String>("merchantAddress", ""));
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
