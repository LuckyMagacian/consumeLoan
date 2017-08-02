package com.lanxi.consumeLoan.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.lanxi.consumeLoan.entity.SystemAccount;

public class Test20170802 {
	
	@Test
	public void test1(){
		SystemAccount account1=new SystemAccount();
		SystemAccount account2=new SystemAccount();
		System.out.println(account1.hashCode());
		System.out.println(account2.hashCode());
		System.out.println(account1.equals(account2));
		System.out.println(account1==account2);
		
		
		account1.setBrokerageRate(new BigDecimal(0.02));
		System.out.println(account1.hashCode());
		System.out.println(account2.hashCode());
		System.out.println(account1.equals(account2));
		System.out.println(account1==account2);
		
	}
}
