package com.lanxi.consumeLoan.functions;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Apply;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyuanjian on 2017/7/13.
 */
@Service
public class ApplyOrderQueryFunction extends AbstractFunction {
	public ApplyOrderQueryFunction() {
		
	}
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
    	String phone = (String) args.get("phone");
    	if(!checkService.checkAuthority(phone, this.getClass().getName()))
    		return failNotice();
    	String userPhone=(String) args.get("userPhone");
    	String state = (String) args.get("state");
    	String startTime= (String) args.get("startTime");
    	String endTime=(String) args.get("endTime");
    	Map<String, Object> map=new HashMap<>();
    	map.put("userPhone",userPhone);
    	map.put("state", state);
    	map.put("startTime", startTime);
    	map.put("endTime", endTime);
    	List<Apply> applys=dao.getApplyDao().selectApplyByParam(map);
    	int count=applys.size();
    	BigDecimal moneyAmount=new BigDecimal(0);
    	for(Apply each:applys){
    		moneyAmount.add(each.getApplyMoney());
    	}
    	String result= JSONObject.toJSONString(applys);
        return successNotice();
    }
}
