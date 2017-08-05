package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
@Service
public class CustomerShopEmployeeAddFunction extends AbstractFunction{
//	public CustomerShopEmployeeAddFunction() {
//		addAttribute(new Attribute<String>("netAddress",""));
//	}
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

	@Override
	public RetMessage excuted(Map<String, Object> args) {
		String phone=(String) args.get("phone");
		String merchantId=(String) args.get("merchantId");
		LogFactory.info(this, "客户经理["+phone+"]尝试为商户["+merchantId+"]添加工作人员["+args+"]!");
		if(merchantId==null||merchantId.isEmpty()){
			LogFactory.info(this, "客户经理["+phone+"]为商户["+merchantId+"]添加工作人员时,未传入商户编号!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"添加商户工作人员时,未传入商户编号!", null);
		}
		Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId);
		if(merchant==null||merchantId.isEmpty()){
			LogFactory.info(this, "客户经理["+phone+"]为商户["+merchantId+"]添加工作人员时,商户不存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "添加商户工作人员时,商户不存在!", null);
		}
		if(!phone.equals(merchant.getCustomerManagerPhone())){
			LogFactory.info(this, "商户["+merchantId+"]的客户经理是["+merchant.getCustomerManagerPhone()+"]不是["+phone+"],无权添加!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "该商户不是当前用户负责,无权添加!", null);
		}
		String userPhone=(String) args.get("userPhone");
		String name=(String) args.get("name");
		String roleName=(String) args.get("roleName");
		if(roleName==null||roleName.isEmpty()){
			LogFactory.info(this, "客户经理["+phone+"]为商户["+merchantId+"]添加工作人员时,角色["+roleName+"]为空!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "添加商户工作人员时,未指定身份!", null);
		}
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
		if(role==null){
			LogFactory.info(this, "客户经理["+phone+"]为商户["+merchantId+"]添加工作人员时,角色["+roleName+"]不存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "添加商户工作人员时,身份不存在!", null);
		}
		User user= dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
		if(user!=null){
			LogFactory.info(this, "客户经理["+phone+"]为商户["+merchantId+"]添加工作人员时,用户["+userPhone+"]已存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"添加商户工作人员时,用户(手机号)已存在!",null);
		}
		user=new User();
		user.setPhone(userPhone);
		user.setRoleName(roleName);
		userManager.addAttributesForUser(user);
		user.set("name", name); 
		user.set("merchantId",merchantId);
		user.set("createBy", phone);
		user.set("merchantName", merchant.getMerchantName());
		dao.getUserDao().addUser(user);
		LogFactory.info(this, "客户经理["+phone+"]为商户["+merchantId+"]添加工作人员["+user.getPhone()+"]成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "添加商户工作人员成功!", null);
	}

}
