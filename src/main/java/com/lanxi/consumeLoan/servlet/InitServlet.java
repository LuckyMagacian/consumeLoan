package com.lanxi.consumeLoan.servlet;

import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.utils.LoggerUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException {
		LoggerUtil.setLogLevel(LoggerUtil.LogLevel.DEBUG);
		LoggerUtil.init();
		ConfigManager.loadConfigs();
	}
}
