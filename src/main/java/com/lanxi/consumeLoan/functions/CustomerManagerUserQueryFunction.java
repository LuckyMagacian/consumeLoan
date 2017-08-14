package com.lanxi.consumeLoan.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.PageBean;
import com.lanxi.consumeLoan.entity.User;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;

/**
 * 客户经理用户查询
 * 
 * @author lx
 *
 */
@Service
public class CustomerManagerUserQueryFunction extends AbstractFunction {

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
		String startTime = (String) args.get("startTime");
		String endTime = (String) args.get("endTime");
		String userPhone = (String) args.get("userPhone");
		String merchantName = (String) args.get("merchantName");
		PageBean page =new PageBean();
		page.setPageCode(Integer.parseInt((String)args.get("pageCode")));
		page.setPageSize(Integer.parseInt((String)args.get("pageSize")));
		Map<String, Object> resultMap = new HashMap<>();
				
		List<Map<String, Object>> userProxy = new ArrayList<>();
		if (userPhone != null && !userPhone.equals("")) {
			User user = dao.getUserDao().selectUserByUniqueIndexOnPhone(userPhone);
			if (user.getRoleName().equals("salesMan")
					|| user.getRoleName().equals("shopKeeper")) {
				userProxy.add(user.toProxy().toSalesMan());
			}else if (user.getRoleName().equals("customerManager")) {
				userProxy.add(user.toProxy().toCustomerManager());
			} else {
				userProxy.add(user.toProxy().toAdmin());
			}
			page.setTotalRecord(1);
			resultMap.put("page", page);
			resultMap.put("userProxy", userProxy);
			LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args
					+ "]查询结果转换[" + userProxy + "]!");
			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "查询成功",resultMap);
		} else {

			Map<String, Object> parm = new HashMap<String, Object>();
			List<User> list = new ArrayList<User>();
			parm.put("customerManagerPhone", phone);
			List<Merchant> merchants = dao.getMerchantDao()
					.selectMerchantByParm(parm);
			if (merchants == null) {
				LogFactory.info(this, "管理员[" + phone + "],根据查询的商户条件[" + phone
						+ "]未查询到数据!");
				return new RetMessage(RetCodeEnum.FAIL.toString(), "商户不存在!",
						null);
			}
			LogFactory.info(this, "管理员[" + phone + "],根据查询的商户条件[" + phone
					+ "],查询到的数据为[" + merchants + "]!");
			for (Merchant merchant : merchants) {
				String attribute = new Attribute<String>("merchantId",
						merchant.getMerchantId()).toJson();
				List<User> users = dao.selectUserByAttibute(attribute);
				list.addAll(users);
			}
			LogFactory.info(this, "管理员[" + phone + "]下所有的用户为[" + list + "]!");

//			if (list == null || list.isEmpty()) {
//				LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args
//						+ "]未查询到数据!");
//				return new RetMessage(RetCodeEnum.FAIL.toString(), "未查询到数据",
//						userProxy);
//			}

			List<User> users  =new ArrayList<>();
			if ((startTime != null && !startTime.isEmpty())
					|| (endTime != null && !endTime.isEmpty())
					||(merchantName != null && !merchantName.isEmpty())) {
				for (User each : list) {
					if (startTime != null && !startTime.isEmpty()) {
						if(startTime.matches("[0-9]{8}"))
							startTime+="000000";
						if (startTime.compareTo((String) each.get("createTime").getValue()) > 0)
							continue;
					}
					if (endTime != null && !endTime.isEmpty()) {
						if(endTime.matches("[0-9]{8}"))
							endTime+="595959";
						if (endTime.compareTo((String) each.get("createTime").getValue()) < 0 || endTime.compareTo((String) each.get("createTime").getValue()) == 0)
							continue;
					}
					if (merchantName != null && !merchantName.isEmpty())
					if (merchantName != (String) each.get("merchantName")
							.getValue())
						continue;
			
					users.add(each);
				}
			}else {
				users =list;
			}
			for (User user : users) {
				if (user.getRoleName().equals("salesMan")
						|| user.getRoleName().equals("shopKeeper")) {
					userProxy.add(user.toProxy().toSalesMan());
				}else if (user.getRoleName().equals("customerManager")) {
					userProxy.add(user.toProxy().toCustomerManager());
				} else {
					userProxy.add(user.toProxy().toAdmin());
				}
			}
			page.setTotalRecord(userProxy.size());
			userProxy = userProxy.subList(page.getStart(), page.getEnd());
			
			resultMap.put("page", page);
			resultMap.put("userProxy", userProxy);
			LogFactory.info(this, "管理员[" + phone + "]尝试根据条件[" + args
					+ "]查询结果转换[" + userProxy + "]!");
			return new RetMessage(RetCodeEnum.SUCCESS.toString(), "查询成功",
					resultMap);
		}
	}
}
