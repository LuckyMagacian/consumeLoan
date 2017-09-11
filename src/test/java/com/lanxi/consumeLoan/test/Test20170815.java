package com.lanxi.consumeLoan.test;

import org.junit.Test;

import com.lanxi.consumeLoan.consts.ConstParam;
import com.lanxi.consumeLoan.service.CheckService;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.RandomUtil;
import com.lanxi.util.utils.RedisCacheUtil;
import com.lanxi.util.utils.TimeUtil;

import redis.clients.jedis.Jedis;

import static com.lanxi.util.utils.SqlUtilForDB.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;
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
	@Test
	public void test3() {
		String dateTime="20170809112365";
		System.out.println(TimeUtil.toPreferDate(dateTime));
	}
	@Test
	public void test4() {
		RedisCacheUtil.setConn(new Jedis("192.168.1.61",6379 ));
		RedisCacheUtil util=new RedisCacheUtil();
//		util.delete(ConstParam.FUNCTION_NAME_LOGIN+"192.168.17.59");
		System.out.println(util.get(ConstParam.FUNCTION_NAME_LOGIN+"192.168.0.106"));
		System.out.println(util.get(ConstParam.USER_STATE_LOGIN+"1005"));
		System.out.println(util.get("com.lanxi.consumeloan-1.0-has-login-1005"));
	}
	@Test
	public void test5() {
		LoggerUtil.init();
		CheckService checkService=new CheckService();
		Stream.generate(()->new Thread(()->System.out.println(checkService.isPhone("15068610940")))).parallel().limit(20).forEach(t->t.run());
	}
	public static void main(String[] args) throws InterruptedException {
		LoggerUtil.init();
		final CountDownLatch latch=new CountDownLatch(Runtime.getRuntime().availableProcessors());
		CheckService checkService=new CheckService();
		Stream.generate(()->new Thread(()->{System.out.println(checkService.isPhoneNew("15068610940"));latch.countDown();})).parallel().limit(Runtime.getRuntime().availableProcessors()).forEach(t->t.start());
		latch.await();
	}
}
