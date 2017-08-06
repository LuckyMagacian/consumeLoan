package com.lanxi.consumeLoan.entity;

import java.math.BigDecimal;
import java.util.List;

import com.lanxi.util.utils.BeanUtil;

import java.lang.String;
import java.lang.reflect.Field;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-08-03 15:25:52
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
	
	/**佣金比例*/
	private BigDecimal brokerageRate;
	
	/**风险准备金比例*/
	private BigDecimal provisionsOfRiskRate;
	
	/**服务费比例*/
	private BigDecimal serviceChargeRate;
	
	/**风险准备金最大值*/
	private BigDecimal provisionsOfRiskMax;
	
	/**版本号*/
	private BigDecimal version;
	
	public SystemAccount() {
		Apply.init(this);
	}
	
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
	/**获取佣金比例*/
	public BigDecimal getBrokerageRate(){
		return this.brokerageRate;
	}
	
	/**设置佣金比例*/
	public void setBrokerageRate(BigDecimal brokerageRate){
		this.brokerageRate=brokerageRate;
		
	}
	/**获取风险准备金比例*/
	public BigDecimal getProvisionsOfRiskRate(){
		return this.provisionsOfRiskRate;
	}
	
	/**设置风险准备金比例*/
	public void setProvisionsOfRiskRate(BigDecimal provisionsOfRiskRate){
		this.provisionsOfRiskRate=provisionsOfRiskRate;
		
	}
	/**获取服务费比例*/
	public BigDecimal getServiceChargeRate(){
		return this.serviceChargeRate;
	}
	
	/**设置服务费比例*/
	public void setServiceChargeRate(BigDecimal serviceChargeRate){
		this.serviceChargeRate=serviceChargeRate;
		
	}
	/**获取风险准备金最大值*/
	public BigDecimal getProvisionsOfRiskMax(){
		return this.provisionsOfRiskMax;
	}
	
	/**设置风险准备金最大值*/
	public void setProvisionsOfRiskMax(BigDecimal provisionsOfRiskMax){
		this.provisionsOfRiskMax=provisionsOfRiskMax;
		
	}
	/**获取版本号*/
	public BigDecimal getVersion(){
		return this.version;
	}
	
	/**设置版本号*/
	public void setVersion(BigDecimal version){
		this.version=version;
		
	}
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.SystemAccount:["+"accountId="+accountId+","+"brokerage="+brokerage+","+"provisionsOfRisk="+provisionsOfRisk+","+"serviceCharge="+serviceCharge+","+"brokerageRate="+brokerageRate+","+"provisionsOfRiskRate="+provisionsOfRiskRate+","+"serviceChargeRate="+serviceChargeRate+","+"provisionsOfRiskMax="+provisionsOfRiskMax+","+"version="+version+"]";
	}

}
