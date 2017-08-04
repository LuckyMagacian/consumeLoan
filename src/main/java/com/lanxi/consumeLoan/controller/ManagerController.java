package com.lanxi.consumeLoan.controller;



import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.lanxi.consumeLoan.manager.ApplicationContextProxy;


@Controller
@RequestMapping("manager")
public class ManagerController {
	@Resource
	private ApplicationContextProxy ac;
	

	
}
