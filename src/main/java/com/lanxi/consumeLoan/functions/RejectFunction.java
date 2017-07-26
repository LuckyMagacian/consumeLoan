package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
/**
 * 拒绝申请
 * @author yangyuanjian
 *
 */
@Service
public class RejectFunction extends AbstractFunction {
	public RejectFunction() {
		addAttribute(new Attribute<String>("adminId", ""));
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
		String phone=(String) args.get("phone");
		if(!checkService.checkAuthority(phone, this.getClass().getName())){
			return failNotice();
		}
		String applyId=(String) args.get("applyId");
		Apply apply=dao.getApplyDao().selectApplyByUniqueIndexOnApplyId(applyId);
		if(apply==null){
			return failNotice();
		}
		apply.setState(ConstParam.APPLY_STATE_REJECT);
		return successNotice();
	}

}
