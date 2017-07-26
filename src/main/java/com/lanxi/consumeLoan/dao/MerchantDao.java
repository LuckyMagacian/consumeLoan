package com.lanxi.consumeLoan.dao;

import com.lanxi.consumeLoan.entity.Merchant;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-25 15:09:08
 */

public interface MerchantDao{
	
	/**插入Merchant到数据库
	 * @param merchant 待插入的对象
	 */
	public void addMerchant(Merchant merchant);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param merchant 删除的条件|被删除的数据本身
	 */
	public void deleteMerchantByClass(Merchant merchant);
	/**根据唯一索引从数据库中删除数据
	 * @param MerchantId 索引:商户编号
	 */
	public void deleteMerchantByUniqueIndexOnMerchantId(@Param(value="merchantId")String merchantId);
	/**根据唯一索引从数据库中删除数据
	 * @param MerchantName 索引:商户名称
	 * @param MerchantAddress 索引:商户地址
	 * @param MerchantType 索引:商户类型
	 */
	public void deleteMerchantByUniqueIndexOnMerchantNameAndMerchantAddressAndMerchantType(@Param(value="merchantName")String merchantName,@Param(value="merchantAddress")String merchantAddress,@Param(value="merchantType")String merchantType);
	
	/**更新数据库中符合条件的数据
	 * @param merchant 更新后的数据
	 * @param param 更新的条件 */
	public void updateMerchantByClass(@Param(value="merchant")Merchant merchant,@Param(value="param")Merchant param);
	/**根据唯一索引更新数据库中的数据
	 * @param merchant 更新后的数据
	 * @param MerchantId 索引:商户编号
	 */
	public void updateMerchantByUniqueIndexOnMerchantId(@Param(value="merchant")Merchant merchant,@Param(value="merchantId")String merchantId);
	/**根据唯一索引更新数据库中的数据
	 * @param merchant 更新后的数据
	 * @param MerchantName 索引:商户名称
	 * @param MerchantAddress 索引:商户地址
	 * @param MerchantType 索引:商户类型
	 */
	public void updateMerchantByUniqueIndexOnMerchantNameAndMerchantAddressAndMerchantType(@Param(value="merchant")Merchant merchant,@Param(value="merchantName")String merchantName,@Param(value="merchantAddress")String merchantAddress,@Param(value="merchantType")String merchantType);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param param 更新的条件|数据本身 * @return 符合条件的数据列表 */
	public List<Merchant> selectMerchantByClass(Merchant merchant);
	/**根据唯一索引选中数据库中的数据
	 * @param merchantId 索引:商户编号
	 * @return 符合条件的数据对象 */
	public Merchant selectMerchantByUniqueIndexOnMerchantId(@Param(value="merchantId")String merchantId);
	/**根据唯一索引选中数据库中的数据
	 * @param merchantName 索引:商户名称
	 * @param merchantAddress 索引:商户地址
	 * @param merchantType 索引:商户类型
	 * @return 符合条件的数据对象 */
	public Merchant selectMerchantByUniqueIndexOnMerchantNameAndMerchantAddressAndMerchantType(@Param(value="merchantName")String merchantName,@Param(value="merchantAddress")String merchantAddress,@Param(value="merchantType")String merchantType);
	
}
