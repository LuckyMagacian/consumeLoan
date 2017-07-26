package com.lanxi.consumeLoan.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

public class UserPrototype {
	private String phone;
	private Map<String, Attribute<?>> attributes;
	private String roleName;
	public UserPrototype() {
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(String attrName,Object obj){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		Attribute attr=attributes.get(attrName);
		attr.setValue(obj);
	}
	
	public Attribute<?> get(String attributeName){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		return this.attributes.get(attributeName);
	}
	
	@SuppressWarnings("rawtypes")
	public void addAttribute(String jsonStr){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		addAttribute(new Attribute().fromJson(jsonStr));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addAttributes(String jsonStr){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		Map<String,JSONObject> map=JSONObject.parseObject(jsonStr,HashMap.class);
		for(Entry<String, JSONObject> each:map.entrySet())
			addAttribute(new Attribute().fromJson(each.getValue().toJSONString()));
	}
	
	public void addAttribute(Attribute<?> attribute){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		attributes.put(attribute.getName(), attribute);
	}
	
	public void addAttribute(Map<String, Attribute<?>> attrs){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		attributes.putAll(attrs);
	}

	public void addAttribute(List<Attribute<?>> attrs){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		for(Attribute<?> each:attrs)
			attributes.put(each.getName(), each);
	}
	
	public String getAttributes(){
		if(attributes==null)
			return null;
		return JSONObject.toJSONString(attributes);
	}

	@SuppressWarnings("rawtypes")
	public void setAttributes(String attributes){
		if(this.attributes==null)
			this.attributes=new HashMap<String, Attribute<?>>();
		JSONObject jobj=JSONObject.parseObject(attributes);
		for(Entry<String,Object> each:jobj.entrySet()){
			this.attributes.put(each.getKey(), new Attribute().fromJson(each.getValue().toString()));
		}
	}
	
	public void setAttributes(Map<String, Attribute<?>> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Attribute<?>> getAttributesObject() {
		return attributes;
	}
	
	public void setAttributesObject(Map<String, Attribute<?>> attributes) {
		this.attributes = attributes;
	}

	public String getRole() {
		return roleName;
	}

	public void setRole(String role){
		this.roleName=role;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserPrototype [phone=" + phone + ", attributes=" + attributes + ", roleName=" + roleName + "]";
	}
}
