package com.lanxi.consumeLoan.consts;

public interface ConstParam {
	
	/**测试标记*/
	public static final boolean TEST_FLAG=true;
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
	/** 用户状态-随商户冻结*/
	public static final String USER_STATE_SPECIAL_FREEZE = "22";

	/** 商户类型-家居 */
	public static final String MERCHANT_TYPE_HOUSEHOLD = "1001";
	/** 商户类型-电器*/
	public static final String MERCHANT_TYPE_ELECTRIC_APPLIANCE  = "1002";
	/** 商户类型-数码*/
	public static final String MERCHANT_TYPE_NUMERAL = "1003";
	/** 商户类型-娱乐*/
	public static final String MERCHANT_TYPE_ENTERAINMENT = "1004";
	/** 商户类型-珠宝*/
	public static final String MERCHANT_TYPE_JEWELS= "1005";
	/** 商户类型-医疗*/
	public static final String MERCHANT_TYPE_MEDICAL_TREATMENT= "1006";
	/** 商户类型-美容*/
	public static final String MERCHANT_TYPE_BEAUTY = "1007";
	/** 商户类型-汽车*/
	public static final String MERCHANT_TYPE_MOTOR_CAR = "1008";
	/** 商户类型-教育*/
	public static final String MERCHANT_TYPE_EDUCATION = "1009";
	/** 商户类型-其他*/
	public static final String MERCHANT_TYPE_OTHERS = "1010";

	/**用户角色名-销售员*/
	public static final String USER_ROLE_NAME_SALESMAN="salesMan";
	/**用户角色名-商户负责人*/
	public static final String USER_ROLE_NAME_SHOP_KEEPER="shopKeeper";
	/**用户角色名-客户经理*/
	public static final String USER_ROLE_NAME_CUSTOMER_MANAGER="customerManager";
	/**用户角色名-管理员*/
	public static final String USER_ROLE_NAME_ADMIN="admin";
	/**用户角色名-root(仅限测试用)*/
	public static final String USER_ROLE_NAME_ROOT="root";
	
	
	
}
