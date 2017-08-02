package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.SystemAccount;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.TimeUtil;
/**
 * 放款功能
 * @author yangyuanjian
 *
 */
@Service
public class LoanFunction extends AbstractFunction {

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

		String applyId=(String) args.get("applyId");
		Apply apply=dao.getApplyDao().selectApplyByUniqueIndexOnApplyId(applyId);
		if(apply==null){
			LogFactory.info(this, "管理员["+phone+"],没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		//修改申请放款金额
		String loanMoney=(String) args.get("loanMoney");
		BigDecimal loan=new BigDecimal(loanMoney);
		apply.setLoanMoney(loan);
		apply.setLoanTime(TimeUtil.getDateTime());
		apply.setState(ConstParam.APPLY_STATE_LOAN);
		dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(apply, applyId);
		//修改系统
		SystemAccount account=dao.getSystemAccountDao().selectSystemAccountByClass(new SystemAccount()).get(0);
		LogFactory.info(this, "管理员["+phone+"],放款之前的佣金值："+account.getBrokerage() + ",放款之前的服务费" + account.getServiceCharge() +",放款之前的风险准备金" + account.getProvisionsOfRisk());
		BigDecimal  newBrokerge = new BigDecimal(0);
		newBrokerge = loan.add(account.getBrokerage().multiply(account.getBrokerageRate()));
		BigDecimal newServiceCharge = new BigDecimal(0);
		newServiceCharge = loan.add(account.getServiceCharge().multiply(account.getServiceChargeRate()));
		BigDecimal newProvisionsOfRisk = new BigDecimal(0);
		newProvisionsOfRisk = loan.add(account.getProvisionsOfRisk().multiply(account.getProvisionsOfRiskRate()));
		
		LogFactory.info(this, "管理员["+phone+"],放款之后的佣金值："+newBrokerge+",放款之后的服务费" + newServiceCharge +",放款之后的佣金" + newProvisionsOfRisk);
		account.setBrokerage(newBrokerge);
		account.setServiceCharge(newServiceCharge);
		account.setProvisionsOfRisk(newProvisionsOfRisk);
		dao.getSystemAccountDao().updateSystemAccountByUniqueIndexOnAccountId(account, account.getAccountId());
		
		LogFactory.info(this, "管理员["+phone+"],放款成功!");
		return new RetMessage(RetCodeEnum.SUCCESS.toString(), "放款成功!", null);
	}

}
