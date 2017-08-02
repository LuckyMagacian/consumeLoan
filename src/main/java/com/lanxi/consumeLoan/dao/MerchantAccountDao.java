package com.lanxi.consumeLoan.dao;

import com.lanxi.consumeLoan.entity.MerchantAccount;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-27 11:30:11
 */

public interface MerchantAccountDao{
	
	/**插入MerchantAccount到数据库
	 * @param merchantAccount 待插入的对象
	 */
	public void addMerchantAccount(MerchantAccount merchantAccount);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param merchantAccount 删除的条件|被删除的数据本身
	 */
	public void deleteMerchantAccountByClass(MerchantAccount merchantAccount);
	/**根据唯一索引从数据库中删除数据
	 * @param merchantId 索引:商户编号
	 */
	public void deleteMerchantAccountByUniqueIndexOnMerchantId(@Param(value="merchantId")String merchantId);
	
	/**更新数据库中符合条件的数据
	 * @param merchantAccount 更新后的数据
	 * @param param 更新的条件 */
	public void updateMerchantAccountByClass(@Param(value="merchantAccount")MerchantAccount merchantAccount,@Param(value="param")MerchantAccount param);
	/**根据唯一索引更新数据库中的数据
	 * @param merchantAccount 更新后的数据
	 * @param merchantId 索引:商户编号
	 */
	public void updateMerchantAccountByUniqueIndexOnMerchantId(@Param(value="merchantAccount")MerchantAccount merchantAccount,@Param(value="merchantId")String merchantId);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param MerchantAccount 选中的条件|数据本身 * @return 符合条件的数据列表 */
	public List<MerchantAccount> selectMerchantAccountByClass(MerchantAccount merchantAccount);
	/**根据唯一索引选中数据库中的数据
	 * @param merchantId 索引:商户编号
	 * @return 符合条件的数据对象 */
	public MerchantAccount selectMerchantAccountByUniqueIndexOnMerchantId(@Param(value="merchantId")String merchantId);
	
}
