package com.lanxi.consumeLoan.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.utils.LogUtil;
import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.util.entity.ConfigManager;
import com.lanxi.util.utils.LoggerUtil;
import com.lanxi.util.utils.LoggerUtil.LogLevel;


public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException {
		LoggerUtil.init();
		ConfigManager.loadConfigs();
	}
}
