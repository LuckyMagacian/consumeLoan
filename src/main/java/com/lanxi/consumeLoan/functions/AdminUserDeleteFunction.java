package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

/**
 * 删除用户
 * @author lx
 *
 */
@Service
public class AdminUserDeleteFunction extends AbstractFunction {

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
        String userPhone = (String) args.get("userPhone");
        if(userPhone.equals(phone))
        	return new RetMessage(RetCodeEnum.FAIL, "无法删除当前用户!", null);
        LogFactory.info(this,"管理员["+phone+"],用户电话为：" + userPhone);
        User user = dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
        if (user == null ){
            LogFactory.info(this, "管理员["+phone+"],用户["+userPhone+"]不存在!");
            return new RetMessage(RetCodeEnum.FAIL.toString(),"用户不存在!",null);
        }
        dao.getUserDao().deleteUserByUniqueIndexOnPhone(userPhone);
        LogFactory.info(this, "管理员["+phone+"],用户删除成功!");
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"用户删除成功!",null);
	}

}
