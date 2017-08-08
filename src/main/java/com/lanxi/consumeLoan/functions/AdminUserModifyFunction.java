package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
@Service
public class AdminUserModifyFunction extends AbstractFunction{

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
		String phone =(String) args.get("phone");
		String userPHone=(String) args.get("userPhone");
		LogFactory.info(this, "管理员["+phone+"]尝试修改用户["+userPHone+"]的信息!");
		
		User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(userPHone);
		if(user==null) {
			LogFactory.info(this,"管理员["+phone+"]修改用户["+userPHone+"]的信息时,用户不存在!" );
			return new RetMessage(RetCodeEnum.FAIL.toString(),"用户不存在", null);
		}
		String password=(String) args.get("password");
		String name=(String) args.get("name");
//		String state=(String) args.get("state");
		if(password!=null&&!password.isEmpty()) {
			LogFactory.info(this, "管理员["+phone+"]修改了用户["+userPHone+"]的密码,原密码["+user.get("password").getValue()+"]新密码["+password+"]");
			user.set("password", password);
		}
		if(name!=null&&!name.isEmpty()){
			LogFactory.info(this, "管理员["+phone+"]修改了用户["+userPHone+"]的姓名,原姓名["+user.get("name").getValue()+"]新姓名["+name+"]");
			user.set("name", name);
		}
		switch (user.getRoleName()) {
			case ConstParam.USER_ROLE_NAME_ADMIN:
				
				break;
			case ConstParam.USER_ROLE_NAME_CUSTOMER_MANAGER:
				String netAddress=(String) args.get("netAddress");
				if(netAddress!=null&&!netAddress.isEmpty()) {
					LogFactory.info(this, "用户["+userPHone+"]是客户经理,管理员["+phone+"]修改了客户经理["+userPHone+"]的负责网点,原网点["+user.get("netAddress").getValue()+"],新网点["+netAddress+"]");
					user.set("netAddress", netAddress);
				}
				break;
			case ConstParam.USER_ROLE_NAME_SHOP_KEEPER:
				
			case ConstParam.USER_ROLE_NAME_SALESMAN:
				String merchantId=(String) args.get("merchantId");
				String merchantName=(String) args.get("merchantName");
				if((merchantId!=null&&!merchantId.isEmpty())||(merchantName!=null&&!merchantName.isEmpty()))
					LogFactory.info(this, "用户["+userPHone+"]是商户人员,管理员修改了用户的商户信息");
				if(merchantId!=null&&!merchantId.isEmpty()) {
					LogFactory.info(this, "用户["+userPHone+"]原商户id["+user.get("merchantId").getValue()+"],新商户id["+merchantId+"]");
					user.set("merchantId", merchantId);
				}
				if(merchantName!=null&&!merchantName.isEmpty()) {
					LogFactory.info(this, "用户["+userPHone+"]原商户名称["+user.get("merchantName").getValue()+"],新商户名称["+merchantName+"]");
					user.set("merchantName", merchantName);
				}
				break;
	
			default:break;
		}
		dao.getUserDao().updateUserByUniqueIndexOnPhone(user, user.getPhone());
		LogFactory.info(this, "更新用户["+userPHone+"]新信息到数据库!用户信息修改成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"修改用户["+userPHone+"]信息成功!", null);
	}

}
