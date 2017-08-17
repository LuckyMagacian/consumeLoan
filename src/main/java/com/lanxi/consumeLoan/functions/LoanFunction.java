package com.lanxi.consumeLoan.functions;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.SystemAccount;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.TimeUtil;

/**
 * 放款功能
 * 
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
		String phone = (String) args.get("phone");

		String applyId = (String) args.get("applyId");
		// 修改申请放款金额
		String loanMoney = (String) args.get("loanMoney");
		String loanTime = (String) args.get("loanTime"); 
		LogFactory.info(this, "管理员[" + phone + "],放款的请求参数为：[" + args + "]");
		Apply apply = dao.getApplyDao().selectApplyByUniqueIndexOnApplyId(applyId);
		if (apply == null) {
			LogFactory.info(this, "管理员[" + phone + "],没查询到数据!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "没查询到数据!", null);
		}
		if (!ConstParam.APPLY_STATE_WAIT_CHECK.equals(apply.getState())) {
			LogFactory.info(this, "管理员[" + phone + "],该申请订单[" + applyId + "]不满足放款条件，该订单申请的状态为["+apply.getState()+"]!");
			return new RetMessage(RetCodeEnum.FAIL.toString(), "不满足放款条件!", null);
		} else {
			BigDecimal loan = new BigDecimal(loanMoney);
			apply.setLoanMoney(loan);
			apply.setLoanTime(loanTime);
			apply.setBrokerage(apply.getLoanMoney().multiply(apply.getBrokerageRate()));
			apply.setState(ConstParam.APPLY_STATE_LOAN);
			// 修改商户
			Merchant merchant = dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(apply.getMerchantId());
			merchant.setLoanAmount(merchant.getLoanAmount() + 1);
			merchant.setLoanMoneyAmount(merchant.getLoanMoneyAmount().add(apply.getLoanMoney()));
			merchant.setBrokerageAmount(merchant.getBrokerageAmount().add(apply.getBrokerage()));
			merchant.setBrokerageLess(merchant.getBrokerageLess().add(apply.getBrokerage()));
			// 修改系统
			SystemAccount account = dao.getSystemAccountDao().selectSystemAccountByUniqueIndexOnAccountId("1001");
			apply.setServiceCharge(apply.getLoanMoney().multiply(account.getBrokerageRate()));
			LogFactory.info(this, "管理员[" + phone + "],放款之前的佣金值：" + account.getBrokerage() + ",放款之前的服务费"
					+ account.getServiceCharge() + ",放款之前的风险准备金" + account.getProvisionsOfRisk());
			BigDecimal newBrokerge = new BigDecimal(0);
			newBrokerge = loan.add(account.getBrokerage().multiply(account.getBrokerageRate()));
			BigDecimal newServiceCharge = new BigDecimal(0);
			newServiceCharge = loan.add(apply.getLoanMoney().multiply(account.getServiceChargeRate()));
			
			BigDecimal newProvisionsOfRisk = new BigDecimal(0);
			newProvisionsOfRisk = loan.add(account.getProvisionsOfRisk().multiply(account.getProvisionsOfRiskRate()));
			BigDecimal oldVersion = account.getVersion();
			LogFactory.info(this, "管理员[" + phone + "],放款之后的佣金值：" + newBrokerge + ",放款之后的服务费" + newServiceCharge
					+ ",放款之后的佣金" + newProvisionsOfRisk);
			account.setBrokerage(newBrokerge);
			account.setServiceCharge(newServiceCharge);
			account.setProvisionsOfRisk(newProvisionsOfRisk);
			SystemAccount newAccount = dao.getSystemAccountDao().selectSystemAccountByUniqueIndexOnAccountId("1001");
			BigDecimal newVersion = newAccount.getVersion();
			LogFactory.info(this, "管理员[" + phone + "],刚进来的版本号为：[" + oldVersion + "],更新时的版本号为:[" + newVersion + "]");
			if (newVersion.equals(oldVersion)) {
				newVersion = newVersion.add(new BigDecimal(1));
				account.setVersion(newVersion);
				if (update(apply, merchant, account))
					return new RetMessage(RetCodeEnum.SUCCESS.toString(), "放款成功!", null);
				else
					return new RetMessage(RetCodeEnum.FAIL.toString(), "放款失败!请重试!", null);
			} else {
				LogFactory.info(this, "管理员[" + phone + "],当前版本与数据库版本不一致无法放款!");
				return new RetMessage(RetCodeEnum.FAIL.toString(), "放款失败请重试!", null);
			}
		}
		// LogFactory.info(this, "管理员["+phone+"],放款成功!");
		// return new RetMessage(RetCodeEnum.SUCCESS.toString(), "放款成功!", null);
	}

	@Transactional
	private boolean update(Apply apply, Merchant merchant, SystemAccount systemAccount) {
		try {
			LogFactory.info(this, "尝试更新系统账户!");
			dao.getSystemAccountDao().updateSystemAccountByUniqueIndexOnAccountId(systemAccount,
					systemAccount.getAccountId());
			LogFactory.info(this, "更新系统账户信息成功!");
			LogFactory.info(this, "尝试更新商户账户!");
			dao.getMerchantDao().updateMerchantByUniqueIndexOnMerchantId(merchant, merchant.getMerchantId());
			LogFactory.info(this, "更新商户佣金信息成功!");
			LogFactory.info(this, "尝试更新申请订单!");
			dao.getApplyDao().updateApplyByUniqueIndexOnApplyId(apply, apply.getApplyId());
			LogFactory.info(this, "更新订单放款信息成功!");
			// String smsTemplate=ConfigManager.get("sms","customerLoanNotice");
			// smsTemplate=smsTemplate.replace("[merchantName]",
			// merchant.getMerchantName());
			// smsTemplate=smsTemplate.replace("[money]", apply.getLoanMoney().toString());
			// smsService.sendSms(smsTemplate, apply.getPhone());
			// LogFactory.info(this,
			// "放款成功,短信通知申请者["+apply.getName()+":"+apply.getPhone()+"],内容["+smsTemplate+"]");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
