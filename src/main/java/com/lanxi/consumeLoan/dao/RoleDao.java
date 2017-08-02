package com.lanxi.consumeLoan.dao;

import com.lanxi.consumeLoan.entity.Role;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
 * no comment
 * @author yyj | auto generator
 * @version 1.0.0 2017-07-28 13:47:19
 */

public interface RoleDao{
	
	/**插入Role到数据库
	 * @param role 待插入的对象
	 */
	public void addRole(Role role);
	
	/**从数据库中删除符合条件的或者指定的数据
	 * @param role 删除的条件|被删除的数据本身
	 */
	public void deleteRoleByClass(@Param(value="role")Role role);
	/**根据唯一索引从数据库中删除数据
	 * @param roleName 索引:角色名
	 */
	public void deleteRoleByUniqueIndexOnRoleName(@Param(value="roleName")String roleName);
	
	/**更新数据库中符合条件的数据
	 * @param role 更新后的数据
	 * @param param 更新的条件 */
	public void updateRoleByClass(@Param(value="role")Role role,@Param(value="param")Role param);
	/**根据唯一索引更新数据库中的数据
	 * @param role 更新后的数据
	 * @param roleName 索引:角色名
	 */
	public void updateRoleByUniqueIndexOnRoleName(@Param(value="role")Role role,@Param(value="roleName")String roleName);
	
	/**选中数据库中符合条件的数据|数据本身
	 * @param Role 选中的条件|数据本身 * @return 符合条件的数据列表 */
	public List<Role> selectRoleByClass(@Param(value="role")Role role);
	/**根据唯一索引选中数据库中的数据
	 * @param roleName 索引:角色名
	 * @return 符合条件的数据对象 */
	public Role selectRoleByUniqueIndexOnRoleName(@Param(value="roleName")String roleName);
	
}
