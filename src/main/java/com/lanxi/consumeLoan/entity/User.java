package com.lanxi.consumeLoan.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.UserProxy;

/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-21 14:15:01
 */

public class User{
	/**手机号码*/
	private String phone;
	
	/**角色名*/
	private String roleName;
	
	/**属性*/
	private Map<String, Attribute<?>> attributes;
	public User() {
		
	}
	/**
	 * 设置 属性中名称为attrName的属性的值为obj
	 * @param attrName	属性名
	 * @param obj		属性值
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(String attrName,Object obj){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		Attribute attr=attributes.get(attrName);
		if(attr==null) {
			addAttribute(new Attribute(attrName, obj));
		}else
			attr.setValue(obj);
	}
	/**
	 * 获取属性中名称为attributeName的属性
	 * @param attributeName 属性名
	 * @return 属性
	 */
	public Attribute<?> get(String attributeName){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		return this.attributes.get(attributeName);
	}
	/**
	 * 添加json型的属性
	 * @param jsonStr 属性的json形式
	 */
	@SuppressWarnings("rawtypes")
	public void addAttribute(String jsonStr){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		addAttribute(new Attribute().fromJson(jsonStr));
	}
	/**
	 * 体哪家 json形式的属性map
	 * @param jsonStr
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addAttributes(String jsonStr){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		Map<String,JSONObject> map=JSONObject.parseObject(jsonStr,HashMap.class);
		for(Entry<String, JSONObject> each:map.entrySet())
			addAttribute(new Attribute().fromJson(each.getValue().toJSONString()));
	}
	/**
	 * 添加属性
	 * @param attribute 属性
	 */
	public void addAttribute(Attribute<?> attribute){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		if(attributes.containsKey(attribute.getName()))
			return;
		attributes.put(attribute.getName(), attribute);
	}
	/**
	 * 添加多个属性
	 * @param attrs
	 */
	public void addAttribute(Map<String, Attribute<?>> attrs){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		for(Entry<String, Attribute<?>> each:attrs.entrySet()){
			if(this.attributes.containsKey(each.getKey()))
				continue;
			this.attributes.put(each.getKey(), each.getValue());
		}
	}
	/**
	 * 添加属性列表
	 * @param attrs
	 */
	public void addAttribute(List<Attribute<?>> attrs){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		for(Attribute<?> each:attrs){
			if(this.attributes.containsKey(each.getName()))
				continue;
			attributes.put(each.getName(), each);
		}
	}
	
	/**获取手机号码*/
	public String getPhone(){
		return this.phone;
	}
	
	/**设置手机号码*/
	public void setPhone(String phone){
		this.phone=phone;
		
	}
	/**获取角色名*/
	public String getRoleName(){
		return this.roleName;
	}
	
	/**设置角色名*/
	public void setRoleName(String roleName){
		this.roleName=roleName;
		
	}
	/**获取属性*/
	public String getAttributes(){
		if(attributes==null)
			return null;
		return JSONObject.toJSONString(attributes);
	}
	
	/**设置属性*/
	@SuppressWarnings("rawtypes")
	public void setAttributes(String attributes){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		JSONObject jobj=JSONObject.parseObject(attributes);
		for(Entry<String,Object> each:jobj.entrySet()){
			this.attributes.put(each.getKey(), new Attribute().fromJson(each.getValue().toString()));
		}
	}
	/**
	 * 获取属性对象
	 * @return
	 */
	public Map<String, Attribute<?>> getAttributesObject() {
		return attributes;
	}
	/**
	 * 设置属性对象
	 * @param attributes
	 */
	public void setAttributesObject(Map<String, Attribute<?>> attributes) {
		this.attributes = attributes;
	}
	@Override
	public String toString(){
		return "com.lanxi.consumeLoan.entity.User:["+"phone="+phone+","+"roleName="+roleName+","+"attributes="+attributes+"]";
	}
	
	@SuppressWarnings("unchecked")
	public UserProxy toProxy(){
		UserProxy proxy=new UserProxy(this);
		Attribute<String> name=(Attribute<String>) get("name");
		if(name!=null)
			proxy.setName(name.getValue());
		proxy.setRoleName(getRoleName());
		proxy.setPhone(getPhone());
		return proxy;
	}
	
	/**
	 * 隐藏属性
	 */
	public void hide1(){
		this.setAttributesObject(null);
		
	}
	public void hide2(){
		
	}
	public void hide3(){
		
	}
}
