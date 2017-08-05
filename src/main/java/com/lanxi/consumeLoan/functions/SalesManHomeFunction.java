package com.lanxi.consumeLoan.functions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum; 
import com.lanxi.util.entity.LogFactory;
@Service
public class SalesManHomeFunction extends AbstractFunction {
//	public SalesManHomeFunction() {
//		addAttribute(new Attribute<String>("salesManId", ""));
//	}
	@Override
	public RetMessage successNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage failNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage showInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetMessage excuted(Map<String, Object> args) {
		String phone=(String) args.get("phone");
    	LogFactory.info(this, "用户["+phone+"]尝试获取销售员主页信息");
    	User user=dao.getUserDao().selectUserByUniqueIndexOnPhone(phone);
//    	if(user==null){
//    		LogFactory.info(this, "用户["+phone+"]不存在!");
//    		return failNotice();
//    	}
    	Attribute<String> merchantId=(Attribute<String>) user.get("merchantId");
    	if(merchantId==null){
    		LogFactory.info(this, "用户["+phone+"]未绑定商户或不是商户工作人员!");
    		return new RetMessage(RetCodeEnum.FAIL.toString(),"当前用户未绑定商户或不是商户工作人员！",null);
    	}
//    	if(shopkeeperId==null){
//    		LogFactory.info(this, "用户["+phone+"]不是店长!");
//    		return failNotice();
//    	}
    	Merchant merchant=dao.getMerchantDao().selectMerchantByUniqueIndexOnMerchantId(merchantId.getValue());
    	LogFactory.info(this, "用户["+phone+"]获取商户信息["+merchant+"]成功!");
    	Map<String, Object> result=new HashMap<>();
    	result.put("merchantInfo", merchant);
    	result.put("employeeInfo", user.toProxy());
        return new RetMessage(RetCodeEnum.SUCCESS.toString(),"获取销售员主页信息成功！", result);
	}

}
