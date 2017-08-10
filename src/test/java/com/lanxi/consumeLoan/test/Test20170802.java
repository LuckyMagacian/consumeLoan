package com.lanxi.consumeLoan.test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.junit.Test;

import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.SystemAccount;
import com.lanxi.util.utils.OtherUtil;

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
	@Test
	public void test2() {
		String str1=null;
		Object obj1=str1;
		String str2=(String)obj1;
		System.out.println(str2);
	}
	@Test
	public void testInit() {
		System.out.println(new OtherUtil.PhoneNumAnalyst("17857311577").getPhoneInfo());
		String regex="[1-9][0-9]{0,2}0{3}\\.?[0]{0,5}";
		BigDecimal total=new BigDecimal("193020.0154546");
		BigDecimal decimal2=new BigDecimal("50000.00000");
		BigDecimal mod=new BigDecimal("1000");
		System.out.println(total.remainder(mod));
		System.out.println(decimal2.remainder(mod));
		System.out.println(decimal2.remainder(mod).equals(new BigDecimal("0.00000")));
		System.out.println(total.toString().matches(regex));
		System.out.println(decimal2.toString().matches(regex));
		System.out.println("500".matches(regex));
		System.out.println("5000000".matches(regex));
		System.out.println("555000".matches(regex));
		System.out.println(total.compareTo(decimal2));
		System.out.println(total.compareTo(new BigDecimal("500000.123456")));
		System.out.println(total.compareTo(new BigDecimal("193020.0154546")));
		System.out.println(decimal2.remainder(new BigDecimal("1000")).compareTo(new BigDecimal("0.0000")));
	}
	
	@Test
	public void testMMM() {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		System.out.println("20170810".matches("[0-9]{8}"));
		System.out.println(format.toPattern().matches("yyyyMMdd"));
	}
}
