package com.lanxi.consumeLoan.functions;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.util.utils.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
/**
 * 放款功能
 * @author yangyuanjian
 *
 */
@Service
public class LoanFunction extends AbstractFunction {

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
		String loanMoney=(String) args.get("loanMoney");
		BigDecimal loan=new BigDecimal(loanMoney);
		apply.setLoanMoney(loan);
		apply.setLoanTime(TimeUtil.getDateTime());
		apply.setState(ConstParam.APPLY_STATE_LOAN);
		dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(apply, applyId);
		return null;
	}

}
