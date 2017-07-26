package com.lanxi.consumeLoan.entity;

import java.math.BigDecimal;
import java.lang.String;
import java.lang.Byte;
import java.lang.Integer;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-25 15:09:08
 */

public class Merchant{
	/**商户编号*/
	private String merchantId;
	
	/**商户名称*/
	private String merchantName;
	
	/**商户类型*/
	private String merchantType;
	
	/**商户地址*/
	private String merchantAddress;
	
	/**是否担保*/
	private Byte[] isAssurance;
	
	/**担保总额*/
	private BigDecimal depositeAmount;
	
	/**担保比例*/
	private BigDecimal depositeRate;
	
	/**保证金余额*/
	private BigDecimal depositeBalance;
	
	/**是否合作分润*/
	private Byte[] isShared;
	
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
	
	/**起始合作时间*/
	private String partnerTime;
	
	/**下架时间*/
	private String stopTime;
	
	/**商户状态*/
	private String state;
	
	/**获取商户编号*/
	public String getMerchantId(){
		return this.merchantId;
	}
	
	/**设置商户编号*/
	public void setMerchantId(String merchantId){
		this.merchantId=merchantId;
		
	}
	/**获取商户名称*/
	public String getMerchantName(){
		return this.merchantName;
	}
	
	/**设置商户名称*/
	public void setMerchantName(String merchantName){
		this.merchantName=merchantName;
		
	}
	/**获取商户类型*/
	public String getMerchantType(){
		return this.merchantType;
	}
	
	/**设置商户类型*/
	public void setMerchantType(String merchantType){
		this.merchantType=merchantType;
		
	}
	/**获取商户地址*/
	public String getMerchantAddress(){
		return this.merchantAddress;
	}
	
	/**设置商户地址*/
	public void setMerchantAddress(String merchantAddress){
		this.merchantAddress=merchantAddress;
		
	}
	/**获取是否担保*/
	public Byte[] getIsAssurance(){
		return this.isAssurance;
	}
	
	/**设置是否担保*/
	public void setIsAssurance(Byte[] isAssurance){
		this.isAssurance=isAssurance;
		
	}
	/**获取担保总额*/
	public BigDecimal getDepositeAmount(){
		return this.depositeAmount;
	}
	
	/**设置担保总额*/
	public void setDepositeAmount(BigDecimal depositeAmount){
		this.depositeAmount=depositeAmount;
		
	}
	/**获取担保比例*/
	public BigDecimal getDepositeRate(){
		return this.depositeRate;
	}
	
	/**设置担保比例*/
	public void setDepositeRate(BigDecimal depositeRate){
		this.depositeRate=depositeRate;
		
	}
	/**获取保证金余额*/
	public BigDecimal getDepositeBalance(){
		return this.depositeBalance;
	}
	
	/**设置保证金余额*/
	public void setDepositeBalance(BigDecimal depositeBalance){
		this.depositeBalance=depositeBalance;
		
	}
	/**获取是否合作分润*/
	public Byte[] getIsShared(){
		return this.isShared;
	}
	
	/**设置是否合作分润*/
	public void setIsShared(Byte[] isShared){
		this.isShared=isShared;
		
	}
	/**获取分润比例*/
	public BigDecimal getSharedRate(){
		return this.sharedRate;
	}
	
	/**设置分润比例*/
	public void setSharedRate(BigDecimal sharedRate){
		this.sharedRate=sharedRate;
		
	}
	/**获取佣金比例*/
	public BigDecimal getBrokerageRate(){
		return this.brokerageRate;
	}
	
	/**设置佣金比例*/
	public void setBrokerageRate(BigDecimal brokerageRate){
		this.brokerageRate=brokerageRate;
		
	}
	/**获取佣金总额*/
	public BigDecimal getBrokerageAmount(){
		return this.brokerageAmount;
	}
	
	/**设置佣金总额*/
	public void setBrokerageAmount(BigDecimal brokerageAmount){
		this.brokerageAmount=brokerageAmount;
		
	}
	/**获取剩余佣金*/
	public BigDecimal getBrokerageLess(){
		return this.brokerageLess;
	}
	
	/**设置剩余佣金*/
	public void setBrokerageLess(BigDecimal brokerageLess){
		this.brokerageLess=brokerageLess;
		
	}
	/**获取申请总数*/
	public Integer getApplyAmount(){
		return this.applyAmount;
	}
	
	/**设置申请总数*/
	public void setApplyAmount(Integer applyAmount){
		this.applyAmount=applyAmount;
		
	}
	/**获取申请总额*/
	public BigDecimal getApplyMoneyAmount(){
		return this.applyMoneyAmount;
	}
	
	/**设置申请总额*/
	public void setApplyMoneyAmount(BigDecimal applyMoneyAmount){
		this.applyMoneyAmount=applyMoneyAmount;
		
	}
	/**获取放款总数*/
	public Integer getLoanAmount(){
		return this.loanAmount;
	}
	
	/**设置放款总数*/
	public void setLoanAmount(Integer loanAmount){
		this.loanAmount=loanAmount;
		
	}
	/**获取放款总额*/
	public BigDecimal getLoanMoneyAmount(){
		return this.loanMoneyAmount;
	}
	
	/**设置放款总额*/
	public void setLoanMoneyAmount(BigDecimal loanMoneyAmount){
		this.loanMoneyAmount=loanMoneyAmount;
		
	}
	/**获取违约总数*/
	public Integer getBreakAmount(){
		return this.breakAmount;
	}
	
	/**设置违约总数*/
	public void setBreakAmount(Integer breakAmount){
		this.breakAmount=breakAmount;
		
	}
	/**获取违约总额*/
	public BigDecimal getBreakMoneyAmount(){
		return this.breakMoneyAmount;
	}
	
	/**设置违约总额*/
	public void setBreakMoneyAmount(BigDecimal breakMoneyAmount){
		this.breakMoneyAmount=breakMoneyAmount;
		
	}
	/**获取起始合作时间*/
	public String getPartnerTime(){
		return this.partnerTime;
	}
	
	/**设置起始合作时间*/
	public void setPartnerTime(String partnerTime){
		this.partnerTime=partnerTime;
		
	}
	/**获取下架时间*/
	public String getStopTime(){
		return this.stopTime;
	}
	
	/**设置下架时间*/
	public void setStopTime(String stopTime){
		this.stopTime=stopTime;
		
	}
	/**获取商户状态*/
	public String getState(){
		return this.state;
	}
	
	/**设置商户状态*/
	public void setState(String state){
		this.state=state;
		
	}
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.Merchant:["+"merchantId="+merchantId+","+"merchantName="+merchantName+","+"merchantType="+merchantType+","+"merchantAddress="+merchantAddress+","+"isAssurance="+isAssurance+","+"depositeAmount="+depositeAmount+","+"depositeRate="+depositeRate+","+"depositeBalance="+depositeBalance+","+"isShared="+isShared+","+"sharedRate="+sharedRate+","+"brokerageRate="+brokerageRate+","+"brokerageAmount="+brokerageAmount+","+"brokerageLess="+brokerageLess+","+"applyAmount="+applyAmount+","+"applyMoneyAmount="+applyMoneyAmount+","+"loanAmount="+loanAmount+","+"loanMoneyAmount="+loanMoneyAmount+","+"breakAmount="+breakAmount+","+"breakMoneyAmount="+breakMoneyAmount+","+"partnerTime="+partnerTime+","+"stopTime="+stopTime+","+"state="+state+"]";
	}
}
