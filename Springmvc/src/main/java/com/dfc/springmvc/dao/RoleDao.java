package com.dfc.springmvc.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dfc.springmvc.pojo.Role;

/**
 * role持久层相关接口
 * 
 * @author dongdong
 * 
 */

@Repository
public interface RoleDao {

	List<Role> queryRoles(RowBounds rowBounds, Role role);

	int getTotalRows(Role role);

	int saveRole(Role role);

	Role getRoleByRoleId(String roleId);

	int updateRole(Role role);

	int deleteRole(String roleId);

	List<Role> getAllRoles();

	List<Role> getRolesByUserId(String userId);

}