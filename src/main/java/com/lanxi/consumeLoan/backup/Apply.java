package com.lanxi.consumeLoan.backup;

import java.math.BigDecimal;

import com.lanxi.util.interfaces.ToJson;
import com.lanxi.util.interfaces.ToMap;

import java.lang.String;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-31 15:35:19
 */

public class Apply implements ToMap,ToJson{
	/**申请编号*/
	private String applyId;
	
	/**申请者姓名*/
	private String name;
	
	/**申请者性别*/
	private String sex;
	
	/**申请者居住地址*/
	private String address;
	
	/**申请者身份证号码*/
	private String idNumber;
	
	/**申请金额*/
	private BigDecimal applyMoney;
	
	/**申请者手机号码*/
	private String phone;
	
	/**手机校验码*/
	private String verifyCode;
	
	/**商户编号*/
	private String merchantId;
	
	/**销售员手机号码*/
	private String salesManPhone;
	
	/**申请时间*/
	private String applyTime;
	
	/**放款时间*/
	private String loanTime;
	
	/**放款金额*/
	private BigDecimal loanMoney;
	
	/**是否逾期*/
	private String isOverdue;
	
	/**逾期金额*/
	private BigDecimal overdueMoney;
	
	/**申请状态*/
	private String state;
	
	/**佣金比例*/
	private BigDecimal brokerageRate;
	
	/**佣金*/
	private BigDecimal brokerage;
	
	/**服务费比例*/
	private BigDecimal serviceRate;
	
	/**服务费*/
	private BigDecimal serviceCharge;
	
	/**担保比例*/
	private BigDecimal depositeRate;
	
	/**违约时间*/
	private String breakTime;
	
	/**违约金额*/
	private BigDecimal breakMoney;
	
	/**所属商家名称*/
	private String merchantName;
	
	/**商家类别*/
	private String merchantType;
	
	/**是否担保*/
	private String isAssurance;
	
	/**获取申请编号*/
	public String getApplyId(){
		return this.applyId;
	}
	
	/**设置申请编号*/
	public void setApplyId(String applyId){
		this.applyId=applyId;
		
	}
	/**获取申请者姓名*/
	public String getName(){
		return this.name;
	}
	
	/**设置申请者姓名*/
	public void setName(String name){
		this.name=name;
		
	}
	/**获取申请者性别*/
	public String getSex(){
		return this.sex;
	}
	
	/**设置申请者性别*/
	public void setSex(String sex){
		this.sex=sex;
		
	}
	/**获取申请者居住地址*/
	public String getAddress(){
		return this.address;
	}
	
	/**设置申请者居住地址*/
	public void setAddress(String address){
		this.address=address;
		
	}
	/**获取申请者身份证号码*/
	public String getIdNumber(){
		return this.idNumber;
	}
	
	/**设置申请者身份证号码*/
	public void setIdNumber(String idNumber){
		this.idNumber=idNumber;
		
	}
	/**获取申请金额*/
	public BigDecimal getApplyMoney(){
		return this.applyMoney;
	}
	
	/**设置申请金额*/
	public void setApplyMoney(BigDecimal applyMoney){
		this.applyMoney=applyMoney;
		
	}
	/**获取申请者手机号码*/
	public String getPhone(){
		return this.phone;
	}
	
	/**设置申请者手机号码*/
	public void setPhone(String phone){
		this.phone=phone;
		
	}
	/**获取手机校验码*/
	public String getVerifyCode(){
		return this.verifyCode;
	}
	
	/**设置手机校验码*/
	public void setVerifyCode(String verifyCode){
		this.verifyCode=verifyCode;
		
	}
	/**获取商户编号*/
	public String getMerchantId(){
		return this.merchantId;
	}
	
	/**设置商户编号*/
	public void setMerchantId(String merchantId){
		this.merchantId=merchantId;
		
	}
	/**获取销售员手机号码*/
	public String getSalesManPhone(){
		return this.salesManPhone;
	}
	
	/**设置销售员手机号码*/
	public void setSalesManPhone(String salesManPhone){
		this.salesManPhone=salesManPhone;
		
	}
	/**获取申请时间*/
	public String getApplyTime(){
		return this.applyTime;
	}
	
	/**设置申请时间*/
	public void setApplyTime(String applyTime){
		this.applyTime=applyTime;
		
	}
	/**获取放款时间*/
	public String getLoanTime(){
		return this.loanTime;
	}
	
	/**设置放款时间*/
	public void setLoanTime(String loanTime){
		this.loanTime=loanTime;
		
	}
	/**获取放款金额*/
	public BigDecimal getLoanMoney(){
		return this.loanMoney;
	}
	
	/**设置放款金额*/
	public void setLoanMoney(BigDecimal loanMoney){
		this.loanMoney=loanMoney;
		
	}
	/**获取是否逾期*/
	public String getIsOverdue(){
		return this.isOverdue;
	}
	
	/**设置是否逾期*/
	public void setIsOverdue(String isOverdue){
		this.isOverdue=isOverdue;
		
	}
	/**获取逾期金额*/
	public BigDecimal getOverdueMoney(){
		return this.overdueMoney;
	}
	
	/**设置逾期金额*/
	public void setOverdueMoney(BigDecimal overdueMoney){
		this.overdueMoney=overdueMoney;
		
	}
	/**获取申请状态*/
	public String getState(){
		return this.state;
	}
	
	/**设置申请状态*/
	public void setState(String state){
		this.state=state;
		
	}
	/**获取佣金比例*/
	public BigDecimal getBrokerageRate(){
		return this.brokerageRate;
	}
	
	/**设置佣金比例*/
	public void setBrokerageRate(BigDecimal brokerageRate){
		this.brokerageRate=brokerageRate;
		
	}
	/**获取佣金*/
	public BigDecimal getBrokerage(){
		return this.brokerage;
	}
	
	/**设置佣金*/
	public void setBrokerage(BigDecimal brokerage){
		this.brokerage=brokerage;
		
	}
	/**获取服务费比例*/
	public BigDecimal getServiceRate(){
		return this.serviceRate;
	}
	
	/**设置服务费比例*/
	public void setServiceRate(BigDecimal serviceRate){
		this.serviceRate=serviceRate;
		
	}
	/**获取服务费*/
	public BigDecimal getServiceCharge(){
		return this.serviceCharge;
	}
	
	/**设置服务费*/
	public void setServiceCharge(BigDecimal serviceCharge){
		this.serviceCharge=serviceCharge;
		
	}
	/**获取担保比例*/
	public BigDecimal getDepositeRate(){
		return this.depositeRate;
	}
	
	/**设置担保比例*/
	public void setDepositeRate(BigDecimal depositeRate){
		this.depositeRate=depositeRate;
		
	}
	/**获取违约时间*/
	public String getBreakTime(){
		return this.breakTime;
	}
	
	/**设置违约时间*/
	public void setBreakTime(String breakTime){
		this.breakTime=breakTime;
		
	}
	/**获取违约金额*/
	public BigDecimal getBreakMoney(){
		return this.breakMoney;
	}
	
	/**设置违约金额*/
	public void setBreakMoney(BigDecimal breakMoney){
		this.breakMoney=breakMoney;
		
	}
	/**获取所属商家名称*/
	public String getMerchantName(){
		return this.merchantName;
	}
	
	/**设置所属商家名称*/
	public void setMerchantName(String merchantName){
		this.merchantName=merchantName;
		
	}
	/**获取商家类别*/
	public String getMerchantType(){
		return this.merchantType;
	}
	
	/**设置商家类别*/
	public void setMerchantType(String merchantType){
		this.merchantType=merchantType;
		
	}
	/**获取是否担保*/
	public String getIsAssurance(){
		return this.isAssurance;
	}
	
	/**设置是否担保*/
	public void setIsAssurance(String isAssurance){
		this.isAssurance=isAssurance;
		
	}
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.Apply:["+"applyId="+applyId+","+"name="+name+","+"sex="+sex+","+"address="+address+","+"idNumber="+idNumber+","+"applyMoney="+applyMoney+","+"phone="+phone+","+"verifyCode="+verifyCode+","+"merchantId="+merchantId+","+"salesManPhone="+salesManPhone+","+"applyTime="+applyTime+","+"loanTime="+loanTime+","+"loanMoney="+loanMoney+","+"isOverdue="+isOverdue+","+"overdueMoney="+overdueMoney+","+"state="+state+","+"brokerageRate="+brokerageRate+","+"brokerage="+brokerage+","+"serviceRate="+serviceRate+","+"serviceCharge="+serviceCharge+","+"depositeRate="+depositeRate+","+"breakTime="+breakTime+","+"breakMoney="+breakMoney+","+"merchantName="+merchantName+","+"merchantType="+merchantType+","+"isAssurance="+isAssurance+"]";
	}
	/**
	 * 商户佣金查询时隐藏信息
	 */
	public void hide1(){
		this.setSex(null);
		this.setAddress(null);
		this.setVerifyCode(null);
		this.setMerchantId(null);
		this.setSalesManPhone(null);
		this.setIsOverdue(null);
		this.setOverdueMoney(null);
		this.setServiceRate(null);
		this.setBrokerageRate(null);
		this.setDepositeRate(null);
		this.setBreakMoney(null);
		this.setBreakTime(null);
		this.setMerchantName(null);
		this.setServiceCharge(null);
	}
	/**
	 * 商户订单查询时隐藏信息
	 */
	public void hide2(){
		this.setServiceRate(null);
		this.setServiceCharge(null);
		this.setSex(null);
		this.setAddress(null);
		this.setVerifyCode(null);
		this.setMerchantId(null);
		this.setSalesManPhone(null);
		this.setLoanTime(null);
		this.setLoanMoney(null);
		this.setIsOverdue(null);
		this.setOverdueMoney(null);
		this.setBrokerageRate(null);
		this.setDepositeRate(null);
		this.setBreakMoney(null);
		this.setBreakTime(null);
		this.setMerchantName(null);;
	}
}