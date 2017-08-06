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
		LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args + "]查询用户!");
		User user = dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
		if (user == null) {
			LogFactory.info(this, "管理员[" + phone + "]根据用户[" + userPhone
					+ "]为查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "未查询到数据", null);
		}
		LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args + "]查询结果["
				+ user + "]!");
		// String oldAttributes = user.getAttributes();//查询到的用户的所有属性
		String oldAttributes = (String) user.get("state").getValue();
		String newAttributes = "";
		String attributes = "";

		if (ConstParam.USER_STATE_FREEZE.equals(status)) {
			if (ConstParam.USER_STATE_NORMAL.equals(oldAttributes)) {
				attributes = new Attribute<String>("state",
						ConstParam.USER_STATE_NORMAL).toJson();// 用户状态-正常
				newAttributes = new Attribute<String>("state",
						ConstParam.USER_STATE_FREEZE).toJson();// 用户状态-冻结
			}
		}

		if (ConstParam.USER_STATE_NORMAL.equals(status)) {
			if (ConstParam.USER_STATE_FREEZE.equals(oldAttributes)) {
				attributes = new Attribute<String>("state",
						ConstParam.USER_STATE_FREEZE).toJson();
				newAttributes = new Attribute<String>("state",
						ConstParam.USER_STATE_NORMAL).toJson();
			}
		}
		oldAttributes = oldAttributes.replace(attributes, newAttributes);
		dao.getUserDao().updateUserByUniqueIndexOnPhone(user, userPhone);
		LogFactory.info(this, "管理员[" + phone + "],修改用户属性之后为：[" + oldAttributes
				+ "]");
		user.setAttributes(oldAttributes);
		LogFactory.info(this, "管理员[" + phone + "]修改[" + userPhone + "]成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "修改通过", null);

	}

}