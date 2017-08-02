package com.lanxi.consumeLoan.functions;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
/**
 * 拒绝申请,驳回
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
			LogFactory.info(this, "没有权限执行该操作!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没有权限!", null);
		}
		String applyId=(String) args.get("applyId");
		String reason=(String) args.get("reason");
		LogFactory.info(this, "用户["+phone+"],驳回的请求参数为："+args);

		Apply apply=dao.getApplyDao().selectApplyByUniqueIndexOnApplyId(applyId);
		if(apply==null){
			LogFactory.info(this, "用户["+phone+"],根据的申请id["+applyId+"]没有查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没有查询到数据!", null);
		}
		apply.setState(ConstParam.APPLY_STATE_REJECT);
		apply.setReason(reason);
		
		dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(apply, applyId);
		
		LogFactory.info(this, "用户["+phone+"],驳回申请id为["+applyId+"]申请成功！");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "驳回成功！", null);
	}

}
