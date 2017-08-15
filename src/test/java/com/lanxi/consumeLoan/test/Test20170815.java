package com.lanxi.consumeLoan.test;

import org.junit.Test;

import static com.lanxi.util.utils.SqlUtilForDB.*;
public class Test20170815 {
	@Test
	public void test1(){
		makeOne(getTable(getConnection(), "apply"), "", "", false, false);
	}
}
