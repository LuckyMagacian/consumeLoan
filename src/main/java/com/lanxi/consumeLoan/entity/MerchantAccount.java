package com.lanxi.consumeLoan.entity;

import java.math.BigDecimal;
import java.util.List;

import com.lanxi.util.utils.BeanUtil;

import java.lang.String;
import java.lang.reflect.Field;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-28 13:47:19
 */

public class MerchantAccount{
	/**商户编号*/
	private String merchantId;
	
	/**佣金*/
	private BigDecimal brokerage;
	
	/**风险准备金*/
	private BigDecimal provisionsOfRisk;
	
	/**服务费*/
	private BigDecimal serviceCharge;
	
	public MerchantAccount() {
		init();
	}
	
	/**获取商户编号*/
	public String getMerchantId(){
		return this.merchantId;
	}
	
	/**设置商户编号*/
	public void setMerchantId(String merchantId){
		this.merchantId=merchantId;
		
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
		return "com.lanxi.consumeLoan.entity.MerchantAccount:["+"merchantId="+merchantId+","+"brokerage="+brokerage+","+"provisionsOfRisk="+provisionsOfRisk+","+"serviceCharge="+serviceCharge+"]";
	}	
	public void hide1(){
		
	}
	public void hide2(){
		
	}
	public void hide3(){
		
	}
	public void init() {
		List<Field> fields=BeanUtil.getFieldListNoStatic(this);
		for(Field each:fields) {
			if(each.getType().equals(BigDecimal.class))
				BeanUtil.set(this, each.getName(), new BigDecimal("0.00000"));
		}
	}
}
