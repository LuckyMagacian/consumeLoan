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
 * 管理员-用户列表开启和冻结用户接口
 * 
 * @author lx
 *
 */
@Service
public class AdminUserStateUpdateFunction extends AbstractFunction {

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
		String status = (String) args.get("status");
		LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args + "]!");
		User user = dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
		if (user == null) {
			LogFactory.info(this, "管理员[" + phone + "]根据用户[" + userPhone
					+ "]为查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "未查询到数据", null);
		}
//		LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args + "]查询结果["
//				+ user + "]!");
		// String oldAttributes = user.getAttributes();//查询到的用户的所有属性
		String oldAttributes = (String) user.get("state").getValue();
		if (ConstParam.USER_STATE_FREEZE.equals(status)) {
			if (ConstParam.USER_STATE_NORMAL.equals(oldAttributes.trim())) {
				LogFactory.info(this, "管理员["+phone+"]尝试将用户["+userPhone+"]从[正常]状态改为[冻结]状态!");
				user.set("state", ConstParam.USER_STATE_FREEZE);
				redisService.delete(ConstParam.USER_STATE_LOGIN+phone);
			}else {
				LogFactory.info(this, "用户["+userPhone+"]当前不是[正常]状态,无法转换为[冻结]状态!"); 
				return new RetMessage(RetCodeEnum.FAIL.toString(), "用户当前不是正常状态,无法冻结!", null);
			}
		}else if (ConstParam.USER_STATE_NORMAL.equals(status)) {
			if (ConstParam.USER_STATE_FREEZE.equals(oldAttributes.trim())) {
				LogFactory.info(this, "管理员["+phone+"]尝试将用户["+userPhone+"]从[冻结]状态改为[正常]状态!");
				user.set("state", ConstParam.USER_STATE_NORMAL);
			}else {
				LogFactory.info(this, "用户["+userPhone+"]当前不是[冻结]状态,无法转换为[正常]状态!");
				return new RetMessage(RetCodeEnum.FAIL.toString(), "用户当前不是[冻结]状态,无法开启!", null);
			}
		}else {
			LogFactory.info(this, "用户["+userPhone+"]传入状态不是[冻结]也不是[正常],不允许修改!");
			return new RetMessage(RetCodeEnum.FAIL.toString(),"传入状态参数异常!检查参数!",null);
		}
		dao.getUserDao().updateUserByUniqueIndexOnPhone(user, userPhone);
		LogFactory.info(this, "管理员[" + phone + "]修改[" + userPhone + "]状态成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "修改通过", null);

	}

}
