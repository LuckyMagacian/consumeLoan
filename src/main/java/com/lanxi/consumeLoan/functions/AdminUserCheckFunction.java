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
 * 管理员用户审核
 * 
 * @author lx
 *
 */
@Service
public class AdminUserCheckFunction extends AbstractFunction {

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
		user.set("state", ConstParam.USER_STATE_NORMAL);
		LogFactory.info(this, "管理员["+phone+"]审核用户["+userPhone+"]通过!");
		dao.getUserDao().updateUserByUniqueIndexOnPhone(user, user.getPhone());
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "审核操作成功!", null);
	}

}
