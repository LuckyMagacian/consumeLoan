package com.lanxi.consumeLoan.entity;

import java.math.BigDecimal;
import java.lang.String;
import java.lang.Byte;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-24 10:25:36
 */

public class MerchantAccountRecord{
	/**记录时间戳-纳秒*/
	private String nanoTime;
	
	/**商户编号*/
	private String merchantId;
	
	/**是否增加*/
	private Byte[] isAdd;
	
	/**金额变动*/
	private BigDecimal monetyChange;
	
	/**记录时间*/
	private String recordTime;
	
	/**金额类型 服务费|准备金|佣金*/
	private String moneyType;
	
	/**变动原因*/
	private String reason;
	
	/**获取记录时间戳-纳秒*/
	public String getNanoTime(){
		return this.nanoTime;
	}
	
	/**设置记录时间戳-纳秒*/
	public void setNanoTime(String nanoTime){
		this.nanoTime=nanoTime;
		
	}
	/**获取商户编号*/
	public String getMerchantId(){
		return this.merchantId;
	}
	
	/**设置商户编号*/
	public void setMerchantId(String merchantId){
		this.merchantId=merchantId;
		
	}
	/**获取是否增加*/
	public Byte[] getIsAdd(){
		return this.isAdd;
	}
	
	/**设置是否增加*/
	public void setIsAdd(Byte[] isAdd){
		this.isAdd=isAdd;
		
	}
	/**获取金额变动*/
	public BigDecimal getMonetyChange(){
		return this.monetyChange;
	}
	
	/**设置金额变动*/
	public void setMonetyChange(BigDecimal monetyChange){
		this.monetyChange=monetyChange;
		
	}
	/**获取记录时间*/
	public String getRecordTime(){
		return this.recordTime;
	}
	
	/**设置记录时间*/
	public void setRecordTime(String recordTime){
		this.recordTime=recordTime;
		
	}
	/**获取金额类型 服务费|准备金|佣金*/
	public String getMoneyType(){
		return this.moneyType;
	}
	
	/**设置金额类型 服务费|准备金|佣金*/
	public void setMoneyType(String moneyType){
		this.moneyType=moneyType;
		
	}
	/**获取变动原因*/
	public String getReason(){
		return this.reason;
	}
	
	/**设置变动原因*/
	public void setReason(String reason){
		this.reason=reason;
		
	}
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.MerchantAccountRecord:["+"nanoTime="+nanoTime+","+"merchantId="+merchantId+","+"isAdd="+isAdd+","+"monetyChange="+monetyChange+","+"recordTime="+recordTime+","+"moneyType="+moneyType+","+"reason="+reason+"]";
	}
}
