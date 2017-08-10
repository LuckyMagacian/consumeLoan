package com.lanxi.consumeLoan.interceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class UrlPathInterceptor extends HandlerInterceptorAdapter {
	@SuppressWarnings("rawtypes")
	@Resource
	private AbstractHandlerMethodMapping urlMap;
	private Map<RequestMappingInfo,HandlerMethod> map;
	Set<String> patterns=new HashSet<String>();
	private boolean inited=false;
	
	@SuppressWarnings("unchecked")
	private void init() {
		map=urlMap.getHandlerMethods();
		inited=true;
		for(Entry<RequestMappingInfo, HandlerMethod>each:map.entrySet()) {
			patterns.add(each.getKey().getPatternsCondition().toString()); 
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(!inited)
			init(); 
		String path=request.getRequestURI();
		path=path.substring(12); 
		path="["+path+"]";
		if(patterns.contains(path))
			return super.preHandle(request, response, handler);
		else
			response.sendRedirect("/test/login");
		return false;
	}

	@SuppressWarnings("rawtypes")
	public AbstractHandlerMethodMapping getUrlMap() {
		return urlMap;
	}

	@SuppressWarnings("rawtypes")
	public void setUrlMap(AbstractHandlerMethodMapping urlMap) {
		this.urlMap = urlMap;
	}
	
	
}
