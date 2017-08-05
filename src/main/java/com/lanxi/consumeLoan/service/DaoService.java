package com.lanxi.consumeLoan.service;
import java.util.List;
import java.util.Map;

import com.lanxi.consumeLoan.basic.Attribute;
import com.lanxi.consumeLoan.dao.ApplyDao;
import com.lanxi.consumeLoan.dao.MerchantAccountDao;
import com.lanxi.consumeLoan.dao.MerchantAccountRecordDao;
import com.lanxi.consumeLoan.dao.MerchantDao;
import com.lanxi.consumeLoan.dao.RoleDao;
import com.lanxi.consumeLoan.dao.SystemAccountDao;
import com.lanxi.consumeLoan.dao.SystemAccountRecordDao;
import com.lanxi.consumeLoan.dao.UserDao;
import com.lanxi.consumeLoan.entity.User;

public interface DaoService {
	public RoleDao getRoleDao();
	public UserDao getUserDao();
	public ApplyDao getApplyDao();
	public MerchantDao getMerchantDao();
	public MerchantAccountDao getMerchantAccountDao();
	public MerchantAccountRecordDao getMerchantAccountRecordDao();
	public SystemAccountDao getSystemAccountDao();
	public SystemAccountRecordDao getSystemAccountRecordDao();
	public List<User> selectUserByAttibute(String attributeJson);
	public List<User> selectUserByAttibute(Attribute<?> attribute);
	public List<User> selectUserByAttributes(List<Attribute<?>> attributes);
	public List<User> selectUserByAttributes(Map<String, Attribute<?>> attributes);
	public List<User> selectUserByClassLike(User user);
}
