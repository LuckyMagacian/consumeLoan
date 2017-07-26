package com.lanxi.consumeLoan.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.dao.ApplyDao;
import com.lanxi.consumeLoan.dao.MerchantAccountDao;
import com.lanxi.consumeLoan.dao.MerchantAccountRecordDao;
import com.lanxi.consumeLoan.dao.MerchantDao;
import com.lanxi.consumeLoan.dao.RoleDao;
import com.lanxi.consumeLoan.dao.SystemAccountDao;
import com.lanxi.consumeLoan.dao.SystemAccountRecordDao;
import com.lanxi.consumeLoan.dao.UserDao;
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

}
