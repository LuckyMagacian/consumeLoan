package com.lanxi.consumeLoan.beforehandentity;

import java.math.BigDecimal;

/**
 * Created by yangyuanjian on 2017/7/11.
 */
public class Apply {
	/**申请编号*/
	private String applyId;
	/**申请者姓名*/
	private String name;
	/**性别*/
	private String sex;
	/**申请者地址*/
	private String address;
	/**申请者身份证号码*/
	private String idNumber;
	/**申请金额*/
	private BigDecimal applyMoney;
	/**申请者手机号码*/
	private String phone;
	/**验证码*/
	private String verifyCode;
	/**申请商户编号*/
	private String merchantId;
	/**销售员编号*/
	private String saleManPhone;
	/**申请时间*/
	private String applyTime;
	/**放款时间*/
	private String loanTime;
	/**放款金额*/
	private BigDecimal loanMoney;
	/**是否逾期*/
	private Boolean isOverdue;
	/**逾期金额*/
	private BigDecimal overdueMoney;
	/**申请状态*/
	private String state;
	/**佣金比例*/
	private BigDecimal brokerageRate;
	/**分润比例*/
	private BigDecimal sharedRate;
	/**担保比例*/
	private BigDecimal depositeRate;
	
}
