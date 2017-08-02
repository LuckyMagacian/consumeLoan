package com.lanxi.consumeLoan.backup;

import com.lanxi.consumeLoan.entity.Apply;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-28 17:23:40
 */

public interface ApplyDao{
	
	/**插入Apply到数据库
	 * @param apply 待插入的对象
	 */
	public void addApply(Apply apply);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param apply 删除的条件|被删除的数据本身
	 */
	public void deleteApplyByClass(@Param(value="apply")Apply apply);
	/**根据唯一索引从数据库中删除数据
	 * @param applyId 索引:申请编号
	 */
	public void deleteApplyByUniqueIndexOnApplyId(@Param(value="applyId")String applyId);
	/**根据唯一索引从数据库中删除数据
	 * @param idNumber 索引:申请者身份证号码
	 * @param applyTime 索引:申请时间
	 * @param phone 索引:申请者手机号码
	 */
	public void deleteApplyByUniqueIndexOnIdNumberAndApplyTimeAndPhone(@Param(value="idNumber")String idNumber,@Param(value="applyTime")String applyTime,@Param(value="phone")String phone);
	
	/**更新数据库中符合条件的数据
	 * @param apply 更新后的数据
	 * @param param 更新的条件 */
	public void updateApplyByClass(@Param(value="apply")Apply apply,@Param(value="param")Apply param);
	/**根据唯一索引更新数据库中的数据
	 * @param apply 更新后的数据
	 * @param applyId 索引:申请编号
	 */
	public void updateApplyByUniqueIndexOnApplyId(@Param(value="apply")Apply apply,@Param(value="applyId")String applyId);
	/**根据唯一索引更新数据库中的数据
	 * @param apply 更新后的数据
	 * @param idNumber 索引:申请者身份证号码
	 * @param applyTime 索引:申请时间
	 * @param phone 索引:申请者手机号码
	 */
	public void updateApplyByUniqueIndexOnIdNumberAndApplyTimeAndPhone(@Param(value="apply")Apply apply,@Param(value="idNumber")String idNumber,@Param(value="applyTime")String applyTime,@Param(value="phone")String phone);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param Apply 选中的条件|数据本身 * @return 符合条件的数据列表 */
	public List<Apply> selectApplyByClass(@Param(value="apply")Apply apply);
	/**根据唯一索引选中数据库中的数据
	 * @param applyId 索引:申请编号
	 * @return 符合条件的数据对象 */
	public Apply selectApplyByUniqueIndexOnApplyId(@Param(value="applyId")String applyId);
	/**根据唯一索引选中数据库中的数据
	 * @param idNumber 索引:申请者身份证号码
	 * @param applyTime 索引:申请时间
	 * @param phone 索引:申请者手机号码
	 * @return 符合条件的数据对象 */
	public Apply selectApplyByUniqueIndexOnIdNumberAndApplyTimeAndPhone(@Param(value="idNumber")String idNumber,@Param(value="applyTime")String applyTime,@Param(value="phone")String phone);
	public List<Apply> selectApplyByParam(Map<String, Object> map);
}
