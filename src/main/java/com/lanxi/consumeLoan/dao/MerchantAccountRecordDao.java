package com.lanxi.consumeLoan.dao;

import com.lanxi.consumeLoan.entity.MerchantAccountRecord;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-28 13:47:19
 */

public interface MerchantAccountRecordDao{
	
	/**插入MerchantAccountRecord到数据库
	 * @param merchantAccountRecord 待插入的对象
	 */
	public void addMerchantAccountRecord(MerchantAccountRecord merchantAccountRecord);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param merchantAccountRecord 删除的条件|被删除的数据本身
	 */
	public void deleteMerchantAccountRecordByClass(@Param(value="merchantAccountRecord")MerchantAccountRecord merchantAccountRecord);
	/**根据唯一索引从数据库中删除数据
	 * @param nanoTime 索引:记录时间戳-纳秒
	 */
	public void deleteMerchantAccountRecordByUniqueIndexOnNanoTime(@Param(value="nanoTime")String nanoTime);
	
	/**更新数据库中符合条件的数据
	 * @param merchantAccountRecord 更新后的数据
	 * @param param 更新的条件 */
	public void updateMerchantAccountRecordByClass(@Param(value="merchantAccountRecord")MerchantAccountRecord merchantAccountRecord,@Param(value="param")MerchantAccountRecord param);
	/**根据唯一索引更新数据库中的数据
	 * @param merchantAccountRecord 更新后的数据
	 * @param nanoTime 索引:记录时间戳-纳秒
	 */
	public void updateMerchantAccountRecordByUniqueIndexOnNanoTime(@Param(value="merchantAccountRecord")MerchantAccountRecord merchantAccountRecord,@Param(value="nanoTime")String nanoTime);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param MerchantAccountRecord 选中的条件|数据本身 * @return 符合条件的数据列表 */
	public List<MerchantAccountRecord> selectMerchantAccountRecordByClass(@Param(value="merchantAccountRecord")MerchantAccountRecord merchantAccountRecord);
	/**根据唯一索引选中数据库中的数据
	 * @param nanoTime 索引:记录时间戳-纳秒
	 * @return 符合条件的数据对象 */
	public MerchantAccountRecord selectMerchantAccountRecordByUniqueIndexOnNanoTime(@Param(value="nanoTime")String nanoTime);
	
}
