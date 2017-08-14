package com.lanxi.consumeLoan.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.LoggerUtil.LogLevel;


public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException {
//		LoggerUtil.setLogLevel(LogLevel.DEBUG);
		LoggerUtil.init();
		ConfigManager.loadConfigs();
	}
}
