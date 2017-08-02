package com.lanxi.consumeLoan.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanxi.consumeLoan.basic.Function;
import com.lanxi.consumeLoan.basic.RolePrototype;
import com.lanxi.consumeLoan.entity.SystemAccount;

public abstract class StaticParam implements ConstParam{
	public static List<RolePrototype> roles=new ArrayList<RolePrototype>();
	public static Map<String, Function> functions=new HashMap<String,Function>();
	public static SystemAccount systemAccount=null;
}
