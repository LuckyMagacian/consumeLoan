package com.lanxi.consumeLoan.backup;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.Function;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-21 14:15:01
 */

public class Role{
	/**角色名*/
	private String roleName;
	
	/**接口权限*/
	private List<String> authority;
	
	/**获取角色名*/
	public String getRoleName(){
		return this.roleName;
	}
	
	/**设置角色名*/
	public void setRoleName(String roleName){
		this.roleName=roleName;
		
	}
	/**获取接口权限*/
	public String getAuthority(){
		if(authority==null)
			return null;
		return JSONObject.toJSONString(authority);
	}
	
	/**设置接口权限*/
	public void setAuthority(String jsonStr){
		this.authority=JSONArray.parseArray(jsonStr,String.class);	
	}
	/**获取接口权限*/
	public List<String> getAuthorityObject(){
		return this.authority;
	}
	/**设置接口权限*/
	public void setAuthorityObject(List<String> list){
		this.authority=list;
	}
	/**添加接口权限*/
	public void addAuthority(List<Function> funs){
		for(Function each:funs){
			addAuthority(each);
		}
	}
	/**添加接口权限*/
	public void addAuthority(Class<? extends Function> funClass){
		if(authority==null)
			authority=new ArrayList<>();
		authority.add(funClass.getName());
	}
	/**添加接口权限*/
	public void addAuthority(Function fun){
		if(authority==null)
			authority=new ArrayList<>();
		authority.add(fun.getClass().getName());
	}
	/**移除接口权限*/
	public void removeAuthority(Class<? extends Function> funClass){
		if(authority==null)
			authority=new ArrayList<>();
		authority.remove(funClass.getName());
	}
	/**移除接口权限*/
	public void removeAuthority(Function fun){
		if(authority==null)
			authority=new ArrayList<>();
		authority.remove(fun.getClass().getName());
	}
	/**移除接口权限*/
	public void removeAuthority(String funName){
		if(authority==null)
			authority=new ArrayList<>();
		authority.remove(funName);
	}
	
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.Role:["+"roleName="+roleName+","+"authority="+authority+"]";
	}
}
