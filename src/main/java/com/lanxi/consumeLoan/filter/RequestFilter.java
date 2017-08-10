package com.lanxi.consumeLoan.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.lanxi.util.entity.LogFactory;
@Component(value="requestFilter")
public class RequestFilter extends OncePerRequestFilter {
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
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(!inited)
			init(); 
		LogFactory.info(this, "过滤器工作---------------");
		String path=request.getRequestURI();
		path=path.substring(12); 
		path="["+path+"]";
		if(patterns.contains(path))
			filterChain.doFilter(request, response);
		else { 
			LogFactory.info(this, "非法路径,转登录!");
			response.sendRedirect("/consumeLoan/test/getPicCode"); 
		}
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
