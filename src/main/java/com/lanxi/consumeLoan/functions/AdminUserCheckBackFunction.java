package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
/**
 * 管理员用户审核退回
 * @author lx
 *
 */
@Service
public class AdminUserCheckBackFunction extends AbstractFunction {

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
		String phone = (String) args.get("phone");
		String userPhone = (String) args.get("userPhone");
		LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args + "]查询用户!");
		User user = dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
		if (user == null) {
			LogFactory.info(this, "管理员[" + phone + "]根据用户[" + userPhone
					+ "]为查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "未查询到数据", null);
		}
		LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args + "]查询结果["
				+ user + "]!");
//		String oldAttributes = user.getAttributes();//查询到的用户的所有属性
//		String newAttributes = new Attribute<String>("state",
//				ConstParam.USER_STATE_REJECT).toJson();//审核成功之后用户的状态
//		String attributes = new Attribute<String>("state",
//				ConstParam.USER_STATE_WAIT_CHECK).toJson();//正常的用户状态
//		LogFactory.info(this, "管理员[" + phone + "],根据用户[" + userPhone
//				+ "]查询到的属性为：[" + oldAttributes + "],正常状态下用户的属性为：[" + attributes
//				+ "],用户审核成功之后应该为：[" + newAttributes + "]");
//		if (oldAttributes.contains(attributes)) {
//			oldAttributes = oldAttributes.replace(attributes, newAttributes);
//			LogFactory.info(this, "管理员[" + phone + "],审核通过之后用户属性为：["+oldAttributes+"]");
//			user.setAttributes(oldAttributes);
//			dao.getUserDao().updateUserByUniqueIndexOnPhone(user, userPhone);
//			LogFactory.info(this, "管理员[" + phone + "]审核用户[" + userPhone
//					+ "]审核已退回!");
//			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "审核已退回", null);
//		} else {
//			LogFactory.info(this, "管理员[" + phone + "]审核用户[" + userPhone
//					+ "],用户未通过，未达到审核条件!");
//			return new RetMessage(RetCodeEnum.FAIL.toString(),
//					"用户审核未通过，未达到审核条件", null);
//		}
		user.set("state", ConstParam.USER_STATE_REJECT);
		LogFactory.info(this, "管理员["+phone+"]拒绝了用户["+userPhone+"]的审核");
		redisService.delete(ConstParam.USER_STATE_LOGIN+userPhone);
		dao.getUserDao().updateUserByUniqueIndexOnPhone(user, userPhone);
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "审核操作完成!", null);
	}

}
