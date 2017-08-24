package com.lanxi.consumeLoan.test;

import org.junit.Test;

import com.lanxi.util.utils.RandomUtil;
import com.lanxi.util.utils.TimeUtil;

import static com.lanxi.util.utils.SqlUtilForDB.*;
public class Test20170815 {
	@Test
	public void test1(){
		makeOne(getTable(getConnection(), "apply"), "", "", false, false);
	}
	@Test
	public void test2() {
		System.out.println(TimeUtil.getDate()+TimeUtil.getNanoTime()+RandomUtil.getRandomNumber(6));
		System.out.println((TimeUtil.getDate()+TimeUtil.getNanoTime()+RandomUtil.getRandomNumber(6)).length());
		System.out.println("2017082263334211140319350906398".length());
		System.out.println("2017082293452191468734670843".length());
	}
}
