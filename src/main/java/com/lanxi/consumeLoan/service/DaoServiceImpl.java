package com.lanxi.consumeLoan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.dao.ApplyDao;
import com.lanxi.consumeLoan.dao.MerchantAccountDao;
import com.lanxi.consumeLoan.dao.MerchantAccountRecordDao;
import com.lanxi.consumeLoan.dao.MerchantDao;
import com.lanxi.consumeLoan.dao.RoleDao;
import com.lanxi.consumeLoan.dao.SystemAccountDao;
import com.lanxi.consumeLoan.dao.SystemAccountRecordDao;
import com.lanxi.consumeLoan.dao.UserDao;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.consumeLoan.entity.Merchant;
import com.lanxi.consumeLoan.entity.User;
@Service
public class DaoServiceImpl implements DaoService{
	@Resource
	private RoleDao role;
	@Resource
	private UserDao user;
	@Resource
	private ApplyDao apply;
	@Resource
	private MerchantDao merchant;
	@Resource
	private MerchantAccountDao merchantAccount;
	@Resource
	private MerchantAccountRecordDao merchantAccountRecord;
	@Resource
	private SystemAccountDao systemAccount;
	@Resource
	private SystemAccountRecordDao systemAccountRecord;

	@Override
	public RoleDao getRoleDao() {
		return role;
	}

	@Override
	public UserDao getUserDao() {
		return user;
	}

	@Override
	public ApplyDao getApplyDao() {
		return apply;
	}

	@Override
	public MerchantDao getMerchantDao() {
		return merchant;
	}

	@Override
	public MerchantAccountDao getMerchantAccountDao() {
		return merchantAccount;
	}

	@Override
	public MerchantAccountRecordDao getMerchantAccountRecordDao() {
		return merchantAccountRecord;
	}

	@Override
	public SystemAccountDao getSystemAccountDao() {
		return systemAccount;
	}

	@Override
	public SystemAccountRecordDao getSystemAccountRecordDao() {
		return systemAccountRecord;
	}

	@Override
	public List<User> selectUserByAttibute(Attribute<?> attribute) {
		return user.selectUserByAttibute("%"+attribute.toJson()+"%");
	}

	@Override
	public List<User> selectUserByAttibute(String attributeJson) {
		return user.selectUserByAttibute("%"+attributeJson+"%");
	}

	@Override
	public List<User> selectUserByAttributes(List<Attribute<?>> attributes) {
		if(attributes==null||attributes.isEmpty())
			return selectUserByAttibute("");
		StringBuffer buffer=new StringBuffer();
		for(Attribute<?> each:attributes){
			if(each==null)
				continue;
			if(each.getValue()==null)
				continue;
			if(each.getType().equals(String.class.getName())){
				if(((String)each.getValue()).isEmpty())
					continue;
			}
			buffer.append(each.toJson()+"%");
		}
		return selectUserByAttibute(buffer.subSequence(0, buffer.length()-1).toString());
	}
	public List<User> selectUserByAttributes(Map<String, Attribute<?>> attributes){
		if(attributes==null||attributes.isEmpty())
			return selectUserByAttibute("");
		StringBuffer buffer=new StringBuffer();
		for(Entry<String, Attribute<?>> each:attributes.entrySet()){
			if(each==null)
				continue;
			if(each.getValue()==null)
				continue;
			if(each.getValue().getValue()==null)
				continue;
			if(each.getValue().getType().equals(String.class.getName())){
				if(((String)each.getValue().getValue()).isEmpty())
					continue;
			}
			buffer.append(each.getValue().toJson()+"%");
		}
		return selectUserByAttibute(buffer.subSequence(0, buffer.length()-1).toString());
	}
	@Override
	public List<User> selectUserByClassLike(User user){
		String phone=user.getPhone();
		String rolename=user.getRoleName();
		Map<String, Attribute<?>>attributes=user.getAttributesObject();
		Map<String, Object> map=new HashMap<>();
		if(phone!=null&&!phone.isEmpty())
			map.put("phone", phone);
		if(rolename!=null&&!rolename.isEmpty())
			map.put("roleName",rolename);
		if(attributes!=null&&!attributes.isEmpty()){
			StringBuffer buffer=new StringBuffer();
			for(Entry<String, Attribute<?>> each:attributes.entrySet()){
				if(each==null)
					continue;
				if(each.getValue()==null)
					continue;
				if(each.getValue().getValue()==null)
					continue;
				if(each.getValue().getType().equals(String.class.getName())){
					if(((String)each.getValue().getValue()).isEmpty())
						continue;
				}
				buffer.append("%"+each.getValue().toJson());
			}
			buffer.append("%");
			map.put("attributes", buffer.toString());
			System.err.println("buffer=" + buffer);
		}
		
		System.err.println(user);
		
		return this.user.selectUserByClassLike(map);
	}
	
	public List<Merchant> selectMerchantByParm(Map<String,Object> parm){
		dealTime(parm);
		return merchant.selectMerchantByParm(dealTime(parm));
	}
	public List<Merchant> selectAdminMerchantByParm(Map<String,Object> parm){
		dealTime(parm);
		return merchant.selectAdminMerchantByParm(dealTime(parm));
	}
	public List<Merchant> selectMerchantByPage(Map<String,Object> parm){
		dealTime(parm);
		return merchant.selectMerchantByPage(dealTime(parm));
	}
	
	
	public List<Apply> selectApplyByParam(Map<String, Object> map){
		dealTime(map);
		return apply.selectApplyByParam(map);
	}
	

	public List<Apply> selectApplyByPage(Map<String, Object> map){
		dealTime(map);
		return apply.selectApplyByPage(dealTime(map));
	}
	
	private Map<String, Object> dealTime(Map<String, Object> map){
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String pageSize=(String) map.get("pageSize");
		String pageCode=(String) map.get("pageCode");
		if(startTime!=null){
			while(startTime.length()<14)
				startTime+="0";
			map.put("startTime", startTime);
		}
		
		if(endTime!=null){
			while(endTime.length()<14)
				endTime+="9";
			map.put("endTime", endTime);
		}
		
		if(pageCode!=null&&pageCode.matches("[0-9]+")) {
			int temp=Integer.parseInt(pageCode);
			if(temp<=0) {
				pageCode="1";
				map.put("pageCode", pageCode);
			}
		}
		if(pageSize!=null&&pageCode.matches("[0-9]+")) {
			int temp=Integer.parseInt(pageSize);
			if(temp<=0) {
				pageCode="10";
				map.put("pageSize", pageSize);
			}
		}
		
		return map;
	}
}
