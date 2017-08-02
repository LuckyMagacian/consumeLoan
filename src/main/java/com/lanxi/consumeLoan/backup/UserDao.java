package com.lanxi.consumeLoan.backup;

import com.lanxi.consumeLoan.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-24 10:25:36
 */

public interface UserDao{
	
	/**插入User到数据库
	 * @param user 待插入的对象
	 */
	public void addUser(User user);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param user 删除的条件|被删除的数据本身
	 */
	public void deleteUserByClass(User user);
	/**根据唯一索引从数据库中删除数据
	 * @param Phone 索引:手机号码
	 */
	public void deleteUserByUniqueIndexOnPhone(@Param(value="phone")String phone);
	
	/**更新数据库中符合条件的数据
	 * @param user 更新后的数据
	 * @param param 更新的条件 */
	public void updateUserByClass(@Param(value="user")User user,@Param(value="param")User param);
	/**根据唯一索引更新数据库中的数据
	 * @param user 更新后的数据
	 * @param Phone 索引:手机号码
	 */
	public void updateUserByUniqueIndexOnPhone(@Param(value="user")User user,@Param(value="phone")String phone);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param param 更新的条件|数据本身 * @return 符合条件的数据列表 */
	public List<User> selectUserByClass(User user);
	/**根据唯一索引选中数据库中的数据
	 * @param phone 索引:手机号码
	 * @return 符合条件的数据对象 */
	public User selectUserByUniqueIndexOnPhone(@Param(value="phone")String phone);
	
	
	public List<User> selectUserByAttibute(@Param(value="attribute")String attribute);
	
}
