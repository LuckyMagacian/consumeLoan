package com.lanxi.consumeLoan.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class Test20170725 {
	
	@Test
	public void test1(){
		String json="{'money':1.99999999}";
		Temp temp=JSONObject.parseObject(json, Temp.class);
		System.out.println(temp.getMoney().getClass());
	}
	
	public static class Temp{
		BigDecimal money;

		public BigDecimal getMoney() {
			return money;
		}

		public void setMoney(BigDecimal money) {
			this.money = money;
		}

		@Override
		public String toString() {
			return "Temp [money=" + money + "]";
		}
		
	}
}
