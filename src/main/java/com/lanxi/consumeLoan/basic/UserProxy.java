package com.lanxi.consumeLoan.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.interfaces.ToJson;
import com.lanxi.util.interfaces.ToMap;

public class UserProxy implements ToMap,ToJson{
	/**map 存放 user属性*/
	private Map<String, Object> map;
	public UserProxy() {
		map=new HashMap<>();
	}
	
	public UserProxy(User user){
		map=new HashMap<>();
		Map<String, Attribute<?>> temp=user.getAttributesObject();
		for(Entry<String, Attribute<?>> each:temp.entrySet()){
			map.put(each.getValue().getName(), each.getValue().getValue());
		}
		
	}
	public String getName() {
		return (String) map.get("name");
	}
	public void setName(String name) {
		map.put("name", name);
	}
	public String getPhone() {
		return (String) map.get("phone");
	}
	public void setPhone(String phone) {
		map.put("phone", phone);
	}
	
	public String getRoleName() {
		return (String) map.get("roleName");
	}
	public void setRoleName(String roleName) {
		map.put("roleName", roleName);
	}
	public Object get(String name){
		return map.get(name);
	}
	public void set(String name,Object value){
		map.put(name, value);
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(map);
	}
	public Map<String, Object> toUser(){
		Map<String, Object> temp=new HashMap<>();
		temp.put("phone", map.get("phone"));
		temp.put("roleName", map.get("roleName"));
		temp.put("name", map.get("name"));
		temp.put("createTime", map.get("createTime"));
		temp.put("state", map.get("state"));
		temp.put("createBy", map.get("createBy"));
		return temp;
	} 
	public Map<String, Object> toSalesMan(){
		Map<String, Object> temp=toUser();
		temp.put("merchantName", map.get("merchantName"));
		temp.put("merchantAddress", map.get("merchantAddress"));
		return temp;
	}
	public Map<String, Object> toShopKeeper(){
		return toSalesMan();
	}
	public Map<String, Object> toCustomerManager(){
		Map<String, Object> temp=toUser();
		temp.put("netAddress", map.get("netAddress"));
		return temp;
	}
	public Map<String, Object> toAdmin(){
		return toUser();
	}
	
	public Map<String, Object> getMap(){
		return map;
	}
}
