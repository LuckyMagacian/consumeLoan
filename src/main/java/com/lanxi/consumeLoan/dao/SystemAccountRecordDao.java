package com.lanxi.consumeLoan.dao;

import com.lanxi.consumeLoan.entity.SystemAccountRecord;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-24 10:25:36
 */

public interface SystemAccountRecordDao{
	
	/**插入SystemAccountRecord到数据库
	 * @param systemAccountRecord 待插入的对象
	 */
	public void addSystemAccountRecord(SystemAccountRecord systemAccountRecord);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param systemAccountRecord 删除的条件|被删除的数据本身
	 */
	public void deleteSystemAccountRecordByClass(SystemAccountRecord systemAccountRecord);
	/**根据唯一索引从数据库中删除数据
	 * @param NanoTime 索引:记录时间戳-纳秒
	 */
	public void deleteSystemAccountRecordByUniqueIndexOnNanoTime(@Param(value="nanoTime")String nanoTime);
	
	/**更新数据库中符合条件的数据
	 * @param systemAccountRecord 更新后的数据
	 * @param param 更新的条件 */
	public void updateSystemAccountRecordByClass(@Param(value="systemAccountRecord")SystemAccountRecord systemAccountRecord,@Param(value="param")SystemAccountRecord param);
	/**根据唯一索引更新数据库中的数据
	 * @param systemAccountRecord 更新后的数据
	 * @param NanoTime 索引:记录时间戳-纳秒
	 */
	public void updateSystemAccountRecordByUniqueIndexOnNanoTime(@Param(value="systemAccountRecord")SystemAccountRecord systemAccountRecord,@Param(value="nanoTime")String nanoTime);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param param 更新的条件|数据本身 * @return 符合条件的数据列表 */
	public List<SystemAccountRecord> selectSystemAccountRecordByClass(SystemAccountRecord systemAccountRecord);
	/**根据唯一索引选中数据库中的数据
	 * @param nanoTime 索引:记录时间戳-纳秒
	 * @return 符合条件的数据对象 */
	public SystemAccountRecord selectSystemAccountRecordByUniqueIndexOnNanoTime(@Param(value="nanoTime")String nanoTime);
	
}
