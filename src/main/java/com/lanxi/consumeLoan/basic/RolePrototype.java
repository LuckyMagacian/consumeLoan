package com.lanxi.consumeLoan.basic;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RolePrototype {
	private String roleName;
	private List<String> authority;
	
	public void addAuthority(List<Function> funs){
		for(Function each:funs){
			addAuthority(each);
		}
	}
	public void addAuthority(Class<? extends Function> funClass){
		if(authority==null)
			authority=new ArrayList<>();
		authority.add(funClass.getName());
	}
	public void addAuthority(Function fun){
		if(authority==null)
			authority=new ArrayList<>();
		authority.add(fun.getClass().getName());
	}
	public void removeAuthority(Class<? extends Function> funClass){
		if(authority==null)
			authority=new ArrayList<>();
		authority.remove(funClass.getName());
	}
	public void removeAuthority(Function fun){
		if(authority==null)
			authority=new ArrayList<>();
		authority.remove(fun.getClass().getName());
	}
	public void removeAuthority(String funName){
		if(authority==null)
			authority=new ArrayList<>();
		authority.remove(funName);
	}
	public String getAuthority(){
		if(authority==null)
			return null;
//		if(authority.isEmpty())
//			return null;
		return JSONObject.toJSONString(authority);
	}
	
	public List<String> getAuthorityObject(){
		return this.authority;
	}
	
	public void setAuthority(String jsonStr) {
		this.authority=JSONArray.parseArray(jsonStr,String.class);	
	}
	
	public void setAuthorityObject(List<String> list){
		this.authority=list;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String name) {
		this.roleName = name.trim();
	}

	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.Role:["+"roleName="+roleName+","+"authority="+getAuthority()+"]";
	}
	
	public RolePrototype fromJson(String jsonStr){
		JSONObject jobj=JSONObject.parseObject(jsonStr);
		roleName=jobj.getString("roleName");
		for(Object each:jobj.getJSONArray("authority"))
			authority.add(each.toString());
		return this;
	}
}
