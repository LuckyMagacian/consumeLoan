package com.lanxi.consumeLoan.basic;

import java.math.BigDecimal;

public abstract class AbstractAccount {
	private BigDecimal brokerage;
	private BigDecimal provisionsOfRisk;
	private BigDecimal serviceCharge;
	public BigDecimal getBrokerage() {
		return brokerage;
	}
	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}
	public BigDecimal getProvisionsOfRisk() {
		return provisionsOfRisk;
	}
	public void setProvisionsOfRisk(BigDecimal provisionsOfRisk) {
		this.provisionsOfRisk = provisionsOfRisk;
	}
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
}
