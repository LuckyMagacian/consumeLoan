package com.lanxi.consumeLoan.consts;

public interface ConstParam {
	/** 应用名称 */
	public static final String APP_NAME = "com.lanxi.consumeloan";
	/** 应用版本 */
	public static final String VERSION = "1.0";
	/** 项目名称 */
	public static final String PROJECT_NAME = APP_NAME + "-" + VERSION;
	/** 功能名-登录 */
	public static final String FUNCTION_NAME_LOGIN = PROJECT_NAME + "-LOGIN-";
	/** 功能名-增加申请 */
	public static final String FUNCTION_NAME_APPLY_ADD = PROJECT_NAME + "-APPLY-ADD-";
	/** 用户状态-已登录 */
	public static final String USER_STATE_LOGIN = PROJECT_NAME + "-has-login-";

	/** 申请状态-待审核 */
	public static final String APPLY_STATE_WAIT_CHECK = "01";
	/** 申请状态-拒绝 */
	public static final String APPLY_STATE_REJECT = "11";
	/** 申请状态-放款 */
	public static final String APPLY_STATE_LOAN = "10";
	/** 申请状态-逾期 */
	public static final String APPLY_STATE_OVERDUE = "21";
	/** 申请状态-完成 */
	public static final String APPLY_STATE_FINISH = "20";

	/** 商户状态-等待审核 */
	public static final String MERCHANT_STATE_WAIT_CHECK = "01";
	/** 商户状态-审核功过等待上架 */
	public static final String MERCHANT_STATE_WAIT_SHELVE = "10";
	/** 商户状态-审核拒绝 */
	public static final String MERCHANT_STATE_REJECT = "11";
	/** 商户状态-已上架 */
	public static final String MERCHANT_STATE_SHELVED = "20";
	/** 商户状态-已下架 */
	public static final String MERCHANT_STATE_UNSHELVED = "21";
	/** 商户状态-黑名单 */
	public static final String MERCHANT_STATE_BLACK = "31";
	/** 商户状态-警告 */
	public static final String MERCHANT_STATE_WARNING = "32";
	/** 商户状态-异常 */
	public static final String MERCHANT_STATE_EXCEPTION = "33";

	/** 用户状态-待审核 */
	public static final String USER_STATE_WAIT_CHECK = "01";
	/** 用户状态-正常 */
	public static final String USER_STATE_NORMAL = "10";
	/** 用户状态-拒绝 */
	public static final String USER_STATE_REJECT = "11";
	/** 用户状态-冻结 */
	public static final String USER_STATE_FREEZE = "21";

	/** 商户类型 */
	public static final String MERCHANT_TYPE = "";

}
