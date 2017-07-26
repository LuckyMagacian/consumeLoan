package com.lanxi.consumeLoan.beforehandentity;

import java.math.BigDecimal;

/**
 * Created by yangyuanjian on 2017/7/11.
 */
public class MerchantAccountRecord {
	/**记录时间-纳秒*/
	private String nanoTime;
	/**商户编号*/
	private String merchantId;
	/**增加金额 true 减少金额 false*/
	private Boolean isAdd;
	/**记录时间*/
	private String recordTime;
	/**金额变更*/
	private BigDecimal moneyChange;
	/**金额类型 服务费|准备金|佣金*/
	private String moneyType;
	/**记录原因*/
	private String reason;
}
