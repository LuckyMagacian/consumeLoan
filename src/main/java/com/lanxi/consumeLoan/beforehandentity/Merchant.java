package com.lanxi.consumeLoan.beforehandentity;

import java.math.BigDecimal;

/**
 * Created by yangyuanjian on 2017/7/11.
 */
public class Merchant {
    /**商户编号*/
	private String merchantId;
	/**商户名称*/
	private String merchantName;
	/**商户类型*/
	private String merchantType;
	/**商户地址*/
	private String merchantAddress;
	/**是否担保*/
	private Boolean isAssurance;
	/**担保总额*/
	private BigDecimal depositAmount;
	/**担保比例*/
	private BigDecimal depositRate;
	/**保证金余额*/
    private BigDecimal depositBalance;
    /**是否合作分润*/
    private Boolean isShared;
    /**分润比例*/
    private BigDecimal sharedRate;
    /**佣金比例*/
    private BigDecimal brokerageRate;
    /**佣金总额*/
    private BigDecimal brokerageAmount;
    /**剩余佣金*/
    private BigDecimal brokerageLess;
    /**申请总数*/
    private Integer applyAmount;
    /**申请总额*/
    private BigDecimal applyMoneyAmount;
    /**放款总数*/
    private Integer loanAmount;
    /**放款总额*/
    private BigDecimal loanMoneyAmount;
    /**违约总数*/
    private Integer breakAmount;
    /**违约总额*/
    private BigDecimal breakMoneyAmount;
	
}
