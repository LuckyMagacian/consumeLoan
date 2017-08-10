package com.lanxi.consumeLoan.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.consts.ConstParam;
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
		String merchantName=(String )args.get("merchantName");
		String special=(String) args.get("special");
		String state=(String)args.get("state");
		user.setPhone(userPhone);
		user.setRoleName(roleName);
		if(merchantName!=null&&!merchantName.isEmpty())
			user.addAttribute(new Attribute<String>("merchantName", merchantName));
		if(state!=null&&!state.isEmpty())
			user.addAttribute(new Attribute<String>("state",state));
		String startTime=(String) args.get("startTime");
		String endTime=(String) args.get("endTime");
		List<User> users = null;
		if(special==null||special.isEmpty()) {
			users = dao.selectUserByClassLike(user);
		}else {
			LogFactory.info(this, "特殊标记不为空!查询所有商户人员!");
			user.setRoleName(ConstParam.USER_ROLE_NAME_SALESMAN);
			users = dao.selectUserByClassLike(user);
			user.setRoleName(ConstParam.USER_ROLE_NAME_SHOP_KEEPER);
			users.addAll(dao.selectUserByClassLike(user));
		}
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
			if((merchantName==null||merchantName.isEmpty())&&(roleName==null||roleName.isEmpty())&&(special==null||special.isEmpty())){
				LogFactory.info(this, "管理员["+phone+"]查询结果转用户通用属性");
				for(User each:users){
					userProxy.add(each.toProxy().toUser());
				}
				LogFactory.info(this, "管理员["+phone+"]转换结果["+userProxy+"]");
			}else if((special!=null&&!special.isEmpty())||(merchantName!=null&&!merchantName.isEmpty())||ConstParam.USER_ROLE_NAME_SHOP_KEEPER.equals(roleName)||ConstParam.USER_ROLE_NAME_SALESMAN.equals(roleName)){
				LogFactory.info(this, "管理员["+phone+"]查询结果转商户通用属性");
				for(User each:users){
					userProxy.add(each.toProxy().toSalesMan());
				}
				LogFactory.info(this, "管理员["+phone+"]转换结果["+userProxy+"]");
			}else if(ConstParam.USER_ROLE_NAME_CUSTOMER_MANAGER.equals(roleName)){
				LogFactory.info(this, "管理员["+phone+"]查询结果转客户经理属性");
				for(User each:users){
					userProxy.add(each.toProxy().toCustomerManager());
				} 
				LogFactory.info(this, "管理员["+phone+"]转换结果["+userProxy+"]");
			}else{
				LogFactory.info(this, "管理员["+phone+"]查询结果转管理员属性");
				for(User each:users){
					userProxy.add(each.toProxy().toAdmin());
				}
				LogFactory.info(this, "管理员["+phone+"]转换结果["+userProxy+"]");
			}
			userProxy = userProxy.subList(page.getStart(), page.getEnd());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("page", page);
			resultMap.put("users", userProxy);
			LogFactory.info(this, "管理员["+phone+"]尝试根据条件["+args+"]查询结果转换["+userProxy+"]!");
			return new RetMessage(RetCodeEnum.SUCCESS.toString(),"查询成功",resultMap);
		}
		return new RetMessage(RetCodeEnum.SUCCESS.toString(),"查询成功",users);
	}

}
