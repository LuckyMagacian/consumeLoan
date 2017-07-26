package com.lanxi.consumeLoan.test;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

import com.lanxi.util.entity.MyClassLoader;
import com.lanxi.util.utils.FileUtil;

public class Test20170720 {
	@Test
	public void test1() throws Exception{
		JarFile jar=new JarFile("C:/Users/yangyuanjian/.m2/repository/com/lanxi/LanxiToken/1.1/LanxiToken-1.1.jar");
		System.out.println(jar.getManifest());
		Enumeration<JarEntry> jars=jar.entries();
		while(jars.hasMoreElements()){
			JarEntry each=jars.nextElement();
			if(each.getName().endsWith("class")&&!each.getName().contains("target")){
				
				System.out.println(each.getName());
			}
		}
	}
	@Test
	public void test2()throws Exception{
		File[] file=FileUtil.getFilesOppositeClassPath("com/lanxi/consumeLoan/functions", null);
		System.out.println(file.length);
	}
}
