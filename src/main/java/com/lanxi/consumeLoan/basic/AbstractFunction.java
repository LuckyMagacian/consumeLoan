package com.lanxi.consumeLoan.basic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lanxi.common.interfaces.RedisCacheServiceInterface;
import com.lanxi.common.interfaces.SmsSendServiceInterface;
import com.lanxi.consumeLoan.manager.ApplicationContextProxy;
import com.lanxi.consumeLoan.manager.UserManager;
import com.lanxi.consumeLoan.service.CheckService;
import com.lanxi.consumeLoan.service.DaoService;
/**
 * Created by yangyuanjian on 2017/7/11.
 */
public abstract class AbstractFunction implements Function {
	/**dao服务*/
	@Resource
	protected DaoService dao;
	/**spring 上下文的代理*/
	@Resource
	protected ApplicationContextProxy applicationContextProxy;
	/**redis缓存服务*/
	@Resource
	protected RedisCacheServiceInterface redisService;
	/**短信发送服务*/
	@Resource
	protected SmsSendServiceInterface smsService;
	/**检查校验服务*/
	@Resource
	protected CheckService checkService;
	/**用户管理*/
	@Resource
	protected UserManager userManager;
	/**接口名称*/
    private String name;    
    /**接口属性要求*/
    private Map<String,Attribute<?>> attributes;
    public AbstractFunction(){
        attributes=new HashMap<String,Attribute<?>>();
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public void setName(String name){
        this.name=name;
    }

    @Override
    public boolean hasAuthority(UserPrototype user) {
        if(user.getAttributesObject().containsKey(this.getName()))
            return true;
        return false;
    }

    @Override
    public Map<String,Attribute<?>> getAttributes(){
        return attributes;
    }

    @Override
    public void  addAttribute(Attribute<?> attribute){
        attributes.put(attribute.getName(),attribute);
    }
    
}
