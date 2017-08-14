package com.lanxi.consumeLoan.basic;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.util.consts.RetCodeEnum;

public class RetMessage {
	private String code;
	private String message;
	private Object result;
	public RetMessage() {}
	public RetMessage(RetCodeEnum code,String message,Object result) {
		this.code=code.toString();
		this.message=message;
		this.result=result;
	}
	public RetMessage(String code,String message,Object result) {
		this.code=code;
		this.message=message;
		this.result=result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "RetMessage [code=" + code + ", message=" + message + ", result=" + result + "]";
	}
	
	public String toJson(){
		return JSONObject.toJSONString(this);
	}
}
