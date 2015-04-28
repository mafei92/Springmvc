package com.dfc.springmvc.service;

import java.util.List;

import com.dfc.springmvc.common.PageInfo;
import com.dfc.springmvc.pojo.Role;

/**
 * 数据处理接口
 * 
 * @author dongdong
 *
 */

public interface RoleService {

	public List<Role> queryRoles(PageInfo<Role> pageInfo, Role role);

	public int saveRole(Role role, String menuIds);

	public Role getRoleByRoleId(String roleId);

	public int updateRole(Role role, String menuIds);
	
	public int deleteRole(String roleId);
	
	public List<Role> getAllRoles();
	
	public List<Role> getRolesByUserId(String userId);
	
}
