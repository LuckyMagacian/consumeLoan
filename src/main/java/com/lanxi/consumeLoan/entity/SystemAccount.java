package com.lanxi.consumeLoan.entity;

import java.math.BigDecimal;
import java.lang.String;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-24 10:25:36
 */

public class SystemAccount{
	/**账户编号*/
	private String accountId;
	
	/**佣金*/
	private BigDecimal brokerage;
	
	/**风险准备金*/
	private BigDecimal provisionsOfRisk;
	
	/**服务费*/
	private BigDecimal serviceCharge;
	
	/**获取账户编号*/
	public String getAccountId(){
		return this.accountId;
	}
	
	/**设置账户编号*/
	public void setAccountId(String accountId){
		this.accountId=accountId;
		
	}
	/**获取佣金*/
	public BigDecimal getBrokerage(){
		return this.brokerage;
	}
	
	/**设置佣金*/
	public void setBrokerage(BigDecimal brokerage){
		this.brokerage=brokerage;
		
	}
	/**获取风险准备金*/
	public BigDecimal getProvisionsOfRisk(){
		return this.provisionsOfRisk;
	}
	
	/**设置风险准备金*/
	public void setProvisionsOfRisk(BigDecimal provisionsOfRisk){
		this.provisionsOfRisk=provisionsOfRisk;
		
	}
	/**获取服务费*/
	public BigDecimal getServiceCharge(){
		return this.serviceCharge;
	}
	
	/**设置服务费*/
	public void setServiceCharge(BigDecimal serviceCharge){
		this.serviceCharge=serviceCharge;
		
	}
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.SystemAccount:["+"accountId="+accountId+","+"brokerage="+brokerage+","+"provisionsOfRisk="+provisionsOfRisk+","+"serviceCharge="+serviceCharge+"]";
	}
}
