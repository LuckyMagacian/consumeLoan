package com.lanxi.consumeLoan.basic;

import java.util.Map;

public interface Function {
	public String getName();
	public void setName(String name);
	public Map<String,Attribute<?>> getAttributes();
	public void addAttribute(Attribute<?> attribute);
	public boolean hasAuthority(UserPrototype user);
	public RetMessage successNotice();
	public RetMessage failNotice();
	public RetMessage showInfo();
	public RetMessage excuted(Map<String,Object> args);

}
