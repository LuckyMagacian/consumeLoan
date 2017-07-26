package com.lanxi.consumeLoan.dao;

import com.lanxi.consumeLoan.entity.SystemAccount;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-24 10:25:36
 */

public interface SystemAccountDao{
	
	/**插入SystemAccount到数据库
	 * @param systemAccount 待插入的对象
	 */
	public void addSystemAccount(SystemAccount systemAccount);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param systemAccount 删除的条件|被删除的数据本身
	 */
	public void deleteSystemAccountByClass(SystemAccount systemAccount);
	/**根据唯一索引从数据库中删除数据
	 * @param AccountId 索引:账户编号
	 */
	public void deleteSystemAccountByUniqueIndexOnAccountId(@Param(value="accountId")String accountId);
	
	/**更新数据库中符合条件的数据
	 * @param systemAccount 更新后的数据
	 * @param param 更新的条件 */
	public void updateSystemAccountByClass(@Param(value="systemAccount")SystemAccount systemAccount,@Param(value="param")SystemAccount param);
	/**根据唯一索引更新数据库中的数据
	 * @param systemAccount 更新后的数据
	 * @param AccountId 索引:账户编号
	 */
	public void updateSystemAccountByUniqueIndexOnAccountId(@Param(value="systemAccount")SystemAccount systemAccount,@Param(value="accountId")String accountId);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param param 更新的条件|数据本身 * @return 符合条件的数据列表 */
	public List<SystemAccount> selectSystemAccountByClass(SystemAccount systemAccount);
	/**根据唯一索引选中数据库中的数据
	 * @param accountId 索引:账户编号
	 * @return 符合条件的数据对象 */
	public SystemAccount selectSystemAccountByUniqueIndexOnAccountId(@Param(value="accountId")String accountId);
	
}
