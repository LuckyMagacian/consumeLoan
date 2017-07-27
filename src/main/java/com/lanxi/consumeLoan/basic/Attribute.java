package com.lanxi.consumeLoan.basic;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.util.interfaces.ToJson;


public class Attribute<T> implements ToJson{
	private String name;
	private T value;
	private String type;
	public Attribute(){};
	public Attribute(String name,T value){
		this.name=name;
		this.value=value;
		this.type=value.getClass().getName();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		if(value!=null){
			if(!value.getClass().getName().equals(type))
				throw new IllegalArgumentException("属性["+name+"]的值的类型为["+type+"],参数类型为["+value.getClass().getName()+"],类型不匹配!");
		}
		this.value = value;
	}
	public void setType(Class<?> clazz){
		this.type=clazz.getName();
	}
	public void setType(String clazzName){
		this.type=clazzName;
	}
	public String getType(){
		if(type==null)
			type=value.getClass().getName();
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public Attribute<T> fromJson(String json) {
		JSONObject jObj=JSONObject.parseObject(json);
		this.name=jObj.getString("name");
		this.type=jObj.getString("type");
		Class<?> clazz;
		try {
			clazz = Class.forName(this.type);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		if(type.equals("java.lang.String"))
			value=(T) jObj.getString("value");
		else
			this.value=(T)JSONObject.parseObject(jObj.getString("value"),clazz);
		return this;
	}
	
	@Override
	public String toString() {
		return "Attribute [name=" + getName() + ", value=" + getValue() + ", type=" +getType() + "]";
	}

	
}
