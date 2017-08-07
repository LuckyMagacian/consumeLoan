package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.Role;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

@Service
public class AdminUserAddFunction extends AbstractFunction{

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
		String userPhone=(String) args.get("userPhone");
		String roleName=(String) args.get("roleName");
		LogFactory.info(this, "管理员["+phone+"]尝试添加用户["+userPhone+"]为["+roleName+"]");
		if(userPhone==null||userPhone.isEmpty()){
			LogFactory.info(this, "管理员["+phone+"]尝试添加用户["+userPhone+"]为["+roleName+"]时,手机号码为空!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"手机号码为空!",null);
		}
		if(roleName==null||roleName.isEmpty()){
			LogFactory.info(this, "管理员["+phone+"]尝试添加用户["+userPhone+"]为["+roleName+"]时,角色名为空!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"角色名为空!",null);
		}
		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
		if(user!=null){
			LogFactory.info(this, "管理员["+phone+"]添加用户["+userPhone+"]时,用户已存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"用户已存在!",null);
		}
		Role role=dao.getRoleDao().selectRoleByUniqueIndexOnRoleName(roleName);
		if(role==null){
			LogFactory.info(this, "管理员["+phone+"]添加用户["+userPhone+"]为["+roleName+"]时,角色不存在!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"角色不存在!",null);
		}
		user=new User();
		user.setRoleName(roleName);
		userManager.addAttributesForUser(user);
		String name=(String) args.get("name");
		if(roleName.equals("customerManager")){
			String netAddress=(String) args.get("netAddress");
			user.set("netAddress", netAddress);
		}
		if(roleName.equals("salesMan")||roleName.equals("shopKeeper")){
			String merchantId=(String) args.get("merchantId");
			if(merchantId==null||merchantId.isEmpty()){
				LogFactory.info(this, "管理员["+phone+"]添加用户["+userPhone+"]为["+roleName+"]时,商户["+merchantId+"]为空!");
				return new RetMessage(RetCodeEnum.FAIL.toString(),"商户编号为空!",null);
			}
			Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId);
			if(merchant==null){
				LogFactory.info(this, "管理员["+phone+"]添加用户["+userPhone+"]为["+roleName+"]时,商户["+merchantId+"]不存在!");
				return new RetMessage(RetCodeEnum.FAIL.toString(),"商户不存在!",null);
			}
			user.set("merchantId", merchant.getMerchantId());
			user.set("merchantName", merchant.getMerchantName());
			user.set("merchantAddress", merchant.getMerchantAddress());
		}
		user.setPhone(userPhone);
		user.set("name", name);
		user.set("createBy", phone);
		dao.getUserDao().addUser(user);
		LogFactory.info(this, "管理员["+phone+"]添加客户经理["+user+"]成功");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "添加用户成功!", null);
	}
	
}
