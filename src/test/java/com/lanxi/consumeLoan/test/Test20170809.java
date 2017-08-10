package com.lanxi.consumeLoan.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class Test20170809 {
	
	public static void main(String[] args) {
		Random random=new Random();
		int times=10000;
		List<BigDecimal> list=new ArrayList<>();
		Map<Integer, BigDecimal> map=new HashMap<>();
		Integer temp=null;
		for(int i=0;i<times;i++) {
			temp=random.nextInt(100000);
			if(temp<10)
				map.put(i, new BigDecimal(temp/100000D));
			list.add(new BigDecimal(temp/100000D));
//			System.out.println(temp);
		}
		if(map.size()==0)
			System.out.println("雅丽哥随机了"+times+"次,小于0.0001的数一个也没有!");
		else {
			StringBuilder builder=new StringBuilder("雅丽哥随机了"+times+"次,第");
			for(Entry<Integer, BigDecimal> each:map.entrySet()) {
				builder.append(each.getKey()+",");
			}
			builder.replace(builder.length()-1, builder.length(), "");
			builder.append("次的数小于0.0001,分别为");
			for(Entry<Integer, BigDecimal> each:map.entrySet()) {
				builder.append(each.getValue()+",");
			}
			builder.replace(builder.length()-1, builder.length(), "");
			System.out.println(builder);
		}
	}
}
