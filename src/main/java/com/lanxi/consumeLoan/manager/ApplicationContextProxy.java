package com.lanxi.consumeLoan.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
/**
 * spring上下文代理类
 * @author yangyuanjian
 *
 */
@Service
public class ApplicationContextProxy {
	/**spring 上下文*/
	@Resource
	private ApplicationContext ac;

	/**
	 * 根据t的类型获取一个bean
	 * @param t
	 * @return
	 */
	public <T> T getBean(Class<T> t){
		return ac.getBean(t);
	}
	/**
	 * 根据类名获取一个bean
	 * @param className 类名
	 * @return
	 */
	public Object getBean(String className){
		Class<?> clazz=null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return  ac.getBean(clazz);
	}
	/**
	 * 根据t的类型获取多个bean
	 * @param t
	 * @return
	 */
	public <T> Map<String, T> getBeans(Class<T> t){
		return ac.getBeansOfType(t);
	}
	
	public ApplicationContext getAc() {
		return ac;
	}

	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}
	
}
