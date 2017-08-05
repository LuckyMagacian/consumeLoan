package com.lanxi.consumeLoan.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.PageBean;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

/**
 * 客户经理用户列表查询
 *
 * @author lx
 *
 */
@Service
public class AdminUserQueryFunction extends AbstractFunction{

	@Override
	public RetMessage successNotice() {
		
		return null;
	}

	@Override
	public RetMessage failNotice() {
		
		return null;
	}

	@Override
	public RetMessage showInfo() {
		
		return null;
	}

	@Override
	public RetMessage excuted(Map<String, Object> args) {
		String phone=(String) args.get("phone");
		LogFactory.info(this, "管理员["+phone+"]尝试根据条件["+args+"]查询用户!");
		String userPhone=(String) args.get("userPhone");
		if(userPhone==null||userPhone.isEmpty())
			userPhone=null;
		String roleName=(String) args.get("roleName");
		if(roleName==null||roleName.isEmpty())
			roleName=null;
		User user=new User();
		user.setPhone(userPhone);
		user.setRoleName(roleName);
		String startTime=(String) args.get("startTime");
		String endTime=(String) args.get("endTime");
		List<User> users=dao.getUserDao().selectUserByClass(user);
		
		PageBean page = new PageBean();
    	int pageSize = Integer.parseInt((String) args.get("pageSize"));
		int pageCode = Integer.parseInt((String) args.get("pageCode"));
		page.setPageSize(pageSize);
		page.setPageCode(pageCode); 
		page.setTotalRecord(users.size());

		
		if((startTime!=null&&!startTime.isEmpty())||(endTime!=null&&!endTime.isEmpty()))
			for(User each:users){
				if(startTime!=null&&!startTime.isEmpty())
					if(startTime.compareTo((String)each.get("createTime").getValue())>0)
						users.remove(each);
				if(endTime!=null&&!endTime.isEmpty())
					if(endTime.compareTo((String)each.get("createTime").getValue())<0)
						users.remove(each);
			}
		LogFactory.info(this, "管理员["+phone+"]尝试根据条件["+args+"]查询结果["+users+"]!");
		List<Map<String, Object>> userProxy=new ArrayList<>();
		if(!users.isEmpty()){
			if(roleName==null||roleName.isEmpty()){
				for(User each:users){
					userProxy.add(each.toProxy().toUser());
				}
			}else if(roleName.equals("salesMan")||roleName.equals("shopKeeper")){
				for(User each:users){
					userProxy.add(each.toProxy().toSalesMan());
				}
			}else if(roleName.equals("customerManager")){
				for(User each:users){
					userProxy.add(each.toProxy().toCustomerManager());
				}
			}else{
				for(User each:users){
					userProxy.add(each.toProxy().toAdmin());
				}
			}
			userProxy = userProxy.subList(page.getStart(), page.getEnd());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("page", page);
			resultMap.put("userProxy", userProxy);
			LogFactory.info(this, "管理员["+phone+"]尝试根据条件["+args+"]查询结果转换["+userProxy+"]!");
			return new RetMessage(RetCodeEnum.SUCCESS.toString(),"查询成功",resultMap);
		}
		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"查询成功",users);
	}

}
